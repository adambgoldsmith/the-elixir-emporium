package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * TrashCan class for players to dispose of items.
 *
 * @author Adam , Vivian
 * @version 2023
 */
public class TrashCan extends Interactable {

    /**
     * TrashCan sprite.
     */
    public final static Image TRASHCAN_SPRITE = new Image(Objects.requireNonNull(TrashCan.class.getResourceAsStream("TrashCan.png")));

    /**
     * TrashCan width.
     */
    public static final int TRASH_BOX_WIDTH = 50;

    /**
     * TrashCan height.
     */
    public static final int TRASH_BOX_HEIGHT = 50;

    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;

    /**
     * Constructs a TrashCan object.
     *
     * @param xPosition the x position
     * @param yPosition the y position
     */
    public TrashCan(final double xPosition, final double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = TRASH_BOX_WIDTH;
        this.height = TRASH_BOX_HEIGHT;
    }

    /**
     * Draws the TrashCan.
     *
     * @param gc the graphics context
     */
    public void draw(final GraphicsContext gc) {gc.drawImage(TRASHCAN_SPRITE, xPosition, yPosition);}

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

