package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class IngredientBox extends Interactable implements Collidable {
    public static final int INGREDIENT_BOX_WIDTH = 50;
    public static final int INGREDIENT_BOX_HEIGHT = 50;
    public static final Color INGREDIENT_BOX_COLOR = Color.DARKORANGE;

    private final int xPosition;
    private final int yPosition;
    private final int width;
    private final int height;
    private final Color color;


    public IngredientBox(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = INGREDIENT_BOX_WIDTH;
        this.height = INGREDIENT_BOX_HEIGHT;
        this.color = INGREDIENT_BOX_COLOR;
    }

    public void draw(final GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(xPosition, yPosition, width, height);
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
