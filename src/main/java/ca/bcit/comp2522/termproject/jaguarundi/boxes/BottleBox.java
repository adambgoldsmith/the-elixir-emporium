package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Collidable;
import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class BottleBox extends Interactable implements Collidable {
    public final static Image BOTTLE_BOX_SPRITE = new Image(Objects.requireNonNull(BottleBox.class.getResourceAsStream("bottle_table.png")));
    public static final int BOTTLE_BOX_WIDTH = 50;
    public static final int BOTTLE_BOX_HEIGHT = 50;

    private final double xPosition;
    private final double yPosition;
    private final double width;
    private final double height;

    public BottleBox(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = BOTTLE_BOX_WIDTH;
        this.height = BOTTLE_BOX_HEIGHT;
    }

    public void draw(final GraphicsContext gc) {
        gc.drawImage(BOTTLE_BOX_SPRITE, xPosition, yPosition);
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}