// InputConfiguration.java 
package com.icytower.systems;

import com.icytower.entities.Player;
import com.icytower.core.GameManager;
import com.icytower.commands.*;
import java.awt.event.KeyEvent;

public class InputConfiguration {
    

    public static void setupPlayerControls(Player player) {
        InputManager input = InputManager.getInstance();
        
     
        input.bindHoldCommand(KeyEvent.VK_LEFT, new LeftCommand(player));
        input.bindHoldCommand(KeyEvent.VK_A, new LeftCommand(player));
        input.bindHoldCommand(KeyEvent.VK_RIGHT, new RightCommand(player));
        input.bindHoldCommand(KeyEvent.VK_D, new RightCommand(player));
        
    
        input.bindReleaseCommand(KeyEvent.VK_LEFT, new StopMovementCommand(player));
        input.bindReleaseCommand(KeyEvent.VK_A, new StopMovementCommand(player));
        input.bindReleaseCommand(KeyEvent.VK_RIGHT, new StopMovementCommand(player));
        input.bindReleaseCommand(KeyEvent.VK_D, new StopMovementCommand(player));
        
       
        input.bindPressCommand(KeyEvent.VK_SPACE, new JumpCommand(player));
    }
    
   
    public static void setupGameControls(GameManager gameManager) {
        InputManager input = InputManager.getInstance();
        
      
        input.bindPressCommand(KeyEvent.VK_ESCAPE, new PauseCommand(gameManager));
        input.bindPressCommand(KeyEvent.VK_P, new PauseCommand(gameManager));
    }
    
 
    public static void clearAllControls() {
        InputManager.getInstance().clearBindings();
    }
}