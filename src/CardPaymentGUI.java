import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// GUI Class
public class CardPaymentGUI {
    private JFrame frame;
    private JPanel panel;
    private CardLayout layout;
    private Map<String, User> userMap = new HashMap<>();
    private User currentUser = null;

    public CardPaymentGUI() {
        frame = new JFrame("1 Card Payment System");
        panel = new JPanel();
        layout = new CardLayout();
        panel.setLayout(layout);

        initLoginPanel();
        initRegisterPanel();
        initUserPanel();

        frame.add(panel);
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        layout.show(panel, "Login");
    }

    private void initLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(5, 1));
        JTextField phoneField = new JTextField();
        JPasswordField pinField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton goToRegister = new JButton("Create Account");

        loginPanel.add(new JLabel("Phone Number:"));
        loginPanel.add(phoneField);
        loginPanel.add(new JLabel("PIN:"));
        loginPanel.add(pinField);
        loginPanel.add(loginBtn);
        loginPanel.add(goToRegister);

        loginBtn.addActionListener(e -> {
            String phone = phoneField.getText();
            String pin = new String(pinField.getPassword());
            User user = userMap.get(phone);
            if (user != null && user.getPin().equals(pin)) {
                currentUser = user;
                JOptionPane.showMessageDialog(frame, "Login successful!");
                layout.show(panel, "User");
            } else {
                JOptionPane.showMessageDialog(frame, "Login failed. Check phone or PIN.");
            }
        });

        goToRegister.addActionListener(e -> layout.show(panel, "Register"));
        panel.add(loginPanel, "Login");
    }

    private void initRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridLayout(8, 2));
        JTextField username = new JTextField();
        JTextField phone = new JTextField();
        JTextField pin = new JTextField();
        JTextField nid = new JTextField();
        JTextField address = new JTextField();
        JTextField balance = new JTextField();

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        registerPanel.add(new JLabel("Username:")); registerPanel.add(username);
        registerPanel.add(new JLabel("Phone:")); registerPanel.add(phone);
        registerPanel.add(new JLabel("PIN:")); registerPanel.add(pin);
        registerPanel.add(new JLabel("NID:")); registerPanel.add(nid);
        registerPanel.add(new JLabel("Address:")); registerPanel.add(address);
        registerPanel.add(new JLabel("Balance:")); registerPanel.add(balance);
        registerPanel.add(registerBtn); registerPanel.add(backBtn);

        registerBtn.addActionListener(e -> {
            try {
                User newUser = new RegularUser(
                        username.getText(), phone.getText(), pin.getText(), nid.getText(), address.getText(), Double.parseDouble(balance.getText())
                );
                userMap.put(phone.getText(), newUser);
                JOptionPane.showMessageDialog(frame, "Account created.");
                layout.show(panel, "Login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> layout.show(panel, "Login"));
        panel.add(registerPanel, "Register");
    }

    private void initUserPanel() {
        JPanel userPanel = new JPanel(new GridLayout(6, 1));
        JButton showDetails = new JButton("Show Details");
        JButton checkBalance = new JButton("Check Balance");
        JButton deposit = new JButton("Deposit");
        JButton transfer = new JButton("Transfer");
        JButton logout = new JButton("Logout");

        userPanel.add(showDetails);
        userPanel.add(checkBalance);
        userPanel.add(deposit);
        userPanel.add(transfer);
        userPanel.add(logout);

        showDetails.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Username: " + currentUser.getUsername() + "\n" +
                            "Phone: " + currentUser.getPhoneNumber() + "\n" +
                            "NID: " + currentUser.getNid() + "\n" +
                            "Address: " + currentUser.getAddress() + "\n" +
                            "Balance: BDT " + currentUser.getBalance()
            );
        });

        checkBalance.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Balance: BDT " + currentUser.getBalance());
        });

        deposit.addActionListener(e -> {
            String amtStr = JOptionPane.showInputDialog("Enter amount to deposit:");
            try {
                double amt = Double.parseDouble(amtStr);
                currentUser.setBalance(currentUser.getBalance() + amt);
                JOptionPane.showMessageDialog(frame, "Deposit successful.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount.");
            }
        });

        transfer.addActionListener(e -> {
            String phone = JOptionPane.showInputDialog("Enter recipient phone:");
            String amtStr = JOptionPane.showInputDialog("Enter amount to transfer:");
            try {
                double amt = Double.parseDouble(amtStr);
                User receiver = userMap.get(phone);
                if (receiver == null) {
                    JOptionPane.showMessageDialog(frame, "User not found.");
                } else if (currentUser.getBalance() >= amt) {
                    currentUser.setBalance(currentUser.getBalance() - amt);
                    receiver.setBalance(receiver.getBalance() + amt);
                    JOptionPane.showMessageDialog(frame, "Transfer successful.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        logout.addActionListener(e -> {
            currentUser = null;
            layout.show(panel, "Login");
        });

        panel.add(userPanel, "User");
    }

    public static void main(String[] args) {
        new CardPaymentGUI();
    }
}
