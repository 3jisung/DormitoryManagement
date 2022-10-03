/**
 * @Copyright ����������Ʈ 8��
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
 * @author �赿��, �輱��
 * 
 * @day 2019-12-11
 * 
 * @brief �� Ŭ������ Menu_Student Ŭ�������� �����Ǹ� ���� �������  
 * �Ի��û ���� �� ������ ��¿� ���õ� ��ü�� �����Ͽ� �Ի��û ���� Ȥ�� ������ ��� Ŭ������ �����Ѵ�.
 */

public class DetailedStatement_Bill extends JFrame {
	
	/** GUI ����� ���� �г� �� ��ư ���� */
	private JPanel contentPane;
	private JButton Button_Join_Application_DetailedStatement;
	private JButton Button_Bill;
	
	/** ���� ����� ���� �⺻ ���� ���� */
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer; 
	private static ObjectInputStream reader;
	private Student student;
	private dormitoryApplication[] appList;

	public DetailedStatement_Bill(Protocol p_t, Student s, ObjectOutputStream oos, ObjectInputStream ois,Socket sk) {

		// ���� ����� ���� �⺻ ���� �ʱ�ȭ
		socket = sk;
		p = p_t;
		student = s;
		appList = (dormitoryApplication[])p_t.getBody();
		writer = oos;
		reader = ois;

		// GUI ���
		this.setVisible(true);
		this.setResizable(false); // �ִ�ȭ ���� ���ֱ�
		setTitle("�Ի��û ������ȸ �� ������ ���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button_Join_Application_DetailedStatement = new JButton("�Ի��û ������ȸ"); 
		Button_Join_Application_DetailedStatement.addActionListener(new ActionListener() { // �Ի��û ���� ��ư Ŭ�� ��, �Ի� ��û ���� ��ȸ Ŭ������ ȣ���Ѵ�
			public void actionPerformed(ActionEvent e) {
				new Join_Application_DetailedStatement(student, appList, socket); 
			}
		});
		Button_Join_Application_DetailedStatement.setBounds(30, 40, 140, 40);
		contentPane.add(Button_Join_Application_DetailedStatement);

		Button_Bill = new JButton("������ ���");
		Button_Bill.addActionListener(new ActionListener() { // ������ ��� ��ư Ŭ�� ��, ������ ����Ͽ� �Ի� ���������� Ȯ�� �� ��, ������ ������ ��� Ŭ���� ȣ��, �ƴϸ� ����â ȣ��
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
						JOptionPane.showMessageDialog(null, err); // ������ �ƴ� or ����Ⱓ �ƴ�
					}
				}
			}
		});
		Button_Bill.setBounds(195, 40, 130, 40);
		contentPane.add(Button_Bill);
	}
}