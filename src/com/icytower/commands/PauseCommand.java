package com.icytower.commands;
import com.icytower.managers.GameManager;

public class PauseCommand implements Command {
    @Override
    public void execute() {
        GameManager.getInstance().setState("PAUSED");
    }
    
    @Override
    public void undo() {
        GameManager.getInstance().setState("PLAYING");
    }
}