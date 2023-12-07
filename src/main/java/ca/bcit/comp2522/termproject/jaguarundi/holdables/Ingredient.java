package ca.bcit.comp2522.termproject.jaguarundi.holdables;
/**
 * An ingredient.
 *
 * @author Vivian , Adam
 * @version 2023
 */


public class Ingredient extends Item {
    /**
     * The stage of the ingredient.
     */
    private int stage;
    /**
     * Constructs an ingredient.
     */

    public Ingredient() {
        this.stage = 0;
    }
    /**
     * Gets the stage of the ingredient.
     * @return the stage
     */

    public int getStage() {
        return stage;
    }
    /**
     * Sets the stage of the ingredient.
     * @param stage the stage
     */

    public void setStage(final int stage) {
        this.stage = stage;
    }
}
