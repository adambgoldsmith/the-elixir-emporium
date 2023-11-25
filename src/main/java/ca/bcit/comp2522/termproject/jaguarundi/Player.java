package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.util.Objects;

public class Player {
    // TODO: Add all sprites to a map
    public final static String PLAYER_SPRITE_PATH = "TestSprite.png";
    public final static String PLAYER_SPRITE_PATH_INGREDIENT = "player_purple_ingredient.png";
    public final static String PLAYER_SPRITE_PATH_EMPTY_BOTTLE = "player_empty_bottle.png";
    public final static String PLAYER_SPRITE_PATH_FILLED_BOTTLE= "player_filled_bottle.png";
    public final static int PLAYER_WIDTH = 50;
    public final static int PLAYER_HEIGHT = 50;
    public final static Color PLAYER_COLOR = Color.BLUE;

    private double speed;
    private double xPosition;
    private double yPosition;
    private double xDirection;
    private double yDirection;
    private double width;
    private double height;
    private Color color;
    private Item inventory;
    private Image sprite;

    public Player(final int speed, final int xPosition, final int yPosition) {
        this.speed = speed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.color = PLAYER_COLOR;
        this.inventory = null;
        this.sprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PLAYER_SPRITE_PATH)));
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(sprite, xPosition, yPosition, width, height);
    }

    // TODO: rework this method
    public void animate() {
        if (this.inventory != null && this.inventory.getClass() == Ingredient.class) {
            this.sprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PLAYER_SPRITE_PATH_INGREDIENT)));
        } else if (this.inventory != null && this.inventory.getClass() == Bottle.class) {
            Bottle bottle = (Bottle) this.inventory;
            if (bottle.getIngredients().isEmpty()) {
                this.sprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PLAYER_SPRITE_PATH_EMPTY_BOTTLE)));
            } else {
                this.sprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PLAYER_SPRITE_PATH_FILLED_BOTTLE)));
            }
        } else {
            this.sprite = new Image(Objects.requireNonNull(getClass().getResourceAsStream(PLAYER_SPRITE_PATH)));
        }
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setXDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public void setYDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Item getInventory() {
        return inventory;
    }

    public void move(double delta) {
        if (xDirection == 1) {
            xPosition += speed * delta;
        } else if (xDirection == -1) {
            xPosition -= speed * delta;
        }
        if (yDirection == 1) {
            yPosition += speed * delta;
        } else if (yDirection == -1) {
            yPosition -= speed * delta;
        }
    }

    public void pickUpIngredient(final Ingredient ingredient) {
        if (this.inventory == null) {
            this.inventory = ingredient;
            System.out.println("Player picked up an ingredient!");
        }
    }

    public void pickUpBottle(final Bottle bottle) {
        if (this.inventory == null) {
            this.inventory = bottle;
            System.out.println("Player picked up a bottle!");
        }
    }

    public void removeFromInventory() {
        this.inventory = null;
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
                ", inventory=" + inventory +
                '}';
    }
}