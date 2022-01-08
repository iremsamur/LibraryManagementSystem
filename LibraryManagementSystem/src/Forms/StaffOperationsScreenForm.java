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
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

public class StaffOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	
	private JTable table;
	DefaultTableModel modelim = new DefaultTableModel();
	//Object[] kolonlar = {"Personel No","Personel Kullanýcý Adý","Personel Þifre ","Düzenleyen Admin Adý","Düzenleyen Admin Soyadý","Personel Mail","Personel Telefon Numarasý","Adres","TC Kimlik No","Personel Adý","Personel Soyadý","Personel Kayýt Tarih","Son Sisteme Giriþ Tarih"};
	//Object[] satirlar = new Object[13];
	Object[] kolonlar = {"Personel No","Personel Kullanýcý Adý","Personel Þifre ","Personel Mail","Personel Adý","Personel Soyadý","TC Kimlik No","Personel Kayýt Tarih","Son Sisteme Giriþ Tarih"};
	Object[] satirlar = new Object[9];
	private JTextField txtStaffTCSearch;
	private JTextField txtStaffUsername;
	private JTextField txtStaffName;
	private JTextField txtStaffSurname;
	private JTextField txtStaffMail;
	private JTextField txtStaffTCNo;
	private JTextField txtUpdateStaffUsername;
	private JTextField txtUpdateStaffName;
	private JTextField txtUpdateStaffSurname;
	private JTextField txtUpdateStaffMail;
	private JTextField txtUpdateStaffTC;
	private int staffNumber=0;
	private String staffUsername ="";
	private String staffPassword = "";
	private String staffMail = "";
	//private String staffPhoneNumber = "";
	//private String staffAddress = "";
	private String staffCreatedate = " ";
	
	private String tcIdentificationNumber = "";
	private String staffName = "";
	private String staffSurname = "";
	private String staffLastLoginDate="";
	private JPasswordField pswStaffPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffOperationsScreenForm frame = new StaffOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public OracleResultSet showStaffUsers(String sorgu) {
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
	public static String createPasswordForNewStaffUsers() {
		String password = "";
		 String dizi[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","y","z","x",
		 "0","1","2","3","4","5","6","7","8","9"};
		 
		 for(int i = 0; i < 9; i++){       
		 int rastgele = (1+(int)(Math.random()*35));                     
		 password += dizi[rastgele];
		 }
		 return password;
	}
	public void listStaffs() {
		
		/*
		String sorgu = "SELECT Staff.staffNumber,Staff.staffUsername,Staff.staffPassword,Admin.adminName,Admin.adminSurname,Staff.staffMail,Staff.staffPhoneNumber,Staff.staffAddress,Staff.TCIdentificationNumber,Staff.staffName,Staff.staffSurname,Staff.staffLastLoginDate,Staff.staffCreatedate "+
				"FROM Admin,Staff "+
				"WHERE Admin.adminNumber = Staff.adminNumber ORDER BY Staff.staffNumber ASC";
				*/
		String sorgu = "SELECT * FROM SystemUser,Role WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle = 'Staff' ORDER BY SystemUser.userNumber ASC";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showStaffUsers(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("userNumber");
				satirlar[1] = rs.getString("userName");
				satirlar[2] = rs.getString("password");
				satirlar[3] = rs.getString("mailAdress");
				satirlar[4] = rs.getString("name");
				satirlar[5] = rs.getString("surname");
				satirlar[6] = rs.getString("TCIdentificationNumber");
				satirlar[7] = rs.getString("creatDate");
				satirlar[8] = rs.getString("lastLoginDate");
				
				
				
				
				modelim.addRow(satirlar);
				
			}
			table.setModel(modelim);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		/*
		String sorgu = "SELECT Staff.staffNumber,Staff.staffUsername,Staff.staffPassword,Admin.adminName,Admin.adminSurname,Staff.staffMail,Staff.staffPhoneNumber,Staff.staffAddress,Staff.TCIdentificationNumber,Staff.staffName,Staff.staffSurname,Staff.staffLastLoginDate,Staff.staffCreatedate "+
				"FROM Admin,Staff "+
				"WHERE Admin.adminNumber = Staff.adminNumber ORDER BY Staff.staffNumber ASC";
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showStaffUsers(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("staffNumber");
				satirlar[1] = rs.getString("staffUsername");
				satirlar[2] = rs.getString("staffPassword");
				
				satirlar[3] = rs.getString("adminName");
				
				satirlar[4] = rs.getString("adminSurname");
				satirlar[5] = rs.getString("staffMail");
				satirlar[6] = rs.getString("staffPhoneNumber");
				satirlar[7] = rs.getString("staffAddress");
				satirlar[8] = rs.getString("TCIdentificationNumber");
				satirlar[9] = rs.getString("staffName");
				satirlar[10] = rs.getString("staffSurname");
				satirlar[11] = rs.getString("staffLastLoginDate");
				satirlar[12] = rs.getString("staffCreatedate");
				
				
				
				modelim.addRow(satirlar);
				
			}
			table.setModel(modelim);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		*/
	}
	
	


	
	

	/**
	 * Create the frame.
	 */
	public StaffOperationsScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1047, 738);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 130, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//listUpdate();
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel.setBackground(new Color(135, 206, 235));
		panel.setBounds(10, 391, 1021, 306);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 81, 991, 211);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//güncelleme ve silme
				
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				staffNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				staffUsername = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				staffPassword = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
				staffMail = tblModel.getValueAt(table.getSelectedRow(), 3).toString();
				
				
				staffName = tblModel.getValueAt(table.getSelectedRow(), 4).toString();
				staffSurname = tblModel.getValueAt(table.getSelectedRow(), 5).toString();
				tcIdentificationNumber = tblModel.getValueAt(table.getSelectedRow(), 6).toString();
				staffCreatedate = tblModel.getValueAt(table.getSelectedRow(), 7).toString();
				staffLastLoginDate = tblModel.getValueAt(table.getSelectedRow(), 8).toString();
				txtUpdateStaffUsername.setText(staffUsername);
				txtUpdateStaffName.setText(staffName);
				txtUpdateStaffSurname.setText(staffSurname);
				txtUpdateStaffMail.setText(staffMail);
				
				txtUpdateStaffTC.setText(tcIdentificationNumber);
				pswStaffPassword.setText(staffPassword);
				
				
				
			}
		});
		table.setBackground(new Color(240, 230, 140));
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Sistemde Kay\u0131tl\u0131 Personeller");
		lblNewLabel.setForeground(new Color(139, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(433, 11, 304, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Aramak \u0130stedi\u011Finiz Personelin TC Kimlik Numaras\u0131 ");
		lblNewLabel_1.setForeground(new Color(0, 0, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(278, 36, 304, 34);
		panel.add(lblNewLabel_1);
		
		txtStaffTCSearch = new JTextField();
		txtStaffTCSearch.setForeground(new Color(128, 0, 0));
		txtStaffTCSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtStaffTCSearch.setBounds(592, 36, 206, 34);
		panel.add(txtStaffTCSearch);
		txtStaffTCSearch.setColumns(10);
		
		JButton btnSearchStaff = new JButton("Ara");
		btnSearchStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Personel Arama
				
				//Arama iþlemi
				
				String staffTCNumber = txtStaffTCSearch.getText();
				if(staffTCNumber.length()!=0) {
					String sorgu = "SELECT * FROM SystemUser WHERE TCIdentificationNumber = '" + staffTCNumber + "'";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showStaffUsers(sorgu);
					try {
						while(rs.next()) {
							satirlar[0] = rs.getInt("userNumber");
							satirlar[1] = rs.getString("userName");
							satirlar[2] = rs.getString("password");
							satirlar[3] = rs.getString("mailAdress");
							satirlar[4] = rs.getString("name");
							satirlar[5] = rs.getString("surname");
							satirlar[6] = rs.getString("TCIdentificationNumber");
							satirlar[7] = rs.getString("creatDate");
							satirlar[8] = rs.getString("lastLoginDate");
							
							
							modelim.addRow(satirlar);
							
							
						}
						table.setModel(modelim);
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Aranacak personel TC alaný boþ býrakýlamaz!!! Lütfen aranacak personelin 11 haneli TC Kimlik numarasýný giriniz!!");
				}
				
				/*
				String staffTCNumber = txtStaffTCSearch.getText();
				String sorgu = "SELECT Staff.staffNumber,Staff.staffUsername,Staff.staffPassword,Admin.adminName,Admin.adminSurname,Staff.staffMail,Staff.staffPhoneNumber,Staff.staffAddress,Staff.TCIdentificationNumber,Staff.staffName,Staff.staffSurname,Staff.staffLastLoginDate,Staff.staffCreatedate "+
						"FROM Admin,Staff "+
						"WHERE Admin.adminNumber = Staff.adminNumber AND Staff.TCIdentificationNumber = '"+staffTCNumber+"' ORDER BY Staff.staffNumber ASC";
				modelim.setColumnCount(0);
				modelim.setRowCount(0);
				modelim.setColumnIdentifiers(kolonlar);
				OracleResultSet rs = showStaffUsers(sorgu);
				try {
					while(rs.next()) {
						satirlar[0] = rs.getInt("staffNumber");
						satirlar[1] = rs.getString("staffUsername");
						satirlar[2] = rs.getString("staffPassword");
						
						satirlar[3] = rs.getString("adminName");
						
						satirlar[4] = rs.getString("adminSurname");
						satirlar[5] = rs.getString("staffMail");
						satirlar[6] = rs.getString("staffPhoneNumber");
						satirlar[7] = rs.getString("staffAddress");
						satirlar[8] = rs.getString("TCIdentificationNumber");
						satirlar[9] = rs.getString("staffName");
						satirlar[10] = rs.getString("staffSurname");
						satirlar[11] = rs.getString("staffLastLoginDate");
						satirlar[12] = rs.getString("staffCreatedate");
						
						
						modelim.addRow(satirlar);
						
						
					}
					table.setModel(modelim);
				}
				catch(Exception ex) {
					ex.printStackTrace();
					
				}
				*/
			}
		});
		btnSearchStaff.setBackground(new Color(0, 128, 128));
		btnSearchStaff.setForeground(new Color(128, 0, 128));
		btnSearchStaff.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSearchStaff.setBounds(837, 34, 89, 36);
		panel.add(btnSearchStaff);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_1.setBounds(230, 11, 395, 33);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Personel Y\u00F6netim Paneli");
		lblNewLabel_2.setForeground(new Color(0, 0, 128));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(98, 11, 200, 14);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(100, 149, 237));
		panel_2.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_2.setBounds(10, 55, 459, 325);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setForeground(new Color(128, 0, 128));
		lblNewLabel_3.setBounds(10, 26, 87, 26);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Personel Ad\u0131 ");
		lblNewLabel_4.setForeground(new Color(128, 0, 128));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(10, 78, 87, 15);
		panel_2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Personel Soyad\u0131");
		lblNewLabel_5.setForeground(new Color(128, 0, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(10, 122, 104, 14);
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Mail Adresi");
		lblNewLabel_6.setForeground(new Color(128, 0, 128));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(10, 165, 104, 15);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_8 = new JLabel("TC Kimlik Numaras\u0131");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setForeground(new Color(128, 0, 128));
		lblNewLabel_8.setBounds(10, 214, 118, 14);
		panel_2.add(lblNewLabel_8);
		
		txtStaffUsername = new JTextField();
		txtStaffUsername.setForeground(new Color(255, 0, 0));
		txtStaffUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtStaffUsername.setBounds(145, 27, 125, 26);
		panel_2.add(txtStaffUsername);
		txtStaffUsername.setColumns(10);
		
		txtStaffName = new JTextField();
		txtStaffName.setForeground(new Color(255, 0, 0));
		txtStaffName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtStaffName.setBounds(145, 73, 125, 26);
		panel_2.add(txtStaffName);
		txtStaffName.setColumns(10);
		
		txtStaffSurname = new JTextField();
		txtStaffSurname.setForeground(new Color(255, 0, 0));
		txtStaffSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtStaffSurname.setBounds(145, 120, 125, 26);
		panel_2.add(txtStaffSurname);
		txtStaffSurname.setColumns(10);
		
		txtStaffMail = new JTextField();
		txtStaffMail.setForeground(new Color(255, 0, 0));
		txtStaffMail.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtStaffMail.setBounds(145, 160, 125, 26);
		panel_2.add(txtStaffMail);
		txtStaffMail.setColumns(10);
		
		txtStaffTCNo = new JTextField();
		txtStaffTCNo.setForeground(new Color(255, 0, 0));
		txtStaffTCNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtStaffTCNo.setBounds(145, 209, 125, 26);
		panel_2.add(txtStaffTCNo);
		txtStaffTCNo.setColumns(10);
		
		JButton btnStaffAdd = new JButton("Personel Ekle");
		btnStaffAdd.setBounds(82, 257, 202, 57);
		panel_2.add(btnStaffAdd);
		btnStaffAdd.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\add-user-group-woman-man-icon-add-group-icons-11553399907j3rychc2js_50x50.jpg"));
		btnStaffAdd.setBackground(new Color(144, 238, 144));
		btnStaffAdd.setForeground(new Color(128, 0, 128));
		btnStaffAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnStaffAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Personel Ekle
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String staffUsername = txtStaffUsername.getText();
				String staffName = txtStaffName.getText();
				String staffSurname = txtStaffSurname.getText();
				String staffMail = txtStaffMail.getText();
				//String staffPhoneNumber = txtStaffPhoneNumber.getText();
				String staffTC = txtStaffTCNo.getText();
				//String staffAddress = txtStaffAddress.getText();
				String staffPassword = createPasswordForNewStaffUsers();
				//int adminID = AdminScreenForm.loggedAdminID;
				//String staffDeleteControl = "True";
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String createDate = localDate.toString()+" "+localTime.toString();
				String lastLoginDateStaff = localDate.toString()+" "+localTime.toString();
				if(staffUsername.length()!=0 && staffPassword.length()!=0 && staffMail.length()!=0 && staffTC.length()!=0 && staffName.length()!=0 &&staffSurname.length()!=0 ) {
					if(staffTC.length()==11) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM SystemUser,Role WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle = 'Staff' AND SystemUser.userName = '" + staffUsername + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								JOptionPane.showMessageDialog(null, "Bu kullanýcý adý zaten sistemde kayýtlý bulunmaktadýr!!");
								
								
								
								
								
								
								
							}
							else {
								
								//String queryAdd = "INSERT INTO Staff(staffNumber,staffUsername,staffPassword,adminNumber,staffMail,staffPhoneNumber,staffAddress,staffDeleteControl,TCIdentificationNumber,staffName,staffSurname,staffLastLoginDate,staffCreatedate) VALUES(add_staff.nextval,'"+staffUsername+"','"+staffPassword+"',"+adminID+",'"+staffMail+"','"+staffPhoneNumber+"','"+staffAddress+"','"+staffDeleteControl+"','"+staffTC+"','"+staffName+"','"+staffSurname+"','"+lastLoginDateStaff+"','"+createDate+"')";
								String queryAdd = "INSERT INTO SystemUser(userNumber,roleNumber,userName,password,mailAdress,name,surname,TCIdentificationNumber,creatDate,lastLoginDate) VALUES(add_systemUser.nextval,"+2+",'"+staffUsername+"','"+staffPassword+"','"+staffMail+"','"+staffName+"','"+staffSurname+"','"+staffTC+"','"+createDate+"','"+lastLoginDateStaff+"')";
								pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
								int rowValue = pst2.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, "Yeni personel baþarýyla eklendi.");
									listStaffs();
									txtStaffUsername.setText("");
									txtStaffName.setText("");
									txtStaffSurname.setText("");
									
									txtStaffMail.setText("");
									//txtStaffPhoneNumber.setText("");
									txtStaffTCNo.setText("");
									
									
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
				String staffUsername = txtStaffUsername.getText();
				String staffName = txtStaffName.getText();
				String staffSurname = txtStaffSurname.getText();
				String staffMail = txtStaffMail.getText();
				String staffPhoneNumber = txtStaffPhoneNumber.getText();
				String staffTC = txtStaffTCNo.getText();
				String staffAddress = txtStaffAddress.getText();
				String staffPassword = createPasswordForNewStaffUsers();
				int adminID = AdminScreenForm.loggedAdminID;
				String staffDeleteControl = "True";
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String createDate = localDate.toString()+" "+localTime.toString();
				String lastLoginDateStaff = localDate.toString()+" "+localTime.toString();
				if(staffUsername.length()!=0 && staffPassword.length()!=0 && staffMail.length()!=0 && staffTC.length()!=0 && staffName.length()!=0 &&staffSurname.length()!=0 && staffPhoneNumber.length()!=0 && staffAddress.length()!=0 ) {
					if(staffTC.length()==11) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM Staff WHERE staffUsername = '" + staffUsername + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								JOptionPane.showMessageDialog(null, "Bu kullanýcý adý zaten sistemde kayýtlý bulunmaktadýr!!");
								
								
								
								
								
								
								
							}
							else {
								
								String queryAdd = "INSERT INTO Staff(staffNumber,staffUsername,staffPassword,adminNumber,staffMail,staffPhoneNumber,staffAddress,staffDeleteControl,TCIdentificationNumber,staffName,staffSurname,staffLastLoginDate,staffCreatedate) VALUES(add_staff.nextval,'"+staffUsername+"','"+staffPassword+"',"+adminID+",'"+staffMail+"','"+staffPhoneNumber+"','"+staffAddress+"','"+staffDeleteControl+"','"+staffTC+"','"+staffName+"','"+staffSurname+"','"+lastLoginDateStaff+"','"+createDate+"')";
								pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
								int rowValue = pst2.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, "Yeni personel baþarýyla eklendi.");
									listStaffs();
									
									
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
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(100, 149, 237));
		panel_3.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_3.setBounds(479, 55, 552, 325);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_10 = new JLabel("Kullan\u0131c\u0131 Ad\u0131");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setForeground(new Color(128, 0, 128));
		lblNewLabel_10.setBounds(10, 10, 98, 23);
		panel_3.add(lblNewLabel_10);
		
		JLabel lblNewLabel_12 = new JLabel("Personel Soyad\u0131");
		lblNewLabel_12.setForeground(new Color(128, 0, 128));
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_12.setBounds(10, 141, 98, 23);
		panel_3.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("Mail Adresi");
		lblNewLabel_13.setForeground(new Color(128, 0, 128));
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_13.setBounds(10, 190, 98, 23);
		panel_3.add(lblNewLabel_13);
		
		txtUpdateStaffUsername = new JTextField();
		txtUpdateStaffUsername.setForeground(new Color(255, 0, 0));
		txtUpdateStaffUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateStaffUsername.setBounds(137, 5, 131, 34);
		panel_3.add(txtUpdateStaffUsername);
		txtUpdateStaffUsername.setColumns(10);
		
		txtUpdateStaffSurname = new JTextField();
		txtUpdateStaffSurname.setForeground(new Color(255, 0, 0));
		txtUpdateStaffSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateStaffSurname.setBounds(137, 136, 131, 34);
		panel_3.add(txtUpdateStaffSurname);
		txtUpdateStaffSurname.setColumns(10);
		
		txtUpdateStaffMail = new JTextField();
		txtUpdateStaffMail.setForeground(new Color(255, 0, 0));
		txtUpdateStaffMail.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateStaffMail.setBounds(137, 185, 131, 34);
		panel_3.add(txtUpdateStaffMail);
		txtUpdateStaffMail.setColumns(10);
		
		JLabel lblNewLabel_15 = new JLabel("TC Kimlik No");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_15.setForeground(new Color(128, 0, 128));
		lblNewLabel_15.setBounds(10, 243, 98, 35);
		panel_3.add(lblNewLabel_15);
		
		txtUpdateStaffTC = new JTextField();
		txtUpdateStaffTC.setForeground(new Color(255, 0, 0));
		txtUpdateStaffTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateStaffTC.setBounds(137, 244, 131, 34);
		panel_3.add(txtUpdateStaffTC);
		txtUpdateStaffTC.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_5.setBackground(new Color(255, 255, 255));
		panel_5.setBounds(295, 23, 247, 278);
		panel_3.add(panel_5);
		panel_5.setLayout(null);
		
		JButton btnStaffUpdate = new JButton("Personel G\u00FCncelle");
		btnStaffUpdate.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\updateicon_50x50.jpg"));
		btnStaffUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Personel Güncelle
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				//OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
	            //String staffDeleteControl = "True";
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String createDate = localDate.toString()+" "+localTime.toString();
				//int adminID = AdminScreenForm.loggedAdminID;
				
				
				if(txtUpdateStaffUsername.getText().length()!=0 && txtUpdateStaffName.getText().length()!=0 && String.valueOf(pswStaffPassword.getPassword()).length()!=0 && txtUpdateStaffSurname.getText().length()!=0 && txtUpdateStaffMail.getText().length()!=0 && txtUpdateStaffTC.getText().length()!=0 ) {
					if(txtUpdateStaffTC.getText().length()==11) {
						int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Personeli Güncelle",JOptionPane.YES_NO_OPTION);
						if(selectedOption==0) {
							try {
								//String query = "select * from Admin";
								
								//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
								
								//pst = (OraclePreparedStatement) conn.prepareStatement(query);
							
								//rs = (OracleResultSet) pst.executeQuery();
								//String queryUpdate = "UPDATE Staff SET staffUsername = '"+txtUpdateStaffUsername.getText()+"',staffPassword ='"+String.valueOf(pswStaffPassword.getPassword())+"', adminNumber = "+adminID+",staffMail = '"+txtUpdateStaffMail.getText()+"',staffPhoneNumber = '"+txtUpdateStaffPhone.getText()+"',staffAddress = '"+txtUpdateStaffAddress.getText()+"',staffName = '"+txtUpdateStaffName.getText()+"',staffSurname = '"+txtUpdateStaffSurname.getText()+"',staffCreatedate = '"+createDate+"' WHERE staffNumber = "+staffNumber;
								//String queryUpdate = "UPDATE SystemUser SET userName = '"+txtUpdateStaffUsername.getText()+"',password ='"+String.valueOf(pswStaffPassword.getPassword())+"',mailAdress = '"+txtUpdateStaffMail.getText()+"',TCIdentificationNumber = '"+txtUpdateStaffTC.getText()+"',name = '"+txtUpdateStaffName.getText()+"',surname = '"+txtUpdateStaffSurname.getText()+"',creatDate = '"+createDate+"',lastLoginDate = '"+staffLastLoginDate+"' WHERE userNumber = "+staffNumber;
								String queryUpdate = "UPDATE SystemUser SET userName = '"+txtUpdateStaffUsername.getText()+"',password ='"+
								String.valueOf(pswStaffPassword.getPassword())+"',mailAdress = '"+txtUpdateStaffMail.getText()+"',TCIdentificationNumber = '"+
										txtUpdateStaffTC.getText()+"',name = '"+txtUpdateStaffName.getText()+"',surname = '"+txtUpdateStaffSurname.getText()+
										"' WHERE userNumber = "+staffNumber;
								pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
								int rowValue = pst.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
									listStaffs();
									txtUpdateStaffUsername.setText("");
									txtUpdateStaffName.setText("");
									txtUpdateStaffSurname.setText("");
									txtUpdateStaffMail.setText("");
									
									txtUpdateStaffTC.setText("");
									pswStaffPassword.setText("");
									
									
									
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
	            String staffDeleteControl = "True";
				
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String createDate = localDate.toString()+" "+localTime.toString();
				int adminID = AdminScreenForm.loggedAdminID;
				
				
				if(txtUpdateStaffUsername.getText().length()!=0 && txtUpdateStaffName.getText().length()!=0 && String.valueOf(pswStaffPassword.getPassword()).length()!=0 && txtUpdateStaffSurname.getText().length()!=0 && txtUpdateStaffMail.getText().length()!=0 && txtUpdateStaffPhone.getText().length()!=0 && txtUpdateStaffTC.getText().length()!=0 && txtUpdateStaffAddress.getText().length()!=0 ) {
					if(txtUpdateStaffTC.getText().length()==11) {
						int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Personeli Güncelle",JOptionPane.YES_NO_OPTION);
						if(selectedOption==0) {
							try {
								//String query = "select * from Admin";
								
								//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
								
								//pst = (OraclePreparedStatement) conn.prepareStatement(query);
							
								//rs = (OracleResultSet) pst.executeQuery();
								String queryUpdate = "UPDATE Staff SET staffUsername = '"+txtUpdateStaffUsername.getText()+"',staffPassword ='"+String.valueOf(pswStaffPassword.getPassword())+"', adminNumber = "+adminID+",staffMail = '"+txtUpdateStaffMail.getText()+"',staffPhoneNumber = '"+txtUpdateStaffPhone.getText()+"',staffAddress = '"+txtUpdateStaffAddress.getText()+"',staffName = '"+txtUpdateStaffName.getText()+"',staffSurname = '"+txtUpdateStaffSurname.getText()+"',staffCreatedate = '"+createDate+"' WHERE staffNumber = "+staffNumber;
								pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
								int rowValue = pst.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
									listStaffs();
									txtUpdateStaffUsername.setText("");
									txtUpdateStaffName.setText("");
									txtUpdateStaffUsername.setText("");
									txtUpdateStaffMail.setText("");
									txtUpdateStaffPhone.setText("");
									txtUpdateStaffTC.setText("");
									pswStaffPassword.setText("");
									txtUpdateStaffAddress.setText("");
									
									
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
		btnStaffUpdate.setBackground(new Color(144, 238, 144));
		btnStaffUpdate.setForeground(new Color(128, 0, 128));
		btnStaffUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnStaffUpdate.setBounds(21, 11, 205, 76);
		panel_5.add(btnStaffUpdate);
		
		JButton btnStaffDelete = new JButton("Personel Sil");
		btnStaffDelete.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\deleteadmin.jpg"));
		btnStaffDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//silme
				
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili personeli silmek istediðinizden emin misiniz?","Admini Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM SystemUser WHERE userNumber = "+staffNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listStaffs();
							txtUpdateStaffUsername.setText("");
							txtUpdateStaffName.setText("");
							txtUpdateStaffSurname.setText("");
							txtUpdateStaffMail.setText("");
							
							txtUpdateStaffTC.setText("");
							pswStaffPassword.setText("");
							
							
							
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
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili personeli silmek istediðinizden emin misiniz?","Admini Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM Staff WHERE staffNumber = "+staffNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listStaffs();
							txtUpdateStaffUsername.setText("");
							txtUpdateStaffName.setText("");
							txtUpdateStaffUsername.setText("");
							txtUpdateStaffMail.setText("");
							txtUpdateStaffPhone.setText("");
							txtUpdateStaffTC.setText("");
							pswStaffPassword.setText("");
							txtUpdateStaffAddress.setText("");
							
							
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
		btnStaffDelete.setBackground(new Color(144, 238, 144));
		btnStaffDelete.setForeground(new Color(128, 0, 128));
		btnStaffDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnStaffDelete.setBounds(21, 110, 205, 76);
		panel_5.add(btnStaffDelete);
		
		JButton btnClearField = new JButton("Alanlar\u0131 Temizle");
		btnClearField.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnClearField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//temizle
				
				txtStaffUsername.setText("");
				txtStaffName.setText("");
				txtStaffSurname.setText("");
				txtStaffMail.setText("");
				//txtStaffPhoneNumber.setText("");
				txtStaffTCNo.setText("");
				//txtStaffAddress.setText("");
				txtUpdateStaffUsername.setText("");
				txtUpdateStaffName.setText("");
				txtUpdateStaffSurname.setText("");
				txtUpdateStaffMail.setText("");
				
				txtUpdateStaffTC.setText("");
				pswStaffPassword.setText("");
				
				
			}
		});
		btnClearField.setBackground(new Color(144, 238, 144));
		btnClearField.setForeground(new Color(128, 0, 128));
		btnClearField.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClearField.setBounds(21, 197, 205, 70);
		panel_5.add(btnClearField);
		
		pswStaffPassword = new JPasswordField();
		pswStaffPassword.setForeground(new Color(255, 0, 0));
		pswStaffPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		pswStaffPassword.setBounds(137, 50, 131, 30);
		panel_3.add(pswStaffPassword);
		
		JLabel lblNewLabel_17 = new JLabel("Personel \u015Eifre");
		lblNewLabel_17.setForeground(new Color(128, 0, 128));
		lblNewLabel_17.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_17.setBounds(10, 44, 98, 28);
		panel_3.add(lblNewLabel_17);
		
		JLabel lblNewLabel_11 = new JLabel("Personel Ad\u0131");
		lblNewLabel_11.setBounds(10, 94, 98, 23);
		panel_3.add(lblNewLabel_11);
		lblNewLabel_11.setForeground(new Color(128, 0, 128));
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		txtUpdateStaffName = new JTextField();
		txtUpdateStaffName.setBounds(137, 91, 131, 34);
		panel_3.add(txtUpdateStaffName);
		txtUpdateStaffName.setForeground(new Color(255, 0, 0));
		txtUpdateStaffName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateStaffName.setColumns(10);
		
		JButton btnExitStaff = new JButton("");
		btnExitStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Çýkýþ
				dispose();
			}
		});
		btnExitStaff.setBackground(new Color(0, 0, 128));
		btnExitStaff.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitStaff.setBounds(960, 0, 71, 44);
		contentPane.add(btnExitStaff);
		listStaffs();
		
		
		
		
	}
}
