package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        
        while(true) {
            UI.printBoard(chessMatch.getPieces());
            System.out.print("\nSource: ");
            ChessPosition source = UI.readChessPosition(scanner);
            
            System.out.print("\nTarget: ");
            ChessPosition target = UI.readChessPosition(scanner);
    
            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }
        
    }
}
