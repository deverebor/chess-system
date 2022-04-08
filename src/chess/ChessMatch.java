package chess;

import boardgame.Board;

public class ChessMatch {
    private final Board board;
    
    public ChessMatch() {
        board = new Board(8, 8);
    }
    
    public ChessPiece[][] getPieces() {
        ChessPiece[][] chessMatriz = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                chessMatriz[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return chessMatriz;
    }
}
