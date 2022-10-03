// 생활관비 등록
// 관리자가 각 생활관의 생활관비를 업로드 시킬 수 있다.
// 최대 9개 생활관을 동시에 업로드 시킬 수 있고 업로드할 시엔 12개 Column을 모두 NULL값없이 작성해야 업로드가 이루어진다.

package AdminGUI;
import GUI.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Network.Protocol;
import tableClass.*;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;

public class LivingHall_Cost_Enroll extends JFrame {
	private Socket socket;
	private JPanel contentPane;
	private static Protocol p;
	private static ObjectOutputStream writer;
	private static ObjectInputStream reader;

	
	public LivingHall_Cost_Enroll(Protocol p_t, ObjectOutputStream writer_t, ObjectInputStream reader_t,Socket sk) {
		socket = sk;
		p = p_t;
		writer = writer_t;
		reader = reader_t;
		this.setResizable(false); // 최대화 단추 없애기
		setVisible(true);
		setTitle("생활관비 등록");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1320, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// 테이블에 출력할 컬럼 이름 배열
		String columnNames[] = { "생활관 이름", "관리_1", "관리_S", "관리_2", "관리_W", "5식_1", "5식_S", "5식_2", "5식_W", "7식_1",
				"7식_S", "7식_2", "7식_W" };

		// 테이블에 출력할 데이터 배열
		String data[][] = new String[9][13]; // 데이터 들어갈 범위

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable tbl = new JTable(model);
		tbl.setRowHeight(25);

		// JTable tbl = new JTable(data,columnNames);
		// Table은 JScrollPane위에 출력해야 컬럼 이름이 출력된다! 명심할것
		JScrollPane scroll = new JScrollPane(tbl);
		scroll.setBounds(12, 10, 1292, 274);
		scroll.getVerticalScrollBar().setUnitIncrement(100); // 스크롤 속도
		contentPane.setLayout(null);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scroll);

		JButton btnNewButton = new JButton("제출");		//제출버튼을 누를경우 작성된 생활관비 정보를 서버에 전송한다.
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cnt = -1;
				Dormitory_cost[] dc_t = new Dormitory_cost[9];	//최대 9개 생활관의 비용정보를 배열에 담는다.
				for(int i = 0; i < 9; i++)
				{
					if(model.getValueAt(i, 0) != null)		//생활관을 선택한 tuple만 체크
					{
						for(int j = 1; j <= 12; j++)
						{
							if(model.getValueAt(i, j) == null)		//12개의 Column중 하나라도 NULL이 존재할 경우 업로드는 이루어지지 않는다.
							{
								break;
							}

							else if(j == 12 && model.getValueAt(i, j) != null)		//12개 Column이 모두 Not NULL인 경우
							{														//생활관비 정보를 배열에 담는다.
								cnt += 1;
								dc_t[cnt] = new Dormitory_cost();
								dc_t[cnt].setKind_code((String)model.getValueAt(i, 0));
								dc_t[cnt].setMng_cost1(Integer.parseInt(model.getValueAt(i, 1).toString()));
								dc_t[cnt].setMng_cost2(Integer.parseInt(model.getValueAt(i, 2).toString()));
								dc_t[cnt].setMng_cost3(Integer.parseInt(model.getValueAt(i, 3).toString()));
								dc_t[cnt].setMng_cost4(Integer.parseInt(model.getValueAt(i, 4).toString()));
								dc_t[cnt].setFd_food_cost1(Integer.parseInt(model.getValueAt(i, 5).toString()));
								dc_t[cnt].setFd_food_cost2(Integer.parseInt(model.getValueAt(i, 6).toString()));
								dc_t[cnt].setFd_food_cost3(Integer.parseInt(model.getValueAt(i, 7).toString()));
								dc_t[cnt].setFd_food_cost4(Integer.parseInt(model.getValueAt(i, 8).toString()));
								dc_t[cnt].setSd_food_cost1(Integer.parseInt(model.getValueAt(i, 9).toString()));
								dc_t[cnt].setSd_food_cost2(Integer.parseInt(model.getValueAt(i, 10).toString()));
								dc_t[cnt].setSd_food_cost3(Integer.parseInt(model.getValueAt(i, 11).toString()));
								dc_t[cnt].setSd_food_cost4(Integer.parseInt(model.getValueAt(i, 12).toString()));
							}
						}
					}
				}

				if(cnt != -1)	//생활관비 정보가 1개라도 존재하는 경우 생활관비 배열을 전송
				{
					Dormitory_cost[] dc = new Dormitory_cost[cnt + 1];
					for(int i = 0; i <= cnt; i++)
						dc[i] = new Dormitory_cost(dc_t[i]);
					System.out.println(Integer.parseInt(model.getValueAt(0, 2).toString()));
					System.out.println(dc_t[0].getMng_cost2());
					try
					{
						p.makePacket(22, 1, 0, dc);
						writer.writeObject(p);
						writer.flush();
						writer.reset();
						p = (Protocol)reader.readObject();

						if (p.getSubType() == 2) {		//생활관비 정보 갱신 결과 수신
							if (p.getCode() == 1) {
								JOptionPane.showMessageDialog(null, "생활관비가 정상적으로 등록되었습니다.");
								dispose();
							} else if (p.getCode() == 2) {
								String err = (String) p.getBody();
								JOptionPane.showMessageDialog(null, err); // ?
								dispose();
							}
						}

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else	//생활관비 정보가 0개인 경우 전송이 이루어지지 않는다.
					JOptionPane.showMessageDialog(null, "올바른 양식을 입력해 주세요.");

				//model.getValueAt(1, 1);
				//while(data[i][0] != );
			}
		});
		btnNewButton.setBounds(1185, 295, 105, 40);
		contentPane.add(btnNewButton);

		// Jtable안에 Jcombobox 넣기
		TableColumn dorm = tbl.getColumnModel().getColumn(0);
		JComboBox comboBox = new JComboBox();	//콤보박스를 통해 생활관을 고를 수 있다.
		comboBox.addItem("푸름관1동");
		comboBox.addItem("푸름관2동");
		comboBox.addItem("푸름관3동");
		comboBox.addItem("푸름관4동");
		comboBox.addItem("오름관1동");
		comboBox.addItem("오름관2동");
		comboBox.addItem("오름관3동");
		comboBox.addItem("신평관남자");
		comboBox.addItem("신평관여자");
		dorm.setCellEditor(new DefaultCellEditor(comboBox));

		// combobox가 Jtable에 보이게 함
		DefaultTableCellRenderer comboBoxTableCellRenderer = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean isSelected,
					boolean hasFocus, int arg4, int arg5) {
				JComboBox comboBox = new JComboBox();
				comboBox.addItem(arg1);
				return comboBox;
			}
		};
		tbl.getColumn("생활관 이름").setCellRenderer(comboBoxTableCellRenderer);
	}
}