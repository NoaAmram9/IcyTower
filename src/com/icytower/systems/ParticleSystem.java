// Particle System using Singleton Pattern
package com.icytower.systems;
import com.icytower.entities.Particle;
import com.icytower.graphics.Renderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ParticleSystem {
    private static ParticleSystem instance;
    private List<Particle> particles;
    
    private ParticleSystem() {
        particles = new ArrayList<>();
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
    
    public void render(Renderer renderer) {
        for (Particle particle : particles) {
            particle.render(renderer);
        }
    }
    
    public void createLandingEffect(float x, float y) {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            float vx = (rand.nextFloat() - 0.5f) * 4;
            float vy = -rand.nextFloat() * 3;
            addParticle(new Particle(x, y, vx, vy, Color.WHITE, 1.0f));
        }
    }
}
