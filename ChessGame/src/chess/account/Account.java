package chess.account;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String username;
    private String password;
    private int trophies;
    private int wins;
    private int losses;
    private int draws;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    
    public Account(String username, String password) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.trophies = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.createdAt = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }
    
    public void addWin() {
        wins++;
        trophies += 8;
    }
    
    public void addLoss() {
        losses++;
        int trophyLoss = calculateTrophyLoss();
        trophies = Math.max(0, trophies - trophyLoss);
    }
    
    public void addDraw() {
        draws++;
    }
    
    private int calculateTrophyLoss() {
        if (trophies < 100) return 2;
        if (trophies < 500) return 3;
        if (trophies < 1000) return 3;
        if (trophies < 3000) return 5;
        if (trophies < 10000) return 6;
        if (trophies < 20000) return 7;
        if (trophies < 50000) return 8;
        return 10;
    }
    
    public int getTrophyLossAmount() {
        return calculateTrophyLoss();
    }
    
    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }
    
    // Getters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getTrophies() { return trophies; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    
    public String getRank() {
        if (trophies < 100) return "Bronze";
        if (trophies < 500) return "Silver";
        if (trophies < 1000) return "Gold";
        if (trophies < 3000) return "Platinum";
        if (trophies < 10000) return "Diamond";
        if (trophies < 20000) return "Master";
        if (trophies < 50000) return "Grandmaster";
        return "Legend";
    }
    
    public double getWinRate() {
        int totalGames = wins + losses + draws;
        if (totalGames == 0) return 0.0;
        return (double) wins / totalGames * 100;
    }
    
    @Override
    public String toString() {
        return username + " (" + trophies + " trophies)";
    }
}
