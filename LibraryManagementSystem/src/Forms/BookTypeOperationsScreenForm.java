package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class BookTypeOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Kitap Tür No","Kitap Türü","Ekleyen Admin Adý","Ekleyen Admin Soyadý","Kayýt Tarihi","Güncellenme Tarihi"};
	Object[] satirlar = new Object[6];
	private JTextField txtSearchBookType;
	private JTextField txtBookTypeTitle;
	private int addedAdminNumber = AdminScreenForm.loggedAdminID;
	private JTextField txtBookTypeUpdate;
	private int bookTypeNumber =0;
	private String bookTypeTitle = "";
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeOperationsScreenForm frame = new BookTypeOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showBookTypes(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("bookTypeNumber");
				satirlar[1] = rs.getString("bookTypeTitle");
				
				satirlar[2] = rs.getString("name");
				
				satirlar[3] = rs.getString("surname");
				
				
				satirlar[4] = rs.getString("creatDate");
				satirlar[5] = rs.getString("lastUpdateDate");
				
				
				
				
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
	public BookTypeOperationsScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 853, 678);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 160, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel.setBackground(new Color(135, 206, 250));
		panel.setBounds(10, 323, 827, 305);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 807, 201);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(240, 230, 140));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//clicked
				
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				bookTypeNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				bookTypeTitle = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				
		
				txtBookTypeUpdate.setText(bookTypeTitle);
				
			}
		});
		table.setForeground(new Color(139, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		
		JLabel lbl10 = new JLabel("Sistemde Kay\u0131tl\u0131 Kitap T\u00FCrleri");
		lbl10.setForeground(new Color(128, 0, 0));
		lbl10.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbl10.setBounds(337, 11, 212, 26);
		panel.add(lbl10);
		
		JLabel lbl2 = new JLabel("Aranacak Kitap T\u00FCr\u00FC Ad\u0131");
		lbl2.setForeground(new Color(128, 0, 0));
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl2.setBounds(130, 48, 166, 26);
		panel.add(lbl2);
		
		txtSearchBookType = new JTextField();
		txtSearchBookType.setForeground(new Color(0, 0, 128));
		txtSearchBookType.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtSearchBookType.setColumns(10);
		txtSearchBookType.setBounds(323, 49, 226, 26);
		panel.add(txtSearchBookType);
		
		JButton btnSearchBookTypes = new JButton("Ara");
		btnSearchBookTypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kitap türü arama
				

				String bookTypeTitle = txtSearchBookType.getText();
				if(bookTypeTitle.length()!=0) {
					String sorgu = "SELECT * "+
							"FROM BookType2,SystemUser "+
							"WHERE BookType2.addedAdminNumber = SystemUser.userNumber AND BookType2.bookTypeTitle = '"+bookTypeTitle+"' ORDER BY BookType2.bookTypeNumber ASC";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showBookTypes(sorgu);
					try {
						while(rs.next()) {
							satirlar[0] = rs.getInt("bookTypeNumber");
							satirlar[1] = rs.getString("bookTypeTitle");
							
							satirlar[2] = rs.getString("name");
							
							satirlar[3] = rs.getString("surname");
							
							
							satirlar[4] = rs.getString("creatDate");
							satirlar[5] = rs.getString("lastUpdateDate");
							
							
							
							
							
							
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
		btnSearchBookTypes.setForeground(new Color(75, 0, 130));
		btnSearchBookTypes.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchBookTypes.setBackground(new Color(0, 128, 128));
		btnSearchBookTypes.setBounds(564, 50, 89, 23);
		panel.add(btnSearchBookTypes);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_4.setBounds(241, 11, 423, 44);
		contentPane.add(panel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Kitap T\u00FCr\u00FC Y\u00F6netim Paneli");
		lblNewLabel_3.setForeground(new Color(0, 0, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(98, 11, 300, 22);
		panel_4.add(lblNewLabel_3);
		
		JPanel panel_2_3_4 = new JPanel();
		panel_2_3_4.setLayout(null);
		panel_2_3_4.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_2_3_4.setBackground(new Color(100, 149, 237));
		panel_2_3_4.setBounds(10, 98, 321, 214);
		contentPane.add(panel_2_3_4);
		
		JLabel lbl8 = new JLabel("Kitap T\u00FCr\u00FC Ad\u0131");
		lbl8.setForeground(new Color(128, 0, 128));
		lbl8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl8.setBounds(10, 26, 118, 26);
		panel_2_3_4.add(lbl8);
		
		txtBookTypeTitle = new JTextField();
		txtBookTypeTitle.setForeground(new Color(255, 0, 0));
		txtBookTypeTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookTypeTitle.setColumns(10);
		txtBookTypeTitle.setBounds(118, 27, 193, 26);
		panel_2_3_4.add(txtBookTypeTitle);
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBounds(59, 110, 212, 54);
		panel_2_3_4.add(panel_9);
		
		JButton btnBookTypeAdd = new JButton("T\u00FCr Ekle");
		btnBookTypeAdd.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\indir_50x50 (1).jpg"));
		btnBookTypeAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kitap tür ekle
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String bookTypeTitle = txtBookTypeTitle.getText();
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String creatDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				
				if(bookTypeTitle.length()!=0) {
					try {
						String query = "SELECT * FROM BookType2,SystemUser WHERE SystemUser.userNumber = BookType2.addedAdminNumber AND BookType2.bookTypeTitle = '"+bookTypeTitle+"'";
						
						pst = (OraclePreparedStatement) conn.prepareStatement(query);
					
						rs = (OracleResultSet) pst.executeQuery();
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "Bu kitap türü zaten sistemde kayýtlý bulunmaktadýr!!");
							
							
							
							
							
							
							
						}
						else {
							
							//String queryAdd = "INSERT INTO Staff(staffNumber,staffUsername,staffPassword,adminNumber,staffMail,staffPhoneNumber,staffAddress,staffDeleteControl,TCIdentificationNumber,staffName,staffSurname,staffLastLoginDate,staffCreatedate) VALUES(add_staff.nextval,'"+staffUsername+"','"+staffPassword+"',"+adminID+",'"+staffMail+"','"+staffPhoneNumber+"','"+staffAddress+"','"+staffDeleteControl+"','"+staffTC+"','"+staffName+"','"+staffSurname+"','"+lastLoginDateStaff+"','"+createDate+"')";
							
							String query2 = "INSERT INTO BookType2(bookTypeNumber,bookTypeTitle,addedAdminNumber,creatDate,lastUpdateDate) VALUES(add_bookType2.nextval,'"+bookTypeTitle+"',"+addedAdminNumber+",'"+creatDate+"','"+lastUpdateDate+"')";
							pst2 = (OraclePreparedStatement) conn.prepareStatement(query2);
							int rowValue = pst2.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Yeni kitap türü baþarýyla eklendi.");
								listBookTypes();
								txtBookTypeTitle.setText("");
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Kitap türü eklenemedi!!");
								
							}
						}
						
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
					
					
					
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
				}
			}
		});
		btnBookTypeAdd.setForeground(new Color(128, 0, 128));
		btnBookTypeAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookTypeAdd.setBackground(new Color(144, 238, 144));
		btnBookTypeAdd.setBounds(40, 0, 143, 54);
		panel_9.add(btnBookTypeAdd);
		
		JPanel panel_9_1_2 = new JPanel();
		panel_9_1_2.setLayout(null);
		panel_9_1_2.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_9_1_2.setBackground(new Color(100, 149, 237));
		panel_9_1_2.setBounds(434, 88, 274, 101);
		contentPane.add(panel_9_1_2);
		
		JLabel lbl40 = new JLabel("Kitap T\u00FCr\u00FC Ad\u0131");
		lbl40.setForeground(new Color(128, 0, 128));
		lbl40.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl40.setBounds(77, 11, 98, 23);
		panel_9_1_2.add(lbl40);
		
		txtBookTypeUpdate = new JTextField();
		txtBookTypeUpdate.setForeground(new Color(255, 0, 0));
		txtBookTypeUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBookTypeUpdate.setColumns(10);
		txtBookTypeUpdate.setBounds(63, 45, 131, 34);
		panel_9_1_2.add(txtBookTypeUpdate);
		
		JPanel panel_18 = new JPanel();
		panel_18.setLayout(null);
		panel_18.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_18.setBackground(new Color(100, 149, 237));
		panel_18.setBounds(341, 200, 496, 112);
		contentPane.add(panel_18);
		
		JButton btnBookTypeUpdate = new JButton("T\u00FCr G\u00FCncelle");
		btnBookTypeUpdate.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3994764-middle_50x50.jpg"));
		btnBookTypeUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Güncelle
				Connection conn = null;
				OraclePreparedStatement pst = null;
				//OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
	            //String staffDeleteControl = "True";
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				//int adminID = AdminScreenForm.loggedAdminID;
				String updatebookTypeTitle = txtBookTypeUpdate.getText();
				
				if(updatebookTypeTitle.length()!=0) {
					
					int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Kitap türü Güncelle",JOptionPane.YES_NO_OPTION);
					if(selectedOption==0) {
						try {
							//String query = "select * from Admin";
							
							//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
							
							//pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							//rs = (OracleResultSet) pst.executeQuery();
							//String queryUpdate = "UPDATE Staff SET staffUsername = '"+txtUpdateStaffUsername.getText()+"',staffPassword ='"+String.valueOf(pswStaffPassword.getPassword())+"', adminNumber = "+adminID+",staffMail = '"+txtUpdateStaffMail.getText()+"',staffPhoneNumber = '"+txtUpdateStaffPhone.getText()+"',staffAddress = '"+txtUpdateStaffAddress.getText()+"',staffName = '"+txtUpdateStaffName.getText()+"',staffSurname = '"+txtUpdateStaffSurname.getText()+"',staffCreatedate = '"+createDate+"' WHERE staffNumber = "+staffNumber;
							//String queryUpdate = "UPDATE SystemUser SET userName = '"+txtUpdateStaffUsername.getText()+"',password ='"+String.valueOf(pswStaffPassword.getPassword())+"',mailAdress = '"+txtUpdateStaffMail.getText()+"',TCIdentificationNumber = '"+txtUpdateStaffTC.getText()+"',name = '"+txtUpdateStaffName.getText()+"',surname = '"+txtUpdateStaffSurname.getText()+"',creatDate = '"+createDate+"',lastLoginDate = '"+staffLastLoginDate+"' WHERE userNumber = "+staffNumber;
							String queryUpdate = "UPDATE BookType2 SET bookTypeTitle = '"+updatebookTypeTitle+"',lastUpdateDate = '"+lastUpdateDate+"' WHERE bookTypeNumber = "+bookTypeNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
								listBookTypes();
								txtBookTypeUpdate.setText("");
								
								
								
								
								
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
					JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
				}
			}
		});
		btnBookTypeUpdate.setForeground(new Color(128, 0, 128));
		btnBookTypeUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookTypeUpdate.setBackground(new Color(144, 238, 144));
		btnBookTypeUpdate.setBounds(10, 11, 163, 92);
		panel_18.add(btnBookTypeUpdate);
		
		JButton btnBookTypeDelete = new JButton("T\u00FCr Sil");
		btnBookTypeDelete.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\WhatsApp Image 2021-12-29 at 21.56.23.jpeg"));
		btnBookTypeDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//sil
				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili kitap türünü silmek istediðinizden emin misiniz?","Kitap Türü Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM BookType2 WHERE bookTypeNumber = "+bookTypeNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listBookTypes();
							txtBookTypeUpdate.setText("");
							
							
							
							
							
							
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
		btnBookTypeDelete.setForeground(new Color(128, 0, 128));
		btnBookTypeDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookTypeDelete.setBackground(new Color(144, 238, 144));
		btnBookTypeDelete.setBounds(183, 11, 154, 92);
		panel_18.add(btnBookTypeDelete);
		
		JButton btnBookTypeFieldClear = new JButton("Temizle");
		btnBookTypeFieldClear.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnBookTypeFieldClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//temizle
				txtBookTypeUpdate.setText("");
				txtBookTypeTitle.setText("");
				
			}
		});
		btnBookTypeFieldClear.setForeground(new Color(128, 0, 128));
		btnBookTypeFieldClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBookTypeFieldClear.setBackground(new Color(144, 238, 144));
		btnBookTypeFieldClear.setBounds(347, 11, 139, 92);
		panel_18.add(btnBookTypeFieldClear);
		
		JButton btnExitBookTypeOperations = new JButton("");
		btnExitBookTypeOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitBookTypeOperations.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitBookTypeOperations.setBackground(new Color(0, 0, 128));
		btnExitBookTypeOperations.setBounds(738, 11, 56, 44);
		contentPane.add(btnExitBookTypeOperations);
		listBookTypes();
	}
}
