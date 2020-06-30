package com.mwidyr.tictactoe.service;

import com.mwidyr.tictactoe.entity.TicTacToe;
import com.mwidyr.tictactoe.entity.TicTacToeResponse;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class TicTacToeService implements ITicTacToeService {
    // Name-constants to represent the seeds and cell contents
    public static final int EMPTY = 0;
    public static final int CROSS = 1;
    public static final int NOUGHT = 2;

    // Name-constants to represent the various states of the game
    public static final int PLAYING = 9;
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


    @Override
    public TicTacToeResponse play(TicTacToe request) {
        TicTacToeResponse ticTacToeResponse = new TicTacToeResponse();
        if (currentState != PLAYING) {
            //init new game
            System.out.println("Init Game!");
            ticTacToeResponse = initGame(request);
            if (null != ticTacToeResponse.getMessage() && !ticTacToeResponse.getMessage().equalsIgnoreCase("")) {
                ticTacToeResponse.setBoard(board);
                return ticTacToeResponse;
            }
            ticTacToeResponse.setMessage("Initialize Game with grid : " + request.getGridSize());
        } else {
            if (!request.getPlayer().equalsIgnoreCase((currentPlayer == NOUGHT) ? "O" : "X")) {
                ticTacToeResponse.setBoard(board);
                ticTacToeResponse.setMessage("Wrong player turn, it should be : " + ((currentPlayer == NOUGHT) ? "O" : "X"));
                return ticTacToeResponse;
            }
            ticTacToeResponse = playerMove(currentPlayer, request); // update currentRow and currentCol
            if (null != ticTacToeResponse.getMessage() && !ticTacToeResponse.getMessage().equalsIgnoreCase("")) {
                ticTacToeResponse.setBoard(board);
                return ticTacToeResponse;
            }
            System.out.println();
            updateGame(currentPlayer, currentRow, currentCol); // update currentState
            printBoard();
            // Print message if game-over
            String gameOverMessage = "";
            if (currentState == CROSS_WON) {
                gameOverMessage = "'X' won! Bye!";
                System.out.println(gameOverMessage);
            } else if (currentState == NOUGHT_WON) {
                gameOverMessage = "'O' won! Bye!";
                System.out.println(gameOverMessage);
            } else if (currentState == DRAW) {
                gameOverMessage = "It's a Draw! Bye!";
                System.out.println(gameOverMessage);
            }
            // Switch player
            currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
            if (!gameOverMessage.equalsIgnoreCase("")) {
                ticTacToeResponse.setMessage(gameOverMessage);

            }
        }
        ticTacToeResponse.setBoard(board);

        return ticTacToeResponse;
    }

    /**
     * Initialize the game-board contents and the current states
     */
    public TicTacToeResponse initGame(TicTacToe request) {
        System.out.print("Please enter TicTacToe grid size start from 2: " + request.getGridSize());

        GRIDSIZE = request.getGridSize();
        if (GRIDSIZE < 2) {
            String notValid = "Not valid Grid, please re-enter again";
            System.out.print(notValid);
            return new TicTacToeResponse(board, notValid);
        }
        board = new int[GRIDSIZE][GRIDSIZE];
        for (int row = 0; row < GRIDSIZE; ++row) {
            for (int col = 0; col < GRIDSIZE; ++col) {
                board[row][col] = EMPTY;  // all cells empty
            }
        }
        currentState = PLAYING; // ready to play
        currentPlayer = CROSS;  // cross plays first
        return new TicTacToeResponse(board);
    }

    /**
     * Player with the "theSeed" makes one move, with input validation.
     * Update global variables "currentRow" and "currentCol".
     */
    public TicTacToeResponse playerMove(int theSeed, TicTacToe request) {
        TicTacToeResponse ticTacToeResponse = new TicTacToeResponse();
        if (theSeed == CROSS) {
            System.out.print("Player 'X', enter your move (row[1-" + GRIDSIZE + "] column[1-" + GRIDSIZE + "]): " + request.getRow() + " - " + request.getColumn());
        } else {
            System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): " + request.getRow() + " - " + request.getColumn());
        }
        int row = request.getRow() - 1;  // array index starts at 0 instead of 1
        int col = request.getColumn() - 1;
        if (row >= 0 && row < GRIDSIZE && col >= 0 && col < GRIDSIZE && board[row][col] == EMPTY) {
            currentRow = row;
            currentCol = col;
            board[currentRow][currentCol] = theSeed;  // update game-board content
        } else {
            String notValidMove = "This move at (" + (row + 1) + "," + (col + 1)
                    + ") is not valid. Try again...";
            System.out.println(notValidMove);
            ticTacToeResponse.setMessage(notValidMove);
        }
        return ticTacToeResponse;
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
