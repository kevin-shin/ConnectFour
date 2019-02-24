import java.io.*;
import java.util.*;
import java.util.Scanner;

public class ConnectFour {
    enum Color {
        RED, BLACK, EMPTY}

    class Player {
        private Color color;
        public Player(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }


    public ConnectFour(){};

    private int colSize = 7;
    private int rowSize = 6;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Color[][] board = new Color[colSize][rowSize];
    private Scanner scanner = new Scanner(System.in);

    public Color[][] getBoard() {
        return board;
    }

    public void initialize() {
        if (colSize <= 4 || rowSize <= 4) {
            System.out.println("Invalid Board Params");
            return;
        }

        for (int i = 0; i < rowSize; i++) {
            Color[] col = new Color[colSize];
            for (int j = 0; j < colSize; j++) {
                col[j] = Color.EMPTY;
            }
            board[i] = col;
        }
        player1 = new Player(Color.RED);
        player2 = new Player(Color.BLACK);

        currentPlayer = player1;
    }

    public void displayBoard() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    private void playerMove() {
        System.out.println(" ");
        System.out.println("It's your turn player " + currentPlayer.getColor() + ". Which column would you like to choose?");
        updateBoard();

    }

    private void updateBoard() {
        while (true) {
            int col = legalWidth();
            for (int i = rowSize - 1; i >= 0; i--) {
                if (board[i][col].equals(Color.EMPTY)) {
                    board[i][col] = currentPlayer.getColor();
                    if (currentPlayer == player1){
                        currentPlayer = player2;
                    }
                    else if (currentPlayer == player2){
                        currentPlayer = player1;
                    }
                    return;
                }
            }
            System.out.println("Illegal Move. Try again.");
        }
    }

    private int legalWidth() {
        while (true) {
            System.out.println(">>> ");
            Integer userInput = scanner.nextInt();
            if (userInput >= 0 && userInput < rowSize){
                return userInput;
            }
        }
    }

    public void run() {
        boolean winner = false;
        while (!winner){
            displayBoard();
            playerMove();
        }

    }

    public static void main(String[] args) {
        ConnectFour cf = new ConnectFour();
        cf.initialize();
        cf.run();
    }
}