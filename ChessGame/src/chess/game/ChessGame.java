package chess.game;

import chess.pieces.*;
import chess.pieces.Piece.PieceColor;
import java.util.List;

public class ChessGame {
    private Board board;
    private PieceColor currentPlayer;
    private GameState gameState;
    
    public enum GameState {
        ACTIVE, CHECK, CHECKMATE, STALEMATE
    }
    
    public ChessGame() {
        board = new Board();
        currentPlayer = PieceColor.WHITE;
        gameState = GameState.ACTIVE;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }
    
    public GameState getGameState() {
        return gameState;
    }
    
    public boolean makeMove(Position from, Position to) {
        Piece piece = board.getPiece(from);
        
        // Validate move
        if (piece == null) return false;
        if (piece.getColor() != currentPlayer) return false;
        
        List<Position> legalMoves = getLegalMovesForPiece(from);
        if (!legalMoves.contains(to)) return false;
        
        // Make the move
        board.movePiece(from, to);
        
        // Check for pawn promotion
        if (piece instanceof Pawn) {
            int row = to.getRow();
            if ((piece.getColor() == PieceColor.WHITE && row == 0) ||
                (piece.getColor() == PieceColor.BLACK && row == 7)) {
                // Promote to queen
                Queen queen = new Queen(piece.getColor(), to);
                board.setPiece(to, queen);
                
                List<Piece> pieces = board.getPieces(piece.getColor());
                pieces.remove(piece);
                pieces.add(queen);
            }
        }
        
        // Switch player
        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        
        // Update game state
        updateGameState();
        
        return true;
    }
    
    public List<Position> getLegalMovesForPiece(Position position) {
        Piece piece = board.getPiece(position);
        if (piece == null) return List.of();
        
        List<Position> pseudoLegalMoves = piece.getLegalMoves(board);
        List<Position> legalMoves = new java.util.ArrayList<>();
        
        // Filter out moves that would leave king in check
        for (Position move : pseudoLegalMoves) {
            if (isMoveLegal(position, move)) {
                legalMoves.add(move);
            }
        }
        
        return legalMoves;
    }
    
    private boolean isMoveLegal(Position from, Position to) {
        // Make a copy of the board and try the move
        Board testBoard = board.copy();
        testBoard.movePiece(from, to);
        
        // Check if our king is in check after the move
        Piece piece = board.getPiece(from);
        King ourKing = testBoard.getKing(piece.getColor());
        PieceColor opponentColor = (piece.getColor() == PieceColor.WHITE) ? 
                                    PieceColor.BLACK : PieceColor.WHITE;
        
        return !testBoard.isSquareAttacked(ourKing.getPosition(), opponentColor);
    }
    
    private void updateGameState() {
        // Check if current player is in check
        King currentKing = board.getKing(currentPlayer);
        PieceColor opponentColor = (currentPlayer == PieceColor.WHITE) ? 
                                    PieceColor.BLACK : PieceColor.WHITE;
        
        boolean inCheck = board.isSquareAttacked(currentKing.getPosition(), opponentColor);
        
        // Check if current player has any legal moves
        boolean hasLegalMoves = false;
        List<Piece> pieces = board.getPieces(currentPlayer);
        
        for (Piece piece : pieces) {
            if (!getLegalMovesForPiece(piece.getPosition()).isEmpty()) {
                hasLegalMoves = true;
                break;
            }
        }
        
        if (!hasLegalMoves) {
            gameState = inCheck ? GameState.CHECKMATE : GameState.STALEMATE;
        } else {
            gameState = inCheck ? GameState.CHECK : GameState.ACTIVE;
        }
    }
    
    public void reset() {
        board = new Board();
        currentPlayer = PieceColor.WHITE;
        gameState = GameState.ACTIVE;
    }
}
