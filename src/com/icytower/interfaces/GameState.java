// State Pattern Interface
package com.icytower.interfaces;
import com.icytower.graphics.Renderer;
import com.icytower.systems.InputManager;
public interface GameState {
  void enter();
    void exit();
    void update();
    void render(Renderer renderer);
    void handleInput(InputManager inputManager);
}