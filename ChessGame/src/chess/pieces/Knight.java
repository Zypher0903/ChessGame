package chess.pieces;

import chess.game.Position;
import chess.game.Board;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    public Knight(PieceColor color, Position position) {
        super(color, position);
    }
    
    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }
    
    @Override
    public List<Position> getLegalMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCol();
        
        // Knight moves in L-shape (8 possible positions)
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2},  {1, 2},  {2, -1},  {2, 1}
        };
        
        for (int[] move : knightMoves) {
            Position newPos = new Position(row + move[0], col + move[1]);
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
        Knight copy = new Knight(this.color, this.position);
        copy.hasMoved = this.hasMoved;
        return copy;
    }
}
