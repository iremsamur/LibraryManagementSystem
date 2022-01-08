package Forms;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.*;
import ManagerClasses.PasswordSecurityControlManager;

public class AdminForgotMyPasswordScreenForm extends JFrame {

	private JPanel contentPane;
	private JTextField adminMail;
	private JPasswordField adminPasswordTxt;
	private JPasswordField confirmPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminForgotMyPasswordScreenForm frame = new AdminForgotMyPasswordScreenForm();
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
	public AdminForgotMyPasswordScreenForm() {
		setResizable(false);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 539);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(100, 149, 237));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\reset-password-icon-7.jpg"));
		lblNewLabel.setBounds(10, 93, 256, 306);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(232, 29, 225, 37);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\u015E\u0130FREN\u0130 YEN\u0130LE");
		lblNewLabel_1.setForeground(new Color(128, 0, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(59, 0, 135, 26);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(276, 130, 289, 246);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Mail Adresi");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setForeground(new Color(199, 21, 133));
		lblNewLabel_2.setBounds(19, 30, 84, 30);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Yeni \u015Eifre");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setForeground(new Color(199, 21, 133));
		lblNewLabel_3.setBounds(19, 109, 84, 30);
		panel_1.add(lblNewLabel_3);
		
		adminMail = new JTextField();
		adminMail.setBackground(new Color(173, 216, 230));
		adminMail.setBounds(113, 32, 166, 30);
		panel_1.add(adminMail);
		adminMail.setColumns(10);
		
		adminPasswordTxt = new JPasswordField();
		adminPasswordTxt.setBackground(new Color(173, 216, 230));
		adminPasswordTxt.setBounds(113, 111, 166, 30);
		panel_1.add(adminPasswordTxt);
		
		JLabel lblNewLabel_3_1 = new JLabel("\u015Eifre Tekrar");
		lblNewLabel_3_1.setForeground(new Color(199, 21, 133));
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3_1.setBounds(19, 187, 84, 30);
		panel_1.add(lblNewLabel_3_1);
		
		confirmPassword = new JPasswordField();
		confirmPassword.setBackground(new Color(173, 216, 230));
		confirmPassword.setBounds(113, 187, 166, 30);
		panel_1.add(confirmPassword);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 192, 203));
		panel_2.setBounds(192, 410, 184, 53);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnResetPassword = new JButton("\u015Eifremi Yenile");
		btnResetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Þifremi yenile
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OraclePreparedStatement pst2 = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String mailAdres = adminMail.getText();
				String adminPassword = String.valueOf(adminPasswordTxt.getPassword());
				String adminConfirmPassword = String.valueOf(confirmPassword.getPassword());
			
				
				if(mailAdres.length()!=0 && adminPassword.length()!=0 && adminConfirmPassword.length()!=0) {
					if(adminPassword.equals(adminConfirmPassword)==true) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM SystemUser,Role WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle = 'Admin' AND SystemUser.mailAdress = '" + mailAdres + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								String queryUpdate = "UPDATE SystemUser SET password = '" + adminPassword + "' WHERE mailAdress='" + mailAdres + "'";
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
				String mailAdres = adminMail.getText();
				String adminPassword = String.valueOf(adminPasswordTxt.getPassword());
				String adminConfirmPassword = String.valueOf(confirmPassword.getPassword());
			
				
				if(mailAdres.length()!=0 && adminPassword.length()!=0 && adminConfirmPassword.length()!=0) {
					if(adminPassword.equals(adminConfirmPassword)==true) {
						try {
							//String query = "select * from Admin";
							
							String query = "SELECT * FROM Admin WHERE adminMail = '" + mailAdres + "'";
							
							pst = (OraclePreparedStatement) conn.prepareStatement(query);
						
							rs = (OracleResultSet) pst.executeQuery();
							if(rs.next()) {
								String queryUpdate = "UPDATE Admin SET adminPassword = '" + adminPassword + "' WHERE adminMail='" + mailAdres + "'";
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
		btnResetPassword.setBackground(new Color(0, 128, 128));
		btnResetPassword.setForeground(new Color(0, 0, 139));
		btnResetPassword.setBounds(10, 11, 164, 26);
		panel_2.add(btnResetPassword);
	}
}
