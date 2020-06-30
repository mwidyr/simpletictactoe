package com.mwidyr.tictactoe.entity;

public class TicTacToe {
    String player;
    Integer gridSize;
    Integer row;
    Integer column;

    public Integer getGridSize() {
        return gridSize;
    }

    public void setGridSize(Integer gridSize) {
        this.gridSize = gridSize;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public TicTacToe(String player, Integer gridSize, Integer row, Integer column) {
        this.player = player;
        this.gridSize = gridSize;
        this.row = row;
        this.column = column;
    }

    public TicTacToe() {
    }


}
