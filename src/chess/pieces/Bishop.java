package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() { return "B"; }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position bishopPosition = new Position(0, 0);
        
        //NW
        bishopPosition.setValues(position.getRow() - 1, position.getColumn() -1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
            bishopPosition.setValues(bishopPosition.getRow() - 1, bishopPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
        }
        
        
        //NE
        bishopPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
            bishopPosition.setValues(bishopPosition.getRow() - 1, bishopPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
        }
        
        //SW
        bishopPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
            bishopPosition.setValues(bishopPosition.getRow() + 1, bishopPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
        }
    
        //SE
        bishopPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(bishopPosition) && !getBoard().thereIsAPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
            bishopPosition.setValues(bishopPosition.getRow() + 1, bishopPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(bishopPosition) && isThereOpponentPiece(bishopPosition)) {
            matriz[bishopPosition.getRow()][bishopPosition.getColumn()] = true;
        }
        
        return matriz;
    }
}
