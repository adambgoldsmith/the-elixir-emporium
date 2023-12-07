package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Collidable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Wall class for players to collide with.
 *
 * @Author Adam , Vivian
 * @version 2023
 */
public class Wall implements Collidable {

    /**
     * Wall sprite map.
     */
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

    /**
     * Constructs a Wall object.
     *
     * @param width the width
     * @param height the height
     * @param wallType the wall type
     */
    public Wall(final int width, final int height, final String wallType) {
        this.xPosition = 0;
        this.yPosition = 0;
        this.width = width;
        this.height = height;
        this.wallType = wallType;
    }

    /**
     * Draws the Wall.
     *
     * @param gc the graphics context
     */
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

    /**
     * Gets the x position.
     *
     * @return the x position
     */
    public double getXPosition() {
        return xPosition;
    }

    /**
     * Gets the y position.
     *
     * @return the y position
     */
    public double getYPosition() {
        return yPosition;
    }

    /**
     * Sets the x position.
     *
     * @param xPosition the x position
     */
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Sets the y position.
     *
     * @param yPosition the y position
     */
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }
}
