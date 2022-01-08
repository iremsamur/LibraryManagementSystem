package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class MemberLogRecordsScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtMemberTC;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Üye No","Üye Adý","Üye Soyadý","Üye TC","Üye Doðum Tarihi","Üye Mail Adresi","Üye Telefon Numarasý","Cinsiyet","Adres"};
	Object[] satirlar = new Object[9];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberLogRecordsScreen frame = new MemberLogRecordsScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public OracleResultSet listTable(String sorgu) {
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			
			pst = (OraclePreparedStatement) conn.prepareStatement(sorgu);
		
			rs = (OracleResultSet) pst.executeQuery();
			return rs;
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}
		
		
	}
	
	public void listLogRecordsFormerMembers() {
		String sorgu = "SELECT * FROM LogRecordsLibraryMember";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = listTable(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("memberID");
				
				
				satirlar[1] = rs.getString("memberName");
				
				satirlar[2] = rs.getString("memberSurname");
				satirlar[3] = rs.getString("TCIdentificationNumber");
				satirlar[4] = rs.getString("memberBirthDate");
				satirlar[5] = rs.getString("memberMail");
				satirlar[6] = rs.getString("memberPhoneNumber");
				
				satirlar[7] = rs.getString("memberGender");
				
				satirlar[8] = rs.getString("memberAddress");
			
				
				
				
			
				
				
				
				
				modelim.addRow(satirlar);
				
			}
			table.setModel(modelim);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}

	/**
	 * Create the frame.
	 */
	public MemberLogRecordsScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 703, 521);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(33, 71, 644, 400);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 84, 610, 305);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(173, 216, 230));
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Aranacak \u00DCyenin TC Kimlik Numaras\u0131");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(34, 35, 247, 33);
		panel.add(lblNewLabel);
		
		txtMemberTC = new JTextField();
		txtMemberTC.setForeground(new Color(0, 0, 128));
		txtMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberTC.setBounds(291, 33, 202, 37);
		panel.add(txtMemberTC);
		txtMemberTC.setColumns(10);
		
		JButton btnSearchLogMembers = new JButton("Ara");
		btnSearchLogMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//arama
				String memberTC = txtMemberTC.getText();
				
				if(memberTC.length()!=0) {
					String sorgu = "SELECT * FROM LogRecordsLibraryMember WHERE TCIdentificationNumber = '"+memberTC+"'";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = listTable(sorgu);
					try {
						while(rs.next()) {
							
							satirlar[0] = rs.getInt("memberID");
							
							
							satirlar[1] = rs.getString("memberName");
							
							satirlar[2] = rs.getString("memberSurname");
							satirlar[3] = rs.getString("TCIdentificationNumber");
							satirlar[4] = rs.getString("memberBirthDate");
							satirlar[5] = rs.getString("memberMail");
							satirlar[6] = rs.getString("memberPhoneNumber");
							
							satirlar[7] = rs.getString("memberGender");
							
							satirlar[8] = rs.getString("memberAddress");
							
							
							
							modelim.addRow(satirlar);
							
							
						}
						table.setModel(modelim);
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Aranacak üye TC adý alaný boþ býrakýlamaz!!! Lütfen aranacak üyenin TC kimlik numarasýný giriniz!!");
				}
			}
		});
		btnSearchLogMembers.setBackground(new Color(255, 215, 0));
		btnSearchLogMembers.setForeground(new Color(128, 0, 128));
		btnSearchLogMembers.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchLogMembers.setBounds(513, 35, 89, 33);
		panel.add(btnSearchLogMembers);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(63, 11, 473, 49);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Eski \u00DCyeler Log Kay\u0131tlar\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(141, 11, 360, 27);
		panel_1.add(lblNewLabel_1);
		
		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExit.setBounds(585, 11, 63, 49);
		contentPane.add(btnExit);
		listLogRecordsFormerMembers();
	}
}
