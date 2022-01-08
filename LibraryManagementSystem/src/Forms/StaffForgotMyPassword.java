package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class StaffForgotMyPassword extends JFrame {

	private JPanel contentPane;
	private JTextField txtStaffMail;
	private JPasswordField txtStaffPasswordChange;
	private JPasswordField txtStaffPasswordConfirm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffForgotMyPassword frame = new StaffForgotMyPassword();
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
	public StaffForgotMyPassword() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 504);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(60, 179, 113));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\reset-password-icon-7.jpg"));
		lblNewLabel.setBounds(58, 54, 243, 265);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(123, 104, 238));
		panel.setBounds(412, 28, 235, 39);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\u015Eifreni Yenile");
		lblNewLabel_1.setForeground(new Color(255, 255, 224));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(37, 11, 175, 14);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(372, 118, 352, 218);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Mail Adresi");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setForeground(new Color(128, 0, 128));
		lblNewLabel_2.setBounds(42, 46, 75, 14);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Yeni \u015Eifre");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setForeground(new Color(128, 0, 128));
		lblNewLabel_3.setBounds(42, 100, 75, 14);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u015Eifre Tekrar");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setForeground(new Color(128, 0, 128));
		lblNewLabel_4.setBounds(42, 159, 93, 14);
		panel_1.add(lblNewLabel_4);
		
		txtStaffMail = new JTextField();
		txtStaffMail.setBackground(new Color(175, 238, 238));
		txtStaffMail.setBounds(145, 43, 179, 20);
		panel_1.add(txtStaffMail);
		txtStaffMail.setColumns(10);
		
		txtStaffPasswordChange = new JPasswordField();
		txtStaffPasswordChange.setBackground(new Color(175, 238, 238));
		txtStaffPasswordChange.setBounds(145, 97, 179, 20);
		panel_1.add(txtStaffPasswordChange);
		
		txtStaffPasswordConfirm = new JPasswordField();
		txtStaffPasswordConfirm.setBackground(new Color(175, 238, 238));
		txtStaffPasswordConfirm.setBounds(145, 156, 179, 20);
		panel_1.add(txtStaffPasswordConfirm);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(238, 130, 238));
		panel_2.setBounds(448, 379, 184, 53);
		contentPane.add(panel_2);
		
		JButton btnSifreYenileStaff = new JButton("\u015Eifremi Yenile");
		btnSifreYenileStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Staff Þifremi yenile
				
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String mailAdres = txtStaffMail.getText();
				String staffPassword = String.valueOf(txtStaffPasswordChange.getPassword());
				String staffConfirmPassword = String.valueOf(txtStaffPasswordConfirm.getPassword());
			
				
				if(mailAdres.length()!=0 && staffPassword.length()!=0 && staffConfirmPassword.length()!=0) {
					if(staffPassword.equals(staffConfirmPassword)==true) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM SystemUser,Role WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle = 'Staff' AND SystemUser.mailAdress = '" + mailAdres + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								
								String queryUpdate = "UPDATE SystemUser SET password = '" + staffPassword + "' WHERE mailAdress='" + mailAdres + "'";
								pst2 = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
								int rowValue = pst2.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, " Þifre baþarýyla deðiþtirildi.");
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Þifre deðiþtirilemedi!!");
									
								}
								
								
								
								
								
							}
							else {
								
								JOptionPane.showMessageDialog(null, "Böyle bir mail adresi bulunmamaktadýr!!!");
							}
							
						}catch(Exception ex) {
							ex.printStackTrace();
							
						}
						
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Þifre ve tekrar þifre alaný uyumlu deðil!!!");
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Þifre, Mail adresi ve tekrar þifre alanlarý boþ býrakýlamaz!!");
				}
				
				/*
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String mailAdres = txtStaffMail.getText();
				String staffPassword = String.valueOf(txtStaffPasswordChange.getPassword());
				String staffConfirmPassword = String.valueOf(txtStaffPasswordConfirm.getPassword());
			
				
				if(mailAdres.length()!=0 && staffPassword.length()!=0 && staffConfirmPassword.length()!=0) {
					if(staffPassword.equals(staffConfirmPassword)==true) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM Staff WHERE staffMail = '" + mailAdres + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								String queryUpdate = "UPDATE Staff SET staffPassword = '" + staffPassword + "' WHERE staffMail='" + mailAdres + "'";
								pst2 = (OraclePreparedStatement) conn.prepareStatement(queryUpdate);
								int rowValue = pst2.executeUpdate();
								if(rowValue>0) {
									JOptionPane.showMessageDialog(null, " Þifre baþarýyla deðiþtirildi.");
									
								}
								else {
									JOptionPane.showMessageDialog(null, "Þifre deðiþtirilemedi!!");
									
								}
								
								
								
								
								
							}
							else {
								
								JOptionPane.showMessageDialog(null, "Böyle bir mail adresi bulunmamaktadýr!!!");
							}
							
						}catch(Exception ex) {
							ex.printStackTrace();
							
						}
						
						
						
					}
					else {
						JOptionPane.showMessageDialog(null, "Þifre ve tekrar þifre alaný uyumlu deðil!!!");
						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Þifre, Mail adresi ve tekrar þifre alanlarý boþ býrakýlamaz!!");
				}
				*/
			}
		});
		btnSifreYenileStaff.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSifreYenileStaff.setForeground(new Color(0, 0, 128));
		btnSifreYenileStaff.setBounds(25, 11, 149, 23);
		panel_2.add(btnSifreYenileStaff);
	}
}
