package com.icytower.entities;
import java.awt.Color;
import java.util.*;
import com.icytower.graphics.Renderer;
import com.icytower.enums.ParticleType;


// Complete Particle Class
public class Particle {
    private float x, y, vx, vy;
    private Color color;
    private float life, maxLife;
    private float size;
    private float gravity;
    private ParticleType type;
    
    // Basic constructor
    public Particle(float x, float y, float vx, float vy, Color color, float life) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.color = color;
        this.life = life;
        this.maxLife = life;
        this.size = 3.0f;
        this.gravity = 0.1f;
        this.type = ParticleType.NORMAL;
    }
    
    // Advanced constructor with size and type
    public Particle(float x, float y, float vx, float vy, Color color, float life, float size, ParticleType type) {
        this(x, y, vx, vy, color, life);
        this.size = size;
        this.type = type;
        
        // Set gravity based on particle type
        switch (type) {
            case SNOW:
                this.gravity = 0.05f;
                break;
            case SPARK:
                this.gravity = 0.02f;
                break;
            case DUST:
                this.gravity = 0.08f;
                break;
            case SMOKE:
                this.gravity = -0.02f; // Smoke rises
                break;
            default:
                this.gravity = 0.1f;
                break;
        }
    }
    
    public void update(float deltaTime) {
        // Update position
        x += vx * deltaTime;
        y += vy * deltaTime;
        
        // Apply gravity
        vy += gravity;
        
        // Apply air resistance based on particle type
        switch (type) {
            case SNOW:
                vx *= 0.99f;
                vy *= 0.98f;
                break;
            case SPARK:
                vx *= 0.95f;
                vy *= 0.95f;
                break;
            case DUST:
                vx *= 0.97f;
                vy *= 0.96f;
                break;
            case SMOKE:
                vx *= 0.98f;
                vy *= 0.99f;
                break;
            default:
                vx *= 0.98f;
                vy *= 0.98f;
                break;
        }
        
        // Decrease life
        life -= deltaTime;
        
        // Update size based on life for some particle types
        if (type == ParticleType.SPARK) {
            size = (life / maxLife) * 4.0f;
        } else if (type == ParticleType.SMOKE) {
            size = (1.0f - (life / maxLife)) * 6.0f + 2.0f; // Smoke expands over time
        }
    }
    
    public void render(Renderer renderer) {
        if (!isAlive()) return;
        
        float alpha = Math.max(0, Math.min(1, life / maxLife));
        int alphaValue = (int)(alpha * 255);
        
        // Ensure alpha is valid
        if (alphaValue <= 0) return;
        
        Color fadedColor = new Color(
            color.getRed(), 
            color.getGreen(), 
            color.getBlue(), 
            alphaValue
        );
        
        renderer.setColor(fadedColor);
        
        int renderSize = Math.max(1, (int)size);
        int renderX = (int)(x - size / 2);
        int renderY = (int)(y - size / 2);
        
        // Render based on particle type
        switch (type) {
            case SNOW:
                // Render as a small circle with white center
                renderer.fillOval(renderX, renderY, renderSize, renderSize);
                renderer.setColor(Color.WHITE);
                renderer.fillOval(renderX + 1, renderY + 1, Math.max(1, renderSize - 2), Math.max(1, renderSize - 2));
                break;
                
            case SPARK:
                // Render as a bright glowing particle
                renderer.fillOval(renderX, renderY, renderSize, renderSize);
                // Add glow effect
                Color glowColor = new Color(
                    Math.min(255, color.getRed() + 50),
                    Math.min(255, color.getGreen() + 50),
                    Math.min(255, color.getBlue() + 50),
                    alphaValue / 3
                );
                renderer.setColor(glowColor);
                renderer.fillOval(renderX - 1, renderY - 1, renderSize + 2, renderSize + 2);
                break;
                
            case DUST:
                // Render as a small square
                renderer.fillRect(renderX, renderY, renderSize, renderSize);
                break;
                
            case SMOKE:
                // Render as a soft circle with gradient
                renderer.fillOval(renderX, renderY, renderSize, renderSize);
                // Add soft edge
                Color softerColor = new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    alphaValue / 2
                );
                renderer.setColor(softerColor);
                renderer.fillOval(renderX - 1, renderY - 1, renderSize + 2, renderSize + 2);
                break;
                
            default:
                // Normal circular particle
                renderer.fillOval(renderX, renderY, renderSize, renderSize);
                break;
        }
    }
    
    public boolean isAlive() {
        return life > 0;
    }
    
    // Getters and Setters
    public float getX() { return x; }
    public float getY() { return y; }
    public float getVX() { return vx; }
    public float getVY() { return vy; }
    public Color getColor() { return color; }
    public float getLife() { return life; }
    public float getMaxLife() { return maxLife; }
    public float getSize() { return size; }
    public ParticleType getType() { return type; }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setVelocity(float vx, float vy) {
        this.vx = vx;
        this.vy = vy;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void setSize(float size) {
        this.size = Math.max(0.5f, size);
    }
    
    public void addVelocity(float dvx, float dvy) {
        this.vx += dvx;
        this.vy += dvy;
    }
    
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    
    public float getGravity() {
        return gravity;
    }
    
    public void setType(ParticleType type) {
        this.type = type;
    }
    
    public void resetLife(float newLife) {
        this.life = newLife;
        this.maxLife = newLife;
    }
    
    public float getLifePercentage() {
        return maxLife > 0 ? life / maxLife : 0;
    }
    
    // Utility method to check if particle is within bounds
    public boolean isWithinBounds(float minX, float minY, float maxX, float maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
    
    // Method to apply force to particle
    public void applyForce(float forceX, float forceY) {
        vx += forceX;
        vy += forceY;
    }
    
    // Method to bounce off surfaces
    public void bounce(float dampening) {
        vx *= -dampening;
        vy *= -dampening;
    }
    
    // Method to fade particle faster or slower
    public void fadeFaster(float multiplier) {
        life -= multiplier;
    }
    
    // Clone method for creating similar particles
    public Particle clone() {
        Particle newParticle = new Particle(x, y, vx, vy, new Color(color.getRGB()), maxLife, size, type);
        newParticle.life = this.life;
        newParticle.gravity = this.gravity;
        return newParticle;
    }
    
    // Method to create random variation of this particle
    public Particle createVariation(Random random) {
        float newX = x + (random.nextFloat() - 0.5f) * 10;
        float newY = y + (random.nextFloat() - 0.5f) * 10;
        float newVX = vx + (random.nextFloat() - 0.5f) * 2;
        float newVY = vy + (random.nextFloat() - 0.5f) * 2;
        float newSize = size + (random.nextFloat() - 0.5f) * 2;
        float newLife = maxLife + (random.nextFloat() - 0.5f) * maxLife * 0.5f;
        
        return new Particle(newX, newY, newVX, newVY, color, newLife, newSize, type);
    }
}