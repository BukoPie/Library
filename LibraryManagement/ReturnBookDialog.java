package LibraryManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ReturnBookDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField txtBookID;
    private JTextField txtStudentID;
    private JTextField txtReturnDate;
    private JTextField txtFine;

    public ReturnBookDialog() {
        super((Frame) null, "Return Book", true);
        
        setBounds(100, 100, 500, 430);
        setLocationRelativeTo(null);
        setResizable(false);
        
        contentPanel.setBackground(new Color(243, 233, 220));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(94, 48, 35));
        headerPanel.setBounds(0, 0, 500, 60);
        contentPanel.add(headerPanel);
        headerPanel.setLayout(null);
        
        JLabel lblTitle = new JLabel("RETURN BOOK");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setBounds(20, 15, 200, 30);
        headerPanel.add(lblTitle);
        
        // Labels & Fields
        int lblX = 50, fldX = 190, y = 90, gap = 55;
        
        JLabel lblBookID = new JLabel("Book ID:");
        lblBookID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBookID.setBounds(lblX, y, 120, 25);
        contentPanel.add(lblBookID);
        
        txtBookID = new JTextField();
        txtBookID.setBounds(fldX, y, 250, 28);
        contentPanel.add(txtBookID);
        
        JLabel lblStudentID = new JLabel("Student ID:");
        lblStudentID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStudentID.setBounds(lblX, y + gap, 120, 25);
        contentPanel.add(lblStudentID);
        
        txtStudentID = new JTextField();
        txtStudentID.setBounds(fldX, y + gap, 250, 28);
        contentPanel.add(txtStudentID);
        
        JLabel lblReturnDate = new JLabel("Return Date:");
        lblReturnDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblReturnDate.setBounds(lblX, y + gap*2, 120, 25);
        contentPanel.add(lblReturnDate);
        
        txtReturnDate = new JTextField(LocalDate.now().toString());
        txtReturnDate.setBounds(fldX, y + gap*2, 250, 28);
        txtReturnDate.setEditable(false);
        txtReturnDate.setBackground(new Color(225, 214, 201));
        contentPanel.add(txtReturnDate);
        
        JLabel lblFine = new JLabel("Fine (₱):");
        lblFine.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblFine.setBounds(lblX, y + gap*3, 120, 25);
        contentPanel.add(lblFine);
        
        txtFine = new JTextField("0.00");
        txtFine.setBounds(fldX, y + gap*3, 250, 28);
        txtFine.setEditable(false);
        txtFine.setBackground(new Color(225, 214, 201));
        contentPanel.add(txtFine);
        
        // Buttons
        JButton btnCheck = new JButton("Check");
        btnCheck.setForeground(Color.WHITE);
        btnCheck.setBackground(new Color(190, 129, 88));
        btnCheck.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCheck.setBounds(50, 330, 100, 32);
        contentPanel.add(btnCheck);
        
        JButton btnReturn = new JButton("Return Book");
        btnReturn.setForeground(Color.WHITE);
        btnReturn.setBackground(new Color(94, 48, 35));
        btnReturn.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnReturn.setBounds(170, 330, 120, 32);
        contentPanel.add(btnReturn);
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setForeground(new Color(94, 48, 35));
        btnCancel.setBackground(new Color(225, 214, 201));
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCancel.setBounds(310, 330, 100, 32);
        contentPanel.add(btnCancel);
        
        // Listeners
        btnCancel.addActionListener(e -> dispose());
        
        btnCheck.addActionListener(e -> checkStatus());
        
        btnReturn.addActionListener(e -> processReturn());
    }
    
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "hoshi060222");
    }
    
    private void checkStatus() {
        String bookID = txtBookID.getText().trim();
        String studentID = txtStudentID.getText().trim();
        
        if (bookID.isEmpty() || studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Book ID and Student ID.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try (Connection con = getConnection()) {
            // Note: Adjust table/column names to match your actual database schema.
            String sql = "SELECT due_date FROM issued_books WHERE book_id = ? AND student_id = ? AND return_date IS NULL";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, bookID);
            pst.setString(2, studentID);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                Date dueDate = rs.getDate("due_date");
                LocalDate today = LocalDate.now();
                
                if (dueDate != null && today.isAfter(dueDate.toLocalDate())) {
                    long daysLate = ChronoUnit.DAYS.between(dueDate.toLocalDate(), today);
                    double fine = daysLate * 10.0; // ₱10 per day overdue
                    txtFine.setText(String.format("%.2f", fine));
                    JOptionPane.showMessageDialog(this, "Record found. Overdue by " + daysLate + " day(s). Fine: ₱" + fine);
                } else {
                    txtFine.setText("0.00");
                    JOptionPane.showMessageDialog(this, "Record found. No fine due.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No active issue record found.", "Not Found", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void processReturn() {
        String bookID = txtBookID.getText().trim();
        String studentID = txtStudentID.getText().trim();
        
        if (bookID.isEmpty() || studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Book ID and Student ID.", "Missing Info", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Process return for Book ID: " + bookID + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            
            // Note: Adjust SQL to match your schema.
            String updateIssue = "UPDATE issued_books SET return_date = ?, fine = ? WHERE book_id = ? AND student_id = ? AND return_date IS NULL";
            PreparedStatement pst1 = con.prepareStatement(updateIssue);
            pst1.setDate(1, Date.valueOf(LocalDate.now()));
            pst1.setDouble(2, Double.parseDouble(txtFine.getText()));
            pst1.setString(3, bookID);
            pst1.setString(4, studentID);
            int rows1 = pst1.executeUpdate();
            
            String updateBook = "UPDATE books SET status = 'Available' WHERE book_id = ?";
            PreparedStatement pst2 = con.prepareStatement(updateBook);
            pst2.setString(1, bookID);
            int rows2 = pst2.executeUpdate();
            
            if (rows1 > 0 && rows2 > 0) {
                con.commit();
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                con.rollback();
                JOptionPane.showMessageDialog(this, "Return failed. Verify IDs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}