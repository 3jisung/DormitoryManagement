/**
 * @Copyright 융합프로젝트 8조
 */

package StudentGUI;

import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;

import Network.Protocol;
import tableClass.*;

/**
 * @file Dormitory_Application.java
 * 
 * @author 김동윤, 김선진
 * 
 * @day 2019-12-11
 * 
 * @brief 이 클래스는 Menu_Student 클래스에서 생성되며 사용자로 부터 값을 입력받아 소켓 통신을 통해 입사신청에 관련된 객체를
 *        송신하여 서버 클래스에 전달한다.
 */

public class Dormitory_Application extends JFrame {

	/** GUI 출력을 위한 패널 및 버튼 선언 */
	private JPanel contentPane;
	private JTextField textField_StudentId;
	private JTextField textField_StudentDepartment;
	private JTextField textField_StudentName;
	private JTextField textField_StudentGrade;
	private JTextField textField_title_DormitoryApplication;
	private JTextField textField_title_DormitoryWish1;
	private JTextField textField_title_OneSemester;
	private JTextField textField_Id;
	private JTextField textField_Department;
	private JTextField textField_Name;
	private JTextField textField_Grade;
	private JTextField textField_DormitoryWishYear;
	private JTextField textField_MealDivisionYear;
	private JTextField textField_MealDivision1;
	private JTextField textField_MealDivision2;
	private JTextField textField_MealDivision3;
	private JTextField textField_DormitoryWish1;
	private JTextField textField_DormitoryWish2;
	private JTextField textField_DormitoryWish3;

	/** GUI 출력 및 값 입력 받기 위한 콤보박스 및 버튼 선언 */
	private JComboBox comboBox_DormitoryWishYear;
	private JComboBox comboBox_MealDivisionYear;
	private JComboBox comboBox_DormitoryWish1;
	private JComboBox comboBox_MealDivision1;
	private JComboBox comboBox_DormitoryWish2;
	private JComboBox comboBox_MealDivision2;
	private JComboBox comboBox_DormitoryWish3;
	private JComboBox comboBox_MealDivision3;
	private JButton Button_Application;

	/** GUI를 통해 사용자에게 입사 신청 정보를 받기 위한 변수 선언 */
	private String mealDivision1;
	private String mealDivision2;
	private String mealDivision3;
	private String mealDivisionYear;
	private String dormitoryWish1;
	private String dormitoryWish2;
	private String dormitoryWish3;
	private String dormitoryWishYear;

	/** 소켓 통신을 위한 기본 변수 선언 */
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private Student student;
	private Socket socket;

	public Dormitory_Application(Student s, ObjectOutputStream oos, ObjectInputStream ois, Socket sk) {

		// 소켓 통신을 위한 기본 변수 초기화
		socket = sk;
		student = s;
		writer = oos;
		reader = ois;

		// GUI 텍스트 필드 출력
		this.setResizable(false); // 최대화 단추 없애기
		setVisible(true);
		setTitle("입사신청");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 150, 1015, 610);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_title_DormitoryApplication = new JTextField();
		textField_title_DormitoryApplication.setFont(new Font("굴림", Font.PLAIN, 17));
		textField_title_DormitoryApplication.setEditable(false);
		textField_title_DormitoryApplication.setForeground(Color.RED);
		textField_title_DormitoryApplication.setText("생활관 입사신청");
		textField_title_DormitoryApplication.setBounds(35, 10, 169, 40);
		contentPane.add(textField_title_DormitoryApplication);
		textField_title_DormitoryApplication.setColumns(10);

		textField_Id = new JTextField();
		textField_Id.setText("학번");
		textField_Id.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Id.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Id.setEditable(false);
		textField_Id.setColumns(10);
		textField_Id.setBackground(Color.LIGHT_GRAY);
		textField_Id.setBounds(35, 53, 125, 40);
		contentPane.add(textField_Id);

		textField_Department = new JTextField();
		textField_Department.setText("학과");
		textField_Department.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Department.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Department.setEditable(false);
		textField_Department.setColumns(10);
		textField_Department.setBackground(Color.LIGHT_GRAY);
		textField_Department.setBounds(372, 53, 125, 40);
		contentPane.add(textField_Department);

		textField_Name = new JTextField();
		textField_Name.setText("성명");
		textField_Name.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Name.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Name.setEditable(false);
		textField_Name.setColumns(10);
		textField_Name.setBackground(Color.LIGHT_GRAY);
		textField_Name.setBounds(372, 99, 125, 40);
		contentPane.add(textField_Name);

		textField_Grade = new JTextField();
		textField_Grade.setText("학년");
		textField_Grade.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Grade.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_Grade.setEditable(false);
		textField_Grade.setColumns(10);
		textField_Grade.setBackground(Color.LIGHT_GRAY);
		textField_Grade.setBounds(35, 99, 125, 40);
		contentPane.add(textField_Grade);

		textField_StudentId = new JTextField(); // 학번
		textField_StudentId.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentId.setText(student.getStudentId());
		textField_StudentId.setEditable(false);
		textField_StudentId.setBounds(159, 53, 214, 40);
		contentPane.add(textField_StudentId);
		textField_StudentId.setColumns(10);

		textField_StudentDepartment = new JTextField(); // 학과
		textField_StudentDepartment.setText(student.getDepartmentName());
		textField_StudentDepartment.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentDepartment.setEditable(false);
		textField_StudentDepartment.setColumns(10);
		textField_StudentDepartment.setBounds(496, 53, 214, 40);
		contentPane.add(textField_StudentDepartment);

		textField_StudentName = new JTextField(); // 성명
		textField_StudentName.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentName.setText(student.getName());
		textField_StudentName.setEditable(false);
		textField_StudentName.setColumns(10);
		textField_StudentName.setBounds(496, 99, 214, 40);
		contentPane.add(textField_StudentName);

		textField_StudentGrade = new JTextField(); // 학년
		textField_StudentGrade.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_StudentGrade.setText(Integer.toString(student.getGrade()));
		textField_StudentGrade.setEditable(false);
		textField_StudentGrade.setColumns(10);
		textField_StudentGrade.setBounds(159, 99, 214, 40);
		contentPane.add(textField_StudentGrade);

		textField_title_DormitoryWish1 = new JTextField();
		textField_title_DormitoryWish1.setFont(new Font("굴림", Font.PLAIN, 17));
		textField_title_DormitoryWish1.setText("1년 기숙 모집");
		textField_title_DormitoryWish1.setForeground(new Color(255, 0, 0));
		textField_title_DormitoryWish1.setEditable(false);
		textField_title_DormitoryWish1.setColumns(10);
		textField_title_DormitoryWish1.setBounds(35, 176, 169, 40);
		contentPane.add(textField_title_DormitoryWish1);

		textField_DormitoryWishYear = new JTextField();
		textField_DormitoryWishYear.setText("구분");
		textField_DormitoryWishYear.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWishYear.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_DormitoryWishYear.setEditable(false);
		textField_DormitoryWishYear.setColumns(10);
		textField_DormitoryWishYear.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWishYear.setBounds(35, 219, 125, 40);
		contentPane.add(textField_DormitoryWishYear);

		textField_MealDivisionYear = new JTextField();
		textField_MealDivisionYear.setText("식사구분 (1년)");
		textField_MealDivisionYear.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivisionYear.setFont(new Font("굴림", Font.PLAIN, 13));
		textField_MealDivisionYear.setEditable(false);
		textField_MealDivisionYear.setColumns(10);
		textField_MealDivisionYear.setBackground(Color.LIGHT_GRAY);
		textField_MealDivisionYear.setBounds(284, 219, 125, 40);
		contentPane.add(textField_MealDivisionYear);

		textField_title_OneSemester = new JTextField();
		textField_title_OneSemester.setText("한학기 기숙 모집");
		textField_title_OneSemester.setForeground(Color.RED);
		textField_title_OneSemester.setFont(new Font("굴림", Font.PLAIN, 17));
		textField_title_OneSemester.setEditable(false);
		textField_title_OneSemester.setColumns(10);
		textField_title_OneSemester.setBounds(35, 295, 169, 40);
		contentPane.add(textField_title_OneSemester);

		textField_MealDivision1 = new JTextField();
		textField_MealDivision1.setText("식사구분");
		textField_MealDivision1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivision1.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_MealDivision1.setEditable(false);
		textField_MealDivision1.setColumns(10);
		textField_MealDivision1.setBackground(Color.LIGHT_GRAY);
		textField_MealDivision1.setBounds(422, 339, 125, 40);
		contentPane.add(textField_MealDivision1);

		textField_MealDivision2 = new JTextField();
		textField_MealDivision2.setText("식사구분");
		textField_MealDivision2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivision2.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_MealDivision2.setEditable(false);
		textField_MealDivision2.setColumns(10);
		textField_MealDivision2.setBackground(Color.LIGHT_GRAY);
		textField_MealDivision2.setBounds(422, 386, 125, 40);
		contentPane.add(textField_MealDivision2);

		textField_MealDivision3 = new JTextField();
		textField_MealDivision3.setText("식사구분");
		textField_MealDivision3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivision3.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_MealDivision3.setEditable(false);
		textField_MealDivision3.setColumns(10);
		textField_MealDivision3.setBackground(Color.LIGHT_GRAY);
		textField_MealDivision3.setBounds(422, 432, 125, 40);
		contentPane.add(textField_MealDivision3);

		textField_DormitoryWish1 = new JTextField();
		textField_DormitoryWish1.setText("1지망");
		textField_DormitoryWish1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWish1.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_DormitoryWish1.setEditable(false);
		textField_DormitoryWish1.setColumns(10);
		textField_DormitoryWish1.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWish1.setBounds(35, 339, 125, 40);
		contentPane.add(textField_DormitoryWish1);

		textField_DormitoryWish2 = new JTextField();
		textField_DormitoryWish2.setText("2지망");
		textField_DormitoryWish2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWish2.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_DormitoryWish2.setEditable(false);
		textField_DormitoryWish2.setColumns(10);
		textField_DormitoryWish2.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWish2.setBounds(35, 386, 125, 40);
		contentPane.add(textField_DormitoryWish2);

		textField_DormitoryWish3 = new JTextField();
		textField_DormitoryWish3.setText("3지망");
		textField_DormitoryWish3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWish3.setFont(new Font("굴림", Font.PLAIN, 16));
		textField_DormitoryWish3.setEditable(false);
		textField_DormitoryWish3.setColumns(10);
		textField_DormitoryWish3.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWish3.setBounds(35, 432, 125, 40);
		contentPane.add(textField_DormitoryWish3);

		// GUI 콤보박스 출력

		comboBox_DormitoryWish1 = new JComboBox();
		comboBox_DormitoryWish1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "선택안함";
				dormitoryWish1 = comboBox_DormitoryWish1.getSelectedItem().toString();
				if (dormitoryWish1 == "선택안함") {
					comboBox_MealDivision1.setEnabled(false);
					comboBox_MealDivision1.setSelectedItem(str);
					comboBox_DormitoryWish2.setEnabled(false);
					comboBox_DormitoryWish2.setSelectedItem(str);
					comboBox_MealDivision2.setEnabled(false);
					comboBox_MealDivision2.setSelectedItem(str);
					comboBox_DormitoryWish3.setEnabled(false);
					comboBox_DormitoryWish3.setSelectedItem(str);
					comboBox_MealDivision3.setEnabled(false);
					comboBox_MealDivision3.setSelectedItem(str);
				} else if (dormitoryWish1 == comboBox_DormitoryWish2.getSelectedItem().toString()
						|| dormitoryWish1 == comboBox_DormitoryWish3.getSelectedItem().toString()) {
					JOptionPane.showMessageDialog(null, "중복입니다");
					comboBox_DormitoryWish1.setSelectedItem(str);
				} else if (dormitoryWish1 == "오름관1동" || dormitoryWish1 == "오름관2동" || dormitoryWish1 == "오름관3동") {
					comboBox_MealDivision1.removeItem("식사안함");
					comboBox_MealDivision1.setEnabled(true);
				} else {
					if (comboBox_MealDivision1.getItemCount() < 4) {
						comboBox_MealDivision1.addItem("식사안함");
					}
					comboBox_MealDivision1.setEnabled(true);
				}
			}
		});
		
		comboBox_DormitoryWishYear = new JComboBox();
		comboBox_DormitoryWishYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "선택안함";
				dormitoryWishYear = comboBox_DormitoryWishYear.getSelectedItem().toString();

				if (str == dormitoryWishYear) {
					comboBox_MealDivisionYear.setEnabled(false);
					comboBox_MealDivisionYear.setSelectedItem(str);
				} else {
					comboBox_MealDivisionYear.setEnabled(true);
				}
			}
		});
		comboBox_DormitoryWishYear.setModel(new DefaultComboBoxModel(new String[] { "선택안함", "푸름관2동", "푸름관3동" }));
		comboBox_DormitoryWishYear.setBounds(159, 219, 125, 40);
		contentPane.add(comboBox_DormitoryWishYear);

		
		comboBox_MealDivisionYear = new JComboBox();
		comboBox_MealDivisionYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mealDivisionYear = comboBox_MealDivisionYear.getSelectedItem().toString();
			}
		});
		comboBox_MealDivisionYear.setEnabled(false);
		comboBox_MealDivisionYear.setModel(new DefaultComboBoxModel(new String[] { "\uC120\uD0DD\uC548\uD568",
				"5\uC77C\uC2DD", "7\uC77C\uC2DD", "\uC2DD\uC0AC\uC548\uD568" }));
		comboBox_MealDivisionYear.setBounds(409, 219, 88, 40);
		contentPane.add(comboBox_MealDivisionYear);
		comboBox_DormitoryWish1.setModel(new DefaultComboBoxModel(new String[] { "선택안함", "푸름관1동", "푸름관2동", "푸름관3동",
				"푸름관4동", "오름관1동", "오름관2동", "오름관3동", "신평관남자", "신평관여자" }));
		comboBox_DormitoryWish1.setBounds(159, 339, 263, 40);
		contentPane.add(comboBox_DormitoryWish1);


		comboBox_MealDivision1 = new JComboBox();
		comboBox_MealDivision1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "선택안함";
				mealDivision1 = comboBox_MealDivision1.getSelectedItem().toString();
				if (str == mealDivision1) {
					comboBox_DormitoryWish2.setEnabled(false);
				} else {
					comboBox_DormitoryWish2.setEnabled(true);
				}
			}
		});
		comboBox_MealDivision1.setEnabled(false);
		comboBox_MealDivision1.setModel(new DefaultComboBoxModel(new String[] { "\uC120\uD0DD\uC548\uD568",
				"5\uC77C\uC2DD", "7\uC77C\uC2DD", "\uC2DD\uC0AC\uC548\uD568" }));
		comboBox_MealDivision1.setBounds(547, 339, 163, 40);
		contentPane.add(comboBox_MealDivision1);


		comboBox_DormitoryWish2 = new JComboBox();
		comboBox_DormitoryWish2.setEnabled(false);
		comboBox_DormitoryWish2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "선택안함";
				dormitoryWish2 = comboBox_DormitoryWish2.getSelectedItem().toString();

				if (str.equals(dormitoryWish2)) {
					comboBox_MealDivision2.setEnabled(false);
					comboBox_MealDivision2.setSelectedItem(str);
					comboBox_DormitoryWish3.setEnabled(false);
					comboBox_DormitoryWish3.setSelectedItem(str);
					comboBox_MealDivision3.setEnabled(false);
					comboBox_MealDivision3.setSelectedItem(str);
				} else if (dormitoryWish2.equals(comboBox_DormitoryWish1.getSelectedItem().toString())
						|| dormitoryWish2.equals(comboBox_DormitoryWish3.getSelectedItem().toString())) {
					JOptionPane.showMessageDialog(null, "중복입니다");
					comboBox_DormitoryWish2.setSelectedItem(str);
				} else if (dormitoryWish2 == "오름관1동" || dormitoryWish2 == "오름관2동" || dormitoryWish2 == "오름관3동") {
					comboBox_MealDivision2.removeItem("식사안함");
					comboBox_MealDivision2.setEnabled(true);
				} else {
					if (comboBox_MealDivision2.getItemCount() < 4) {
						comboBox_MealDivision2.addItem("식사안함");
					}
					comboBox_MealDivision2.setEnabled(true);
				}
			}
		});
		comboBox_DormitoryWish2.setModel(new DefaultComboBoxModel(new String[] { "선택안함", "푸름관1동", "푸름관2동", "푸름관3동",
				"푸름관4동", "오름관1동", "오름관2동", "오름관3동", "신평관남자", "신평관여자" }));
		comboBox_DormitoryWish2.setBounds(159, 386, 263, 40);
		contentPane.add(comboBox_DormitoryWish2);

		comboBox_MealDivision2 = new JComboBox();
		comboBox_MealDivision2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "선택안함";
				mealDivision2 = comboBox_MealDivision2.getSelectedItem().toString();

				if (str == mealDivision2) {
					comboBox_DormitoryWish3.setEnabled(false);
					comboBox_MealDivision3.setEnabled(false);
				} else {
					comboBox_DormitoryWish3.setEnabled(true);
				}
			}
		});
		comboBox_MealDivision2.setEnabled(false);
		comboBox_MealDivision2.setModel(new DefaultComboBoxModel(new String[] { "\uC120\uD0DD\uC548\uD568",
				"5\uC77C\uC2DD", "7\uC77C\uC2DD", "\uC2DD\uC0AC\uC548\uD568" }));
		comboBox_MealDivision2.setBounds(547, 386, 163, 40);
		contentPane.add(comboBox_MealDivision2);

		comboBox_DormitoryWish3 = new JComboBox();
		comboBox_DormitoryWish3.setEnabled(false);
		comboBox_DormitoryWish3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "선택안함";
				dormitoryWish3 = comboBox_DormitoryWish3.getSelectedItem().toString();

				if (str == dormitoryWish3) {
					comboBox_MealDivision3.setEnabled(false);
					comboBox_MealDivision3.setSelectedItem(str);
				} else if (dormitoryWish3 == comboBox_DormitoryWish1.getSelectedItem().toString()
						|| dormitoryWish3 == comboBox_DormitoryWish2.getSelectedItem().toString()) {
					JOptionPane.showMessageDialog(null, "중복입니다");
					comboBox_DormitoryWish3.setSelectedItem(str);
				} else if (dormitoryWish3 == "오름관1동" || dormitoryWish3 == "오름관2동" || dormitoryWish3 == "오름관3동") {
					comboBox_MealDivision3.removeItem("식사안함");
					comboBox_MealDivision3.setEnabled(true);
				} else {
					if (comboBox_MealDivision3.getItemCount() < 4) {
						comboBox_MealDivision3.addItem("식사안함");
					}
					comboBox_MealDivision3.setEnabled(true);
				}
			}
		});

		comboBox_MealDivision3 = new JComboBox();
		comboBox_DormitoryWish3.setModel(new DefaultComboBoxModel(new String[] { "선택안함", "푸름관1동", "푸름관2동", "푸름관3동",
				"푸름관4동", "오름관1동", "오름관2동", "오름관3동", "신평관남자", "신평관여자" }));
		comboBox_DormitoryWish3.setBounds(159, 432, 263, 40);
		contentPane.add(comboBox_DormitoryWish3);

		comboBox_MealDivision3.setEnabled(false);
		comboBox_MealDivision3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				mealDivision3 = comboBox_MealDivision3.getSelectedItem().toString();
			}
		});
		comboBox_MealDivision3.setModel(new DefaultComboBoxModel(new String[] { "\uC120\uD0DD\uC548\uD568",
				"5\uC77C\uC2DD", "7\uC77C\uC2DD", "\uC2DD\uC0AC\uC548\uD568" }));
		comboBox_MealDivision3.setBounds(547, 432, 163, 40);
		contentPane.add(comboBox_MealDivision3);

		// GUI 버튼 출력
		Button_Application = new JButton("신청");
		Button_Application.addActionListener(new ActionListener() { // 신청 버튼 클릭 시, 사용자가 입력한 입사 신청 정보를 소켓 통신을 통해 서버로 전송한다.
			public void actionPerformed(ActionEvent e) {
				dormitoryApplication app = new dormitoryApplication();

				app.setStudentId(student.getStudentId());

				if (!comboBox_DormitoryWishYear.getSelectedItem().equals("선택안함")) {
					app.setDormitoryWishYear(
							convertDormitoryNameToCode(comboBox_DormitoryWishYear.getSelectedItem().toString()));
					app.setMealDivisionYear(comboBox_MealDivisionYear.getSelectedItem().toString());
				}

				if (!comboBox_DormitoryWish1.getSelectedItem().equals("선택안함")) {
					app.setDormitoryWish1(
							convertDormitoryNameToCode(comboBox_DormitoryWish1.getSelectedItem().toString()));
					app.setMealDivision1(comboBox_MealDivision1.getSelectedItem().toString());
				}

				if (!comboBox_DormitoryWish2.getSelectedItem().equals("선택안함")) {
					app.setDormitoryWish2(
							convertDormitoryNameToCode(comboBox_DormitoryWish2.getSelectedItem().toString()));
					app.setMealDivision2(comboBox_MealDivision2.getSelectedItem().toString());
				}
				if (!comboBox_DormitoryWish3.getSelectedItem().equals("선택안함")) {
					app.setDormitoryWish3(
							convertDormitoryNameToCode(comboBox_DormitoryWish3.getSelectedItem().toString()));
					app.setMealDivision3(comboBox_MealDivision3.getSelectedItem().toString());
				}

				if (!comboBox_DormitoryWishYear.getSelectedItem().equals("선택안함")
						|| !comboBox_DormitoryWish1.getSelectedItem().equals("선택안함")) {
					try {
						writer.writeObject(new Protocol(11, 3, 0, app));
						writer.flush();
						try {
							p = (Protocol) reader.readObject();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						if (p.getSubType() == 4) {
							if (p.getCode() == 1) {
								JOptionPane.showMessageDialog(null, "신청이 성공적으로 완료되었습니다!!");
								dispose();
							} else if (p.getCode() == 2)
								JOptionPane.showMessageDialog(null, (String) p.getBody());
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "최소 1지망은 선택해야 합니다.");
				}
			}
		});

		Button_Application.setBounds(620, 149, 90, 30);
		contentPane.add(Button_Application);
		setSize(750, 550);
	}

	/**
	 * 생활관 이름을 코드로 변환 하는 메소드
	 * @param String s 는 생활관 이름
	 * @return code 는 생활관 코드
	 **/
	public String convertDormitoryNameToCode(String s) {
		String code = null;

		if (s.equals("푸름관1동"))
			code = new String("1");
		else if (s.equals("푸름관2동"))
			code = new String("2");
		else if (s.equals("푸름관3동"))
			code = new String("3");
		else if (s.equals("푸름관4동"))
			code = new String("4");
		else if (s.equals("오름관1동"))
			code = new String("5");
		else if (s.equals("오름관2동"))
			code = new String("6");
		else if (s.equals("오름관3동"))
			code = new String("7");

		else if (s.equals("신평관남자"))
			code = new String("8");
		else if (s.equals("신평관여자"))
			code = new String("9");
		return code;
	}
}