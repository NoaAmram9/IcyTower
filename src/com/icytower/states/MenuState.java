package com.icytower.states;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.core.GameManager;
import com.icytower.interfaces.GameState;
    

public class MenuState implements GameState {
    @Override
    public void enter() {
        System.out.println("Entering Menu State");
    }
    
    @Override
    public void exit() {
        System.out.println("Exiting Menu State");
    }
    
    @Override
    public void update() {
        // Menu logic
        if (InputManager.getInstance().isKeyPressed(KeyEvent.VK_ENTER)) {
            GameManager.getInstance().setState("PLAYING");
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.fillRect(0, 0, 800, 600);
        
        renderer.setColor(Color.WHITE);
        renderer.setFont(new Font("Arial", Font.BOLD, 48));
        renderer.drawString("ICY TOWER", 250, 200);
        
        renderer.setFont(new Font("Arial", Font.PLAIN, 24));
        renderer.drawString("Press ENTER to Start", 280, 350);
        renderer.drawString("Use Arrow Keys or WASD to Move", 220, 400);
        renderer.drawString("Press SPACE to Jump", 290, 450);
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Handled in update
    }
}