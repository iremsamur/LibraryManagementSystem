package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class CurrentMemberRegistrationOperationScreen extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel modelim = new DefaultTableModel();
	
	Object[] kolonlar = {"Üye No","Adý","Soyadý","TC Kimlik Numarasý","Doðum Tarihi","Mail Adresi","Cep Telefon Numarasý","Cinsiyet","Adres","Ekleyen Personel Adý","Ekleyen Personel Soyadý","Kayýt Tarihi","Son Güncellenme Tarihi"};
	Object[] satirlar = new Object[13];
	private JTextField txtCurrentMemberTC;
	private JTextField txtMemberName;
	private JTextField txtMemberSurname;
	private JTextField txtMemberTC;
	private JTextField txtMemberPhoneNumber;
	private JTextField txtMemberMail;
	private int memberNumber=0;
	private String memberName ="";
	private String memberSurname = "";
	private String memberTC = "";
	private String memberBirthDate = "";
	private String memberMail = "";
	private String memberPhone = "";
	private String memberGender = "";
	private String memberAddress = "";
	
	private String memberCreatedate = " ";
	
	
	private String memberLastUpdateDate="";
	JDateChooser dateChooser = null;
	JComboBox cmbMemberGender=null;
	JTextArea txtMemberAdress=null;
	private JTextField txtBirthDate;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CurrentMemberRegistrationOperationScreen frame = new CurrentMemberRegistrationOperationScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showMembers(sorgu);
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
				satirlar[9] = rs.getString("name");
				satirlar[10] = rs.getString("surname");
				satirlar[11] = rs.getString("creatDate");
				satirlar[12] = rs.getString("lastUpdateDate");
				
				
				
				
				
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
	public CurrentMemberRegistrationOperationScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1190, 713);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel.setBackground(new Color(173, 216, 230));
		panel.setBounds(10, 364, 1135, 300);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 1115, 183);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//güncelleme ve silme
				
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				memberNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				memberName = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				memberSurname = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
				memberTC = tblModel.getValueAt(table.getSelectedRow(), 3).toString();
				
				
				memberBirthDate = tblModel.getValueAt(table.getSelectedRow(), 4).toString();
				memberMail = tblModel.getValueAt(table.getSelectedRow(), 5).toString();
				memberPhone = tblModel.getValueAt(table.getSelectedRow(), 6).toString();
				memberGender = tblModel.getValueAt(table.getSelectedRow(), 7).toString();
				memberAddress = tblModel.getValueAt(table.getSelectedRow(), 8).toString();
				memberCreatedate = tblModel.getValueAt(table.getSelectedRow(), 11).toString();
				memberLastUpdateDate = tblModel.getValueAt(table.getSelectedRow(), 12).toString();
				txtMemberName.setText(memberName);
				txtMemberSurname.setText(memberSurname);
				txtMemberTC.setText(memberTC);
				if(memberGender.equals("K")) {
					cmbMemberGender.setSelectedItem("Kadýn");
					
				}
				else {
					cmbMemberGender.setSelectedItem("Erkek");
					
				}
				
				
				
				
				dateChooser.setDate(null);
				txtMemberMail.setText(memberMail);
				txtMemberPhoneNumber.setText(memberPhone);
			
				txtMemberAdress.setText(memberAddress);
				txtBirthDate.setText(memberBirthDate);
				
				
				
				
			}
		});
		table.setForeground(new Color(139, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.setBackground(new Color(221, 160, 221));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Sistemde Kay\u0131tl\u0131 \u00DCyeler");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(444, 0, 215, 38);
		panel.add(lblNewLabel);
		
		txtCurrentMemberTC = new JTextField();
		txtCurrentMemberTC.setForeground(new Color(0, 0, 128));
		txtCurrentMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtCurrentMemberTC.setBounds(454, 49, 224, 32);
		panel.add(txtCurrentMemberTC);
		txtCurrentMemberTC.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("TC Kimlik Numaras\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(322, 48, 121, 32);
		panel.add(lblNewLabel_1);
		
		JButton btnCurrentMemberSearch = new JButton("Ara");
		btnCurrentMemberSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//search - üye arama
                //Arama iþlemi
				
				String memberTCNumber = txtCurrentMemberTC.getText();
				if(memberTCNumber.length()!=0) {
					String sorgu = "SELECT * FROM LibraryMember,SystemUser WHERE SystemUser.userNumber = LibraryMember.addedStaffNumber "
							+ "AND LibraryMember.TCIdentificationNumber = '"+memberTCNumber+"' ORDER BY LibraryMember.memberID ASC";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showMembers(sorgu);
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
							satirlar[9] = rs.getString("name");
							satirlar[10] = rs.getString("surname");
							satirlar[11] = rs.getString("creatDate");
							satirlar[12] = rs.getString("lastUpdateDate");
							
							
							modelim.addRow(satirlar);
							
							
						}
						table.setModel(modelim);
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Aranacak üye TC alaný boþ býrakýlamaz!!! Lütfen aranacak üyenin 11 haneli TC Kimlik numarasýný giriniz!!");
				}
				
			}
		});
		btnCurrentMemberSearch.setBackground(new Color(255, 0, 255));
		btnCurrentMemberSearch.setForeground(new Color(128, 0, 128));
		btnCurrentMemberSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCurrentMemberSearch.setBounds(707, 48, 89, 32);
		panel.add(btnCurrentMemberSearch);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(416, 0, 294, 31);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("\u00DCye Y\u00F6netim Paneli");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(61, 0, 177, 30);
		panel_1.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_2.setBackground(new Color(255, 192, 203));
		panel_2.setBounds(10, 63, 803, 290);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Ad\u0131");
		lblNewLabel_1_1_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1_2.setBounds(10, 18, 95, 37);
		panel_2.add(lblNewLabel_1_1_2);
		
		txtMemberName = new JTextField();
		txtMemberName.setForeground(new Color(0, 0, 128));
		txtMemberName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberName.setColumns(10);
		txtMemberName.setBounds(160, 23, 131, 28);
		panel_2.add(txtMemberName);
		
		JLabel lblNewLabel_2_1 = new JLabel("Soyad\u0131");
		lblNewLabel_2_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2_1.setBounds(10, 66, 114, 28);
		panel_2.add(lblNewLabel_2_1);
		
		txtMemberSurname = new JTextField();
		txtMemberSurname.setForeground(new Color(0, 0, 128));
		txtMemberSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberSurname.setColumns(10);
		txtMemberSurname.setBounds(161, 62, 131, 28);
		panel_2.add(txtMemberSurname);
		
		JLabel lblNewLabel_4 = new JLabel("TC Kimlik Numaras\u0131");
		lblNewLabel_4.setForeground(new Color(128, 0, 0));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(10, 105, 131, 29);
		panel_2.add(lblNewLabel_4);
		
		txtMemberTC = new JTextField();
		txtMemberTC.setForeground(new Color(0, 0, 128));
		txtMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberTC.setColumns(10);
		txtMemberTC.setBounds(160, 106, 131, 28);
		panel_2.add(txtMemberTC);
		
		JLabel lblNewLabel_5 = new JLabel("Do\u011Fum Tarihi");
		lblNewLabel_5.setForeground(new Color(128, 0, 0));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(10, 215, 114, 37);
		panel_2.add(lblNewLabel_5);
		
		dateChooser = new JDateChooser();
		
		dateChooser.getCalendarButton().setForeground(new Color(0, 0, 128));
		dateChooser.getCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateChooser.setBounds(272, 215, 19, 37);
		panel_2.add(dateChooser);
		
		JLabel lblNewLabel_7 = new JLabel("Cep Telefon Numaras\u0131");
		lblNewLabel_7.setForeground(new Color(128, 0, 0));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(5, 161, 136, 20);
		panel_2.add(lblNewLabel_7);
		
		txtMemberPhoneNumber = new JTextField();
		txtMemberPhoneNumber.setForeground(new Color(0, 0, 128));
		txtMemberPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberPhoneNumber.setColumns(10);
		txtMemberPhoneNumber.setBounds(160, 155, 131, 33);
		panel_2.add(txtMemberPhoneNumber);
		
		JLabel lblNewLabel_6 = new JLabel("Mail Adresi");
		lblNewLabel_6.setForeground(new Color(128, 0, 0));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(394, 24, 74, 25);
		panel_2.add(lblNewLabel_6);
		
		txtMemberMail = new JTextField();
		txtMemberMail.setForeground(new Color(0, 0, 128));
		txtMemberMail.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtMemberMail.setColumns(10);
		txtMemberMail.setBounds(531, 23, 208, 28);
		panel_2.add(txtMemberMail);
		
		JLabel lblNewLabel_8 = new JLabel("Cinsiyet");
		lblNewLabel_8.setForeground(new Color(128, 0, 0));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setBounds(394, 82, 74, 14);
		panel_2.add(lblNewLabel_8);
		
		cmbMemberGender = new JComboBox();
		cmbMemberGender.setModel(new DefaultComboBoxModel(new String[] {"--Cinsiyet Se\u00E7iniz--", "Kad\u0131n", "Erkek"}));
		cmbMemberGender.setForeground(new Color(0, 0, 128));
		cmbMemberGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		cmbMemberGender.setBounds(531, 62, 208, 37);
		panel_2.add(cmbMemberGender);
		
		JLabel lblNewLabel_9 = new JLabel("Adres");
		lblNewLabel_9.setForeground(new Color(128, 0, 0));
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_9.setBounds(394, 124, 75, 25);
		panel_2.add(lblNewLabel_9);
		
		txtMemberAdress = new JTextArea();
		txtMemberAdress.setForeground(new Color(0, 0, 128));
		txtMemberAdress.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtMemberAdress.setBounds(478, 126, 315, 141);
		panel_2.add(txtMemberAdress);
		
		txtBirthDate = new JTextField();
		txtBirthDate.setForeground(new Color(0, 0, 128));
		txtBirthDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtBirthDate.setBounds(158, 216, 114, 37);
		panel_2.add(txtBirthDate);
		txtBirthDate.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_3.setBackground(new Color(255, 192, 203));
		panel_3.setBounds(847, 63, 294, 290);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnUpdateMember = new JButton("G\u00FCncelle");
		btnUpdateMember.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\istockphoto-1146313245-612x612_50x50.jpg"));
		btnUpdateMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//güncelle
				
				
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
				String currentMemberBirthDate = "";
				if(dateChooser.getDate()!=null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					currentMemberBirthDate = sdf.format(dateChooser.getDate());
					
				}
				else {
					currentMemberBirthDate = memberBirthDate;
				}
				txtBirthDate.setText(currentMemberBirthDate);
				String memberGenderValue = cmbMemberGender.getSelectedItem().toString();
				String memberGender2="";
				if(memberGenderValue.equals("Kadýn")) {
					memberGender2="K";
					
					
				}
				else if(memberGenderValue.equals("Erkek")) {
					memberGender2="E";
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bir cinsiyet seçimi yapýnýz");
					
				}
				
				//int adminID = AdminScreenForm.loggedAdminID;
				
				
				if(txtMemberName.getText().length()!=0 && txtMemberSurname.getText().length()!=0 && txtMemberTC.getText().length()!=0 && txtMemberMail.getText().length()!=0 && txtMemberPhoneNumber.getText().length()!=0 && cmbMemberGender.getSelectedIndex()!=-1 && txtMemberAdress.getText().length()!=0) {
					if(txtMemberTC.getText().length()==11) {
						int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Üyeyi Güncelle",JOptionPane.YES_NO_OPTION);
						if(selectedOption==0) {
							try {
								//String query = "select * from Admin";
								
								//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
								
								//pst = (OraclePreparedStatement) conn.prepareStatement(query);
							
								//rs = (OracleResultSet) pst.executeQuery();
								//String queryUpdate = "UPDATE Staff SET staffUsername = '"+txtUpdateStaffUsername.getText()+"',staffPassword ='"+String.valueOf(pswStaffPassword.getPassword())+"', adminNumber = "+adminID+",staffMail = '"+txtUpdateStaffMail.getText()+"',staffPhoneNumber = '"+txtUpdateStaffPhone.getText()+"',staffAddress = '"+txtUpdateStaffAddress.getText()+"',staffName = '"+txtUpdateStaffName.getText()+"',staffSurname = '"+txtUpdateStaffSurname.getText()+"',staffCreatedate = '"+createDate+"' WHERE staffNumber = "+staffNumber;
								//String queryUpdate = "UPDATE SystemUser SET userName = '"+txtUpdateStaffUsername.getText()+"',password ='"+String.valueOf(pswStaffPassword.getPassword())+"',mailAdress = '"+txtUpdateStaffMail.getText()+"',TCIdentificationNumber = '"+txtUpdateStaffTC.getText()+"',name = '"+txtUpdateStaffName.getText()+"',surname = '"+txtUpdateStaffSurname.getText()+"',creatDate = '"+createDate+"',lastLoginDate = '"+staffLastLoginDate+"' WHERE userNumber = "+staffNumber;
								String queryUpdate = "UPDATE LibraryMember SET memberName = '"+txtMemberName.getText()+"',memberSurname ='"+txtMemberSurname.getText()+"',TCIdentificationNumber = '"+txtMemberTC.getText()+"',memberBirthDate = '"+currentMemberBirthDate+"',memberMail = '"+txtMemberMail.getText()+"',memberPhoneNumber = '"+txtMemberPhoneNumber.getText()+"',memberGender = '"+memberGender2+"', memberAddress = '"+txtMemberAdress.getText()+"',lastUpdateDate = '"+lastUpdateDate+"' WHERE memberID = "+memberNumber;
								pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
								int rowValue = pst.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
									listMembers();
									txtMemberName.setText("");
									txtMemberSurname.setText("");
									txtMemberTC.setText("");
									txtMemberPhoneNumber.setText("");
									txtBirthDate.setText("");
									txtMemberMail.setText("");
									cmbMemberGender.setSelectedItem("--Cinsiyet Seçiniz--");
									txtMemberAdress.setText("");
								
									
									
									
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
			}
		});
		btnUpdateMember.setBackground(new Color(152, 251, 152));
		btnUpdateMember.setForeground(new Color(128, 0, 0));
		btnUpdateMember.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUpdateMember.setBounds(45, 11, 208, 64);
		panel_3.add(btnUpdateMember);
		
		JButton btnDeleteMember = new JButton("Sil");
		btnDeleteMember.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\deleteadmin.jpg"));
		btnDeleteMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//silme
				String currentMemberBirthDate = "";
				if(dateChooser.getDate()!=null) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					currentMemberBirthDate = sdf.format(dateChooser.getDate());
					
				}
				else {
					currentMemberBirthDate = memberBirthDate;
				}
				String memberGender2="";
				String memberGenderValue = cmbMemberGender.getSelectedItem().toString();
				if(memberGenderValue.equals("Kadýn")) {
					memberGender2="K";
					
					
				}
				else if(memberGenderValue.equals("Erkek")) {
					memberGender2="E";
					
					
				}
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili üyeyi silmek istediðinizden emin misiniz?","Üyeyi Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						//trigger silme iþlemi yapýldýktan sonra silinen üyenin log kaydýný tutuyor
						String queryUpdate = "DELETE FROM LibraryMember WHERE memberID = "+memberNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listMembers();
							txtMemberName.setText("");
							txtMemberSurname.setText("");
							txtMemberTC.setText("");
							txtMemberPhoneNumber.setText("");
							txtBirthDate.setText("");
							txtMemberMail.setText("");
							cmbMemberGender.setSelectedItem("--Cinsiyet Seçiniz--");
							txtMemberAdress.setText("");
							
							
							
							
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
		btnDeleteMember.setForeground(new Color(128, 0, 0));
		btnDeleteMember.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDeleteMember.setBackground(new Color(152, 251, 152));
		btnDeleteMember.setBounds(45, 99, 208, 64);
		panel_3.add(btnDeleteMember);
		
		JButton btnClear = new JButton("Temizle");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//temizle
				txtMemberName.setText("");
				txtMemberSurname.setText("");
				txtMemberTC.setText("");
				txtMemberPhoneNumber.setText("");
				txtBirthDate.setText("");
				txtMemberMail.setText("");
				cmbMemberGender.setSelectedItem("--Cinsiyet Seçiniz--");
				txtMemberAdress.setText("");
				
				
				
			}
		});
		btnClear.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnClear.setForeground(new Color(128, 0, 0));
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnClear.setBackground(new Color(152, 251, 152));
		btnClear.setBounds(45, 192, 208, 64);
		panel_3.add(btnClear);
		
		JLabel lblNewLabel_3 = new JLabel("\u00DCye Bilgileri G\u00FCncelleme Ve Silme \u0130\u015Flemleri");
		lblNewLabel_3.setForeground(new Color(128, 0, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(407, 42, 303, 14);
		contentPane.add(lblNewLabel_3);
		
		JButton btnExitCurrentMember = new JButton("");
		btnExitCurrentMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitCurrentMember.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitCurrentMember.setBounds(1113, 0, 61, 49);
		contentPane.add(btnExitCurrentMember);
		listMembers();
	}
}
