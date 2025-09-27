// Paused State
package com.icytower.states;
import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.core.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import com.icytower.interfaces.GameState;

public class PausedState implements GameState {
    @Override
    public void enter() {
        System.out.println("Game Paused");
    }
    
    @Override
    public void exit() {
        System.out.println("Game Resumed");
    }
    
    @Override
    public void update() {
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ESCAPE)) {
            GameManager.getInstance().setState("PLAYING");
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        renderer.setColor(new Color(0, 0, 0, 128));
        renderer.fillRect(0, 0, 800, 600);
        
        renderer.setColor(Color.WHITE);
        renderer.setFont(new Font("Arial", Font.BOLD, 48));
        renderer.drawString("PAUSED", 310, 300);
        
        renderer.setFont(new Font("Arial", Font.PLAIN, 24));
        renderer.drawString("Press ESC to Resume", 300, 350);
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Handled in update
    }
}