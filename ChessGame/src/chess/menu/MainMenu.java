package chess.menu;

import chess.account.*;
import chess.ai.ChessAI;
import chess.gui.GameWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    private AccountManager accountManager;
    private Account currentAccount;
    
    public MainMenu(AccountManager accountManager) {
        this.accountManager = accountManager;
        this.currentAccount = accountManager.getCurrentAccount();
        
        setTitle("Chess Pro - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        initializeUI();
    }
    
    private void initializeUI() {
        // Main panel with gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 60, 114), w, h, new Color(42, 82, 152));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        headerPanel.setBounds(0, 0, 800, 100);
        mainPanel.add(headerPanel);
        
        // Game modes panel
        JPanel modesPanel = createModesPanel();
        modesPanel.setBounds(50, 120, 700, 400);
        mainPanel.add(modesPanel);
        
        // Stats panel
        JPanel statsPanel = createStatsPanel();
        statsPanel.setBounds(50, 530, 350, 40);
        mainPanel.add(statsPanel);
        
        // Logout button
        JButton logoutButton = createSmallButton("Logout");
        logoutButton.setBounds(650, 530, 100, 40);
        logoutButton.addActionListener(e -> handleLogout());
        mainPanel.add(logoutButton);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        
        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome, " + currentAccount.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 20, 400, 35);
        panel.add(welcomeLabel);
        
        // Trophy count with icon
        JLabel trophyLabel = new JLabel(currentAccount.getTrophies() + " Trophies");
        trophyLabel.setFont(new Font("Arial", Font.BOLD, 22));
        trophyLabel.setForeground(new Color(255, 215, 0));
        trophyLabel.setBounds(50, 55, 300, 30);
        panel.add(trophyLabel);
        
        // Rank badge
        JLabel rankLabel = new JLabel(currentAccount.getRank());
        rankLabel.setFont(new Font("Arial", Font.BOLD, 18));
        rankLabel.setForeground(Color.WHITE);
        rankLabel.setOpaque(true);
        rankLabel.setBackground(getRankColor(currentAccount.getRank()));
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rankLabel.setBounds(650, 30, 120, 40);
        rankLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panel.add(rankLabel);
        
        return panel;
    }
    
    private JPanel createModesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 20, 20));
        panel.setOpaque(false);
        
        // VS AI Mode
        panel.add(createModeCard(
            "VS AI",
            "Play against computer",
            new Color(231, 76, 60),
            e -> openAIGameSetup()
        ));
        
        // VS Online Mode
        panel.add(createModeCard(
            "VS ONLINE",
            "Play with other players",
            new Color(52, 152, 219),
            e -> openOnlineMode()
        ));
        
        // Party Mode
        panel.add(createModeCard(
            "PARTY MODE",
            "Create or join a party",
            new Color(155, 89, 182),
            e -> openPartyMode()
        ));
        
        // Profile & Stats
        panel.add(createModeCard(
            "PROFILE",
            "View your statistics",
            new Color(46, 204, 113),
            e -> openProfile()
        ));
        
        return panel;
    }
    
    private JPanel createModeCard(String title, String description, Color color, ActionListener action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        card.setLayout(null);
        card.setOpaque(false);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 40, 300, 35);
        card.add(titleLabel);
        
        // Description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(new Color(255, 255, 255, 200));
        descLabel.setBounds(20, 75, 300, 25);
        card.add(descLabel);
        
        // Play button
        JButton playButton = new JButton("PLAY");
        playButton.setBounds(20, 130, 100, 35);
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.setForeground(color);
        playButton.setBackground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);
        playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        playButton.addActionListener(action);
        card.add(playButton);
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(color.brighter());
                card.repaint();
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(color);
                card.repaint();
            }
        });
        
        return card;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        panel.setOpaque(false);
        
        addStatLabel(panel, "W: " + currentAccount.getWins(), new Color(46, 204, 113));
        addStatLabel(panel, "L: " + currentAccount.getLosses(), new Color(231, 76, 60));
        addStatLabel(panel, "D: " + currentAccount.getDraws(), new Color(149, 165, 166));
        addStatLabel(panel, String.format("Win Rate: %.1f%%", currentAccount.getWinRate()), 
                    new Color(52, 152, 219));
        
        return panel;
    }
    
    private void addStatLabel(JPanel panel, String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(color);
        panel.add(label);
    }
    
    private Color getRankColor(String rank) {
        switch (rank) {
            case "Bronze": return new Color(205, 127, 50);
            case "Silver": return new Color(192, 192, 192);
            case "Gold": return new Color(255, 215, 0);
            case "Platinum": return new Color(229, 228, 226);
            case "Diamond": return new Color(185, 242, 255);
            case "Master": return new Color(138, 43, 226);
            case "Grandmaster": return new Color(255, 0, 0);
            case "Legend": return new Color(255, 140, 0);
            default: return Color.GRAY;
        }
    }
    
    private JButton createSmallButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void openAIGameSetup() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(this,
            "Select AI Difficulty",
            "AI Game Setup",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        if (choice >= 0) {
            ChessAI.DifficultyLevel difficulty;
            switch (choice) {
                case 0: difficulty = ChessAI.DifficultyLevel.EASY; break;
                case 1: difficulty = ChessAI.DifficultyLevel.MEDIUM; break;
                case 2: difficulty = ChessAI.DifficultyLevel.HARD; break;
                default: difficulty = ChessAI.DifficultyLevel.MEDIUM;
            }
            
            GameWindow gameWindow = new GameWindow(accountManager, difficulty);
            gameWindow.setVisible(true);
            dispose();
        }
    }
    
    private void openOnlineMode() {
        JOptionPane.showMessageDialog(this,
            "Online multiplayer coming soon!\nStay tuned for updates.",
            "Coming Soon",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openPartyMode() {
        JOptionPane.showMessageDialog(this,
            "Party mode coming soon!\nInvite friends and play together.",
            "Coming Soon",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void openProfile() {
        ProfileWindow profile = new ProfileWindow(accountManager);
        profile.setVisible(true);
    }
    
    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Logout",
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            accountManager.logout();
            LoginScreen loginScreen = new LoginScreen(accountManager);
            loginScreen.setVisible(true);
            dispose();
        }
    }
}
