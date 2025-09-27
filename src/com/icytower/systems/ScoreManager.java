// Singleton - Score Manager
package com.icytower.systems;
import com.icytower.core.GameManager;

public class ScoreManager {
    private static ScoreManager instance;
    private int currentScore;
    private int highScore;
    
    private ScoreManager() {
        currentScore = 0;
        highScore = 0;
    }
    
    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }
    
    public void updateScore(int newScore) {
        if (newScore > currentScore) {
            currentScore = newScore;
            GameManager.getInstance().notifyScoreChanged(currentScore);
            
            if (currentScore > highScore) {
                highScore = currentScore;
            }
        }
    }
    
    public void resetScore() {
        currentScore = 0;
    }
    
    public int getCurrentScore() { return currentScore; }
    public int getHighScore() { return highScore; }
}