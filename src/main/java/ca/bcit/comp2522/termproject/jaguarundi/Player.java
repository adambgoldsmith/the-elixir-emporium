package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    public final static int PLAYER_WIDTH = 50;
    public final static int PLAYER_HEIGHT = 50;
    public final static Color PLAYER_COLOR = Color.BLUE;
    public final static int INVENTORY_SIZE = 1;

    private int speed;
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private Color color;
    private Bottle bottle;
    private ArrayList<Ingredient> inventory;

    public Player(final int speed, final int xPosition, final int yPosition) {
        this.speed = speed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.color = PLAYER_COLOR;
        this.inventory = new ArrayList<Ingredient>();
        this.bottle = null;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(xPosition, yPosition, width, height);
    }

    public double getSpeed() {
        return speed;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Ingredient> getInventory() {
        return inventory;
    }

    public void move(int x, int y) {
        // TODO: Check for keyboard input
        xPosition += x * speed;
        yPosition += y * speed;
    }

    public void addIngredientToInventory(final Ingredient ingredient) {
        if (this.inventory.size() < INVENTORY_SIZE) {
            this.inventory.add(ingredient);
        }
    }

    public void pickUpBottle(final Bottle bottle) {
        if (this.bottle == null) {
            this.bottle = bottle;
            System.out.println("Player picked up a bottle!");
        }
    }

    public void fillBottle(final Ingredient firsIngredient, final Ingredient secondIngredient) {
        if (this.bottle != null) {
            this.bottle.fill(firsIngredient, secondIngredient);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "speed=" + speed +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                ", width=" + width +
                ", height=" + height +
                ", color=" + color +
                ", bottle=" + bottle +
                ", inventory=" + inventory +
                '}';
    }
}
