package com.icytower.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import com.icytower.graphics.Renderer;
import com.icytower.interfaces.GameState;
import com.icytower.interfaces.GameObserver;
import com.icytower.core.GameEngine;
import com.icytower.states.PlayingState;
import com.icytower.states.MenuState;
import com.icytower.states.GameOverState;
import com.icytower.states.PausedState;

// Singleton Pattern - Game Manager
public class GameManager {
    private static GameManager instance;
    private GameEngine gameEngine;
    private List<GameObserver> observers;
    private GameState currentState;
    private Map<String, GameState> states;
    private boolean isPaused = false; 
    
    private GameManager() {
        observers = new ArrayList<>();
        states = new HashMap<>();
        initializeStates();
    }
    
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
    
    private void initializeStates() {
        states.put("PLAYING", new PlayingState());
        states.put("MENU", new MenuState());
        states.put("GAME_OVER", new GameOverState());
        states.put("PAUSED", new PausedState());
    }
    
    public void setState(String stateName) {
        GameState newState = states.get(stateName);
        if (newState != null && newState != currentState) {
            if (currentState != null) {
                currentState.exit();
            }
            currentState = newState;
            currentState.enter();
        }
    }
    

    public void togglePause() {
        if (currentState == states.get("PLAYING")) {
            setState("PAUSED");
        } else if (currentState == states.get("PAUSED")) {
            setState("PLAYING");
        }
    }
    public boolean isPaused() {
        return isPaused;
    }
    
    public GameState getCurrentState() {
        return currentState;
    }
    
    public void setGameEngine(GameEngine engine) {
        this.gameEngine = engine;
    }
    
    public GameEngine getGameEngine() {
        return gameEngine;
    }
    
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    
    public void notifyScoreChanged(int newScore) {
        for (GameObserver observer : observers) {
            observer.onScoreChanged(newScore);
        }
    }
    
    public void notifyGameOver(int finalScore) {
        for (GameObserver observer : observers) {
            observer.onGameOver(finalScore);
        }
    }
    
    public void notifyPlayerPositionChanged(float x, float y) {
        for (GameObserver observer : observers) {
            observer.onPlayerPositionChanged(x, y);
        }
    }
}