package com.mwidyr.tictactoe.service;

import com.mwidyr.tictactoe.entity.TicTacToe;
import com.mwidyr.tictactoe.entity.TicTacToeResponse;

public interface ITicTacToeService {
    TicTacToeResponse play(TicTacToe request);
}
