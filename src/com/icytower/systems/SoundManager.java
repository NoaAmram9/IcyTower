// Sound Manager using Singleton Pattern
package com.icytower.systems;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private Map<String, String> sounds = new HashMap<>();
    
    private SoundManager() {
        // Initialize sound mappings
        sounds.put("jump", "jump.wav");
        sounds.put("land", "land.wav");
        sounds.put("gameOver", "gameOver.wav");
    }
    
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    public void playSound(String soundName) {
        // Placeholder for sound playing logic
        System.out.println("Playing sound: " + soundName);
    }
}