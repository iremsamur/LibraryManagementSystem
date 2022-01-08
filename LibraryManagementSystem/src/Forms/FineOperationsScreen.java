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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;

public class FineOperationsScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtSearchMemberTC;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Ceza No","Adý","Soyadý","Üye TC","Mail Adresi","Telefon Numarasý","Kitap Adý","Ödünç Alma Tarihi","Teslim Tarihi","Asýl Teslim Tarihi","Ceza Tarihi","Gecikme Gün Sayýsý","Ceza Tutarý","Ceza Ödeme Durumu","Ekleyen Personel Adý","Ekleyen Personel Soyadý","Kayýt Tarihi","Son Güncellenme Tarihi"};
	Object[] satirlar = new Object[18];
	
	private JTextField txtMemberName;
	private JTextField txtMemberSurname;
	private JTextField txtMemberTC;
	private JTextField txtBookTitle;
	private JTextField txtBorrowedDate;
	private JTextField txtFineDate;
	private JTextField txtFineAmount;
	private JTextField txtDelayTimeDay;
	private JTextField txtActualReturnDate;
	private JTextField txtDeliveryDate;
	private int fineNumber = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FineOperationsScreen frame = new FineOperationsScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	public OracleResultSet showFines(String sorgu) {
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
	
	
	public void listFines() {
		String sorgu = "SELECT * "+
				"FROM Fine2,Borrowing2,LibraryMember,Book2,SystemUser "+
				"WHERE Fine2.borrowingID = Borrowing2.borrowingID AND Borrowing2.memberID = LibraryMember.memberID AND Borrowing2.bookNumber = Book2.bookNumber AND Fine2.addedStaffNumber = SystemUser.userNumber ORDER BY Fine2.fineID ASC";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showFines(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("fineID");
				
				
				satirlar[1] = rs.getString("memberName");
				
				satirlar[2] = rs.getString("memberSurname");
				satirlar[3] = rs.getString("TCIdentificationNumber");
				satirlar[4] = rs.getString("memberMail");
				satirlar[5] = rs.getString("memberPhoneNumber");
				
				satirlar[6] = rs.getString("bookTitle");
				
				satirlar[7] = rs.getDate("dateBorrowed").toString();
				satirlar[8] = rs.getDate("dateReturn").toString();
				satirlar[9] = rs.getDate("actualReturnDate").toString();
				satirlar[10] = rs.getString("fineDate");
				satirlar[11] = String.valueOf(rs.getInt("delayTimeDay"));
				satirlar[12] = String.valueOf(rs.getInt("fineAmount"));
				if(rs.getString("finePaymentStatus").equals("True")) {
					satirlar[13] = "Aktif";
				}
				else if(rs.getString("finePaymentStatus").equals("False")) {
					satirlar[13] = "Pasif";
				}
				satirlar[14] = rs.getString("name");
				satirlar[15] = rs.getString("surname");
				satirlar[16] = rs.getString("CreatDate");
				satirlar[17] = rs.getString("lastUpdateDate");
				
				
			
				
				
				
				
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
	public FineOperationsScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1102, 742);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(10, 423, 1017, 277);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 991, 192);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.setBackground(new Color(221, 160, 221));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//ceza silme
				
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				fineNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				String memberName = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				String memberSurname = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
				String memberTC = tblModel.getValueAt(table.getSelectedRow(), 3).toString();
				String bookTitle = tblModel.getValueAt(table.getSelectedRow(), 6).toString();
				String borrowingDate= tblModel.getValueAt(table.getSelectedRow(), 7).toString();
				String deliveryDate= tblModel.getValueAt(table.getSelectedRow(), 8).toString();
				String actualReturnDate= tblModel.getValueAt(table.getSelectedRow(), 9).toString();
				String delayTimeDay= tblModel.getValueAt(table.getSelectedRow(), 11).toString();
				String totalFine = tblModel.getValueAt(table.getSelectedRow(), 12).toString();
				String fineDate = tblModel.getValueAt(table.getSelectedRow(), 10).toString();
			
				txtMemberName.setText(memberName);
				txtMemberSurname.setText(memberSurname);
				txtMemberTC.setText(memberTC);
				txtBookTitle.setText(bookTitle);
				txtBorrowedDate.setText(borrowingDate);
				txtDeliveryDate.setText(deliveryDate);
				txtActualReturnDate.setText(actualReturnDate);
				txtDelayTimeDay.setText(delayTimeDay);
				txtFineAmount.setText(totalFine);
				txtFineDate.setText(fineDate);
				
				
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Ge\u00E7 Teslim Eden \u00DCyeler Ve Cezalar\u0131 Bilgileri");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(314, 0, 302, 34);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u00DCye TC Kimlik Numaras\u0131");
		lblNewLabel_1.setForeground(new Color(0, 0, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(175, 42, 164, 24);
		panel.add(lblNewLabel_1);
		
		txtSearchMemberTC = new JTextField();
		txtSearchMemberTC.setForeground(new Color(128, 0, 0));
		txtSearchMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSearchMemberTC.setBounds(374, 42, 196, 23);
		panel.add(txtSearchMemberTC);
		txtSearchMemberTC.setColumns(10);
		
		JButton btnSearch = new JButton("Ara");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Arama iþlemi
				String memberTC = txtSearchMemberTC.getText();
				
				String sorgu = "SELECT * "+
						"FROM Fine2,Borrowing2,LibraryMember,Book2,SystemUser "+
						"WHERE Fine2.borrowingID = Borrowing2.borrowingID AND Borrowing2.memberID = LibraryMember.memberID AND Borrowing2.bookNumber = Book2.bookNumber AND Fine2.addedStaffNumber = SystemUser.userNumber AND LibraryMember.TCIdentificationNumber = '"+memberTC+"' ORDER BY Fine2.fineID ASC";
						
				modelim.setColumnIdentifiers(kolonlar);
				
				modelim.setColumnCount(0);
				modelim.setRowCount(0);
				modelim.setColumnIdentifiers(kolonlar);
				OracleResultSet rs = showFines(sorgu);
				try {
					while(rs.next()) {
						satirlar[0] = rs.getInt("fineID");
						
						
						satirlar[1] = rs.getString("memberName");
						
						satirlar[2] = rs.getString("memberSurname");
						satirlar[3] = rs.getString("TCIdentificationNumber");
						satirlar[4] = rs.getDate("memberMail").toString();
						satirlar[5] = rs.getDate("memberPhoneNumber").toString();
						
						satirlar[6] = rs.getString("bookTitle");
						
						satirlar[7] = rs.getDate("dateBorrowed").toString();
						satirlar[8] = rs.getDate("dateReturn").toString();
						satirlar[9] = rs.getDate("actualReturnDate").toString();
						satirlar[10] = rs.getDate("fineDate").toString();
						satirlar[11] = rs.getDate("delayTimeDay").toString();
						satirlar[12] = rs.getDate("fineAmount").toString();
						satirlar[13] = rs.getDate("name").toString();
						satirlar[14] = rs.getDate("surname").toString();
						satirlar[15] = rs.getDate("CreatDate").toString();
						satirlar[16] = rs.getDate("lastUpdateDate").toString();
						
						
					
						
						
						
						
						modelim.addRow(satirlar);
						
					}
					table.setModel(modelim);
				}
				catch(Exception ex) {
					ex.printStackTrace();
					
				}
			}
		});
		btnSearch.setBackground(new Color(0, 128, 128));
		btnSearch.setForeground(new Color(128, 0, 128));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearch.setBounds(614, 42, 102, 24);
		panel.add(btnSearch);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(384, 11, 391, 43);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Ceza \u0130\u015Flemleri Paneli");
		lblNewLabel_3.setForeground(new Color(128, 0, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(102, 5, 175, 27);
		panel_2.add(lblNewLabel_3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_3.setBackground(new Color(255, 192, 203));
		panel_3.setBounds(10, 70, 726, 342);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Ceza Alan \u00DCye Ad\u0131");
		lblNewLabel_4.setBounds(10, 0, 155, 30);
		panel_3.add(lblNewLabel_4);
		lblNewLabel_4.setForeground(new Color(128, 0, 128));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_5 = new JLabel("Ceza Alan \u00DCye Soyad\u0131");
		lblNewLabel_5.setForeground(new Color(128, 0, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(10, 56, 155, 39);
		panel_3.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("TC Kimlik Numaras\u0131");
		lblNewLabel_5_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5_1.setBounds(10, 117, 155, 39);
		panel_3.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_6 = new JLabel("Kitap Ad\u0131");
		lblNewLabel_6.setForeground(new Color(128, 0, 128));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(10, 181, 155, 30);
		panel_3.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("\u00D6d\u00FCn\u00E7 Alma Tarihi");
		lblNewLabel_6_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1.setBounds(10, 256, 155, 30);
		panel_3.add(lblNewLabel_6_1);
		
		JLabel lblNewLabel_6_1_2 = new JLabel("As\u0131l Teslim Tarihi");
		lblNewLabel_6_1_2.setForeground(new Color(128, 0, 128));
		lblNewLabel_6_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_2.setBounds(277, 60, 155, 30);
		panel_3.add(lblNewLabel_6_1_2);
		
		JLabel lblNewLabel_6_1_2_1 = new JLabel("Gecikme G\u00FCn Say\u0131s\u0131");
		lblNewLabel_6_1_2_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_6_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_2_1.setBounds(277, 121, 155, 30);
		panel_3.add(lblNewLabel_6_1_2_1);
		
		JLabel lblNewLabel_6_1_2_2 = new JLabel("Ceza Tutar\u0131");
		lblNewLabel_6_1_2_2.setForeground(new Color(128, 0, 128));
		lblNewLabel_6_1_2_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_2_2.setBounds(277, 191, 155, 30);
		panel_3.add(lblNewLabel_6_1_2_2);
		
		JLabel lblNewLabel_6_1_2_2_1 = new JLabel("Ceza Tarihi");
		lblNewLabel_6_1_2_2_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_6_1_2_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_2_2_1.setBounds(277, 256, 155, 30);
		panel_3.add(lblNewLabel_6_1_2_2_1);
		
		txtMemberName = new JTextField();
		txtMemberName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberName.setForeground(new Color(0, 0, 128));
		txtMemberName.setEditable(false);
		txtMemberName.setBounds(10, 27, 155, 30);
		panel_3.add(txtMemberName);
		txtMemberName.setColumns(10);
		
		txtMemberSurname = new JTextField();
		txtMemberSurname.setForeground(new Color(0, 0, 128));
		txtMemberSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberSurname.setEditable(false);
		txtMemberSurname.setColumns(10);
		txtMemberSurname.setBounds(10, 89, 155, 30);
		panel_3.add(txtMemberSurname);
		
		txtMemberTC = new JTextField();
		txtMemberTC.setForeground(new Color(0, 0, 128));
		txtMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberTC.setEditable(false);
		txtMemberTC.setColumns(10);
		txtMemberTC.setBounds(10, 151, 155, 30);
		panel_3.add(txtMemberTC);
		
		txtBookTitle = new JTextField();
		txtBookTitle.setForeground(new Color(0, 0, 128));
		txtBookTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookTitle.setEditable(false);
		txtBookTitle.setColumns(10);
		txtBookTitle.setBounds(10, 208, 155, 30);
		panel_3.add(txtBookTitle);
		
		txtBorrowedDate = new JTextField();
		txtBorrowedDate.setForeground(new Color(0, 0, 128));
		txtBorrowedDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBorrowedDate.setEditable(false);
		txtBorrowedDate.setColumns(10);
		txtBorrowedDate.setBounds(10, 297, 155, 30);
		panel_3.add(txtBorrowedDate);
		
		JLabel lblNewLabel_6_1_1_1 = new JLabel("Teslim Etti\u011Fi Tarih");
		lblNewLabel_6_1_1_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_6_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_1_1.setBounds(277, 6, 155, 30);
		panel_3.add(lblNewLabel_6_1_1_1);
		
		txtFineDate = new JTextField();
		txtFineDate.setEditable(false);
		txtFineDate.setForeground(new Color(0, 0, 128));
		txtFineDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtFineDate.setBounds(463, 261, 182, 25);
		panel_3.add(txtFineDate);
		txtFineDate.setColumns(10);
		
		txtFineAmount = new JTextField();
		txtFineAmount.setForeground(new Color(0, 0, 128));
		txtFineAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtFineAmount.setEditable(false);
		txtFineAmount.setBounds(463, 191, 182, 30);
		panel_3.add(txtFineAmount);
		txtFineAmount.setColumns(10);
		
		txtDelayTimeDay = new JTextField();
		txtDelayTimeDay.setForeground(new Color(0, 0, 128));
		txtDelayTimeDay.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtDelayTimeDay.setEditable(false);
		txtDelayTimeDay.setBounds(463, 126, 182, 30);
		panel_3.add(txtDelayTimeDay);
		txtDelayTimeDay.setColumns(10);
		
		txtActualReturnDate = new JTextField();
		txtActualReturnDate.setForeground(new Color(0, 0, 128));
		txtActualReturnDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtActualReturnDate.setEditable(false);
		txtActualReturnDate.setBounds(463, 65, 182, 30);
		panel_3.add(txtActualReturnDate);
		txtActualReturnDate.setColumns(10);
		
		txtDeliveryDate = new JTextField();
		txtDeliveryDate.setForeground(new Color(0, 0, 128));
		txtDeliveryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtDeliveryDate.setEditable(false);
		txtDeliveryDate.setBounds(463, 11, 182, 30);
		panel_3.add(txtDeliveryDate);
		txtDeliveryDate.setColumns(10);
		
		JButton btnExitFine = new JButton("");
		btnExitFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitFine.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitFine.setBounds(1025, 0, 61, 49);
		contentPane.add(btnExitFine);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(756, 70, 270, 342);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnDeleteFine = new JButton("Sil");
		btnDeleteFine.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\WhatsApp Image 2021-12-29 at 21.56.23.jpeg"));
		btnDeleteFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//silme
				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Pasif olan cezalarý silmek istediðinizden emin misiniz?","Ceza Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM Fine2 WHERE finePaymentStatus = 'False'";
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listFines();
							txtMemberName.setText("");
							txtMemberSurname.setText("");
							txtMemberTC.setText("");
							txtBookTitle.setText("");
							txtBorrowedDate.setText("");
							txtDeliveryDate.setText("");
							txtActualReturnDate.setText("");
							txtDelayTimeDay.setText("");
							txtFineAmount.setText("");
							txtFineDate.setText("");
						
							
							
							
							
							
							
						}
						else {
							JOptionPane.showMessageDialog(null, "Silme iþlemi gerçekleþtirilemedi.");
							
						}
						
						
						
					}catch(Exception ex) {
						ex.printStackTrace();
						
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Silme iþlemi iptal edildi.");
				}
			}
		});
		btnDeleteFine.setBackground(new Color(152, 251, 152));
		btnDeleteFine.setForeground(new Color(128, 0, 0));
		btnDeleteFine.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeleteFine.setBounds(25, 149, 212, 69);
		panel_1.add(btnDeleteFine);
		
		JButton btnClearFineFields = new JButton("Temizle");
		btnClearFineFields.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnClearFineFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//temizle
				
				
				txtMemberName.setText("");
				txtMemberSurname.setText("");
				txtMemberTC.setText("");
				txtBookTitle.setText("");
				txtBorrowedDate.setText("");
				txtDeliveryDate.setText("");
				txtActualReturnDate.setText("");
				txtDelayTimeDay.setText("");
				txtFineAmount.setText("");
				txtFineDate.setText("");
			}
		});
		btnClearFineFields.setBackground(new Color(152, 251, 152));
		btnClearFineFields.setForeground(new Color(128, 0, 0));
		btnClearFineFields.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClearFineFields.setBounds(25, 253, 212, 78);
		panel_1.add(btnClearFineFields);
		
		JLabel lblNewLabel_2 = new JLabel("Ceza \u00D6deme Onay\u0131");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(68, 11, 153, 14);
		panel_1.add(lblNewLabel_2);
		
		JCheckBox chckBoxFinePaymentConfirm = new JCheckBox("Onayla");
		chckBoxFinePaymentConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckBoxFinePaymentConfirm.setForeground(new Color(128, 0, 128));
		chckBoxFinePaymentConfirm.setBounds(79, 42, 97, 23);
		panel_1.add(chckBoxFinePaymentConfirm);
		
		JButton btnPayFine = new JButton("Ceza \u00D6deme");
		btnPayFine.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\WhatsApp Image 2021-12-29 at 21.53.00.jpeg"));
		btnPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				OraclePreparedStatement pst = null;
				//OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				//ceza ödeme onayý
				
				
				
if(fineNumber>=1) {
					
					//iade iþlemi burada yazýlacak
					
					if(chckBoxFinePaymentConfirm.isSelected()==true) {
						try {
							
							String queryUpdate = "UPDATE Fine2 SET finePaymentStatus = 'False' WHERE fineID = "+fineNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Ceza Ödeme baþarýyla yapýldý.");
								listFines();
								
								
								
								
								
								
								
								
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Ceza ödeme yapýlamadý!!");
								
							}
							
							
							
						}catch(Exception ex) {
							ex.printStackTrace();
							
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Ceza ödeme yapýlamadý!!");
					}
					
					
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen geç getirme cezasý ödenecek olan üyeyi tablodan seçiniz!!");
				}
				
			}
		});
		btnPayFine.setBackground(new Color(152, 251, 152));
		btnPayFine.setForeground(new Color(128, 0, 0));
		btnPayFine.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPayFine.setBounds(25, 84, 212, 41);
		panel_1.add(btnPayFine);
		
		listFines();
	}
}
