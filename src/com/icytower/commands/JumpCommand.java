package com.icytower.commands;
import com.icytower.entities.Player;
import com.icytower.interfaces.Command;
public class JumpCommand implements Command {
    private Player player;
    
    public JumpCommand(Player player) {
        this.player = player;
    }
    
    @Override
    public void execute() {
        player.jump();
    }
    
    @Override
    public void undo() {
        // Jump can't be undone in this context
    }
}