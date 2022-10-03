// 입사자 등록 및 조회
// 결핵진단서와 생활관비를 전부 납부한 사람들을 최종입사자로 등록시키고, 최종입사자를 조회할 수 있다.

package AdminGUI;
import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Network.Protocol;
import tableClass.*;

import javax.swing.JButton;

public class Joiner_Enroll_and_Check extends JFrame {
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;

	private JPanel contentPane;


	public Joiner_Enroll_and_Check(Protocol p_t, ObjectOutputStream writer_t, ObjectInputStream reader_t,Socket sk) {
		socket = sk;
		p = p_t;
		writer = writer_t;
		reader = reader_t;
		this.setResizable(false); // 최대화 단추 없애기
		setVisible(true);
		setTitle("입사자 등록 및 조회");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 350, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 결핵진단서와 납부상태가 'O'인 사람들에 한해서 등록여부(최종입사 여부)를 'O'로 갱신시킨다.
		JButton btnNewButton = new JButton("입사자 등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					p = new Protocol(23, 1);
					writer.writeObject(p);
					writer.flush();
					writer.reset();
					p = (Protocol) reader.readObject();
					System.out.println(p.getMainType() + " " + p.getSubType());

				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (p.getSubType() == 2) {		// 입사자 등록요청에 대한 응답 도착
					if (p.getCode() == 1) {		// 입사자들을 정상적으로 등록시킴
						JOptionPane.showMessageDialog(null, "입사자들이 정상적으로 등록되었습니다.");
						dispose();
					} else if (p.getCode() == 2) {		//입사자를 등록시키는 도중 오류가 발생함
						String err = (String) p.getBody();
						JOptionPane.showMessageDialog(null, err);	//에러메세지 출력
						dispose();
					}

				}
			}
		});
		btnNewButton.setBounds(30, 40, 110, 40);
		contentPane.add(btnNewButton);

		JButton button = new JButton("입사자 조회");		//등록여부가 'O'인 최종입사자들을 조회할 수 있다.
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Joiner_Check(p, writer, reader, socket); // 입사자 조회 메뉴로 넘어간다.
			}
		});
		button.setBounds(195, 40, 110, 40);
		contentPane.add(button);
	}
}
