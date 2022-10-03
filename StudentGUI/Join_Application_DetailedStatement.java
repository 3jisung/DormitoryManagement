/**
 * @Copyright 융합프로젝트 8조
 */

package StudentGUI;

import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Network.Protocol;
import tableClass.*;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 * @file Join_Application_Detailed.java
 * 
 * @author 김동윤, 김선진
 * 
 * @day 2019-12-11
 * 
 * @brief 이 클래스는 Menu_Student 클래스에서 생성되며 소켓 통신을 통해 서버로 부터 사용자의 입사 신청 정보를 수신받아
 *        GUI를 통해 화면에 출력함.
 */

public class Join_Application_DetailedStatement extends JFrame {
	/** GUI 출력을 위한 텍스트 필드 선언 */
	private JPanel contentPane;
	private JTextField textField_title_ApplicationDetailed;
	private JTextField textField_Id;
	private JTextField textField_Name;
	private JTextField textField_Department;
	private JTextField textField_Grade;
	private JTextField textField_Addres;
	private JTextField textField_StudenId;
	private JTextField textField_StudentName;
	private JTextField textField_StudentGrade;
	private JTextField textField_StudentDepartment;
	private JTextField textField_StudentAddress;
	
	/** 소켓 통신을 위한 변수 선언 */
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private Student student;
	private dormitoryApplication[] appList;

	public Join_Application_DetailedStatement(Student s, dormitoryApplication[] apps, Socket sk) {
		// 소켓 통신을 위한 변수 초기화
		socket = sk;
		appList = apps;
		student = s;

		// GUI 출력
		this.setResizable(false); // 최대화 단추 없애기
		setVisible(true);
		setTitle("입사신청 내역조회");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 990, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textField_title_ApplicationDetailed = new JTextField();
		textField_title_ApplicationDetailed.setText("학적내역");
		textField_title_ApplicationDetailed.setForeground(Color.RED);
		textField_title_ApplicationDetailed.setFont(new Font("굴림", Font.PLAIN, 17));
		textField_title_ApplicationDetailed.setEditable(false);
		textField_title_ApplicationDetailed.setColumns(10);
		textField_title_ApplicationDetailed.setBounds(12, 10, 169, 40);
		contentPane.add(textField_title_ApplicationDetailed);
		textField_Id = new JTextField();
		textField_Id.setText("학번");
		textField_Id.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Id.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Id.setEditable(false);
		textField_Id.setColumns(10);
		textField_Id.setBackground(Color.LIGHT_GRAY);
		textField_Id.setBounds(12, 60, 125, 40);
		contentPane.add(textField_Id);

		textField_Department = new JTextField();
		textField_Department.setText("학과");
		textField_Department.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Department.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Department.setEditable(false);
		textField_Department.setColumns(10);
		textField_Department.setBackground(Color.LIGHT_GRAY);
		textField_Department.setBounds(325, 60, 125, 40);
		contentPane.add(textField_Department);

		textField_Name = new JTextField();
		textField_Name.setText("성명");
		textField_Name.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Name.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Name.setEditable(false);
		textField_Name.setColumns(10);
		textField_Name.setBackground(Color.LIGHT_GRAY);
		textField_Name.setBounds(638, 60, 125, 40);
		contentPane.add(textField_Name);

		textField_Grade = new JTextField();
		textField_Grade.setText("학년");
		textField_Grade.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Grade.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Grade.setEditable(false);
		textField_Grade.setColumns(10);
		textField_Grade.setBackground(Color.LIGHT_GRAY);
		textField_Grade.setBounds(12, 110, 125, 40);
		contentPane.add(textField_Grade);

		textField_Addres = new JTextField();
		textField_Addres.setText("주소");
		textField_Addres.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Addres.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Addres.setEditable(false);
		textField_Addres.setColumns(10);
		textField_Addres.setBackground(Color.LIGHT_GRAY);
		textField_Addres.setBounds(325, 110, 125, 40);
		contentPane.add(textField_Addres);

		textField_StudentName = new JTextField(); // 성명
		textField_StudentName.setText(student.getName());
		textField_StudentName.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentName.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentName.setEditable(false);
		textField_StudentName.setColumns(10);
		textField_StudentName.setBounds(763, 60, 190, 40);
		contentPane.add(textField_StudentName);

		textField_StudenId = new JTextField(); // 학번
		textField_StudenId.setText(student.getStudentId());
		textField_StudenId.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudenId.setFont(new Font("굴림", Font.PLAIN, 16));

		textField_StudenId.setEditable(false);
		textField_StudenId.setColumns(10);
		textField_StudenId.setBounds(136, 60, 190, 40);
		contentPane.add(textField_StudenId);

		textField_StudentGrade = new JTextField(); // 학년
		textField_StudentGrade.setText(Integer.toString(student.getGrade()));
		textField_StudentGrade.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentGrade.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentGrade.setEditable(false);
		textField_StudentGrade.setColumns(10);
		textField_StudentGrade.setBounds(136, 110, 190, 40);
		contentPane.add(textField_StudentGrade);

		textField_StudentDepartment = new JTextField(); // 학과
		textField_StudentDepartment.setText(student.getDepartmentName());
		textField_StudentDepartment.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentDepartment.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentDepartment.setEditable(false);
		textField_StudentDepartment.setColumns(10);
		textField_StudentDepartment.setBounds(449, 60, 190, 40);
		contentPane.add(textField_StudentDepartment);

		textField_StudentAddress = new JTextField(); // 주소
		textField_StudentAddress.setText(student.getStudentAddress());
		textField_StudentAddress.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentAddress.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentAddress.setEditable(false);
		textField_StudentAddress.setColumns(10);
		textField_StudentAddress.setBounds(449, 110, 507, 40);
		contentPane.add(textField_StudentAddress);

		// 테이블에 출력할 컬럼 이름 배열
		String columnNames[] = { "No.", "생활관구분", "지망", "식비구분", "결과" };

		// 테이블에 출력할 데이터 배열

		String data[][] = new String[4][5]; // 데이터 들어갈 범위
		int rowIdx = 0;
		if (appList[0].getDormitoryWish1() != null) {
			data[rowIdx][0] = appList[0].getApplicatonNumber();
			data[rowIdx][1] = convertDormitoryCodeToName(appList[0].getDormitoryWish1());
			data[rowIdx][2] = "1지망";
			data[rowIdx][3] = appList[0].getMealDivision1();
			data[rowIdx++][4] = appList[0].getApplicationState();
		}
		if (appList[0].getDormitoryWish2() != null) {
			data[rowIdx][0] = appList[0].getApplicatonNumber();
			data[rowIdx][1] = convertDormitoryCodeToName(appList[0].getDormitoryWish2());
			data[rowIdx][2] = "2지망";
			data[rowIdx][3] = appList[0].getMealDivision2();
			data[rowIdx++][4] = appList[0].getApplicationState();
		}
		if (appList[0].getDormitoryWish3() != null) {
			data[rowIdx][0] = appList[0].getApplicatonNumber();
			data[rowIdx][1] = convertDormitoryCodeToName(appList[0].getDormitoryWish3());
			data[rowIdx][2] = "3지망";
			data[rowIdx][3] = appList[0].getMealDivision3();
			data[rowIdx++][4] = appList[0].getApplicationState();
		}
		if (appList[0].getDormitoryWishYear() != null) {
			data[rowIdx][0] = appList[0].getApplicatonNumber();
			data[rowIdx][1] = convertDormitoryCodeToName(appList[0].getDormitoryWishYear());
			data[rowIdx][2] = "1년기숙";
			data[rowIdx][3] = appList[0].getMealDivisionYear();
			data[rowIdx++][4] = appList[0].getApplicationState();
		}

		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int rowIndex, int mCollindex) {
				return false;
			}
		};
		JTable tbl = new JTable(model);
		tbl.setRowHeight(25);

		// JTable tbl = new JTable(data,columnNames);
		// Table은 JScrollPane위에 출력해야 컬럼 이름이 출력된다! 명심할것
		JScrollPane scroll_ApplicationDetailed = new JScrollPane(tbl);
		scroll_ApplicationDetailed.getVerticalScrollBar().setUnitIncrement(100); // 스크롤 속도
		scroll_ApplicationDetailed.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scroll_ApplicationDetailed);
		scroll_ApplicationDetailed.setSize(940, 165);
		scroll_ApplicationDetailed.setLocation(12, 170);
	}


	/**
	 * 생활관 이름을 코드로 변환 하는 메소드
	 * @param String s 는 생활관 이름
	 * @return code 는 생활관 코드
	 **/
	
	public String convertDormitoryCodeToName(String s) {
		String name = null;
		if (s.equals("1"))
			name = new String("푸름관1동");
		else if (s.equals("2"))
			name = new String("푸름관2동");
		else if (s.equals("3"))
			name = new String("푸름관3동");
		else if (s.equals("4"))
			name = new String("푸름관4동");

		else if (s.equals("5"))
			name = new String("오름관1동");
		else if (s.equals("6"))
			name = new String("오름관2동");
		else if (s.equals("7"))
			name = new String("오름관3동");

		else if (s.equals("8"))
			name = new String("신평관1동");
		else if (s.equals("9"))
			name = new String("신평관2동");

		return name;
	}
}