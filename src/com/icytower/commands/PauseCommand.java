package com.icytower.commands;
import com.icytower.core.GameManager;
import com.icytower.interfaces.Command;
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