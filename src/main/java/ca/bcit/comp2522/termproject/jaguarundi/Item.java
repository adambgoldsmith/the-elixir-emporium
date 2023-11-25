package ca.bcit.comp2522.termproject.jaguarundi;

public abstract class Item {
    private final String name;

    public Item(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}