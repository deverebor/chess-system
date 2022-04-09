package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    private ChessMatch chessMatch;
    
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    
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
            if(getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
            
            //En Passant white pieces
            if(position.getRow() == 3) {
                Position leftPawn = new Position(position.getRow(), position.getColumn() - 1);
                if(
                        getBoard().positionExists(leftPawn)
                        && isThereOpponentPiece(leftPawn)
                        && getBoard().piece(leftPawn) == chessMatch.getEnPassantVulnerable()
                ) {
                    matriz[leftPawn.getRow() - 1][leftPawn.getColumn()] = true;
                }
    
                Position rightPawn = new Position(position.getRow(), position.getColumn() + 1);
                if(
                        getBoard().positionExists(rightPawn)
                                && isThereOpponentPiece(rightPawn)
                                && getBoard().piece(rightPawn) == chessMatch.getEnPassantVulnerable()
                ) {
                    matriz[rightPawn.getRow() - 1][rightPawn.getColumn()] = true;
                }
            }
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
            if(getBoard().positionExists(pawnPosition) && isThereOpponentPiece(pawnPosition)) {
                matriz[pawnPosition.getRow()][pawnPosition.getColumn()] = true;
            }
    
            //En Passant black pieces
            if(position.getRow() == 4) {
                Position leftPawn = new Position(position.getRow(), position.getColumn() - 1);
                if(
                        getBoard().positionExists(leftPawn)
                        && isThereOpponentPiece(leftPawn)
                        && getBoard().piece(leftPawn) == chessMatch.getEnPassantVulnerable()
                ) {
                    matriz[leftPawn.getRow() + 1][leftPawn.getColumn()] = true;
                }
        
                Position rightPawn = new Position(position.getRow(), position.getColumn() + 1);
                if(
                        getBoard().positionExists(rightPawn)
                        && isThereOpponentPiece(rightPawn)
                        && getBoard().piece(rightPawn) == chessMatch.getEnPassantVulnerable()
                ) {
                    matriz[rightPawn.getRow() + 1][rightPawn.getColumn()] = true;
                }
            }
        }
    
        return matriz;
    }
}
