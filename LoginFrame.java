package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel titleLabel = new JLabel("Inventory Management Login");
        titleLabel.setBounds(80, 20, 250, 25);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 70, 80, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(140, 70, 180, 25);
        add(emailField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 110, 80, 25);
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 110, 180, 25);
        add(passField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 150, 100, 30);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please enter both email and password.");
                    return;
                }

                UserDAO userDAO = new UserDAO();
                User user = userDAO.login(email, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Login successful. Welcome, " + user.getName() + "!");
                    // Hide login window
                    dispose();
                    // Show Inventory window
                    SwingUtilities.invokeLater(() -> new InventoryFrame().setVisible(true));
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid email or password.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
