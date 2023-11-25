package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Customer {
    public final static int CUSTOMER_WIDTH = 50;
    public final static int CUSTOMER_HEIGHT = 50;
    public final static Color CUSTOMER_COLOR = Color.RED;

    private double speed;
    private int patience;
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private Color color;
    private ArrayList<Ingredient> order;

    public Customer(final double speed, final int patience, final int xPosition, final int yPosition) {
        this.speed = speed;
        this.patience = patience;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = CUSTOMER_WIDTH;
        this.height = CUSTOMER_HEIGHT;
        this.color = CUSTOMER_COLOR;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(xPosition, yPosition, width, height);
    }

    public double getSpeed() {
        return speed;
    }

    public int getPatience() {
        return patience;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void verifyOrder(final Bottle bottle) {
        if (this.order != null) {
            if (this.order.get(0).getName() == bottle.getIngredients().get(0).getName() &&
                    this.order.get(1).getName() == bottle.getIngredients().get(1).getName()) {
                System.out.println("Correct order!");
            } else {
                System.out.println("Wrong order!");
            }
        }
    }
}
