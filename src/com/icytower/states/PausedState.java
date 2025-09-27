package com.icytower.states;

import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.core.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.BasicStroke;
import com.icytower.interfaces.GameState;

public class PausedState implements GameState {
    private float animationTime = 0;
    private float overlayOpacity = 0;
    private float pauseIconScale = 1.0f;
    private boolean isEntering = true;
    private int pulseTimer = 0;
    private boolean textPulse = true;
    
    @Override
    public void enter() {
        System.out.println("Game Paused");
        animationTime = 0;
        overlayOpacity = 0;
        isEntering = true;
    }
    
    @Override
    public void exit() {
        System.out.println("Game Resumed");
    }
    
    @Override
    public void update() {
        animationTime += 0.03f;
        
        // Smooth entrance animation
        if (isEntering) {
            overlayOpacity = Math.min(overlayOpacity + 0.05f, 0.85f);
            if (overlayOpacity >= 0.85f) {
                isEntering = false;
            }
        }
        
        // Pause icon breathing effect
        pauseIconScale = 1.0f + 0.15f * (float)Math.sin(animationTime * 2);
        
        // Text pulsing
        pulseTimer++;
        if (pulseTimer >= 40) {
            textPulse = !textPulse;
            pulseTimer = 0;
        }
        
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            GameManager.getInstance().setState("PLAYING");
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        Graphics2D g2d = renderer.getGraphics2D();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Animated overlay background with blur effect
        drawAnimatedOverlay(g2d);
        
        // Main pause panel
        drawPausePanel(g2d);
        
        // Pause icon
        drawPauseIcon(g2d);
        
        // Title and instructions
        drawPauseText(g2d);
        
        // Animated resume prompt
        if (textPulse) {
            drawResumePrompt(g2d);
        }
        
        // Decorative ice crystals
        drawIceCrystals(g2d);
    }
    
    private void drawAnimatedOverlay(Graphics2D g2d) {
        // Create a sophisticated overlay with gradient and animation
        GradientPaint overlayGradient = new GradientPaint(
            0, 0, new Color(0, 20, 40, (int)(200 * overlayOpacity)),
            800, 600, new Color(10, 30, 60, (int)(150 * overlayOpacity))
        );
        g2d.setPaint(overlayGradient);
        g2d.fillRect(0, 0, 800, 600);
        
        // Add animated frost pattern
        g2d.setColor(new Color(100, 150, 200, (int)(30 * overlayOpacity)));
        for (int i = 0; i < 8; i++) {
            float angle = animationTime * 10 + i * 45;
            float radius = 150 + 50 * (float)Math.sin(animationTime + i);
            float x = 400 + radius * (float)Math.cos(Math.toRadians(angle));
            float y = 300 + radius * (float)Math.sin(Math.toRadians(angle));
            
            g2d.fillOval((int)x - 2, (int)y - 2, 4, 4);
        }
    }
    
    private void drawPausePanel(Graphics2D g2d) {
        // Glass-like main panel
        RoundRectangle2D panel = new RoundRectangle2D.Float(200, 150, 400, 300, 25, 25);
        
        // Panel background with gradient
        GradientPaint panelGradient = new GradientPaint(
            200, 150, new Color(40, 80, 140, (int)(180 * overlayOpacity)),
            200, 450, new Color(20, 40, 80, (int)(200 * overlayOpacity))
        );
        g2d.setPaint(panelGradient);
        g2d.fill(panel);
        
        // Panel glow border
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setColor(new Color(100, 180, 255, (int)(150 * overlayOpacity)));
        g2d.draw(panel);
        
        // Inner highlight
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.setColor(new Color(150, 200, 255, (int)(80 * overlayOpacity)));
        RoundRectangle2D innerPanel = new RoundRectangle2D.Float(205, 155, 390, 290, 20, 20);
        g2d.draw(innerPanel);
    }
    
    private void drawPauseIcon(Graphics2D g2d) {
        int centerX = 400;
        int centerY = 220;
        int iconSize = (int)(40 * pauseIconScale * overlayOpacity);
        
        // Pause bars with glow
        for (int i = 0; i < 3; i++) {
            int alpha = 255 - (i * 60);
            g2d.setColor(new Color(150, 220, 255, alpha));
            
            // Left bar
            g2d.fillRoundRect(centerX - 25 - i, centerY - iconSize/2 - i, 
                             15 + i*2, iconSize + i*2, 8, 8);
            // Right bar  
            g2d.fillRoundRect(centerX + 10 - i, centerY - iconSize/2 - i, 
                             15 + i*2, iconSize + i*2, 8, 8);
        }
        
        // Core pause bars
        g2d.setColor(new Color(200, 240, 255, (int)(255 * overlayOpacity)));
        g2d.fillRoundRect(centerX - 25, centerY - iconSize/2, 15, iconSize, 8, 8);
        g2d.fillRoundRect(centerX + 10, centerY - iconSize/2, 15, iconSize, 8, 8);
    }
    
    private void drawPauseText(Graphics2D g2d) {
        // Main "PAUSED" title
        String pausedText = "GAME PAUSED";
        g2d.setFont(new Font("Segoe UI Semibold Black", Font.BOLD, 42));
        int textWidth = g2d.getFontMetrics().stringWidth(pausedText);
        int textX = (800 - textWidth) / 2;
        int textY = 320;
        
        // Text glow effect
        for (int i = 4; i >= 0; i--) {
            int alpha = (int)((60 - i * 10) * overlayOpacity);
            g2d.setColor(new Color(100, 200, 255, alpha));
            g2d.drawString(pausedText, textX - i, textY - i);
        }
        
        // Main text
        GradientPaint textGradient = new GradientPaint(
            textX, textY - 20, new Color(220, 240, 255, (int)(255 * overlayOpacity)),
            textX, textY + 20, new Color(150, 200, 240, (int)(255 * overlayOpacity))
        );
        g2d.setPaint(textGradient);
        g2d.drawString(pausedText, textX, textY);
        
        // Subtitle
        g2d.setColor(new Color(180, 220, 255, (int)(200 * overlayOpacity)));
        g2d.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 18));
        String subtitle = "Take a moment to catch your breath";
        int subtitleWidth = g2d.getFontMetrics().stringWidth(subtitle);
        g2d.drawString(subtitle, (800 - subtitleWidth) / 2, 350);
    }
    
    private void drawResumePrompt(Graphics2D g2d) {
        // Animated resume instruction
        String resumeText = "Press ESC to Resume";
        g2d.setFont(new Font("Segoe UI Semibold", Font.BOLD, 22));
        int promptWidth = g2d.getFontMetrics().stringWidth(resumeText);
        int promptX = (800 - promptWidth) / 2;
        int promptY = 390;

        // Glowing background for prompt
        RoundRectangle2D promptBg = new RoundRectangle2D.Float(
            promptX - 15, promptY - 20, promptWidth + 30, 30, 15, 15
        );

        float glowIntensity = 0.6f + 0.4f * (float)Math.sin(animationTime * 3);
        Color promptGlow = new Color(80, 160, 240, (int)(120 * glowIntensity * overlayOpacity));
        g2d.setColor(promptGlow);
        g2d.fill(promptBg);

        // Prompt text
        Color promptColor = new Color(255, 255, 255, (int)((200 + 55 * glowIntensity) * overlayOpacity));
        g2d.setColor(promptColor);
        g2d.drawString(resumeText, promptX, promptY);
    }
    
    private void drawIceCrystals(Graphics2D g2d) {
        // Decorative floating ice crystals
        g2d.setStroke(new BasicStroke(2.0f));
        
        for (int i = 0; i < 6; i++) {
            float angle = animationTime * 30 + i * 60;
            float x = 400 + 180 * (float)Math.cos(Math.toRadians(angle));
            float y = 300 + 120 * (float)Math.sin(Math.toRadians(angle));
            
            g2d.setColor(new Color(150, 200, 255, (int)(80 * overlayOpacity)));
            
            // Draw simple crystal shape
            int[] xPoints = {
                (int)x, (int)(x + 8), (int)x, (int)(x - 8)
            };
            int[] yPoints = {
                (int)(y - 10), (int)y, (int)(y + 10), (int)y
            };
            
            g2d.drawPolygon(xPoints, yPoints, 4);
            g2d.drawLine((int)(x - 6), (int)y, (int)(x + 6), (int)y);
        }
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Handled in update
    }
}