package Forms;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField passwordField;
	private JTextField txtStaffUsername;
	private JPasswordField txtStaffPassword;
	public static int staffIDValue = 0;
	public static int adminIDValue =0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
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
	public LoginForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(192, 148, 498, 211);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		tabbedPane.addTab("Admin Giriþi", null, panel, null);
		panel.setLayout(null);
		
		JButton btnGirisYap = new JButton("Giri\u015F Yap");
		btnGirisYap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*Giriþ Yap Alaný Admin Giriþ*/
				
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String userName = txtUserName.getText();
				String adminPassword = String.valueOf(passwordField.getPassword());
				
				
				
				
				
				try {
					//String query = "select * from Admin";
					
					//String query = "SELECT SystemUser.userName,SystemUser.password,Role.roleTitle FROM Role,SystemUser WHERE Role.roleNumber = SystemUser.roleNumber AND Role.roleTitle = 'Admin' AND SystemUser.userName = '" + userName + "'"+" AND SystemUser.password = '" + adminPassword + "'";
					String query = "SELECT * "+
							"FROM SystemUser,Role "+
							"WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle='Admin' AND "
							+ "SystemUser.userName = '"+userName+"' AND SystemUser.password = '"+adminPassword+"'";
					
					
					pst = (OraclePreparedStatement) conn.prepareStatement(query);
				
					rs = (OracleResultSet) pst.executeQuery();
					if(rs.next()) {
						adminIDValue = rs.getInt("userNumber");
						
						JOptionPane.showMessageDialog(null, " Giriþ Baþarýlý");
						AdminScreenForm adminScreen = new AdminScreenForm();
						adminScreen.setVisible(true);
						dispose();
						
					}
					else {
						
						JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ !!! Lütfen tekrar deneyin.");
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}
				/*
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String userName = txtUserName.getText();
				String adminPassword = String.valueOf(passwordField.getPassword());
				
				
				
				
				
				try {
					//String query = "select * from Admin";
					
					String query = "SELECT * FROM Admin WHERE adminUsername = '" + userName + "'"+" AND adminPassword = '" + adminPassword + "'";
					
					pst = (OraclePreparedStatement) conn.prepareStatement(query);
				
					rs = (OracleResultSet) pst.executeQuery();
					if(rs.next()) {
						adminIDValue = rs.getInt("adminNumber");
						
						JOptionPane.showMessageDialog(null, " Giriþ Baþarýlý");
						AdminScreenForm adminScreen = new AdminScreenForm();
						adminScreen.setVisible(true);
						dispose();
						
					}
					else {
						
						JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ !!! Lütfen tekrar deneyin.");
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}
				*/
				
			}
		});
		btnGirisYap.setForeground(new Color(0, 0, 128));
		btnGirisYap.setBackground(new Color(72, 209, 204));
		btnGirisYap.setBounds(115, 137, 105, 23);
		panel.add(btnGirisYap);
		
		JLabel lblNewLabel_1 = new JLabel("Kullan\u0131c\u0131 Ad\u0131 ");
		lblNewLabel_1.setForeground(new Color(0, 128, 128));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(135, 17, 115, 23);
		panel.add(lblNewLabel_1);
		
		txtUserName = new JTextField();
		txtUserName.setForeground(Color.BLUE);
		txtUserName.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtUserName.setBounds(281, 16, 169, 26);
		panel.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\u015Eifre");
		lblNewLabel_2.setForeground(new Color(0, 128, 128));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(135, 79, 99, 23);
		panel.add(lblNewLabel_2);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLUE);
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordField.setBounds(281, 79, 169, 23);
		panel.add(passwordField);
		
		JButton btnSifremUnuttum = new JButton("\u015Eifremi Unuttum");
		btnSifremUnuttum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Þifremi unuttum
				
				AdminForgotMyPasswordScreenForm forgotMyPassword = new AdminForgotMyPasswordScreenForm();
				forgotMyPassword.setVisible(true);
				dispose();
				
				
			}
		});
		btnSifremUnuttum.setBackground(new Color(135, 206, 235));
		btnSifremUnuttum.setForeground(new Color(0, 0, 128));
		btnSifremUnuttum.setBounds(285, 137, 134, 23);
		panel.add(btnSifremUnuttum);
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		lblNewLabel_8.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\Admin-icon_100x100.jpg"));
		lblNewLabel_8.setBounds(10, 17, 99, 100);
		panel.add(lblNewLabel_8);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(135, 206, 235));
		tabbedPane.addTab("Personel Giriþi", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnStaffGirisYap = new JButton("Giri\u015F Yap");
		btnStaffGirisYap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//Staff Giriþ Yap
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String userName = txtStaffUsername.getText();
				String staffPassword = String.valueOf(txtStaffPassword.getPassword());
				
				
				
				
				
				try {
					//String query = "select * from Admin";
					String query = "SELECT * "+
							"FROM SystemUser,Role "+
							"WHERE SystemUser.roleNumber = Role.roleNumber AND Role.roleTitle='Staff' AND "
							+ "SystemUser.userName = '"+userName+"' AND SystemUser.password = '"+staffPassword+"'";
					
					
					
					pst = (OraclePreparedStatement) conn.prepareStatement(query);
				
					rs = (OracleResultSet) pst.executeQuery();
					if(rs.next()) {
						staffIDValue = rs.getInt("userNumber");
						
						
						JOptionPane.showMessageDialog(null, " Giriþ Baþarýlý");
						StaffScreenForm staffScreen = new StaffScreenForm();
						staffScreen.setVisible(true);
						dispose();
						
					}
					else {
						
						JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ !!! Lütfen tekrar deneyin.");
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}
				
				
				/*
				Connection conn = null;
				OraclePreparedStatement pst = null;
				OracleResultSet rs = null;
				conn = ConnectionClass.dbConnect();
				String userName = txtStaffUsername.getText();
				String staffPassword = String.valueOf(txtStaffPassword.getPassword());
				
				
				
				
				
				try {
					//String query = "select * from Admin";
					
					String query = "SELECT * FROM Staff WHERE staffUsername = '" + userName + "'"+" AND staffPassword = '" + staffPassword + "'";
					
					pst = (OraclePreparedStatement) conn.prepareStatement(query);
				
					rs = (OracleResultSet) pst.executeQuery();
					if(rs.next()) {
						staffIDValue = rs.getInt("staffNumber");
						
						
						JOptionPane.showMessageDialog(null, " Giriþ Baþarýlý");
						StaffScreenForm staffScreen = new StaffScreenForm();
						staffScreen.setVisible(true);
						dispose();
						
					}
					else {
						
						JOptionPane.showMessageDialog(null, "Kullanýcý adý veya þifre yanlýþ !!! Lütfen tekrar deneyin.");
					}
					
				}catch(Exception ex) {
					ex.printStackTrace();
					
				}
				*/
				
				
			}
		});
		btnStaffGirisYap.setBackground(new Color(199, 21, 133));
		btnStaffGirisYap.setForeground(new Color(0, 0, 205));
		btnStaffGirisYap.setBounds(85, 135, 109, 23);
		panel_1.add(btnStaffGirisYap);
		
		JButton btnStaffSifremiUnuttum = new JButton("\u015Eifremi Unuttum");
		btnStaffSifremiUnuttum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Staff Þifremi Unuttum
				StaffForgotMyPassword forgotMyPassword = new StaffForgotMyPassword();
				forgotMyPassword.setVisible(true);
				dispose();
				
				
			}
		});
		btnStaffSifremiUnuttum.setBackground(new Color(199, 21, 133));
		btnStaffSifremiUnuttum.setForeground(new Color(0, 0, 205));
		btnStaffSifremiUnuttum.setBounds(285, 135, 140, 23);
		panel_1.add(btnStaffSifremiUnuttum);
		
		JLabel lblNewLabel_3 = new JLabel("Kullan\u0131c\u0131 Ad\u0131  ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setForeground(new Color(255, 0, 0));
		lblNewLabel_3.setBounds(172, 29, 101, 14);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u015Eifre");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setForeground(new Color(255, 0, 0));
		lblNewLabel_4.setBounds(172, 88, 46, 14);
		panel_1.add(lblNewLabel_4);
		
		txtStaffUsername = new JTextField();
		txtStaffUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtStaffUsername.setForeground(new Color(0, 0, 128));
		txtStaffUsername.setBounds(317, 26, 134, 21);
		panel_1.add(txtStaffUsername);
		txtStaffUsername.setColumns(10);
		
		txtStaffPassword = new JPasswordField();
		txtStaffPassword.setForeground(new Color(0, 0, 128));
		txtStaffPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtStaffPassword.setBounds(317, 88, 134, 20);
		panel_1.add(txtStaffPassword);
		
		JLabel lblNewLabel_7 = new JLabel("New label");
		lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\staff_150x150.jpg"));
		lblNewLabel_7.setBounds(10, 11, 152, 97);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_6 = new JLabel("L\u00FCtfen sisteme giri\u015F i\u00E7in size verilen kullan\u0131c\u0131 ad\u0131n\u0131z\u0131 kullan\u0131n\u0131z...");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setForeground(new Color(128, 0, 0));
		lblNewLabel_6.setBounds(192, 91, 540, 14);
		contentPane.add(lblNewLabel_6);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(128, 0, 0), new Color(128, 0, 0), new Color(128, 0, 0), new Color(128, 0, 0)));
		panel_2.setBackground(new Color(135, 206, 235));
		panel_2.setBounds(192, 32, 431, 32);
		contentPane.add(panel_2);
		
		JLabel lblNewLabel_5 = new JLabel("K\u00DCT\u00DCPHANE Y\u00D6NET\u0130M S\u0130STEM\u0130");
		panel_2.add(lblNewLabel_5);
		lblNewLabel_5.setForeground(new Color(0, 128, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\book2_900x800.jpg"));
		lblNewLabel.setBounds(0, -11, 900, 572);
		contentPane.add(lblNewLabel);
	}
}
