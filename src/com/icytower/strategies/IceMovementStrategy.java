// Ice Movement Strategy
package com.icytower.strategies;

import com.icytower.interfaces.MovementStrategy;
import com.icytower.systems.InputManager;
import com.icytower.core.GameObject;
import java.awt.event.KeyEvent;

public class IceMovementStrategy implements MovementStrategy {
    private static final float MOVE_SPEED = 3f;
    private static final float FRICTION = 0.95f; // Less friction on ice
    private static final float MAX_SPEED = 12f;

    @Override
    public void move(GameObject object, InputManager inputManager) {
        // Move left
        if (inputManager.isKeyPressed(KeyEvent.VK_LEFT) || inputManager.isKeyPressed(KeyEvent.VK_A)) {
            object.setVelocityX(object.getVelocityX() - MOVE_SPEED * 0.2f);
        }

        // Move right
        if (inputManager.isKeyPressed(KeyEvent.VK_RIGHT) || inputManager.isKeyPressed(KeyEvent.VK_D)) {
            object.setVelocityX(object.getVelocityX() + MOVE_SPEED * 0.2f);
        }

        // Apply less friction (more slippery)
        object.setVelocityX(object.getVelocityX() * FRICTION);

        // Higher max speed
        if (object.getVelocityX() > MAX_SPEED) object.setVelocityX(MAX_SPEED);
        if (object.getVelocityX() < -MAX_SPEED) object.setVelocityX(-MAX_SPEED);
    }
}
