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
import com.icytower.systems.ParticleSystem;


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

    float minGap = 50;
    float maxGap = 70;
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
        if (!(obj instanceof Platform)) continue;
        Platform platform = (Platform) obj;

        // Only check collision if player is falling (velocityY >= 0)
        if (player.intersects(platform) && player.getVelocityY() >= 0) {

            // Check if player is above the platform
            boolean landedOnTop = player.getY() + player.getHeight() <= platform.getY() + 10;

            if (landedOnTop) {
                // Player lands on the platform
                player.landOn(platform.getY());
               

                // Handle platform type
                switch (platform.getType()) {
                    case ICE:
                        player.setMovementStrategy(new IceMovementStrategy());
                        break;

                    case BREAKABLE:
                        player.setMovementStrategy(new DefaultMovementStrategy());
                        platform.setPlayerSteppedOn(true);
                        break;

                    default:
                        player.setMovementStrategy(new DefaultMovementStrategy());
                        break;
                }
            }
        } else {
            // Player is no longer touching the platform, handle BREAKABLE case
            if (platform.getType() == PlatformType.BREAKABLE &&
                platform.hasPlayerSteppedOn() && !platform.isBroken()) {

                platform.breakPlatform();
               
                ParticleSystem.getInstance()
                    .createIceBreakEffect(platform.getX(), platform.getY(), platform.getWidth());
            }
        }
    }

    // Remove broken platforms from the game
    gameObjects.removeIf(obj -> obj instanceof Platform && ((Platform) obj).isBroken());
}

        private void generateNewPlatforms() {
        if (gameObjects.size() < 100) {
            int highestY = gameObjects.stream()
                .filter(obj -> obj instanceof Platform)
                .mapToInt(obj -> (int)obj.getY())
                .min()
                .orElse(0);

            float minGap = 50;
            float maxGap = 70;
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
  gameObjects.removeIf(obj -> (obj instanceof Platform) && ((Platform)obj).isBroken());
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
