// 입사선발자 결과등록
// 입사신청을 한 사람들을 성적순으로 나열하여 희망생활관(빈 호실에 한해)에 순차적으로 배정한다.

package AdminGUI;
import GUI.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Network.Protocol;
import tableClass.*;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.Point;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.DropMode;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Joiner_Result_Enroll extends JFrame {
	private Socket socket;
	private static Protocol p;
	private static OutputStream os;
	private static ObjectOutputStream writer;
	private static InputStream is;
	private static ObjectInputStream reader;

	private JPanel contentPane;
	private JTextField textField;

	public Joiner_Result_Enroll(Protocol p_t, ObjectOutputStream writer_t, ObjectInputStream reader_t,Socket sk) {
		socket = sk;
		p = p_t;
		writer = writer_t;
		reader = reader_t;
		this.setResizable(false); // 최대화 단추 없애기
		setTitle("입사선발자 결과등록");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 510, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setForeground(Color.BLACK);
		textArea_1.setFont(new Font("돋움", Font.BOLD, 18));
		textArea_1.setEditable(false);
		textArea_1.setText("\u25C8 입사선발자 결과등록");
		textArea_1.setBackground(SystemColor.control);
		textArea_1.setBounds(35, 27, 210, 20);
		contentPane.add(textArea_1);

		JButton button = new JButton("예");		//'예' 버튼을 누를 경우 입사선발자 알고리즘을 작동시켜 생활관을 할당한다.
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					p.makePacket(25, 1, 0, null);		//알고리즘 작동 요청 전송
					writer.writeObject(p);
					writer.flush();
					writer.reset();
					p = (Protocol) reader.readObject();

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (p.getSubType() == 2) {		// 알고리즘 처리결과 수신
					if (p.getCode() == 1) {		// 각 신청자들을 성적, 생활관에 맞게 배정하고 불합격자에게 대기번호를 부여함.
						JOptionPane.showMessageDialog(null, "입사선발자들이 정상적으로 등록되었습니다.");
						dispose();
					} else if (p.getCode() == 2) {	//알고리즘 작동 중 오류발생
						String err = (String) p.getBody();
						JOptionPane.showMessageDialog(null, err);
						dispose();
					}
				}
			}
		});
		button.setBounds(35, 165, 182, 48);
		contentPane.add(button);

		JButton button_1 = new JButton("아니요");		// '아니오' 를 누를 경우 메인메뉴(관리자메뉴)로 돌아간다.
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "메인메뉴로 이동합니다.");
				dispose();
			}
		});
		button_1.setBounds(274, 165, 182, 48);
		contentPane.add(button_1);

		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("굴림", Font.BOLD, 16));
		textField.setText("결과 등록 하시겠습니까?");
		textField.setBounds(35, 57, 421, 80);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}