// Ice Movement Strategy
package com.icytower.strategies;
import com.icytower.interfaces.MovementStrategy;
import com.icytower.systems.InputManager;
import com.icytower.entities.GameObject;
import java.awt.event.KeyEvent;

public class IceMovementStrategy implements MovementStrategy {
    private static final float MOVE_SPEED = 3f;
    private static final float FRICTION = 0.95f; // Less friction on ice
    private static final float MAX_SPEED = 12f;
    
    @Override
    public void move(GameObject object, InputManager inputManager) {
        if (inputManager.isKeyPressed(KeyEvent.VK_LEFT) || inputManager.isKeyPressed(KeyEvent.VK_A)) {
            object.velocityX -= MOVE_SPEED * 0.2f;
        }
        if (inputManager.isKeyPressed(KeyEvent.VK_RIGHT) || inputManager.isKeyPressed(KeyEvent.VK_D)) {
            object.velocityX += MOVE_SPEED * 0.2f;
        }
        
        // Apply less friction (more slippery)
        object.velocityX *= FRICTION;
        
        // Higher max speed
        if (object.velocityX > MAX_SPEED) object.velocityX = MAX_SPEED;
        if (object.velocityX < -MAX_SPEED) object.velocityX = -MAX_SPEED;
    }
}