package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static ChessPosition readChessPosition(Scanner scanner) {
        try {
            String sc = scanner.nextLine();
            char column = sc.charAt(0);
            int row = Integer.parseInt(sc.substring(1));
    
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error creating ChessPosition: Valid values are a1 to h8.");
        }
        
    }
    
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(capturedPieces);
        System.out.println("\nTurn: " + chessMatch.getTurn());
        if(!chessMatch.getCheckMate()) {
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
    
            if(chessMatch.getCheck()) {
                System.out.println("The match is in CHECK!");
            }
        } else {
            System.out.println("CHECK MATE!!" + chessMatch.getCurrentPlayer() + " WINS!");
        }
    }
    
    public static void printBoard(ChessPiece[][] pieces) {
        for(int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for(int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }
    
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for(int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for(int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }
    
    private static void printPiece(ChessPiece piece, boolean pieceBackground) {
        if(pieceBackground) {
            System.out.print(ANSI_GREEN_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_CYAN + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
    
    private static void printCapturedPieces(List<ChessPiece> capturedPieces) {
        List<ChessPiece> whiteCapturedPieces =
                capturedPieces.stream().filter(index -> index.getColor() == Color.WHITE).toList();
        List<ChessPiece> blackCapturedPieces =
                capturedPieces.stream().filter(index -> index.getColor() == Color.BLACK).toList();
        
        System.out.println("Captured pieces: ");
        System.out.println("White: " + ANSI_CYAN + Arrays.toString(whiteCapturedPieces.toArray()) + ANSI_RESET);
        System.out.println("Black: " + ANSI_YELLOW + Arrays.toString(blackCapturedPieces.toArray()) + ANSI_RESET);
    }
}
