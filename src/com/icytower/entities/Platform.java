// Platform Class
package com.icytower.entities;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import com.icytower.core.GameObject;
import com.icytower.graphics.Renderer;
import com.icytower.enums.PlatformType;

public class Platform extends GameObject {
    private PlatformType type;
    
    public Platform(float x, float y, float width, float height, PlatformType type) {
        super(x, y, width, height);
        this.type = type;
        this.color = type.getColor();
    }
    
    @Override
    public void update(float deltaTime) {
        // Platforms are static, no update needed
    }
    
    @Override
    public void render(Renderer renderer) {
        // Platform shadow
        renderer.setColor(new Color(150, 150, 200));
        renderer.fillRoundRect((int)x + 2, (int)y + 2, (int)width, (int)height, 8, 8);
        
        // Platform
        renderer.setColor(color);
        renderer.fillRoundRect((int)x, (int)y, (int)width, (int)height, 8, 8);
        
        // Platform highlight
        renderer.setColor(type.getHighlightColor());
        renderer.fillRoundRect((int)x, (int)y, (int)width, 3, 8, 8);
    }
    
    @Override
    public Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    
    public PlatformType getType() { return type; }
}