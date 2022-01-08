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
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class BookReturnedOperationsScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtSearchBorrowerMemberTC;
	
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Ödünç No","Üye Adý","Üye Soyadý","Üye Tc Kimlik No","Kitap No","Kitap Adý","Ekleyen Personel Adý","Ekleyen Personel Soyadý","Ödünç Alma Tarihi","Teslim Ettiði Tarih","Asýl Teslim Tarihi","Teslim Durumu","Kayýt Tarihi","Son Güncellenme Tarihi"};
	Object[] satirlar = new Object[14];
	private JTextField txtMemberName;
	private JTextField txtMemberSurname;
	private JTextField txtMemberTC;
	private JTextField txtBookNumber;
	private JTextField txtBookTitle;
	private JTextField txtBookBorrowedDate;
	private JTextField txtDeliveryDate;
	private int borrowingNo = 0;
	private int bookNo = 0;
	private String actualReturnDate = null;
	private JLabel lblBookDeliveryStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookReturnedOperationsScreen frame = new BookReturnedOperationsScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	//find delay time
	
	public OracleResultSet findDelayTimeDay(int borrowingID) {
		//Gecikme Zamaný
		
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			//String sorgu = "SELECT COUNT(Borrowing2.borrowingID) FROM Borrowing2 WHERE Borrowing2.memberID = "+memberID;
			String sorgu = "SELECT trunc(dateReturn-actualReturnDate) FROM Borrowing2 WHERE Borrowing2.borrowingID = "+borrowingID;;
			pst = (OraclePreparedStatement) conn.prepareStatement(sorgu);
		
			rs = (OracleResultSet) pst.executeQuery();
			return rs;
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}
		
	}
	public boolean fineAdd(int selectedBorrowingNumber) {
		
		
		
		OracleResultSet rs = findDelayTimeDay(selectedBorrowingNumber);
		LocalDate fineDate = LocalDate.now();/*Cezanýn yazýldýðý tarih*/
		int addedStaffNo = StaffScreenForm.loggedStaffID;
		int finePriceForADayDelayTime = 10;
		if(selectedBorrowingNumber!=0) {
			
			
			try {
				int dayResult=0;
				while(rs.next()) {
					dayResult = rs.getInt(1);
					
				}
				int totalFineAmount = finePriceForADayDelayTime*dayResult; // toplam ceza tutarý 1 kitap için
				
				
				
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String creatDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				Connection conn = null;
				OraclePreparedStatement pst = null;
				String finePaymentStatus = "True";
				//OraclePreparedStatement pst2 = null;
			
				OracleResultSet rs2 = null;
				conn = ConnectionClass.dbConnect();
				String query2 = "INSERT INTO Fine2(fineID,borrowingID,fineDate,delayTimeDay,fineAmount,addedStaffNumber,creatDate,lastUpdateDate,finePaymentStatus) VALUES(add_fine2.nextval,"+selectedBorrowingNumber+",TO_DATE('"+fineDate+"','YYYY-MM-DD'),"+dayResult+","+totalFineAmount+","+addedStaffNo+",'"+creatDate+"','"+lastUpdateDate+"','"+finePaymentStatus+"')";
				pst = (OraclePreparedStatement) conn.prepareStatement(query2);
				int rowValue = pst.executeUpdate();
				if(rowValue>0) {
					//JOptionPane.showMessageDialog(null, "Ceza eklendi!!");
					return true;
					
					
					
					
					
				
					
				
					
					
					
					
					
					
					
					
					
				}
				else {
					//JOptionPane.showMessageDialog(null, "Ceza Eklenemedi!!");
					return false;
					
				}
				
				
				
			}
			catch(Exception ex) {
				ex.printStackTrace();
				return false;
				
			}
			
			
			
			
		}
		else {
			return false;
		}
		
	}
	
	public boolean updateBookStatus(int bookID) {
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OraclePreparedStatement pst2 = null;
		
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			String queryUpdate = "UPDATE Book2 SET bookStatus = 'True' WHERE bookNumber = "+bookID;
			pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
			int rowValue = pst.executeUpdate();
			if(rowValue>0) {
				return true;
				
				
				
				
				
				
				
			}
			else {
				return false;
				
			}
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
			
		}
		
	}
	
	public OracleResultSet showBorrowingInformations(String sorgu) {
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
	
	
	public void listBorrowingInformations() {
		String sorgu = "SELECT * "+
				"FROM Borrowing2,LibraryMember,Book2,SystemUser "+
				"WHERE Borrowing2.MemberID = LibraryMember.memberID AND Book2.bookNumber = Borrowing2.bookNumber AND Borrowing2.addedStaffNumber = SystemUser.userNumber AND borrowingStatus = 'True' ORDER BY Borrowing2.borrowingID ASC";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showBorrowingInformations(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("borrowingID");
				satirlar[1] = rs.getString("memberName");
				satirlar[2] = rs.getString("memberSurname");
				satirlar[3] = rs.getString("TCIdentificationNumber");
				satirlar[4] = rs.getInt("bookNumber");
				
				
				satirlar[5] = rs.getString("bookTitle");
				satirlar[6] = rs.getString("name");
				satirlar[7] = rs.getString("surname");
				
				satirlar[8] = rs.getString("dateBorrowed");
				satirlar[9] = rs.getString("dateReturn");
				satirlar[10] = rs.getString("actualReturnDate");
				if(rs.getString("borrowingStatus").equals("True")) {
					satirlar[11] = "Aktif";
				}
				else if(rs.getString("bookStatus").equals("False")) {
					satirlar[11] = "Pasif";
				}
				
				

				
				satirlar[12] = rs.getString("creatDate");
				satirlar[13] = rs.getString("lastUpdateDate");
				
				
				
				
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
	public BookReturnedOperationsScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1108, 736);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(10, 367, 1050, 328);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 1030, 230);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//ödünç bilgileri
				
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				borrowingNo = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				String memberName = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				String memberSurname = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
				String memberTC = tblModel.getValueAt(table.getSelectedRow(), 3).toString();
				bookNo = (int)tblModel.getValueAt(table.getSelectedRow(), 4);
				String bookTitle= tblModel.getValueAt(table.getSelectedRow(), 5).toString();
				String borrowedDate = tblModel.getValueAt(table.getSelectedRow(), 8).toString();
				actualReturnDate= tblModel.getValueAt(table.getSelectedRow(), 10).toString();
				
				
			
			
				txtMemberName.setText(memberName);
				//txtBookReleaseYear.setText(bookReleaseDate);
				txtMemberSurname.setText(memberSurname);
				txtMemberTC.setText(memberTC);
				txtBookNumber.setText(String.valueOf(bookNo));
				txtBookTitle.setText(bookTitle);
				txtBookBorrowedDate.setText(borrowedDate);
				txtDeliveryDate.setText(actualReturnDate);
				
				
				
			
				
				
			}
		});
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.setBackground(new Color(221, 160, 221));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Aktif \u00D6d\u00FCn\u00E7ler Listesi");
		lblNewLabel.setForeground(new Color(255, 0, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(362, 0, 299, 38);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u00D6d\u00FCn\u00E7 Alan Aranacak \u00DCyenin TC Kimlik Numaras\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(95, 38, 299, 34);
		panel.add(lblNewLabel_1);
		
		txtSearchBorrowerMemberTC = new JTextField();
		txtSearchBorrowerMemberTC.setForeground(new Color(0, 0, 128));
		txtSearchBorrowerMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSearchBorrowerMemberTC.setBounds(430, 38, 216, 33);
		panel.add(txtSearchBorrowerMemberTC);
		txtSearchBorrowerMemberTC.setColumns(10);
		
		JButton btnNewButton = new JButton("Ara");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//arama
				
				String memberTC = txtSearchBorrowerMemberTC.getText();
				if(memberTC.length()!=0) {
					String sorgu = "SELECT * "+
							"FROM Borrowing2,LibraryMember,Book2,SystemUser "+
							"WHERE Borrowing2.MemberID = LibraryMember.memberID AND Book2.bookNumber = Borrowing2.bookNumber AND Borrowing2.addedStaffNumber = SystemUser.userNumber AND borrowingStatus = 'True' AND LibraryMember.TCIdentificationNumber = '"+memberTC+"' ORDER BY Borrowing2.borrowingID ASC";
							
					
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showBorrowingInformations(sorgu);
					try {
						while(rs.next()) {
							satirlar[0] = rs.getInt("borrowingID");
							satirlar[1] = rs.getString("memberName");
							satirlar[2] = rs.getString("memberSurname");
							satirlar[3] = rs.getString("TCIdentificationNumber");
							satirlar[4] = rs.getInt("bookNumber");
							
							
							satirlar[5] = rs.getString("bookTitle");
							satirlar[6] = rs.getString("name");
							satirlar[7] = rs.getString("surname");
							
							satirlar[8] = rs.getString("dateBorrowed");
							satirlar[9] = rs.getString("dateReturn");
							satirlar[10] = rs.getString("actualReturnDate");
							if(rs.getString("borrowingStatus").equals("True")) {
								satirlar[11] = "Aktif";
							}
							else if(rs.getString("bookStatus").equals("False")) {
								satirlar[11] = "Pasif";
							}
							
							

							
							satirlar[12] = rs.getString("creatDate");
							satirlar[13] = rs.getString("lastUpdateDate");
							
							
							
							
							
							
							
							
							
							modelim.addRow(satirlar);
							
							
						}
						table.setModel(modelim);
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Aranacak kitap türü adý alaný boþ býrakýlamaz!!! Lütfen aranacak kitap türünün adýný giriniz!!");
				}
			}
		});
		btnNewButton.setBackground(new Color(0, 128, 128));
		btnNewButton.setForeground(new Color(128, 0, 128));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(672, 38, 89, 34);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(323, 11, 371, 47);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Kitap \u0130ade \u0130\u015Flemleri Y\u00F6netim Paneli");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(31, 11, 330, 25);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_2.setBackground(new Color(255, 192, 203));
		panel_2.setBounds(10, 69, 632, 287);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("\u00DCye Ad\u0131");
		lblNewLabel_3.setForeground(new Color(128, 0, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(29, 56, 74, 22);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u00DCye Soyad\u0131");
		lblNewLabel_4.setForeground(new Color(128, 0, 0));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(29, 99, 74, 22);
		panel_2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("TC Kimlik No");
		lblNewLabel_5.setForeground(new Color(128, 0, 0));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(29, 145, 88, 27);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Kitap Ad\u0131");
		lblNewLabel_6.setForeground(new Color(128, 0, 0));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(29, 241, 88, 22);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Kitap No");
		lblNewLabel_7.setForeground(new Color(128, 0, 0));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(29, 200, 88, 14);
		panel_2.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("\u00D6d\u00FCn\u00E7 Alma Tarihi");
		lblNewLabel_8.setForeground(new Color(128, 0, 0));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setBounds(395, 57, 110, 20);
		panel_2.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Teslim Etmesi Gereken Tarih");
		lblNewLabel_9.setForeground(new Color(128, 0, 0));
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_9.setBounds(391, 148, 181, 22);
		panel_2.add(lblNewLabel_9);
		
		txtMemberName = new JTextField();
		txtMemberName.setBackground(new Color(255, 255, 255));
		txtMemberName.setEditable(false);
		txtMemberName.setForeground(new Color(0, 0, 128));
		txtMemberName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberName.setBounds(128, 58, 160, 20);
		panel_2.add(txtMemberName);
		txtMemberName.setColumns(10);
		
		txtMemberSurname = new JTextField();
		txtMemberSurname.setForeground(new Color(0, 0, 128));
		txtMemberSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberSurname.setEditable(false);
		txtMemberSurname.setColumns(10);
		txtMemberSurname.setBackground(new Color(255, 255, 255));
		txtMemberSurname.setBounds(128, 101, 160, 20);
		panel_2.add(txtMemberSurname);
		
		txtMemberTC = new JTextField();
		txtMemberTC.setForeground(new Color(0, 0, 128));
		txtMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberTC.setEditable(false);
		txtMemberTC.setColumns(10);
		txtMemberTC.setBackground(new Color(255, 255, 255));
		txtMemberTC.setBounds(127, 149, 161, 20);
		panel_2.add(txtMemberTC);
		
		txtBookNumber = new JTextField();
		txtBookNumber.setForeground(new Color(0, 0, 128));
		txtBookNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookNumber.setEditable(false);
		txtBookNumber.setColumns(10);
		txtBookNumber.setBackground(new Color(255, 255, 255));
		txtBookNumber.setBounds(127, 198, 161, 20);
		panel_2.add(txtBookNumber);
		
		txtBookTitle = new JTextField();
		txtBookTitle.setForeground(new Color(0, 0, 128));
		txtBookTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookTitle.setEditable(false);
		txtBookTitle.setColumns(10);
		txtBookTitle.setBackground(new Color(255, 255, 255));
		txtBookTitle.setBounds(127, 243, 161, 20);
		panel_2.add(txtBookTitle);
		
		txtBookBorrowedDate = new JTextField();
		txtBookBorrowedDate.setForeground(new Color(0, 0, 128));
		txtBookBorrowedDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookBorrowedDate.setEditable(false);
		txtBookBorrowedDate.setColumns(10);
		txtBookBorrowedDate.setBackground(new Color(255, 255, 255));
		txtBookBorrowedDate.setBounds(395, 101, 177, 20);
		panel_2.add(txtBookBorrowedDate);
		
		txtDeliveryDate = new JTextField();
		txtDeliveryDate.setForeground(new Color(0, 0, 128));
		txtDeliveryDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtDeliveryDate.setEditable(false);
		txtDeliveryDate.setColumns(10);
		txtDeliveryDate.setBackground(new Color(255, 255, 255));
		txtDeliveryDate.setBounds(395, 198, 177, 20);
		panel_2.add(txtDeliveryDate);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_3.setBackground(new Color(255, 192, 203));
		panel_3.setBounds(678, 69, 382, 287);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_10 = new JLabel("Teslim Etme Onay\u0131");
		lblNewLabel_10.setForeground(new Color(128, 0, 0));
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setBounds(75, 11, 223, 36);
		panel_3.add(lblNewLabel_10);
		
		JCheckBox chckDeliveryConfirm = new JCheckBox("Onayla");
		chckDeliveryConfirm.setForeground(new Color(0, 0, 128));
		chckDeliveryConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckDeliveryConfirm.setBounds(75, 59, 268, 48);
		panel_3.add(chckDeliveryConfirm);
		
		JButton btnConfirm = new JButton("Teslim Et");
		btnConfirm.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\return-book-flat-modern-icon-design-vector-30109817_50x50.jpg"));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kitap teslim etme
				Connection conn = null;
				OraclePreparedStatement pst = null;
				//OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				LocalDate deliveryDateConvert = LocalDate.now();/*kitabýn verileceði üyenin belirttiði tarih*/
				LocalDateTime localDateTime = LocalDateTime.now();
				
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String deliveryDate = deliveryDateConvert.toString()+" 00:00:00.0";
				
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				System.out.println("Teslim Ettiði Tarih : "+deliveryDate);
				System.out.println("Gerçek Teslim Tarihi : "+actualReturnDate);
				
				
				if(txtMemberName.getText().length()!=0 && txtMemberSurname.getText().length()!=0 && txtMemberTC.getText().length()!=0 && txtBookNumber.getText().length()!=0 && txtBookTitle.getText().length()!=0 && txtBookBorrowedDate.getText().length()!=0 && txtBookBorrowedDate.getText().length()!=0) {
					
					//iade iþlemi burada yazýlacak
					
					if(chckDeliveryConfirm.isSelected()==true) {
						try {
							
							String queryUpdate = "UPDATE Borrowing2 SET borrowingStatus = 'False',dateReturn = TO_DATE('"+deliveryDateConvert+"','YYYY-MM-DD'), lastUpdateDate = '"+lastUpdateDate+"' WHERE borrowingID = "+borrowingNo;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								
								if(updateBookStatus(bookNo)) {
									JOptionPane.showMessageDialog(null, "Teslim iþlemi onaylandý.");
									//ceza ekleme metodu çýksa
									listBorrowingInformations();
									
									if(deliveryDate.compareTo(actualReturnDate)==1) {
										//deliveryDate.equals(actualReturnDate)==false
										//eþit deðilse
										JOptionPane.showMessageDialog(null, "Kitap iade iþlemi gerçekleþtirilen üye asýl teslim etmesi gereken tarihten daha geç getirmiþtir!!!!");
										lblBookDeliveryStatus.setText("Teslim Durumu : Zamanýnda Teslim Edilmedi.");
										boolean result = fineAdd(borrowingNo);
										if(result == true) {
											JOptionPane.showMessageDialog(null, "Geç teslim edildiði için ceza yazýldý.");
											
										}
										
										
										
										
									}
									else {
										//eþitse
										lblBookDeliveryStatus.setText("Teslim Durumu : Zamanýnda teslim edildi.");
									}
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Teslim iþlemi onaylanmadý.");
								}
								
								
								
								
								
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Teslim iþlemi onaylanmadý.");
								
							}
							
							
							
						}catch(Exception ex) {
							ex.printStackTrace();
							
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Teslim iþlemi onaylanmadý.");
					}
					
					
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen kitap iade iþlemi yapýlacak olan üyeyi tablodan seçiniz!!");
				}
			}
		});
		btnConfirm.setBackground(new Color(175, 238, 238));
		btnConfirm.setForeground(new Color(128, 0, 0));
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConfirm.setBounds(90, 165, 208, 42);
		panel_3.add(btnConfirm);
		
		lblBookDeliveryStatus = new JLabel("");
		lblBookDeliveryStatus.setForeground(new Color(128, 0, 0));
		lblBookDeliveryStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblBookDeliveryStatus.setBounds(66, 192, 277, 72);
		panel_3.add(lblBookDeliveryStatus);
		
		JButton btnExitBookReturned = new JButton("");
		btnExitBookReturned.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitBookReturned.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitBookReturned.setBackground(new Color(0, 0, 128));
		btnExitBookReturned.setBounds(1031, 0, 61, 44);
		contentPane.add(btnExitBookReturned);
		listBorrowingInformations();
	}
}
