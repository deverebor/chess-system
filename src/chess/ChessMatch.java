package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    private int turn;
    private boolean check;
    private boolean checkMate;
    
    private ChessPiece pawnPromotion;
    private ChessPiece enPassantVulnerable;
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
    
    public boolean getCheckMate() { return checkMate; }
    
    public ChessPiece getEnPassantVulnerable() { return enPassantVulnerable; }
    
    public ChessPiece getPawnPromotion() { return pawnPromotion; }
    
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
        
        if(validateCheckInKing(currentPlayer) || validateIllegalCastling(source, target)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        
        ChessPiece movedPiece = (ChessPiece) board.piece(target);
        
        // Pawn Promotion
        pawnPromotion = null;
        if(movedPiece instanceof Pawn) {
            if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
                || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)
            ) {
                pawnPromotion = (ChessPiece) board.piece(target);
                pawnPromotion = replacePromotedPawn("Q");
            }
        }
        
        check = validateCheckInKing(validateOpponentColor(currentPlayer));
        
        if(validateCheckMated(validateOpponentColor(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        
        // En Passant
        if(
                movedPiece instanceof Pawn
                && target.getRow() == source.getRow() - 2
                || target.getRow() == source.getRow() + 2
        ) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }
        
        return (ChessPiece)capturedPiece;
    }
    
    private Piece makeMove(Position source, Position target) {
        ChessPiece newSource = (ChessPiece)board.removePiece(source);
        newSource.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(newSource, target);
        
        if(capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPiecesOnTheBoard.add(capturedPiece);
        }
        
        // makeCastling
        //King side
        if(newSource instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position originRookPosition = new Position(source.getRow(), source.getColumn() + 3);
            Position targetRookPosition = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(originRookPosition);
            board.placePiece(rook, targetRookPosition);
            rook.increaseMoveCount();
        }
    
        //Queen side
        if(newSource instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position originRookPosition = new Position(source.getRow(), source.getColumn() - 4);
            Position targetRookPosition = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(originRookPosition);
            board.placePiece(rook, targetRookPosition);
            rook.increaseMoveCount();
        }
        
        //makeEnPassant
        if(newSource instanceof Pawn) {
            if(source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if(newSource.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPiecesOnTheBoard.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }
        
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece newSource = (ChessPiece)board.removePiece(target);
        newSource.decreaseMoveCount();
        board.placePiece(newSource, source);
        
        if(capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPiecesOnTheBoard.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    
        // undoCastling
        //King side
        if(newSource instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position originRookPosition = new Position(source.getRow(), source.getColumn() + 3);
            Position targetRookPosition = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetRookPosition);
            board.placePiece(rook, originRookPosition);
            rook.decreaseMoveCount();
        }
    
        //Queen side
        if(newSource instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position originRookPosition = new Position(source.getRow(), source.getColumn() - 4);
            Position targetRookPosition = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetRookPosition);
            board.placePiece(rook, originRookPosition);
            rook.decreaseMoveCount();
        }
    
        //undoEnPassant
        if(newSource instanceof Pawn) {
            if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if(newSource.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }
    }
    
    public ChessPiece replacePromotedPawn(String newPiceType) {
        if(
                !newPiceType.equals("B")
                && !newPiceType.equals("N")
                && !newPiceType.equals("R")
                && !newPiceType.equals("Q")
        ) {
            throw new InvalidParameterException("Invalid piece type");
        }
        
        Position promotedPiecePosition = pawnPromotion.getChessPosition().toPosition();
        Piece newPawn = board.removePiece(promotedPiecePosition);
        piecesOnTheBoard.remove(newPawn);
        
        ChessPiece newPromotedPawn = newPromotedPiece(newPiceType, pawnPromotion.getColor());
        board.placePiece(newPromotedPawn, promotedPiecePosition);
        piecesOnTheBoard.add(newPromotedPawn);
        
        return newPromotedPawn;
        
    }
    
    private ChessPiece newPromotedPiece(String pieceType, Color color) {
        if(pieceType.equals("B")) return new Bishop(board, color);
        if(pieceType.equals("N")) return new Knight(board, color);
        if(pieceType.equals("R")) return new Rook(board, color);
        return new Queen(board, color);
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
    
    private boolean validateCheckMated(Color color) {
        if(!validateCheckInKing(color)) {
            return false;
        }
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece) piece).getColor() == color).toList();
        for(Piece pieceInList : opponentPieces) {
            boolean[][] possiblePieceMoves = pieceInList.possibleMoves();
            for(int i = 0; i < board.getRows(); i++) {
                for(int j = 0; j < board.getColumns(); j++) {
                    if(possiblePieceMoves[i][j]) {
                        Position source = ((ChessPiece)pieceInList).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean isInCheckMate = validateCheckInKing(color);
                        undoMove(source, target, capturedPiece);
                        
                        if(!isInCheckMate) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    private boolean validateIllegalCastling(Position source, Position target) {
        ChessPiece movedPiece = (ChessPiece) board.piece(target);
        boolean isIllegal = false;
        
        Position squareBetweenKing = new Position(source.getRow(),
                (source.getColumn() > target.getColumn()) ? source.getColumn() - 1 : target.getColumn() - 1);
        
        if (movedPiece instanceof King) {
            if (Math.abs(source.getColumn() - target.getColumn()) == 2) {
                Piece capturedPiece = makeMove(target, squareBetweenKing);
    
                if (validateCheckInKing(currentPlayer)) {
                    isIllegal = true;
                }
    
                undoMove(target, squareBetweenKing, capturedPiece);
            }
        }
        return isIllegal;
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }
    
    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
    
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
