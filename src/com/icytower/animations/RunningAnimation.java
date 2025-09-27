package com.icytower.animations;
import com.icytower.graphics.Renderer;
import java.awt.Color;

public class RunningAnimation extends Animation {
    public RunningAnimation() {
        super(6, 8.0f);
    }
    
    @Override
    public void render(Renderer renderer, float x, float y, float width, float height) {
        // Simple running animation with slight bounce
        float bounceOffset = (float) Math.sin(currentFrame * 2) * 2;
        renderer.setColor(new Color(255, 100, 100));
        renderer.fillRoundRect((int)x, (int)y + (int)bounceOffset, (int)width, (int)height, 10, 10);
    }
}