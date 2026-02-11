package chess.pieces;

import chess.game.Position;
import chess.game.Board;
import java.util.List;

public abstract class Piece {
    protected final PieceColor color;
    protected Position position;
    protected boolean hasMoved = false;
    
    public enum PieceColor {
        WHITE, BLACK
    }
    
    public enum PieceType {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
    }
    
    public Piece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }
    
    public PieceColor getColor() {
        return color;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
        this.hasMoved = true;
    }
    
    public boolean hasMoved() {
        return hasMoved;
    }
    
    public abstract PieceType getType();
    
    public abstract List<Position> getLegalMoves(Board board);
    
    public abstract Piece copy();
    
    protected boolean isOpponent(Piece other) {
        return other != null && other.color != this.color;
    }
    
    protected boolean isSameColor(Piece other) {
        return other != null && other.color == this.color;
    }
}
