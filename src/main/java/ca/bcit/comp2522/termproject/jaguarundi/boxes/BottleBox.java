package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Collidable;
import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;
/**
 * A box that holds bottles.
 *
 * @author Vivian , Adam
 * @version 2023
 */

public class BottleBox extends Interactable implements Collidable {
    /**
     * The sprite for the bottle box.
     */
    public final static Image BOTTLE_BOX_SPRITE = new Image(Objects.requireNonNull(BottleBox.class.
            getResourceAsStream("bottle_table.png")));
    /**
     * The width of the bottle box.
     */
    public static final int BOTTLE_BOX_WIDTH = 50;
    /**
     * The height of the bottle box.
     */
    public static final int BOTTLE_BOX_HEIGHT = 50;

    private final double xPosition;
    private final double yPosition;
    private final double width;
    private final double height;
    /**
     * Constructs a bottle box.
     *
     * @param xPosition the x position of the bottle box
     * @param yPosition the y position of the bottle box
     */

    public BottleBox(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = BOTTLE_BOX_WIDTH;
        this.height = BOTTLE_BOX_HEIGHT;
    }
    /**
     * Draws the bottle box.
     *
     * @param gc the graphics context to draw with
     */

    public void draw(final GraphicsContext gc) {
        gc.drawImage(BOTTLE_BOX_SPRITE, xPosition, yPosition);
    }
    /**
     * Gets the x position of the bottle box.
     *
     * @return the x position of the bottle box
     */

    public double getXPosition() {
        return xPosition;
    }
    /**
     * Gets the y position of the bottle box.
     *
     * @return the y position of the bottle box
     */

    public double getYPosition() {
        return yPosition;
    }
    /**
     * Gets the width of the bottle box.
     *
     * @return the width of the bottle box
     */

    public double getWidth() {
        return width;
    }
    /**
     * Gets the height of the bottle box.
     *
     * @return the height of the bottle box
     */

    public double getHeight() {
        return height;
    }
}
