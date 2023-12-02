package ca.bcit.comp2522.termproject.jaguarundi;

public class Ingredient extends Item {
    private int stage;

    public Ingredient() {
        this.stage = 0;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(final int stage) {
        this.stage = stage;
    }
}