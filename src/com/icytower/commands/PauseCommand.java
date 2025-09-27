package com.icytower.commands;

import com.icytower.core.GameManager;
import com.icytower.interfaces.Command;

public class PauseCommand implements Command {
    private GameManager gameManager;
    
   
    public PauseCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    
    @Override
    public void execute() {

        gameManager.togglePause();
    }
    
    public void undo() {
        gameManager.togglePause();
    }
}