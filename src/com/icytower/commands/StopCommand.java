package com.icytower.commands;
import com.icytower.interfaces.Command;
import com.icytower.entities.Player;

public class StopCommand implements Command {
    private Player player;
    
    public StopCommand(Player player) {
        this.player = player;
    }
    
    @Override
    public void execute() {
        player.stopMoving();
    }
    @Override
    public void undo() {
        // Stop can't be undone in this context
    }
}