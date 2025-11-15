import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StaffLogin extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public StaffLogin() {
        setTitle("Staff Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> login());
        panel.add(new JLabel());
        panel.add(loginBtn);

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

            String sql = "SELECT * FROM Staff WHERE Email = ? AND Password = ? AND Role = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, "Support");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int staffId = rs.getInt("StaffID");
                JOptionPane.showMessageDialog(this, "Login successful!");
                new StaffDashboard(staffId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials or not authorized.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new StaffLogin();
    }
}
