import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Customer Login");
        setSize(300, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login as Customer");
        loginBtn.addActionListener(e -> login());
        panel.add(loginBtn);

        JButton staffLoginBtn = new JButton("Login as Staff");
        staffLoginBtn.addActionListener(e -> {
            new StaffLogin();
            dispose();
        });
        panel.add(staffLoginBtn);

        add(panel);
        setVisible(true);
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }

            String sql = "SELECT * FROM Customers WHERE Email = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                int customerId = rs.getInt("CustomerID");
                new FeedbackForm(customerId);
                dispose();
            } else {
                int choice = JOptionPane.showConfirmDialog(this, "User not found. Would you like to register?", "Register", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    register(email, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void register(String email, String password) {
        String name = JOptionPane.showInputDialog(this, "Enter your name:");
        String phone = JOptionPane.showInputDialog(this, "Enter your phone number:");
        String address = JOptionPane.showInputDialog(this, "Enter your address:");

        if (name == null || phone == null || address == null ||
            name.trim().isEmpty() || phone.trim().isEmpty() || address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Registration cancelled or incomplete.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Customers (Name, Email, Password, Phone) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, phone);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    int customerId = keys.getInt(1);
                    JOptionPane.showMessageDialog(this, "Registration successful! You are now logged in.");
                    new FeedbackForm(customerId);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
