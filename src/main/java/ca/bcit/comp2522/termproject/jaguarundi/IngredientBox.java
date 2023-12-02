package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class IngredientBox extends Interactable implements Collidable {
    public static final int INGREDIENT_BOX_WIDTH = 50;
    public static final int INGREDIENT_BOX_HEIGHT = 50;

    private Ingredient ingredient;
    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private Image sprite;


    public IngredientBox(final Ingredient ingredient, final Image sprite) {
        this.ingredient = ingredient;
        this.xPosition = 0;
        this.yPosition = 0;
        this.width = INGREDIENT_BOX_WIDTH;
        this.height = INGREDIENT_BOX_HEIGHT;
        this.sprite = sprite;
    }

    public void draw(final GraphicsContext gc) {
        gc.setImageSmoothing(false);
        gc.drawImage(sprite, xPosition, yPosition, width, height);
    }

    public Ingredient getIngredient() {
        return ingredient;
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
