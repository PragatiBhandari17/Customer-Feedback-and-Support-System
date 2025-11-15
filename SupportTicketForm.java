import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SupportTicketForm extends JFrame {
    private JComboBox<String> categoryBox;
    private JTextField subjectField;
    private JTextArea descriptionArea;
    private int customerId;

    public SupportTicketForm(int customerId) {
        this.customerId = customerId;

        setTitle("Submit Support Ticket");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        categoryBox = new JComboBox<>();
        subjectField = new JTextField();
        descriptionArea = new JTextArea(4, 20);

        loadCategories();

        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);
        panel.add(new JLabel("Subject:"));
        panel.add(subjectField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));

        JButton submitBtn = new JButton("Submit Ticket");
        submitBtn.addActionListener(e -> submitTicket());
        panel.add(new JLabel());
        panel.add(submitBtn);

        add(panel);
        setVisible(true);
    }

    private void loadCategories() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CategoryID, Name FROM TicketCategories");
            while (rs.next()) {
                categoryBox.addItem(rs.getInt("CategoryID") + " - " + rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitTicket() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO SupportTickets (CustomerID, Subject, Description, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            int categoryId = Integer.parseInt(categoryBox.getSelectedItem().toString().split(" - ")[0]);
            String subject = subjectField.getText().trim();
            String description = descriptionArea.getText().trim();

            stmt.setInt(1, customerId);
            stmt.setString(2, subject);
            stmt.setString(3, description);
            stmt.setString(4, "Open");

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Support ticket submitted successfully!");
            subjectField.setText("");
            descriptionArea.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting ticket.");
        }
    }
}
