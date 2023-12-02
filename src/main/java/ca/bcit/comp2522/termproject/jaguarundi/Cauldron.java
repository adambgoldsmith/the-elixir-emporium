package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Cauldron extends Interactable implements Collidable {
    public final static int CAULDRON_WIDTH = 50;
    public final static int CAULDRON_HEIGHT = 50;
    public final static Color CAULDRON_COLOR = Color.DARKGRAY;
    public final static double BOIL_TIME = 5.0;

    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private Color color;
    private Ingredient ingredient;
    private boolean isBoiling;
    private double timer;

    public Cauldron(final int xPosition, final int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = CAULDRON_WIDTH;
        this.height = CAULDRON_HEIGHT;
        this.color = CAULDRON_COLOR;
        this.ingredient = null;
        this.isBoiling = false;
        this.timer = 0.0;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(xPosition, yPosition, width, height);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void addIngredient(final Ingredient ingredient) {
        if (this.ingredient == null) {
            this.ingredient = ingredient;
            System.out.println("Added ingredient to cauldron");
            this.isBoiling = true;
            System.out.println("Started boiling cauldron");
        }
    }

    public void boil(double delta) {
        if (this.isBoiling && this.timer < BOIL_TIME) {
            this.timer += delta;
        } else if (this.isBoiling && this.timer >= BOIL_TIME) {
            this.isBoiling = false;
            this.timer = 0.0;
            System.out.println("Finished boiling cauldron");
            // create an increment method in Ingredient?
//            this.ingredient.setStage(this.ingredient.getStage() + 1);
        }
    }

    public void removeIngredient() {
        this.ingredient = null;
        System.out.println("Removed ingredient from cauldron");
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