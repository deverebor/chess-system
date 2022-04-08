package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() {
        return "R";
    }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position rookPosition = new Position(0, 0);
        
        //above
        rookPosition.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
            rookPosition.setRow(rookPosition.getRow() - 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
        }
        
        
        //left
        rookPosition.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
            rookPosition.setColumn(rookPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
        }
    
        //right
        rookPosition.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
            rookPosition.setColumn(rookPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
        }
    
        //below
        rookPosition.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(rookPosition) && !getBoard().thereIsAPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
            rookPosition.setRow(rookPosition.getRow() + 1);
        }
        if (getBoard().positionExists(rookPosition) && isThereOpponentPiece(rookPosition)) {
            matriz[rookPosition.getRow()][rookPosition.getColumn()] = true;
        }
        
        return matriz;
    }
}
