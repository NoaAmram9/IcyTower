package com.icytower.states;

import com.icytower.interfaces.GameState;
import com.icytower.core.GameManager;
import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.systems.SoundManager;
import com.icytower.systems.ScoreManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.GradientPaint;
import java.awt.BasicStroke;

public class GameOverState implements GameState {
    private int finalScore;
    private float animationTime = 0;
    private float overlayOpacity = 0;
    private boolean isEntering = true;
    private int pulseTimer = 0;
    private boolean textPulse = true;

    @Override
    public void enter() {
        finalScore = ScoreManager.getInstance().getCurrentScore();
        SoundManager.getInstance().playSound("gameOver");
        animationTime = 0;
        overlayOpacity = 0;
        isEntering = true;
    }

    @Override
    public void exit() {
        // Cleanup if needed
    }

    @Override
    public void update() {
        animationTime += 0.03f;

        if (isEntering) {
            overlayOpacity = Math.min(overlayOpacity + 0.05f, 0.85f);
            if (overlayOpacity >= 0.85f) {
                isEntering = false;
            }
        }

        
        pulseTimer++;
        if (pulseTimer >= 40) {
            textPulse = !textPulse;
            pulseTimer = 0;
        }

        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_R)) {
            GameManager.getInstance().setState("PLAYING");
        }
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            GameManager.getInstance().setState("MENU");
        }
    }

    @Override
    public void render(Renderer renderer) {
        Graphics2D g2d = renderer.getGraphics2D();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawAnimatedOverlay(g2d);
        drawMainPanel(g2d);
        drawGameOverText(g2d);
        drawScores(g2d);
        drawOptions(g2d);
        drawIceCrystals(g2d);
    }

    private void drawAnimatedOverlay(Graphics2D g2d) {
        GradientPaint overlayGradient = new GradientPaint(
                0, 0, new Color(20, 0, 40, (int)(200 * overlayOpacity)),
                800, 600, new Color(60, 0, 80, (int)(150 * overlayOpacity))
        );
        g2d.setPaint(overlayGradient);
        g2d.fillRect(0, 0, 800, 600);
    }

    private void drawMainPanel(Graphics2D g2d) {
        RoundRectangle2D panel = new RoundRectangle2D.Float(180, 120, 440, 360, 25, 25);

        GradientPaint panelGradient = new GradientPaint(
                200, 150, new Color(60, 0, 100, (int)(180 * overlayOpacity)),
                200, 450, new Color(30, 0, 60, (int)(200 * overlayOpacity))
        );
        g2d.setPaint(panelGradient);
        g2d.fill(panel);

        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setColor(new Color(200, 100, 255, (int)(150 * overlayOpacity)));
        g2d.draw(panel);
    }

    private void drawGameOverText(Graphics2D g2d) {
        String gameOverText = "GAME OVER";
        g2d.setFont(new Font("Segoe UI Black", Font.BOLD, 50));
        int textWidth = g2d.getFontMetrics().stringWidth(gameOverText);
        int textX = (800 - textWidth) / 2;
        int textY = 200;

        // Glow
        for (int i = 5; i >= 0; i--) {
            int alpha = (int)((70 - i * 12) * overlayOpacity);
            g2d.setColor(new Color(220, 100, 255, alpha));
            g2d.drawString(gameOverText, textX - i, textY - i);
        }

        GradientPaint textGradient = new GradientPaint(
                textX, textY - 20, new Color(255, 220, 255, (int)(255 * overlayOpacity)),
                textX, textY + 20, new Color(180, 100, 220, (int)(255 * overlayOpacity))
        );
        g2d.setPaint(textGradient);
        g2d.drawString(gameOverText, textX, textY);
    }

    private void drawScores(Graphics2D g2d) {
        g2d.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        g2d.setColor(new Color(230, 200, 255, (int)(220 * overlayOpacity)));

        String scoreText = "Final Score: " + finalScore;
        String highScoreText = "High Score: " + ScoreManager.getInstance().getHighScore();

        int scoreWidth = g2d.getFontMetrics().stringWidth(scoreText);
        int highScoreWidth = g2d.getFontMetrics().stringWidth(highScoreText);

        g2d.drawString(scoreText, (800 - scoreWidth) / 2, 260);
        g2d.drawString(highScoreText, (800 - highScoreWidth) / 2, 300);
    }

    private void drawOptions(Graphics2D g2d) {
        String restartText = "Press R to Restart";
        String menuText = "Press ESC for Menu";

        g2d.setFont(new Font("Segoe UI", Font.BOLD, 22));

        Color glowColor = textPulse
                ? new Color(255, 180, 255, (int)(255 * overlayOpacity))
                : new Color(200, 150, 220, (int)(200 * overlayOpacity));

        g2d.setColor(glowColor);
        int restartWidth = g2d.getFontMetrics().stringWidth(restartText);
        int menuWidth = g2d.getFontMetrics().stringWidth(menuText);

        g2d.drawString(restartText, (800 - restartWidth) / 2, 380);
        g2d.drawString(menuText, (800 - menuWidth) / 2, 420);
    }

    private void drawIceCrystals(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2.0f));
        for (int i = 0; i < 6; i++) {
            float angle = animationTime * 25 + i * 60;
            float x = 400 + 200 * (float)Math.cos(Math.toRadians(angle));
            float y = 300 + 140 * (float)Math.sin(Math.toRadians(angle));

            g2d.setColor(new Color(200, 150, 255, (int)(80 * overlayOpacity)));

            int[] xPoints = {(int)x, (int)(x + 8), (int)x, (int)(x - 8)};
            int[] yPoints = {(int)(y - 10), (int)y, (int)(y + 10), (int)y};

            g2d.drawPolygon(xPoints, yPoints, 4);
            g2d.drawLine((int)(x - 6), (int)y, (int)(x + 6), (int)y);
        }
    }

    @Override
    public void handleInput(InputManager inputManager) {
        // Already handled in update
    }
}
