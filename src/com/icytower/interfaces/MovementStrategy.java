// Strategy Pattern Interface
package com.icytower.interfaces;
import com.icytower.core.GameObject;
import com.icytower.systems.InputManager;
public interface MovementStrategy {
    void move(GameObject object, InputManager inputManager);
}