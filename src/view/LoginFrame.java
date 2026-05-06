package view;

import model.User;
import service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel lblTitle, lblUsername, lblPassword;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnReset;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();
        GUI();
    }

    public void GUI() {
        setTitle("Dang nhap he thong");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblTitle = new JLabel("DANG NHAP", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));

        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Dang nhap");
        btnReset = new JButton("Lam moi");

        btnLogin.addActionListener(this);
        btnReset.addActionListener(this);

        JPanel pnInput = new JPanel(new GridLayout(2, 2, 10, 10));
        pnInput.add(lblUsername);
        pnInput.add(txtUsername);
        pnInput.add(lblPassword);
        pnInput.add(txtPassword);

        JPanel pnButton = new JPanel(new FlowLayout());
        pnButton.add(btnLogin);
        pnButton.add(btnReset);

        setLayout(new BorderLayout(10, 10));
        add(lblTitle, BorderLayout.NORTH);
        add(pnInput, BorderLayout.CENTER);
        add(pnButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            User user = authService.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Dang nhap thanh cong!");
                new MainFrame(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tai khoan hoac mat khau!");
            }
        }

        if (e.getSource() == btnReset) {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
        }
    }
}