// Renderer Class for 2D Graphics
package com.icytower.graphics;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

public class Renderer {
    private Graphics2D g2d;
    private Image backgroundImage;

    public Renderer(Graphics2D g2d) {
        this.g2d = g2d;
        
        backgroundImage = Toolkit.getDefaultToolkit().getImage("resources/images/background.png");
    }
  
    public void setColor(Color color) {
        g2d.setColor(color);
    }

    public void fillRect(int x, int y, int w, int h) {
        g2d.fillRect(x, y, w, h);
    }

    public void fillOval(int x, int y, int w, int h) {
        g2d.fillOval(x, y, w, h);
    }

    public void drawString(String text, int x, int y) {
        g2d.drawString(text, x, y);
    }
    public void drawImage(BufferedImage img, int x, int y, int w, int h) {
    g2d.drawImage(img, x, y, w, h, null);
    }

    /**
     * @param cameraX 
     * @param cameraY 
     * @param width 
     * @param height 
     */
    public void drawBackground(float cameraX, float cameraY, int width, int height) {
        if (backgroundImage != null) {
   
            int imgWidth = backgroundImage.getWidth(null);
            int imgHeight = backgroundImage.getHeight(null);

            if (imgWidth <= 0 || imgHeight <= 0) {
                GradientPaint skyGradient = new GradientPaint(
            0, 0, new Color(135, 206, 235),
            0, height, new Color(176, 224, 230)
        );
        g2d.setPaint(skyGradient);
        g2d.fillRect(0, 0, width, height);
                return;
            }

            int offsetX = (int) (-cameraX % imgWidth);
            int offsetY = (int) (-cameraY % imgHeight);

            for (int y = offsetY - imgHeight; y < height; y += imgHeight) {
                for (int x = offsetX - imgWidth; x < width; x += imgWidth) {
                    g2d.drawImage(backgroundImage, x, y, imgWidth, imgHeight, null);
                }
            }
        } else {
           
            GradientPaint skyGradient = new GradientPaint(
            0, 0, new Color(135, 206, 235),
            0, height, new Color(176, 224, 230)
        );
        g2d.setPaint(skyGradient);
        g2d.fillRect(0, 0, width, height);
        }
    }

  
    public void setFont(Font font) { g2d.setFont(font); }
  
    public void fillRoundRect(int x, int y, int w, int h, int arcW, int arcH) { g2d.fillRoundRect(x, y, w, h, arcW, arcH); }
    public void drawArc(int x, int y, int w, int h, int startAngle, int arcAngle) { g2d.drawArc(x, y, w, h, startAngle, arcAngle); }
    public void translate(float dx, float dy) { g2d.translate(dx, dy); }
}
