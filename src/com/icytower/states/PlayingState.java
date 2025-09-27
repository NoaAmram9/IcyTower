// Playing State
package com.icytower.states;
import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.core.GameManager;
import com.icytower.systems.ScoreManager;
import com.icytower.commands.JumpCommand;
import com.icytower.commands.PauseCommand;
import com.icytower.entities.Player;
import com.icytower.core.World;
import com.icytower.graphics.Camera;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import com.icytower.interfaces.GameState;

public class PlayingState implements GameState {
    private World world;
    private Player player;
    private Camera camera;
    
    @Override
    public void enter() {
        world = new World();
        player = new Player(400, 500);
        camera = new Camera();
        
        // Bind commands
        InputManager input = InputManager.getInstance();
        input.bindKey(KeyEvent.VK_SPACE, new JumpCommand(player));
        input.bindKey(KeyEvent.VK_ESCAPE, new PauseCommand());
        
        ScoreManager.getInstance().resetScore();
    }
    
    @Override
    public void exit() {
        // Cleanup if needed
    }
    
    @Override
    public void update() {
        player.update(1.0f);
        world.update(player);
        camera.update(player.getX(), player.getY(), 600);
        
        // Update score based on height
        int newScore = Math.max(0, (int)(600 - player.getY()) / 10);
        ScoreManager.getInstance().updateScore(newScore);
        
        // Check game over
        if (player.getY() > -camera.getY() + 700) {
            GameManager.getInstance().notifyGameOver(ScoreManager.getInstance().getCurrentScore());
            GameManager.getInstance().setState("GAME_OVER");
        }
    }
    
    @Override
    public void render(Renderer renderer) {
        renderer.drawGradientBackground(800, 600);
        
        renderer.translate(camera.getX(), camera.getY());
        world.render(renderer);
        player.render(renderer);
        renderer.translate(-camera.getX(), -camera.getY());
        
        // Draw UI
        renderer.setColor(Color.BLACK);
        renderer.setFont(new Font("Arial", Font.BOLD, 20));
        renderer.drawString("Score: " + ScoreManager.getInstance().getCurrentScore(), 10, 30);
        renderer.drawString("Height: " + Math.max(0, (int)(600 - player.getY()) / 10), 10, 55);
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Input is handled through commands
    }
    
    public Player getPlayer() { return player; }
    public World getWorld() { return world; }
}