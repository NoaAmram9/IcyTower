package com.icytower.systems;

import javax.sound.sampled.*;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundManager {
    
    private static SoundManager instance;
    private final Map<String, String> sounds = new HashMap<>();
    private final Map<String, Clip> preloadedClips = new HashMap<>();
    private final ExecutorService soundExecutor = Executors.newFixedThreadPool(5);
    private boolean soundEnabled = true;
    
    private SoundManager() {
        sounds.put("jump", "jump.wav");
        sounds.put("land", "land.wav");
        sounds.put("gameOver", "gameOver.wav");
        
        System.out.println("=== Sound Manager Initialization ===");
        preloadSounds();
        
        // If no sounds are loaded, create silent fallback
        if (preloadedClips.isEmpty()) {
            System.out.println("No sounds loaded - creating silent fallback");
            createSilentFallback();
        }
    }
    
    public static SoundManager getInstance() {
        if (instance == null) instance = new SoundManager();
        return instance;
    }
    
    private void preloadSounds() {
        for (Map.Entry<String, String> entry : sounds.entrySet()) {
            String soundName = entry.getKey();
            String fileName = entry.getValue();
            
            System.out.println("Attempting to load: " + soundName + " (" + fileName + ")");
            
            // Try to load the file
            Clip clip = loadSoundFile(fileName);
            if (clip != null) {
                preloadedClips.put(soundName, clip);
                System.out.println("Successfully loaded: " + soundName);
            } else {
                System.out.println("Failed to load: " + soundName);
                // Create a silent clip as fallback
                Clip silentClip = createSilentClip();
                if (silentClip != null) {
                    preloadedClips.put(soundName, silentClip);
                    System.out.println("Created silent fallback for: " + soundName);
                }
            }
        }
        
        System.out.println("Sound loading complete: " + preloadedClips.size() + "/" + sounds.size() + " sounds ready");
    }
    
    private Clip loadSoundFile(String fileName) {
        // List of paths to try
        String[] paths = {
            "resources/sounds/" + fileName,
            "src/resources/sounds/" + fileName,
            "sounds/" + fileName,
            fileName
        };
        
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                System.out.println("Found file at: " + file.getAbsolutePath());
                
                // Check if it is a valid WAV file
                if (isValidWavFile(file)) {
                    try {
                        AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
                        
                        // Check if the format is supported
                        AudioFormat format = audioInput.getFormat();
                        System.out.println("Audio format: " + format);
                        
                        // If format is not supported, try to convert
                        if (!AudioSystem.isConversionSupported(AudioFormat.Encoding.PCM_SIGNED, format)) {
                            audioInput = convertToPCM(audioInput);
                        }
                        
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInput);
                        return clip;
                        
                    } catch (Exception e) {
                        System.out.println("Error loading " + path + ": " + e.getMessage());
                        // Try next path
                    }
                } else {
                    System.out.println("File is not a valid WAV: " + path);
                }
            }
        }
        
        return null;
    }
    
    private boolean isValidWavFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] header = new byte[12];
            if (fis.read(header) != 12) return false;
            
            // Check WAV header
            String riff = new String(header, 0, 4);
            String wave = new String(header, 8, 4);
            
            return "RIFF".equals(riff) && "WAVE".equals(wave);
            
        } catch (Exception e) {
            return false;
        }
    }
    
    private AudioInputStream convertToPCM(AudioInputStream audioInput) {
        AudioFormat sourceFormat = audioInput.getFormat();
        AudioFormat targetFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            sourceFormat.getSampleRate(),
            16,
            sourceFormat.getChannels(),
            sourceFormat.getChannels() * 2,
            sourceFormat.getSampleRate(),
            false
        );
        
        return AudioSystem.getAudioInputStream(targetFormat, audioInput);
    }
    
    private Clip createSilentClip() {
        try {
            // Create a silent WAV of 0.1 seconds
            AudioFormat format = new AudioFormat(22050, 16, 1, true, false);
            int frames = (int)(format.getFrameRate() * 0.1); // 0.1 seconds
            byte[] silence = new byte[frames * format.getFrameSize()];
            
            ByteArrayInputStream bais = new ByteArrayInputStream(silence);
            AudioInputStream audioInput = new AudioInputStream(bais, format, frames);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            return clip;
            
        } catch (Exception e) {
            System.out.println("Could not create silent clip: " + e.getMessage());
            return null;
        }
    }
    
    private void createSilentFallback() {
        for (String soundName : sounds.keySet()) {
            if (!preloadedClips.containsKey(soundName)) {
                Clip silentClip = createSilentClip();
                if (silentClip != null) {
                    preloadedClips.put(soundName, silentClip);
                }
            }
        }
    }
    
    public void playSound(String soundName) {
        if (!soundEnabled) return;
        
        soundExecutor.submit(() -> {
            Clip clip = preloadedClips.get(soundName);
            if (clip == null) {
                System.out.println("Sound not found: " + soundName);
                return;
            }
            
            try {
                if (clip.isRunning()) {
                    clip.stop();
                }
                
                clip.setFramePosition(0);
                clip.start();
                
            } catch (Exception e) {
                System.out.println("Error playing sound " + soundName + ": " + e.getMessage());
            }
        });
    }
    
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        System.out.println("Sound " + (enabled ? "enabled" : "disabled"));
    }
    
    public void stopAllSounds() {
        for (Clip clip : preloadedClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
    
    public void cleanup() {
        soundExecutor.shutdown();
        for (Clip clip : preloadedClips.values()) {
            clip.close();
        }
        preloadedClips.clear();
    }
    
    public void debugSoundStatus() {
        System.out.println("=== Sound Manager Status ===");
        System.out.println("Sound enabled: " + soundEnabled);
        System.out.println("Available sounds: " + sounds.keySet());
        System.out.println("Loaded clips: " + preloadedClips.keySet());
        
        for (String soundName : sounds.keySet()) {
            boolean isLoaded = preloadedClips.containsKey(soundName);
            System.out.println(soundName + ": " + (isLoaded ? "Ready" : "Missing"));
        }
        System.out.println("============================");
    }
}