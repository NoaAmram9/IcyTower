// Input Manager - Simplified for Strategy Pattern only
package com.icytower.systems;

import com.icytower.interfaces.Command;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener {
    private static InputManager instance;
    
    private boolean[] keys = new boolean[256];
    
    private Map<Integer, Command> keyCommands = new HashMap<>();
    
    private InputManager() {}
    
    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
    
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }
    
    // backward compatibility
    public boolean isKeyPressed(int keyCode) {
        return isKeyDown(keyCode);
    }
   
    public void bindKey(int keyCode, Command command) {
        keyCommands.put(keyCode, command);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        boolean wasPressed = keys[keyCode];
        keys[keyCode] = true;
        
        if (!wasPressed && keyCommands.containsKey(keyCode)) {
            keyCommands.get(keyCode).execute();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public void clearBindings() {
        keyCommands.clear();
    }
}