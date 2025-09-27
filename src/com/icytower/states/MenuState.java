 package com.icytower.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.core.GameManager;
import com.icytower.interfaces.GameState;

public class MenuState implements GameState {
    private float animationTime = 0;
    private float titlePulse = 1.0f;
    private float starRotation = 0;
    private boolean instructionBlink = true;
    private int blinkTimer = 0;
    
    @Override
    public void enter() {
        System.out.println("Entering Menu State");
        InputManager.getInstance().clearBindings();
        animationTime = 0;
    }
        
    @Override
    public void exit() {
        System.out.println("Exiting Menu State");
    }
    
    @Override
    public void update() {
        // Update animations
        animationTime += 0.02f;
        titlePulse = 1.0f + 0.1f * (float)Math.sin(animationTime * 3);
        starRotation += 1.5f;
        
        blinkTimer++;
        if (blinkTimer >= 30) {
            instructionBlink = !instructionBlink;
            blinkTimer = 0;
        }
        
        // Menu logic
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ENTER)) {
            GameManager.getInstance().setState("PLAYING");
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        
       Graphics2D g2d = renderer.getGraphics2D();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Gradient background
        GradientPaint bgGradient = new GradientPaint(
            0, 0, new Color(15, 30, 60),
            0, 600, new Color(5, 15, 35)
        );
        g2d.setPaint(bgGradient);
        g2d.fillRect(0, 0, 800, 600);
        
        // Animated stars background
        drawStars(g2d);
        
        // Ice/snow effect particles
        drawSnowParticles(g2d);
        
        // Title with glow effect
        drawGlowingTitle(g2d);
        
        // Subtitle with ice theme
        g2d.setColor(new Color(180, 220, 255, 200));
        g2d.setFont(new Font("Segoe UI Semibold", Font.ITALIC, 18));
        String subtitle = "- Climb Higher Than Ever -";
        int subtitleWidth = g2d.getFontMetrics().stringWidth(subtitle);
        // g2d.drawString(subtitle, (800 - subtitleWidth) / 2, 260);
        
        // Modern instruction panel
        drawInstructionPanel(g2d);
        
        // Animated "Press ENTER" prompt
        if (instructionBlink) {
            drawPressEnterPrompt(g2d);
        }
        
        // Footer
        g2d.setColor(new Color(100, 150, 200, 100));
        g2d.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        g2d.drawString("Create By Noa Amram", 20, 580);
    }
    
    private void drawStars(Graphics2D g2d) {
        g2d.setColor(new Color(255, 255, 255, 80));
        for (int i = 0; i < 50; i++) {
            float x = (i * 137) % 800;
            float y = (i * 219) % 600;
            float size = 1 + (i % 3);
            float rotation = starRotation + i * 10;
            
            g2d.translate(x, y);
            g2d.rotate(Math.toRadians(rotation));
            g2d.fillOval(-1, -1, (int)size, (int)size);
            g2d.rotate(-Math.toRadians(rotation));
            g2d.translate(-x, -y);
        }
    }
    
    private void drawSnowParticles(Graphics2D g2d) {
        g2d.setColor(new Color(255, 255, 255, 60));
        for (int i = 0; i < 30; i++) {
            float x = (i * 73 + animationTime * 20) % 800;
            float y = (i * 127 + animationTime * 15) % 600;
            float size = 2 + (i % 4);
            g2d.fillOval((int)x, (int)y, (int)size, (int)size);
        }
    }
    
    private void drawGlowingTitle(Graphics2D g2d) {
        String title = "ICY TOWER";
        Font titleFont = new Font("Segoe UI Semibold Black", Font.BOLD, (int)(60 * titlePulse));
        g2d.setFont(titleFont);
        
        int titleWidth = g2d.getFontMetrics().stringWidth(title);
        int titleX = (800 - titleWidth) / 2;
        int titleY = 180;
        
        // Glow effect
        for (int i = 8; i >= 0; i--) {
            int alpha = 255 / (i + 1);
            g2d.setColor(new Color(100, 200, 255, alpha / 4));
            g2d.drawString(title, titleX - i/2, titleY - i/2);
        }
        
        // Ice gradient text
        GradientPaint titleGradient = new GradientPaint(
            titleX, titleY - 30, new Color(200, 240, 255),
            titleX, titleY + 10, new Color(100, 180, 240)
        );
        g2d.setPaint(titleGradient);
        g2d.drawString(title, titleX, titleY);
        
        // Ice shine effect
        g2d.setColor(new Color(255, 255, 255, 120));
        g2d.drawString(title, titleX + 1, titleY - 1);
    }
    
    private void drawInstructionPanel(Graphics2D g2d) {
        // Glass-like panel background
        RoundRectangle2D panel = new RoundRectangle2D.Float(150, 320, 500, 180, 20, 20);

        GradientPaint panelGradient = new GradientPaint(
            150, 320, new Color(30, 60, 120, 80),
            150, 500, new Color(15, 30, 60, 120)
        );
        g2d.setPaint(panelGradient);
        g2d.fill(panel);

        // Panel border
        g2d.setColor(new Color(100, 150, 200, 100));
        g2d.draw(panel);

        // Instructions
        g2d.setColor(new Color(220, 240, 255));
        g2d.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));

        String[] instructions = {
            "Controls:",
            "Arrow Keys / WASD - Move",
            "SPACE - Jump",
            "ESC - Pause Game"
        };

        for (int i = 0; i < instructions.length; i++) {
            int textWidth = g2d.getFontMetrics().stringWidth(instructions[i]);
            int x = (800 - textWidth) / 2;
            int y = 360 + (i * 30);

          

            g2d.drawString(instructions[i], x, y);
        }
    }
    
    private void drawPressEnterPrompt(Graphics2D g2d) {
        // Animated glow box for ENTER prompt
        String prompt = "Press ENTER to Start";
        g2d.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        int promptWidth = g2d.getFontMetrics().stringWidth(prompt);
        int promptX = (800 - promptWidth) / 2;
        int promptY = 540;

        // Glowing background
        RoundRectangle2D promptBox = new RoundRectangle2D.Float(
            promptX - 20, promptY - 25, promptWidth + 40, 35, 15, 15
        );

        float glowIntensity = 0.7f + 0.3f * (float)Math.sin(animationTime * 4);
        Color glowColor = new Color(100, 200, 255, (int)(100 * glowIntensity));
        g2d.setColor(glowColor);
        g2d.fill(promptBox);

        // Text with pulse effect
        Color textColor = new Color(255, 255, 255, (int)(200 + 55 * glowIntensity));
        g2d.setColor(textColor);
        g2d.drawString(prompt, promptX, promptY);
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Handled in update
    }
}