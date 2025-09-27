// Default Movement Strategy
package com.icytower.strategies;
import com.icytower.interfaces.MovementStrategy;
import com.icytower.systems.InputManager;
import com.icytower.entities.GameObject;
import java.awt.event.KeyEvent;

public class DefaultMovementStrategy implements MovementStrategy {
    private static final float MOVE_SPEED = 5f;
    private static final float FRICTION = 0.8f;
    private static final float MAX_SPEED = 8f;
    
    @Override
    public void move(GameObject object, InputManager inputManager) {
        if (inputManager.isKeyPressed(KeyEvent.VK_LEFT) || inputManager.isKeyPressed(KeyEvent.VK_A)) {
            object.velocityX -= MOVE_SPEED * 0.3f;
        }
        if (inputManager.isKeyPressed(KeyEvent.VK_RIGHT) || inputManager.isKeyPressed(KeyEvent.VK_D)) {
            object.velocityX += MOVE_SPEED * 0.3f;
        }
        
        // Apply friction
        object.velocityX *= FRICTION;
        
        // Limit speed
        if (object.velocityX > MAX_SPEED) object.velocityX = MAX_SPEED;
        if (object.velocityX < -MAX_SPEED) object.velocityX = -MAX_SPEED;
    }
}