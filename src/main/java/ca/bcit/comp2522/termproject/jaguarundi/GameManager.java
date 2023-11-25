package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class GameManager {
    private Player player;
    private Cauldron cauldron;
    private BottleBox bottleBox;
    private IngredientBox ingredientBox;
    private ArrayList<Customer> customers;
    private int rubies;
    private double timeRemaining;
    private int day;

    public GameManager() {
        this.player = new Player(10, 0, 0);
        this.cauldron = new Cauldron(75, 376);
        this.bottleBox = new BottleBox(156, 230);
        this.ingredientBox = new IngredientBox(423, 26);
        this.customers = new ArrayList<>();
        this.rubies = 0;
        this.timeRemaining = 0.0;
        this.day = 0;
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        // Handle key presses
        if (code == KeyCode.W) {
            player.move(0, -1); // Move up
        } else if (code == KeyCode.A) {
            player.move(-1, 0); // Move left
        } else if (code == KeyCode.S) {
            player.move(0, 1); // Move down
        } else if (code == KeyCode.D) {
            player.move(1, 0); // Move right
        } else if (code == KeyCode.E) {
            if (isPlayerNearInteractable(bottleBox)) {
                player.pickUpBottle(new Bottle());
            } else if (isPlayerNearInteractable(ingredientBox)) {
//                player.pickUpIngredient(new Ingredient());
                System.out.println("Picked up ingredient");
            } else if (isPlayerNearInteractable(cauldron)) {
//                player.addIngredientToCauldron(cauldron);
                System.out.println("Added ingredient to cauldron");
            } else {
                System.out.println("Nothing to interact with");
            }
        }


    }

    public void update(double elapsedTime) {
        // Update the game objects
        isPlayerCollidingWithCollidable(ingredientBox);
    }

    public void drawObjects(GraphicsContext gc) {
        player.draw(gc);
        cauldron.draw(gc);
        bottleBox.draw(gc);
        ingredientBox.draw(gc);
        for (Customer customer : customers) {
            customer.draw(gc);
        }
    }

    public boolean isPlayerNearInteractable(Interactable interactable) {
        // Calculate the distance between the player and the interactable
        double playerX = player.getXPosition() + (double) player.getWidth() / 2;
        double playerY = player.getYPosition() + (double) player.getHeight() / 2;
        double interactableX = interactable.getXPosition() + (double) interactable.getWidth() / 2;
        double interactableY = interactable.getYPosition() + (double) interactable.getHeight() / 2;

        double distance = Math.sqrt(Math.pow(playerX - interactableX, 2) + Math.pow(playerY - interactableY, 2));

        // Define a threshold distance for "near"
        double threshold = 75.0; // Adjust this threshold as needed

        return distance <= threshold;
    }

    public void isPlayerCollidingWithCollidable(Collidable collidable) {
        // Calculate the distance between the player and the interactable
        double playerX = player.getXPosition() + (double) player.getWidth() / 2;
        double playerY = player.getYPosition() + (double) player.getHeight() / 2;
        double collidableX = collidable.getXPosition() + (double) collidable.getWidth() / 2;
        double collidableY = collidable.getYPosition() + (double) collidable.getHeight() / 2;

        double distance = Math.sqrt(Math.pow(playerX - collidableX, 2) + Math.pow(playerY - collidableY, 2));

        if (distance < 50) {
            if (player.getXPosition() + player.getWidth() > collidable.getXPosition()) {
                System.out.println("Left collided! ");
                if (player.getXPosition() < collidable.getXPosition() + collidable.getWidth()) {
                    System.out.println("Right collided! ");
                } else if (player.getYPosition() + player.getHeight() > collidable.getYPosition()) {
                    System.out.println("Top collided! ");
                } else if (player.getYPosition() < collidable.getYPosition() + collidable.getHeight()) {
                    System.out.println("Bottom collided! ");
                }
            } else {
                System.out.println("Not collided! ");
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Cauldron getCauldron() {
        return cauldron;
    }

    public BottleBox getBottleBox() {
        return bottleBox;
    }

    public IngredientBox getIngredientBox() {
        return ingredientBox;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public int getRubies() {
        return rubies;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public int getDay() {
        return day;
    }
}
