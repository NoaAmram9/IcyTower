// Platform Class
package com.icytower.entities;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.BasicStroke;
import com.icytower.core.GameObject;
import com.icytower.graphics.Renderer;
import com.icytower.enums.PlatformType;
import com.icytower.systems.ParticleSystem;
public class Platform extends GameObject {
    private PlatformType type;
     private boolean broken = false;
private boolean playerHasSteppedOn = false;




    public Platform(float x, float y, float width, float height, PlatformType type) {
        super(x, y, width, height);
        this.type = type;
        this.color = type.getColor();
    }
    
   
        @Override
        public void update(float deltaTime) {
           if (broken) return;
        }

        public void breakPlatform() {
            if (!broken) {
                broken = true;
                ParticleSystem.getInstance().createIceBreakEffect(x, y, width);
            }
        }

        public boolean isBroken() {
            return broken;
        }

// Platform.java
public boolean hasPlayerSteppedOn() {
    return playerHasSteppedOn;
}

public void setPlayerSteppedOn(boolean steppedOn) {
    this.playerHasSteppedOn = steppedOn;
}

 @Override
public void render(Renderer renderer) {
    if (broken) return;

    Graphics2D g2d = renderer.getGraphics2D();

    g2d.setColor(new Color(0, 0, 0, 60));
    g2d.fillRoundRect((int)x + 4, (int)y + 4, (int)width, (int)height, 12, 12);

    switch (type) {
        case NORMAL -> {
      
            GradientPaint normalGradient = new GradientPaint(
                    (int)x, (int)y, new Color(200, 220, 255, 200),
                    (int)x, (int)(y + height), new Color(140, 170, 220, 200)
            );
            g2d.setPaint(normalGradient);
            g2d.fillRoundRect((int)x, (int)y, (int)width, (int)height, 12, 12);

            g2d.setColor(new Color(255, 255, 255, 120));
            g2d.fillRoundRect((int)x + 2, (int)y + 2, (int)width - 4, (int)(height / 3), 10, 10);
        }

        case ICE -> {
          
            GradientPaint iceGradient = new GradientPaint(
                    (int)x, (int)y, new Color(180, 255, 255, 200),
                    (int)x, (int)(y + height), new Color(100, 200, 220, 200)
            );
            g2d.setPaint(iceGradient);
            g2d.fillRoundRect((int)x, (int)y, (int)width, (int)height, 12, 12);

          
            g2d.setColor(new Color(255, 255, 255, 150));
            g2d.fillRoundRect((int)x + 2, (int)y + 2, (int)width - 4, (int)(height / 3), 10, 10);

            
            g2d.setColor(new Color(255, 255, 255, 80));
            g2d.setStroke(new BasicStroke(1f));
            for (int i = 0; i < width; i += 14) {
                g2d.drawLine((int)x + i, (int)y, (int)x + i - 6, (int)(y + height));
            }
        }

        case BREAKABLE -> {
           
            GradientPaint breakGradient = new GradientPaint(
                    (int)x, (int)y, new Color(255, 200, 200, 200),
                    (int)x, (int)(y + height), new Color(200, 100, 100, 200)
            );
            g2d.setPaint(breakGradient);
            g2d.fillRoundRect((int)x, (int)y, (int)width, (int)height, 12, 12);

        
            g2d.setColor(new Color(255, 255, 255, 80));
            g2d.fillRoundRect((int)x + 2, (int)y + 2, (int)width - 4, (int)(height / 4), 10, 10);

          
            g2d.setColor(new Color(50, 0, 0, 120));
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawLine((int)(x + 10), (int)(y + 5), (int)(x + width - 15), (int)(y + height - 5));
            g2d.drawLine((int)(x + width / 3), (int)y, (int)(x + width / 2), (int)(y + height));
        }
    }

  
    g2d.setStroke(new BasicStroke(1.5f));
    g2d.setColor(new Color(255, 255, 255, 80));
    g2d.drawRoundRect((int)x, (int)y, (int)width, (int)height, 12, 12);
}

    @Override
    public Rectangle2D.Float getBounds() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    
    public PlatformType getType() { return type; }
    
}