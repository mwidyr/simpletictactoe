import java.util.Scanner;

/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version.
 * All variables/methods are declared as static (belong to the class)
 * in the non-OO version.
 */
public class TTTConsoleNonOO2P {
    // Name-constants to represent the seeds and cell contents
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    // Name-constants to represent the various states of the game
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int NOUGHT_WON = 3;

    // The game board and the game status
    public static int GRIDSIZE = 2; // number of rows and columns
    public static int[][] board = new int[GRIDSIZE][GRIDSIZE]; // game board in 2D array
    //  containing (EMPTY, CROSS, NOUGHT)
    public static int currentState;  // the current state of the game
    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
    public static int currentPlayer; // the current player (CROSS or NOUGHT)
    public static int currentRow, currentCol; // current seed's row and column

    public static Scanner in = new Scanner(System.in); // the input Scanner

    /**
     * The entry main method (the program starts here)
     */
    public static void main(String[] args) {
        // Initialize the game-board and current status
        TTTConsoleNonOO2P game = new TTTConsoleNonOO2P();
        game.initGame();
        // Play the game once
        do {
            game.playerMove(currentPlayer); // update currentRow and currentCol
            game.updateGame(currentPlayer, currentRow, currentCol); // update currentState
            game.printBoard();
            // Print message if game-over
            if (currentState == CROSS_WON) {
                System.out.println("'X' won! Bye!");
            } else if (currentState == NOUGHT_WON) {
                System.out.println("'O' won! Bye!");
            } else if (currentState == DRAW) {
                System.out.println("It's a Draw! Bye!");
            }
            // Switch player
            currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
        } while (currentState == PLAYING); // repeat if not game-over
    }

    /**
     * Initialize the game-board contents and the current states
     */
    public void initGame() {
        System.out.print("Please enter TicTacToe grid size start from 2: ");
        do {
            GRIDSIZE = in.nextInt();
            if (GRIDSIZE < 2) {
                System.out.print("Not valid Grid, please re-enter again : ");
            }
        } while (GRIDSIZE < 2); // repeat so grid size will be eligible
        board = new int[GRIDSIZE][GRIDSIZE];
        for (int row = 0; row < GRIDSIZE; ++row) {
            for (int col = 0; col < GRIDSIZE; ++col) {
                board[row][col] = EMPTY;  // all cells empty
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = CROSS;  // cross plays first
    }

    /**
     * Player with the "theSeed" makes one move, with input validation.
     * Update global variables "currentRow" and "currentCol".
     */
    public void playerMove(int theSeed) {
        boolean validInput = false;  // for input validation
        do {
            if (theSeed == CROSS) {
                System.out.print("Player 'X', enter your move (row[1-" + GRIDSIZE + "] column[1-" + GRIDSIZE + "]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
            }
            int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
            int col = in.nextInt() - 1;
            if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE && board[row][col] == EMPTY) {
                currentRow = row;
                currentCol = col;
                board[currentRow][currentCol] = theSeed;  // update game-board content
                validInput = true;  // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);  // repeat until input is valid
    }

    /**
     * Update the "currentState" after the player with "theSeed" has placed on
     * (currentRow, currentCol).
     */
    public void updateGame(int theSeed, int currentRow, int currentCol) {
        if (hasWon(theSeed, currentRow, currentCol)) {  // check if winning move
            currentState = (theSeed == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = DRAW;
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /**
     * Return true if it is a draw (no more empty cell)
     */
    public boolean isDraw() {
        for (int row = 0; row < GRIDSIZE; ++row) {
            for (int col = 0; col < GRIDSIZE; ++col) {
                if (board[row][col] == EMPTY) {
                    return false;  // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /**
     * Return true if the player with "theSeed" has won after placing at
     * (currentRow, currentCol)
     */
    public boolean hasWon(int theSeed, int currentRow, int currentCol) {
        boolean rowStepBefore = true;
        boolean rowStepNow = false;
        boolean colStepBefore = true;
        boolean colStepNow = false;
        boolean diagonalStepBefore = true;
        boolean diagonalStepNow = false;
        boolean oppDiagonalStepBefore = true;
        boolean oppDiagonalStepNow = false;
        for (int i = 0; i < GRIDSIZE; i++) {
            // in-the-row
            if (rowStepBefore) {
                if (board[currentRow][i] == theSeed) {
                    rowStepNow = true;
                } else {
                    rowStepBefore = false;
                    rowStepNow = false;
                }
                if (i == (GRIDSIZE - 1) && rowStepBefore && rowStepNow) {
                    break;
                }
                rowStepBefore = rowStepNow;
            }
            // in-the-columns
            if (colStepBefore) {
                if (board[i][currentCol] == theSeed) {
                    colStepNow = true;
                } else {
                    colStepBefore = false;
                    colStepNow = false;
                }
                if (i == (GRIDSIZE - 1) && colStepBefore && colStepNow) {
                    break;
                }
                colStepBefore = colStepNow;
            }
            // in-the-diagonal
            if (currentRow == currentCol && diagonalStepBefore) {
                if (board[i][i] == theSeed) {
                    diagonalStepNow = true;
                } else {
                    diagonalStepBefore = false;
                    diagonalStepNow = false;
                }
                if (i == (GRIDSIZE - 1) && diagonalStepBefore && diagonalStepNow) {
                    break;
                }
                diagonalStepBefore = diagonalStepNow;
            }
            // in-the-diagonal
            if ((currentRow + currentCol == GRIDSIZE - 1) && oppDiagonalStepBefore) {
                if (board[i][(GRIDSIZE - 1) - i] == theSeed) {
                    oppDiagonalStepNow = true;
                } else {
                    oppDiagonalStepBefore = false;
                    oppDiagonalStepNow = false;
                }
                if (i == (GRIDSIZE - 1) && oppDiagonalStepBefore && oppDiagonalStepNow) {
                    break;
                }
                oppDiagonalStepBefore = oppDiagonalStepNow;
            }
        }
        return rowStepNow || colStepNow || diagonalStepNow || oppDiagonalStepNow;
    }

    /**
     * Print the game board
     */
    public void printBoard() {
        String baseDash = "----";
        StringBuilder dash = new StringBuilder();
        dash.append(baseDash.repeat(GRIDSIZE));
        for (int row = 0; row < GRIDSIZE; ++row) {
            for (int col = 0; col < GRIDSIZE; ++col) {
                printCell(board[row][col]); // print each of the cells
                if (col != GRIDSIZE - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != GRIDSIZE - 1) {
                System.out.println(dash); // print horizontal partition
            }
        }
        System.out.println();
    }

    /**
     * Print a cell with the specified "content"
     */
    public void printCell(int content) {
        switch (content) {
            case EMPTY:
                System.out.print("   ");
                break;
            case NOUGHT:
                System.out.print(" O ");
                break;
            case CROSS:
                System.out.print(" X ");
                break;
        }
    }
}