package chess.game;

import chess.pieces.*;
import chess.pieces.Piece.PieceColor;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private Piece[][] board;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private King whiteKing;
    private King blackKing;
    
    public Board() {
        board = new Piece[8][8];
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        initializeBoard();
    }
    
    private void initializeBoard() {
        // Place pawns
        for (int col = 0; col < 8; col++) {
            Pawn whitePawn = new Pawn(PieceColor.WHITE, new Position(6, col));
            Pawn blackPawn = new Pawn(PieceColor.BLACK, new Position(1, col));
            placePiece(whitePawn);
            placePiece(blackPawn);
        }
        
        // Place rooks
        placePiece(new Rook(PieceColor.WHITE, new Position(7, 0)));
        placePiece(new Rook(PieceColor.WHITE, new Position(7, 7)));
        placePiece(new Rook(PieceColor.BLACK, new Position(0, 0)));
        placePiece(new Rook(PieceColor.BLACK, new Position(0, 7)));
        
        // Place knights
        placePiece(new Knight(PieceColor.WHITE, new Position(7, 1)));
        placePiece(new Knight(PieceColor.WHITE, new Position(7, 6)));
        placePiece(new Knight(PieceColor.BLACK, new Position(0, 1)));
        placePiece(new Knight(PieceColor.BLACK, new Position(0, 6)));
        
        // Place bishops
        placePiece(new Bishop(PieceColor.WHITE, new Position(7, 2)));
        placePiece(new Bishop(PieceColor.WHITE, new Position(7, 5)));
        placePiece(new Bishop(PieceColor.BLACK, new Position(0, 2)));
        placePiece(new Bishop(PieceColor.BLACK, new Position(0, 5)));
        
        // Place queens
        placePiece(new Queen(PieceColor.WHITE, new Position(7, 3)));
        placePiece(new Queen(PieceColor.BLACK, new Position(0, 3)));
        
        // Place kings
        whiteKing = new King(PieceColor.WHITE, new Position(7, 4));
        blackKing = new King(PieceColor.BLACK, new Position(0, 4));
        placePiece(whiteKing);
        placePiece(blackKing);
    }
    
    private void placePiece(Piece piece) {
        Position pos = piece.getPosition();
        board[pos.getRow()][pos.getCol()] = piece;
        
        if (piece.getColor() == PieceColor.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }
    
    public Piece getPiece(Position position) {
        if (!position.isValid()) return null;
        return board[position.getRow()][position.getCol()];
    }
    
    public void setPiece(Position position, Piece piece) {
        board[position.getRow()][position.getCol()] = piece;
    }
    
    public boolean movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece == null) return false;
        
        Piece captured = getPiece(to);
        if (captured != null) {
            if (captured.getColor() == PieceColor.WHITE) {
                whitePieces.remove(captured);
            } else {
                blackPieces.remove(captured);
            }
        }
        
        setPiece(from, null);
        setPiece(to, piece);
        piece.setPosition(to);
        
        return true;
    }
    
    public King getKing(PieceColor color) {
        return (color == PieceColor.WHITE) ? whiteKing : blackKing;
    }
    
    public List<Piece> getPieces(PieceColor color) {
        return (color == PieceColor.WHITE) ? new ArrayList<>(whitePieces) : new ArrayList<>(blackPieces);
    }
    
    public Board copy() {
        Board copy = new Board();
        copy.board = new Piece[8][8];
        copy.whitePieces = new ArrayList<>();
        copy.blackPieces = new ArrayList<>();
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = this.board[row][col];
                if (piece != null) {
                    Piece pieceCopy = piece.copy();
                    copy.board[row][col] = pieceCopy;
                    
                    if (pieceCopy.getColor() == PieceColor.WHITE) {
                        copy.whitePieces.add(pieceCopy);
                        if (pieceCopy instanceof King) {
                            copy.whiteKing = (King) pieceCopy;
                        }
                    } else {
                        copy.blackPieces.add(pieceCopy);
                        if (pieceCopy instanceof King) {
                            copy.blackKing = (King) pieceCopy;
                        }
                    }
                }
            }
        }
        
        return copy;
    }
    
    public boolean isSquareAttacked(Position position, PieceColor attackingColor) {
        List<Piece> attackers = getPieces(attackingColor);
        
        for (Piece attacker : attackers) {
            List<Position> moves = attacker.getLegalMoves(this);
            if (moves.contains(position)) {
                return true;
            }
        }
        
        return false;
    }
}
