// Performance Monitor - Singleton
package com.icytower.utils;

public class PerformanceMonitor {
    private static PerformanceMonitor instance;
    private long lastTime;
    private int frameCount;
    private float fps;
    
    private PerformanceMonitor() {
        lastTime = System.nanoTime();
        frameCount = 0;
        fps = 0;
    }
    
    public static PerformanceMonitor getInstance() {
        if (instance == null) {
            instance = new PerformanceMonitor();
        }
        return instance;
    }
    
    public void update() {
        frameCount++;
        long currentTime = System.nanoTime();
        
        if (currentTime - lastTime >= 1_000_000_000) { // 1 second
            fps = frameCount;
            frameCount = 0;
            lastTime = currentTime;
        }
    }
    
    public float getFPS() {
        return fps;
    }
}