package TicTactoe;

import java.util.Scanner;
import Game.Board;

public class Game {

    public static void playerTurn(Board board, Scanner scanner) {
        System.out.println("Enter row: ");
        int row = scanner.nextInt();
        System.out.println("Enter column: ");
        int column = scanner.nextInt();
        Moves moves = new Moves(board);
        try {
            moves.CheckMove(row, column);
            moves.PlaceMove(row, column, "X");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            playerTurn(board, scanner);
        }
    }



    public static void main(String[] args) {

        Scanner scanner;

        while (true) {

            scanner = new Scanner(System.in);

            Board board = new Board(3, 3);
            Moves moves = new Moves(board);
            System.out.println(board);

            playerTurn(board, scanner);

//            if (isGameFinished(board)) {
//                break;
//            }
//
//            printBoard(board);
//
//            computerTurn(board);
//            if (isGameFinished(board)) {
//                break;
//            }
//            printBoard(board);
        }
    }




}
