package com.icytower.animations;
import com.icytower.graphics.Renderer;
import java.awt.Color;

public class IdleAnimation extends Animation {
    public IdleAnimation() {
        super(4, 2.0f);
    }
    
    @Override
    public void render(Renderer renderer, float x, float y, float width, float height) {
        // Simple breathing animation
        float scale = 1.0f + (float) Math.sin(currentFrame) * 0.05f;
        renderer.setColor(new Color(255, 100, 100));
        int scaledWidth = (int)(width * scale);
        int scaledHeight = (int)(height * scale);
        int offsetX = (int)(width - scaledWidth) / 2;
        int offsetY = (int)(height - scaledHeight) / 2;
        renderer.fillRoundRect((int)x + offsetX, (int)y + offsetY, scaledWidth, scaledHeight, 10, 10);
    }
}
