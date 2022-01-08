package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import com.toedter.calendar.JCalendar;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.LineBorder;

public class NewMemberRegistrationOperationScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtNewMemberName;
	private JTextField txtNewMemberSurname;
	private JTextField txtNewMemberTC;
	private JTextField txtNewMemberMail;
	private JTextField txtNewMemberPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMemberRegistrationOperationScreen frame = new NewMemberRegistrationOperationScreen();
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
	public NewMemberRegistrationOperationScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 857, 624);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(152, 251, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(239, 21, 321, 39);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Yeni \u00DCye Kayd\u0131 Ekran\u0131");
		lblNewLabel.setForeground(new Color(220, 20, 60));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(70, 0, 206, 28);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 128), 3));
		panel_1.setBackground(new Color(255, 192, 203));
		panel_1.setBounds(183, 127, 648, 356);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad\u0131");
		lblNewLabel_1.setBounds(10, 11, 95, 37);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_2 = new JLabel("Soyad\u0131");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 75, 114, 28);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("TC Kimlik Numaras\u0131");
		lblNewLabel_4.setForeground(new Color(128, 0, 0));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setBounds(10, 135, 131, 29);
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Do\u011Fum Tarihi");
		lblNewLabel_5.setForeground(new Color(128, 0, 0));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setBounds(10, 208, 114, 37);
		panel_1.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Mail Adresi");
		lblNewLabel_6.setForeground(new Color(128, 0, 0));
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(340, 77, 74, 25);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Cep Telefon Numaras\u0131");
		lblNewLabel_7.setForeground(new Color(128, 0, 0));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(340, 19, 136, 20);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Cinsiyet");
		lblNewLabel_8.setForeground(new Color(128, 0, 0));
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setBounds(340, 142, 74, 14);
		panel_1.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Adres");
		lblNewLabel_9.setForeground(new Color(128, 0, 0));
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_9.setBounds(339, 197, 75, 25);
		panel_1.add(lblNewLabel_9);
		
		txtNewMemberName = new JTextField();
		txtNewMemberName.setForeground(new Color(0, 0, 128));
		txtNewMemberName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNewMemberName.setBounds(140, 20, 131, 28);
		panel_1.add(txtNewMemberName);
		txtNewMemberName.setColumns(10);
		
		txtNewMemberSurname = new JTextField();
		txtNewMemberSurname.setForeground(new Color(0, 0, 128));
		txtNewMemberSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNewMemberSurname.setBounds(141, 83, 130, 28);
		panel_1.add(txtNewMemberSurname);
		txtNewMemberSurname.setColumns(10);
		
		txtNewMemberTC = new JTextField();
		txtNewMemberTC.setForeground(new Color(0, 0, 128));
		txtNewMemberTC.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNewMemberTC.setBounds(140, 140, 131, 28);
		panel_1.add(txtNewMemberTC);
		txtNewMemberTC.setColumns(10);
		
		JTextArea txtNewMemberAddress = new JTextArea();
		txtNewMemberAddress.setFont(new Font("Monospaced", Font.BOLD, 12));
		txtNewMemberAddress.setForeground(new Color(0, 0, 128));
		txtNewMemberAddress.setBounds(406, 203, 208, 141);
		panel_1.add(txtNewMemberAddress);
		
		JComboBox cmBNewMemberGender = new JComboBox();
		cmBNewMemberGender.setModel(new DefaultComboBoxModel(new String[] {"--Cinsiyet--", "Kad\u0131n", "Erkek"}));
		cmBNewMemberGender.setForeground(new Color(0, 0, 128));
		cmBNewMemberGender.setFont(new Font("Tahoma", Font.BOLD, 12));
		cmBNewMemberGender.setBounds(406, 139, 208, 37);
		panel_1.add(cmBNewMemberGender);
		
		txtNewMemberMail = new JTextField();
		txtNewMemberMail.setForeground(new Color(0, 0, 128));
		txtNewMemberMail.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNewMemberMail.setBounds(424, 72, 188, 28);
		panel_1.add(txtNewMemberMail);
		txtNewMemberMail.setColumns(10);
		
		txtNewMemberPhone = new JTextField();
		txtNewMemberPhone.setForeground(new Color(0, 0, 128));
		txtNewMemberPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNewMemberPhone.setBounds(480, 14, 131, 33);
		panel_1.add(txtNewMemberPhone);
		txtNewMemberPhone.setColumns(10);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setForeground(new Color(0, 0, 128));
		dateChooser.getCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 12));
		dateChooser.setBounds(141, 208, 130, 37);
		panel_1.add(dateChooser);
		
		JLabel lblNewLabel_3 = new JLabel("Ki\u015Fisel Bilgiler");
		lblNewLabel_3.setForeground(new Color(128, 0, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(34, 77, 163, 39);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_10 = new JLabel("New label");
		lblNewLabel_10.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\237-2370268_blue-register-icon-png-transparent-png_150x356.jpg"));
		lblNewLabel_10.setBounds(10, 127, 151, 356);
		contentPane.add(lblNewLabel_10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(173, 216, 230));
		panel_2.setBounds(217, 494, 454, 85);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnRegister = new JButton("Kaydet");
		btnRegister.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\293-2934783_free-vector-graphic-add-member-icon_50x50.jpg"));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kaydet iþlemi
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String memberName = txtNewMemberName.getText();
				String memberSurname = txtNewMemberSurname.getText();
				String memberTC = txtNewMemberTC.getText();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String memberBirthDate = "";
				if(dateChooser.getDate()!=null) {
					memberBirthDate = sdf.format(dateChooser.getDate());
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen doðum tarihi seçimi yapýnýz!");
				}
				
				
				
				
				
				
				String memberPhoneNumber = txtNewMemberPhone.getText();
				String memberMail = txtNewMemberMail.getText();
				String memberGender = cmBNewMemberGender.getSelectedItem().toString();
				String memberGender2="";
				if(memberGender.equals("Kadýn")) {
					memberGender2="K";
					
					
				}
				else if(memberGender.equals("Erkek")) {
					memberGender2="E";
					
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bir cinsiyet seçimi yapýnýz");
					
				}
				String memberAddress = txtNewMemberAddress.getText();
				
				
				//String memberBirthDate = String.valueOf(calendar.getDate());
				//SimpleDateFormat dateNow = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				//Date dateHourNow = new Date();
				//String createDate = dateNow.format(dateHourNow);
				//String lastLoginDateAdmin = createDate;
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String createDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				int addedStaffNumber = StaffScreenForm.loggedStaffID;
				
				
				//String adminCreatedate = Date.parse(df.format(dateNow));
				if(memberName.length()!=0 && memberSurname.length()!=0 && memberTC.length()!=0 && memberBirthDate.length()!=0 && memberPhoneNumber.length()!=0 && memberMail.length()!=0 && memberGender.length()!=0 && memberAddress.length()!=0) {
					if(memberTC.length()==11) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM LibraryMember,SystemUser WHERE SystemUser.userNumber = LibraryMember.addedStaffNumber AND LibraryMember.TCIdentificationNumber = '" + memberTC + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								JOptionPane.showMessageDialog(null, "Bu üye adý zaten sistemde kayýtlý bulunmaktadýr!!");
								
								
								
								
								
								
								
							}
							else {
								
								String queryAdd = "INSERT INTO LibraryMember(memberID,memberName,memberSurname,TCIdentificationNumber,"
										+ "memberBirthDate,memberMail,memberPhoneNumber,memberGender,memberAddress,addedStaffNumber,creatDate,"
										+ "lastUpdateDate) VALUES(add_libraryMember2.nextval,'"+memberName+"','"+memberSurname+"','"+memberTC+"','"+
										memberBirthDate+"','"+memberMail+"','"+memberPhoneNumber+"','"+memberGender2+"','"+memberAddress+"',"+
										addedStaffNumber+",'"+createDate+"','"+lastUpdateDate+"')";
								pst2 = (OraclePreparedStatement) conn.prepareStatement(queryAdd);
								int rowValue = pst2.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, "Üye kaydý baþarýyla yapýldý.");
									txtNewMemberName.setText("");
									txtNewMemberSurname.setText("");
									txtNewMemberTC.setText("");
									dateChooser.setCalendar(null);
									txtNewMemberPhone.setText("");
									txtNewMemberMail.setText("");
									cmBNewMemberGender.setSelectedIndex(-1);
									txtNewMemberAddress.setText("");
									
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Üye kaydedilemedi!!");
									
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
				
				
			}
		});
		btnRegister.setBackground(new Color(220, 20, 60));
		btnRegister.setForeground(new Color(0, 0, 128));
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegister.setBounds(20, 11, 167, 63);
		panel_2.add(btnRegister);
		
		JButton btnClear = new JButton("Alanlar\u0131 Temizle");
		btnClear.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//temizle
				txtNewMemberName.setText("");
				txtNewMemberSurname.setText("");
				txtNewMemberTC.setText("");
				dateChooser.setCalendar(null);
				txtNewMemberPhone.setText("");
				txtNewMemberMail.setText("");
				cmBNewMemberGender.setSelectedIndex(-1);
				txtNewMemberAddress.setText("");
				
				
				
				
				
				
			}
		});
		btnClear.setBackground(new Color(220, 20, 60));
		btnClear.setForeground(new Color(0, 0, 128));
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBounds(216, 11, 208, 63);
		panel_2.add(btnClear);
		
		JButton btnExitRegister = new JButton("");
		btnExitRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitRegister.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitRegister.setBounds(754, 11, 61, 49);
		contentPane.add(btnExitRegister);
	}
}
