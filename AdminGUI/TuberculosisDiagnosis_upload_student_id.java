// (관리자) 결핵진단서 업로드 클릭시, '제출자의 학번을 입력해주세요'라는 창
// 제출자의 학번을 토대로 제출여부 검사 후 결과 수신

package AdminGUI;
import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import Network.Protocol;
import StudentGUI.TuberculosisDiagnosis_storage;
import tableClass.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class TuberculosisDiagnosis_upload_student_id extends JFrame {
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private static String ip;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton;


	public TuberculosisDiagnosis_upload_student_id(Protocol p_t, ObjectOutputStream writer_t, ObjectInputStream reader_t, String ip_t,Socket sk) {      
		socket = sk;
		p = p_t;
		writer = writer_t;
		reader = reader_t;
		ip = ip_t;
		setVisible(true);
		setTitle("결핵진단서 업로드 시 학번 입력");
		this.setResizable(false); // 최대화 단추 없앰
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 505, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("학번");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 86, 90, 45);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.doClick(); // 엔터 클릭시 확인 버튼 눌러짐
			}
		});
		textField.setBounds(140, 87, 260, 45);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("제출자의 학번을 입력해 주세요!");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(30, 33, 350, 43);
		contentPane.add(lblNewLabel_1);

		btnNewButton = new JButton("확인");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() != null && textField.getText().length() == 8)	
				{		//입력한 학번이 NULL값이거나 8자리가 아닌경우 패킷전송이 이루어지지 않음.
					try {
						p.makePacket(27, 1, 0, textField.getText());	//작성된 학번을 Body에 담아 전송
						writer.writeObject(p);
						writer.flush();
						writer.reset();
						p = (Protocol) reader.readObject();

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}

					if (p.getSubType() == 2) {		//제출대상인지 검사한 것에 대한 결과 수신
						if(p.getCode() == 1)		//제출대상(입사선발자이면서 동시에 결핵진단서 제출여부가 X인 사람)
						{	//결핵진단서를 제출할 수 있는 창으로 이동한다(학생메뉴의 기능과 동일하여 같은 클래스 사용)
							new TuberculosisDiagnosis_storage(p, writer, reader, ip, socket);
						}

						else
						{
							String err = (String) p.getBody();
							JOptionPane.showMessageDialog(null, err);
							dispose();
						}  
					}  
				}

				else
					JOptionPane.showMessageDialog(null, "입력값이 올바르지 않습니다.");
			}
		});
		btnNewButton.setBounds(355, 180, 117, 38);
		contentPane.add(btnNewButton);
	}
}