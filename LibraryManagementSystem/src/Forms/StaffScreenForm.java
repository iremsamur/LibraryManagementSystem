package Forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;

import ManagerClasses.ConnectionClass;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;

public class StaffScreenForm extends JFrame {

	private JPanel contentPane;
	public static int loggedStaffID =0;
	private JLabel bookNumberLbl2;
	private JLabel memberNumberLbl2;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffScreenForm frame = new StaffScreenForm();
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
			bookNumberLbl2.setText(result);
			
			
			
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
			memberNumberLbl2.setText(result);
			
			
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			
		}
	}

	/**
	 * Create the frame.
	 */
	Timer timer;
	public StaffScreenForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 705);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(75, 0, 130), new Color(75, 0, 130), new Color(75, 0, 130), new Color(75, 0, 130)));
		panel.setBackground(new Color(65, 105, 225));
		panel.setBounds(0, 0, 270, 676);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(128, 0, 128), new Color(128, 0, 128)));
		panel_1.setBackground(new Color(152, 251, 152));
		panel_1.setBounds(10, 11, 188, 39);
		panel.add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Men\u00FC");
		lblNewLabel_1.setForeground(new Color(128, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(44, 11, 94, 14);
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewMember = new JButton("Yeni \u00DCye Kayd\u0131");
		btnNewMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//üye kaydý
				loggedStaffID = LoginForm.staffIDValue;
				NewMemberRegistrationOperationScreen newMemberOperations = new NewMemberRegistrationOperationScreen();
				newMemberOperations.setVisible(true);
			}
		});
		btnNewMember.setBackground(new Color(128, 0, 128));
		btnNewMember.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewMember.setForeground(new Color(128, 0, 0));
		btnNewMember.setBounds(10, 61, 250, 82);
		panel.add(btnNewMember);
		
		JButton btnCurrentMember = new JButton("Mevcut \u00DCye \u0130\u015Flemleri");
		btnCurrentMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//member operations
				loggedStaffID = LoginForm.staffIDValue;
				CurrentMemberRegistrationOperationScreen currentMemberOperations = new CurrentMemberRegistrationOperationScreen();
				currentMemberOperations.setVisible(true);
				
			}
		});
		btnCurrentMember.setBackground(new Color(128, 0, 128));
		btnCurrentMember.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCurrentMember.setForeground(new Color(128, 0, 0));
		btnCurrentMember.setBounds(10, 155, 250, 82);
		panel.add(btnCurrentMember);
		
		JButton btnBookLendingOperations = new JButton("Kitap \u00D6d\u00FCn\u00E7 Verme \u0130\u015Flemleri");
		btnBookLendingOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ödünç verme iþlemleri
				loggedStaffID = LoginForm.staffIDValue;
				BorrowingOperationsScreenForm borrowingOperations = new BorrowingOperationsScreenForm();
				borrowingOperations.setVisible(true);
				
			}
		});
		btnBookLendingOperations.setBackground(new Color(128, 0, 128));
		btnBookLendingOperations.setForeground(new Color(128, 0, 0));
		btnBookLendingOperations.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBookLendingOperations.setBounds(10, 248, 250, 71);
		panel.add(btnBookLendingOperations);
		
		JButton btnPunishmentOperations = new JButton("Ceza \u0130\u015Flemleri");
		btnPunishmentOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loggedStaffID = LoginForm.staffIDValue;
				 FineOperationsScreen fineOperations = new FineOperationsScreen();
				 fineOperations.setVisible(true);
				 
				
			}
		});
		btnPunishmentOperations.setBackground(new Color(128, 0, 128));
		btnPunishmentOperations.setForeground(new Color(128, 0, 0));
		btnPunishmentOperations.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPunishmentOperations.setBounds(10, 412, 250, 71);
		panel.add(btnPunishmentOperations);
		
		JButton btnBookReturnsOperations = new JButton("Kitap \u0130ade \u0130\u015Flemleri");
		btnBookReturnsOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Kitap iade iþlemleri
				loggedStaffID = LoginForm.staffIDValue;
				 BookReturnedOperationsScreen bookReturnedOperations = new BookReturnedOperationsScreen();
				 bookReturnedOperations.setVisible(true);
				
				
			}
		});
		btnBookReturnsOperations.setForeground(new Color(128, 0, 0));
		btnBookReturnsOperations.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBookReturnsOperations.setBackground(new Color(128, 0, 128));
		btnBookReturnsOperations.setBounds(10, 330, 250, 71);
		panel.add(btnBookReturnsOperations);
		
		JButton btnFinePaymentOperations = new JButton("Ceza \u00D6demeyenler");
		btnFinePaymentOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ceza ödemeyenler
				loggedStaffID = LoginForm.staffIDValue;
				 FineNotPayMemberScreen fineNotPayMember = new FineNotPayMemberScreen();
				 fineNotPayMember.setVisible(true);
			}
		});
		btnFinePaymentOperations.setBounds(10, 501, 250, 71);
		panel.add(btnFinePaymentOperations);
		btnFinePaymentOperations.setForeground(new Color(128, 0, 0));
		btnFinePaymentOperations.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFinePaymentOperations.setBackground(new Color(128, 0, 128));
		
		JButton btnMemberLogRecords = new JButton("\u00DCyeler Log Kay\u0131tlar\u0131");
		btnMemberLogRecords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//üyeler log kayýtlarý
				loggedStaffID = LoginForm.staffIDValue;
				 MemberLogRecordsScreen memberLogRecords = new MemberLogRecordsScreen();
				 memberLogRecords.setVisible(true);
				
			}
		});
		btnMemberLogRecords.setForeground(new Color(128, 0, 0));
		btnMemberLogRecords.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMemberLogRecords.setBackground(new Color(128, 0, 128));
		btnMemberLogRecords.setBounds(10, 583, 250, 71);
		panel.add(btnMemberLogRecords);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(new Color(173, 216, 230));
		panel_3.setBounds(336, 119, 421, 228);
		contentPane.add(panel_3);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\old-books-in-library-shelf-luoman_300x300.jpg"));
		lblNewLabel_2.setBounds(0, 0, 242, 228);
		panel_3.add(lblNewLabel_2);
		
		bookNumberLbl2 = new JLabel("50 Adet Kitap");
		bookNumberLbl2.setForeground(new Color(128, 0, 128));
		bookNumberLbl2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		bookNumberLbl2.setBounds(252, 49, 159, 88);
		panel_3.add(bookNumberLbl2);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBackground(new Color(173, 216, 230));
		panel_2_1.setBounds(336, 358, 421, 307);
		contentPane.add(panel_2_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\WhatsApp Image 2021-12-07 at 22.44.44.jpeg"));
		lblNewLabel.setBounds(0, 0, 242, 307);
		panel_2_1.add(lblNewLabel);
		
		memberNumberLbl2 = new JLabel("100 \u00DCye");
		memberNumberLbl2.setForeground(new Color(128, 0, 128));
		memberNumberLbl2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		memberNumberLbl2.setBackground(new Color(238, 130, 238));
		memberNumberLbl2.setBounds(252, 110, 147, 38);
		panel_2_1.add(memberNumberLbl2);
		listBookNumber();
		listMemberNumber();
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setLayout(null);
		panel_3_1.setBackground(new Color(176, 224, 230));
		panel_3_1.setBounds(292, 11, 349, 34);
		contentPane.add(panel_3_1);
		
		JLabel lblNewLabel_5 = new JLabel("Personel Y\u00F6netim Paneli");
		lblNewLabel_5.setForeground(new Color(0, 0, 128));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_5.setBounds(103, 11, 301, 14);
		panel_3_1.add(lblNewLabel_5);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setForeground(new Color(173, 216, 230));
		panel_4.setBackground(new Color(173, 216, 230));
		panel_4.setBounds(336, 69, 421, 39);
		contentPane.add(panel_4);
		
		JLabel lblRealTime2 = new JLabel("New label");
		lblRealTime2.setForeground(new Color(128, 0, 128));
		lblRealTime2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRealTime2.setBounds(256, 11, 98, 14);
		panel_4.add(lblRealTime2);
		
		JLabel lblRealDate2 = new JLabel("New label");
		lblRealDate2.setForeground(new Color(128, 0, 128));
		lblRealDate2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRealDate2.setBounds(41, 11, 98, 14);
		panel_4.add(lblRealDate2);
		
		JButton btnExitFromStaff = new JButton("");
		btnExitFromStaff.setBounds(741, 0, 64, 59);
		contentPane.add(btnExitFromStaff);
		btnExitFromStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginForm loginForm = new LoginForm();
				loginForm.setVisible(true);
				dispose();
			}
		});
		btnExitFromStaff.setIcon(new ImageIcon("C:\\Users\\\u0130REM SAMUR\\Pictures\\3094700_50x50.jpg"));
		//idValueLbl.setText(String.valueOf(LoginForm.staffIDValue)); id deðerini tutuyor
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Date date = new Date();
				DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
				String time = timeFormat.format(date);
				lblRealTime2.setText(time);
				
				Date date2 = new Date();
				DateFormat timeFormat2 = new SimpleDateFormat("dd/MM/yyyy");
				String time2 = timeFormat2.format(date2);
				lblRealDate2.setText(time2);
				
			}

			
		};
		timer = new Timer(1000,actionListener);
		timer.setInitialDelay(0);
		timer.start();
	}
}
