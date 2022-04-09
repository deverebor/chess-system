package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }
    
    @Override
    public String toString() { return "Q"; }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position queenPosition = new Position(0, 0);
        
        //N
        queenPosition.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setRow(queenPosition.getRow() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
    
        //NW
        queenPosition.setValues(position.getRow() - 1, position.getColumn() -1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setValues(queenPosition.getRow() - 1, queenPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
    
    
        //NE
        queenPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setValues(queenPosition.getRow() - 1, queenPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
        
        
        //W
        queenPosition.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setColumn(queenPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
        
        //E
        queenPosition.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setColumn(queenPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
        
        //S
        queenPosition.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setRow(queenPosition.getRow() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
    
        //SW
        queenPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setValues(queenPosition.getRow() + 1, queenPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
    
        //SE
        queenPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(queenPosition) && !getBoard().thereIsAPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
            queenPosition.setValues(queenPosition.getRow() + 1, queenPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(queenPosition) && isThereOpponentPiece(queenPosition)) {
            matriz[queenPosition.getRow()][queenPosition.getColumn()] = true;
        }
        
        return matriz;
    }
}
