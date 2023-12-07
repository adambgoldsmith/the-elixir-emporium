package ca.bcit.comp2522.termproject.jaguarundi.holdables;

import java.util.ArrayList;

/**
 * A bottle that holds ingredients.
 *
 * @author Vivian , Adam
 * @version 2023
 */

public class Bottle extends Item {
    /**
     * The width of the bottle.
     */
    private final ArrayList<Ingredient> ingredients;
    /**
     * Constructs a bottle.
     */

    public Bottle() {
        this.ingredients = new ArrayList<>();
    }
    /**
     * Adds an ingredient to the bottle.
     *
     * @param ingredient the ingredient
     */

    public void addIngredient(final Ingredient ingredient) {
        this.ingredients.add(ingredient);
        System.out.println("Added ingredient to bottle");
    }
    /**
     * Gets the ingredients.
     *
     * @return the ingredients
     */

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
}
