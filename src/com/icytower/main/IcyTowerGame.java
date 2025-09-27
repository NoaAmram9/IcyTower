package com.icytower.main;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.icytower.core.GameEngine;
import com.icytower.systems.InputManager;

/**
 * 
 * @author NOA
 * @version 1.0
 */
public class IcyTowerGame extends JFrame {
    
    //  Settings
    private static final String GAME_TITLE = "Icy Tower - Advanced OOP with Design Patterns - Created by NOA";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final boolean WINDOW_RESIZABLE = false;
    
    // Variables
    private GameEngine gameEngine;
    
    /**
     * Constructor - Initialize the game window and components
     */
    public IcyTowerGame() {
        System.out.println("Starting Icy Tower Game...");
        initializeWindow();
        initializeGame();
        System.out.println("Game initialized successfully!");
    }
    
    /**
     * Initialize the main game window
     */
    private void initializeWindow() {
        // Window settings
        setTitle(GAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(WINDOW_RESIZABLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        try {
        Image icon = Toolkit.getDefaultToolkit().getImage("resources/images/icon.png");
        setIconImage(icon);
       } catch (Exception e) {
        System.out.println("Could not load window icon: " + e.getMessage());
       }
        // Center the window on the screen
        setLocationRelativeTo(null);
        
        // Additional window settings
        setBackground(Color.BLACK);
        
        // Add listener for window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Game is shutting down...");
                cleanup();
                System.exit(0);
            }
        });
        
        // Set window icon (optional)
        try {
            // setIconImage(Toolkit.getDefaultToolkit().getImage("resources/images/icon.png"));
        } catch (Exception e) {
            System.out.println("Could not load window icon: " + e.getMessage());
        }
    }
    
    /**
     * Initialize the game
     */
    private void initializeGame() {
        try {
            // Create the game engine
            gameEngine = new GameEngine();
            
            // Add the game engine to the window
            add(gameEngine);
            
            // Fit window size to content
            pack();
            
            // Show the window
            setVisible(true);
            
            // Give focus to the game engine (important for keyboard input)
            gameEngine.requestFocusInWindow();
            
            System.out.println("Game engine started successfully!");
            
        } catch (Exception e) {
            System.err.println("Error initializing game: " + e.getMessage());
            e.printStackTrace();
            
            // Show error message to user
            JOptionPane.showMessageDialog(
                this,
                "Error starting game: " + e.getMessage(),
                "Game Error",
                JOptionPane.ERROR_MESSAGE
            );
            
            System.exit(1);
        }
    }
    
    /**
     * Clean up resources before closing the game
     */
    private void cleanup() {
        try {
            if (gameEngine != null) {
                // Additional resource cleanup can be added here
                System.out.println("Cleaning up game resources...");
            }
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    /**
     * Get the game engine (for debug purposes)
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }
    
    /**
     * Run the game in fullscreen mode (optional)
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            dispose();
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
            gameEngine.requestFocusInWindow();
        } else {
            dispose();
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
            setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            setLocationRelativeTo(null);
            setVisible(true);
            gameEngine.requestFocusInWindow();
        }
    }
    
    /**
     * Main entry point of the program
     */
    public static void main(String[] args) {
        // Print game info
        printGameInfo();
        
        // Set system Look and Feel
        setupLookAndFeel();
        
        // Create and run the game in the EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                new IcyTowerGame();
            } catch (Exception e) {
                System.err.println("Fatal error starting game: " + e.getMessage());
                e.printStackTrace();
                
                // Show critical error message
                JOptionPane.showMessageDialog(
                    null,
                    "Fatal error starting game:\n" + e.getMessage() + 
                    "\n\nPlease check the console for more details.",
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE
                );
                
                System.exit(1);
            }
        });
    }
    
    /**
     * Print game info
     */
    private static void printGameInfo() {
        System.out.println("==========================================");
        System.out.println("         ICY TOWER GAME v1.0");
        System.out.println("      Advanced OOP with Design Patterns");
        System.out.println("==========================================");
        System.out.println("Game Features:");
        System.out.println("- Singleton Pattern (GameManager, InputManager, etc.)");
        System.out.println("- Strategy Pattern (Movement strategies)");
        System.out.println("- Observer Pattern (Game events)");
        System.out.println("- Factory Pattern (Platform creation)");
        System.out.println("- State Pattern (Game states)");
        System.out.println("- Command Pattern (Input commands)");
        System.out.println("- Particle System (Visual effects)");
        System.out.println("==========================================");
        System.out.println("Controls:");
        System.out.println("- Arrow Keys / WASD: Move");
        System.out.println("- SPACE: Jump");
        System.out.println("- ENTER: Start Game (from menu)");
        System.out.println("- ESC: Pause/Resume");
        System.out.println("- R: Restart (from game over)");
        System.out.println("==========================================");
        System.out.println();
    }
    
    /**
     * Set system Look and Feel
     */
    private static void setupLookAndFeel() {
        try {
            // Use system Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("Look and Feel set to: " + UIManager.getLookAndFeel().getName());
            
        } catch (ClassNotFoundException e) {
            System.err.println("Look and Feel class not found: " + e.getMessage());
        } catch (InstantiationException e) {
            System.err.println("Look and Feel instantiation error: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Look and Feel access error: " + e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Look and Feel not supported: " + e.getMessage());
        }
        
        // Additional Swing settings
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }


    /**
     * Check system requirements (optional)
     */
    private static boolean checkSystemRequirements() {
        // Check Java version
        String javaVersion = System.getProperty("java.version");
        System.out.println("Java Version: " + javaVersion);
        
        // Check available memory
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        
        System.out.println("Max Memory: " + (maxMemory / 1024 / 1024) + " MB");
        System.out.println("Total Memory: " + (totalMemory / 1024 / 1024) + " MB");
        System.out.println("Free Memory: " + (freeMemory / 1024 / 1024) + " MB");
        
        // Check operating system
        String os = System.getProperty("os.name");
        System.out.println("Operating System: " + os);
        
        return true; // Always returns true in this case
    }
}