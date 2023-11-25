package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class GameManager {
    private long lastUpdateTime = 0;

    private Player player;
    private Cauldron cauldron;
    private BottleBox bottleBox;
    private IngredientBox ingredientBox;
    private ArrayList<Customer> customers;
    private ArrayList<Wall> walls;
    private int rubies;
    private double timeRemaining;
    private int day;

    public GameManager() {
        this.player = new Player(200, 500, 500);
        this.cauldron = new Cauldron(75, 376);
        this.bottleBox = new BottleBox(156, 230);
        this.ingredientBox = new IngredientBox(423, 26);
        this.customers = new ArrayList<>();
        this.walls = new ArrayList<>();
        spawnWalls();
        this.rubies = 0;
        this.timeRemaining = 0.0;
        this.day = 0;
    }

    public void handleKeyPress(KeyEvent event) {
        KeyCode code = event.getCode();
        // Handle key presses
        if (code == KeyCode.W) {
            player.setYDirection(-1);
        } else if (code == KeyCode.S) {
            player.setYDirection(1);
        }
        if (code == KeyCode.A) {
            player.setXDirection(-1);
        } else if (code == KeyCode.D) {
            player.setXDirection(1);
        }

        if (code == KeyCode.E) {
            if (isPlayerNearInteractable(bottleBox)) {
                pickBottle();
            } else if (isPlayerNearInteractable(ingredientBox)) {
                pickIngredient();
            } else if (isPlayerNearInteractable(cauldron)) {
                // * Is there a better way to do this? VV
                if (cauldron.getIngredient() == null &&
                        player.getInventory() != null &&
                        player.getInventory().getClass() == Ingredient.class) {
                    cauldron.addIngredient((Ingredient) player.getInventory());
                    player.removeFromInventory();
                } else if (cauldron.getIngredient() != null &&
                        player.getInventory() != null &&
                        player.getInventory().getClass() == Bottle.class) {
                    Bottle bottle = (Bottle) player.getInventory();
                    bottle.addIngredient(cauldron.getIngredient());
                    cauldron.removeIngredient();
                }
            } else {
                System.out.println("Nothing to interact with");
            }
        }

        if (code == KeyCode.I) {
            displayInventory();
        }
    }

    public void handleKeyRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        // Handle key releases
        if (code == KeyCode.W) {
            player.setYDirection(0);
        } else if (code == KeyCode.S) {
            player.setYDirection(0);
        }
        if (code == KeyCode.A) {
            player.setXDirection(0);
        } else if (code == KeyCode.D) {
            player.setXDirection(0);
        }
    }

    public void update(double elapsedTime) {
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.nanoTime();
        }
        long currentTime = System.nanoTime();
        double delta = (currentTime - lastUpdateTime) / 1e9;
        lastUpdateTime = currentTime;

        // Update the game objects using delta time
        player.move(delta);
        player.animate();
        isPlayerCollidingWithCollidable(ingredientBox);
        isPlayerCollidingWithCollidable(bottleBox);
        isPlayerCollidingWithCollidable(cauldron);
        for (Wall wall : walls) {
            isPlayerCollidingWithCollidable(wall);
        }
        cauldron.boil(delta);
    }

    public void drawObjects(GraphicsContext gc) {
        player.draw(gc);
        cauldron.draw(gc);
        bottleBox.draw(gc);
        ingredientBox.draw(gc);
        for (Customer customer : customers) {
            customer.draw(gc);
        }
        for (Wall wall : walls) {
            wall.draw(gc);
        }
    }

    public void spawnWalls() {
        walls.add(new Wall(0, 0, 800, 25));
        walls.add(new Wall(0, 0, 25, 600));
        walls.add(new Wall(0, 575, 800, 25));
        walls.add(new Wall(775, 0, 25, 600));
    }

    public boolean isPlayerNearInteractable(Interactable interactable) {
        // Calculate the distance between the player and the interactable
        double playerX = player.getXPosition() + player.getWidth() / 2;
        double playerY = player.getYPosition() + player.getHeight() / 2;
        double interactableX = interactable.getXPosition() + (double) interactable.getWidth() / 2;
        double interactableY = interactable.getYPosition() + (double) interactable.getHeight() / 2;

        double distance = Math.sqrt(Math.pow(playerX - interactableX, 2) + Math.pow(playerY - interactableY, 2));

        // Define a threshold distance for "near"
        double threshold = 75.0; // Adjust this threshold as needed

        return distance <= threshold;
    }

    public void isPlayerCollidingWithCollidable(Collidable collidable) {
        double xOverlap = Math.max(0, Math.min(player.getXPosition() + player.getWidth(), collidable.getXPosition() + collidable.getWidth()) - Math.max(player.getXPosition(), collidable.getXPosition()));
        double yOverlap = Math.max(0, Math.min(player.getYPosition() + player.getHeight(), collidable.getYPosition() + collidable.getHeight()) - Math.max(player.getYPosition(), collidable.getYPosition()));

        if (xOverlap > 0 && yOverlap > 0) {
            if (xOverlap < yOverlap) {
                // Horizontal collision
                if (player.getXPosition() + player.getWidth() / 2 < collidable.getXPosition() + collidable.getWidth() / 2) {
                    player.setXPosition(collidable.getXPosition() - player.getWidth());
                } else {
                    player.setXPosition(collidable.getXPosition() + collidable.getWidth());
                }
            } else {
                // Vertical collision
                if (player.getYPosition() + player.getHeight() / 2 < collidable.getYPosition() + collidable.getHeight() / 2) {
                    player.setYPosition(collidable.getYPosition() - player.getHeight());
                } else {
                    player.setYPosition(collidable.getYPosition() + collidable.getHeight());
                }
            }
        }
    }

    public void pickBottle() {
        if (player.getInventory() != null && player.getInventory().getClass() == Bottle.class&& ((Bottle) player.getInventory()).getIngredients().isEmpty()) {
            player.removeFromInventory();
        }
        else{
            player.pickUpBottle(new Bottle());
        }
    }

    public void pickIngredient() {
        if (player.getInventory() != null && player.getInventory().getClass() == Ingredient.class) {
            player.removeFromInventory();
        }
        else{
            player.pickUpIngredient(new Ingredient("Test Ingredient"));
        }
    }

    public void displayInventory() {
        if (player.getInventory() != null) {
            System.out.println(player.getInventory().getName());
        } else {
            System.out.println("Inventory is empty");
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
}}