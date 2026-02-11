import chess.account.AccountManager;
import chess.menu.LoginScreen;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            AccountManager accountManager = new AccountManager();
            LoginScreen loginScreen = new LoginScreen(accountManager);
            loginScreen.setVisible(true);
        });
    }
}
