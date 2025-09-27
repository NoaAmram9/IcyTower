package com.icytower.enums;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File; // Add this import
public enum PlatformType {
    NORMAL("resources/images/platforms/normal.png", new Color(200, 200, 255), new Color(220, 220, 255)),
    ICE("resources/images/platforms/ice.png", new Color(200, 255, 255), new Color(220, 255, 255)),
    BREAKABLE("resources/images/platforms/break.png", new Color(255, 200, 200), new Color(255, 220, 220));

    private final BufferedImage image;
    private final Color color;
    private final Color highlightColor;


    PlatformType(String resourcePath, Color color, Color highlightColor) {
    BufferedImage img = null;
    try {
        img = ImageIO.read(new File(resourcePath)); // Use resourcePath here
        if (img == null) {
            System.err.println("Image not found at: " + resourcePath);
        }
    } catch (IOException e) {
        System.err.println("Failed to load image at: " + resourcePath);
    }
    this.image = img;
    this.color = color;
    this.highlightColor = highlightColor;
}

    public BufferedImage getImage() {
        return image;
    }

    public boolean hasImage() {
        return image != null;
    }

    public Color getColor() {
        return color;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }
}
