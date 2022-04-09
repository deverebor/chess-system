package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();
        
        while(!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.print("\nSource: ");
                ChessPosition source = UI.readChessPosition(scanner);
                
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
    
                System.out.print("\nTarget: ");
                ChessPosition target = UI.readChessPosition(scanner);
    
                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                
                if(capturedPiece != null) {
                    captured.add(capturedPiece);
                }
                
                if(chessMatch.getPawnPromotion() != null) {
                    System.out.print("\nEnter an letter to promote pawn (B/N/R/Q): ");
                    String pawnType = scanner.nextLine().toUpperCase();
                    while (
                            !pawnType.equals("B")
                            && !pawnType.equals("N")
                            && !pawnType.equals("R")
                            && !pawnType.equals("Q")
                    ) {
                        System.out.print("Invalid letter! Enter the correct letter (B/N/R/Q): ");
                        pawnType = scanner.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPawn(pawnType);
                }
            } catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                System.out.println("\nPress ENTER to continue.");
                scanner.nextLine();
            }
        }
        
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
        
    }
}
