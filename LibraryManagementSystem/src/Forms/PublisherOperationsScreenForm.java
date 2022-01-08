package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

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

public class PublisherOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtPublisherSearch;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Yayýnevi No","Yayýnevi Adý","Yayýnevi Adresi","Ekleyen Admin Adý","Ekleyen Admin Soyadý","Kayýt Tarihi","Güncellenme Tarihi"};
	Object[] satirlar = new Object[7];
	private JTextField txtPublisherTitle;
	private JTextField txtPublisherAddress;
	private JTextField txtUpdatePublisherTitle;
	private JTextField txtUpdatePublisherAddress;
	private int addedAdminNumber = AdminScreenForm.loggedAdminID;
	private int publisherNumber =0;
	private String publisherTitle = "";
	private String publisherAddress = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PublisherOperationsScreenForm frame = new PublisherOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showPublishers(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("publisherNumber");
				satirlar[1] = rs.getString("publisherTitle");
				satirlar[2] = rs.getString("publisherAddress");
				satirlar[3] = rs.getString("name");
				
				satirlar[4] = rs.getString("surname");
				
				
				satirlar[5] = rs.getString("creatDate");
				satirlar[6] = rs.getString("lastUpdateDate");
				
				
				
				
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
	public PublisherOperationsScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 948, 706);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 130, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(135, 206, 235));
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel.setBounds(10, 416, 912, 252);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 73, 892, 173);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setBackground(new Color(240, 230, 140));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//güncelleme ve silme
				
				//mouse clicked
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				publisherNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				publisherTitle = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				publisherAddress = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
		
				txtUpdatePublisherTitle.setText(publisherTitle);
				txtUpdatePublisherAddress.setText(publisherAddress);
			}
		});
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		
		JLabel lblYayýnevi = new JLabel("Sistemde Kay\u0131tl\u0131 Yay\u0131nevleri");
		lblYayýnevi.setForeground(new Color(128, 0, 0));
		lblYayýnevi.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblYayýnevi.setBounds(328, 0, 250, 26);
		panel.add(lblYayýnevi);
		
		JLabel lblNewLabel_2 = new JLabel("Aranacak Yay\u0131nevi Ad\u0131");
		lblNewLabel_2.setForeground(new Color(128, 0, 0));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(142, 36, 140, 26);
		panel.add(lblNewLabel_2);
		
		txtPublisherSearch = new JTextField();
		txtPublisherSearch.setForeground(new Color(0, 0, 128));
		txtPublisherSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPublisherSearch.setColumns(10);
		txtPublisherSearch.setBounds(292, 37, 226, 26);
		panel.add(txtPublisherSearch);
		
		JButton btnSearchPublisher = new JButton("Ara");
		btnSearchPublisher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//arama
				
				
				String publisherTitle = txtPublisherSearch.getText();
				if(publisherTitle.length()!=0) {
					String sorgu = "SELECT * "+
							"FROM Publisher2,SystemUser "+
							"WHERE Publisher2.addedAdminNumber = SystemUser.userNumber AND Publisher2.publisherTitle LIKE '"+publisherTitle+"%' ORDER BY Publisher2.publisherNumber ASC";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showPublishers(sorgu);
					try {
						while(rs.next()) {
							
							satirlar[0] = rs.getInt("publisherNumber");
							satirlar[1] = rs.getString("publisherTitle");
							satirlar[2] = rs.getString("publisherAddress");
							satirlar[3] = rs.getString("name");
							
							satirlar[4] = rs.getString("surname");
							
							
							satirlar[5] = rs.getString("creatDate");
							satirlar[6] = rs.getString("lastUpdateDate");
							
							
							
							
							modelim.addRow(satirlar);
							
							
						}
						table.setModel(modelim);
					}
					catch(Exception ex) {
						ex.printStackTrace();
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Aranacak yayýnevi adý alaný boþ býrakýlamaz!!! Lütfen aranacak yayýnevinin adýný giriniz!!");
				}
			}
		});
		btnSearchPublisher.setForeground(new Color(75, 0, 130));
		btnSearchPublisher.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchPublisher.setBackground(new Color(0, 128, 128));
		btnSearchPublisher.setBounds(539, 37, 89, 23);
		panel.add(btnSearchPublisher);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_3.setBounds(233, 11, 395, 33);
		contentPane.add(panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("Yay\u0131nevi Y\u00F6netim Paneli");
		lblNewLabel_3.setForeground(new Color(0, 0, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(98, 11, 200, 14);
		panel_3.add(lblNewLabel_3);
		
		JPanel panel_2_3 = new JPanel();
		panel_2_3.setLayout(null);
		panel_2_3.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_2_3.setBackground(new Color(100, 149, 237));
		panel_2_3.setBounds(10, 73, 333, 332);
		contentPane.add(panel_2_3);
		
		JLabel lblNewLabel_3_6 = new JLabel("Yay\u0131nevi Ad\u0131");
		lblNewLabel_3_6.setForeground(new Color(128, 0, 128));
		lblNewLabel_3_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_6.setBounds(10, 26, 87, 26);
		panel_2_3.add(lblNewLabel_3_6);
		
		JLabel lblNewLabel_7 = new JLabel("Yay\u0131nevi Adres");
		lblNewLabel_7.setForeground(new Color(128, 0, 128));
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(10, 81, 100, 34);
		panel_2_3.add(lblNewLabel_7);
		
		txtPublisherTitle = new JTextField();
		txtPublisherTitle.setForeground(new Color(255, 0, 0));
		txtPublisherTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPublisherTitle.setColumns(10);
		txtPublisherTitle.setBounds(118, 27, 193, 26);
		panel_2_3.add(txtPublisherTitle);
		
		txtPublisherAddress = new JTextField();
		txtPublisherAddress.setForeground(new Color(255, 0, 0));
		txtPublisherAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtPublisherAddress.setColumns(10);
		txtPublisherAddress.setBounds(118, 73, 193, 183);
		panel_2_3.add(txtPublisherAddress);
		
		JButton btnPublisherAdd = new JButton("Yay\u0131nevi Ekle");
		btnPublisherAdd.setBounds(64, 267, 173, 54);
		panel_2_3.add(btnPublisherAdd);
		btnPublisherAdd.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\indir_50x50 (1).jpg"));
		btnPublisherAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//yayýnevi ekle
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String publisherTitle = txtPublisherTitle.getText();
				String publisherAddress = txtPublisherAddress.getText();
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String creatDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				
				if(publisherTitle.length()!=0 && publisherAddress.length()!=0) {
					try {
						String query = "SELECT * FROM Publisher2,SystemUser WHERE SystemUser.userNumber = Publisher2.addedAdminNumber AND Publisher2.publisherTitle = '"+publisherTitle+"'";
						
						pst = (OraclePreparedStatement) conn.prepareStatement(query);
					
						rs = (OracleResultSet) pst.executeQuery();
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "Bu yayýnevi zaten sistemde kayýtlý bulunmaktadýr!!");
							
							
							
							
							
							
							
						}
						else {
							
							//String queryAdd = "INSERT INTO Staff(staffNumber,staffUsername,staffPassword,adminNumber,staffMail,staffPhoneNumber,staffAddress,staffDeleteControl,TCIdentificationNumber,staffName,staffSurname,staffLastLoginDate,staffCreatedate) VALUES(add_staff.nextval,'"+staffUsername+"','"+staffPassword+"',"+adminID+",'"+staffMail+"','"+staffPhoneNumber+"','"+staffAddress+"','"+staffDeleteControl+"','"+staffTC+"','"+staffName+"','"+staffSurname+"','"+lastLoginDateStaff+"','"+createDate+"')";
							
							String query2 = "INSERT INTO Publisher2(publisherNumber,publisherTitle,publisherAddress,addedAdminNumber,creatDate,lastUpdateDate) VALUES(add_publisher2.nextval,'"+publisherTitle+"','"+publisherAddress+"',"+addedAdminNumber+",'"+creatDate+"','"+lastUpdateDate+"')";
							pst2 = (OraclePreparedStatement) conn.prepareStatement(query2);
							int rowValue = pst2.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Yeni yayýnevi baþarýyla eklendi.");
								listPublishers();
								txtPublisherTitle.setText("");
								txtPublisherAddress.setText("");
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Yayýnevi eklenemedi!!");
								
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
		btnPublisherAdd.setForeground(new Color(128, 0, 128));
		btnPublisherAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPublisherAdd.setBackground(new Color(144, 238, 144));
		
		JPanel panel_9 = new JPanel();
		panel_9.setLayout(null);
		panel_9.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_9.setBackground(new Color(100, 149, 237));
		panel_9.setBounds(363, 73, 559, 168);
		contentPane.add(panel_9);
		
		JLabel lblNewLabel_10_3 = new JLabel("Yay\u0131nevi Ad\u0131");
		lblNewLabel_10_3.setForeground(new Color(128, 0, 128));
		lblNewLabel_10_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10_3.setBounds(51, 28, 98, 23);
		panel_9.add(lblNewLabel_10_3);
		
		JLabel lblNewLabel_21 = new JLabel("Yay\u0131nevi Adresi");
		lblNewLabel_21.setForeground(new Color(128, 0, 128));
		lblNewLabel_21.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_21.setBounds(190, 28, 98, 23);
		panel_9.add(lblNewLabel_21);
		
		txtUpdatePublisherTitle = new JTextField();
		txtUpdatePublisherTitle.setForeground(new Color(255, 0, 0));
		txtUpdatePublisherTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdatePublisherTitle.setColumns(10);
		txtUpdatePublisherTitle.setBounds(44, 62, 131, 34);
		panel_9.add(txtUpdatePublisherTitle);
		
		txtUpdatePublisherAddress = new JTextField();
		txtUpdatePublisherAddress.setForeground(new Color(255, 0, 0));
		txtUpdatePublisherAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdatePublisherAddress.setColumns(10);
		txtUpdatePublisherAddress.setBounds(309, 23, 218, 134);
		panel_9.add(txtUpdatePublisherAddress);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(100, 149, 237));
		panel_1.setBounds(363, 264, 559, 136);
		contentPane.add(panel_1);
		
		JButton btnAuthorUpdate = new JButton("Yay\u0131nevi G\u00FCncelle");
		btnAuthorUpdate.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3994764-middle_50x50.jpg"));
		btnAuthorUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Yayýnevi güncelle
				
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
				String updatePublisherTitle = txtUpdatePublisherTitle.getText();
				String updatePublisherAddress = txtUpdatePublisherAddress.getText();
				if(updatePublisherTitle.length()!=0 && updatePublisherAddress.length()!=0) {
					
					int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Yayýnevi Güncelle",JOptionPane.YES_NO_OPTION);
					if(selectedOption==0) {
						try {
							//String query = "select * from Admin";
							
							//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
							
							//pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							//rs = (OracleResultSet) pst.executeQuery();
							//String queryUpdate = "UPDATE Staff SET staffUsername = '"+txtUpdateStaffUsername.getText()+"',staffPassword ='"+String.valueOf(pswStaffPassword.getPassword())+"', adminNumber = "+adminID+",staffMail = '"+txtUpdateStaffMail.getText()+"',staffPhoneNumber = '"+txtUpdateStaffPhone.getText()+"',staffAddress = '"+txtUpdateStaffAddress.getText()+"',staffName = '"+txtUpdateStaffName.getText()+"',staffSurname = '"+txtUpdateStaffSurname.getText()+"',staffCreatedate = '"+createDate+"' WHERE staffNumber = "+staffNumber;
							//String queryUpdate = "UPDATE SystemUser SET userName = '"+txtUpdateStaffUsername.getText()+"',password ='"+String.valueOf(pswStaffPassword.getPassword())+"',mailAdress = '"+txtUpdateStaffMail.getText()+"',TCIdentificationNumber = '"+txtUpdateStaffTC.getText()+"',name = '"+txtUpdateStaffName.getText()+"',surname = '"+txtUpdateStaffSurname.getText()+"',creatDate = '"+createDate+"',lastLoginDate = '"+staffLastLoginDate+"' WHERE userNumber = "+staffNumber;
							String queryUpdate = "UPDATE Publisher2 SET publisherTitle = '"+updatePublisherTitle+"',publisherAddress ='"+updatePublisherAddress+"',lastUpdateDate = '"+lastUpdateDate+"' WHERE publisherNumber = "+publisherNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
								listPublishers();
								txtUpdatePublisherTitle.setText("");
								txtUpdatePublisherAddress.setText("");
								
								
								
								
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
		btnAuthorUpdate.setForeground(new Color(128, 0, 128));
		btnAuthorUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAuthorUpdate.setBackground(new Color(144, 238, 144));
		btnAuthorUpdate.setBounds(10, 33, 223, 75);
		panel_1.add(btnAuthorUpdate);
		
		JButton btnAuthorDelete = new JButton("Yay\u0131nevi Sil");
		btnAuthorDelete.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\WhatsApp Image 2021-12-29 at 21.56.23.jpeg"));
		btnAuthorDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//silme iþlemi
				

				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili yayýnevini silmek istediðinizden emin misiniz?","Yayýnevi Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM Publisher2 WHERE publisherNumber = "+publisherNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listPublishers();
							txtUpdatePublisherTitle.setText("");
							txtUpdatePublisherAddress.setText("");
							
							
							
							
							
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
		btnAuthorDelete.setForeground(new Color(128, 0, 128));
		btnAuthorDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAuthorDelete.setBackground(new Color(144, 238, 144));
		btnAuthorDelete.setBounds(243, 33, 160, 75);
		panel_1.add(btnAuthorDelete);
		
		JButton btnClearFields = new JButton("Temizle");
		btnClearFields.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//temizle
				txtUpdatePublisherTitle.setText("");
				txtUpdatePublisherAddress.setText("");
				txtPublisherTitle.setText("");
				txtPublisherAddress.setText("");
				
				
			}
		});
		btnClearFields.setForeground(new Color(128, 0, 128));
		btnClearFields.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClearFields.setBackground(new Color(144, 238, 144));
		btnClearFields.setBounds(413, 33, 136, 75);
		panel_1.add(btnClearFields);
		
		JButton btnExitPublisherOperations = new JButton("");
		btnExitPublisherOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitPublisherOperations.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitPublisherOperations.setBackground(new Color(0, 0, 128));
		btnExitPublisherOperations.setBounds(886, 0, 56, 44);
		contentPane.add(btnExitPublisherOperations);
		listPublishers();
	}
}
