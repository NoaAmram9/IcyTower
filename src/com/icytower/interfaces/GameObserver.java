// Observer Pattern Interface
package com.icytower.interfaces;
public interface GameObserver {
    void onScoreChanged(int newScore);
    void onGameOver(int finalScore);
    void onPlayerPositionChanged(float x, float y);
}