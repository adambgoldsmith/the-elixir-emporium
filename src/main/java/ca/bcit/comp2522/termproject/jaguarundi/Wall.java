package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall implements Collidable {
    public final static Color WALL_COLOR = Color.DARKRED;

    private final int xPosition;
    private final int yPosition;
    private final int width;
    private final int height;
    private final Color color;

    public Wall(final int xPosition, final int yPosition, final int width, final int height) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.color = WALL_COLOR;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(xPosition, yPosition, width, height);
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}