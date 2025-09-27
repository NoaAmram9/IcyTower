// Platform Factory
package com.icytower.factories;
import com.icytower.core.GameObject;
import com.icytower.entities.Platform;
import com.icytower.entities.PlatformType;
import java.util.Random;
import com.icytower.core.GameObjectFactory;

public class PlatformFactory implements GameObjectFactory {
    private Random random = new Random();
    
    @Override
    public GameObject create(float x, float y) {
        float width = 120 + random.nextInt(180);
        PlatformType type = getRandomPlatformType();
        return new Platform(x, y, width, 15, type);
    }
    
    private PlatformType getRandomPlatformType() {
        int rand = random.nextInt(100);
        if (rand < 70) return PlatformType.NORMAL;
        if (rand < 85) return PlatformType.ICE;
        return PlatformType.BREAKABLE;
    }
}
