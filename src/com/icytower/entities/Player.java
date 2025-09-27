// Player Class with Strategy Pattern
package com.icytower.entities;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import com.icytower.core.GameObject;
import com.icytower.graphics.Renderer;
import com.icytower.core.GameManager;
import com.icytower.systems.SoundManager;
import com.icytower.systems.InputManager;
import com.icytower.strategies.IceMovementStrategy;
import com.icytower.interfaces.MovementStrategy;
import com.icytower.strategies.DefaultMovementStrategy;
import com.icytower.enums.PlayerState;

public class Player extends GameObject {
    private MovementStrategy movementStrategy;
    private boolean onGround;
    private PlayerState playerState;
    private static final float GRAVITY = 0.5f;
    
    public Player(float x, float y) {
        super(x, y, 30, 40);
        this.movementStrategy = new DefaultMovementStrategy();
        this.onGround = false;
        this.playerState = PlayerState.IDLE;
        this.color = new Color(255, 100, 100);
    }
    
    @Override
    public void update(float deltaTime) {
        movementStrategy.move(this, InputManager.getInstance());
        applyGravity();
        updatePosition(deltaTime);
        updateState();
        
        // Notify observers
        GameManager.getInstance().notifyPlayerPositionChanged(x, y);
    }
    
    private void applyGravity() {
        if (!onGround) {
            velocityY += GRAVITY;
        }
    }
    
    private void updatePosition(float deltaTime) {
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
        
        // Keep within bounds
        x = Math.max(0, Math.min(x, 800 - width));
    }
    
    private void updateState() {
        if (onGround) {
            if (Math.abs(velocityX) > 0.1f) {
                playerState = PlayerState.RUNNING;
            } else {
                playerState = PlayerState.IDLE;
            }
        } else {
            if (velocityY < 0) {
                playerState = PlayerState.JUMPING;
            } else {
                playerState = PlayerState.FALLING;
            }
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        renderer.setColor(color);
        renderer.fillRoundRect((int)x, (int)y, (int)width, (int)height, 10, 10);
        
        // Draw eyes
        renderer.setColor(Color.BLACK);
        renderer.fillOval((int)x + 8, (int)y + 10, 4, 4);
        renderer.fillOval((int)x + 18, (int)y + 10, 4, 4);
        
        // Draw mouth based on state
        if (playerState == PlayerState.JUMPING) {
            renderer.drawArc((int)x + 10, (int)y + 18, 10, 8, 0, 180);
        } else {
            renderer.drawArc((int)x + 10, (int)y + 18, 10, 8, 0, -180);
        }
    }
    
    @Override
    public Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    
    public void jump() {
        if (onGround) {
            velocityY = -12f;
            onGround = false;
            SoundManager.getInstance().playSound("jump");
        }
    }
    
    public void landOn(float platformY) {
        y = platformY - height;
        velocityY = 0;
        onGround = true;
    }
    
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }
    
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
    public PlayerState getPlayerState() { return playerState; }
}