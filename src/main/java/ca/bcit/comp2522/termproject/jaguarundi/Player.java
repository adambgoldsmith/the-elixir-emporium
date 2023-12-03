package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;

import java.util.Objects;

public class Player {
    // TODO: Add all sprites to a map
    private static final Map<String, Image> PLAYER_SPRITE_MAP = new HashMap<>();
    static {
        PLAYER_SPRITE_MAP.put("idle", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player.png"))));
        PLAYER_SPRITE_MAP.put("idle_back", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_back.png"))));
        PLAYER_SPRITE_MAP.put("idle_back_item", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_back_item.png"))));
        PLAYER_SPRITE_MAP.put("hogroot", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_hogroot.png"))));
        PLAYER_SPRITE_MAP.put("frostfern", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_frostfern_leaves.png"))));
        PLAYER_SPRITE_MAP.put("scorch radish", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_scorch_radish.png"))));
        PLAYER_SPRITE_MAP.put("cobalt compound", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_cobalt_compound.png"))));
        PLAYER_SPRITE_MAP.put("empty_bottle", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_empty_bottle.png"))));
        PLAYER_SPRITE_MAP.put("filled_bottle", new Image(Objects.requireNonNull(Player.class.getResourceAsStream("player_filled_bottle.png"))));
    }
    public final static int PLAYER_WIDTH = 50;
    public final static int PLAYER_HEIGHT = 50;

    private double speed;
    private double xPosition;
    private double yPosition;
    private double xDirection;
    private double yDirection;
    private double width;
    private double height;
    private Item inventory;
    private Image sprite;

    public Player(final int speed, final int xPosition, final int yPosition) {
        this.speed = speed;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = PLAYER_WIDTH;
        this.height = PLAYER_HEIGHT;
        this.inventory = null;
        this.sprite = PLAYER_SPRITE_MAP.get("idle");
    }

    public void draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);
        // TODO: rework these magic numbers
        gc.drawImage(sprite, xPosition, yPosition - 39, width, height + 39);
    }

    // TODO: rework this method
    public void animate() {
        if (this.yDirection == -1 && this.inventory != null) {
            this.sprite = PLAYER_SPRITE_MAP.get("idle_back_item");
        }
        else if (this.inventory != null && this.inventory instanceof Ingredient) {
            if (this.inventory instanceof Hogroot) {
                this.sprite = PLAYER_SPRITE_MAP.get("hogroot");
            } else if (this.inventory instanceof FrostfernLeaves) {
                this.sprite = PLAYER_SPRITE_MAP.get("frostfern");
            } else if (this.inventory instanceof ScorchRadish) {
                this.sprite = PLAYER_SPRITE_MAP.get("scorch radish");
            }
        } else if (this.inventory != null && this.inventory instanceof Bottle bottle) {
            if (bottle.getIngredients().isEmpty()) {
                this.sprite = PLAYER_SPRITE_MAP.get("empty_bottle");
            } else {
                this.sprite = PLAYER_SPRITE_MAP.get("filled_bottle");
            }
        } else {
            if (this.yDirection == -1) {
                this.sprite = PLAYER_SPRITE_MAP.get("idle_back");
            } else {
                this.sprite = PLAYER_SPRITE_MAP.get("idle");
            }
        }
    }

    public boolean isNearInteractable(Interactable interactable) {
        // Calculate the distance between the player and the interactable
        double playerX = this.getXPosition() + this.getWidth() / 2;
        double playerY = this.getYPosition() + this.getHeight() / 2;
        double interactableX = interactable.getXPosition() + (double) interactable.getWidth() / 2;
        double interactableY = interactable.getYPosition() + (double) interactable.getHeight() / 2;

        double distance = Math.sqrt(Math.pow(playerX - interactableX, 2) + Math.pow(playerY - interactableY, 2));

        // Define a threshold distance for "near"
        double threshold = 75.0; // Adjust this threshold as needed

        return distance <= threshold;
    }

    public void isCollidingWithCollidable(Collidable collidable) {
        double xOverlap = Math.max(0, Math.min(this.getXPosition() + this.getWidth(), collidable.getXPosition() + collidable.getWidth()) - Math.max(this.getXPosition(), collidable.getXPosition()));
        double yOverlap = Math.max(0, Math.min(this.getYPosition() + this.getHeight(), collidable.getYPosition() + collidable.getHeight()) - Math.max(this.getYPosition(), collidable.getYPosition()));

        if (xOverlap > 0 && yOverlap > 0) {
            if (xOverlap < yOverlap) {
                // Horizontal collision
                if (this.getXPosition() + this.getWidth() / 2 < collidable.getXPosition() + collidable.getWidth() / 2) {
                    this.setXPosition(collidable.getXPosition() - this.getWidth());
                } else {
                    this.setXPosition(collidable.getXPosition() + collidable.getWidth());
                }
            } else {
                // Vertical collision
                if (this.getYPosition() + this.getHeight() / 2 < collidable.getYPosition() + collidable.getHeight() / 2) {
                    this.setYPosition(collidable.getYPosition() - this.getHeight());
                } else {
                    this.setYPosition(collidable.getYPosition() + collidable.getHeight());
                }
            }
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

    public void handleIngredient(IngredientBox ingredientBox) {
        if (this.inventory == null) {
            if (ingredientBox.getClass() == HogrootBox.class) {
                this.inventory = new Hogroot();
            } else if (ingredientBox.getClass() == FrostfernLeavesBox.class) {
                this.inventory = new FrostfernLeaves();
            } else if (ingredientBox.getClass() == ScorchRadishBox.class) {
                this.inventory = new ScorchRadish();
            } else {
                System.out.println("Error: IngredientBox class not recognized");
            }
            System.out.println("Player picked up " + this.getInventory());
        } else if (ingredientBox.getIngredient().getClass() == this.inventory.getClass() &&
                getInventory() instanceof Ingredient &&
                ((Ingredient) this.inventory).getStage() == 0) {
            System.out.println("Player returned " + this.getInventory());
            removeFromInventory();
        }
    }

    public void handleBottle() {
        if (this.inventory == null) {
            this.inventory = new Bottle();
            System.out.println("Player picked up a bottle!");
        } else if (this.inventory.getClass() == Bottle.class &&
                ((Bottle) this.inventory).getIngredients().isEmpty()) {
            removeFromInventory();
            System.out.println("Player returned bottle");
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
                ", inventory=" + inventory +
                '}';
    }
}
