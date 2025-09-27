package com.icytower.enums;
import java.awt.Color;
public
enum PlatformType {
    NORMAL(new Color(200, 200, 255), new Color(220, 220, 255)),
    ICE(new Color(200, 255, 255), new Color(220, 255, 255)),
    BREAKABLE(new Color(255, 200, 200), new Color(255, 220, 220));
    
    private final Color color;
    private final Color highlightColor;
    
    PlatformType(Color color, Color highlightColor) {
        this.color = color;
        this.highlightColor = highlightColor;
    }
    
    public Color getColor() { return color; }
    public Color getHighlightColor() { return highlightColor; }
}