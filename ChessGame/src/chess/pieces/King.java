package chess.pieces;

import chess.game.Position;
import chess.game.Board;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    
    public King(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
    
    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();
        
        // All 8 directions around the king
        int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int[] dir : directions) {
            Position newPos = new Position(row + dir[0], col + dir[1]);
            if (newPos.isValid()) {
                Piece target = board.getPiece(newPos);
                if (target == null || isOpponent(target)) {
                    moves.add(newPos);
                }
            }
        }
        
        return moves;
    }
    
    @Override
    public Piece copy() {
        King copy = new King(this.color, this.position);
        copy.hasMoved = this.hasMoved;
        return copy;
    }
}
