package com.icytower.entities;
 
 import java.awt.Color;
 import com.icytower.graphics.Renderer;
 
public class Particle {
    private float x, y, vx, vy;
    private Color color;
    private float life, maxLife;
    
    public Particle(float x, float y, float vx, float vy, Color color, float life) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.life = life;
        this.maxLife = life;
    }
    
    public void update(float deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        vy += 0.1f; // Simple gravity
        life -= deltaTime;
    }
    
    public void render(Renderer renderer) {
        float alpha = life / maxLife;
        Color fadedColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(alpha * 255));
        renderer.setColor(fadedColor);
        renderer.fillOval((int)x, (int)y, 3, 3);
    }
    
    public boolean isAlive() {
        return life > 0;
    }
// } 
// currentState != null) {
//             currentState.update();
//         }
//     }
    
//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         Renderer renderer = new Renderer((Graphics2D) g);
        
//         GameState currentState = gameManager.getCurrentState();
//         if (
}