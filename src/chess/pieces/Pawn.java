package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    
    public Pawn(Board board, Color color) { super(board, color); }
    
    @Override
    public String toString() { return "P"; }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pawnPosition = new Position(0, 0);
        
        if(getColor() == Color.WHITE) {
            pawnPosition.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            pawnPosition.setValues(position.getRow() - 2, position.getColumn());
            Position pawnPositionTwo = new Position(position.getRow() - 1, position.getColumn());
            if(
                    getBoard().positionExists(pawnPosition)
                    && !getBoard().thereIsAPiece(pawnPosition)
                    && getBoard().positionExists(pawnPositionTwo)
                    && !getBoard().thereIsAPiece(pawnPositionTwo)
                    && getMoveCount() == 0
            ) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            pawnPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
            if(getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            pawnPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        } else {
            pawnPosition.setValues(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(pawnPosition) && !getBoard().thereIsAPiece(pawnPosition)) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            pawnPosition.setValues(position.getRow() + 2, position.getColumn());
            Position pawnPositionTwo = new Position(position.getRow() + 1, position.getColumn());
            if(
                    getBoard().positionExists(pawnPosition)
                    && !getBoard().thereIsAPiece(pawnPosition)
                    && getBoard().positionExists(pawnPositionTwo)
                    && !getBoard().thereIsAPiece(pawnPositionTwo)
                    && getMoveCount() == 0
            ) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            pawnPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
            if(getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            pawnPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        }
        if(getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
            matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
        }
    
        return matriz;
    }
}
