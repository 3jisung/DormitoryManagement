//관리자메뉴

package AdminGUI;

import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import Network.Protocol;
import tableClass.*;
import StudentGUI.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Menu_Admin extends JFrame {
	private Socket socket;
	private JPanel contentPane;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private static String ip;

	public Menu_Admin(Protocol p_t, ObjectOutputStream writer_t, ObjectInputStream reader_t, String ip_t,Socket sk) {
		socket = sk;
		p = p_t;
		writer = writer_t;
		reader = reader_t;
		ip = ip_t;
		
		this.addWindowListener(new MyWindowListener(socket, writer));
		this.setResizable(false); // 최대화 단추 없애기
		
		setTitle("관리자 메뉴");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 745, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton button = new JButton("선발일정 등록");		//선발일정 등록을 누를경우 등록할 수 있는 창으로 이동한다.
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Selection_Schedule_Enroll(p, writer, reader, socket);
			}
		});
		button.setBounds(45, 87, 285, 50);
		contentPane.add(button);

		JButton button_1 = new JButton("생활관비 등록");	//생활관비 등록 창으로 이동한다.
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LivingHall_Cost_Enroll(p, writer, reader, socket);
			}
		});
		button_1.setBounds(353, 87, 285, 50);
		contentPane.add(button_1);

		JButton button_2 = new JButton("입사자 등록 및 조회");	//입사자 등록 및 조회를 고를 수 있는 창으로 이동한다.
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Joiner_Enroll_and_Check(p, writer, reader, socket);
			}
		});
		button_2.setBounds(45, 162, 285, 50);
		contentPane.add(button_2);

		JButton button_3 = new JButton("입사선발자 결과등록");	//입사선발자 결과를 등록할 수 있는 창으로 이동한다.
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Joiner_Result_Enroll(p,writer,reader, socket);
			}
		});
		button_3.setBounds(353, 162, 285, 50);
		contentPane.add(button_3);

		JButton button_4 = new JButton("결핵진단서 업로드 및 제출확인");		//결핵진단서 업로드와 제출확인을 고를 수 있는 창으로 이동한다.
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TuberculosisDiagnosis_upload_submit_check(p, writer, reader, ip, socket);
			}
		});
		button_4.setBounds(45, 235, 285, 50);
		contentPane.add(button_4);

		JButton button_5 = new JButton("입사선발자 납부상태 갱신");		//입사선발자 납부상태를 갱신할 수 있는 창으로 이동한다.
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Pay_State_Check(p, writer, reader, socket);
			}
		});
		button_5.setBounds(353, 235, 285, 50);
		contentPane.add(button_5);
		setVisible(true);

		String name = (String)(p.getBody());		//로그인에서 입력한 ID를 토대로 사용자의 이름을 불러와 화면에 출력시킨다.
		JLabel lblNewLabel = new JLabel(name + "님 환영합니다!");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setBounds(49, 18, 160, 40);
		contentPane.add(lblNewLabel);

		setVisible(true);
	}

}
