package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class FineNotPayMemberScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Ödemeyen No","Üye Adý","Üye Soyadý","Üye TC","Mail Adresi","Telefon Numarasý","Adres","Kitap Adý","Gecikme Gün Sayýsý","Ceza Tutarý","Ceza Tarihi"};
	Object[] satirlar = new Object[11];
	private JTextField txtSearchMemberByTC;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FineNotPayMemberScreen frame = new FineNotPayMemberScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public boolean controlMember(String memberTC) {
		
		
		try {
			String query = "SELECT * FROM FineNotPayMembers WHERE memberTC = '"+memberTC+"'";
			
			OracleResultSet rs = listTable(query);
			if(rs.next()) {
				return true;
				
				
				
				
				
				
				
			}
			else {
				return false;
				
				
				
				
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
			
		}
		
		
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
	public void listAddNotPay(String memberName,String memberSurname,String memberTC,String memberMail,String memberPhoneNumber,String memberAddress,String bookTitle,int delayTimeDay,int fineAmount,String fineDate) {
		try {
			
			Connection conn = null;
			OraclePreparedStatement pst = null;
			OraclePreparedStatement pst2 = null;
			OracleResultSet rs = null;
			boolean controlValue = false;
			conn = ConnectionClass.dbConnect();
			if(controlMember(memberTC)==true) {
				controlValue = false;
				
			}
			else {
				String queryAdd = "INSERT INTO FineNotPayMembers(fineNotPayID,memberName,memberSurname,memberTC,memberMail,memberPhoneNumber,memberAddress,bookTitle,delayTimeDay,fineAmount,fineDate) VALUES(add_fine_not_pay_records.nextval,'"+memberName+"','"+memberSurname+"','"+memberTC+"','"+memberMail+"','"+memberPhoneNumber+"','"+memberAddress+"','"+bookTitle+"',"+delayTimeDay+","+fineAmount+",'"+fineDate+"')";
				pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
				int rowValue = pst2.executeUpdate();
				controlValue = true;
			}
			
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			
		}
		
	}
	public void deletePayFine(String memberTC) {
		Connection conn = null;
		OraclePreparedStatement pst = null;
		
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			//trigger silme iþlemi yapýldýktan sonra silinen üyenin log kaydýný tutuyor
			String queryUpdate = "DELETE FROM FineNotPayMembers WHERE memberTC = '"+memberTC+"'";
			pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
			int rowValue = pst.executeUpdate();
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		
	}
	public void storedProcedureAddNotPayFine() {
		
		String sorgu = "SELECT * "+
				"FROM Fine2,Borrowing2,LibraryMember,Book2,SystemUser "+
				"WHERE Fine2.borrowingID = Borrowing2.borrowingID AND Borrowing2.memberID = LibraryMember.memberID AND Borrowing2.bookNumber = Book2.bookNumber AND Fine2.addedStaffNumber = SystemUser.userNumber ORDER BY Fine2.fineID ASC";
				
		
		OracleResultSet rs = listTable(sorgu);
		
		String memberName = "";
		String memberSurname = "";
		String memberTC = "";
		String memberMail = "";
		String memberPhoneNumber = "";
		String memberAddress = "";
		String bookTitle = "";
		int delayTimeDay = 0;
		int fineAmount = 0;
		String fineDate = "";
		String finePaymentStatus = "";
		
		
		try {
			while(rs.next()) {
				
				
				
				memberName = rs.getString("memberName");
				
				memberSurname = rs.getString("memberSurname");
				memberTC = rs.getString("TCIdentificationNumber");
				memberMail = rs.getString("memberMail");
				memberPhoneNumber = rs.getString("memberPhoneNumber");
				
				memberAddress = rs.getString("memberAddress");
				
				bookTitle = rs.getString("bookTitle");
				delayTimeDay = rs.getInt("delayTimeDay");
				fineAmount = rs.getInt("fineAmount");
				fineDate = rs.getString("fineDate");
				finePaymentStatus = rs.getString("finePaymentStatus");
				if(finePaymentStatus.equals("True")) {
					
					listAddNotPay(memberName,memberSurname,memberTC,memberMail,memberPhoneNumber,memberAddress,bookTitle,delayTimeDay,fineAmount,fineDate);
					
					
					
				}
				else if(finePaymentStatus.equals("False")) {
					deletePayFine(memberTC);
					
					
					
				}
				
				
				
			
				
				
				
				
				
				
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	public void listPayNotMember() {
		String sorgu = "SELECT * FROM FineNotPayMembers";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = listTable(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("fineNotPayID");
				
				
				satirlar[1] = rs.getString("memberName");
				
				satirlar[2] = rs.getString("memberSurname");
				satirlar[3] = rs.getString("memberTC");
				satirlar[4] = rs.getString("memberMail");
				satirlar[5] = rs.getString("memberPhoneNumber");
				satirlar[6] = rs.getString("memberAddress");
				
				satirlar[7] = rs.getString("bookTitle");
				
				satirlar[8] = rs.getInt("delayTimeDay");
				satirlar[9] = rs.getInt("fineAmount");
				satirlar[10] = rs.getString("fineDate");
				
				
				
			
				
				
				
				
				modelim.addRow(satirlar);
				
			}
			table.setModel(modelim);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	public void addNotPayMembersWithProcedure(String memberName,String memberSurname,String memberTC,
			String memberMail,String memberPhoneNumber,String memberAddress,String bookTitle,int delayTimeDay,int fineAmount,
			String fineDate) {
		
		
		//store procedure2ü burada yazalým çaðýralým
		
		
		try {
			//String query = "select * from Admin";
			Connection conn = null;
			OraclePreparedStatement pst = null;
			OraclePreparedStatement pst2 = null;
			OracleResultSet rs = null;
			boolean controlValue = false;
			conn = ConnectionClass.dbConnect();
			if(controlMember(memberTC)==true) {
				controlValue = false;
				
			}
			else {
				
				
				String queryAdd = "EXECUTE fine_pay_not_members_sp(add_fine_not_pay_records.nextval,'"+memberName+"','"+
				memberSurname+"','"+memberTC+"','"+memberMail+"','"+memberPhoneNumber+"','"+memberAddress+"',"+delayTimeDay+","+
						fineAmount+",'"+fineDate+"')";
				pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
				int rowValue = pst2.executeUpdate();
				controlValue = true;
			
				
			}
			
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			
		}
		
		
	}
	
	public void storedProcedureAddNotPayFine(String finePaymentStatus) {
		finePaymentStatus = "True";
		String sorgu = "SELECT * "+
				"FROM Fine2,Borrowing2,LibraryMember,Book2,SystemUser "+
				"WHERE Fine2.borrowingID = Borrowing2.borrowingID AND Borrowing2.memberID = LibraryMember.memberID AND "
				+ "Borrowing2.bookNumber = Book2.bookNumber AND Fine2.addedStaffNumber = SystemUser.userNumber AND "
				+ "Fine2.finePaymentStatus = '"+finePaymentStatus+"' ORDER BY Fine2.fineID ASC";
				
		
		OracleResultSet rs = listTable(sorgu);
		
		String memberName = "";
		String memberSurname = "";
		String memberTC = "";
		String memberMail = "";
		String memberPhoneNumber = "";
		String memberAddress = "";
		String bookTitle = "";
		int delayTimeDay = 0;
		int fineAmount = 0;
		String fineDate = "";
		String finePaymentStatusValue="";
		
		
		try {
			while(rs.next()) {
				
				
				
				memberName = rs.getString("memberName");
				
				memberSurname = rs.getString("memberSurname");
				memberTC = rs.getString("TCIdentificationNumber");
				memberMail = rs.getString("memberMail");
				memberPhoneNumber = rs.getString("memberPhoneNumber");
				
				memberAddress = rs.getString("memberAddress");
				
				bookTitle = rs.getString("bookTitle");
				delayTimeDay = rs.getInt("delayTimeDay");
				fineAmount = rs.getInt("fineAmount");
				fineDate = rs.getString("fineDate");
				finePaymentStatusValue = rs.getString("finePaymentstatus");
                 if(finePaymentStatusValue.equals("True")) {
					
                	 addNotPayMembersWithProcedure(memberName,memberSurname,memberTC,memberMail,memberPhoneNumber,
                			 memberAddress,bookTitle,delayTimeDay,fineAmount,fineDate);
					
					
					
				}
				else if(finePaymentStatus.equals("False")) {
					deletePayFine(memberTC);
					
					
					
				}
				
				
				
				
				
				
			
				
				
				
				
				
				
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		
		
	}

	/**
	 * Create the frame.
	 */
	public FineNotPayMemberScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 758, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel.setBackground(new Color(255, 192, 203));
		panel.setBounds(10, 75, 705, 298);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 679, 203);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(173, 216, 230));
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		
		JButton btnListNotPayFine = new JButton("\u00D6demeyenleri Getir");
		btnListNotPayFine.setBackground(new Color(255, 215, 0));
		btnListNotPayFine.setForeground(new Color(128, 0, 128));
		btnListNotPayFine.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListNotPayFine.setBounds(500, 22, 156, 39);
		panel.add(btnListNotPayFine);
		
		JLabel lblNewLabel_1 = new JLabel("Aranacak \u00DCye TC Kimlik Numaras\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(29, 25, 211, 32);
		panel.add(lblNewLabel_1);
		
		txtSearchMemberByTC = new JTextField();
		txtSearchMemberByTC.setForeground(new Color(0, 0, 128));
		txtSearchMemberByTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSearchMemberByTC.setBounds(250, 24, 143, 36);
		panel.add(txtSearchMemberByTC);
		txtSearchMemberByTC.setColumns(10);
		
		JButton btnSearchMemberTC = new JButton("Ara");
		btnSearchMemberTC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//üye arama
				
				String memberTC = txtSearchMemberByTC.getText();
				if(memberTC.length()!=0) {
					String sorgu = "SELECT * FROM FineNotPayMembers WHERE memberTC = '"+memberTC+"'";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = listTable(sorgu);
					try {
						while(rs.next()) {
							
							satirlar[0] = rs.getInt("fineNotPayID");
							
							
							satirlar[1] = rs.getString("memberName");
							
							satirlar[2] = rs.getString("memberSurname");
							satirlar[3] = rs.getString("memberTC");
							satirlar[4] = rs.getString("memberMail");
							satirlar[5] = rs.getString("memberPhoneNumber");
							satirlar[6] = rs.getString("memberAddress");
							
							satirlar[7] = rs.getString("bookTitle");
							
							satirlar[8] = rs.getInt("delayTimeDay");
							satirlar[9] = rs.getInt("fineAmount");
							satirlar[10] = rs.getString("fineDate");
							
							
							
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
		btnSearchMemberTC.setBackground(new Color(255, 215, 0));
		btnSearchMemberTC.setForeground(new Color(128, 0, 0));
		btnSearchMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchMemberTC.setBounds(401, 22, 89, 39);
		panel.add(btnSearchMemberTC);
		btnListNotPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				storedProcedureAddNotPayFine();
				listPayNotMember();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(194, 11, 368, 53);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ceza \u00D6demeyen \u00DCyeler Ve Ceza Bilgileri");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 348, 44);
		panel_1.add(lblNewLabel);
		
		JButton btnExitFineNotPay = new JButton("");
		btnExitFineNotPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		btnExitFineNotPay.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitFineNotPay.setBounds(671, 11, 53, 45);
		contentPane.add(btnExitFineNotPay);
		listPayNotMember();
	}
}
