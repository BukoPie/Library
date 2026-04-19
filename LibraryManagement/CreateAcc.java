package LibraryManagement;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class CreateAcc extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	// Launch the application.
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	
    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement","root","hoshi060222");
    	System.out.println("connection oki");
    	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage frame = new Homepage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Create Account Frame
	public CreateAcc() {
		setTitle("Create Account");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 944, 577);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(243, 233, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 180, 667);
		panel.setBackground(new Color(94, 48, 35));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 190, 180, 19);
		panel.add(separator);
		
		JLabel lblNewLabel = new JLabel("ADMIN");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(64, 126, 61, 19);
		panel.add(lblNewLabel);
		
		JLabel Home = new JLabel("Home");
		Home.setForeground(new Color(255, 255, 255));
		Home.setFont(new Font("Tahoma", Font.BOLD, 15));
		Home.setBounds(22, 219, 78, 12);
		panel.add(Home);
		
		JLabel ManageBooks = new JLabel("Manage books");
		ManageBooks.setForeground(Color.WHITE);
		ManageBooks.setFont(new Font("Tahoma", Font.BOLD, 15));
		ManageBooks.setBounds(22, 241, 130, 19);
		panel.add(ManageBooks);
		
		JLabel ManageStudents = new JLabel("Manage students");
		ManageStudents.setForeground(Color.WHITE);
		ManageStudents.setFont(new Font("Tahoma", Font.BOLD, 15));
		ManageStudents.setBounds(22, 270, 130, 19);
		panel.add(ManageStudents);
		
		JLabel IssueBook = new JLabel("Issue book");
		IssueBook.setForeground(Color.WHITE);
		IssueBook.setFont(new Font("Tahoma", Font.BOLD, 15));
		IssueBook.setBounds(22, 299, 130, 19);
		panel.add(IssueBook);
		
		JLabel ReturnBook = new JLabel("Return book");
		ReturnBook.setForeground(Color.WHITE);
		ReturnBook.setFont(new Font("Tahoma", Font.BOLD, 15));
		ReturnBook.setBounds(22, 328, 130, 19);
		panel.add(ReturnBook);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 375, 180, 19);
		panel.add(separator_1);
		
		JLabel SwitchAcc = new JLabel("Switch account");
		SwitchAcc.setForeground(Color.WHITE);
		SwitchAcc.setFont(new Font("Tahoma", Font.BOLD, 15));
		SwitchAcc.setBounds(22, 404, 130, 19);
		panel.add(SwitchAcc);
		
		JLabel LogOut = new JLabel("Log out");
		LogOut.setForeground(Color.WHITE);
		LogOut.setFont(new Font("Tahoma", Font.BOLD, 15));
		LogOut.setBounds(22, 433, 130, 19);
		panel.add(LogOut);
		
		
		// Add mouse listener to LogOut label
				LogOut.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						// Ask for confirmation
						int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
						
						if (confirm == JOptionPane.YES_OPTION) {
							// Show logout message
							JOptionPane.showMessageDialog(null, "You have been logged out successfully!");
							
							// Close current homepage
							dispose();
							
							// Open Login page
							LogIn login = new LogIn();
							login.setVisible(true);
						}
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						LogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
						LogOut.setForeground(new Color(255, 200, 200));
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						LogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
						LogOut.setForeground(Color.WHITE);
					}
				});
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setForeground(new Color(243, 233, 220));
		panel_1_1.setBackground(new Color(225, 214, 201));
		panel_1_1.setBounds(220, 128, 680, 389);
		contentPane.add(panel_1_1);
		
		textField = new JTextField();
		textField.setBounds(220, 33, 680, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		
		JButton AddBook = new JButton("Add Book");
		AddBook.setBounds(220, 84, 134, 25);
		contentPane.add(AddBook);
		AddBook.setForeground(new Color(255, 255, 255));
		AddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Replace 'AddBookDialog' with the actual name of your Dialog file
				AddBookDialog dialog = new AddBookDialog();

				// This makes the pop-up appear on top of your homepage
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		AddBook.setFont(new Font("Tahoma", Font.BOLD, 14));
		AddBook.setBackground(new Color(190, 129, 88));

	}
}