package com.mwidyr.tictactoe.entity;

public class TicTacToeResponse {
    int[][] board;
    String message;

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TicTacToeResponse(int[][] board, String message) {
        this.board = board;
        this.message = message;
    }

    public TicTacToeResponse() {
    }

    public TicTacToeResponse(int[][] board) {
        this.board = board;
    }
}
