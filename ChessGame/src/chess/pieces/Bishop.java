package chess.pieces;

import chess.game.Position;
import chess.game.Board;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    
    public Bishop(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
    
    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();
        
        // Bishop moves diagonally (4 directions)
        int[][] directions = {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            while (true) {
                Position newPos = new Position(newRow, newCol);
                if (!newPos.isValid()) break;
                
                Piece target = board.getPiece(newPos);
                if (target == null) {
                    moves.add(newPos);
                } else {
                    if (isOpponent(target)) {
                        moves.add(newPos);
                    }
                    break;
                }
                
                newRow += dir[0];
                newCol += dir[1];
            }
        }
        
        return moves;
    }
    
    @Override
    public Piece copy() {
        Bishop copy = new Bishop(this.color, this.position);
        copy.hasMoved = this.hasMoved;
        return copy;
    }
}
