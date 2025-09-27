// Default Movement Strategy
package com.icytower.strategies;

import com.icytower.interfaces.MovementStrategy;
import com.icytower.systems.InputManager;
import com.icytower.core.GameObject;
import java.awt.event.KeyEvent;

public class DefaultMovementStrategy implements MovementStrategy {
    private static final float MOVE_SPEED = 5f;
    private static final float FRICTION = 0.8f;
    private static final float MAX_SPEED = 8f;

    @Override
    public void move(GameObject object, InputManager inputManager) {
        // Move left
        if (inputManager.isKeyPressed(KeyEvent.VK_LEFT) || inputManager.isKeyPressed(KeyEvent.VK_A)) {
            object.setVelocityX(object.getVelocityX() - MOVE_SPEED * 0.3f);
        }

        // Move right
        if (inputManager.isKeyPressed(KeyEvent.VK_RIGHT) || inputManager.isKeyPressed(KeyEvent.VK_D)) {
            object.setVelocityX(object.getVelocityX() + MOVE_SPEED * 0.3f);
        }

        // Apply friction
        object.setVelocityX(object.getVelocityX() * FRICTION);

        // Limit speed
        if (object.getVelocityX() > MAX_SPEED) object.setVelocityX(MAX_SPEED);
        if (object.getVelocityX() < -MAX_SPEED) object.setVelocityX(-MAX_SPEED);
    }
}
