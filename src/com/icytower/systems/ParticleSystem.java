// Particle System using Singleton Pattern
package com.icytower.systems;
import com.icytower.entities.Particle;
import com.icytower.graphics.Renderer;
import com.icytower.enums.ParticleType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

// Particle System - for effects
public class ParticleSystem {
    private static ParticleSystem instance;
    private List<Particle> particles;
    private Random random;
    private ParticleSystem() {
        particles = new ArrayList<>();
        random = new Random();
    }
    
    public static ParticleSystem getInstance() {
        if (instance == null) {
            instance = new ParticleSystem();
        }
        return instance;
    }
    
    public void addParticle(Particle particle) {
        particles.add(particle);
    }
    
    public void update(float deltaTime) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(deltaTime);
            if (!particle.isAlive()) {
                iterator.remove();
            }
        }
    }
    
    public void render(Renderer renderer, float cameraX, float cameraY) {
    for (Particle p : particles) {
        p.render(renderer, cameraX, cameraY);
    }
}

    
    // Create landing effect when player touches platform
    public void createLandingEffect(float x, float y) {
        for (int i = 0; i < 8; i++) {
            float vx = (random.nextFloat() - 0.5f) * 6;
            float vy = -random.nextFloat() * 4 - 1;
            Color dustColor = new Color(200, 200, 200, 150);
            addParticle(new Particle(x + random.nextFloat() * 30, y, vx, vy, dustColor, 1.5f, 2.0f, ParticleType.DUST));
        }
    }
    
    // Create jump effect when player jumps
    public void createJumpEffect(float x, float y) {
        for (int i = 0; i < 5; i++) {
            float vx = (random.nextFloat() - 0.5f) * 3;
            float vy = random.nextFloat() * 2;
            Color sparkColor = new Color(255, 255, 100, 200);
            addParticle(new Particle(x + 15, y + 40, vx, vy, sparkColor, 1.0f, 3.0f, ParticleType.SPARK));
        }
    }
    
    // Create snow effect for ambiance
    public void createSnowEffect(int screenWidth, int screenHeight, float cameraY) {
        // Only create snow occasionally to avoid too many particles
        if (random.nextInt(5) == 0) {
            float x = random.nextFloat() * screenWidth;
            float y = -cameraY - 50; // Above screen
            float vx = (random.nextFloat() - 0.5f) * 0.5f;
            float vy = random.nextFloat() * 2 + 0.5f;
            Color snowColor = new Color(255, 255, 255, 180);
            addParticle(new Particle(x, y, vx, vy, snowColor, 10.0f, 2.0f, ParticleType.SNOW));
        }
    }
    
    // Create ice platform break effect
    public void createIceBreakEffect(float x, float y, float width) {
        for (int i = 0; i < 12; i++) {
            float vx = (random.nextFloat() - 0.5f) * 8;
            float vy = -random.nextFloat() * 6 - 2;
            float particleX = x + random.nextFloat() * width;
            Color iceColor = new Color(200, 255, 255, 200);
            addParticle(new Particle(particleX, y, vx, vy, iceColor, 2.0f, 3.0f, ParticleType.SPARK));
        }
    }
    
    // Create trail effect for player when moving fast
    public void createTrailEffect(float x, float y, float velocityX, float velocityY) {
        if (Math.abs(velocityX) > 3 || Math.abs(velocityY) > 5) {
            float vx = -velocityX * 0.3f + (random.nextFloat() - 0.5f) * 2;
            float vy = -velocityY * 0.2f + (random.nextFloat() - 0.5f) * 2;
            Color trailColor = new Color(255, 150, 150, 100);
            addParticle(new Particle(x + 15, y + 20, vx, vy, trailColor, 0.8f, 2.0f, ParticleType.NORMAL));
        }
    }
    
    // Clean up particles that are too far away
    public void cleanupParticles(float playerY) {
        particles.removeIf(particle -> particle.getY() > playerY + 800);
    }
    
    // Get particle count for debugging
    public int getParticleCount() {
        return particles.size();
    }
    
    // Clear all particles
    public void clear() {
        particles.clear();
    }
}
