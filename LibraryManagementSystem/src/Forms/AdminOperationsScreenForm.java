package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

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
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class AdminOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	String sorgu=null;
	DefaultTableModel myModel = new DefaultTableModel();
	/*
	Object[] columns = {"Admin No","Admin Kullanýcý Adý","Admin Þifre","Admin Eklenme Tarihi","Mail Adres","TC Kimlik no","Admin Adý","Admin Soyadý","Son Sisteme Giriþ Tarihi"};
	Object[] rows = new Object[9];
	*/
	Object[] columns = {"Admin No","Admin Kullanýcý Adý","Admin Þifre","Mail Adres","Admin Adý","Admin Soyadý","TC Kimlik no","Admin Eklenme Tarihi","Son Sisteme Giriþ Tarihi"};
	Object[] rows = new Object[9];
	private JTextField txtSearchAdmin;
	private JTextField txtAdminUsername;
	private JTextField txtAdminMail;
	private JTextField txtTCAdmin;
	private JTextField txtAdminName;
	private JTextField txtAdminSurname;
	private JTextField txtAdminUsernameUpdate;
	private JTextField txtAdminMailUpdate;
	private JTextField txtAdminNameUpdate;
	private JTextField txtAdminSurnameUpdate;
	private JTextField txtAdminTCUpdate;
	private JPasswordField txtAdminPasswordUpdate;
	private int adminNumber=0;
	private String adminUsername ="";
	private String adminPassword = "";
	private String adminCreatedate = " ";
	private String adminMail = " ";
	private String tcIdentificationNumber = "";
	private String adminName = "";
	private String adminSurname = "";
	private String adminLastLoginDate="";
	private int loggedAdminIDValue = AdminScreenForm.loggedAdminID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminOperationsScreenForm frame = new AdminOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static OracleResultSet showAdminUsers(String sorgu) {
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
	public static void updateLoginDate() {
		
		
		Connection conn = null;
		OraclePreparedStatement pst = null;
		//OraclePreparedStatement pst2 = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		//SimpleDateFormat dateNow = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		
		//Date dateHourNow = new Date();
		//String createDate = dateNow.format(dateHourNow);
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalTime localTime = localDateTime.toLocalTime();
		LocalDate localDate = localDateTime.toLocalDate();
		String lastLoginDate = localDate.toString()+" "+localTime.toString();
				
		
		try {
			
			String queryUpdate = "UPDATE SystemUser SET lastLoginDate = '"+lastLoginDate+"' WHERE userNumber = "+AdminScreenForm.loggedAdminID;
			pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
			int rowValue = pst.executeUpdate();
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		/*
		Connection conn = null;
		OraclePreparedStatement pst = null;
		//OraclePreparedStatement pst2 = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		//SimpleDateFormat dateNow = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		
		//Date dateHourNow = new Date();
		//String createDate = dateNow.format(dateHourNow);
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalTime localTime = localDateTime.toLocalTime();
		LocalDate localDate = localDateTime.toLocalDate();
		String createDate = localDate.toString()+" "+localTime.toString();
				
		
		try {
			
			String queryUpdate = "UPDATE Admin SET lastLoginDate = '"+createDate+"' WHERE adminNumber = "+AdminScreenForm.loggedAdminID;
			pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
			int rowValue = pst.executeUpdate();
			
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
		}
		*/
		
	}
	public static String createPasswordForNewUsers() {
		String password = "";
		 String dizi[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","y","z","x",
		 "0","1","2","3","4","5","6","7","8","9"};
		 
		 for(int i = 0; i < 9; i++){       
		 int rastgele = (1+(int)(Math.random()*35));                     
		 password += dizi[rastgele];
		 }
		 return password;
	}
	public void listUpdate() {
		
		sorgu = "SELECT * FROM SystemUser,Role WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle = 'Admin' ORDER BY SystemUser.userNumber ASC";
		myModel.setColumnCount(0);
		myModel.setRowCount(0);
		myModel.setColumnIdentifiers(columns);
		OracleResultSet rs = showAdminUsers(sorgu);
		try {
			while(rs.next()) {
				rows[0] = rs.getInt("userNumber");
				rows[1] = rs.getString("userName");
				rows[2] = rs.getString("password");
				rows[3] = rs.getString("mailAdress");
				rows[4] = rs.getString("name");
				rows[5] = rs.getString("surname");
				rows[6] = rs.getString("TCIdentificationNumber");
				rows[7] = rs.getString("creatDate");
				rows[8] = rs.getString("lastLoginDate");
				
				
				myModel.addRow(rows);
				
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		/*
		sorgu = "SELECT * FROM Admin ORDER BY adminNumber ASC";
		myModel.setColumnCount(0);
		myModel.setRowCount(0);
		myModel.setColumnIdentifiers(columns);
		OracleResultSet rs = showAdminUsers(sorgu);
		try {
			while(rs.next()) {
				rows[0] = rs.getInt("adminNumber");
				rows[1] = rs.getString("adminUsername");
				rows[2] = rs.getString("adminPassword");
				rows[3] = rs.getString("adminCreatedate");
				rows[4] = rs.getString("adminMail");
				rows[5] = rs.getString("TCIdentificationNumber");
				rows[6] = rs.getString("adminName");
				rows[7] = rs.getString("adminSurname");
				rows[8] = rs.getString("lastLoginDate");
				
				
				myModel.addRow(rows);
				
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		*/
	}
	
	public AdminOperationsScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1005, 740);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 130, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 
	
		updateLoginDate();
		listUpdate();
		
		
	
	
		
			
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(new Color(128, 0, 0), 4));
			panel.setBackground(new Color(135, 206, 235));
			panel.setBounds(10, 333, 976, 355);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			
			scrollPane.setBounds(10, 88, 944, 256);
			panel.add(scrollPane);
			
			table = new JTable();
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//mousecliked
					DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
					adminNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
					adminUsername = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
					adminPassword = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
					adminMail = tblModel.getValueAt(table.getSelectedRow(), 3).toString();
					
					adminName = tblModel.getValueAt(table.getSelectedRow(), 4).toString();
					adminSurname = tblModel.getValueAt(table.getSelectedRow(), 5).toString();
					
					tcIdentificationNumber = tblModel.getValueAt(table.getSelectedRow(), 6).toString();
					adminCreatedate = tblModel.getValueAt(table.getSelectedRow(), 7).toString();
					
					adminLastLoginDate = tblModel.getValueAt(table.getSelectedRow(), 8).toString();
					txtAdminUsernameUpdate.setText(adminUsername);
					txtAdminMailUpdate.setText(adminMail);
					txtAdminPasswordUpdate.setText(adminPassword);
					txtAdminTCUpdate.setText(tcIdentificationNumber);
					txtAdminNameUpdate.setText(adminName);
					txtAdminSurnameUpdate.setText(adminSurname);
					
					
					
					
					
					
					
				}
			});
			table.setBackground(new Color(240, 230, 140));
			table.setFont(new Font("Tahoma", Font.BOLD, 12));
			table.setForeground(new Color(102, 0, 0));
			scrollPane.setViewportView(table);
			table.setModel(myModel);
			
			JLabel lblNewLabel = new JLabel("Sistemde Kay\u0131tl\u0131 Sistem Y\u00F6neticileri");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel.setForeground(new Color(139, 0, 0));
			lblNewLabel.setBounds(290, 11, 315, 25);
			panel.add(lblNewLabel);
			
			txtSearchAdmin = new JTextField();
			txtSearchAdmin.setForeground(new Color(0, 0, 128));
			txtSearchAdmin.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtSearchAdmin.setBounds(450, 41, 302, 36);
			panel.add(txtSearchAdmin);
			txtSearchAdmin.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Aramak \u0130stedi\u011Finiz Y\u00F6neticinin TC Kimlik No ");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_1.setForeground(new Color(139, 0, 0));
			lblNewLabel_1.setBounds(183, 40, 266, 36);
			panel.add(lblNewLabel_1);
			
			JButton btnSearchAdmin = new JButton("Ara");
			btnSearchAdmin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//Arama iþlemi
					String adminTCNumber = txtSearchAdmin.getText();
					if(adminTCNumber != null) {
						sorgu = "SELECT * FROM SystemUser WHERE TCIdentificationNumber = '" + adminTCNumber + "'";
						myModel.setColumnCount(0);
						myModel.setRowCount(0);
						myModel.setColumnIdentifiers(columns);
						OracleResultSet rs = showAdminUsers(sorgu);
						try {
							while(rs.next()) {
								rows[0] = rs.getInt("userNumber");
								rows[1] = rs.getString("userName");
								rows[2] = rs.getString("password");
								rows[3] = rs.getString("mailAdress");
								rows[4] = rs.getString("name");
								rows[5] = rs.getString("surname");
								rows[6] = rs.getString("TCIdentificationNumber");
								rows[7] = rs.getString("creatDate");
								rows[8] = rs.getString("lastLoginDate");
								
								
								myModel.addRow(rows);
								
							}
						}
						catch(Exception ex) {
							ex.printStackTrace();
							
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Aranacak admin TC alaný boþ býrakýlamaz!!! Lütfen aranacak adminin 11 haneli TC Kimlik numarasýný giriniz!!");
					}
					
					
					
					
					
				}
			});
			btnSearchAdmin.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnSearchAdmin.setBackground(new Color(128, 0, 128));
			btnSearchAdmin.setForeground(new Color(128, 0, 0));
			btnSearchAdmin.setBounds(766, 33, 89, 44);
			panel.add(btnSearchAdmin);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(100, 149, 237));
			panel_1.setBorder(new LineBorder(new Color(128, 0, 0), 3));
			panel_1.setBounds(10, 59, 357, 263);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblNewLabel_3 = new JLabel("Admin Kullan\u0131c\u0131 Ad\u0131");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_3.setForeground(new Color(128, 0, 128));
			lblNewLabel_3.setBounds(27, 11, 124, 31);
			panel_1.add(lblNewLabel_3);
			
			txtAdminUsername = new JTextField();
			txtAdminUsername.setForeground(new Color(255, 0, 0));
			txtAdminUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminUsername.setBounds(188, 14, 136, 26);
			panel_1.add(txtAdminUsername);
			txtAdminUsername.setColumns(10);
			
			JLabel lblNewLabel_5 = new JLabel("Admin Mail");
			lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_5.setForeground(new Color(128, 0, 128));
			lblNewLabel_5.setBounds(27, 53, 81, 26);
			panel_1.add(lblNewLabel_5);
			
			txtAdminMail = new JTextField();
			txtAdminMail.setForeground(new Color(255, 0, 0));
			txtAdminMail.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminMail.setBounds(188, 51, 136, 22);
			panel_1.add(txtAdminMail);
			txtAdminMail.setColumns(10);
			
			JLabel lblNewLabel_6 = new JLabel("Admin TC Kimlik No");
			lblNewLabel_6.setForeground(new Color(128, 0, 128));
			lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_6.setBounds(27, 166, 136, 31);
			panel_1.add(lblNewLabel_6);
			
			JLabel lblNewLabel_7 = new JLabel("Ad");
			lblNewLabel_7.setForeground(new Color(128, 0, 128));
			lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_7.setBounds(27, 90, 98, 31);
			panel_1.add(lblNewLabel_7);
			
			txtTCAdmin = new JTextField();
			txtTCAdmin.setForeground(new Color(255, 0, 0));
			txtTCAdmin.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtTCAdmin.setBounds(188, 173, 136, 24);
			panel_1.add(txtTCAdmin);
			txtTCAdmin.setColumns(10);
			
			txtAdminName = new JTextField();
			txtAdminName.setForeground(new Color(255, 0, 0));
			txtAdminName.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminName.setBounds(188, 94, 136, 25);
			panel_1.add(txtAdminName);
			txtAdminName.setColumns(10);
			
			JLabel txtadminSurname = new JLabel("Soyad");
			txtadminSurname.setForeground(new Color(128, 0, 128));
			txtadminSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtadminSurname.setBounds(27, 141, 46, 14);
			panel_1.add(txtadminSurname);
			
			txtAdminSurname = new JTextField();
			txtAdminSurname.setForeground(new Color(255, 0, 0));
			txtAdminSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminSurname.setBounds(187, 136, 136, 26);
			panel_1.add(txtAdminSurname);
			txtAdminSurname.setColumns(10);
			
			JButton btnAddAdmin = new JButton("Admin Ekle");
			btnAddAdmin.setBounds(63, 208, 199, 44);
			panel_1.add(btnAddAdmin);
			btnAddAdmin.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\add-user-group-woman-man-icon-add-group-icons-11553399907j3rychc2js_50x50.jpg"));
			btnAddAdmin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//admin ekle
					
					Connection conn = null;
					OraclePreparedStatement pst = null;
					OraclePreparedStatement pst2 = null;
					OracleResultSet rs = null;
					conn = ConnectionClass.dbConnect();
					String newAdminUsername = txtAdminUsername.getText();
					String newAdminPassword = createPasswordForNewUsers();
					String newAdminMail = txtAdminMail.getText();
					String newAdminTC = txtTCAdmin.getText();
					String newAdminName = txtAdminName.getText();
					String newAdminSurname = txtAdminSurname.getText();
					//SimpleDateFormat dateNow = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
					//Date dateHourNow = new Date();
					//String createDate = dateNow.format(dateHourNow);
					//String lastLoginDateAdmin = createDate;
					LocalDateTime localDateTime = LocalDateTime.now();
					LocalTime localTime = localDateTime.toLocalTime();
					LocalDate localDate = localDateTime.toLocalDate();
					String createDate = localDate.toString()+" "+localTime.toString();
					String lastLoginDateAdmin = localDate.toString()+" "+localTime.toString();
					
					
					//String adminCreatedate = Date.parse(df.format(dateNow));
					if(newAdminUsername.length()!=0 && newAdminPassword.length()!=0 && newAdminMail.length()!=0 && newAdminTC.length()!=0 && newAdminName.length()!=0 &&newAdminSurname.length()!=0 ) {
						if(newAdminTC.length()==11) {
							try {
								//String query = "select * from Admin";
								
								String query = "SELECT * FROM SystemUser,Role WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle = 'Admin' AND SystemUser.userName = '" + newAdminUsername + "'";
								
								pst = (OraclePreparedStatement) conn.prepareStatement(query);
							
								rs = (OracleResultSet) pst.executeQuery();
								if(rs.next()) {
									JOptionPane.showMessageDialog(null, "Bu kullanýcý adý zaten sistemde kayýtlý bulunmaktadýr!!");
									
									
									
									
									
									
									
								}
								else {
									
									String queryAdd = "INSERT INTO SystemUser(userNumber,roleNumber,userName,password,mailAdress,name,surname,TCIdentificationNumber,creatDate,lastLoginDate) VALUES(add_systemUser.nextval,"+1+",'"+newAdminUsername+"','"+newAdminPassword+"','"+newAdminMail+"','"+newAdminName+"','"+newAdminSurname+"','"+newAdminTC+"','"+createDate+"','"+lastLoginDateAdmin+"')";
									pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
									int rowValue = pst2.executeUpdate();
									if(rowValue>0) {
										JOptionPane.showMessageDialog(null, "Yeni personel baþarýyla eklendi.");
										listUpdate();
										txtAdminUsername.setText("");
										
										txtAdminMail.setText("");
										txtTCAdmin.setText("");
										txtAdminName.setText("");
										txtAdminSurname.setText("");
										
									}
									else {
										JOptionPane.showMessageDialog(null, "Personel eklenemedi!!");
										
									}
								}
								
							}catch(Exception ex) {
								ex.printStackTrace();
								
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "TC Kimlik numarasý 11 haneli olmalýdýr!!");
						}
						
						
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
					}
					
					
					
					
					/*
					Connection conn = null;
					OraclePreparedStatement pst = null;
					OraclePreparedStatement pst2 = null;
					OracleResultSet rs = null;
					conn = ConnectionClass.dbConnect();
					String newAdminUsername = txtAdminUsername.getText();
					String newAdminPassword = createPasswordForNewUsers();
					String newAdminMail = txtAdminMail.getText();
					String newAdminTC = txtTCAdmin.getText();
					String newAdminName = txtAdminName.getText();
					String newAdminSurname = txtAdminSurname.getText();
					//SimpleDateFormat dateNow = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
					//Date dateHourNow = new Date();
					//String createDate = dateNow.format(dateHourNow);
					//String lastLoginDateAdmin = createDate;
					LocalDateTime localDateTime = LocalDateTime.now();
					LocalTime localTime = localDateTime.toLocalTime();
					LocalDate localDate = localDateTime.toLocalDate();
					String createDate = localDate.toString()+" "+localTime.toString();
					String lastLoginDateAdmin = localDate.toString()+" "+localTime.toString();
					
					
					//String adminCreatedate = Date.parse(df.format(dateNow));
					if(newAdminUsername.length()!=0 && newAdminPassword.length()!=0 && newAdminMail.length()!=0 && newAdminTC.length()!=0 && newAdminName.length()!=0 &&newAdminSurname.length()!=0 ) {
						if(newAdminTC.length()==11) {
							try {
								//String query = "select * from Admin";
								
								String query = "SELECT * FROM Admin WHERE adminUsername = '" + newAdminUsername + "'";
								
								pst = (OraclePreparedStatement) conn.prepareStatement(query);
							
								rs = (OracleResultSet) pst.executeQuery();
								if(rs.next()) {
									JOptionPane.showMessageDialog(null, "Bu kullanýcý adý zaten sistemde kayýtlý bulunmaktadýr!!");
									
									
									
									
									
									
									
								}
								else {
									
									String queryAdd = "INSERT INTO Admin(adminNumber,adminUsername,adminPassword,adminCreatedate,adminMail,TCIdentificationNumber,adminName,adminSurname,lastLoginDate) VALUES(add_admin.nextval,'"+newAdminUsername+"','"+newAdminPassword+"','"+createDate+"','"+newAdminMail+"','"+newAdminTC+"','"+newAdminName+"','"+newAdminSurname+"','"+lastLoginDateAdmin+"')";
									pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
									int rowValue = pst2.executeUpdate();
									if(rowValue>0) {
										JOptionPane.showMessageDialog(null, "Yeni personel baþarýyla eklendi.");
										listUpdate();
										txtAdminUsername.setText("");
										
										txtAdminMail.setText("");
										txtTCAdmin.setText("");
										txtAdminName.setText("");
										txtAdminSurname.setText("");
										
									}
									else {
										JOptionPane.showMessageDialog(null, "Personel eklenemedi!!");
										
									}
								}
								
							}catch(Exception ex) {
								ex.printStackTrace();
								
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "TC Kimlik numarasý 11 haneli olmalýdýr!!");
						}
						
						
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
					}
					*/
					
					
					
				
				
					
					
				}
			});
			btnAddAdmin.setForeground(new Color(128, 0, 0));
			btnAddAdmin.setBackground(new Color(144, 238, 144));
			btnAddAdmin.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			JPanel panel_2 = new JPanel();
			panel_2.setBorder(new LineBorder(new Color(128, 0, 0), 4));
			panel_2.setBounds(172, 11, 423, 37);
			contentPane.add(panel_2);
			panel_2.setLayout(null);
			
			JLabel lblNewLabel_2 = new JLabel("Admin Y\u00F6netim Ekran\u0131");
			lblNewLabel_2.setForeground(new Color(128, 0, 0));
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_2.setBounds(122, 11, 192, 21);
			panel_2.add(lblNewLabel_2);
			
			JPanel panel_5 = new JPanel();
			panel_5.setBorder(new LineBorder(new Color(128, 0, 0), 3));
			panel_5.setBackground(new Color(100, 149, 237));
			panel_5.setBounds(386, 59, 600, 263);
			contentPane.add(panel_5);
			panel_5.setLayout(null);
			
			JPanel panel_3 = new JPanel();
			panel_3.setBounds(345, 11, 229, 241);
			panel_5.add(panel_3);
			panel_3.setBorder(new LineBorder(new Color(0, 0, 128), 4));
			panel_3.setLayout(null);
			
			JButton btnDeleteAdmin = new JButton("Admin Sil");
			btnDeleteAdmin.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\deleteadmin.jpg"));
			btnDeleteAdmin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//silme iþlemi
					
					Connection conn = null;
					OraclePreparedStatement pst = null;
					
					OracleResultSet rs = null;
					conn = ConnectionClass.dbConnect();
					int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili admini silmek istediðinizden emin misiniz?","Admini Sil",JOptionPane.YES_NO_OPTION);
					if(selectedOption == 0) {
						try {
							
							String queryUpdate = "DELETE FROM SystemUser WHERE userNumber = "+adminNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
								listUpdate();
								txtAdminUsernameUpdate.setText("");
								txtAdminMailUpdate.setText("");
								txtAdminPasswordUpdate.setText("");
								txtAdminTCUpdate.setText("");
								txtAdminNameUpdate.setText("");
								txtAdminSurnameUpdate.setText("");
								
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
					
					/*
					Connection conn = null;
					OraclePreparedStatement pst = null;
					
					OracleResultSet rs = null;
					conn = ConnectionClass.dbConnect();
					int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili admini silmek istediðinizden emin misiniz?","Admini Sil",JOptionPane.YES_NO_OPTION);
					if(selectedOption == 0) {
						try {
							
							String queryUpdate = "DELETE FROM Admin WHERE adminNumber = "+adminNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
								listUpdate();
								txtAdminUsernameUpdate.setText("");
								txtAdminMailUpdate.setText("");
								txtAdminPasswordUpdate.setText("");
								txtAdminTCUpdate.setText("");
								txtAdminNameUpdate.setText("");
								txtAdminSurnameUpdate.setText("");
								
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
					*/
					
					
				}
			});
			btnDeleteAdmin.setBackground(new Color(144, 238, 144));
			btnDeleteAdmin.setForeground(new Color(128, 0, 0));
			btnDeleteAdmin.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnDeleteAdmin.setBounds(21, 93, 179, 47);
			panel_3.add(btnDeleteAdmin);
			
			JButton btnUpdateAdmin = new JButton("Admin G\u00FCncelle");
			btnUpdateAdmin.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\updateicon_50x50.jpg"));
			btnUpdateAdmin.setBounds(21, 23, 179, 47);
			panel_3.add(btnUpdateAdmin);
			btnUpdateAdmin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//güncelleme
					
					Connection conn = null;
					OraclePreparedStatement pst = null;
					//OraclePreparedStatement pst2 = null;
					OracleResultSet rs = null;
					conn = ConnectionClass.dbConnect();
					if(txtAdminUsernameUpdate.getText().length()!=0 && txtAdminMailUpdate.getText().length()!=0 && String.valueOf(txtAdminPasswordUpdate.getPassword()).length()!=0 && txtAdminTCUpdate.getText().length()!=0 && txtAdminNameUpdate.getText().length()!=0 && txtAdminSurnameUpdate.getText().length()!=0 ) {
						if(txtAdminTCUpdate.getText().length()==11) {
							int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Admini Güncelle",JOptionPane.YES_NO_OPTION);
							if(selectedOption==0) {
								try {
									//String query = "select * from Admin";
									
									//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
									
									//pst = (OraclePreparedStatement) conn.prepareStatement(query);
								
									//rs = (OracleResultSet) pst.executeQuery();
									String queryUpdate = "UPDATE SystemUser SET userName = '"+txtAdminUsernameUpdate.getText()+"',password ='"+String.valueOf(txtAdminPasswordUpdate.getPassword())+"',mailAdress = '"+txtAdminMailUpdate.getText()+"',TCIdentificationNumber = '"+txtAdminTCUpdate.getText()+"',name = '"+txtAdminNameUpdate.getText()+"',surname = '"+txtAdminSurnameUpdate.getText()+"',creatDate = '"+adminCreatedate+"',lastLoginDate = '"+adminLastLoginDate+"' WHERE userNumber = "+adminNumber;
									pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
									int rowValue = pst.executeUpdate();
									if(rowValue>0) {
										JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
										listUpdate();
										txtAdminUsernameUpdate.setText("");
										txtAdminMailUpdate.setText("");
										txtAdminPasswordUpdate.setText("");
										txtAdminTCUpdate.setText("");
										txtAdminNameUpdate.setText("");
										txtAdminSurnameUpdate.setText("");
										
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
							JOptionPane.showMessageDialog(null, "TC Kimlik numarasý 11 haneli olmalýdýr!!");
						}
						
						
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
					}
					/*
					Connection conn = null;
					OraclePreparedStatement pst = null;
					//OraclePreparedStatement pst2 = null;
					OracleResultSet rs = null;
					conn = ConnectionClass.dbConnect();
					if(txtAdminUsernameUpdate.getText().length()!=0 && txtAdminMailUpdate.getText().length()!=0 && String.valueOf(txtAdminPasswordUpdate.getPassword()).length()!=0 && txtAdminTCUpdate.getText().length()!=0 && txtAdminNameUpdate.getText().length()!=0 && txtAdminSurnameUpdate.getText().length()!=0 ) {
						if(txtAdminTCUpdate.getText().length()==11) {
							int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Admini Güncelle",JOptionPane.YES_NO_OPTION);
							if(selectedOption==0) {
								try {
									//String query = "select * from Admin";
									
									//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
									
									//pst = (OraclePreparedStatement) conn.prepareStatement(query);
								
									//rs = (OracleResultSet) pst.executeQuery();
									String queryUpdate = "UPDATE Admin SET adminUsername = '"+txtAdminUsernameUpdate.getText()+"',adminPassword ='"+String.valueOf(txtAdminPasswordUpdate.getPassword())+"',adminMail = '"+txtAdminMailUpdate.getText()+"',TCIdentificationNumber = '"+txtAdminTCUpdate.getText()+"',adminName = '"+txtAdminNameUpdate.getText()+"',adminSurname = '"+txtAdminSurnameUpdate.getText()+"',adminCreatedate = '"+adminCreatedate+"',lastLoginDate = '"+adminLastLoginDate+"' WHERE adminNumber = "+adminNumber;
									pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
									int rowValue = pst.executeUpdate();
									if(rowValue>0) {
										JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
										listUpdate();
										txtAdminUsernameUpdate.setText("");
										txtAdminMailUpdate.setText("");
										txtAdminPasswordUpdate.setText("");
										txtAdminTCUpdate.setText("");
										txtAdminNameUpdate.setText("");
										txtAdminSurnameUpdate.setText("");
										
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
							JOptionPane.showMessageDialog(null, "TC Kimlik numarasý 11 haneli olmalýdýr!!");
						}
						
						
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalýdýr!!");
					}
					*/
					
					
					
					
					
				}
			});
			btnUpdateAdmin.setBackground(new Color(144, 238, 144));
			btnUpdateAdmin.setForeground(new Color(128, 0, 0));
			btnUpdateAdmin.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			JButton btnClear = new JButton("Alanlar\u0131 Temizle");
			btnClear.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
			btnClear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					//temizleme
					txtAdminUsername.setText("");
					
					txtAdminMail.setText("");
					txtTCAdmin.setText("");
					txtAdminName.setText("");
					txtAdminSurname.setText("");
					
					txtAdminUsernameUpdate.setText("");
					txtAdminMailUpdate.setText("");
					txtAdminPasswordUpdate.setText("");
					txtAdminTCUpdate.setText("");
					txtAdminNameUpdate.setText("");
					txtAdminSurnameUpdate.setText("");
				}
			});
			btnClear.setBackground(new Color(144, 238, 144));
			btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
			btnClear.setForeground(new Color(128, 0, 0));
			btnClear.setBounds(21, 173, 193, 41);
			panel_3.add(btnClear);
			
			JLabel lblNewLabel_4 = new JLabel("Admin Kullan\u0131c\u0131 Ad\u0131");
			lblNewLabel_4.setForeground(new Color(128, 0, 128));
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_4.setBounds(21, 22, 130, 14);
			panel_5.add(lblNewLabel_4);
			
			txtAdminUsernameUpdate = new JTextField();
			txtAdminUsernameUpdate.setForeground(new Color(255, 0, 0));
			txtAdminUsernameUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminUsernameUpdate.setBounds(183, 20, 130, 20);
			panel_5.add(txtAdminUsernameUpdate);
			txtAdminUsernameUpdate.setColumns(10);
			
			JLabel lblNewLabel_8 = new JLabel("Admin Mail");
			lblNewLabel_8.setForeground(new Color(128, 0, 128));
			lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_8.setBounds(21, 53, 92, 14);
			panel_5.add(lblNewLabel_8);
			
			txtAdminMailUpdate = new JTextField();
			txtAdminMailUpdate.setForeground(new Color(255, 0, 0));
			txtAdminMailUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminMailUpdate.setBounds(183, 51, 130, 20);
			panel_5.add(txtAdminMailUpdate);
			txtAdminMailUpdate.setColumns(10);
			
			JLabel lblNewLabel_9 = new JLabel("Ad");
			lblNewLabel_9.setForeground(new Color(128, 0, 128));
			lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_9.setBounds(21, 88, 61, 14);
			panel_5.add(lblNewLabel_9);
			
			txtAdminNameUpdate = new JTextField();
			txtAdminNameUpdate.setForeground(new Color(255, 0, 0));
			txtAdminNameUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminNameUpdate.setBounds(183, 86, 130, 20);
			panel_5.add(txtAdminNameUpdate);
			txtAdminNameUpdate.setColumns(10);
			
			JLabel lblNewLabel_10 = new JLabel("Soyad");
			lblNewLabel_10.setForeground(new Color(128, 0, 128));
			lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_10.setBounds(21, 127, 46, 14);
			panel_5.add(lblNewLabel_10);
			
			JLabel lblNewLabel_11 = new JLabel("TC Kimlik No");
			lblNewLabel_11.setForeground(new Color(128, 0, 128));
			lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel_11.setBounds(21, 173, 92, 14);
			panel_5.add(lblNewLabel_11);
			
			JLabel lblNewLabel_12 = new JLabel("\u015Eifre");
			lblNewLabel_12.setForeground(new Color(128, 0, 128));
			lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_12.setBounds(21, 213, 46, 14);
			panel_5.add(lblNewLabel_12);
			
			txtAdminSurnameUpdate = new JTextField();
			txtAdminSurnameUpdate.setForeground(new Color(255, 0, 0));
			txtAdminSurnameUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminSurnameUpdate.setBounds(183, 125, 130, 20);
			panel_5.add(txtAdminSurnameUpdate);
			txtAdminSurnameUpdate.setColumns(10);
			
			txtAdminTCUpdate = new JTextField();
			txtAdminTCUpdate.setForeground(new Color(255, 0, 0));
			txtAdminTCUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminTCUpdate.setBounds(183, 171, 130, 20);
			panel_5.add(txtAdminTCUpdate);
			txtAdminTCUpdate.setColumns(10);
			
			txtAdminPasswordUpdate = new JPasswordField();
			txtAdminPasswordUpdate.setForeground(new Color(255, 0, 0));
			txtAdminPasswordUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
			txtAdminPasswordUpdate.setBounds(183, 212, 130, 20);
			panel_5.add(txtAdminPasswordUpdate);
			
			JButton btnExitAdminScreen = new JButton("");
			btnExitAdminScreen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnExitAdminScreen.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
			btnExitAdminScreen.setBounds(912, -1, 62, 49);
			contentPane.add(btnExitAdminScreen);
			
			
			
			
		

		
	
		
		
		}
}

