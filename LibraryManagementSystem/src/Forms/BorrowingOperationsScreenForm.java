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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Date;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class BorrowingOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtBorrowingSearch;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Ödünç No","Üye TC Kimlik Numarasý","Üye Adý","Üye Soyadý","Mail Adresi","Telefon Numarasý","Kitap Adý","Kitap Açýklamasý","Kitap Türü","Yazar Adý","Yazar Soyadý","Yayýnevi Adý","Ödünç Veren Personel Adý","Ödünç Veren Personel Soyadý","Ödünç Alma Tarihi","Belirtilen Teslim Tarihi","Getirilen Teslim Tarihi","Ödünç Alma Durumu","Kayýt Tarihi","Son Güncellenme Tarihi"};
	Object[] satirlar = new Object[20];
	private JTextField txtSelectedBookNumber;
	private JTable table_1;
	private JTable table_2;
	private JTextField txtSelectedMemberNo;
	DefaultTableModel modelim2 = new DefaultTableModel();
	Object[] kolonlar2 = {"Kitap No","Kitap Adý","Kitap Sayfa Sayýsý","Kitap Basým Yýlý","Kitap Açýklamasý","Kitap Türü","Yazar Adý","Yazar Soyadý","Yayýnevi Adý","Yayýnevi Adresi"};
	Object[] satirlar2= new Object[11];
	
	DefaultTableModel modelim3 = new DefaultTableModel();
	Object[] kolonlar3 = {"Üye No","Üye Adý","Üye Soyadý","TC Kimlik Numarasý","Doðum Tarihi","Mail Adresi","Telefon Numarasý","Cinsiyet","Adres"};
	Object[] satirlar3= new Object[9];
	private int selectedBookNumber = 0;
	private int selectedMemberNumber = 0;
	private int selectedBorrowingNumber = 0;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BorrowingOperationsScreenForm frame = new BorrowingOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	
	
	public OracleResultSet showBorrowings(String sorgu) {
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
	
	
	
	
	public void listBorrowings() {
		String sorgu = "SELECT * "+
				"FROM Borrowing2,LibraryMember,Book2,BookType2,Author2,Publisher2,SystemUser "+
				"WHERE Borrowing2.memberID = LibraryMember.memberID AND Borrowing2.bookNumber = Book2.bookNumber AND Book2.bookTypeNumber = BookType2.bookTypeNumber AND Book2.authorNumber = Author2.authorNumber AND Book2.publisherNumber = Publisher2.publisherNumber AND Borrowing2.addedStaffNumber = SystemUser.userNumber ORDER BY Borrowing2.borrowingID ASC";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showBorrowings(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("borrowingID");
				satirlar[1] = rs.getString("TCIdentificationNumber");
				
				satirlar[2] = rs.getString("memberName");
				
				satirlar[3] = rs.getString("memberSurname");
				satirlar[4] = rs.getString("memberMail");
				satirlar[5] = rs.getString("memberPhoneNumber");
				satirlar[6] = rs.getString("bookTitle");
				satirlar[7] = rs.getString("bookDescription");
				satirlar[8] = rs.getString("bookTypeTitle");
				satirlar[9] = rs.getString("authorName");
				satirlar[10] = rs.getString("authorSurname");
				satirlar[11] = rs.getString("publisherTitle");
				
				satirlar[12] = rs.getString("name");
				satirlar[13] = rs.getString("surname");
				satirlar[14] = rs.getDate("dateBorrowed").toString();
				satirlar[15] = rs.getDate("actualReturnDate").toString();
				if(rs.getDate("dateReturn")!=null) {
					satirlar[16] = rs.getDate("dateReturn").toString();
				}
				else if(rs.getDate("dateReturn")==null) {
					satirlar[16] = "Henüz Teslim Edilmedi";
				}
				
				if(rs.getString("borrowingStatus").equals("True")) {
					satirlar[17] = "Aktif";
				}
				else if(rs.getString("borrowingStatus").equals("False")) {
					satirlar[17] = "Pasif";
				}
				
				
				
				
				
				
				
				
				
				
				
				
				satirlar[18] = rs.getString("creatDate");
				satirlar[19] = rs.getString("lastUpdateDate");
				
				
				
				
				modelim.addRow(satirlar);
				
			}
			table.setModel(modelim);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}

	
	
	
	public OracleResultSet showBooks(String sorgu) {
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
	public OracleResultSet bookNumberOfMember(int memberID,LocalDate borrowedDate) {
		//Bir kitap aldýðýnda en fazla 3 kitap alabilsin
		int bookNumber = 0;
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			//String sorgu = "SELECT COUNT(Borrowing2.borrowingID) FROM Borrowing2 WHERE Borrowing2.memberID = "+memberID;
			String sorgu = "SELECT COUNT(Borrowing2.borrowingID) FROM Borrowing2 WHERE Borrowing2.memberID = "+memberID+" AND dateBorrowed = TO_DATE('"+borrowedDate+"','YYYY-MM-DD') AND borrowingStatus = 'True'";
			pst = (OraclePreparedStatement) conn.prepareStatement(sorgu);
		
			rs = (OracleResultSet) pst.executeQuery();
			return rs;
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return null;
			
		}
		
	}
	
	
	public boolean controlMemberBorrowingStatus(int memberID) {
		//cezasýnýn ödeme durumu aktif olan kitap alamasýn
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			//String sorgu = "SELECT COUNT(Borrowing2.borrowingID) FROM Borrowing2 WHERE Borrowing2.memberID = "+memberID;
			String sorgu = "SELECT * "+
					"FROM Fine2,Borrowing2,LibraryMember,Book2,SystemUser "+
					"WHERE Fine2.borrowingID = Borrowing2.borrowingID AND Borrowing2.memberID = LibraryMember.memberID AND Borrowing2.bookNumber = Book2.bookNumber AND Fine2.addedStaffNumber = SystemUser.userNumber AND LibraryMember.memberID = "+memberID+" AND Fine2.finePaymentStatus = 'True' ORDER BY Fine2.fineID ASC";
			pst = (OraclePreparedStatement) conn.prepareStatement(sorgu);
		
			rs = (OracleResultSet) pst.executeQuery();
			if(rs.next()==true) {
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
	
	
	public void listBooks() {
		
		
		String sorgu = "SELECT * "+
				"FROM Book2,BookType2,Author2,Publisher2,SystemUser "+
				"WHERE Book2.bookTypeNumber = BookType2.bookTypeNumber AND Book2.authorNumber = Author2.authorNumber AND Book2.publisherNumber = Publisher2.publisherNumber AND Book2.addedAdminNumber = SystemUser.userNumber AND Book2.bookStatus = 'True' ORDER BY Book2.bookNumber ASC";
				
		modelim2.setColumnIdentifiers(kolonlar2);
		
		modelim2.setColumnCount(0);
		modelim2.setRowCount(0);
		modelim2.setColumnIdentifiers(kolonlar2);
		OracleResultSet rs = showBooks(sorgu);
		try {
			while(rs.next()) {
				satirlar2[0] = rs.getInt("bookNumber");
				satirlar2[1] = rs.getString("bookTitle");
				
				satirlar2[2] = rs.getString("bookPageNumber");
				
				satirlar2[3] = rs.getString("bookReleaseDate");
				
				satirlar2[4] = rs.getString("bookDescription");
				
				satirlar2[5] = rs.getString("bookTypeTitle");
				
				satirlar2[6] = rs.getString("authorName");
				satirlar2[7] = rs.getString("authorSurname");
				
				satirlar2[8] = rs.getString("publisherTitle");
				satirlar2[9] = rs.getString("publisherAddress");
				
				
				
				
				
				
				
				
				
				
				modelim2.addRow(satirlar2);
				
			}
			table_1.setModel(modelim2);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	public OracleResultSet showMembers(String sorgu) {
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
	
public void listMembers() {
		
		
	String sorgu = "SELECT * FROM LibraryMember,SystemUser WHERE SystemUser.userNumber = LibraryMember.addedStaffNumber ORDER BY LibraryMember.memberID ASC";
	
	modelim3.setColumnIdentifiers(kolonlar3);
	
	modelim3.setColumnCount(0);
	modelim3.setRowCount(0);
	modelim3.setColumnIdentifiers(kolonlar3);
	OracleResultSet rs = showMembers(sorgu);
	try {
		while(rs.next()) {
			satirlar3[0] = rs.getInt("memberID");
			satirlar3[1] = rs.getString("memberName");
			satirlar3[2] = rs.getString("memberSurname");
			satirlar3[3] = rs.getString("TCIdentificationNumber");
			satirlar3[4] = rs.getString("memberBirthDate");
			satirlar3[5] = rs.getString("memberMail");
			satirlar3[6] = rs.getString("memberPhoneNumber");
			satirlar3[7] = rs.getString("memberGender");
			satirlar3[8] = rs.getString("memberAddress");
			
			
			
			
			
			
			modelim3.addRow(satirlar3);
			
		}
		table_2.setModel(modelim3);
	}
	catch(Exception ex) {
		ex.printStackTrace();
		
	}
	}

public boolean updateBookStatus(int bookID) {
	Connection conn = null;
	OraclePreparedStatement pst = null;
	OraclePreparedStatement pst2 = null;
	
	OracleResultSet rs = null;
	conn = ConnectionClass.dbConnect();
	try {
		
		String queryUpdate = "UPDATE Book2 SET bookStatus = 'False' WHERE bookNumber = "+bookID;
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



	/**
	 * Create the frame.
	 */
	public BorrowingOperationsScreenForm() {
		setBackground(new Color(152, 251, 152));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1386, 741);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(28, 406, 1315, 286);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 1291, 194);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(221, 160, 221));
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//ödünç alma silme
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				selectedBorrowingNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("\u00D6d\u00FCn\u00E7 Verilenler Bilgileri");
		lblNewLabel.setForeground(new Color(0, 128, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(568, 10, 174, 23);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Aranacak \u00DCyenin TC Kimlik Numaras\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(325, 50, 220, 23);
		panel.add(lblNewLabel_1);
		
		txtBorrowingSearch = new JTextField();
		txtBorrowingSearch.setForeground(new Color(0, 0, 128));
		txtBorrowingSearch.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtBorrowingSearch.setBounds(555, 44, 174, 34);
		panel.add(txtBorrowingSearch);
		txtBorrowingSearch.setColumns(10);
		
		JButton btnSearchBorrowing = new JButton("Ara");
		btnSearchBorrowing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//aramaya göre listeleme
				String borrowingSearchByTC = txtBorrowingSearch.getText();
				
				String sorgu = "SELECT * "+
						"FROM Borrowing2,LibraryMember,Book2,BookType2,Author2,Publisher2,SystemUser "+
						"WHERE Borrowing2.memberID = LibraryMember.memberID AND Borrowing2.bookNumber = Book2.bookNumber "
						+ "AND Book2.bookTypeNumber = BookType2.bookTypeNumber AND Book2.authorNumber = Author2.authorNumber "
						+ "AND Book2.publisherNumber = Publisher2.publisherNumber AND Borrowing2.addedStaffNumber = SystemUser.userNumber "
						+ "AND LibraryMember.TCIdentificationNumber = '"+borrowingSearchByTC+"'ORDER BY Borrowing2.borrowingID ASC";
						
				modelim.setColumnIdentifiers(kolonlar);
				
				modelim.setColumnCount(0);
				modelim.setRowCount(0);
				modelim.setColumnIdentifiers(kolonlar);
				OracleResultSet rs = showBorrowings(sorgu);
				try {
					while(rs.next()) {
						satirlar[0] = rs.getInt("borrowingID");
						satirlar[1] = rs.getString("TCIdentificationNumber");
						
						satirlar[2] = rs.getString("memberName");
						
						satirlar[3] = rs.getString("memberSurname");
						satirlar[4] = rs.getString("memberMail");
						satirlar[5] = rs.getString("memberPhoneNumber");
						satirlar[6] = rs.getString("bookTitle");
						satirlar[7] = rs.getString("bookDescription");
						satirlar[8] = rs.getString("bookTypeTitle");
						satirlar[9] = rs.getString("authorName");
						satirlar[10] = rs.getString("authorSurname");
						satirlar[11] = rs.getString("publisherTitle");
						
						satirlar[12] = rs.getString("name");
						satirlar[13] = rs.getString("surname");
						satirlar[14] = rs.getDate("dateBorrowed").toString();
						satirlar[15] = rs.getDate("dateReturn").toString();
						
						if(rs.getString("borrowingStatus").equals("True")) {
							satirlar[16] = "Aktif";
						}
						else if(rs.getString("borrowingStatus").equals("False")) {
							satirlar[16] = "Pasif";
						}
						if(rs.getDate("dateReturn")!=null) {
							satirlar[17] = rs.getDate("dateReturn").toString();
						}
						else if(rs.getDate("dateReturn")==null) {
							satirlar[17] = "Henüz Teslim Edilmedi";
						}
						
						
						
						
						
						
						
						
						
						
						
						satirlar[18] = rs.getString("creatDate");
						satirlar[19] = rs.getString("lastUpdateDate");
						
						
						
						
						modelim.addRow(satirlar);
						
					}
					table.setModel(modelim);
				}
				catch(Exception ex) {
					ex.printStackTrace();
					
				}
				
			}
		});
		btnSearchBorrowing.setBackground(new Color(0, 128, 128));
		btnSearchBorrowing.setForeground(new Color(128, 0, 128));
		btnSearchBorrowing.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSearchBorrowing.setBounds(763, 49, 89, 23);
		panel.add(btnSearchBorrowing);
		
		JButton btnDeleteBorrowing = new JButton("Sil");
		btnDeleteBorrowing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//silme iþlemi
				
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Pasif olan ödünç alma satýrlarýný silmek istediðinizden emin misiniz?","Ödünç Alan Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM Borrowing2 WHERE borrowingStatus = 'False'";
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listBorrowings();
							
						
							
							
							
							
							
							
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
		btnDeleteBorrowing.setBackground(new Color(0, 128, 128));
		btnDeleteBorrowing.setForeground(new Color(128, 0, 128));
		btnDeleteBorrowing.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeleteBorrowing.setBounds(892, 50, 89, 23);
		panel.add(btnDeleteBorrowing);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(479, 0, 315, 40);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("\u00D6d\u00FCn\u00E7 Verme \u0130\u015Flemleri Paneli");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(30, 0, 285, 29);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_2.setBackground(new Color(255, 192, 203));
		panel_2.setBounds(28, 60, 1315, 335);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(135, 206, 235));
		panel_3.setBounds(20, 48, 497, 274);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("\u00D6d\u00FCn\u00E7 Verilebilecek Kitaplar");
		lblNewLabel_4.setForeground(new Color(128, 0, 0));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBackground(new Color(135, 206, 235));
		lblNewLabel_4.setBounds(146, 0, 226, 30);
		panel_3.add(lblNewLabel_4);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 34, 477, 229);
		panel_3.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//ödünç verilebilecek kitaplar
				DefaultTableModel tblModel = (DefaultTableModel)table_1.getModel();
				selectedBookNumber = (int) tblModel.getValueAt(table_1.getSelectedRow(), 0);
				txtSelectedBookNumber.setText(String.valueOf(selectedBookNumber));
				
				
				
				
			}
		});
		table_1.setBackground(new Color(221, 160, 221));
		table_1.setForeground(new Color(128, 0, 0));
		table_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblNewLabel_3 = new JLabel("Se\u00E7ilen Kitap No");
		lblNewLabel_3.setForeground(new Color(128, 0, 0));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(20, 11, 130, 26);
		panel_2.add(lblNewLabel_3);
		
		txtSelectedBookNumber = new JTextField();
		txtSelectedBookNumber.setForeground(new Color(0, 0, 128));
		txtSelectedBookNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSelectedBookNumber.setEditable(false);
		txtSelectedBookNumber.setBounds(182, 11, 130, 28);
		panel_2.add(txtSelectedBookNumber);
		txtSelectedBookNumber.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(135, 206, 235));
		panel_4.setBounds(538, 48, 568, 274);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 41, 544, 227);
		panel_4.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//üyeler
				DefaultTableModel tblModel = (DefaultTableModel)table_2.getModel();
				selectedMemberNumber = (int) tblModel.getValueAt(table_2.getSelectedRow(), 0);
				txtSelectedMemberNo.setText(String.valueOf(selectedMemberNumber));
				
			}
		});
		table_2.setBackground(new Color(221, 160, 221));
		table_2.setForeground(new Color(128, 0, 0));
		table_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblNewLabel_4_1 = new JLabel("Kay\u0131tl\u0131 \u00DCyeler");
		lblNewLabel_4_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4_1.setBackground(new Color(135, 206, 235));
		lblNewLabel_4_1.setBounds(257, 0, 226, 30);
		panel_4.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_5 = new JLabel("Se\u00E7ilen \u00DCye No");
		lblNewLabel_5.setForeground(new Color(128, 0, 0));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(648, 15, 119, 19);
		panel_2.add(lblNewLabel_5);
		
		txtSelectedMemberNo = new JTextField();
		txtSelectedMemberNo.setForeground(new Color(0, 0, 128));
		txtSelectedMemberNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSelectedMemberNo.setEditable(false);
		txtSelectedMemberNo.setBounds(798, 14, 153, 22);
		panel_2.add(txtSelectedMemberNo);
		txtSelectedMemberNo.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Teslim Edilecek Tarih");
		lblNewLabel_6.setForeground(new Color(128, 0, 0));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(1120, 29, 188, 37);
		panel_2.add(lblNewLabel_6);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateChooser.getCalendarButton().setForeground(new Color(0, 0, 128));
		dateChooser.setBounds(1117, 77, 191, 37);
		panel_2.add(dateChooser);
		
		JButton btnBookBorrow = new JButton("\u00D6d\u00FCn\u00E7 Ver");
		btnBookBorrow.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\borrow-book-library-icon-fun-260nw-1636989505_50x50.jpg"));
		btnBookBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ödünç verme
				
				LocalDate dateToday = LocalDate.now();
				
				if(txtSelectedMemberNo.getText().toString()!=null && txtSelectedBookNumber.getText().toString()!=null && dateChooser.getDate().toString()!=null) {
					int memberID = Integer.valueOf(txtSelectedMemberNo.getText());
					OracleResultSet rs = bookNumberOfMember(memberID,dateToday);
					boolean resultControl = controlMemberBorrowingStatus(memberID);
					if(resultControl==false) {
						
						try {
							int countResult=0;
							while(rs.next()) {
								countResult = rs.getInt(1);
								
							}
							//System.out.println(countResult);
							
							
							if(countResult==3) {
								JOptionPane.showMessageDialog(null, "Üyeler bir defada en fazla 3 kitap alabilirler!!!! Seçili üye kitap alým limitini doldurmuþtur.");
								
							}
							else {
								//ödünç verme burada yazýlýr
								String dateFormat = "yyyy-MM-dd";
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
								String deliveryDate = dateChooser.getDateFormatString();
								//System.out.println(deliveryDate);
								//Date deliveryDateConvert = simpleDateFormat.parse(deliveryDate);//teslim edeceði tarih
								Date deliveryDateConvert = new Date(dateChooser.getDate().getTime());
								System.out.println(deliveryDateConvert);//asýl teslim edeceði tarih
								//Date borrowedDateConvert = new Date(dateChooser.getDate().getTime());
								LocalDate borrowedDateConvert = LocalDate.now();/*kitabýn verileceði üyenin belirttiði tarih*/
								
								//System.out.println(deliveryDateConvert);
								//Date dateToday = new Date();
								//Date borrowedDateConvert = simpleDateFormat.parse(dateToday.toString());/*kitabýn verdiliði tarih*/
								int bookIDValue = Integer.valueOf(txtSelectedBookNumber.getText());
								int memberIDValue = Integer.valueOf(txtSelectedMemberNo.getText());
								int addedStaffNo = StaffScreenForm.loggedStaffID;
								String borrowingStatus = "True";
								LocalDateTime localDateTime = LocalDateTime.now();
								LocalTime localTime = localDateTime.toLocalTime();
								LocalDate localDate = localDateTime.toLocalDate();
								String creatDate = localDate.toString()+" "+localTime.toString();
								String lastUpdateDate = localDate.toString()+" "+localTime.toString();
								Connection conn = null;
								OraclePreparedStatement pst = null;
								//OraclePreparedStatement pst2 = null;
							
								OracleResultSet rs2 = null;
								conn = ConnectionClass.dbConnect();
								String query2 = "INSERT INTO Borrowing2(borrowingID,memberID,bookNumber,addedStaffNumber,dateBorrowed,dateReturn,borrowingStatus,actualReturnDate,creatDate,lastUpdateDate) VALUES(add_borrowing2.nextval,"+memberIDValue+","+bookIDValue+","+addedStaffNo+",TO_DATE('"+borrowedDateConvert+"','YYYY-MM-DD'),TO_DATE('"+borrowedDateConvert+"','YYYY-MM-DD'),'"+borrowingStatus+"',TO_DATE('"+deliveryDateConvert+"','YYYY-MM-DD'),'"+creatDate+"','"+lastUpdateDate+"')";
								pst = (OraclePreparedStatement) conn.prepareStatement(query2);
								int rowValue = pst.executeUpdate();
								if(rowValue>0) {
									
									if(updateBookStatus(bookIDValue)==true) {
										JOptionPane.showMessageDialog(null, "Kitap ödünç verildi.");
										listBorrowings();
										listBooks();
										txtSelectedBookNumber.setText("");
										txtSelectedMemberNo.setText("");
										dateChooser.setDate(null);
										
										
									}
									else {
										JOptionPane.showMessageDialog(null, "Kitap ödünç verilemedi");
										
									}
									
								
									
								
									
									
									
									
									
									
									
									
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Kitap ödünç verilemedi");
									
								}
								
								
								
								
							}
							
						}
						catch(Exception ex) {
							ex.printStackTrace();
							
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Üyenin yeni ödünç kitap alabilmesi için ödenmemiþ cezasý bulunmamalýdýr!! Seçili üyenin ödenmemiþ cezasý bulunmaktadýr.");
						
					}
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Ödünç verilecek kitap ve ödünç verilecek üye ilgili tablolardan seçilmelidir!!! Teslim tarihi girilmelidir!! Bu alanlar boþ býrakýlamaz!!");
				}
				
				
				
				
				
				
				
				
				
				
				
			}
		});
		btnBookBorrow.setBackground(new Color(199, 21, 133));
		btnBookBorrow.setForeground(new Color(0, 0, 128));
		btnBookBorrow.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookBorrow.setBounds(1116, 125, 192, 52);
		panel_2.add(btnBookBorrow);
		
		JButton btnClearFields = new JButton("Temizle");
		btnClearFields.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//temizle
				txtSelectedBookNumber.setText("");
				txtSelectedMemberNo.setText("");
				dateChooser.setDate(null);
			}
		});
		btnClearFields.setBackground(new Color(199, 21, 133));
		btnClearFields.setForeground(new Color(0, 0, 128));
		btnClearFields.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClearFields.setBounds(1119, 265, 189, 57);
		panel_2.add(btnClearFields);
		
		JButton btnNewButton = new JButton("S\u00FCreyi Uzat");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//süreyi uzat
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				//OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				String dateFormat = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
				String deliveryDate = dateChooser.getDateFormatString();
				//System.out.println(deliveryDate);
				//Date deliveryDateConvert = simpleDateFormat.parse(deliveryDate);//teslim edeceði tarih
				Date deliveryDateConvert = new Date(dateChooser.getDate().getTime());
				System.out.println("deliveryDateConvert"+deliveryDateConvert);
				
				
				
				
				if(deliveryDateConvert!=null) {
					int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Teslim Süresini Uzatma",JOptionPane.YES_NO_OPTION);
					if(selectedOption==0) {
						try {
							
							String queryUpdate = "UPDATE Borrowing2 SET actualReturnDate = TO_DATE('"+deliveryDateConvert+"','YYYY-MM-DD'),lastUpdateDate ='"+lastUpdateDate+"' WHERE borrowingID = "+selectedBorrowingNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Teslim süresi baþarýyla uzatýldý.");
								listBorrowings();
								
							
								
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Deðiþiklikler kaydedilemedi!!");
								
							}
							
							
							
						}catch(Exception ex) {
							ex.printStackTrace();
							
						}
					}
					else {
						
						JOptionPane.showMessageDialog(null, "Güncelleme iþlemi iptal edildi.");
					}
				}
				
				
				
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\business-delivery-time-clock-icon-vector-19261045_50x50.jpg"));
		btnNewButton.setBackground(new Color(199, 21, 133));
		btnNewButton.setForeground(new Color(0, 0, 128));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(1116, 188, 192, 57);
		panel_2.add(btnNewButton);
		
		JButton btnExitBorrowing = new JButton("");
		btnExitBorrowing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitBorrowing.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitBorrowing.setBounds(1299, 0, 61, 49);
		contentPane.add(btnExitBorrowing);
		listBorrowings();
		listBooks();
		listMembers();
	}
}
