package com.mwidyr.tictactoe.controller;

import com.mwidyr.tictactoe.entity.TicTacToe;
import com.mwidyr.tictactoe.entity.TicTacToeResponse;
import com.mwidyr.tictactoe.service.ITicTacToeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicTacToeController {
    @Autowired
    ITicTacToeService ticTacToeService;

    @PostMapping("/play")
    public TicTacToeResponse create(@RequestBody TicTacToe request) {
        return ticTacToeService.play(request);
    }

}
