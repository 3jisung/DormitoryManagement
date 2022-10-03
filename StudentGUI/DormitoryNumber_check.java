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
import javax.swing.border.EmptyBorder;

import Network.Protocol;
import tableClass.*;

import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 * @file DormitoryNumber_check.java
 * 
 * @author 김동윤, 김선진
 * 
 * @day 2019-12-11
 * 
 * @brief 이 클래스는 Menu_Student 클래스에서 생성되며 입사선발자의 정보를 수신받아 GUI를 통해 화면에 출력함.
 */

public class DormitoryNumber_check extends JFrame {

	/** GUI 출력을 위한 텍스트 필드 선언 */
	private JPanel contentPane;
	private JTextField textField_title_AcademicRecord;
	private JTextField textField_Id;
	private JTextField textField_Department;
	private JTextField textField_Name;
	private JTextField textField_Grade;
	private JTextField textField_Address;
	private JTextField textField_StudentId;
	private JTextField textField_StudentDepartment;
	private JTextField textField_StudentName;
	private JTextField textField_StudentGrade;
	private JTextField textField_StudentAddress;
	private JTextField textField_title_DormitoryNumber_Check;
	private JTextField textField_RegisterStatus;
	private JTextField textField_PayStatus;
	private JTextField textField_RoomStatus;
	private JTextField textField_Dormitory;
	private JTextField textField_RoomCode;
	private JTextField textField_Student_RegisterStatus;
	private JTextField textField_Student_PayStatus;
	private JTextField textField_StudentRoomStatus;
	private JTextField textField_StudentDormitory;
	private JTextField textField_StudentRoomCode;
	private JButton Button_Check;

	/** 소켓 통신을 위한 변수 선언 */
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private Student student;
	private SelectedStudent selectedStudent;

	public DormitoryNumber_check(Student s, SelectedStudent ss, ObjectOutputStream oos, ObjectInputStream ois,
			Socket sk) {

		// 소켓 통신 변수 초기화
		socket = sk;
		student = s;
		selectedStudent = ss;
		writer = oos;
		reader = ois;

		//GUI 출력
		this.setResizable(false); // 최대화 단추 없애기
		setTitle("호실조회");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 300, 1010, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_title_AcademicRecord = new JTextField();
		textField_title_AcademicRecord.setText("\u25C8 학적내역");
		textField_title_AcademicRecord.setForeground(Color.RED);
		textField_title_AcademicRecord.setFont(new Font("굴림", Font.PLAIN, 17));
		textField_title_AcademicRecord.setEditable(false);
		textField_title_AcademicRecord.setColumns(10);
		textField_title_AcademicRecord.setBounds(35, 32, 169, 40);
		contentPane.add(textField_title_AcademicRecord);

		textField_StudentAddress = new JTextField(); // 주소

		textField_StudentAddress.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentAddress.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentAddress.setEditable(false);
		textField_StudentAddress.setColumns(10);
		textField_StudentAddress.setBounds(472, 128, 503, 40);
		contentPane.add(textField_StudentAddress);

		textField_Department = new JTextField();
		textField_Department.setEditable(false);
		textField_Department.setText("학과");
		textField_Department.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Department.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Department.setColumns(10);
		textField_Department.setBackground(Color.LIGHT_GRAY);
		textField_Department.setBounds(348, 82, 125, 40);
		contentPane.add(textField_Department);

		textField_Name = new JTextField();
		textField_Name.setEditable(false);
		textField_Name.setText("성명");
		textField_Name.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Name.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Name.setColumns(10);
		textField_Name.setBackground(Color.LIGHT_GRAY);
		textField_Name.setBounds(661, 82, 125, 40);
		contentPane.add(textField_Name);

		textField_Grade = new JTextField();
		textField_Grade.setEditable(false);
		textField_Grade.setText("학년");
		textField_Grade.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Grade.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Grade.setColumns(10);
		textField_Grade.setBackground(Color.LIGHT_GRAY);
		textField_Grade.setBounds(35, 128, 125, 40);
		contentPane.add(textField_Grade);

		textField_Address = new JTextField();
		textField_Address.setText("주소");
		textField_Address.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Address.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Address.setEditable(false);
		textField_Address.setColumns(10);
		textField_Address.setBackground(Color.LIGHT_GRAY);
		textField_Address.setBounds(348, 128, 125, 40);
		contentPane.add(textField_Address);

		textField_StudentId = new JTextField(""); // 학번

		textField_StudentId.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentId.setEditable(false);
		textField_StudentId.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentId.setColumns(10);
		textField_StudentId.setBounds(159, 82, 190, 40);
		contentPane.add(textField_StudentId);

		textField_StudentDepartment = new JTextField(); // 학과

		textField_StudentDepartment.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentDepartment.setEditable(false);
		textField_StudentDepartment.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentDepartment.setColumns(10);
		textField_StudentDepartment.setBounds(472, 82, 190, 40);
		contentPane.add(textField_StudentDepartment);

		textField_StudentName = new JTextField(); // 성명

		textField_StudentName.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentName.setEditable(false);
		textField_StudentName.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentName.setColumns(10);
		textField_StudentName.setBounds(785, 82, 190, 40);
		contentPane.add(textField_StudentName);

		textField_StudentGrade = new JTextField(); // 학년

		textField_StudentGrade.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentGrade.setEditable(false);
		textField_StudentGrade.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentGrade.setColumns(10);
		textField_StudentGrade.setBounds(159, 128, 190, 40);
		contentPane.add(textField_StudentGrade);

		textField_title_DormitoryNumber_Check = new JTextField();
		textField_title_DormitoryNumber_Check.setText("\u25C8 신청 생활관 및 호실 확인");
		textField_title_DormitoryNumber_Check.setForeground(Color.RED);
		textField_title_DormitoryNumber_Check.setFont(new Font("굴림", Font.PLAIN, 17));
		textField_title_DormitoryNumber_Check.setEditable(false);
		textField_title_DormitoryNumber_Check.setColumns(10);
		textField_title_DormitoryNumber_Check.setBounds(35, 178, 250, 40);
		contentPane.add(textField_title_DormitoryNumber_Check);

		textField_Id = new JTextField();
		textField_Id.setEditable(false);
		textField_Id.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Id.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Id.setText("학번");
		textField_Id.setBackground(Color.LIGHT_GRAY);
		textField_Id.setBounds(35, 82, 125, 40);
		contentPane.add(textField_Id);
		textField_Id.setColumns(10);

		textField_PayStatus = new JTextField();
		textField_PayStatus.setEditable(false);
		textField_PayStatus.setText("납부여부");
		textField_PayStatus.setHorizontalAlignment(SwingConstants.CENTER);
		textField_PayStatus.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_PayStatus.setColumns(10);
		textField_PayStatus.setBackground(Color.LIGHT_GRAY);
		textField_PayStatus.setBounds(504, 228, 280, 40);
		contentPane.add(textField_PayStatus);

		textField_Dormitory = new JTextField();
		textField_Dormitory.setEditable(false);
		textField_Dormitory.setText("생활관");
		textField_Dormitory.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Dormitory.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Dormitory.setColumns(10);
		textField_Dormitory.setBackground(Color.LIGHT_GRAY);
		textField_Dormitory.setBounds(504, 267, 280, 40);
		contentPane.add(textField_Dormitory);

		textField_RoomCode = new JTextField();
		textField_RoomCode.setEditable(false);
		textField_RoomCode.setText("호실 / 침대번호");
		textField_RoomCode.setHorizontalAlignment(SwingConstants.CENTER);
		textField_RoomCode.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_RoomCode.setColumns(10);
		textField_RoomCode.setBackground(Color.LIGHT_GRAY);
		textField_RoomCode.setBounds(504, 307, 280, 40);
		contentPane.add(textField_RoomCode);

		textField_Student_RegisterStatus = new JTextField(""); // 선발결과

		textField_Student_RegisterStatus.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Student_RegisterStatus.setEditable(false);
		textField_Student_RegisterStatus.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Student_RegisterStatus.setColumns(10);
		textField_Student_RegisterStatus.setBounds(315, 228, 190, 40);
		contentPane.add(textField_Student_RegisterStatus);

		textField_Student_PayStatus = new JTextField(""); // 납부여부

		textField_Student_PayStatus.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Student_PayStatus.setEditable(false);
		textField_Student_PayStatus.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Student_PayStatus.setColumns(10);
		textField_Student_PayStatus.setBounds(785, 228, 190, 40);
		contentPane.add(textField_Student_PayStatus);

		textField_StudentRoomStatus = new JTextField(""); // 식비구분x 호실유형o

		textField_StudentRoomStatus.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentRoomStatus.setEditable(false);
		textField_StudentRoomStatus.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentRoomStatus.setColumns(10);
		textField_StudentRoomStatus.setBounds(315, 267, 190, 40);
		contentPane.add(textField_StudentRoomStatus);

		textField_StudentDormitory = new JTextField(""); // 생활관

		textField_StudentDormitory.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentDormitory.setEditable(false);
		textField_StudentDormitory.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentDormitory.setColumns(10);
		textField_StudentDormitory.setBounds(785, 267, 190, 40);
		contentPane.add(textField_StudentDormitory);

		textField_StudentRoomCode = new JTextField(""); // 호실 / 침대번호
		textField_StudentRoomCode.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentRoomCode.setEditable(false);
		textField_StudentRoomCode.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentRoomCode.setColumns(10);
		textField_StudentRoomCode.setBounds(785, 307, 190, 40);
		contentPane.add(textField_StudentRoomCode);

		textField_RegisterStatus = new JTextField();
		textField_RegisterStatus.setEditable(false);
		textField_RegisterStatus.setHorizontalAlignment(SwingConstants.CENTER);
		textField_RegisterStatus.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_RegisterStatus.setText("등록여부");
		textField_RegisterStatus.setBackground(Color.LIGHT_GRAY);
		textField_RegisterStatus.setBounds(35, 228, 280, 40);
		contentPane.add(textField_RegisterStatus);
		textField_RegisterStatus.setColumns(10);

		textField_RoomStatus = new JTextField();
		textField_RoomStatus.setEditable(false);
		textField_RoomStatus.setText("호실유형");
		textField_RoomStatus.setHorizontalAlignment(SwingConstants.CENTER);
		textField_RoomStatus.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_RoomStatus.setColumns(10);
		textField_RoomStatus.setBackground(Color.LIGHT_GRAY);
		textField_RoomStatus.setBounds(35, 267, 280, 40);
		contentPane.add(textField_RoomStatus);

		// GUI 버튼
		Button_Check = new JButton("조회");
		Button_Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_StudentId.setText(student.getStudentId());
				textField_StudentDepartment.setText(student.getDepartmentName());
				textField_StudentName.setText(student.getName());
				textField_StudentGrade.setText(Integer.toString(student.getGrade()));
				textField_StudentAddress.setText(student.getStudentAddress());
				textField_Student_RegisterStatus.setText(selectedStudent.getRegister_status());
				textField_Student_PayStatus.setText(selectedStudent.getPay_status());
				textField_StudentDormitory.setText(convertDormitoryCodeToName(selectedStudent.getDormitoryCode()));
				textField_StudentRoomStatus.setText("일반실");
				textField_StudentRoomCode
						.setText(selectedStudent.getRoom_code() + " / " + selectedStudent.getBed_code());
			}
		});
		Button_Check.setBounds(865, 32, 110, 30);
		contentPane.add(Button_Check);
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