package connectionDB;

import tableClass.*;
import Network.*;

import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;


//import com.mysql.cj.xdevapi.Statement;
// jdbc:mysql://192.168.209.250:3306/dorm
// 카페 192.168.209.250
public class DBManager {
	public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://" + "localhost" + ":3306" + "/dorm" + "?characterEncoding=UTF-8&serverTimezone=UTC";

	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;
	private ResultSet rs;

	private User currentUser;	// 클라이언트의 사용자 정보를 담는 객체
	private String today;   //오늘 날짜
	private String year = "2019";
	private String semester ="2";

	public DBManager(String id, String pw)   //생성자
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");        
		today = sdf.format(date);

		try{
			// 1. 드라이버 로딩
			// 드라이버 인터페이스를 구현한 클래스를 로딩
			// mysql, oracle 등 각 벤더사 마다 클래스 이름이 다르다.
			// mysql은 "com.mysql.jdbc.Driver"이며, 이는 외우는 것이 아니라 구글링하면 된다.
			// 참고로 이전에 연동했던 jar 파일을 보면 com.mysql.jdbc 패키지에 Driver 라는 클래스가 있다.
			//         String dbID, dbPW;
			//         Scanner scan = new Scanner(System.in);
			//         
			//         System.out.print("db manager id :");
			//         dbID = scan.nextLine();
			//         System.out.print("db manager password :");
			//         dbPW = scan.nextLine();      //db manager 생성시 id와 pw를 입력받아서 원격으로 db에 로그인한다

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(URL, id, pw);
			stmt = conn.createStatement();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void closeConnection() throws SQLException
	{
		try {
			//자원 반환
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (conn.isClosed())
				conn.close();
		}
	}
	////////////////////////////////////////////////////////////////////////////
	//기능

	public void loginCheck(Protocol protocol, User user) throws SQLException   //로그인정보 검색
	{
		String loginId = user.getUserID();
		String loginPw = user.getPassword();

		currentUser =user;   //db manager에서 현재 client의 정보를 저장하고 있는다

		String SQL = "SELECT * FROM dorm.사용자";
		rs = stmt.executeQuery(SQL);

		//사용자 테이블의 모든 ID 검색 혹은 일치하는 ID가 있다면 PW 일치 확인 
		while(rs.next()) {    
			if (loginId.equals(rs.getString("사용자ID"))) 
			{   //아이디가 맞는 경우
				if (loginPw.equals(rs.getString("password"))) 
				{
					System.out.println("로그인성공");
					currentUser = user;
					currentUser.setSeparaterUser(rs.getString("사용자구분"));
					currentUser.setName(rs.getString("성명"));

					if(rs.getString("사용자구분").equals("1") == true)		//사용자가 학생인 경우 학번(id)로 학생테이블에서 학생 정보를 객체에 담아서 클라이언트로 보낸다
					{
						String st_number = rs.getString("사용자ID");
						SQL = "SELECT * FROM dorm.학생 where 학번 = " + st_number;
						rs = stmt.executeQuery(SQL);

						rs.next();
						Student student = new Student(rs.getString("학번"), rs.getString("성명"));

						student.setGender(rs.getString("성별"));
						student.setDepartmentName(rs.getString("학과명"));
						student.setGrade(rs.getInt("학년"));
						student.setStudentAddress(rs.getString("학생주소"));
						student.setStudentPhoneNumber(rs.getString("학생전화번호"));
						protocol.makePacket(1,2,1, student);		//클라이언트로 보낼 protocol setting
						return;
					}

					else		//사용자가 관리자나 담당직원인 경우 이름을 클라이언트로 보낸다
					{
						protocol.makePacket(1,2,2, rs.getString("성명"));	
						return;
					}
				} 
			}
		}
		protocol.makePacket(1,2,3, "해당정보 없음");
	}

	public void inquireSchedule(Protocol protocol) {
		//기본 선발일정에 대한 프로그램 코드 : 년도 + 학기 + 01
		SelectionSchedule ss = new SelectionSchedule();
		String programCode = year + semester + "1";
		String sql = "select * from dorm.선발일정  where 프로그램코드='" + programCode + "'"; 
		try {
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				ss.setProgram_code(rs.getString("프로그램코드"));
				ss.setStart_date(rs.getString("게시일"));
				ss.setEnd_date(rs.getString("종료일"));
				ss.setContent(rs.getString("공지내용"));
				protocol.makePacket(2,2,1, ss);
			}
			else {
				protocol.makePacket(2,2,2, "현재 선발일정 없음");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//입사신청 요청에 대한 응답
	public void checkDormitoryApplication(Protocol protocol)
	{
		try   //해당 학기에 학생이 신청 정보가 있는지 확인한다
		{
			System.out.println(currentUser.getUserID());
			String sql = "select * from dorm.신청  where 학번='" + currentUser.getUserID() + "' and 년도='2019' and 학기='2'";
			rs = stmt.executeQuery(sql);

			if(rs.next()) {	//해당 학기에 입사신청정보가 있는경우 학생의 입사신청을 막는다
				protocol.makePacket(11, 2, 2, "신청정보가 있음");
			}
			else {   //신청 정보가 없는 경우 학생의 입사신청을 허용해줌
				protocol.makePacket(11, 2, 1,null);
			}
		}
		catch(SQLException e)
		{
			e.getStackTrace();
		}
	}
	//입사신청 정보 저장
	public void insertDormitoryApplication(Protocol protocol, dormitoryApplication app)   //클라이언트가 입사신청정보를 보낸걸 파라미터로 받음
	{
		try
		{
			String applicationCount= year + semester;		//신청번호 생성
			int tmpAppNum = 0;
			String sql = "select * from 신청 order by 신청번호";
			rs =stmt.executeQuery(sql);
			if (rs.next()) {
				rs.last();
				tmpAppNum = Integer.parseInt(rs.getString("신청번호"));
				tmpAppNum++;
				applicationCount = Integer.toString(tmpAppNum);
			}
			else {
				applicationCount += "1";   //신청번호 생성
			}

			//학생의 총 학점을 계산하는 sql문
			sql = "select 학번, convert(sum(case 성적등급 when 'A+' then convert(4.5*학점,float) when 'A' then convert(4.0*학점,float) " 
					+ "  when 'B+' then convert(3.5*학점,float) when 'B' then convert(3.0*학점,float) " 
					+"   when 'C+' then convert(2.5*학점,float) when 'C' then convert(2.0*학점,float) "
					+"   when 'D+' then convert(1.5*학점,float) when 'D' then convert(1.0*학점,float) "
					+"  when 'F' then convert(0.0*학점,float) end) /sum(학점),decimal(3,2)) as 평점평균 "
					+"from 성적  where 학번 = '" + currentUser.getUserID() + "'" +  " group by 학번";

			rs = stmt.executeQuery(sql);

			Double grade;
			if (rs.next())
				grade = rs.getDouble("평점평균");
			else
				grade = 0.0;

			//거리 가산점 계산 부분 추가(currentUser 학번으로 학생주소 조회후 app 객체 거리가산점 설정)
			String address;
			sql = "SELECT 학생주소 FROM 학생  WHERE 학번='" + currentUser.getUserID() + "'";
			rs = stmt.executeQuery(sql);
			rs.next();
			address = rs.getString("학생주소");
			Date date = new Date();
			today = Integer.toString((date.getYear() + 1900)) + "-" + Integer.toString(date.getMonth() + 1) + "-" + Integer.toString(date.getDate());

			//학생의 주소로 거리가산점 계산
			double distancePoint = 0.0;
			if (address.contains("제주도") || address.contains("울릉군")) {
				distancePoint = 0.4;
			} else if (address.contains("서울") || address.contains("경기") || address.contains("인천") || address.contains("강원")
					|| address.contains("충청") || address.contains("전라") || address.contains("광주") || address.contains("세종")) {
				distancePoint = 0.3;
			} else if (address.contains("대전")|| address.contains("부산") || address.contains("울산") || address.contains("경상남도")) {
				distancePoint = 0.2;
			} else if ((address.contains("경상북도") && !address.contains("구미")) || address.contains("대구")) {
				distancePoint = 0.1;
			} 
			app.setDistancePoint(distancePoint);
			app.setFinallyValue(grade + distancePoint);

			//입사신청정보 테이블에 저장
			sql = "insert into 신청 values ('" + applicationCount + "', '" + currentUser.getUserID() +  "', 2019, 2, "; 
			if (app.getMealDivision1() != null)   sql += "'" + app.getMealDivision1() + "',"; else sql += "null,";
			if (app.getMealDivision2() != null)   sql += "'" + app.getMealDivision2() + "',"; else sql += "null,";
			if (app.getMealDivision3() != null)   sql += "'" + app.getMealDivision3() + "',"; else sql += "null,";
			if (app.getMealDivisionYear() != null)   sql += "'" + app.getMealDivisionYear() + "',"; else sql += "null,";
			sql += grade + ", " + distancePoint + ", " + app.getFinallyValue() + ", ";
			if (app.getDormitoryWish1() != null ) sql += "'" + app.getDormitoryWish1() + "',"; else sql += "null,";
			if (app.getDormitoryWish2() != null ) sql += "'" + app.getDormitoryWish2() + "',"; else sql += "null,";
			if (app.getDormitoryWish3() != null ) sql += "'" + app.getDormitoryWish3() + "',"; else sql += "null,";
			if (app.getDormitoryWishYear() != null ) sql += "'" + app.getDormitoryWishYear() + "',"; else sql += "null,";
			sql += "'" + today + "', '신청', " + 0 +  ", 'O');";
			System.out.println(sql);
			stmt.executeUpdate(sql);

			protocol.makePacket(11, 4, 1, null);	//저장결과 성공
		}
		catch(SQLException e)
		{
			protocol.makePacket(11, 4, 2, "저장 실패");	//저장결과 실패
		}
	}

	//호실조회
	public void roomCheck(Protocol protocol)
	{
		try
		{
			String id = currentUser.getUserID();
			String loginPw = currentUser.getPassword();
			SelectedStudent ss = new SelectedStudent();  

			String SQL = "SELECT * FROM 입사선발자 WHERE 학번='" + id + "'";	 //학생의 입사선발자 정보를 db에서 받아온다
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			if (rs.next()) {		//정보가 있는경우 입사선발자 객체를 만들어서 값을 저장해준다
				ss.setDormitoryCode(rs.getString("생활관분류코드"));
				ss.setRoom_code(rs.getString("호실코드"));
				ss.setBed_code(rs.getString("침대번호"));
				ss.setPay_status(rs.getString("납부상태"));
				ss.setSubmissionTuberculosis(rs.getString("결핵진단서제출여부"));
				ss.setRegister_status(rs.getString("등록여부"));
				protocol.makePacket(12,2,1, ss);		//객체에 정보를 다 저장하고 protocol에 setting
				System.out.println("정상조회");
			} else {
				protocol.makePacket(12,2,2, "해당정보 없음");	//정보가 없는 경우
			}
		}
		catch(SQLException e)
		{
			protocol.makePacket(12,2,2, "조회 실패");
		}
	}
	//입사신청내역 조회
	public void inquireDormitoryApplication(Protocol protocol)
	{
		try
		{
			String sql = "select * from 신청  where 학번='" + currentUser.getUserID() + "' and 년도=" + year +  " and 학기=" + semester;
			rs = stmt.executeQuery(sql);
			rs.last();
			dormitoryApplication[] array = new dormitoryApplication[rs.getRow()];   //해당 학번에 해당하는 신청번호 행들을 저장할 배열 생성
			rs.first();
			rs.previous();
			//rs의 각 인덱스 값을 하나씩짤라서 배열에 저장 
			int i=0;
			while(rs.next())
			{
				array[i]= new dormitoryApplication(rs.getString("신청번호"), rs.getString("학번"));
				array[i].setYear("2019"); array[i].setSemester("2"); 
				array[i].setMealDivision1(rs.getString("1지망식비구분"));array[i].setMealDivision2(rs.getString("2지망식비구분"));
				array[i].setMealDivision3(rs.getString("3지망식비구분"));array[i].setMealDivisionYear(rs.getString("1년식비구분"));
				array[i].setGrade(rs.getFloat("학점")); array[i].setGrade(rs.getFloat("거리가산점")); 
				array[i].setDormitoryWish1(rs.getString("1지망"));array[i].setDormitoryWish2(rs.getString("2지망"));
				array[i].setDormitoryWish3(rs.getString("3지망"));array[i].setDormitoryWishYear(rs.getString("1년지망"));
				array[i].setApplicationDay(rs.getString("신청일"));array[i].setApplicationState(rs.getString("신청상태"));
				array[i].setStandbyNumber(rs.getString("대기번호"));array[i].setAcceptanceOfAgreement(rs.getString("입사서약동의여부")); array[i].setFinallyValue(rs.getFloat("총점")); 
				i++;
			}
			if(i==0)   //입사신청내역이 없으면
				protocol.makePacket(13, 2, 2, "입사신청내역이 없습니다");
			protocol.makePacket(13, 2, 1, array);
		}
		catch(SQLException e)
		{
			protocol.makePacket(13, 2, 2, "조회 실패");
		}
	}

	//maintype 14, 고지서 출력
	public void printBill(Protocol protocol)
	{
		try
		{
			String id = currentUser.getUserID();
			String loginPw = currentUser.getPassword();
			SelectedStudent ss = new SelectedStudent();  

			String SQL = "SELECT * FROM 입사선발자 WHERE 학번='" + id + "'";		//입사선발자에 학생의 정보가 있는지 검색해본다
			System.out.println(SQL);
			rs = stmt.executeQuery(SQL);

			if (rs.next()) {		//정보가 있는경우 입사선발자 객체를 생성해 값을 넣어준다
				ss.setDormitoryCode(rs.getString("생활관분류코드"));
				ss.setRoom_code(rs.getString("호실코드"));
				ss.setBed_code(rs.getString("침대번호"));
				ss.setPay_status(rs.getString("납부상태"));
				ss.setSubmissionTuberculosis(rs.getString("결핵진단서제출여부"));
				ss.setRegister_status(rs.getString("등록여부"));
				ss.setMng_cost(rs.getInt("관리비"));
				ss.setFood_cost(rs.getInt("식비"));
				ss.setTotal_cost(rs.getInt("합계"));
				protocol.makePacket(14,2,1, ss);
				System.out.println("정상조회");
			} else {		//정보가 없는 경우 에러메세지를 클라이언트로 보낼 protocol setting
				protocol.makePacket(14,2,2, "입사선발자가 아니므로 고지서가 없습니다.");
			}
		}
		catch(SQLException e)
		{
			protocol.makePacket(14,2,2, "고지서 조회 실패");
		}
	}


	//결핵진단서 출력 이전에 학생이 입사선발이 됐는지 조회해본다 
	public void checkSelectedStudent(Protocol protocol)
	{
		int main = protocol.getMainType();
		int sub = protocol.getSubType();
		try
		{
			String sql = "select * from 입사선발자  where 학번 = " + currentUser.getUserID();
			rs = stmt.executeQuery(sql);
			if(rs.next())   //입사선발자가 아니고 해당 신청번호로 선발된 값이 하나일경우만
				protocol.makePacket(main,sub+1, 1, null);   //요청 수락
			else
				protocol.makePacket(main, sub+1, 2, "입사선발 대상자가 아님");  
		}
		catch(SQLException e)
		{
			protocol.makePacket(main, sub, 2, "조회 실패");
		}
	}
	//결핵진단서 파일 저장
	public void updateState_TuberculosisDiagnosis(Protocol protocol)   //결핵진단서를 제출했다는 프로토콜을 받으면 결핵진단서 제출 상태를 업데이트 시킨다
	{
		try
		{
			String sql = "update dorm.입사선발자 set 결핵진단서제출여부='O' where 결핵진단서제출여부='X' and 신청번호 =" +(String)protocol.getBody();
			stmt.executeUpdate(sql);
			protocol.makePacket(15,4,1, "결핵진단서 저장 성공");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			protocol.makePacket(15, 4, 2, "결핵진단서 저장 실패");
		}/**/
	}

	//여기서부터 관리자메뉴
	//선발일정 등록
	public void insertSchedule(Protocol protocol, SelectionSchedule schedule)
	{
		String sql;
		int cnt = 1;

		try {
			String year = Integer.toString(schedule.getYear());
			String semester = Integer.toString(schedule.getSemester());
			sql = "select * from dorm.선발일정 where 년도='" + year + "' and 학기='" + semester + "'";
			rs = stmt.executeQuery(sql);
			while(rs.next())		//현재 저장된 선발일정이 몇개인지 알아본다
				cnt += 1;
			rs.beforeFirst();
			String program_code = year + semester + Integer.toString(cnt);	//새로운 프로그램코드 생성
			String start_date = schedule.getStart_date();
			String end_date = schedule.getEnd_date();
			String content = schedule.getContent();
			
			//전달받은 스케쥴을 테이블에 insert
			sql="INSERT INTO dorm.선발일정 VALUES(" + year + ", " + semester + ", '"  + program_code + "', '" 
					+ start_date + "', '"  + end_date + "', '"  + content + "')";
			stmt.executeUpdate(sql);
			protocol.makePacket(21, 2, 1, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			protocol.makePacket(21, 2, 2, "선발일정 등록이 정상적으로 이루어지지 않았습니다.");
		}
	}

	public void insertDormitoryCost(Protocol protocol)   //생할관 사용료 및 급식비 등록
	{
		try
		{
			String sql;
			Dormitory_cost cost[] = (Dormitory_cost[])protocol.getBody();		//클라이언트가 등록 요청과 함께 등록 내용을 보낸다(배열의 형태로)
			for(int i = 0; i < cost.length; i++)
			{
				sql = "select 분류코드 from dorm.생활관 where 생활관명 = '" + cost[i].getKind_code() + "'";
				System.out.println(cost[i].getKind_code());
				rs = stmt.executeQuery(sql);
				rs.next();
				cost[i].setKind_code(rs.getString("분류코드"));	// 테이블에 insert
				sql = "update dorm.생활관비 set 생활관분류코드 = " + cost[i].getKind_code()
						+ ", 관리비_1학기=" + cost[i].getMng_cost1() + ", 관리비_하계방학 = " + cost[i].getMng_cost2()
						+ ", 관리비_2학기 = " + cost[i].getMng_cost3() + ", 관리비_동계방학 = " + cost[i].getMng_cost4()
						+ ", 7일식비_1학기 = " + cost[i].getSd_food_cost1() + ", 7일식비_하계방학 = " + cost[i].getSd_food_cost2()
						+ ", 7일식비_2학기 = " + cost[i].getSd_food_cost3() + ", 7일식비_동계방학 = " + cost[i].getSd_food_cost4()
						+ ", 5일식비_1학기 = " + cost[i].getFd_food_cost1() + ", 5일식비_하계방학 = " + cost[i].getFd_food_cost2()
						+ ", 5일식비_2학기 = " + cost[i].getFd_food_cost3() + ", 5일식비_동계방학 = " + cost[i].getFd_food_cost4()
						+ " where 생활관분류코드 = " + cost[i].getKind_code();
				stmt.executeUpdate(sql);
			}

			protocol.makePacket(22, 2, 1, "저장 성공");	//저장 결과를 클라이언트로 보낸다
			System.out.println("저장성공");

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			protocol.makePacket(22, 2, 2, "생활관비 저장중 오류 발생");
		}

	}
	//입사자등록 -> 입사선발자중 생활관비를 납부하고 결핵진단서를 제출한 학생을 최종 입사자로 등록여부를 update 시켜준다
	public void enrollJoiner(Protocol protocol)
	{
		String sql = "update 입사선발자 set 등록여부='O' where 납부상태='O' and 결핵진단서제출여부='O'";	
		try {
			stmt.executeUpdate(sql);
			protocol.makePacket(23, 2, 1, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			protocol.makePacket(23, 2, 2, "등록이 정상적으로 이루어지지 않았습니다.");
		}


	}

	//입사자 조회, 최종으로 선발된 학생들을 조회해서 클라이언트로 보낸다
	public void joinerCheck(Protocol protocol)
	{   
		int cnt = 0;
		String sql1 = "select 학번, 생활관분류코드, 호실코드, 침대번호 from dorm.입사선발자 where 등록여부='O'";
		try {
			rs = stmt.executeQuery(sql1);
			while(rs.next())
				cnt += 1;		//테이블 행 갯수를 세는 count값
			String[][] arr = new String[cnt][5];
			rs.beforeFirst();	//rs 커서를 검색된 테이블의 맨 처음 행으로 돌림
			int i = 0;
			while(rs.next())	//각 행을 탐색하면서 배열에 값을 채운다 arr[row][속성값]
			{
				arr[i][0] = rs.getString("학번");         
				arr[i][3] = rs.getString("호실코드");
				arr[i][4] = rs.getString("침대번호");
				i++;
			}
			i = 0;
			String sql2 = "select 생활관명 from dorm.생활관 where 분류코드 in (select 생활관분류코드 from 입사선발자"
					+ " where 등록여부='O')"; //입사자들이 입사한 생활관이 어디인지 조회하려고 사용한 구문
			rs = stmt.executeQuery(sql2);
			while(rs.next())
			{
				arr[i][2] = rs.getString("생활관명");	//배열에 넣어준다
				i++;
			}
			i = 0;
			sql2 = "select 성명 from dorm.학생 where 학번 in (select 학번 from dorm.입사선발자 " + //입사자들의 이름을 추가해주려고 검색
					" where 등록여부='O')";		
			rs = stmt.executeQuery(sql2);
			while(rs.next())
			{
				arr[i][1] = rs.getString("성명");
				i++;
			}
			i = 0;
			protocol.makePacket(24, 2, 1, arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			protocol.makePacket(24, 2, 2, "체크가 정상적으로 이루어지지 않았습니다.");
		}
	}

	//입사선발대상자 학생들의 결핵진단서 제출여부 확인
	public void checkTuberculosisDiagnosis(Protocol protocol)
	{
		int cnt = 0;
		String sql="select 신청번호, 학번, 결핵진단서제출여부 from dorm.입사선발자";		//입사선발자의 테이블을 불러온다
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
				cnt += 1;
			String[][] arr = new String[cnt][3];
			rs.beforeFirst();
			int i = 0;
			while(rs.next())	//행을 탐색하면서 배열에 값을 저장한다
			{
				arr[i][0] = rs.getString("신청번호");
				arr[i][1] = rs.getString("학번");
				arr[i][2] = rs.getString("결핵진단서제출여부");
				i++;
			}

			Arrays.sort(arr, new Comparator<String[]>() {
				@Override
				public int compare(final String[] entry1, final String[] entry2) {
					if( ((Comparable)entry2[1]).compareTo(entry1[1]) < 0 )
						return 1;
					else
						return -1;
				}
			});
			protocol.makePacket(26, 2, 1, arr);		//클라이언트로 배열을 보낸다
		} catch (SQLException e) {
			e.printStackTrace();
			protocol.makePacket(26, 2, 2, "제출여부를 정상적으로 가져오지 못했습니다.");
		}
	}
	//결핵진단서 업로드
	public void dicisionTuberculosisDiagnosisSubmit(Protocol protocol)
	{
		String studentNumber = (String)protocol.getBody();
		String sql="select 학번, 신청번호 from dorm.입사선발자 where 결핵진단서제출여부 = 'X' and 학번 = " + studentNumber;

		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				System.out.println("studentNumber : " + studentNumber);
				System.out.println(rs.getString("학번"));
				protocol.makePacket(27, 2, 1, rs.getString("신청번호")); 
			}
			else
			{
				protocol.makePacket(27, 2, 2, "대상 학생이 아닙니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			protocol.makePacket(27, 2, 2, "쿼리도중 오류가 발생했습니다.");
		}
	}
	//입사선발자 납부상태 update -> 기숙사비를 오프라인으로 낸 경우만 고려해서 관리자가 납부상태를 업데이트 해줘야됨
	public void dicisionCostUploadSubmit(Protocol protocol)
	{
		String studentNumber = (String)protocol.getBody();
		String sql="select 학번, 신청번호 from dorm.입사선발자 where 납부상태 = 'X' and 학번 = " + studentNumber;
		try {
			rs = stmt.executeQuery(sql);
			if(!rs.next())
				protocol.makePacket(28, 2, 2, "대상학생이 아닙니다.111111111111111");
			else
			{
				rs.first();
				if(studentNumber.equals(rs.getString("학번")))
				{
					sql="update dorm.입사선발자 set 납부상태='O' where 학번 ='" + studentNumber + "'";
					stmt.executeUpdate(sql);
					protocol.makePacket(28, 2, 1, null);
				}

				else
					protocol.makePacket(28, 2, 2, "대상학생이 아닙니다.2222222222222");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			protocol.makePacket(28, 2, 2, "쿼리도중 오류가 발생했습니다.");
		}
	}
//입사선발자 결과등록
	public void enrollSelectedStudent(Protocol protocol)
	{
		// 1년지망
		int cnt = 0;
		int waitNumber = 0;
		String oneSemesterCost[] = new String[3];
		String oneYearCost[] = new String[12];
		String sql;   
		sql = "select 신청번호, 학번, 총점, 1년지망, 1년식비구분 from dorm.신청 where 년도=2019 and 학기=2 and 신청상태='신청' and 1년지망 is not null";
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next())
				cnt += 1;
			rs.beforeFirst();
			String oneYearHuman[][] = new String [cnt][5];
			cnt = 0;
			int i = 0;
			while(rs.next())
			{
				oneYearHuman[i][0] = rs.getString("신청번호");
				oneYearHuman[i][1] = rs.getString("학번");
				oneYearHuman[i][2] = rs.getString("총점");
				oneYearHuman[i][3] = rs.getString("1년지망");
				oneYearHuman[i][4] = rs.getString("1년식비구분");
				i++;
			}
			i = 0;

			//배열 정렬
			Arrays.sort(oneYearHuman, new Comparator<String[]>() {
				@Override      //2, 1은 오름차순, 1, 2는 내림차순
				public int compare(final String[] entry1, final String[] entry2) {
					if( ((Comparable)entry1[2]).compareTo(entry2[2]) < 0 )
						return 1;
					else
						return -1;
				}
			});

			sql = "select * from dorm.생활관호실 where 배정상태='X'";
			rs = stmt.executeQuery(sql);
			while(rs.next())
				cnt += 1;
			rs.beforeFirst();
			String oneYearRoom[][] = new String[cnt][4];
			cnt = 0;
			while(rs.next())
			{
				oneYearRoom[i][0] = rs.getString("생활관분류코드");
				oneYearRoom[i][1] = rs.getString("호실코드");
				oneYearRoom[i][2] = rs.getString("침대번호");
				oneYearRoom[i][3] = rs.getString("배정상태");
				i++;
			}
			i = 0;
			//배열 정렬
			Arrays.sort(oneYearRoom, new Comparator<String[]>() {
				@Override      //2, 1은 오름차순, 1, 2는 내림차순
				public int compare(final String[] entry1, final String[] entry2) {
					if( ((Comparable)entry2[0]).compareTo(entry1[0]) < 0 )
						return 1;
					else
						return -1;
				}
			});

			System.out.println("디버깅 중간다리");
			
			//호실 배정상태가 "X인 호실을 검색해서 입사선발자를 insert 하는 동시에 호실을 배정해준다
			for(int j = 0; j < oneYearHuman.length; j++)
			{
				for(int k = 0; k < oneYearRoom.length; k++)
				{
					if(oneYearHuman[j][3].equals(oneYearRoom[k][0]) && oneYearRoom[k][3].equals("X"))
					{
						sql = "insert into dorm.입사선발자 values('" + oneYearHuman[j][0] + "', '" + oneYearHuman[j][1]
		                        + "', '" + oneYearRoom[k][1] + "', '" + oneYearRoom[k][2] + "', null, null, null, 'X'"
		                              + ", 'X', 'X', '" + oneYearHuman[j][3] + "', 'O', '" + oneYearHuman[j][4] + "')";
						stmt.executeUpdate(sql);		//1년지망 입사선발된 학생을 테이블에 insert

						sql = "select * from dorm.생활관비 where 생활관분류코드=" + oneYearHuman[j][3];
						rs = stmt.executeQuery(sql);
						rs.next();

						oneYearCost[0] = rs.getString("관리비_1학기");		//1년 기숙에 대한 값을 저장하는 배열
						oneYearCost[1] = rs.getString("관리비_하계방학");
						oneYearCost[2] = rs.getString("관리비_2학기");
						oneYearCost[3] = rs.getString("관리비_동계방학");
						oneYearCost[4] = rs.getString("7일식비_1학기");
						oneYearCost[5] = rs.getString("7일식비_하계방학");
						oneYearCost[6] = rs.getString("7일식비_2학기");
						oneYearCost[7] = rs.getString("7일식비_동계방학");
						oneYearCost[8] = rs.getString("5일식비_1학기");
						oneYearCost[9] = rs.getString("5일식비_하계방학");
						oneYearCost[10] = rs.getString("5일식비_2학기");
						oneYearCost[11] = rs.getString("5일식비_동계방학");

						int mng_sum = Integer.parseInt(oneYearCost[0]) + Integer.parseInt(oneYearCost[1])	//관리비 계산
						+ Integer.parseInt(oneYearCost[2]) + Integer.parseInt(oneYearCost[3]);

						int fd_sum = Integer.parseInt(oneYearCost[4]) + Integer.parseInt(oneYearCost[5])	//5일식 계산(five day)
						+ Integer.parseInt(oneYearCost[6]) + Integer.parseInt(oneYearCost[7]);

						int sd_sum = Integer.parseInt(oneYearCost[8]) + Integer.parseInt(oneYearCost[9])	//7일식 계산(seven day)
						+ Integer.parseInt(oneYearCost[10]) + Integer.parseInt(oneYearCost[11]);

						if(oneYearHuman[j][4].equals("5일식"))		//1년 지망의 식비구분이 5일식인 경우의 sql문
						{
							sql = "update dorm.입사선발자 set 관리비=" + mng_sum + ", 식비=" + fd_sum + ", 합계=" + (mng_sum+fd_sum)
									+ " where 신청번호 = '" + oneYearHuman[j][0] + "'";

						}
						else if(oneYearHuman[j][4].equals("7일식"))	//1년 지망의 식비구분이 7일식인 경우의 sql문
						{
							sql = "update dorm.입사선발자 set 관리비=" + mng_sum + ", 식비=" + sd_sum + ", 합계=" + (mng_sum+sd_sum)
									+ " where 신청번호 = '" + oneYearHuman[j][0] + "'";
						}
						else if(oneYearHuman[j][4].equals("식사안함"))		//1년 지망의 식비구분이 식사안함인 경우의 sql문
							sql = "update dorm.입사선발자 set 관리비=" + mng_sum + ", 합계=" + (mng_sum) + " where 신청번호 = '" + oneYearHuman[j][0] + "'";

						stmt.executeUpdate(sql);		//sql문으로 update

						sql = "update dorm.생활관호실 set 배정상태='O' where 생활관분류코드='" + oneYearRoom[k][0]
								+ "'and 호실코드 ='" + oneYearRoom[k][1] + "'and 침대번호='" + oneYearRoom[k][2] + "'";
						stmt.executeUpdate(sql);

						oneYearRoom[k][3] = "O";

						sql = "update dorm.신청 set 신청상태='합격' where 신청번호='" + oneYearHuman[j][0] + "'";
						stmt.executeUpdate(sql);

						System.out.println(oneYearHuman[j][0] + "등록!");
						break;
					}

					else if (k == oneYearRoom.length - 1 &&
							!(oneYearHuman[j][3].equals(oneYearRoom[k][0]) && oneYearRoom[k][3].equals("X")))
					{
						waitNumber += 1;
						sql = "update dorm.신청 set 대기번호=" + waitNumber + " where 신청번호='" + oneYearHuman[j][0] + "'";
						stmt.executeUpdate(sql);
					}
				}      
			}

			//한학기 지망
			cnt = 0;
			sql = "select 신청번호, 학번, 학기, 총점, 1지망, 2지망, 3지망, 1지망식비구분, 2지망식비구분, 3지망식비구분"
					+ " from dorm.신청 where 년도=2019 and 학기=2 and 신청상태='신청' and 1년지망 is null";	//1학기 지망을 신청한 학생들을 모두 불러온다
			rs = stmt.executeQuery(sql);
			while(rs.next())
				cnt += 1;
			rs.beforeFirst();
			String oneSemesterHuman[][] = new String [cnt][10];		//기숙사 신청지망이 1학기인 학생들을 저장하는 배열
			cnt = 0;
			int l = 0;
			while(rs.next())		//sql문으로 불러온 테이블의 행을 탐색하면서 값을 배열에 저장해준다
			{
				oneSemesterHuman[l][0] = rs.getString("신청번호");
				oneSemesterHuman[l][1] = rs.getString("학번");
				oneSemesterHuman[l][2] = rs.getString("학기");
				oneSemesterHuman[l][3] = rs.getString("총점");
				oneSemesterHuman[l][4] = rs.getString("1지망");
				oneSemesterHuman[l][5] = rs.getString("2지망");
				oneSemesterHuman[l][6] = rs.getString("3지망");
				oneSemesterHuman[l][7] = rs.getString("1지망식비구분");
				oneSemesterHuman[l][8] = rs.getString("2지망식비구분");
				oneSemesterHuman[l][9] = rs.getString("3지망식비구분");
				l++;
				System.out.println("1학기 디버깅");
			}
			l = 0;

			//
			Arrays.sort(oneSemesterHuman, new Comparator<String[]>() {
				@Override      //2, 1은 오름차순, 1, 2는 내림차순
				public int compare(final String[] entry1, final String[] entry2) {
					if( ((Comparable)entry1[2]).compareTo(entry2[2]) < 0 )
						return 1;
					else
						return -1;
				}
			});

			sql = "select * from dorm.생활관호실 where 배정상태='X'";
			rs = stmt.executeQuery(sql);
			while(rs.next())
				cnt += 1;
			rs.beforeFirst();
			String oneSemesterRoom[][] = new String[cnt][4];		//배정이 안된 호실정보를 담을 배열
			cnt = 0;
			System.out.println("i값: " + i);
			while(rs.next())
			{
				oneSemesterRoom[i][0] = rs.getString("생활관분류코드");
				oneSemesterRoom[i][1] = rs.getString("호실코드");
				oneSemesterRoom[i][2] = rs.getString("침대번호");
				oneSemesterRoom[i][3] = rs.getString("배정상태");
				i++;
			}

			Arrays.sort(oneSemesterRoom, new Comparator<String[]>() {
				@Override      //2, 1은 오름차순, 1, 2는 내림차순
				public int compare(final String[] entry1, final String[] entry2) {
					if( ((Comparable)entry2[0]).compareTo(entry1[0]) < 0 )
						return 1;
					else
						return -1;
				}
			});


			int c = 0;
			for(int j = 0; j < oneSemesterHuman.length; j++)
			{
				for(int m = 4; m < 7; m++)
				{
					for(int k = 0; k < oneSemesterRoom.length; k++)
					{
						if(oneSemesterHuman[j][m]!= null && oneSemesterHuman[j][m].equals(oneSemesterRoom[k][0]) && oneSemesterRoom[k][3].equals("X"))
						{
							//System.out.println(j + " " + m + " " + k);
							sql = "insert into dorm.입사선발자 values('" + oneSemesterHuman[j][0] + "', '" + oneSemesterHuman[j][1]
			                           + "', '" + oneSemesterRoom[k][1] + "', '" + oneSemesterRoom[k][2] + "', null, null, null, 'X'"
		                                 + ", 'X', 'X', '" + oneSemesterHuman[j][m] + "', 'X', '" + oneSemesterHuman[j][m+3] + "')";
							stmt.executeUpdate(sql);

							if(oneSemesterHuman[j][2].equals("1"))
							{
								sql = "select 관리비_1학기, 7일식비_1학기, 5일식비_1학기"
										+ " from dorm.생활관비 where 생활관분류코드=" + oneSemesterHuman[j][m];

								rs = stmt.executeQuery(sql);
								rs.next();

								oneSemesterCost[0] = rs.getString("관리비_1학기");
								oneSemesterCost[1] = rs.getString("7일식비_1학기");
								oneSemesterCost[2] = rs.getString("5일식비_1학기");
							}

							else if(oneSemesterHuman[j][2].equals("2"))
							{
								sql = "select 관리비_2학기, 7일식비_2학기, 5일식비_2학기"
										+ " from dorm.생활관비 where 생활관분류코드=" + oneSemesterHuman[j][m];

								rs = stmt.executeQuery(sql);
								rs.next();

								oneSemesterCost[0] = rs.getString("관리비_2학기");
								oneSemesterCost[1] = rs.getString("7일식비_2학기");
								oneSemesterCost[2] = rs.getString("5일식비_2학기");
							}

							int mng = Integer.parseInt(oneSemesterCost[0]);
							int fd = Integer.parseInt(oneSemesterCost[1]);
							int sd = Integer.parseInt(oneSemesterCost[2]);

							if(oneSemesterHuman[j][m+3].equals("5일식"))
							{
								sql = "update dorm.입사선발자 set 관리비=" + mng + ", 식비=" + fd + ", 합계=" + (mng+fd)
										+ " where 신청번호 = '" + oneSemesterHuman[j][0] + "'";   
							}
							else if(oneSemesterHuman[j][m+3].equals("7일식"))
							{
								sql = "update dorm.입사선발자 set 관리비=" + mng + ", 식비=" + sd + ", 합계=" + (mng+sd)
										+ " where 신청번호 = '" + oneSemesterHuman[j][0] + "'";
							}
							else if(oneSemesterHuman[j][m+3].equals("식사안함"))
								sql = "update dorm.입사선발자 set 관리비=" + mng + ", 합계=" + mng + " where 신청번호 = '" + oneSemesterHuman[j][0] + "'";

							stmt.executeUpdate(sql);

							sql = "update dorm.생활관호실 set 배정상태='O' where 생활관분류코드='" + oneSemesterRoom[k][0]
									+ "'and 호실코드 ='" + oneSemesterRoom[k][1] + "'and 침대번호='" + oneSemesterRoom[k][2] + "'";
							stmt.executeUpdate(sql);

							oneSemesterRoom[k][3] = "O";

							sql = "update dorm.신청 set 신청상태='합격' where 신청번호='" + oneSemesterHuman[j][0] + "'";
							stmt.executeUpdate(sql);

							System.out.println(oneSemesterHuman[j][0] + "등록!");
							c = 1;	//1학기 기숙에 대한 배정을 끝난거를 알려주는 값
							break;
						}
						//3지망까지 입사선발이 되지 않은 학생에 대해서 대기번호를 부여한다
						else if (m == 6 && k == oneSemesterRoom.length - 1 &&
								!(oneSemesterHuman[j][m] != null && oneSemesterHuman[j][m].equals(oneSemesterRoom[k][0]) && oneSemesterRoom[k][3].equals("X")))
						{
							waitNumber += 1;
							sql = "update dorm.신청 set 대기번호=" + waitNumber + " where 신청번호='" + oneSemesterHuman[j][0] + "'";
							stmt.executeUpdate(sql);
						}
					}//다음 학생에 대해서 검색해주기 위해서 break 해주고 배정상태를 알려주는 함수도 원상복구 시킨다
					if(c == 1)
					{
						c = 0;
						break;
					}
				}      
			}

			protocol.makePacket(25, 2, 1, null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			protocol.makePacket(25, 2, 2, "쿼리도중 오류가 발생했습니다.");
		}
	}

	private static void checkWarnings(SQLWarning w) throws SQLException {
		if (w != null) {
			while (w != null) {
				System.out.println("SQL 상태:" + w.getSQLState());
				System.out.println("자바 예외 메세지:" + w.getMessage());
				System.out.println("DBMS 에러 코드:" + w.getErrorCode());
				w.getNextWarning();
			}
		}
	}
}