package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Cauldron extends Interactable implements Collidable {
    public final static int CAULDRON_WIDTH = 50;
    public final static int CAULDRON_HEIGHT = 50;
    public final static Color CAULDRON_COLOR = Color.DARKGRAY;
    public final static int CAULDRON_CAPACITY = 2;

    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private Color color;
    private ArrayList<Ingredient> ingredients;

    public Cauldron(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = CAULDRON_WIDTH;
        this.height = CAULDRON_HEIGHT;
        this.color = CAULDRON_COLOR;
        this.ingredients = new ArrayList<Ingredient>();
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(xPosition, yPosition, width, height);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(final Ingredient ingredient) {
        if (this.ingredients.size() < CAULDRON_CAPACITY) {
            this.ingredients.add(ingredient);
        }
    }

    public void boil() {
        for (Ingredient ingredient : this.ingredients) {
            ingredient.setStage(ingredient.getStage() + 1);
        }
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