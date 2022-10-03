// (������) �������ܼ� ���ε� Ŭ����, '�������� �й��� �Է����ּ���'��� â
// �������� �й��� ���� ���⿩�� �˻� �� ��� ����

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
		setTitle("�������ܼ� ���ε� �� �й� �Է�");
		this.setResizable(false); // �ִ�ȭ ���� ����
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 505, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("�й�");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("����", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(50, 86, 90, 45);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton.doClick(); // ���� Ŭ���� Ȯ�� ��ư ������
			}
		});
		textField.setBounds(140, 87, 260, 45);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("�������� �й��� �Է��� �ּ���!");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(30, 33, 350, 43);
		contentPane.add(lblNewLabel_1);

		btnNewButton = new JButton("Ȯ��");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText() != null && textField.getText().length() == 8)	
				{		//�Է��� �й��� NULL���̰ų� 8�ڸ��� �ƴѰ�� ��Ŷ������ �̷������ ����.
					try {
						p.makePacket(27, 1, 0, textField.getText());	//�ۼ��� �й��� Body�� ��� ����
						writer.writeObject(p);
						writer.flush();
						writer.reset();
						p = (Protocol) reader.readObject();

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}

					if (p.getSubType() == 2) {		//���������� �˻��� �Ϳ� ���� ��� ����
						if(p.getCode() == 1)		//������(�Ի缱�����̸鼭 ���ÿ� �������ܼ� ���⿩�ΰ� X�� ���)
						{	//�������ܼ��� ������ �� �ִ� â���� �̵��Ѵ�(�л��޴��� ��ɰ� �����Ͽ� ���� Ŭ���� ���)
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
					JOptionPane.showMessageDialog(null, "�Է°��� �ùٸ��� �ʽ��ϴ�.");
			}
		});
		btnNewButton.setBounds(355, 180, 117, 38);
		contentPane.add(btnNewButton);
	}
}