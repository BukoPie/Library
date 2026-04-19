package LibraryManagement;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LogIn extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField Username;
    private JPasswordField passwordField;

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
    	
    	Class.forName("com.mysql.cj.jdbc.Driver");
    	
    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement","root","hoshi060222");
    	System.out.println("connection oki");
    	
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LogIn frame = new LogIn();
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
    public LogIn() {
        setTitle("Noe's Library - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 930, 572);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(94, 48, 45)); // Brown Background
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(243, 233, 220));
        panel.setBounds(0, 0, 340, 848);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("NOE'S");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setBounds(49, 79, 116, 35);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("LIBRARY");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1_1.setBounds(49, 109, 116, 35);
        panel.add(lblNewLabel_1_1);

        Username = new JTextField();
        Username.setBounds(489, 150, 218, 28);
        contentPane.add(Username);
        Username.setColumns(10);

        JLabel lblNewLabel = new JLabel("Username");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(381, 157, 98, 12);
        contentPane.add(lblNewLabel);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblPassword.setBounds(381, 230, 98, 12);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(489, 225, 218, 28);
        contentPane.add(passwordField);
        

        // Login
        JButton btnLogIn = new JButton("LOG IN");
        btnLogIn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLogIn.setForeground(new Color(94, 48, 45));
        btnLogIn.setBackground(new Color(243, 233, 220));
        btnLogIn.setBounds(381, 284, 100, 30);
        

        btnLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String user = Username.getText();
                String pass = new String(passwordField.getPassword());
                
                if (user.equals("ADMIN") && pass.equals("123456789")) {
                    
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    
                    Homepage mainDashboard = new Homepage();
                    mainDashboard.setVisible(true);
                    
                    dispose();
                    
                } else {
                    // Show error message if credentials don't match
                    JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        contentPane.add(btnLogIn);
        
        JLabel lblDoesntHaveAn = new JLabel("Doesn't have an account? Create one!");
        lblDoesntHaveAn.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblDoesntHaveAn.setForeground(new Color(255, 255, 255));
        lblDoesntHaveAn.setBounds(381, 361, 267, 28);
        contentPane.add(lblDoesntHaveAn);
        
        JButton btnCreateAcc = new JButton("CREATE");
        btnCreateAcc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		CreateAcc CA = new CreateAcc();
                CA.setVisible(true);
                
                dispose();
        		
        	}
        });
        btnCreateAcc.setForeground(new Color(94, 48, 45));
        btnCreateAcc.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCreateAcc.setBackground(new Color(243, 233, 220));
        btnCreateAcc.setBounds(381, 399, 98, 28);
        contentPane.add(btnCreateAcc);
    }
}
