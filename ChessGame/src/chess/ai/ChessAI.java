package chess.ai;

import chess.game.*;
import chess.pieces.Piece;
import chess.pieces.Piece.PieceColor;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class ChessAI {
    private PieceColor aiColor;
    private DifficultyLevel difficulty;
    private Random random;
    
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD
    }
    
    public ChessAI(PieceColor aiColor, DifficultyLevel difficulty) {
        this.aiColor = aiColor;
        this.difficulty = difficulty;
        this.random = new Random();
    }
    
    public Move getBestMove(ChessGame game) {
        switch (difficulty) {
            case EASY:
                return getRandomMove(game);
            case MEDIUM:
                return getMediumMove(game);
            case HARD:
                return getHardMove(game);
            default:
                return getRandomMove(game);
        }
    }
    
    private Move getRandomMove(ChessGame game) {
        List<Move> allMoves = getAllPossibleMoves(game);
        if (allMoves.isEmpty()) return null;
        return allMoves.get(random.nextInt(allMoves.size()));
    }
    
    private Move getMediumMove(ChessGame game) {
        List<Move> allMoves = getAllPossibleMoves(game);
        if (allMoves.isEmpty()) return null;
        
        // 50% chance to make a good move, 50% random
        if (random.nextBoolean()) {
            return findCapturingMove(allMoves, game);
        }
        return allMoves.get(random.nextInt(allMoves.size()));
    }
    
    private Move getHardMove(ChessGame game) {
        List<Move> allMoves = getAllPossibleMoves(game);
        if (allMoves.isEmpty()) return null;
        
        // Try to find best move using simple evaluation
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        
        for (Move move : allMoves) {
            int score = evaluateMove(move, game);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        
        return bestMove != null ? bestMove : allMoves.get(0);
    }
    
    private List<Move> getAllPossibleMoves(ChessGame game) {
        List<Move> allMoves = new ArrayList<>();
        Board board = game.getBoard();
        
        for (Piece piece : board.getPieces(aiColor)) {
            Position from = piece.getPosition();
            List<Position> legalMoves = game.getLegalMovesForPiece(from);
            
            for (Position to : legalMoves) {
                allMoves.add(new Move(from, to));
            }
        }
        
        return allMoves;
    }
    
    private Move findCapturingMove(List<Move> moves, ChessGame game) {
        Board board = game.getBoard();
        
        for (Move move : moves) {
            Piece target = board.getPiece(move.getTo());
            if (target != null && target.getColor() != aiColor) {
                return move;
            }
        }
        
        return moves.get(random.nextInt(moves.size()));
    }
    
    private int evaluateMove(Move move, ChessGame game) {
        Board board = game.getBoard();
        int score = 0;
        
        // Capture value
        Piece target = board.getPiece(move.getTo());
        if (target != null) {
            score += getPieceValue(target) * 10;
        }
        
        // Center control
        int row = move.getTo().getRow();
        int col = move.getTo().getCol();
        if (row >= 2 && row <= 5 && col >= 2 && col <= 5) {
            score += 5;
        }
        
        // Development
        Piece piece = board.getPiece(move.getFrom());
        if (piece != null && !piece.hasMoved()) {
            score += 3;
        }
        
        return score;
    }
    
    private int getPieceValue(Piece piece) {
        switch (piece.getType()) {
            case PAWN: return 1;
            case KNIGHT: return 3;
            case BISHOP: return 3;
            case ROOK: return 5;
            case QUEEN: return 9;
            case KING: return 100;
            default: return 0;
        }
    }
}
