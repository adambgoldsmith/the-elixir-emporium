package ca.bcit.comp2522.termproject.jaguarundi;
import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class Level {
    public static final int TRANSITION_TIME = 3;

    private GameManager gameManager;

    private Player player;
    private BottleBox bottleBox;
    private TrashCan trashCan;
    private ArrayList<Cauldron> cauldrons;
    private ArrayList<IngredientBox> ingredientBoxes;
    private ArrayList<Customer> customers;
    private ArrayList<Wall> walls;
    private ArrayList<Customer> copyCustomers;
    private double transitionTimer;
    private boolean levelCompleted ;

    public Level (GameManager gameManager, Player player, BottleBox bottleBox, TrashCan trashCan, ArrayList<Cauldron> cauldrons, ArrayList<IngredientBox> ingredientBoxes, ArrayList<Customer> customers, ArrayList<Wall> walls) {
        this.gameManager = gameManager;
        this.player = player;
        this.bottleBox = bottleBox;
        this.trashCan = trashCan;
        this.cauldrons = cauldrons;
        this.ingredientBoxes = ingredientBoxes;
        this.customers = customers;
        this.copyCustomers = new ArrayList<>(customers);
        this.walls = walls;
        this.transitionTimer = 0;
        this.levelCompleted = false;
    }

    public void initializeObjectPositions(
            double[][] cauldronPositions, double[][] ingredientBoxPositions,
            double[][] customerPositions, double[][] wallPositions) {
        for (int i = 0; i < cauldrons.size(); i++) {
            cauldrons.get(i).setXPosition(cauldronPositions[i][0]);
            cauldrons.get(i).setYPosition(cauldronPositions[i][1]);
            System.out.println(cauldrons.get(i).getXPosition());
        }
        for (int i = 0; i < ingredientBoxes.size(); i++) {
            ingredientBoxes.get(i).setXPosition(ingredientBoxPositions[i][0]);
            ingredientBoxes.get(i).setYPosition(ingredientBoxPositions[i][1]);
        }
        for (int i = 0; i < customers.size(); i++) {
            customers.get(i).setXPosition(customerPositions[i][0]);
            customers.get(i).setYPosition(customerPositions[i][1]);
        }
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).setXPosition(wallPositions[i][0]);
            walls.get(i).setYPosition(wallPositions[i][1]);
        }
    }

    public void updateLevel(double delta) {
        // Update the game objects using delta time
        player.move(delta);
        player.animate();
        player.isCollidingWithCollidable(trashCan);
        for (IngredientBox ingredientBox : ingredientBoxes) {
            player.isCollidingWithCollidable(ingredientBox);
        }
        player.isCollidingWithCollidable(bottleBox);
        for (Cauldron cauldron : cauldrons) {
            player.isCollidingWithCollidable(cauldron);
            cauldron.boil(delta);
            cauldron.animate();
        }
        for (Wall wall : walls) {
            player.isCollidingWithCollidable(wall);
        }
        for (Customer customer : customers) {
            customer.move(delta, copyCustomers);
            customer.incrementPatience(delta, copyCustomers);
        }

        if (copyCustomers.isEmpty()) {
            incrementTransitionTimer(delta);
        }
    }

    public void drawLevel(GraphicsContext gc) {
        for (Cauldron cauldron : cauldrons) {
            cauldron.draw(gc);
        }
        bottleBox.draw(gc);
        for (IngredientBox ingredientBox : ingredientBoxes) {
            ingredientBox.draw(gc);
        }
        for (Wall wall : walls) {
            wall.draw(gc);
        }
        for (Customer customer : customers) {
            customer.draw(gc);
        }
        trashCan.draw(gc);
        player.draw(gc);
        if (levelCompleted) {
            gc.fillRect(275, 150, 400, 250);
            gc.setFill(javafx.scene.paint.Color.WHITE);
            gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 40));
            gc.fillText("Level Completed!", 307, 195);
            gc.fillText("Rubies Earned:", 307, 270);
            gc.fillText("" + gameManager.getRubies(), 450, 340);
            gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 25));
            gc.fillText("Press 'Enter' to continue", 325, 375);
        }
    }

    public void incrementTransitionTimer(double delta) {
        if (transitionTimer < TRANSITION_TIME) {
            transitionTimer += delta;
        } else {
            levelCompleted = true;
        }
    }

    public void handleKeyPress(KeyEvent event) {
        if (!levelCompleted) {
            KeyCode code = event.getCode();
            // Handle key presses specific to this level
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
                handleInteractions(); // Handle interactions when the 'E' key is pressed
            }
        }
        else{
            if(event.getCode() == KeyCode.ENTER && gameManager.getCurrentLevelIndex() < 2){
                gameManager.advanceLevel();
                updateSaveFile(gameManager.getCurrentUser(), gameManager.getCurrentLevelIndex());
            }
        }
    }

    public void handleKeyRelease(KeyEvent event) {
        KeyCode code = event.getCode();
        // Handle key releases specific to this level
        if (code == KeyCode.W || code == KeyCode.S) {
            player.setYDirection(0);
        }
        if (code == KeyCode.A || code == KeyCode.D) {
            player.setXDirection(0);
        }
    }

    private void handleInteractions() {
        for (IngredientBox ingredientBox : ingredientBoxes) {
            if (player.isNearInteractable(ingredientBox)) {
                player.handleIngredient(ingredientBox);
            }
        }

        for (Cauldron cauldron : cauldrons) {
            if (player.isNearInteractable(cauldron)) {
                // TODO: Refactor this logic into the Cauldron class/beautify it.
                if (cauldron.getIngredient() == null &&
                        player.getInventory() != null &&
                        player.getInventory() instanceof Ingredient) {
                    cauldron.addIngredient((Ingredient) player.getInventory());
                    player.removeFromInventory();
                } else if (cauldron.getIngredient() != null &&
                        player.getInventory() != null &&
                        player.getInventory().getClass() == Bottle.class &&
                        cauldron.getIngredient().getStage() == 1) { // Change this?
                    Bottle bottle = (Bottle) player.getInventory();
                    bottle.addIngredient(cauldron.getIngredient());
                    cauldron.removeIngredient();
                }
            }
        }

        for (Customer customer : customers) {

            if (customer.getPatience() >= 100) {
                System.out.println("Customer has left. Skipping interaction.");
                continue;
            }
            if (player.isNearInteractable(customer)) {
                if (player.getInventory() != null && player.getInventory().getClass() == Bottle.class) {
                    if (verifyOrder(customer)) {
                        System.out.println("Correct order!");
                        player.removeFromInventory();
                    } else {
                        System.out.println("Incorrect order!");
                        player.removeFromInventory();
                    }
                    copyCustomers.remove(customer);
                }
            }
        }

        if (player.isNearInteractable(bottleBox)) {
            player.handleBottle();
        }
        if(player.isNearInteractable(trashCan)){
            player.removeFromInventory();
        }
    }

    public boolean verifyOrder(Customer customer) {
        Bottle playerBottle = (Bottle) player.getInventory();
        ArrayList<Ingredient> playerOrder = playerBottle.getIngredients();
        ArrayList<Ingredient> customerOrder = customer.getOrder();
        System.out.println(playerOrder);
        System.out.println(customerOrder);


        int correctCount = 0;

        // TODO: fix this so it only counts an ingredient once.
        for (Ingredient playerIngredient : playerOrder) {
            for (Ingredient customerIngredient : customerOrder) {
                if (playerIngredient.getClass() == customerIngredient.getClass()) {
                    correctCount++;
                    break;
                }
            }
        }

        customer.setSatisfactionLevel(((double) correctCount / customerOrder.size()) * 100);
        gameManager.incrementRubies(customer.calculateRubies(correctCount));
        return correctCount > 0;
    }

    public Player getPlayer() {
        return player;
    }
}
