package com.icytower.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.icytower.core.GameObject;
import com.icytower.entities.Player;
import com.icytower.entities.Platform;
import com.icytower.enums.PlatformType;
import com.icytower.factories.PlatformFactory;
import com.icytower.strategies.DefaultMovementStrategy;
import com.icytower.strategies.IceMovementStrategy;
import com.icytower.graphics.Renderer;
import com.icytower.systems.SoundManager;



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
    gameObjects.add(new Platform(0, WINDOW_HEIGHT - 20, WINDOW_WIDTH, 20, PlatformType.NORMAL));

    float minGap = 60;
    float maxGap = 80;
    int yPos = WINDOW_HEIGHT - 100;

    for (int i = 1; i <= 50; i++) {
        float gap = minGap + random.nextFloat(maxGap - minGap + 1);
        yPos -= gap;
        float x = random.nextInt(WINDOW_WIDTH - 200);
        GameObject platform = platformFactory.create(x, yPos);
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
            
                if (player.getY() + player.getHeight() <= platform.getY() + 20) { 
                    player.landOn(platform.getY());
                    
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

            float minGap = 60;
            float maxGap = 80;
            int yPos = highestY;

            for (int i = 0; i < 10; i++) {
                float gap = minGap + random.nextFloat(maxGap - minGap + 1);
                yPos -= gap;
                float x = random.nextInt(WINDOW_WIDTH - 200);
                GameObject platform = platformFactory.create(x, yPos);
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
