package com.icytower.strategies;

import com.icytower.interfaces.MovementStrategy;
import com.icytower.core.GameObject;
import com.icytower.entities.Player;
import com.icytower.systems.InputManager;
import java.awt.event.KeyEvent;

public class DefaultMovementStrategy implements MovementStrategy {
    
    private boolean wasSpacePressed = false;
    
    @Override
    public void move(GameObject gameObject, InputManager input) {
        // Cast to Player to access player-specific methods
        if (!(gameObject instanceof Player)) {
            return; // Only handle Player objects
        }
        
        Player player = (Player) gameObject;
        
        float horizontalSpeed = 0f;
        
    
        if (input.isKeyDown(KeyEvent.VK_LEFT) || input.isKeyDown(KeyEvent.VK_A)) {
            horizontalSpeed -= 5f;
        }
        
        if (input.isKeyDown(KeyEvent.VK_RIGHT) || input.isKeyDown(KeyEvent.VK_D)) {
            horizontalSpeed += 5f;
        }
        
        player.setVelocityX(horizontalSpeed);
        
       
        boolean spaceCurrentlyPressed = input.isKeyDown(KeyEvent.VK_SPACE);
        
        if (spaceCurrentlyPressed && !wasSpacePressed) {
            player.jump();
        }
        
        wasSpacePressed = spaceCurrentlyPressed;
        
        
        if (input.isKeyDown(KeyEvent.VK_UP) || input.isKeyDown(KeyEvent.VK_W)) {
            if (player.isOnGround()) {
                player.jump();
            }
        }
    }
}