package com.icytower.core;

import com.icytower.interfaces.GameState;
import com.icytower.states.MenuState;
import com.icytower.systems.InputManager;
import com.icytower.systems.SoundManager;
import com.icytower.systems.ScoreManager;
import com.icytower.graphics.Renderer;


public class GameManager {

    // Singleton instance
    private static GameManager instance;

    private GameEngine gameEngine;
    private GameState currentState;

    private GameManager() {
        // private constructor
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }


    public void setGameEngine(GameEngine engine) {
        this.gameEngine = engine;
    }

    public void addObserver(Object observer) {
        // TODO: implement observer logic
    }

    public void setState(String stateName) {
      
        System.out.println("Switching state to " + stateName);
    }

    public GameState getCurrentState() {
        return currentState;
    }

   
    public void notifyScoreChanged(int score) {
        System.out.println("Score updated: " + score);
    }
}

