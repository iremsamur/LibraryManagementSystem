package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import com.toedter.calendar.JYearChooser;

public class BookOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Kitap No","Kitap Adý","Kitap Sayfa Sayýsý","Kitap Basým Yýlý","Kitap Durumu","Kitap Açýklamasý","Tür No","Kitap Türü","Yazar No","Yazar Adý","Yazar Soyadý","Yayýnevi No","Yayýnevi Adý","Yayýnevi Adresi","Ekleyen Admin Adý","Ekleyen Admin Soyadý","Kayýt Tarihi","Son Güncellenme Tarihi"};
	Object[] satirlar = new Object[18];
	private JTextField txtSearchBookTitle;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	DefaultTableModel modelim2 = new DefaultTableModel();
	Object[] kolonlar2 = {"Yazar No","Yazar Adý","Yazar Soyadý"};
	Object[] satirlar2 = new Object[3];
	DefaultTableModel modelim3 = new DefaultTableModel();
	Object[] kolonlar3 = {"Yayýnevi No","Yayýnevi Adý"};
	Object[] satirlar3 = new Object[2];
	DefaultTableModel modelim4 = new DefaultTableModel();
	Object[] kolonlar4 = {"Kitap Tür No","Tür Adý"};
	Object[] satirlar4 = new Object[2];
	private JTextField txtAuthorNumber;
	private JTextField txtPublisherNumber;
	private JTextField txtBookTypeNumber;
	private JTextField txtBookTitle;
	private JTextField txtBookPageNumber;
	private JTextField txtBookDescription;
	private JYearChooser yearChooser;
	
	private int addedAdminNumber = AdminScreenForm.loggedAdminID;
	private int bookNumber=0;
	private String bookTitle="";
	private String bookPageNumber = "";
	private String bookReleaseDate ="";
	private String bookDescription="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookOperationsScreenForm frame = new BookOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	
	
	public void listBooks() {
		String sorgu = "SELECT * "+
				"FROM Book2,BookType2,Author2,Publisher2,SystemUser "+
				"WHERE Book2.bookTypeNumber = BookType2.bookTypeNumber AND Book2.authorNumber = Author2.authorNumber "
				+ "AND Book2.publisherNumber = Publisher2.publisherNumber AND Book2.addedAdminNumber = SystemUser.userNumber "
				+ "ORDER BY Book2.bookNumber ASC";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showBooks(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("bookNumber");
				satirlar[1] = rs.getString("bookTitle");
				
				satirlar[2] = rs.getString("bookPageNumber");
				
				satirlar[3] = rs.getString("bookReleaseDate");
				if(rs.getString("bookStatus").equals("True")) {
					satirlar[4] = "Aktif";
				}
				else if(rs.getString("bookStatus").equals("False")) {
					satirlar[4] = "Pasif";
				}
				satirlar[5] = rs.getString("bookDescription");
				satirlar[6] = rs.getInt("bookTypeNumber");
				satirlar[7] = rs.getString("bookTypeTitle");
				satirlar[8] = rs.getInt("authorNumber");
				satirlar[9] = rs.getString("authorName");
				satirlar[10] = rs.getString("authorSurname");
				satirlar[11] = rs.getInt("publisherNumber");
				satirlar[12] = rs.getString("publisherTitle");
				satirlar[13] = rs.getString("publisherAddress");
				satirlar[14] = rs.getString("name");
				satirlar[15] = rs.getString("surname");
				
				
				
				
				satirlar[16] = rs.getString("creatDate");
				satirlar[17] = rs.getString("lastUpdateDate");
				
				
				
				
				modelim.addRow(satirlar);
				
			}
			table.setModel(modelim);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	public OracleResultSet showAuthors(String sorgu) {
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
	
	
	public void listAuthors() {
		String sorgu = "SELECT * "+
				"FROM Author2,SystemUser "+
				"WHERE Author2.addedAdminNumber = SystemUser.userNumber ORDER BY Author2.authorNumber ASC";
				
		modelim2.setColumnIdentifiers(kolonlar2);
		
		modelim2.setColumnCount(0);
		modelim2.setRowCount(0);
		modelim2.setColumnIdentifiers(kolonlar2);
		OracleResultSet rs = showAuthors(sorgu);
		try {
			while(rs.next()) {
				satirlar2[0] = rs.getInt("authorNumber");
				satirlar2[1] = rs.getString("authorName");
				satirlar2[2] = rs.getString("authorSurname");
				
				
				
				
				
				modelim2.addRow(satirlar2);
				
			}
			table_1.setModel(modelim2);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	public OracleResultSet showPublishers(String sorgu) {
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
	
	
	public void listPublishers() {
		String sorgu = "SELECT * "+
				"FROM Publisher2,SystemUser "+
				"WHERE Publisher2.addedAdminNumber = SystemUser.userNumber ORDER BY Publisher2.publisherNumber ASC";
				
		modelim3.setColumnIdentifiers(kolonlar3);
		
		modelim3.setColumnCount(0);
		modelim3.setRowCount(0);
		modelim3.setColumnIdentifiers(kolonlar3);
		OracleResultSet rs = showPublishers(sorgu);
		try {
			while(rs.next()) {
				satirlar3[0] = rs.getInt("publisherNumber");
				satirlar3[1] = rs.getString("publisherTitle");
				
				
				
				
				
				modelim3.addRow(satirlar3);
				
			}
			table_2.setModel(modelim3);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}
	public OracleResultSet showBookTypes(String sorgu) {
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
	
	
	public void listBookTypes() {
		String sorgu = "SELECT * "+
				"FROM BookType2,SystemUser "+
				"WHERE BookType2.addedAdminNumber = SystemUser.userNumber ORDER BY BookType2.bookTypeNumber ASC";
				
		modelim4.setColumnIdentifiers(kolonlar4);
		
		modelim4.setColumnCount(0);
		modelim4.setRowCount(0);
		modelim4.setColumnIdentifiers(kolonlar4);
		OracleResultSet rs = showBookTypes(sorgu);
		try {
			while(rs.next()) {
				satirlar4[0] = rs.getInt("bookTypeNumber");
				satirlar4[1] = rs.getString("bookTypeTitle");
				
				
				
				
				
				
				modelim4.addRow(satirlar4);
				
			}
			table_3.setModel(modelim4);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
	}

	/**
	 * Create the frame.
	 */
	public BookOperationsScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1211, 725);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 160, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(34, 439, 1151, 248);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 1131, 171);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//kitap güncelleme silme
				
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				bookNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				bookTitle = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				bookPageNumber = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
				bookReleaseDate = tblModel.getValueAt(table.getSelectedRow(), 3).toString();
				bookDescription = tblModel.getValueAt(table.getSelectedRow(), 5).toString();
				String bookTypeNumber= tblModel.getValueAt(table.getSelectedRow(), 6).toString();
				String authorNumber= tblModel.getValueAt(table.getSelectedRow(), 8).toString();
				String publisherNumber= tblModel.getValueAt(table.getSelectedRow(), 11).toString();
				
			
				txtBookTitle.setText(bookTitle);
				txtBookPageNumber.setText(bookPageNumber);
				//txtBookReleaseYear.setText(bookReleaseDate);
				yearChooser.setYear(Integer.valueOf(bookReleaseDate));
			
				
				txtBookDescription.setText(bookDescription);
				txtBookTypeNumber.setText(bookTypeNumber);
				txtAuthorNumber.setText(authorNumber);
				txtPublisherNumber.setText(publisherNumber);
				
				
				
				
			}
		});
		table.setBackground(new Color(240, 230, 140));
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		
		JLabel lbl11 = new JLabel("Sistemde Kay\u0131tl\u0131 Kitap Bilgileri");
		lbl11.setForeground(new Color(128, 0, 0));
		lbl11.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl11.setBounds(487, 0, 212, 26);
		panel.add(lbl11);
		
		JLabel lbl24 = new JLabel("Aranacak Kitap  Ad\u0131");
		lbl24.setForeground(new Color(128, 0, 0));
		lbl24.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl24.setBounds(316, 31, 136, 26);
		panel.add(lbl24);
		
		txtSearchBookTitle = new JTextField();
		txtSearchBookTitle.setForeground(new Color(0, 0, 128));
		txtSearchBookTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSearchBookTitle.setColumns(10);
		txtSearchBookTitle.setBounds(462, 32, 226, 26);
		panel.add(txtSearchBookTitle);
		
		JButton btnSearchBookTitle = new JButton("Ara");
		btnSearchBookTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//arama
				String bookTitle = txtSearchBookTitle.getText();
				if(bookTitle.length()!=0) {
					String sorgu = "SELECT * "+
							"FROM Book2,BookType2,Author2,Publisher2,SystemUser "+
							"WHERE Book2.bookTypeNumber = BookType2.bookTypeNumber AND Book2.authorNumber = Author2.authorNumber AND Book2.publisherNumber = Publisher2.publisherNumber AND Book2.addedAdminNumber = SystemUser.userNumber AND Book2.bookTitle LIKE '"+bookTitle+"%' ORDER BY Book2.bookNumber ASC";
					
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showBooks(sorgu);
					try {
						while(rs.next()) {
							satirlar[0] = rs.getInt("bookNumber");
							satirlar[1] = rs.getString("bookTitle");
							
							satirlar[2] = rs.getString("bookPageNumber");
							
							satirlar[3] = rs.getString("bookReleaseDate");
							if(rs.getString("bookStatus").equals("True")) {
								satirlar[4] = "Aktif";
							}
							else if(rs.getString("bookStatus").equals("False")) {
								satirlar[4] = "Pasif";
							}
							satirlar[5] = rs.getString("bookDescription");
							satirlar[6] = rs.getInt("bookTypeNumber");
							satirlar[7] = rs.getString("bookTypeTitle");
							satirlar[8] = rs.getInt("authorNumber");
							satirlar[9] = rs.getString("authorName");
							satirlar[10] = rs.getString("authorSurname");
							satirlar[11] = rs.getInt("publisherNumber");
							satirlar[12] = rs.getString("publisherTitle");
							satirlar[13] = rs.getString("publisherAddress");
							satirlar[14] = rs.getString("name");
							satirlar[15] = rs.getString("surname");
							
							
							
							
							satirlar[16] = rs.getString("creatDate");
							satirlar[17] = rs.getString("lastUpdateDate");
							
							
							
							
							
							
							
							
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
		btnSearchBookTitle.setForeground(new Color(75, 0, 130));
		btnSearchBookTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchBookTitle.setBackground(new Color(0, 128, 128));
		btnSearchBookTitle.setBounds(704, 33, 89, 23);
		panel.add(btnSearchBookTitle);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_6.setBounds(314, 0, 423, 44);
		contentPane.add(panel_6);
		
		JLabel lblNewLabel_3_5 = new JLabel("Kitap Bilgileri Y\u00F6netim Paneli");
		lblNewLabel_3_5.setForeground(new Color(0, 0, 128));
		lblNewLabel_3_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3_5.setBounds(98, 11, 300, 22);
		panel_6.add(lblNewLabel_3_5);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_1.setBackground(new Color(100, 149, 237));
		panel_1.setBounds(34, 55, 937, 373);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(26, 165, 319, 197);
		panel_1.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setBackground(new Color(255, 182, 193));
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//yazarlar mouse clicked
				DefaultTableModel tblModel = (DefaultTableModel)table_1.getModel();
				int authorNumber=(int) tblModel.getValueAt(table_1.getSelectedRow(), 0);
				//authorNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				String textAuthorNumber= String.valueOf(authorNumber);
				txtAuthorNumber.setText(textAuthorNumber);
				
			}
		});
		table_1.setForeground(new Color(0, 0, 128));
		table_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblNewLabel_1 = new JLabel("Kitap Sayfa Say\u0131s\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(26, 59, 126, 25);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Kitap Bas\u0131m Y\u0131l\u0131");
		lblNewLabel_2.setForeground(new Color(128, 0, 128));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(26, 95, 104, 25);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Kitap A\u00E7\u0131klamas\u0131");
		lblNewLabel_3.setForeground(new Color(128, 0, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(633, 17, 105, 25);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Yazar No");
		lblNewLabel_4.setForeground(new Color(128, 0, 128));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(294, 24, 105, 19);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Yay\u0131nevi No");
		lblNewLabel_5.setForeground(new Color(128, 0, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(294, 62, 92, 19);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Kitap T\u00FCr No");
		lblNewLabel_6.setForeground(new Color(128, 0, 128));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(294, 94, 92, 27);
		panel_1.add(lblNewLabel_6);
		
		txtAuthorNumber = new JTextField();
		txtAuthorNumber.setForeground(new Color(255, 0, 0));
		txtAuthorNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAuthorNumber.setEditable(false);
		txtAuthorNumber.setBounds(441, 28, 116, 20);
		panel_1.add(txtAuthorNumber);
		txtAuthorNumber.setColumns(10);
		
		txtPublisherNumber = new JTextField();
		txtPublisherNumber.setForeground(new Color(255, 0, 0));
		txtPublisherNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPublisherNumber.setEditable(false);
		txtPublisherNumber.setColumns(10);
		txtPublisherNumber.setBounds(441, 61, 116, 20);
		panel_1.add(txtPublisherNumber);
		
		txtBookTypeNumber = new JTextField();
		txtBookTypeNumber.setForeground(new Color(255, 0, 0));
		txtBookTypeNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookTypeNumber.setEditable(false);
		txtBookTypeNumber.setColumns(10);
		txtBookTypeNumber.setBounds(441, 97, 116, 20);
		panel_1.add(txtBookTypeNumber);
		
		txtBookTitle = new JTextField();
		txtBookTitle.setForeground(new Color(255, 0, 0));
		txtBookTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookTitle.setBounds(145, 20, 116, 28);
		panel_1.add(txtBookTitle);
		txtBookTitle.setColumns(10);
		
		txtBookPageNumber = new JTextField();
		txtBookPageNumber.setForeground(new Color(255, 0, 0));
		txtBookPageNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookPageNumber.setBounds(145, 62, 116, 22);
		panel_1.add(txtBookPageNumber);
		txtBookPageNumber.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 904, 124);
		panel_1.add(panel_2);
		panel_2.setBackground(new Color(144, 238, 144));
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Kitap Ad\u0131");
		lblNewLabel.setBounds(20, 11, 104, 37);
		panel_2.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(128, 0, 128));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txtBookDescription = new JTextField();
		txtBookDescription.setForeground(new Color(255, 0, 0));
		txtBookDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookDescription.setBounds(743, 11, 151, 109);
		panel_2.add(txtBookDescription);
		txtBookDescription.setColumns(10);
		
		yearChooser = new JYearChooser();
		yearChooser.getSpinner().setForeground(new Color(255, 0, 0));
		yearChooser.getSpinner().setFont(new Font("Tahoma", Font.BOLD, 12));
		yearChooser.setBounds(137, 93, 117, 20);
		panel_2.add(yearChooser);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(144, 238, 144));
		panel_4.setBounds(10, 146, 904, 224);
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(654, 16, 240, 197);
		panel_4.add(scrollPane_3);
		
		table_3 = new JTable();
		table_3.setBackground(new Color(255, 182, 193));
		table_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//kitap türleri clicked
				DefaultTableModel tblModel = (DefaultTableModel)table_3.getModel();
				int bookTypeNumber=(int) tblModel.getValueAt(table_3.getSelectedRow(), 0);
				//authorNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				String textBookTypeNumber= String.valueOf(bookTypeNumber);
				txtBookTypeNumber.setText(textBookTypeNumber);
				
				
			}
		});
		table_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		table_3.setForeground(new Color(0, 0, 128));
		scrollPane_3.setViewportView(table_3);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(352, 16, 276, 197);
		panel_4.add(scrollPane_2);
		
		table_2 = new JTable();
		table_2.setBackground(new Color(255, 182, 193));
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//yayýnevi
				DefaultTableModel tblModel = (DefaultTableModel)table_2.getModel();
				int publisherNumber=(int) tblModel.getValueAt(table_2.getSelectedRow(), 0);
				//authorNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				String textPublisherNumber= String.valueOf(publisherNumber);
				txtPublisherNumber.setText(textPublisherNumber);
				
			}
		});
		table_2.setForeground(new Color(0, 0, 128));
		table_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblNewLabel_7_1_2 = new JLabel("Yay\u0131nevleri");
		lblNewLabel_7_1_2.setBounds(425, 0, 112, 14);
		panel_4.add(lblNewLabel_7_1_2);
		lblNewLabel_7_1_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_7_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_7 = new JLabel("Yazarlar");
		lblNewLabel_7.setBounds(110, 0, 112, 14);
		panel_4.add(lblNewLabel_7);
		lblNewLabel_7.setForeground(new Color(128, 0, 0));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_7_1_2_1 = new JLabel("Kitap T\u00FCrleri");
		lblNewLabel_7_1_2_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_7_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7_1_2_1.setBounds(725, 1, 112, 14);
		panel_4.add(lblNewLabel_7_1_2_1);
		
		JLabel lblNewLabel_7_1_5 = new JLabel("Kitap T\u00FCrleri");
		lblNewLabel_7_1_5.setBounds(743, 146, 112, 14);
		panel_1.add(lblNewLabel_7_1_5);
		lblNewLabel_7_1_5.setForeground(new Color(128, 0, 0));
		lblNewLabel_7_1_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_3.setBackground(new Color(100, 149, 237));
		panel_3.setBounds(981, 55, 204, 373);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNewBookAdd = new JButton("Kitap Ekle");
		btnNewBookAdd.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\books_blue_add_50x50.jpg"));
		btnNewBookAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kitap ekle
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String bookTitle = txtBookTitle.getText();
				String bookPageNumber = txtBookPageNumber.getText();
				//String bookReleaseYear = txtBookReleaseYear.getText();
				String bookReleaseYear = String.valueOf(yearChooser.getYear());
			
				String bookDescription = txtBookDescription.getText();
				String bookStatus = "True";
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String creatDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				if(bookTitle.length()!=0 && bookPageNumber.length()!=0 && bookReleaseYear.length()!=0 && bookDescription.length()!=0) {
					if(txtAuthorNumber.getText().length()!=0 && txtPublisherNumber.getText().length()!=0 && txtBookTypeNumber.getText().length()!=0) {
						int authorNumberValue = Integer.valueOf(txtAuthorNumber.getText());
						int publisherNumberValue = Integer.valueOf(txtPublisherNumber.getText());
						int bookTypeNumberValue = Integer.valueOf(txtBookTypeNumber.getText());
						
						try {
							String query = "SELECT * "+
									"FROM Book2,BookType2,Author2,Publisher2,SystemUser "+
									"WHERE Book2.bookTypeNumber = BookType2.bookTypeNumber AND Book2.authorNumber = Author2.authorNumber AND Book2.publisherNumber = Publisher2.publisherNumber AND Book2.addedAdminNumber = SystemUser.userNumber AND Book2.bookTitle = '"+bookTitle+"' ORDER BY Book2.bookNumber ASC";
									
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								JOptionPane.showMessageDialog(null, "Bu kitap zaten sistemde kayýtlý bulunmaktadýr!!");
								
								
								
								
								
								
								
							}
							else {
								
								//String queryAdd = "INSERT INTO Staff(staffNumber,staffUsername,staffPassword,adminNumber,staffMail,staffPhoneNumber,staffAddress,staffDeleteControl,TCIdentificationNumber,staffName,staffSurname,staffLastLoginDate,staffCreatedate) VALUES(add_staff.nextval,'"+staffUsername+"','"+staffPassword+"',"+adminID+",'"+staffMail+"','"+staffPhoneNumber+"','"+staffAddress+"','"+staffDeleteControl+"','"+staffTC+"','"+staffName+"','"+staffSurname+"','"+lastLoginDateStaff+"','"+createDate+"')";
								
								String query2 = "INSERT INTO Book2(bookNumber,bookTitle,bookPageNumber,bookReleaseDate,bookStatus,bookDescription,bookTypeNumber,authorNumber,publisherNumber,addedAdminNumber,creatDate,lastUpdateDate) VALUES(add_book2.nextval,'"+bookTitle+"','"+bookPageNumber+"','"+bookReleaseYear+"','"+bookStatus+"','"+bookDescription+"',"+bookTypeNumberValue+","+authorNumberValue+","+publisherNumberValue+","+addedAdminNumber+",'"+creatDate+"','"+lastUpdateDate+"')";
								pst2 = (OraclePreparedStatement) conn.prepareStatement(query2);
								int rowValue = pst2.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, "Yeni kitap baþarýyla eklendi.");
									listBooks();
									txtBookTitle.setText("");
									txtBookPageNumber.setText("");
									
									txtBookDescription.setText("");
									txtAuthorNumber.setText("");
									txtPublisherNumber.setText("");
									txtBookTypeNumber.setText("");
									
									yearChooser.setYear(2022);
									
									
									
									
									
									
									
									
									
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Kitap eklenemedi!!");
									
								}
							}
							
						}
						catch(Exception ex) {
							ex.printStackTrace();
							
						}
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Lütfen kitap yazarý, yayýnevi ve kitap türü için ilgili tablolardan seçim yapýnýz!!");
						
					}
					
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
				}
				
				
				
				
			}
		});
		btnNewBookAdd.setBackground(new Color(144, 238, 144));
		btnNewBookAdd.setForeground(new Color(128, 0, 128));
		btnNewBookAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewBookAdd.setBounds(10, 22, 184, 71);
		panel_3.add(btnNewBookAdd);
		
		JButton btnBookUpdate = new JButton("Kitap G\u00FCncelle");
		btnBookUpdate.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\mark-book-logo-icon-design-vector-22504806_50x50.jpg"));
		btnBookUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//güncelleme
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String bookTitle = txtBookTitle.getText();
				String bookPageNumber = txtBookPageNumber.getText();
				String bookReleaseYear = String.valueOf(yearChooser.getYear());
			
				String bookDescription = txtBookDescription.getText();
				String bookStatus = "True";
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String creatDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				if(bookTitle.length()!=0 && bookPageNumber.length()!=0 && bookReleaseYear.length()!=0 && bookDescription.length()!=0) {
					if(txtAuthorNumber.getText().length()!=0 && txtPublisherNumber.getText().length()!=0 && txtBookTypeNumber.getText().length()!=0) {
						int authorNumberValue = Integer.valueOf(txtAuthorNumber.getText());
						int publisherNumberValue = Integer.valueOf(txtPublisherNumber.getText());
						int bookTypeNumberValue = Integer.valueOf(txtBookTypeNumber.getText());
						int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Kitap Güncelle",JOptionPane.YES_NO_OPTION);
						if(selectedOption==0) {
							try {
								
								String queryUpdate = "UPDATE Book2 SET bookTitle = '"+bookTitle+"',bookPageNumber ='"+bookPageNumber+"',bookReleaseDate ='"+bookReleaseYear+"',bookDescription ='"+bookDescription+"',bookTypeNumber ="+bookTypeNumberValue+",authorNumber ="+authorNumberValue+",publisherNumber ="+publisherNumberValue+",addedAdminNumber ="+addedAdminNumber+",lastUpdateDate = '"+lastUpdateDate+"' WHERE bookNumber = "+bookNumber;
								pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
								int rowValue = pst.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
									listBooks();
									txtBookTitle.setText("");
									txtBookPageNumber.setText("");
									
									txtBookDescription.setText("");
									txtAuthorNumber.setText("");
									txtPublisherNumber.setText("");
									txtBookTypeNumber.setText("");
									yearChooser.setYear(2022);
									
									
									
									
									
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
					else {
						JOptionPane.showMessageDialog(null, "Lütfen kitap yazarý, yayýnevi ve kitap türü için ilgili tablolardan seçim yapýnýz!!");
						
					}
					
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
				}
				
			}
		});
		btnBookUpdate.setForeground(new Color(128, 0, 128));
		btnBookUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookUpdate.setBackground(new Color(144, 238, 144));
		btnBookUpdate.setBounds(10, 104, 184, 71);
		panel_3.add(btnBookUpdate);
		
		JButton btnBookDelete = new JButton("Kitap Sil");
		btnBookDelete.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\remove-book-icon-vector-4540701_50x50.jpg"));
		btnBookDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//silme iþlemi
				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili kitabý silmek istediðinizden emin misiniz?","Kitap Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM Book2 WHERE bookNumber = "+bookNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listBooks();
							txtBookTitle.setText("");
							txtBookPageNumber.setText("");
							
							txtBookDescription.setText("");
							txtAuthorNumber.setText("");
							txtPublisherNumber.setText("");
							txtBookTypeNumber.setText("");
							yearChooser.setYear(2022);
							
							
							
							
							
							
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
		btnBookDelete.setForeground(new Color(128, 0, 128));
		btnBookDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookDelete.setBackground(new Color(144, 238, 144));
		btnBookDelete.setBounds(10, 199, 184, 71);
		panel_3.add(btnBookDelete);
		
		JButton btnBookFieldsClear = new JButton("Temizle");
		btnBookFieldsClear.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnBookFieldsClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//temizle
				txtBookTitle.setText("");
				txtBookPageNumber.setText("");
				
				txtBookDescription.setText("");
				txtAuthorNumber.setText("");
				txtPublisherNumber.setText("");
				txtBookTypeNumber.setText("");
				yearChooser.setYear(2022);
			}
		});
		btnBookFieldsClear.setBackground(new Color(144, 238, 144));
		btnBookFieldsClear.setForeground(new Color(128, 0, 128));
		btnBookFieldsClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookFieldsClear.setBounds(10, 288, 184, 74);
		panel_3.add(btnBookFieldsClear);
		
		JButton btnExitBookOperations = new JButton("");
		btnExitBookOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//çýkýþ
				dispose();
			}
		});
		btnExitBookOperations.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitBookOperations.setBackground(new Color(0, 0, 128));
		btnExitBookOperations.setBounds(1134, 0, 61, 44);
		contentPane.add(btnExitBookOperations);
		listBooks();
		listAuthors();
		listPublishers();
		listBookTypes();
	}
}
