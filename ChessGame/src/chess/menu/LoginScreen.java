package chess.menu;

import chess.account.AccountManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private AccountManager accountManager;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    
    public LoginScreen(AccountManager accountManager) {
        this.accountManager = accountManager;
        
        setTitle("Chess Pro - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        
        initializeUI();
    }
    
    private void initializeUI() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(45, 52, 54), 0, h, new Color(99, 110, 114));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);
        
        // Title
        JLabel titleLabel = new JLabel("CHESS PRO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, 450, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Master the Game");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setBounds(0, 100, 450, 30);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(subtitleLabel);
        
        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(new Color(255, 255, 255, 10));
        loginPanel.setBounds(50, 170, 350, 280);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 1));
        
        // Username label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(30, 30, 290, 25);
        loginPanel.add(usernameLabel);
        
        // Username field
        usernameField = new JTextField();
        usernameField.setBounds(30, 60, 290, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBackground(new Color(255, 255, 255, 20));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginPanel.add(usernameField);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(30, 115, 290, 25);
        loginPanel.add(passwordLabel);
        
        // Password field
        passwordField = new JPasswordField();
        passwordField.setBounds(30, 145, 290, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBackground(new Color(255, 255, 255, 20));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        loginPanel.add(passwordField);
        
        // Login button
        loginButton = createStyledButton("LOGIN", new Color(52, 152, 219));
        loginButton.setBounds(30, 200, 290, 45);
        loginButton.addActionListener(e -> handleLogin());
        loginPanel.add(loginButton);
        
        mainPanel.add(loginPanel);
        
        // Create account button
        createAccountButton = createStyledButton("CREATE NEW ACCOUNT", new Color(46, 204, 113));
        createAccountButton.setBounds(50, 470, 350, 45);
        createAccountButton.addActionListener(e -> handleCreateAccount());
        mainPanel.add(createAccountButton);
        
        // Enter key listeners
        usernameField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> handleLogin());
        
        setContentPane(mainPanel);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }
        
        if (accountManager.login(username, password)) {
            openMainMenu();
        } else {
            showError("Invalid username or password");
        }
    }
    
    private void handleCreateAccount() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }
        
        if (username.length() < 3) {
            showError("Username must be at least 3 characters");
            return;
        }
        
        if (password.length() < 4) {
            showError("Password must be at least 4 characters");
            return;
        }
        
        if (accountManager.createAccount(username, password)) {
            showSuccess("Account created successfully! You can now login.");
            passwordField.setText("");
        } else {
            showError("Username already exists");
        }
    }
    
    private void openMainMenu() {
        MainMenu mainMenu = new MainMenu(accountManager);
        mainMenu.setVisible(true);
        dispose();
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
