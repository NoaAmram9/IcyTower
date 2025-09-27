// Animation Manager using Singleton Pattern
package com.icytower.systems;
import com.icytower.animations.Animation;
import com.icytower.animations.IdleAnimation;
import com.icytower.animations.RunningAnimation;
import com.icytower.animations.JumpingAnimation;
import java.util.HashMap;
import java.util.Map;


public class AnimationManager {
    private static AnimationManager instance;
    private Map<String, Animation> animations;
    
    private AnimationManager() {
        animations = new HashMap<>();
        initializeAnimations();
    }
    
    public static AnimationManager getInstance() {
        if (instance == null) {
            instance = new AnimationManager();
        }
        return instance;
    }
    
    private void initializeAnimations() {
        animations.put("player_idle", new IdleAnimation());
        animations.put("player_running", new RunningAnimation());
        animations.put("player_jumping", new JumpingAnimation());
    }
    
    public Animation getAnimation(String name) {
        return animations.get(name);
    }
}