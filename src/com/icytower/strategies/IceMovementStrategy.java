package com.icytower.strategies;

import com.icytower.interfaces.MovementStrategy;
import com.icytower.core.GameObject;
import com.icytower.entities.Player;
import com.icytower.systems.InputManager;
import java.awt.event.KeyEvent;

public class IceMovementStrategy implements MovementStrategy {
    
    private boolean wasSpacePressed = false;
    private static final float ICE_ACCELERATION = 0.3f;
    private static final float ICE_FRICTION = 0.95f;
    private static final float MAX_SPEED = 6f;
    
    @Override
    public void move(GameObject gameObject, InputManager input) {
        // Cast to Player to access player-specific methods
        if (!(gameObject instanceof Player)) {
            return;
        }
        
        Player player = (Player) gameObject;
        
        float currentVelX = player.getVelocityX();
        
       
        if (input.isKeyDown(KeyEvent.VK_LEFT) || input.isKeyDown(KeyEvent.VK_A)) {
            currentVelX -= ICE_ACCELERATION;
            currentVelX = Math.max(currentVelX, -MAX_SPEED);
        } else if (input.isKeyDown(KeyEvent.VK_RIGHT) || input.isKeyDown(KeyEvent.VK_D)) {
            currentVelX += ICE_ACCELERATION;
            currentVelX = Math.min(currentVelX, MAX_SPEED);
        } else {
            
            currentVelX *= ICE_FRICTION;
            if (Math.abs(currentVelX) < 0.1f) {
                currentVelX = 0f;
            }
        }
        
        player.setVelocityX(currentVelX);
        
        // קפיצה
        boolean spaceCurrentlyPressed = input.isKeyDown(KeyEvent.VK_SPACE);
        
        if (spaceCurrentlyPressed && !wasSpacePressed) {
            player.jump();
        }
        
        wasSpacePressed = spaceCurrentlyPressed;
        
        // UP/W
        if (input.isKeyDown(KeyEvent.VK_UP) || input.isKeyDown(KeyEvent.VK_W)) {
            if (player.isOnGround()) {
                player.jump();
            }
        }
    }
}