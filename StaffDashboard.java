import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StaffDashboard extends JFrame {
    private JComboBox<String> ticketBox;
    private JTextArea responseArea;
    private JButton assignBtn, respondBtn;
    private int staffId;

    public StaffDashboard(int staffId) {
        this.staffId = staffId;

        setTitle("Staff Dashboard - Ticket Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        ticketBox = new JComboBox<>();
        responseArea = new JTextArea(5, 30);

        assignBtn = new JButton("Assign to Me");
        respondBtn = new JButton("Respond");

        assignBtn.addActionListener(e -> assignTicket());
        respondBtn.addActionListener(e -> respondToTicket());

        loadOpenTickets();

        topPanel.add(new JLabel("Select Ticket:"));
        topPanel.add(ticketBox);
        topPanel.add(new JLabel("Response:"));
        topPanel.add(new JScrollPane(responseArea));
        topPanel.add(assignBtn);
        topPanel.add(respondBtn);

        panel.add(topPanel, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }

    private void loadOpenTickets() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TicketID, Subject FROM SupportTickets WHERE Status = 'Open'");
            while (rs.next()) {
                ticketBox.addItem(rs.getInt("TicketID") + " - " + rs.getString("Subject"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void assignTicket() {
        try (Connection conn = DBConnection.getConnection()) {
            int ticketId = Integer.parseInt(ticketBox.getSelectedItem().toString().split(" - ")[0]);
            String checkSql = "SELECT * FROM TicketAssignments WHERE TicketID = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, ticketId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Ticket already assigned.");
                return;
            }

            String sql = "INSERT INTO TicketAssignments (TicketID, StaffID) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ticketId);
            stmt.setInt(2, staffId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Ticket assigned successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void respondToTicket() {
        try (Connection conn = DBConnection.getConnection()) {
            int ticketId = Integer.parseInt(ticketBox.getSelectedItem().toString().split(" - ")[0]);
            String responseText = responseArea.getText().trim();

            if (responseText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Response cannot be empty.");
                return;
            }

            String sql = "INSERT INTO TicketResponses (TicketID, StaffID, ResponseText) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ticketId);
            stmt.setInt(2, staffId);
            stmt.setString(3, responseText);
            stmt.executeUpdate();

            String updateStatus = "UPDATE SupportTickets SET Status = 'Resolved' WHERE TicketID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateStatus);
            updateStmt.setInt(1, ticketId);
            updateStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Response submitted and ticket marked as resolved.");
            responseArea.setText("");
            ticketBox.removeAllItems();
            loadOpenTickets();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
