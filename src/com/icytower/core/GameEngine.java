package com.icytower.core;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
import com.icytower.interfaces.GameState;
import com.icytower.interfaces.GameObserver;

public class GameEngine extends JPanel implements ActionListener, GameObserver {
    private Timer gameTimer;
    private GameManager gameManager;
    private static final int FPS = 60;

    public GameEngine() {
        // GameManager
        gameManager = GameManager.getInstance();
        gameManager.setGameEngine(this);
        gameManager.addObserver(this);

        // Timer for updates
        gameTimer = new Timer(1000 / FPS, this);

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(InputManager.getInstance());

        // Start with menu state
        gameManager.setState("MENU");

        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();    
        repaint();   
    }

    
    private void update() {
        GameState currentState = gameManager.getCurrentState();
        if (currentState != null) {
            currentState.update(); 
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Renderer renderer = new Renderer((Graphics2D) g);

        GameState currentState = gameManager.getCurrentState();
        if (currentState != null) {
            currentState.render(renderer);
        }
    }

    // GameObserver interface methods
    @Override
    public void onScoreChanged(int newScore) {
    
    }

    @Override
    public void onGameOver(int finalScore) {
        
    }

    @Override
    public void onPlayerPositionChanged(float x, float y) {
      
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}
