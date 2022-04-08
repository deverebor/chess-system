package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private boolean check;
    private Color currentPlayer;
    private final Board board;
    
    private final List<Piece> piecesOnTheBoard = new ArrayList<>();
    private final List<Piece> capturedPiecesOnTheBoard = new ArrayList<>();
    
    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }
    
    public int getTurn() { return turn; }
    
    public Color getCurrentPlayer() { return currentPlayer; }
    
    public boolean getCheck() { return check; }
    
    public ChessPiece[][] getPieces() {
        ChessPiece[][] chessMatriz = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                chessMatriz[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return chessMatriz;
    }
    
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        
        return board.piece(position).possibleMoves();
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        if(validateCheckInKing(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        
        check = validateCheckInKing(validateOpponentColor(currentPlayer));
        
        nextTurn();
        
        return (ChessPiece)capturedPiece;
    }
    
    private Piece makeMove(Position source, Position target) {
        Piece newSource = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(newSource, target);
        
        if(capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPiecesOnTheBoard.add(capturedPiece);
        }
        
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece newSource = board.removePiece(target);
        board.placePiece(newSource, source);
        
        if(capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPiecesOnTheBoard.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }
    
    private void validateSourcePosition(Position position) {
        if(!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if(!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }
    
    private void validateTargetPosition(Position source, Position target) {
        if(!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }
    
    private Color validateOpponentColor(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    private ChessPiece validateKingPiece(Color color) {
        List<Piece> newPieceBoardList =
                piecesOnTheBoard.stream().filter(piece -> ((ChessPiece) piece).getColor() == color).toList();
        for(Piece pieceInList : newPieceBoardList) {
            if(pieceInList instanceof King) {
                return (ChessPiece) pieceInList;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }
    
    private boolean validateCheckInKing(Color color) {
        Position kingPosition = validateKingPiece(color).getChessPosition().toPosition();
        List<Piece> opponentPieces =
                piecesOnTheBoard.stream().filter(piece -> ((ChessPiece) piece).getColor() == validateOpponentColor(color)).toList();
        for(Piece pieceInList : opponentPieces) {
            boolean[][] possiblePieceMoves = pieceInList.possibleMoves();
            if(possiblePieceMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    
    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
    
        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
