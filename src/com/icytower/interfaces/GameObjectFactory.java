// Factory Pattern Interface
package com.icytower.interfaces;
import com.icytower.core.GameObject;
public interface GameObjectFactory {
    GameObject create(float x, float y);
}