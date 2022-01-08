package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;

public class AdminScreenForm extends JFrame {

	private JPanel contentPane;
	public static int loggedAdminID =0;
	private JLabel bookNumberLbl;
	private JLabel memberNumberLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminScreenForm frame = new AdminScreenForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void listBookNumber() {
		
		
		
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			//String sorgu = "SELECT COUNT(Borrowing2.borrowingID) FROM Borrowing2 WHERE Borrowing2.memberID = "+memberID;
			String sorgu = "SELECT COUNT(Book2.bookNumber) FROM Book2";
			pst = (OraclePreparedStatement) conn.prepareStatement(sorgu);
		
			rs = (OracleResultSet) pst.executeQuery();
			int countResult=0;
			while(rs.next()) {
				countResult = rs.getInt(1);
				
			}
			String result = String.valueOf(countResult)+" Adet Kitap";
			bookNumberLbl.setText(result);
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			
		}
	}
	
public void listMemberNumber() {
		
		
		
		Connection conn = null;
		OraclePreparedStatement pst = null;
		OracleResultSet rs = null;
		conn = ConnectionClass.dbConnect();
		try {
			
			//String sorgu = "SELECT COUNT(Borrowing2.borrowingID) FROM Borrowing2 WHERE Borrowing2.memberID = "+memberID;
			String sorgu = "SELECT COUNT(LibraryMember.memberID) FROM LibraryMember";
			pst = (OraclePreparedStatement) conn.prepareStatement(sorgu);
		
			rs = (OracleResultSet) pst.executeQuery();
			int countResult=0;
			while(rs.next()) {
				countResult = rs.getInt(1);
				
			}
			String result = String.valueOf(countResult)+" Üye";
			memberNumberLbl.setText(result);
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			
		}
	}

	/**
	 * Create the frame.
	 */
	Timer timer;
	public AdminScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 617);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(216, 191, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(75, 0, 130), new Color(75, 0, 130), new Color(75, 0, 130), new Color(75, 0, 130)));
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 182, 578);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnAdminOperations = new JButton("Admin \u0130\u015Flemleri");
		btnAdminOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Admin iþlemleri ekranýna gider
				loggedAdminID = LoginForm.adminIDValue;
				AdminOperationsScreenForm adminOperations = new AdminOperationsScreenForm();
				adminOperations.setVisible(true);
				
			}
		});
		btnAdminOperations.setBackground(new Color(128, 0, 128));
		btnAdminOperations.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAdminOperations.setForeground(new Color(128, 0, 0));
		btnAdminOperations.setBounds(10, 75, 162, 56);
		panel.add(btnAdminOperations);
		
		JButton btnStaffOperations = new JButton("Personel \u0130\u015Flemleri");
		btnStaffOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Personel Ýþlemleri
				loggedAdminID = LoginForm.adminIDValue;
				StaffOperationsScreenForm staffOperations = new StaffOperationsScreenForm();
				staffOperations.setVisible(true);
				
			}
		});
		btnStaffOperations.setBackground(new Color(128, 0, 128));
		btnStaffOperations.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnStaffOperations.setForeground(new Color(128, 0, 0));
		btnStaffOperations.setBounds(10, 167, 162, 58);
		panel.add(btnStaffOperations);
		
		JButton btnBookOperations = new JButton("Kitap \u0130\u015Flemleri");
		btnBookOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kitap iþlemleri
				loggedAdminID = LoginForm.adminIDValue;
				BookOperationsScreenForm bookOperations = new BookOperationsScreenForm();
				bookOperations.setVisible(true);
				
			}
		});
		btnBookOperations.setBackground(new Color(128, 0, 128));
		btnBookOperations.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBookOperations.setForeground(new Color(128, 0, 0));
		btnBookOperations.setBounds(10, 258, 162, 59);
		panel.add(btnBookOperations);
		
		JButton btnAuthorOperations = new JButton("Yazar \u0130\u015Flemleri");
		btnAuthorOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//yazar iþlemleri
				loggedAdminID = LoginForm.adminIDValue;
				AuthorOperationsScreenForm authorOperations = new AuthorOperationsScreenForm();
				authorOperations.setVisible(true);
				
			}
		});
		btnAuthorOperations.setBackground(new Color(128, 0, 128));
		btnAuthorOperations.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAuthorOperations.setForeground(new Color(128, 0, 0));
		btnAuthorOperations.setBounds(10, 343, 162, 60);
		panel.add(btnAuthorOperations);
		
		JButton btnBookTypeOperations = new JButton("Kitap T\u00FCr \u0130\u015Flemleri");
		btnBookTypeOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//kitap türü
				loggedAdminID = LoginForm.adminIDValue;
				BookTypeOperationsScreenForm bookTypeOperations = new BookTypeOperationsScreenForm();
				bookTypeOperations.setVisible(true);
				
			}
		});
		btnBookTypeOperations.setBackground(new Color(128, 0, 128));
		btnBookTypeOperations.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnBookTypeOperations.setForeground(new Color(128, 0, 0));
		btnBookTypeOperations.setBounds(10, 431, 162, 57);
		panel.add(btnBookTypeOperations);
		
		JButton btnPublisherOperations = new JButton("Yay\u0131nevi \u0130\u015Flemleri");
		btnPublisherOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Yayýnevi
				
				loggedAdminID = LoginForm.adminIDValue;
				PublisherOperationsScreenForm publisherOperations = new PublisherOperationsScreenForm();
				publisherOperations.setVisible(true);
			}
		});
		btnPublisherOperations.setBackground(new Color(128, 0, 128));
		btnPublisherOperations.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnPublisherOperations.setForeground(new Color(128, 0, 0));
		btnPublisherOperations.setBounds(10, 512, 162, 55);
		panel.add(btnPublisherOperations);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(128, 0, 128), new Color(128, 0, 128)));
		panel_1.setBackground(new Color(152, 251, 152));
		panel_1.setBounds(10, 11, 162, 39);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Men\u00FC");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setBounds(44, 11, 94, 14);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(173, 216, 230));
		panel_2.setBounds(251, 123, 421, 188);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\old-books-in-library-shelf-luoman_300x300.jpg"));
		lblNewLabel_2.setBounds(0, 0, 242, 188);
		panel_2.add(lblNewLabel_2);
		
		bookNumberLbl = new JLabel("50 Adet Kitap");
		bookNumberLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		bookNumberLbl.setForeground(new Color(128, 0, 128));
		bookNumberLbl.setBounds(252, 49, 159, 88);
		panel_2.add(bookNumberLbl);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(new Color(173, 216, 230));
		panel_2_1.setBounds(251, 316, 421, 251);
		contentPane.add(panel_2_1);
		panel_2_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\WhatsApp Image 2021-12-07 at 22.44.44.jpeg"));
		lblNewLabel.setBounds(0, 0, 242, 251);
		panel_2_1.add(lblNewLabel);
		
	    memberNumberLbl = new JLabel("100 \u00DCye");
		memberNumberLbl.setBackground(new Color(238, 130, 238));
		memberNumberLbl.setForeground(new Color(128, 0, 128));
		memberNumberLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		memberNumberLbl.setBounds(252, 110, 147, 38);
		panel_2_1.add(memberNumberLbl);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(176, 224, 230));
		panel_3.setBounds(304, 11, 318, 34);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Admin Y\u00F6netim Paneli");
		lblNewLabel_5.setForeground(new Color(0, 0, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_5.setBounds(76, 11, 189, 14);
		panel_3.add(lblNewLabel_5);
		
		JButton btnExitFromAdmin = new JButton("");
		btnExitFromAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginForm loginForm = new LoginForm();
				loginForm.setVisible(true);
				dispose();
			}
		});
		btnExitFromAdmin.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		btnExitFromAdmin.setBounds(680, 11, 64, 59);
		contentPane.add(btnExitFromAdmin);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(173, 216, 230));
		panel_4.setForeground(new Color(173, 216, 230));
		panel_4.setBounds(251, 73, 408, 39);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblRealTime = new JLabel("New label");
		lblRealTime.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRealTime.setForeground(new Color(128, 0, 128));
		lblRealTime.setBounds(256, 11, 98, 14);
		panel_4.add(lblRealTime);
		
		JLabel lblRealDate = new JLabel("New label");
		lblRealDate.setForeground(new Color(128, 0, 128));
		lblRealDate.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRealDate.setBounds(41, 11, 98, 14);
		panel_4.add(lblRealDate);
		listBookNumber();
		listMemberNumber();
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				String time = timeFormat.format(date);
				lblRealTime.setText(time);
				
				Date date2 = new Date();
				DateFormat timeFormat2 = new SimpleDateFormat("dd/MM/yyyy");
				String time2 = timeFormat2.format(date2);
				lblRealDate.setText(time2);
				
			}

			
		};
		timer = new Timer(1000,actionListener);
		timer.setInitialDelay(0);
		timer.start();
		
		
		
	}
}
