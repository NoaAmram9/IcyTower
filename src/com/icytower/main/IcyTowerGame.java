package com.icytower.main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.icytower.core.GameEngine;

public class IcyTowerGame extends JFrame {
    private GameEngine gameEngine;
    
    public IcyTowerGame() {
        initializeWindow();
        initializeGame();
    }
    
    private void initializeWindow() {
        setTitle("Icy Tower - Advanced OOP with Design Patterns");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    private void initializeGame() {
        gameEngine = new GameEngine();
        add(gameEngine);
        pack();
        
        setVisible(true);
        gameEngine.requestFocusInWindow();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new IcyTowerGame();
        });
    }
}