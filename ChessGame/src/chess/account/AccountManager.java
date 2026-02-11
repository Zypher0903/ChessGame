package chess.account;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static final String DATA_FILE = "data/accounts.dat";
    private Map<String, Account> accounts;
    private Account currentAccount;
    
    public AccountManager() {
        accounts = new HashMap<>();
        loadAccounts();
    }
    
    public boolean createAccount(String username, String password) {
        if (username == null || username.trim().isEmpty()) return false;
        if (password == null || password.trim().isEmpty()) return false;
        if (accounts.containsKey(username.toLowerCase())) return false;
        
        Account account = new Account(username, password);
        accounts.put(username.toLowerCase(), account);
        saveAccounts();
        return true;
    }
    
    public boolean login(String username, String password) {
        if (username == null || password == null) return false;
        
        Account account = accounts.get(username.toLowerCase());
        if (account == null) return false;
        if (!account.getPassword().equals(password)) return false;
        
        account.updateLastLogin();
        currentAccount = account;
        saveAccounts();
        return true;
    }
    
    public void logout() {
        if (currentAccount != null) {
            saveAccounts();
            currentAccount = null;
        }
    }
    
    public Account getCurrentAccount() {
        return currentAccount;
    }
    
    public boolean isLoggedIn() {
        return currentAccount != null;
    }
    
    public void recordWin() {
        if (currentAccount != null) {
            currentAccount.addWin();
            saveAccounts();
        }
    }
    
    public void recordLoss() {
        if (currentAccount != null) {
            currentAccount.addLoss();
            saveAccounts();
        }
    }
    
    public void recordDraw() {
        if (currentAccount != null) {
            currentAccount.addDraw();
            saveAccounts();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadAccounts() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            accounts = (Map<String, Account>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error loading accounts: " + e.getMessage());
            accounts = new HashMap<>();
        }
    }
    
    private void saveAccounts() {
        File file = new File(DATA_FILE);
        file.getParentFile().mkdirs();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(accounts);
        } catch (Exception e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
}
