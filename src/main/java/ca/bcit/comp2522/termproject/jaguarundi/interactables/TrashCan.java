package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.boxes.BottleBox;
import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class TrashCan extends Interactable {
    public final static Image TRASHCAN_SPRITE = new Image(Objects.requireNonNull(TrashCan.class.getResourceAsStream("TrashCan.png")));

    public static final int TRASH_BOX_WIDTH = 50;
    public static final int TRASH_BOX_HEIGHT = 50;

    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;



    public TrashCan(final double xPosition, final double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = TRASH_BOX_WIDTH;
        this.height = TRASH_BOX_HEIGHT;
    }

    public void draw(final GraphicsContext gc) {gc.drawImage(TRASHCAN_SPRITE, xPosition, yPosition);}

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

