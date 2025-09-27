package com.icytower.animations;
import com.icytower.graphics.Renderer;

public abstract class Animation {
    protected float currentFrame;
    protected float frameSpeed;
    protected int totalFrames;
    
    public Animation(int totalFrames, float frameSpeed) {
        this.totalFrames = totalFrames;
        this.frameSpeed = frameSpeed;
        this.currentFrame = 0;
    }
    
    public void update(float deltaTime) {
        currentFrame += frameSpeed * deltaTime;
        if (currentFrame >= totalFrames) {
            currentFrame = 0;
        }
    }
    
    public abstract void render(Renderer renderer, float x, float y, float width, float height);
    
    protected int getCurrentFrameIndex() {
        return (int) currentFrame;
    }
}