package chess.gui;

import chess.account.AccountManager;
import chess.ai.ChessAI;
import chess.game.*;
import chess.menu.MainMenu;
import chess.pieces.*;
import chess.pieces.Piece.PieceColor;
import chess.pieces.Piece.PieceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JFrame {
    private ChessGame game;
    private AccountManager accountManager;
    private ChessAI ai;
    private JPanel boardPanel;
    private JButton[][] squares;
    private Position selectedPosition;
    private List<Position> highlightedMoves;
    private JLabel statusLabel;
    private JLabel trophyLabel;
    private Map<String, Image> pieceImages;
    private boolean isAIGame;
    
    private static final int SQUARE_SIZE = 80;
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color SELECTED_COLOR = new Color(186, 202, 68);
    private static final Color HIGHLIGHT_COLOR = new Color(246, 246, 130);
    
    public GameWindow(AccountManager accountManager, ChessAI.DifficultyLevel aiDifficulty) {
        this.accountManager = accountManager;
        this.game = new ChessGame();
        this.selectedPosition = null;
        this.highlightedMoves = List.of();
        
        if (aiDifficulty != null) {
            this.isAIGame = true;
            this.ai = new ChessAI(PieceColor.BLACK, aiDifficulty);
        } else {
            this.isAIGame = false;
        }
        
        setTitle("Chess Pro - Game");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleBackToMenu();
            }
        });
        setResizable(false);
        
        loadPieceImages();
        initializeUI();
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void loadPieceImages() {
        pieceImages = new HashMap<>();
        
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("assets/pieces.png"));
            
            int pieceWidth = spriteSheet.getWidth() / 6;
            int pieceHeight = spriteSheet.getHeight() / 2;
            
            pieceImages.put("WHITE_KING", spriteSheet.getSubimage(0 * pieceWidth, 0, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("WHITE_QUEEN", spriteSheet.getSubimage(1 * pieceWidth, 0, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("WHITE_BISHOP", spriteSheet.getSubimage(2 * pieceWidth, 0, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("WHITE_KNIGHT", spriteSheet.getSubimage(3 * pieceWidth, 0, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("WHITE_ROOK", spriteSheet.getSubimage(4 * pieceWidth, 0, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("WHITE_PAWN", spriteSheet.getSubimage(5 * pieceWidth, 0, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            
            pieceImages.put("BLACK_KING", spriteSheet.getSubimage(0 * pieceWidth, pieceHeight, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("BLACK_QUEEN", spriteSheet.getSubimage(1 * pieceWidth, pieceHeight, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("BLACK_BISHOP", spriteSheet.getSubimage(2 * pieceWidth, pieceHeight, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("BLACK_KNIGHT", spriteSheet.getSubimage(3 * pieceWidth, pieceHeight, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("BLACK_ROOK", spriteSheet.getSubimage(4 * pieceWidth, pieceHeight, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            pieceImages.put("BLACK_PAWN", spriteSheet.getSubimage(5 * pieceWidth, pieceHeight, pieceWidth, pieceHeight)
                .getScaledInstance(SQUARE_SIZE - 10, SQUARE_SIZE - 10, Image.SCALE_SMOOTH));
            
        } catch (Exception e) {
            System.err.println("Error loading piece images: " + e.getMessage());
        }
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(42, 82, 152));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        statusLabel = new JLabel("White's Turn");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(statusLabel, BorderLayout.CENTER);
        
        trophyLabel = new JLabel(accountManager.getCurrentAccount().getTrophies() + " Trophies");
        trophyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        trophyLabel.setForeground(new Color(255, 215, 0));
        topPanel.add(trophyLabel, BorderLayout.EAST);
        
        JButton backButton = new JButton("Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(52, 73, 94));
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(e -> handleBackToMenu());
        topPanel.add(backButton, BorderLayout.WEST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Chess board
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boardPanel.setBackground(new Color(30, 60, 114));
        
        squares = new JButton[8][8];
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                square.setFocusPainted(false);
                square.setBorderPainted(true);
                
                final int r = row;
                final int c = col;
                
                square.addActionListener(e -> handleSquareClick(r, c));
                
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
        
        add(boardPanel, BorderLayout.CENTER);
        
        updateBoard();
    }
    
    private void handleSquareClick(int row, int col) {
        // Don't allow moves during AI's turn
        if (isAIGame && game.getCurrentPlayer() == PieceColor.BLACK) {
            return;
        }
        
        Position clickedPos = new Position(row, col);
        Piece clickedPiece = game.getBoard().getPiece(clickedPos);
        
        if (selectedPosition == null) {
            if (clickedPiece != null && clickedPiece.getColor() == game.getCurrentPlayer()) {
                selectedPosition = clickedPos;
                highlightedMoves = game.getLegalMovesForPiece(clickedPos);
                updateBoard();
            }
        } else {
            if (highlightedMoves.contains(clickedPos)) {
                game.makeMove(selectedPosition, clickedPos);
                selectedPosition = null;
                highlightedMoves = List.of();
                updateBoard();
                updateStatus();
                checkGameEnd();
                
                // AI move
                if (isAIGame && game.getGameState() == ChessGame.GameState.ACTIVE && 
                    game.getCurrentPlayer() == PieceColor.BLACK) {
                    makeAIMove();
                }
            } else if (clickedPiece != null && clickedPiece.getColor() == game.getCurrentPlayer()) {
                selectedPosition = clickedPos;
                highlightedMoves = game.getLegalMovesForPiece(clickedPos);
                updateBoard();
            } else {
                selectedPosition = null;
                highlightedMoves = List.of();
                updateBoard();
            }
        }
    }
    
    private void makeAIMove() {
        Timer timer = new Timer(500, e -> {
            Move aiMove = ai.getBestMove(game);
            if (aiMove != null) {
                game.makeMove(aiMove.getFrom(), aiMove.getTo());
                updateBoard();
                updateStatus();
                checkGameEnd();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void updateBoard() {
        Board board = game.getBoard();
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = squares[row][col];
                Position pos = new Position(row, col);
                
                boolean isLight = (row + col) % 2 == 0;
                if (selectedPosition != null && selectedPosition.equals(pos)) {
                    square.setBackground(SELECTED_COLOR);
                } else if (highlightedMoves.contains(pos)) {
                    square.setBackground(HIGHLIGHT_COLOR);
                } else {
                    square.setBackground(isLight ? LIGHT_SQUARE : DARK_SQUARE);
                }
                
                Piece piece = board.getPiece(pos);
                if (piece != null) {
                    String key = piece.getColor() + "_" + piece.getType();
                    Image img = pieceImages.get(key);
                    if (img != null) {
                        square.setIcon(new ImageIcon(img));
                    } else {
                        square.setIcon(null);
                        square.setText(getPieceSymbol(piece));
                    }
                } else {
                    square.setIcon(null);
                    square.setText("");
                }
            }
        }
    }
    
    private String getPieceSymbol(Piece piece) {
        String symbol = "";
        switch (piece.getType()) {
            case KING: symbol = "K"; break;
            case QUEEN: symbol = "Q"; break;
            case ROOK: symbol = "R"; break;
            case BISHOP: symbol = "B"; break;
            case KNIGHT: symbol = "N"; break;
            case PAWN: symbol = "P"; break;
        }
        return piece.getColor() == PieceColor.WHITE ? symbol : symbol.toLowerCase();
    }
    
    private void updateStatus() {
        String playerName = game.getCurrentPlayer() == PieceColor.WHITE ? "White" : "Black";
        
        switch (game.getGameState()) {
            case ACTIVE:
                statusLabel.setText(playerName + "'s Turn");
                break;
            case CHECK:
                statusLabel.setText("CHECK! " + playerName + "'s Turn");
                break;
            default:
                break;
        }
    }
    
    private void checkGameEnd() {
        if (game.getGameState() == ChessGame.GameState.CHECKMATE) {
            String winner = game.getCurrentPlayer() == PieceColor.WHITE ? "Black" : "White";
            
            boolean playerWon = (winner.equals("White"));
            if (playerWon) {
                accountManager.recordWin();
                showGameEndDialog("VICTORY!", "You won! +8 Trophies", new Color(46, 204, 113));
            } else {
                accountManager.recordLoss();
                int trophyLoss = accountManager.getCurrentAccount().getTrophyLossAmount();
                showGameEndDialog("DEFEAT", "You lost. -" + trophyLoss + " Trophies", new Color(231, 76, 60));
            }
            
        } else if (game.getGameState() == ChessGame.GameState.STALEMATE) {
            accountManager.recordDraw();
            showGameEndDialog("DRAW", "Game ended in stalemate", new Color(149, 165, 166));
        }
    }
    
    private void showGameEndDialog(String title, String message, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);
        
        JLabel trophiesLabel = new JLabel("Total Trophies: " + accountManager.getCurrentAccount().getTrophies());
        trophiesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        trophiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(trophiesLabel, BorderLayout.SOUTH);
        
        int choice = JOptionPane.showOptionDialog(this, panel, title,
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            new String[]{"Play Again", "Main Menu"}, "Play Again");
        
        if (choice == 0) {
            game.reset();
            selectedPosition = null;
            highlightedMoves = List.of();
            updateBoard();
            updateStatus();
            trophyLabel.setText(accountManager.getCurrentAccount().getTrophies() + " Trophies");
        } else {
            returnToMainMenu();
        }
    }
    
    private void handleBackToMenu() {
        if (game.getGameState() == ChessGame.GameState.ACTIVE || 
            game.getGameState() == ChessGame.GameState.CHECK) {
            int choice = JOptionPane.showConfirmDialog(this,
                "Game in progress. Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                returnToMainMenu();
            }
        } else {
            returnToMainMenu();
        }
    }
    
    private void returnToMainMenu() {
        MainMenu mainMenu = new MainMenu(accountManager);
        mainMenu.setVisible(true);
        dispose();
    }
}
