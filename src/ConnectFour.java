/**
 * Kevin Shin
 * Rent the Runway Take Home Code Challenge
 * Submitted 2/25
 *
 * ConnectFour.java is a program which executes a game of Connect4 between two players through the console.
 * Users are prompted to type in the column number to which they would like to place their piece, and the
 * turns alternate until a player wins, or a stalemate happens.
 *
 * The program is set up so that, by default, the program starts with player RED. Further, at the end of each game,
 * the users are prompted to choose whether to start a new round.
 *
 * INSTRUCTIONS:
 *    Users should run the main method by any IDE of their choice, or compiling the file through the Terminal.
 *
 */

import java.util.Scanner;

public class ConnectFour {

    /*********INNER CLASSES************/
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
    private Player currentPlayer; //Approach: keep track of which user has control over the board by toggling this
                                  //instance variable.
    private boolean winner; //true when a board presents a winning move, false otherwise.
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

        //set up variables as specified above
        currentPlayer = player1;
        winner = false;
    }



    /**
     * Main wrapper function for getting the player's input, and checking whether the board is a winning move.
     * The function itself is used mainly to print out prompts to guide the user.
     */
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

    /**
     * updateBoard() and legalWidth() take in the user input and places their piece in the proper place on the board.
     */
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

    /***************   CHECKERS FOR BOARD CONFIGURATION **************/
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

    public static void main(String[] args) {
        ConnectFour cf = new ConnectFour();
        boolean playAgain = true;
        Scanner scanner = new Scanner(System.in);
        while (playAgain) {
            cf.initialize();
            cf.run();
            System.out.println("Play again? Yes/No");
            String input = scanner.nextLine();

            if ((!input.equalsIgnoreCase("Yes"))){
                playAgain = false;
            }

        }
    }

    /**************    HELPER FUNCTIONS  **************/
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

    private void switchPlayer(){
        if (currentPlayer.equals(player1)){
            currentPlayer = player2;
        }
        else if (currentPlayer.equals(player2)){
            currentPlayer = player1;
        }
    }

    public Color[][] getBoard() {
        return board;
    }


}