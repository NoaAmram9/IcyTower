package com.icytower.graphics;
public
class Camera {
    private float x, y;
    private float targetX, targetY;
    private static final float LERP_SPEED = 0.1f;
    
    public Camera() {
        this.x = 0;
        this.y = 0;
        this.targetX = 0;
        this.targetY = 0;
    }
    
    public void update(float playerX, float playerY, int screenHeight) {
        targetX = 0; // Keep camera centered horizontally
        targetY = Math.min(0, screenHeight / 2 - playerY);
        
        // Smooth camera movement
        x += (targetX - x) * LERP_SPEED;
        y += (targetY - y) * LERP_SPEED;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
}