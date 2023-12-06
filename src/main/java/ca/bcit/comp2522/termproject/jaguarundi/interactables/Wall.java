package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Collidable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Wall implements Collidable {
    private static final Map<String, Image> WALL_SPRITE_MAP = new HashMap<>();
    static {
        WALL_SPRITE_MAP.put("top", new Image(Objects.requireNonNull(Wall.class.getResourceAsStream("wall_top.png"))));
        WALL_SPRITE_MAP.put("bottom", new Image(Objects.requireNonNull(Wall.class.getResourceAsStream("wall_bottom.png"))));
        WALL_SPRITE_MAP.put("side", new Image(Objects.requireNonNull(Wall.class.getResourceAsStream("wall_side.png"))));
        WALL_SPRITE_MAP.put("counter", new Image(Objects.requireNonNull(Wall.class.getResourceAsStream("counter.png"))));
    }

    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private final String wallType;

    public Wall(final int width, final int height, final String wallType) {
        this.xPosition = 0;
        this.yPosition = 0;
        this.width = width;
        this.height = height;
        this.wallType = wallType;
    }

    public void draw(GraphicsContext gc) {
        switch (this.wallType) {
            case "top" -> {
                for (int i = 0; i < width; i += 50) {
                    gc.drawImage(WALL_SPRITE_MAP.get("top"), xPosition + i, yPosition);
                }
            }
            case "bottom" -> {
                for (int i = 0; i < width; i += 50) {
                    gc.drawImage(WALL_SPRITE_MAP.get("bottom"), xPosition + i, yPosition);
                }
            }
            case "side" -> {
                for (int i = 0; i < height; i += 50) {
                    gc.drawImage(WALL_SPRITE_MAP.get("side"), xPosition, yPosition + i);
                }
            }
            case "counter" -> {
                for (int i = 0; i < height; i += 50) {
                    gc.drawImage(WALL_SPRITE_MAP.get("counter"), xPosition, yPosition + i);
                }
            }
        }

    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
