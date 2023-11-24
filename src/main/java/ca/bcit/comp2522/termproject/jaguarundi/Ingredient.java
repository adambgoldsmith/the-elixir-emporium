package ca.bcit.comp2522.termproject.jaguarundi;

public class Ingredient {
    private String name;
    private int stage;

    public Ingredient(final String name) {
        this.name = name;
        this.stage = 0;
    }

    public String getName() {
        return name;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(final int stage) {
        this.stage = stage;
    }
}