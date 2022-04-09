package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) { super(board, color); }
    
    @Override
    public String toString() { return "N"; }
    
    private boolean canMove(Position position) {
        ChessPiece positionPiece = (ChessPiece)getBoard().piece(position);
        return positionPiece == null || positionPiece.getColor() != getColor();
    }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position kingPosition = new Position(0, 0);
        
        //NW
        kingPosition.setValues(position.getRow() - 1, position.getColumn() - 2);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        

        kingPosition.setValues(position.getRow() - 2, position.getColumn() - 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        
        //NE
        kingPosition.setValues(position.getRow() - 2, position.getColumn() + 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        

        kingPosition.setValues(position.getRow() - 1, position.getColumn() + 2);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        
        //SE
        kingPosition.setValues(position.getRow() + 1, position.getColumn() + 2);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        

        kingPosition.setValues(position.getRow() + 2, position.getColumn() + 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        
        //SW
        kingPosition.setValues(position.getRow() + 2, position.getColumn() - 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        

        kingPosition.setValues(position.getRow() + 1, position.getColumn() - 2);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        
        return matriz;
    }
}
