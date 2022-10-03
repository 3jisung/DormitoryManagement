/**
 * @Copyright ����������Ʈ 8��
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
 * @author �赿��, �輱��
 * 
 * @day 2019-12-11
 * 
 * @brief �� Ŭ������ Menu_Student Ŭ�������� �����Ǹ� ����ڷ� ���� ���� �Է¹޾� ���� ����� ���� �Ի��û�� ���õ� ��ü��
 *        �۽��Ͽ� ���� Ŭ������ �����Ѵ�.
 */

public class Dormitory_Application extends JFrame {

	/** GUI ����� ���� �г� �� ��ư ���� */
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

	/** GUI ��� �� �� �Է� �ޱ� ���� �޺��ڽ� �� ��ư ���� */
	private JComboBox comboBox_DormitoryWishYear;
	private JComboBox comboBox_MealDivisionYear;
	private JComboBox comboBox_DormitoryWish1;
	private JComboBox comboBox_MealDivision1;
	private JComboBox comboBox_DormitoryWish2;
	private JComboBox comboBox_MealDivision2;
	private JComboBox comboBox_DormitoryWish3;
	private JComboBox comboBox_MealDivision3;
	private JButton Button_Application;

	/** GUI�� ���� ����ڿ��� �Ի� ��û ������ �ޱ� ���� ���� ���� */
	private String mealDivision1;
	private String mealDivision2;
	private String mealDivision3;
	private String mealDivisionYear;
	private String dormitoryWish1;
	private String dormitoryWish2;
	private String dormitoryWish3;
	private String dormitoryWishYear;

	/** ���� ����� ���� �⺻ ���� ���� */
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private Student student;
	private Socket socket;

	public Dormitory_Application(Student s, ObjectOutputStream oos, ObjectInputStream ois, Socket sk) {

		// ���� ����� ���� �⺻ ���� �ʱ�ȭ
		socket = sk;
		student = s;
		writer = oos;
		reader = ois;

		// GUI �ؽ�Ʈ �ʵ� ���
		this.setResizable(false); // �ִ�ȭ ���� ���ֱ�
		setVisible(true);
		setTitle("�Ի��û");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 150, 1015, 610);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField_title_DormitoryApplication = new JTextField();
		textField_title_DormitoryApplication.setFont(new Font("����", Font.PLAIN, 17));
		textField_title_DormitoryApplication.setEditable(false);
		textField_title_DormitoryApplication.setForeground(Color.RED);
		textField_title_DormitoryApplication.setText("��Ȱ�� �Ի��û");
		textField_title_DormitoryApplication.setBounds(35, 10, 169, 40);
		contentPane.add(textField_title_DormitoryApplication);
		textField_title_DormitoryApplication.setColumns(10);

		textField_Id = new JTextField();
		textField_Id.setText("�й�");
		textField_Id.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Id.setFont(new Font("����", Font.PLAIN, 16));
		textField_Id.setEditable(false);
		textField_Id.setColumns(10);
		textField_Id.setBackground(Color.LIGHT_GRAY);
		textField_Id.setBounds(35, 53, 125, 40);
		contentPane.add(textField_Id);

		textField_Department = new JTextField();
		textField_Department.setText("�а�");
		textField_Department.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Department.setFont(new Font("����", Font.PLAIN, 16));
		textField_Department.setEditable(false);
		textField_Department.setColumns(10);
		textField_Department.setBackground(Color.LIGHT_GRAY);
		textField_Department.setBounds(372, 53, 125, 40);
		contentPane.add(textField_Department);

		textField_Name = new JTextField();
		textField_Name.setText("����");
		textField_Name.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Name.setFont(new Font("����", Font.PLAIN, 16));
		textField_Name.setEditable(false);
		textField_Name.setColumns(10);
		textField_Name.setBackground(Color.LIGHT_GRAY);
		textField_Name.setBounds(372, 99, 125, 40);
		contentPane.add(textField_Name);

		textField_Grade = new JTextField();
		textField_Grade.setText("�г�");
		textField_Grade.setHorizontalAlignment(SwingConstants.CENTER);
		textField_Grade.setFont(new Font("����", Font.PLAIN, 16));
		textField_Grade.setEditable(false);
		textField_Grade.setColumns(10);
		textField_Grade.setBackground(Color.LIGHT_GRAY);
		textField_Grade.setBounds(35, 99, 125, 40);
		contentPane.add(textField_Grade);

		textField_StudentId = new JTextField(); // �й�
		textField_StudentId.setFont(new Font("����", Font.PLAIN, 16));
		textField_StudentId.setText(student.getStudentId());
		textField_StudentId.setEditable(false);
		textField_StudentId.setBounds(159, 53, 214, 40);
		contentPane.add(textField_StudentId);
		textField_StudentId.setColumns(10);

		textField_StudentDepartment = new JTextField(); // �а�
		textField_StudentDepartment.setText(student.getDepartmentName());
		textField_StudentDepartment.setFont(new Font("����", Font.PLAIN, 16));
		textField_StudentDepartment.setEditable(false);
		textField_StudentDepartment.setColumns(10);
		textField_StudentDepartment.setBounds(496, 53, 214, 40);
		contentPane.add(textField_StudentDepartment);

		textField_StudentName = new JTextField(); // ����
		textField_StudentName.setFont(new Font("����", Font.PLAIN, 16));
		textField_StudentName.setText(student.getName());
		textField_StudentName.setEditable(false);
		textField_StudentName.setColumns(10);
		textField_StudentName.setBounds(496, 99, 214, 40);
		contentPane.add(textField_StudentName);

		textField_StudentGrade = new JTextField(); // �г�
		textField_StudentGrade.setFont(new Font("����", Font.PLAIN, 16));
		textField_StudentGrade.setText(Integer.toString(student.getGrade()));
		textField_StudentGrade.setEditable(false);
		textField_StudentGrade.setColumns(10);
		textField_StudentGrade.setBounds(159, 99, 214, 40);
		contentPane.add(textField_StudentGrade);

		textField_title_DormitoryWish1 = new JTextField();
		textField_title_DormitoryWish1.setFont(new Font("����", Font.PLAIN, 17));
		textField_title_DormitoryWish1.setText("1�� ��� ����");
		textField_title_DormitoryWish1.setForeground(new Color(255, 0, 0));
		textField_title_DormitoryWish1.setEditable(false);
		textField_title_DormitoryWish1.setColumns(10);
		textField_title_DormitoryWish1.setBounds(35, 176, 169, 40);
		contentPane.add(textField_title_DormitoryWish1);

		textField_DormitoryWishYear = new JTextField();
		textField_DormitoryWishYear.setText("����");
		textField_DormitoryWishYear.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWishYear.setFont(new Font("����", Font.PLAIN, 16));
		textField_DormitoryWishYear.setEditable(false);
		textField_DormitoryWishYear.setColumns(10);
		textField_DormitoryWishYear.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWishYear.setBounds(35, 219, 125, 40);
		contentPane.add(textField_DormitoryWishYear);

		textField_MealDivisionYear = new JTextField();
		textField_MealDivisionYear.setText("�Ļ籸�� (1��)");
		textField_MealDivisionYear.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivisionYear.setFont(new Font("����", Font.PLAIN, 13));
		textField_MealDivisionYear.setEditable(false);
		textField_MealDivisionYear.setColumns(10);
		textField_MealDivisionYear.setBackground(Color.LIGHT_GRAY);
		textField_MealDivisionYear.setBounds(284, 219, 125, 40);
		contentPane.add(textField_MealDivisionYear);

		textField_title_OneSemester = new JTextField();
		textField_title_OneSemester.setText("���б� ��� ����");
		textField_title_OneSemester.setForeground(Color.RED);
		textField_title_OneSemester.setFont(new Font("����", Font.PLAIN, 17));
		textField_title_OneSemester.setEditable(false);
		textField_title_OneSemester.setColumns(10);
		textField_title_OneSemester.setBounds(35, 295, 169, 40);
		contentPane.add(textField_title_OneSemester);

		textField_MealDivision1 = new JTextField();
		textField_MealDivision1.setText("�Ļ籸��");
		textField_MealDivision1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivision1.setFont(new Font("����", Font.PLAIN, 16));
		textField_MealDivision1.setEditable(false);
		textField_MealDivision1.setColumns(10);
		textField_MealDivision1.setBackground(Color.LIGHT_GRAY);
		textField_MealDivision1.setBounds(422, 339, 125, 40);
		contentPane.add(textField_MealDivision1);

		textField_MealDivision2 = new JTextField();
		textField_MealDivision2.setText("�Ļ籸��");
		textField_MealDivision2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivision2.setFont(new Font("����", Font.PLAIN, 16));
		textField_MealDivision2.setEditable(false);
		textField_MealDivision2.setColumns(10);
		textField_MealDivision2.setBackground(Color.LIGHT_GRAY);
		textField_MealDivision2.setBounds(422, 386, 125, 40);
		contentPane.add(textField_MealDivision2);

		textField_MealDivision3 = new JTextField();
		textField_MealDivision3.setText("�Ļ籸��");
		textField_MealDivision3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_MealDivision3.setFont(new Font("����", Font.PLAIN, 16));
		textField_MealDivision3.setEditable(false);
		textField_MealDivision3.setColumns(10);
		textField_MealDivision3.setBackground(Color.LIGHT_GRAY);
		textField_MealDivision3.setBounds(422, 432, 125, 40);
		contentPane.add(textField_MealDivision3);

		textField_DormitoryWish1 = new JTextField();
		textField_DormitoryWish1.setText("1����");
		textField_DormitoryWish1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWish1.setFont(new Font("����", Font.PLAIN, 16));
		textField_DormitoryWish1.setEditable(false);
		textField_DormitoryWish1.setColumns(10);
		textField_DormitoryWish1.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWish1.setBounds(35, 339, 125, 40);
		contentPane.add(textField_DormitoryWish1);

		textField_DormitoryWish2 = new JTextField();
		textField_DormitoryWish2.setText("2����");
		textField_DormitoryWish2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWish2.setFont(new Font("����", Font.PLAIN, 16));
		textField_DormitoryWish2.setEditable(false);
		textField_DormitoryWish2.setColumns(10);
		textField_DormitoryWish2.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWish2.setBounds(35, 386, 125, 40);
		contentPane.add(textField_DormitoryWish2);

		textField_DormitoryWish3 = new JTextField();
		textField_DormitoryWish3.setText("3����");
		textField_DormitoryWish3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_DormitoryWish3.setFont(new Font("����", Font.PLAIN, 16));
		textField_DormitoryWish3.setEditable(false);
		textField_DormitoryWish3.setColumns(10);
		textField_DormitoryWish3.setBackground(Color.LIGHT_GRAY);
		textField_DormitoryWish3.setBounds(35, 432, 125, 40);
		contentPane.add(textField_DormitoryWish3);

		// GUI �޺��ڽ� ���

		comboBox_DormitoryWish1 = new JComboBox();
		comboBox_DormitoryWish1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "���þ���";
				dormitoryWish1 = comboBox_DormitoryWish1.getSelectedItem().toString();
				if (dormitoryWish1 == "���þ���") {
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
					JOptionPane.showMessageDialog(null, "�ߺ��Դϴ�");
					comboBox_DormitoryWish1.setSelectedItem(str);
				} else if (dormitoryWish1 == "������1��" || dormitoryWish1 == "������2��" || dormitoryWish1 == "������3��") {
					comboBox_MealDivision1.removeItem("�Ļ����");
					comboBox_MealDivision1.setEnabled(true);
				} else {
					if (comboBox_MealDivision1.getItemCount() < 4) {
						comboBox_MealDivision1.addItem("�Ļ����");
					}
					comboBox_MealDivision1.setEnabled(true);
				}
			}
		});
		
		comboBox_DormitoryWishYear = new JComboBox();
		comboBox_DormitoryWishYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "���þ���";
				dormitoryWishYear = comboBox_DormitoryWishYear.getSelectedItem().toString();

				if (str == dormitoryWishYear) {
					comboBox_MealDivisionYear.setEnabled(false);
					comboBox_MealDivisionYear.setSelectedItem(str);
				} else {
					comboBox_MealDivisionYear.setEnabled(true);
				}
			}
		});
		comboBox_DormitoryWishYear.setModel(new DefaultComboBoxModel(new String[] { "���þ���", "Ǫ����2��", "Ǫ����3��" }));
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
		comboBox_DormitoryWish1.setModel(new DefaultComboBoxModel(new String[] { "���þ���", "Ǫ����1��", "Ǫ����2��", "Ǫ����3��",
				"Ǫ����4��", "������1��", "������2��", "������3��", "���������", "���������" }));
		comboBox_DormitoryWish1.setBounds(159, 339, 263, 40);
		contentPane.add(comboBox_DormitoryWish1);


		comboBox_MealDivision1 = new JComboBox();
		comboBox_MealDivision1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "���þ���";
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
				String str = "���þ���";
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
					JOptionPane.showMessageDialog(null, "�ߺ��Դϴ�");
					comboBox_DormitoryWish2.setSelectedItem(str);
				} else if (dormitoryWish2 == "������1��" || dormitoryWish2 == "������2��" || dormitoryWish2 == "������3��") {
					comboBox_MealDivision2.removeItem("�Ļ����");
					comboBox_MealDivision2.setEnabled(true);
				} else {
					if (comboBox_MealDivision2.getItemCount() < 4) {
						comboBox_MealDivision2.addItem("�Ļ����");
					}
					comboBox_MealDivision2.setEnabled(true);
				}
			}
		});
		comboBox_DormitoryWish2.setModel(new DefaultComboBoxModel(new String[] { "���þ���", "Ǫ����1��", "Ǫ����2��", "Ǫ����3��",
				"Ǫ����4��", "������1��", "������2��", "������3��", "���������", "���������" }));
		comboBox_DormitoryWish2.setBounds(159, 386, 263, 40);
		contentPane.add(comboBox_DormitoryWish2);

		comboBox_MealDivision2 = new JComboBox();
		comboBox_MealDivision2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String str = "���þ���";
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
				String str = "���þ���";
				dormitoryWish3 = comboBox_DormitoryWish3.getSelectedItem().toString();

				if (str == dormitoryWish3) {
					comboBox_MealDivision3.setEnabled(false);
					comboBox_MealDivision3.setSelectedItem(str);
				} else if (dormitoryWish3 == comboBox_DormitoryWish1.getSelectedItem().toString()
						|| dormitoryWish3 == comboBox_DormitoryWish2.getSelectedItem().toString()) {
					JOptionPane.showMessageDialog(null, "�ߺ��Դϴ�");
					comboBox_DormitoryWish3.setSelectedItem(str);
				} else if (dormitoryWish3 == "������1��" || dormitoryWish3 == "������2��" || dormitoryWish3 == "������3��") {
					comboBox_MealDivision3.removeItem("�Ļ����");
					comboBox_MealDivision3.setEnabled(true);
				} else {
					if (comboBox_MealDivision3.getItemCount() < 4) {
						comboBox_MealDivision3.addItem("�Ļ����");
					}
					comboBox_MealDivision3.setEnabled(true);
				}
			}
		});

		comboBox_MealDivision3 = new JComboBox();
		comboBox_DormitoryWish3.setModel(new DefaultComboBoxModel(new String[] { "���þ���", "Ǫ����1��", "Ǫ����2��", "Ǫ����3��",
				"Ǫ����4��", "������1��", "������2��", "������3��", "���������", "���������" }));
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

		// GUI ��ư ���
		Button_Application = new JButton("��û");
		Button_Application.addActionListener(new ActionListener() { // ��û ��ư Ŭ�� ��, ����ڰ� �Է��� �Ի� ��û ������ ���� ����� ���� ������ �����Ѵ�.
			public void actionPerformed(ActionEvent e) {
				dormitoryApplication app = new dormitoryApplication();

				app.setStudentId(student.getStudentId());

				if (!comboBox_DormitoryWishYear.getSelectedItem().equals("���þ���")) {
					app.setDormitoryWishYear(
							convertDormitoryNameToCode(comboBox_DormitoryWishYear.getSelectedItem().toString()));
					app.setMealDivisionYear(comboBox_MealDivisionYear.getSelectedItem().toString());
				}

				if (!comboBox_DormitoryWish1.getSelectedItem().equals("���þ���")) {
					app.setDormitoryWish1(
							convertDormitoryNameToCode(comboBox_DormitoryWish1.getSelectedItem().toString()));
					app.setMealDivision1(comboBox_MealDivision1.getSelectedItem().toString());
				}

				if (!comboBox_DormitoryWish2.getSelectedItem().equals("���þ���")) {
					app.setDormitoryWish2(
							convertDormitoryNameToCode(comboBox_DormitoryWish2.getSelectedItem().toString()));
					app.setMealDivision2(comboBox_MealDivision2.getSelectedItem().toString());
				}
				if (!comboBox_DormitoryWish3.getSelectedItem().equals("���þ���")) {
					app.setDormitoryWish3(
							convertDormitoryNameToCode(comboBox_DormitoryWish3.getSelectedItem().toString()));
					app.setMealDivision3(comboBox_MealDivision3.getSelectedItem().toString());
				}

				if (!comboBox_DormitoryWishYear.getSelectedItem().equals("���þ���")
						|| !comboBox_DormitoryWish1.getSelectedItem().equals("���þ���")) {
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
								JOptionPane.showMessageDialog(null, "��û�� ���������� �Ϸ�Ǿ����ϴ�!!");
								dispose();
							} else if (p.getCode() == 2)
								JOptionPane.showMessageDialog(null, (String) p.getBody());
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "�ּ� 1������ �����ؾ� �մϴ�.");
				}
			}
		});

		Button_Application.setBounds(620, 149, 90, 30);
		contentPane.add(Button_Application);
		setSize(750, 550);
	}

	/**
	 * ��Ȱ�� �̸��� �ڵ�� ��ȯ �ϴ� �޼ҵ�
	 * @param String s �� ��Ȱ�� �̸�
	 * @return code �� ��Ȱ�� �ڵ�
	 **/
	public String convertDormitoryNameToCode(String s) {
		String code = null;

		if (s.equals("Ǫ����1��"))
			code = new String("1");
		else if (s.equals("Ǫ����2��"))
			code = new String("2");
		else if (s.equals("Ǫ����3��"))
			code = new String("3");
		else if (s.equals("Ǫ����4��"))
			code = new String("4");
		else if (s.equals("������1��"))
			code = new String("5");
		else if (s.equals("������2��"))
			code = new String("6");
		else if (s.equals("������3��"))
			code = new String("7");

		else if (s.equals("���������"))
			code = new String("8");
		else if (s.equals("���������"))
			code = new String("9");
		return code;
	}
}