// Input Manager using Singleton Pattern and Command Pattern
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
    
    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }
    
    public void bindKey(int keyCode, Command command) {
        keyCommands.put(keyCode, command);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        Command command = keyCommands.get(e.getKeyCode());
        if (command != null) {
            command.execute();
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}