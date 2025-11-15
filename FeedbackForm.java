import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FeedbackForm extends JFrame {
    private JComboBox<String> productBox, categoryBox;
    private JTextArea messageArea;
    private JSlider ratingSlider;
    private int customerId;

    public FeedbackForm(int customerId) {
        this.customerId = customerId;

        setTitle("Submit Feedback");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        productBox = new JComboBox<>();
        categoryBox = new JComboBox<>();
        messageArea = new JTextArea(4, 20);
        ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        ratingSlider.setMajorTickSpacing(1);

        loadComboData();

        panel.add(new JLabel("Product:"));
        panel.add(productBox);
        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);
        panel.add(new JLabel("Feedback:"));
        panel.add(new JScrollPane(messageArea));
        panel.add(new JLabel("Rating:"));
        panel.add(ratingSlider);

        JButton submitBtn = new JButton("Submit Feedback");
        submitBtn.addActionListener(e -> submitFeedback());
        panel.add(new JLabel());
        panel.add(submitBtn);

        JButton supportBtn = new JButton("Need Help? Submit Ticket");
        supportBtn.addActionListener(e -> new SupportTicketForm(customerId));
        panel.add(new JLabel());
        panel.add(supportBtn);

        add(panel);
        setVisible(true);
    }

    private void loadComboData() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT ProductID, Name FROM Products");
            while (rs.next()) {
                productBox.addItem(rs.getInt("ProductID") + " - " + rs.getString("Name"));
            }

            rs = stmt.executeQuery("SELECT CategoryID, Name FROM FeedbackCategories");
            while (rs.next()) {
                categoryBox.addItem(rs.getInt("CategoryID") + " - " + rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitFeedback() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Feedbacks (CustomerID, Message, Rating, ProductID, CategoryID) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            int productId = Integer.parseInt(productBox.getSelectedItem().toString().split(" - ")[0]);
            int categoryId = Integer.parseInt(categoryBox.getSelectedItem().toString().split(" - ")[0]);

            stmt.setInt(1, customerId);
            stmt.setString(2, messageArea.getText());
            stmt.setInt(3, ratingSlider.getValue());
            stmt.setInt(4, productId);
            stmt.setInt(5, categoryId);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Feedback submitted!");
            messageArea.setText("");
            ratingSlider.setValue(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
