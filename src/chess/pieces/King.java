package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    private ChessMatch chessMatch;
    
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    
    private boolean canMove(Position position) {
        ChessPiece positionPiece = (ChessPiece)getBoard().piece(position);
        return positionPiece == null || positionPiece.getColor() != getColor();
    }
    
    private boolean canRookCastle(Position position) {
        ChessPiece rook = (ChessPiece)getBoard().piece(position);
        return rook instanceof Rook && rook.getColor() == getColor() && rook.getMoveCount() == 0;
    }
    
    @Override
    public String toString() { return "K"; }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position kingPosition = new Position(0, 0);
        
        //N
        kingPosition.setValues(position.getRow() - 1, position.getColumn());
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //S
        kingPosition.setValues(position.getRow() + 1, position.getColumn());
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //W
        kingPosition.setValues(position.getRow(), position.getColumn() - 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //E
        kingPosition.setValues(position.getRow(), position.getColumn() + 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //NW
        kingPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //NE
        kingPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //SW
        kingPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
    
        //SE
        kingPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        if(getBoard().positionExists(kingPosition) && canMove(kingPosition)) {
            matriz[kingPosition.getRow()][kingPosition.getColumn()] = true;
        }
        
        //Castling
        if(getMoveCount() == 0 && !chessMatch.getCheck()) {
            //King Side
            Position rookKingSide = new Position(position.getRow(), position.getColumn() + 3);
            if(canRookCastle(rookKingSide)) {
                Position kingEmptySquare = new Position(position.getRow(), position.getColumn() + 1);
                Position rookEmptySquare = new Position(position.getRow(), position.getColumn() + 2);
                if(getBoard().piece(kingEmptySquare) == null && getBoard().piece(rookEmptySquare) == null) {
                    matriz[position.getRow()][position.getColumn() + 2] = true;
                    
                }
            }
            
            //Queen Side
            Position rookQueenSide = new Position(position.getRow(), position.getColumn() - 4);
            if(canRookCastle(rookQueenSide)) {
                Position kingEmptySquare = new Position(position.getRow(), position.getColumn() - 1);
                Position queenEmptySquare = new Position(position.getRow(), position.getColumn() - 2);
                Position rookEmptySquare = new Position(position.getRow(), position.getColumn() - 3);
                if(
                        getBoard().piece(kingEmptySquare) == null
                        && getBoard().piece(queenEmptySquare) == null
                        && getBoard().piece(rookEmptySquare) == null
                ) {
                    matriz[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        
        return matriz;
    }
}
