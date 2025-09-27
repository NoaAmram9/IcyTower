package com.icytower.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import com.icytower.core.GameObject;
import com.icytower.core.Player;
import com.icytower.platforms.Platform;
import com.icytower.platforms.PlatformType;
import com.icytower.platforms.PlatformFactory;
import com.icytower.strategies.DefaultMovementStrategy;
import com.icytower.strategies.IceMovementStrategy;
import com.icytower.graphics.Renderer;
import com.icytower.sound.SoundManager;

public class World {
    private List<GameObject> gameObjects;
    private PlatformFactory platformFactory;
    private Random random;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    public World() {
        gameObjects = new ArrayList<>();
        platformFactory = new PlatformFactory();
        random = new Random();
        generateInitialPlatforms();
    }
    
    private void generateInitialPlatforms() {
        // Ground platform
        gameObjects.add(new Platform(0, WINDOW_HEIGHT - 20, WINDOW_WIDTH, 20, PlatformType.NORMAL));
        
        // Generate platforms going up
        for (int i = 1; i <= 50; i++) {
            float y = WINDOW_HEIGHT - 100 - (i * 80);
            float x = random.nextInt(WINDOW_WIDTH - 200);
            GameObject platform = platformFactory.create(x, y);
            gameObjects.add(platform);
        }
    }
    
    public void update(Player player) {
        // Update all game objects
        for (GameObject obj : gameObjects) {
            obj.update(1.0f);
        }
        
        // Check collisions
        checkCollisions(player);
        
        // Generate new platforms if needed
        generateNewPlatforms();
        
        // Remove old platforms
        removeOldPlatforms(player);
    }
    
    private void checkCollisions(Player player) {
        player.setOnGround(false);
        
        for (GameObject obj : gameObjects) {
            if (obj instanceof Platform && player.intersects(obj) && player.getVelocityY() >= 0) {
                Platform platform = (Platform) obj;
                if (player.getY() + player.getHeight() - 10 < platform.getY()) {
                    player.landOn(platform.getY());
                    player.setOnGround(true);
                    
                    // Apply platform-specific effects
                    if (platform.getType() == PlatformType.ICE) {
                        player.setMovementStrategy(new IceMovementStrategy());
                    } else {
                        player.setMovementStrategy(new DefaultMovementStrategy());
                    }
                    
                    SoundManager.getInstance().playSound("land");
                    break;
                }
            }
        }
    }
    
    private void generateNewPlatforms() {
        if (gameObjects.size() < 100) {
            int highestY = gameObjects.stream()
                .filter(obj -> obj instanceof Platform)
                .mapToInt(obj -> (int)obj.getY())
                .min()
                .orElse(0);
            
            for (int i = 0; i < 10; i++) {
                float y = highestY - 80 - (i * 80);
                float x = random.nextInt(WINDOW_WIDTH - 200);
                GameObject platform = platformFactory.create(x, y);
                gameObjects.add(platform);
            }
        }
    }
    
    private void removeOldPlatforms(Player player) {
        gameObjects.removeIf(obj -> obj.getY() > player.getY() + 700);
    }
    
    public void render(Renderer renderer) {
        for (GameObject obj : gameObjects) {
            if (obj.isActive()) {
                obj.render(renderer);
            }
        }
    }
    
    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }
}