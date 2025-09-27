// Renderer Class for 2D Graphics
package com.icytower.graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

public class Renderer {
    private Graphics2D g2d;
    
    public Renderer(Graphics2D g2d) {
        this.g2d = g2d;
        this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
    public void setColor(Color color) {
        g2d.setColor(color);
    }
    
    public void fillRect(int x, int y, int width, int height) {
        g2d.fillRect(x, y, width, height);
    }
    
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }
    
    public void fillOval(int x, int y, int width, int height) {
        g2d.fillOval(x, y, width, height);
    }
    
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        g2d.drawArc(x, y, width, height, startAngle, arcAngle);
    }
    
    public void drawString(String text, int x, int y) {
        g2d.drawString(text, x, y);
    }
    
    public void setFont(Font font) {
        g2d.setFont(font);
    }
    
    public void drawGradientBackground(int width, int height) {
        GradientPaint skyGradient = new GradientPaint(
            0, 0, new Color(135, 206, 235),
            0, height, new Color(176, 224, 230)
        );
        g2d.setPaint(skyGradient);
        g2d.fillRect(0, 0, width, height);
    }
    
    public void translate(float x, float y) {
        g2d.translate(x, y);
    }
    
    public Graphics2D getGraphics() {
        return g2d;
    }
}