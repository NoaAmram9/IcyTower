// Game Over State Class
package com.icytower.states;
import com.icytower.interfaces.GameState;
import com.icytower.core.GameManager;
import com.icytower.graphics.Renderer;
import com.icytower.managers.InputManager;
import com.icytower.managers.SoundManager;
import com.icytower.managers.ScoreManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class GameOverState implements GameState {
    private int finalScore;
    
    @Override
    public void enter() {
        finalScore = ScoreManager.getInstance().getCurrentScore();
        SoundManager.getInstance().playSound("gameOver");
    }
    
    @Override
    public void exit() {
        // Cleanup
    }
    
    @Override
    public void update() {
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_R)) {
            GameManager.getInstance().setState("PLAYING");
        }
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            GameManager.getInstance().setState("MENU");
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        renderer.setColor(new Color(0, 0, 0, 128));
        renderer.fillRect(0, 0, 800, 600);
        
        renderer.setColor(Color.WHITE);
        renderer.setFont(new Font("Arial", Font.BOLD, 48));
        renderer.drawString("GAME OVER", 250, 250);
        
        renderer.setFont(new Font("Arial", Font.PLAIN, 24));
        renderer.drawString("Final Score: " + finalScore, 310, 320);
        renderer.drawString("High Score: " + ScoreManager.getInstance().getHighScore(), 310, 350);
        renderer.drawString("Press R to Restart", 310, 420);
        renderer.drawString("Press ESC for Menu", 310, 450);
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Handled in update
    }
}