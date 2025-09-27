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
import com.icytower.systems.ParticleSystem;

public class PlayingState implements GameState {
    private World world;
    private Player player;
    private Camera camera;
    

    @Override
    public void exit() {
        // Cleanup if needed
    }
    
    @Override
    public void enter() {
        world = new World();
        player = new Player(400, 520);
        camera = new Camera();
        
        InputManager input = InputManager.getInstance();
        input.clearBindings();
        
        input.bindKey(KeyEvent.VK_ESCAPE, new PauseCommand(GameManager.getInstance()));
        input.bindKey(KeyEvent.VK_P, new PauseCommand(GameManager.getInstance()));
        
        ScoreManager.getInstance().resetScore();
    }
    
    @Override
    public void render(Renderer renderer) {
        float layer1Factor = 0.3f; 
        float layer2Factor = 0.3f;  
        renderer.drawBackground( camera.getX() * layer1Factor, camera.getY() * layer1Factor, 800, 600); 
        renderer.drawBackground( camera.getX() * layer2Factor, camera.getY() * layer2Factor, 800, 600);
        
        renderer.translate(camera.getX(), camera.getY());
        world.render(renderer);
        player.render(renderer);
        
        // Render particle effects       
        ParticleSystem.getInstance().render(renderer, camera.getX(), camera.getY());
        
        renderer.translate(-camera.getX(), -camera.getY());
        
        // Draw UI
        renderer.setColor(Color.BLACK);
        renderer.setFont(new Font("Segoe UI Semibold", Font.BOLD, 20));
        renderer.drawString("Score: " + ScoreManager.getInstance().getCurrentScore(), 10, 30);
        renderer.drawString("Height: " + Math.max(0, (int)(600 - player.getY()) / 10), 10, 55);
        
        // Debug info
        renderer.setColor(Color.DARK_GRAY);
        renderer.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        renderer.drawString("Particles: " + ParticleSystem.getInstance().getParticleCount(), 10, 580);
    }
    
    @Override
    public void handleInput(InputManager inputManager) {
        // Input is handled through commands
    }
    @Override
public void update() {
    world.update(player);      
    player.update(1.0f);      
    camera.update(player.getX(), player.getY(), 600);
    
    // Update particle system
    ParticleSystem particleSystem = ParticleSystem.getInstance();
    particleSystem.update(1.0f);
    particleSystem.createSnowEffect(800, 600, camera.getY());
    particleSystem.cleanupParticles(player.getY());
    
    // Update score
    int newScore = Math.max(0, (int)(600 - player.getY()) / 10);
    ScoreManager.getInstance().updateScore(newScore);
    
    // Check game over
    if (player.getY() > -camera.getY() + 700) {
        GameManager.getInstance().notifyGameOver(ScoreManager.getInstance().getCurrentScore());
        GameManager.getInstance().setState("GAME_OVER");
    }
}
    public Player getPlayer() { return player; }
    public World getWorld() { return world; }
}