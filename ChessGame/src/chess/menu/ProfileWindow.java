package chess.menu;

import chess.account.*;
import javax.swing.*;
import java.awt.*;

public class ProfileWindow extends JFrame {
    private AccountManager accountManager;
    private Account account;
    
    public ProfileWindow(AccountManager accountManager) {
        this.accountManager = accountManager;
        this.account = accountManager.getCurrentAccount();
        
        setTitle("Chess Pro - Profile");
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        initializeUI();
    }
    
    private void initializeUI() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 60, 114), 0, h, new Color(42, 82, 152));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);
        
        // Profile header
        JLabel usernameLabel = new JLabel(account.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 36));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(0, 30, 500, 45);
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(usernameLabel);
        
        // Rank badge
        JLabel rankLabel = new JLabel(account.getRank());
        rankLabel.setFont(new Font("Arial", Font.BOLD, 20));
        rankLabel.setForeground(Color.WHITE);
        rankLabel.setOpaque(true);
        rankLabel.setBackground(getRankColor(account.getRank()));
        rankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rankLabel.setBounds(175, 85, 150, 40);
        rankLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        mainPanel.add(rankLabel);
        
        // Stats panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        statsPanel.setBackground(new Color(255, 255, 255, 15));
        statsPanel.setBounds(50, 150, 400, 360);
        statsPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 30), 2));
        
        int y = 30;
        addStatRow(statsPanel, "Trophies", String.valueOf(account.getTrophies()), y, new Color(255, 215, 0));
        y += 60;
        addStatRow(statsPanel, "Wins", String.valueOf(account.getWins()), y, new Color(46, 204, 113));
        y += 60;
        addStatRow(statsPanel, "Losses", String.valueOf(account.getLosses()), y, new Color(231, 76, 60));
        y += 60;
        addStatRow(statsPanel, "Draws", String.valueOf(account.getDraws()), y, new Color(149, 165, 166));
        y += 60;
        addStatRow(statsPanel, "Win Rate", String.format("%.1f%%", account.getWinRate()), y, new Color(52, 152, 219));
        y += 60;
        addStatRow(statsPanel, "Trophy Loss", "-" + account.getTrophyLossAmount() + " per loss", y, new Color(255, 87, 34));
        
        mainPanel.add(statsPanel);
        
        // Close button
        JButton closeButton = new JButton("CLOSE");
        closeButton.setBounds(175, 530, 150, 40);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(52, 73, 94));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());
        mainPanel.add(closeButton);
        
        setContentPane(mainPanel);
    }
    
    private void addStatRow(JPanel panel, String label, String value, int y, Color valueColor) {
        JLabel labelText = new JLabel(label + ":");
        labelText.setFont(new Font("Arial", Font.PLAIN, 18));
        labelText.setForeground(new Color(200, 200, 200));
        labelText.setBounds(30, y, 200, 30);
        panel.add(labelText);
        
        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("Arial", Font.BOLD, 20));
        valueText.setForeground(valueColor);
        valueText.setBounds(220, y, 150, 30);
        valueText.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(valueText);
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
}
