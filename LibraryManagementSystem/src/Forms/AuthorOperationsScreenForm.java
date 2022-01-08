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

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class AuthorOperationsScreenForm extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtAuthorSearch;
	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = {"Yazar No","Yazar Adý","Yazar Soyadý","Ekleyen Admin Adý","Ekleyen Admin Soyadý","Kayýt Tarihi","Güncellenme Tarihi"};
	Object[] satirlar = new Object[7];
	private JTextField txtAuthorName;
	private JTextField txtAuthorSurname;
	private JTextField txtUpdateAuthorName;
	private JTextField txtUpdateAuthorSurname;
	private int authorNumber =0;
	private String authorName = "";
	private String authorSurname = "";
	private int addedAdminNumber = AdminScreenForm.loggedAdminID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorOperationsScreenForm frame = new AuthorOperationsScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				
		modelim.setColumnIdentifiers(kolonlar);
		
		modelim.setColumnCount(0);
		modelim.setRowCount(0);
		modelim.setColumnIdentifiers(kolonlar);
		OracleResultSet rs = showAuthors(sorgu);
		try {
			while(rs.next()) {
				satirlar[0] = rs.getInt("authorNumber");
				satirlar[1] = rs.getString("authorName");
				satirlar[2] = rs.getString("authorSurname");
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
	public AuthorOperationsScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 921, 717);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 160, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel.setBackground(new Color(135, 206, 235));
		panel.setBounds(10, 298, 895, 374);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 93, 875, 270);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//mouse clicked
				DefaultTableModel tblModel = (DefaultTableModel)table.getModel();
				authorNumber = (int) tblModel.getValueAt(table.getSelectedRow(), 0);
				authorName = tblModel.getValueAt(table.getSelectedRow(), 1).toString();
				authorSurname = tblModel.getValueAt(table.getSelectedRow(), 2).toString();
			
				txtUpdateAuthorName.setText(authorName);
				txtUpdateAuthorSurname.setText(authorSurname);
				
				
				
			}
		});
		table.setForeground(new Color(128, 0, 0));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		table.setBackground(new Color(240, 230, 140));
		scrollPane.setViewportView(table);
		
		txtAuthorSearch = new JTextField();
		txtAuthorSearch.setForeground(new Color(0, 0, 128));
		txtAuthorSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAuthorSearch.setBounds(364, 42, 226, 26);
		panel.add(txtAuthorSearch);
		txtAuthorSearch.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Sistemde Kay\u0131tl\u0131 Yazarlar");
		lblNewLabel.setForeground(new Color(128, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(360, 5, 251, 26);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Aranacak Yazar Ad\u0131");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(200, 41, 140, 26);
		panel.add(lblNewLabel_1);
		
		JButton btnSearchAuthor = new JButton("Ara");
		btnSearchAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//yazar ara
				

				String authorName = txtAuthorSearch.getText();
				if(authorName.length()!=0) {
					String sorgu = "SELECT * "+
							"FROM Author2,SystemUser "+
							"WHERE Author2.addedAdminNumber = SystemUser.userNumber AND Author2.authorName = '"+authorName+"' ORDER BY Author2.authorNumber ASC";
					modelim.setColumnCount(0);
					modelim.setRowCount(0);
					modelim.setColumnIdentifiers(kolonlar);
					OracleResultSet rs = showAuthors(sorgu);
					try {
						while(rs.next()) {
							satirlar[0] = rs.getInt("authorNumber");
							satirlar[1] = rs.getString("authorName");
							satirlar[2] = rs.getString("authorSurname");
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
					JOptionPane.showMessageDialog(null, "Aranacak yazar adý alaný boþ býrakýlamaz!!! Lütfen aranacak yazarýn adýný giriniz!!");
				}
				
			}
		});
		btnSearchAuthor.setBackground(new Color(255, 182, 193));
		btnSearchAuthor.setForeground(new Color(75, 0, 130));
		btnSearchAuthor.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSearchAuthor.setBounds(626, 43, 89, 23);
		panel.add(btnSearchAuthor);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_2.setBounds(184, 11, 395, 33);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Yazar Y\u00F6netim Paneli");
		lblNewLabel_3.setForeground(new Color(0, 0, 128));
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_3.setBounds(98, 11, 200, 14);
		panel_2.add(lblNewLabel_3);
		
		JPanel panel_2_3 = new JPanel();
		panel_2_3.setLayout(null);
		panel_2_3.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_2_3.setBackground(new Color(100, 149, 237));
		panel_2_3.setBounds(10, 55, 296, 232);
		contentPane.add(panel_2_3);
		
		JLabel lblNewLabel_3_2 = new JLabel("Yazar Ad\u0131");
		lblNewLabel_3_2.setForeground(new Color(128, 0, 128));
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_2.setBounds(21, 26, 87, 26);
		panel_2_3.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_10 = new JLabel("Yazar Soyad\u0131 ");
		lblNewLabel_10.setForeground(new Color(128, 0, 128));
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setBounds(21, 78, 87, 15);
		panel_2_3.add(lblNewLabel_10);
		
		txtAuthorName = new JTextField();
		txtAuthorName.setForeground(new Color(255, 0, 0));
		txtAuthorName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAuthorName.setColumns(10);
		txtAuthorName.setBounds(145, 27, 125, 26);
		panel_2_3.add(txtAuthorName);
		
		txtAuthorSurname = new JTextField();
		txtAuthorSurname.setForeground(new Color(255, 0, 0));
		txtAuthorSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAuthorSurname.setColumns(10);
		txtAuthorSurname.setBounds(145, 73, 125, 26);
		panel_2_3.add(txtAuthorSurname);
		
		JButton btnAuthorAdd = new JButton("Yazar Ekle");
		btnAuthorAdd.setBounds(62, 140, 162, 54);
		panel_2_3.add(btnAuthorAdd);
		btnAuthorAdd.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\Universal__2888_29_50x50.jpg"));
		btnAuthorAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//yazar ekle 
				

				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String authorName = txtAuthorName.getText();
				String authorSurname = txtAuthorSurname.getText();
				LocalDateTime localDateTime = LocalDateTime.now();
				LocalTime localTime = localDateTime.toLocalTime();
				LocalDate localDate = localDateTime.toLocalDate();
				String creatDate = localDate.toString()+" "+localTime.toString();
				String lastUpdateDate = localDate.toString()+" "+localTime.toString();
				
				if(authorName.length()!=0 && authorSurname.length()!=0) {
					try {
						String query = "SELECT * FROM Author2,SystemUser WHERE SystemUser.userNumber = Author2.addedAdminNumber AND Author2.authorName = '"+authorName+"' AND Author2.authorSurname = '" + authorSurname + "'";
						
						pst = (OraclePreparedStatement) conn.prepareStatement(query);
					
						rs = (OracleResultSet) pst.executeQuery();
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "Bu yazar zaten sistemde kayýtlý bulunmaktadýr!!");
							
							
							
							
							
							
							
						}
						else {
							
							//String queryAdd = "INSERT INTO Staff(staffNumber,staffUsername,staffPassword,adminNumber,staffMail,staffPhoneNumber,staffAddress,staffDeleteControl,TCIdentificationNumber,staffName,staffSurname,staffLastLoginDate,staffCreatedate) VALUES(add_staff.nextval,'"+staffUsername+"','"+staffPassword+"',"+adminID+",'"+staffMail+"','"+staffPhoneNumber+"','"+staffAddress+"','"+staffDeleteControl+"','"+staffTC+"','"+staffName+"','"+staffSurname+"','"+lastLoginDateStaff+"','"+createDate+"')";
							
							String query2 = "INSERT INTO Author2(authorNumber,authorName,authorSurname,addedAdminNumber,creatDate,lastUpdateDate) VALUES(add_author2.nextval,'"+authorName+"','"+authorSurname+"',"+addedAdminNumber+",'"+creatDate+"','"+lastUpdateDate+"')";
							pst2 = (OraclePreparedStatement) conn.prepareStatement(query2);
							int rowValue = pst2.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, "Yeni yazar baþarýyla eklendi.");
								listAuthors();
								txtAuthorName.setText("");
								txtAuthorSurname.setText("");
								
								
							}
							else {
								JOptionPane.showMessageDialog(null, "Yazar eklenemedi!!");
								
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
		btnAuthorAdd.setForeground(new Color(128, 0, 128));
		btnAuthorAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAuthorAdd.setBackground(new Color(144, 238, 144));
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_7.setBackground(new Color(100, 149, 237));
		panel_7.setBounds(316, 55, 589, 104);
		contentPane.add(panel_7);
		
		JLabel lblNewLabel_10_2 = new JLabel("Yazar Ad\u0131");
		lblNewLabel_10_2.setForeground(new Color(128, 0, 128));
		lblNewLabel_10_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10_2.setBounds(44, 16, 98, 23);
		panel_7.add(lblNewLabel_10_2);
		
		JLabel lblNewLabel_20 = new JLabel("Yazar Soyad\u0131");
		lblNewLabel_20.setForeground(new Color(128, 0, 128));
		lblNewLabel_20.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_20.setBounds(44, 62, 98, 23);
		panel_7.add(lblNewLabel_20);
		
		txtUpdateAuthorName = new JTextField();
		txtUpdateAuthorName.setForeground(new Color(255, 0, 0));
		txtUpdateAuthorName.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateAuthorName.setColumns(10);
		txtUpdateAuthorName.setBounds(185, 11, 241, 34);
		panel_7.add(txtUpdateAuthorName);
		
		txtUpdateAuthorSurname = new JTextField();
		txtUpdateAuthorSurname.setForeground(new Color(255, 0, 0));
		txtUpdateAuthorSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtUpdateAuthorSurname.setColumns(10);
		txtUpdateAuthorSurname.setBounds(185, 57, 241, 34);
		panel_7.add(txtUpdateAuthorSurname);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new LineBorder(new Color(128, 0, 0), 3));
		panel_10.setBackground(new Color(100, 149, 237));
		panel_10.setBounds(316, 170, 589, 117);
		contentPane.add(panel_10);
		panel_10.setLayout(null);
		
		JButton btnUpdatePublisher = new JButton("Yazar G\u00FCncelle");
		btnUpdatePublisher.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\partner-update-user-icon-symbol-recycling-symbol-number-text-transparent-png-1449860_50x50.jpg"));
		btnUpdatePublisher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//yazar güncelle
				
				
				
				
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
				String updateAuthorName = txtUpdateAuthorName.getText();
				String updateAuthorSurname = txtUpdateAuthorSurname.getText();
				if(updateAuthorName.length()!=0 && updateAuthorSurname.length()!=0) {
					
					int selectedOption = JOptionPane.showConfirmDialog(null, "Deðiþiklikler kaydedilsin mi ?","Yazarý Güncelle",JOptionPane.YES_NO_OPTION);
					if(selectedOption==0) {
						try {
							//String query = "select * from Admin";
							
							//String query = "SELECT * FROM Admin WHERE adminUsername = '" + txtAdminUsernameUpdate.getText() + "'";
							
							//pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							//rs = (OracleResultSet) pst.executeQuery();
							//String queryUpdate = "UPDATE Staff SET staffUsername = '"+txtUpdateStaffUsername.getText()+"',staffPassword ='"+String.valueOf(pswStaffPassword.getPassword())+"', adminNumber = "+adminID+",staffMail = '"+txtUpdateStaffMail.getText()+"',staffPhoneNumber = '"+txtUpdateStaffPhone.getText()+"',staffAddress = '"+txtUpdateStaffAddress.getText()+"',staffName = '"+txtUpdateStaffName.getText()+"',staffSurname = '"+txtUpdateStaffSurname.getText()+"',staffCreatedate = '"+createDate+"' WHERE staffNumber = "+staffNumber;
							//String queryUpdate = "UPDATE SystemUser SET userName = '"+txtUpdateStaffUsername.getText()+"',password ='"+String.valueOf(pswStaffPassword.getPassword())+"',mailAdress = '"+txtUpdateStaffMail.getText()+"',TCIdentificationNumber = '"+txtUpdateStaffTC.getText()+"',name = '"+txtUpdateStaffName.getText()+"',surname = '"+txtUpdateStaffSurname.getText()+"',creatDate = '"+createDate+"',lastLoginDate = '"+staffLastLoginDate+"' WHERE userNumber = "+staffNumber;
							String queryUpdate = "UPDATE Author2 SET authorName = '"+updateAuthorName+"',authorSurname ='"+updateAuthorSurname+"',lastUpdateDate = '"+lastUpdateDate+"' WHERE authorNumber = "+authorNumber;
							pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
							int rowValue = pst.executeUpdate();
							if(rowValue>0) {
								JOptionPane.showMessageDialog(null, " Deðiþiklikler baþarýyla kaydedildi.");
								listAuthors();
								txtUpdateAuthorName.setText("");
								txtUpdateAuthorSurname.setText("");
								
								
								
								
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
		btnUpdatePublisher.setBackground(new Color(144, 238, 144));
		btnUpdatePublisher.setForeground(new Color(128, 0, 128));
		btnUpdatePublisher.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdatePublisher.setBounds(10, 31, 196, 48);
		panel_10.add(btnUpdatePublisher);
		
		JButton btnDeletePublisher = new JButton("Yazar Sil");
		btnDeletePublisher.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\png-clipart-computer-icons-data-delete-button-miscellaneous-orange_50x50.jpg"));
		btnDeletePublisher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//silme iþlemi
				

				Connection conn = null;
				OraclePreparedStatement pst = null;
				
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				int selectedOption = JOptionPane.showConfirmDialog(null, "Seçili yazarý silmek istediðinizden emin misiniz?","Yazarý Sil",JOptionPane.YES_NO_OPTION);
				if(selectedOption == 0) {
					try {
						
						String queryUpdate = "DELETE FROM Author2 WHERE authorNumber = "+authorNumber;
						pst = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
						int rowValue = pst.executeUpdate();
						if(rowValue>0) {
							JOptionPane.showMessageDialog(null, "Silme iþlemi baþarýyla gerçekleþtirildi.");
							listAuthors();
							txtUpdateAuthorName.setText("");
							txtUpdateAuthorSurname.setText("");
							
							
							
							
							
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
		btnDeletePublisher.setBackground(new Color(144, 238, 144));
		btnDeletePublisher.setForeground(new Color(128, 0, 128));
		btnDeletePublisher.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDeletePublisher.setBounds(216, 31, 186, 48);
		panel_10.add(btnDeletePublisher);
		
		JButton btnPublisherClearField = new JButton("Temizle");
		btnPublisherClearField.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\delete-icon-blue-monochrome-color-vector-23770233_50x50.jpg"));
		btnPublisherClearField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Temizleme
				txtAuthorName.setText("");
				txtAuthorSurname.setText("");
				txtUpdateAuthorName.setText("");
				txtUpdateAuthorSurname.setText("");
			}
		});
		btnPublisherClearField.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPublisherClearField.setBackground(new Color(144, 238, 144));
		btnPublisherClearField.setForeground(new Color(128, 0, 128));
		btnPublisherClearField.setBounds(422, 31, 157, 48);
		panel_10.add(btnPublisherClearField);
		
		JButton btnExitAuthorOperations = new JButton("");
		btnExitAuthorOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExitAuthorOperations.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitAuthorOperations.setBackground(new Color(0, 0, 128));
		btnExitAuthorOperations.setBounds(844, 0, 61, 44);
		contentPane.add(btnExitAuthorOperations);
		listAuthors();
	}
}
