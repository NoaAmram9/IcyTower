// Player Class with Strategy Pattern
package com.icytower.entities;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.BasicStroke;
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
    this.onGround = true;  
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
    Graphics2D g2d = renderer.getGraphics2D();

    // Glow effect
    g2d.setColor(new Color(150, 220, 255, 50));
    g2d.fillOval((int)x - 5, (int)y - 5, (int)width + 10, (int)height + 10);

    // Body with gradient glass effect
    Color topColor = new Color(100, 180, 255, 200);
    Color bottomColor = new Color(50, 130, 255, 200);
    GradientPaint bodyGradient = new GradientPaint(
        (int)x, (int)y, topColor,
        (int)x, (int)(y + height), bottomColor
    );
    g2d.setPaint(bodyGradient);
    g2d.fillRoundRect((int)x, (int)y, (int)width, (int)height, 12, 12);

    // Outline
    g2d.setStroke(new BasicStroke(2f));
    g2d.setColor(new Color(255, 255, 255, 100));
    g2d.drawRoundRect((int)x, (int)y, (int)width, (int)height, 12, 12);

    // Eyes (bigger, expressive)
    int eyeWidth = 6;
    int eyeHeight = 6;
    g2d.setColor(Color.BLACK);
    g2d.fillOval((int)x + 6, (int)y + 10, eyeWidth, eyeHeight);
    g2d.fillOval((int)x + 18, (int)y + 10, eyeWidth, eyeHeight);

    // Eye highlight
    g2d.setColor(Color.WHITE);
    g2d.fillOval((int)x + 7, (int)y + 11, 2, 2);
    g2d.fillOval((int)x + 19, (int)y + 11, 2, 2);

    // Mouth expressions based on state
    int mouthX = (int)x + 10;
    int mouthY = (int)y + 22;
    int mouthWidth = 10;
    int mouthHeight = 6;

    switch (playerState) {
        // case IDLE, RUNNING:
        //     g2d.drawLine(mouthX, mouthY + mouthHeight / 2, mouthX + mouthWidth, mouthY + mouthHeight / 2); // neutral
        //     break;
        case JUMPING:
            g2d.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 0, 180); // excited smile
            break;
        case FALLING:
            // g2d.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 0, -180); // sad
             g2d.drawLine(mouthX, mouthY + mouthHeight / 2, mouthX + mouthWidth, mouthY + mouthHeight / 2); // neutral
            break;
        // case HAPPY:
        //     g2d.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 0, 180);
        //     break;
        // case SAD:
        //     g2d.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 0, -180);
        //     break;
        // case SURPRISED:
        //     g2d.fillOval(mouthX + 2, mouthY, 6, 6); // round mouth
        //     break;
    }
}

    
    @Override
    public Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    
  public void jump() {
   
    if (onGround || Math.abs(velocityY) < 0.1f) {
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
     public void moveLeft() {
        velocityX = -5f; 
    }
    
    public void moveRight() {
        velocityX = 5f;
    }
    
    public void stopMoving() {
        velocityX = 0f;
    }
   
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityX() {
        return velocityX;
    }
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
    public PlayerState getPlayerState() { return playerState; }
    public void setPlayerState(PlayerState state) { this.playerState = state; }
}