/**
 * @Copyright 융합프로젝트 8조
 */

package StudentGUI;

import GUI.*;
import tableClass.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Network.Protocol;
import tableClass.Student;
import tableClass.dormitoryApplication;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 * @file DetailedStatement_Bill.java
 * 
 * @author 김동윤, 김선진
 * 
 * @day 2019-12-11
 * 
 * @brief 이 클래스는 Menu_Student 클래스에서 생성되며 소켓 통신으로  
 * 입사신청 내역 및 고지서 출력에 관련된 객체를 수신하여 입사신청 내역 혹은 고지서 출력 클래스에 전달한다.
 */

public class DetailedStatement_Bill extends JFrame {
	
	/** GUI 출력을 위한 패널 및 버튼 선언 */
	private JPanel contentPane;
	private JButton Button_Join_Application_DetailedStatement;
	private JButton Button_Bill;
	
	/** 소켓 통신을 위한 기본 변수 선언 */
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer; 
	private static ObjectInputStream reader;
	private Student student;
	private dormitoryApplication[] appList;

	public DetailedStatement_Bill(Protocol p_t, Student s, ObjectOutputStream oos, ObjectInputStream ois,Socket sk) {

		// 소켓 통신을 위한 기본 변수 초기화
		socket = sk;
		p = p_t;
		student = s;
		appList = (dormitoryApplication[])p_t.getBody();
		writer = oos;
		reader = ois;

		// GUI 출력
		this.setVisible(true);
		this.setResizable(false); // 최대화 단추 없애기
		setTitle("입사신청 내역조회 및 고지서 출력");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button_Join_Application_DetailedStatement = new JButton("입사신청 내역조회"); 
		Button_Join_Application_DetailedStatement.addActionListener(new ActionListener() { // 입사신청 내역 버튼 클릭 시, 입사 신청 내역 조회 클래스를 호출한다
			public void actionPerformed(ActionEvent e) {
				new Join_Application_DetailedStatement(student, appList, socket); 
			}
		});
		Button_Join_Application_DetailedStatement.setBounds(30, 40, 140, 40);
		contentPane.add(Button_Join_Application_DetailedStatement);

		Button_Bill = new JButton("고지서 출력");
		Button_Bill.addActionListener(new ActionListener() { // 고지서 출력 버튼 클릭 시, 서버와 통신하여 입사 선발자인지 확인 한 후, 맞으면 고지서 출력 클래스 호출, 아니면 에러창 호출
			public void actionPerformed(ActionEvent e) {
				p = new Protocol(14,1,0,null);
				try {
					writer.writeObject(p);
					writer.flush();
					writer.reset();
					p = (Protocol)reader.readObject();
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (p.getSubType() == 2) {   
					if (p.getCode() == 1) {
						new Bill(student, (SelectedStudent)p.getBody(), writer, reader, socket);
					}
					else if (p.getCode() == 2) {
						String err = (String) p.getBody();
						JOptionPane.showMessageDialog(null, err); // 제출대상 아님 or 제출기간 아님
					}
				}
			}
		});
		Button_Bill.setBounds(195, 40, 130, 40);
		contentPane.add(Button_Bill);
	}
}