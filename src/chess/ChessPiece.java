package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
    private final Color color;
    
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece opponentPiece = (ChessPiece) getBoard().piece(position);
        return opponentPiece != null && opponentPiece.getColor() != color;
    }
}
