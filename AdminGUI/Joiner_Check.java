// �Ի��� ��ȸ
// ���������� �Ի簡 Ȯ���� ������� ��ȸ�Ѵ�.

package AdminGUI;
import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Network.Protocol;
import tableClass.*;
import AdminGUI.*;

public class Joiner_Check extends JFrame {
	private Socket socket;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;
	private JPanel contentPane;


	public Joiner_Check(Protocol p_t, ObjectOutputStream writer_t, ObjectInputStream reader_t,Socket sk) {
		socket = sk;
		p = p_t;
		writer = writer_t;
		reader = reader_t;
		this.setResizable(false); // �ִ�ȭ ���� ���ֱ�
		setVisible(true);
		setTitle("�Ի��� ��ȸ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 810, 640);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			p.makePacket(24, 1, 0, null);
			writer.writeObject(p);
			writer.flush();
			writer.reset();
			p = (Protocol) reader.readObject();
			System.out.println(p.getMainType() + " " + p.getSubType());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		//��ȸ�� ������ ��� �����͹迭�� �޾ƿ� ��½�Ų��.
		if(p.getCode() == 1)
		{
			// ���̺��� ����� �÷� �̸� �迭
			String columnNames[] = {"�й�", "�̸�", "��Ȱ��", "ȣ��", "ħ���ȣ"};

			// ���̺��� ����� ������ �迭
			String[][] data = (String [][])p.getBody();		//�޾ƿ� ������
			for (int i = 0; i < data.length; i++) {
	            System.out.println(data[i][0] + " " + data[i][1]);
	        }

			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			JTable tbl = new JTable(model);
			tbl.setRowHeight(25);

			// JTable tbl = new JTable(data,columnNames);
			// Table�� JScrollPane���� ����ؾ� �÷� �̸��� ��µȴ�! �����Ұ�
			JScrollPane scroll = new JScrollPane(tbl);
			scroll.getVerticalScrollBar().setUnitIncrement(100); // ��ũ�� �ӵ�
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			getContentPane().add(scroll);
			scroll.setSize(772, 583);
			scroll.setLocation(12, 10);
		}
		
		//��ȸ�� ������ ��� �����޼��� ���
		else
		{
			String err = (String) p.getBody();
			JOptionPane.showMessageDialog(null, err);
			dispose();
		}
	}
}