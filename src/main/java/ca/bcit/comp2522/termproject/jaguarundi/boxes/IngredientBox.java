package ca.bcit.comp2522.termproject.jaguarundi.boxes;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Collidable;
import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.Ingredient;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
/**
 * A box that holds ingredients.
 *
 * @author Vivian , Adam
 * @version 2023
 */
public class IngredientBox extends Interactable implements Collidable {
    /**
     * The width of the ingredient box.
     */
    public static final int INGREDIENT_BOX_WIDTH = 50;
    /**
     * The height of the ingredient box.
     */
    public static final int INGREDIENT_BOX_HEIGHT = 50;

    private final Ingredient ingredient;
    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private final Image sprite;

    /**
     * Constructs an ingredient box.
     *
     * @param ingredient the ingredient
     * @param sprite the sprite

     */
    public IngredientBox(final Ingredient ingredient, final Image sprite) {
        this.ingredient = ingredient;
        this.xPosition = 0;
        this.yPosition = 0;
        this.width = INGREDIENT_BOX_WIDTH;
        this.height = INGREDIENT_BOX_HEIGHT;
        this.sprite = sprite;
    }
    /**
     * Draws the ingredient box.
     *
     * @param gc the graphics context
     */

    public void draw(final GraphicsContext gc) {
        gc.setImageSmoothing(false);
        gc.drawImage(sprite, xPosition, yPosition, width, height);
    }
    /**
     * Gets the ingredient.
     *
     * @return the ingredient
     */

    public Ingredient getIngredient() {
        return ingredient;
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
