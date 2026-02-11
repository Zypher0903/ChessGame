package chess.pieces;

import chess.game.Position;
import chess.game.Board;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    
    public Pawn(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
    
    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();
        
        // Direction depends on color (white moves up, black moves down)
        int direction = (color == PieceColor.WHITE) ? -1 : 1;
        
        // Move forward one square
        Position oneForward = new Position(row + direction, col);
        if (oneForward.isValid() && board.getPiece(oneForward) == null) {
            moves.add(oneForward);
            
            // Move forward two squares (if first move)
            if (!hasMoved) {
                Position twoForward = new Position(row + 2 * direction, col);
                if (twoForward.isValid() && board.getPiece(twoForward) == null) {
                    moves.add(twoForward);
                }
            }
        }
        
        // Capture diagonally
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            Position capturePos = new Position(row + direction, captureCol);
            if (capturePos.isValid()) {
                Piece target = board.getPiece(capturePos);
                if (target != null && isOpponent(target)) {
                    moves.add(capturePos);
                }
            }
        }
        
        return moves;
    }
    
    @Override
    public Piece copy() {
        Pawn copy = new Pawn(this.color, this.position);
        copy.hasMoved = this.hasMoved;
        return copy;
    }
}
