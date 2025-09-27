package com.icytower.animations;
import com.icytower.graphics.Renderer;
import java.awt.Color;

public class JumpingAnimation extends Animation {
    public JumpingAnimation() {
        super(1, 1.0f);
    }
    
    @Override
    public void render(Renderer renderer, float x, float y, float width, float height) {
        // Stretched animation for jumping
        renderer.setColor(new Color(255, 120, 120));
        renderer.fillRoundRect((int)x, (int)y, (int)width, (int)(height * 1.2f), 10, 10);
    }
}