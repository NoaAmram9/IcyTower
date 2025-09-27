// Abstract Base Class for Game Objects
package com.icytower.core;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import com.icytower.graphics.Renderer;

public abstract class GameObject {
    protected float x, y;
    protected float width, height;
    protected float velocityX, velocityY;
    protected boolean active;
    protected Color color;
    
    public GameObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocityX = 0;
        this.velocityY = 0;
        this.active = true;
        this.color = Color.BLUE;
    }
    
    public abstract void update(float deltaTime);
    public abstract void render(Renderer renderer);
    public abstract Rectangle2D.Float getBounds();
    
    public boolean intersects(GameObject other) {
        return getBounds().intersects(other.getBounds());
    }
    
    // Getters and Setters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public float getVelocityX() { return velocityX; }
    public float getVelocityY() { return velocityY; }
    public boolean isActive() { return active; }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setVelocity(float vx, float vy) {
        this.velocityX = vx;
        this.velocityY = vy;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    public void setVelocityX(float velocityX) {
    this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    public void setColor(Color color) {
        this.color = color;
    }

}
