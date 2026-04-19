package LibraryManagement;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.sql.*;

public class IssueBookDialog extends JFrame {

    private JTextField searchField;
    private DefaultListModel<String> listModel;
    private JList<String> bookList;

    private int loggedInUserId = 1; // replace nlng pu

    public IssueBookDialog() {
        setTitle("Issue Book");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main pan
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // search field
        searchField = new JTextField();
        panel.add(searchField, BorderLayout.NORTH);

        // book list
        listModel = new DefaultListModel<>();
        bookList = new JList<>(listModel);
        panel.add(new JScrollPane(bookList), BorderLayout.CENTER);

        // issue button
        JButton issueButton = new JButton("Issue Book");
        panel.add(issueButton, BorderLayout.SOUTH);

        add(panel);

        // load books
        loadBooks("");

        // search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }

            private void search() {
                loadBooks(searchField.getText());
            }
        });

        // issueAct
        issueButton.addActionListener(e -> issueBook());
    }

    // connect
    private Connection connect() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/LibraryManagement",
                "root",
                "hoshi060222"
        );
    }

    //  load books
    private void loadBooks(String keyword) {
        listModel.clear();

        try (Connection con = connect()) {
            String sql = "SELECT title FROM books WHERE available = TRUE AND title LIKE ?";
            var stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                listModel.addElement(rs.getString("title"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // issue bookkkk
    private void issueBook() {
        String selectedBook = bookList.getSelectedValue();

        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Select a book first.");
            return;
        }

        try (Connection con = connect()) {

            // Get book ID
            String getBook = "SELECT book_id FROM books WHERE title = ?";
            var stmt1 = con.prepareStatement(getBook);
            stmt1.setString(1, selectedBook);
            ResultSet rs = stmt1.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                // Insert borrowing record
                String insert = "INSERT INTO borrowing (user_id, book_id, issue_date) VALUES (?, ?, CURDATE())";
                var stmt2 = con.prepareStatement(insert);
                stmt2.setInt(1, loggedInUserId);
                stmt2.setInt(2, bookId);
                stmt2.executeUpdate();

                // Update availability
                String update = "UPDATE books SET available = FALSE WHERE book_id = ?";
                var stmt3 = con.prepareStatement(update);
                stmt3.setInt(1, bookId);
                stmt3.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book issued!");

                loadBooks(""); // refresh list

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new IssueBookDialog().setVisible(true);
        });
    }
}
