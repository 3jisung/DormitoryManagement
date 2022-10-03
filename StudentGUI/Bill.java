/** 
 * @copyright 융합프로젝트 8조
 */

package StudentGUI;

import GUI.*;
import tableClass.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Network.*;
import tableClass.*;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;

/**
 * @file Bill.java
 * 
 * @author 김동윤, 김선진
 * 
 * @day 2019-12-11
 * 
 * @brief 이 클래스는 DetailedStatement_Bill 클래스에서 생성되며 호출한 학생의 생활관 고지서를 출력하는 클래스이다.
 */

public class Bill extends JFrame {

	/** GUI 출력을 위한 패널 및 텍스트 필드 선언 */
	private JPanel contentPane;
	private JTextField textField_title_Bill;
	private JTextField textField_StudentName;
	private JTextField textField_StudentId;
	private JTextField textField_MngCost;
	private JTextField textField_FoodCost;
	private JTextField textField_TotalCost;

	/** GUI 상 텍스트 필드 값의 제목을 나타내기 위한 라벨 */
	private JLabel label_StudentName;
	private JLabel label_StudentId;
	private JLabel label_MngCost;
	private JLabel label_FoodCost;
	private JLabel label_TotalCost;

	/** 소켓 통신을 위한 기본 변수 선언 */
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private Socket socket;
	private Student student;
	private SelectedStudent selectedStudent;

	public Bill(Student s, SelectedStudent ss, ObjectOutputStream oos, ObjectInputStream ois, Socket sk) {

		// 소켓 통신을 위한 변수 초기화
		socket = sk;
		student = s;
		selectedStudent = ss;
		writer = oos;
		reader = ois;

		// GUI 라벨 출력
		this.setResizable(false); // 최대화 단추 없애기
		setTitle("고지서 출력");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 510, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label_StudentName = new JLabel("성명"); // 이름
		label_StudentName.setForeground(Color.BLACK);
		label_StudentName.setHorizontalAlignment(SwingConstants.CENTER);
		label_StudentName.setBounds(50, 85, 95, 40);
		contentPane.add(label_StudentName);

		label_StudentId = new JLabel("학번"); // 학번
		label_StudentId.setHorizontalAlignment(SwingConstants.CENTER);
		label_StudentId.setForeground(Color.BLACK);
		label_StudentId.setBounds(50, 130, 95, 40);
		contentPane.add(label_StudentId);

		label_MngCost = new JLabel("생활관 관리비"); // 생활관 관리비
		label_MngCost.setHorizontalAlignment(SwingConstants.CENTER);
		label_MngCost.setForeground(Color.BLACK);
		label_MngCost.setBounds(50, 175, 95, 40);
		contentPane.add(label_MngCost);

		label_FoodCost = new JLabel("식비"); // 식비
		label_FoodCost.setHorizontalAlignment(SwingConstants.CENTER);
		label_FoodCost.setForeground(Color.BLACK);
		label_FoodCost.setBounds(50, 220, 95, 40);
		contentPane.add(label_FoodCost);

		label_TotalCost = new JLabel("총액"); // 총액
		label_TotalCost.setHorizontalAlignment(SwingConstants.CENTER);
		label_TotalCost.setForeground(Color.BLACK);
		label_TotalCost.setBounds(50, 265, 95, 40);
		contentPane.add(label_TotalCost);

		// GUI 상 텍스트 필드 출력
		textField_title_Bill = new JTextField(); // 타이틀
		textField_title_Bill.setForeground(Color.RED);
		textField_title_Bill.setText("고지서 출력");
		textField_title_Bill.setEditable(false);
		textField_title_Bill.setHorizontalAlignment(SwingConstants.CENTER);
		textField_title_Bill.setBounds(50, 20, 317, 50);
		contentPane.add(textField_title_Bill);
		textField_title_Bill.setColumns(10);

		textField_StudentName = new JTextField(); // 성명
		textField_StudentName.setText(student.getName());
		textField_StudentName.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentName.setBackground(new Color(255, 228, 225));
		textField_StudentName.setForeground(Color.BLACK);
		textField_StudentName.setEditable(false);
		textField_StudentName.setColumns(10);
		textField_StudentName.setBounds(147, 85, 220, 40);
		contentPane.add(textField_StudentName);

		textField_StudentId = new JTextField(); // 학번
		textField_StudentId.setText(student.getStudentId());
		textField_StudentId.setHorizontalAlignment(SwingConstants.CENTER);
		textField_StudentId.setBackground(new Color(255, 228, 225));
		textField_StudentId.setForeground(Color.BLACK);
		textField_StudentId.setEditable(false);
		textField_StudentId.setColumns(10);
		textField_StudentId.setBounds(147, 130, 220, 40);
		contentPane.add(textField_StudentId);

		textField_MngCost = new JTextField(Integer.toString(ss.getMng_cost())); // 생활관 관리비
		textField_MngCost.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MngCost.setBackground(new Color(255, 228, 225));
		textField_MngCost.setForeground(Color.BLACK);
		textField_MngCost.setEditable(false);
		textField_MngCost.setColumns(10);
		textField_MngCost.setBounds(147, 175, 220, 40);
		contentPane.add(textField_MngCost);

		textField_FoodCost = new JTextField(Integer.toString(ss.getFood_cost())); // 식비
		textField_FoodCost.setHorizontalAlignment(SwingConstants.CENTER);
		textField_FoodCost.setBackground(new Color(255, 228, 225));
		textField_FoodCost.setForeground(Color.BLACK);
		textField_FoodCost.setEditable(false);
		textField_FoodCost.setColumns(10);
		textField_FoodCost.setBounds(147, 220, 220, 40);
		contentPane.add(textField_FoodCost);

		textField_TotalCost = new JTextField(Integer.toString(selectedStudent.getTotal_cost())); // 입금액(총액)
		textField_TotalCost.setHorizontalAlignment(SwingConstants.CENTER);
		textField_TotalCost.setBackground(new Color(255, 228, 225));
		textField_TotalCost.setForeground(Color.BLACK);
		textField_TotalCost.setEditable(false);
		textField_TotalCost.setColumns(10);
		textField_TotalCost.setBounds(147, 266, 220, 40);
		contentPane.add(textField_TotalCost);
	}
}