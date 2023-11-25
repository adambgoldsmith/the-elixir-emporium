package ca.bcit.comp2522.termproject.jaguarundi;

import java.util.ArrayList;

public class Bottle {
    private ArrayList<Ingredient> ingredients;

    public Bottle() {
        this.ingredients = new ArrayList<>();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void fill(final Ingredient firsIngredient, final Ingredient secondIngredient) {
        this.ingredients.add(firsIngredient);
        this.ingredients.add(secondIngredient);
    }
}