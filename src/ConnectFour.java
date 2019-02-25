/**
 * Kevin Shin
 * Rent the Runway Take Home Code Challenge
 * Submitted 2/25
 *
 * ConnectFour.java is a program which executes a game of Connect4 between two players
 * through the console. Users are prompted to type in the column number to which they
 * would like to place their piece, and the turns alternate until
 */

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
    private boolean winner;
    private Color[][] board = new Color[colSize][rowSize];
    private Scanner scanner = new Scanner(System.in);


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
        winner = false;
    }

    public void displayBoard() {
        for (int i = 0; i < colSize; i++) {
            System.out.print("  " + i + "   ");
        }
        System.out.println(" ");
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                Color cell = board[i][j];
                if (cell.equals(Color.RED)){
                    System.out.print(" " + cell + "  ");
                }
                else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println(" ");
        }
    }

    private void playerMove() {
        displayBoard();
        System.out.println(" ");
        System.out.println("It's your turn player " + currentPlayer.getColor() + ". Which column would you like to choose?");
        updateBoard();
        if (didPlayerwin()){
            winner = true;
            System.out.println("\n" + "Congratulations, player " + currentPlayer.getColor() + ". You've won!");
        }
        else if (!playable()){
            winner = true;
            System.out.println("\n"+ "Stalemate.");
        }
        else {
            switchPlayer();
        }
    }

    private boolean playable(){
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                Color cell = board[i][j];
                if (cell.equals(Color.EMPTY)) {
                    return true;
                }
            }
        }
        return false;
    }


    private void updateBoard() {
        while (true) {
            int col = legalWidth();
            for (int i = rowSize - 1; i >= 0; i--) {
                if (board[i][col].equals(Color.EMPTY)) {
                    board[i][col] = currentPlayer.getColor();
                    return;
                    }
                }
            }
        }

    private void switchPlayer(){
        if (currentPlayer.equals(player1)){
            currentPlayer = player2;
        }
        else if (currentPlayer.equals(player2)){
            currentPlayer = player1;
        }
    }

    private int legalWidth() {
        while (true) {
            System.out.println(">>> ");
            Integer userInput = scanner.nextInt();
            if (userInput >= 0 && userInput <= rowSize){
                return userInput;
            }
            System.out.println("Illegal Move. Try again.");
        }
    }

    private boolean didPlayerwin(){
        return checkHorizontal()
                || checkVertical()
                || checkDiagonal();
    }

    private boolean checkHorizontal() {
        boolean win = false;
        Color[][] board = getBoard();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize-3; j++) {
                if ((board[i][j].equals(Color.RED) || board[i][j].equals(Color.BLACK)) &&
                        board[i][j].equals(board[i][j + 1]) &&
                        board[i][j].equals(board[i][j + 2]) &&
                        board[i][j].equals(board[i][j + 3])) {
                    win = true;
                }
            }
        }
        return win;
    }

    private boolean checkVertical(){
        boolean win = false;
        Color[][] board = getBoard();
        for (int i = 0; i < rowSize-3; i++) {
            for (int j = 0; j < colSize; j++) {
                if ((board[i][j].equals(Color.RED) || board[i][j].equals(Color.BLACK)) &&
                        board[i][j].equals(board[i+1][j]) &&
                        board[i][j].equals(board[i+2][j]) &&
                        board[i][j].equals(board[i+3][j])) {
                        win = true;
                    }
                }
            }
        return win;
    }

    private boolean checkDiagonal() {
        boolean win = false;
        Color[][] board = getBoard();
        for (int i = rowSize-1; i > 2; i--) {
            for (int j = colSize-1; j > 2; j--) {
                if ((board[i][j].equals(Color.RED) || board[i][j].equals(Color.BLACK)) &&
                        board[i][j].equals(board[i-1][j-1]) &&
                        board[i][j].equals(board[i-2][j-2]) &&
                        board[i][j].equals(board[i-3][j-3])) {
                    win = true;
                }
            }
        }
        for (int i = rowSize-1; i > 2; i--) {
            for (int j = 0; j < colSize-3; j++) {
                if ((board[i][j].equals(Color.RED) || board[i][j].equals(Color.BLACK)) &&
                        board[i][j].equals(board[i-1][j+1]) &&
                        board[i][j].equals(board[i-2][j+2]) &&
                        board[i][j].equals(board[i-3][j+3])) {
                    win = true;
                }
            }
        }
        return win;
    }

    public void run() {
        while (!winner){
            playerMove();
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    public static void main(String[] args) {
        ConnectFour cf = new ConnectFour();
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);
        while (playAgain) {
            cf.initialize();
            cf.run();
            System.out.println("Play again? Yes/No");
            String me = scanner.nextLine();

            if ((!me.equalsIgnoreCase("Yes"))){
                playAgain = false;
            }

        }
    }
}