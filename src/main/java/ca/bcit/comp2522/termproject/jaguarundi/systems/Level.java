package ca.bcit.comp2522.termproject.jaguarundi.systems;

import ca.bcit.comp2522.termproject.jaguarundi.boxes.BottleBox;
import ca.bcit.comp2522.termproject.jaguarundi.boxes.IngredientBox;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.Bottle;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.Ingredient;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.Cauldron;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.Customer;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.TrashCan;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.*;

import static ca.bcit.comp2522.termproject.jaguarundi.systems.GameApp.playSound;
import static ca.bcit.comp2522.termproject.jaguarundi.systems.SaveLoadDialog.updateSaveFile;

/**
 * Level class to handle the level logic.
 *
 * @author Vivian, Adam
 * @version 2022
 */
public class Level {
    /**
     * The time it takes to transition to the next level.
     */
    public static final int TRANSITION_TIME = 3;

    /**
     * The maximum level index.
     */
    private static final int MAX_LEVEL_INDEX = 2;

    /**
     * The level complete banner.
     */
    public final static Image LEVEL_COMPLETE_BANNER = new Image(Objects.requireNonNull(Level.class.getResourceAsStream("level_complete.png")));

    /**
     * The game complete banner.
     */
    public final static Image GAME_COMPLETE_BANNER = new Image(Objects.requireNonNull(Level.class.getResourceAsStream("game_complete.png")));

    private final GameManager gameManager;
    private final Player player;
    private final BottleBox bottleBox;
    private TrashCan trashCan;
    private ArrayList<Cauldron> cauldrons;
    private ArrayList<IngredientBox> ingredientBoxes;
    private ArrayList<Customer> customers;
    private ArrayList<Wall> walls;
    private ArrayList<Customer> copyCustomers;
    private double transitionTimer;
    private boolean levelCompleted ;

    /**
     * Constructs a level.
     *
     * @param gameManager the game manager
     * @param player the player
     * @param bottleBox the bottle box
     * @param trashCan the trash can
     * @param cauldrons the cauldrons
     * @param ingredientBoxes the ingredient boxes
     * @param customers the customers
     * @param walls the walls
     */
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

    /**
     * Initializes the positions of the objects.
     *
     * @param cauldronPositions the cauldron positions
     * @param ingredientBoxPositions the ingredient box positions
     * @param customerPositions the customer positions
     * @param wallPositions the wall positions
     */
    public void initializeObjectPositions(
            double[][] cauldronPositions, double[][] ingredientBoxPositions,
            double[][] customerPositions, double[][] wallPositions) {
        for (int i = 0; i < cauldrons.size(); i++) {
            cauldrons.get(i).setXPosition(cauldronPositions[i][0]);
            cauldrons.get(i).setYPosition(cauldronPositions[i][1]);
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

    /**
     * Updates the level.
     *
     * @param delta the time
     */
    public void updateLevel(double delta) {
        // Update the game objects using delta time
        player.move(delta);
        player.animate();
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

    /**
     * Draws the level.
     *
     * @param gc the graphics context
     */
    public void drawLevel(GraphicsContext gc) {
        for (Cauldron cauldron : cauldrons) cauldron.draw(gc);
        bottleBox.draw(gc);
        for (IngredientBox ingredientBox : ingredientBoxes) ingredientBox.draw(gc);
        for (Wall wall : walls) wall.draw(gc);
        for (Customer customer : customers) customer.draw(gc);
        trashCan.draw(gc);
        player.draw(gc);

        if (levelCompleted) {
            if (gameManager.getCurrentLevelIndex() < MAX_LEVEL_INDEX) {
                gc.drawImage(LEVEL_COMPLETE_BANNER, 250, 150);
            } else {
                gc.drawImage(GAME_COMPLETE_BANNER, 250, 150);
            }
            gc.setFill(Color.BLACK);
            gc.setFont(javafx.scene.text.Font.font("Baskerville Old Face", 30));
            String rubiesText = String.valueOf(gameManager.getRubies());
            double rubiesX = 450;
            gc.fillText(rubiesText, rubiesX, 300);
        }
    }

    /**
     * Increments the transition timer.
     *
     * @param delta the time
     */
    public void incrementTransitionTimer(double delta) {
        if (transitionTimer < TRANSITION_TIME) {
            transitionTimer += delta;
        } else {
            levelCompleted = true;
        }
    }

    /**
     * Handles key presses.
     *
     * @param event the key event
     */
    public void handleKeyPress(KeyEvent event) {
        if (!levelCompleted) {
            KeyCode code = event.getCode();

            switch (code) {
                case W:
                    player.setYDirection(-1);
                    break;
                case S:
                    player.setYDirection(1);
                    break;
                case A:
                    player.setXDirection(-1);
                    break;
                case D:
                    player.setXDirection(1);
                    break;
                case E, SPACE:
                    handleInteractions();
                    break;
                default:
                    break;
            }
        } else {
            if (event.getCode() == KeyCode.ENTER && gameManager.getCurrentLevelIndex() < MAX_LEVEL_INDEX) {
                gameManager.advanceLevel();
                updateSaveFile(gameManager.getCurrentUser(), gameManager.getCurrentLevelIndex());
            }
        }
    }

    /**
     * Handles key releases.
     *
     * @param event the key event
     */
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

    /**
     * Handles interactions.
     */
    private void handleInteractions() {
        for (IngredientBox ingredientBox : ingredientBoxes) {
            interactWithIngredientBox(ingredientBox);
        }

        for (Cauldron cauldron : cauldrons) {
            interactWithCauldron(cauldron);
        }

        for (Customer customer : customers) {
            interactWithCustomer(customer);
        }

        interactWithBottleBox();
        interactWithTrashCan();
    }

    /**
     * Interacts with an ingredient box.
     *
     * @param ingredientBox the ingredient box
     */
    private void interactWithIngredientBox(IngredientBox ingredientBox) {
        if (player.isNearInteractable(ingredientBox)) {
            player.handleIngredient(ingredientBox);
            playSound("item_pickup.wav");
        }
    }

    /**
     * Interacts with a cauldron.
     *
     * @param cauldron the cauldron
     */
    private void interactWithCauldron(Cauldron cauldron) {
        if (player.isNearInteractable(cauldron)) {
            if (cauldron.getIngredient() == null && player.getInventory() instanceof Ingredient) {
                cauldron.addIngredient((Ingredient) player.getInventory());
                player.removeFromInventory();
                playSound("add_to_cauldron.wav");
            } else if (cauldron.getIngredient() != null &&
                    player.getInventory() instanceof Bottle &&
                    cauldron.getIngredient().getStage() == 1) {
                ((Bottle) player.getInventory()).addIngredient(cauldron.getIngredient());
                cauldron.removeIngredient();
                playSound("bottle_potion.wav");
            }
        }
    }

    /**
     * Interacts with a customer.
     *
     * @param customer the customer
     */
    private void interactWithCustomer(Customer customer) {
        if (customer.getPatience() < 100 && player.isNearInteractable(customer) && player.getInventory() instanceof Bottle) {
            this.verifyOrder(customer);
            player.removeFromInventory();
            copyCustomers.remove(customer);
        }
    }

    /**
     * Interacts with the bottle box.
     */
    private void interactWithBottleBox() {
        if (player.isNearInteractable(bottleBox)) {
            player.handleBottle();
            playSound("item_pickup.wav");
        }
    }

    /**
     * Interacts with the trash can.
     */
    private void interactWithTrashCan() {
        if (player.isNearInteractable(trashCan)) {
            player.removeFromInventory();
        }
    }

    /**
     * Verifies the order.
     *
     * @param customer the customer
     */
    public void verifyOrder(Customer customer) {
        Bottle playerBottle = (Bottle) player.getInventory();
        ArrayList<Ingredient> playerOrder = playerBottle.getIngredients();
        ArrayList<Ingredient> customerOrder = customer.getOrder();
        playerOrder.sort(Comparator.comparing(o -> o.getClass().getName()));
        customerOrder.sort(Comparator.comparing(o -> o.getClass().getName()));

        int correctCount = 0;

        for (int i = 0; i < Math.min(playerOrder.size(), customerOrder.size()); i++) {
            if (playerOrder.get(i).getClass() == customerOrder.get(i).getClass()) {
                correctCount++;
            }
        }
        if (playerOrder.size() > customerOrder.size()) {
            correctCount -= playerOrder.size() - customerOrder.size();
            if (correctCount < 0) {
                correctCount = 0;
            }
        }

        customer.setSatisfactionLevel(((double) correctCount / customerOrder.size()) * 100);
        gameManager.incrementRubies(customer.calculateRubies(correctCount));

        double satisfactionLevel = customer.getSatisfactionLevel();
        if (satisfactionLevel >= 66) {
            playSound("order_good.wav");
        } else if (satisfactionLevel >= 33) {
            playSound("order_mid.wav");
        } else {
            playSound("order_bad.wav");
        }
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return Double.compare(transitionTimer, level.transitionTimer) == 0 && levelCompleted == level.levelCompleted &&
                Objects.equals(gameManager, level.gameManager) && Objects.equals(player, level.player) &&
                Objects.equals(bottleBox, level.bottleBox) && Objects.equals(trashCan, level.trashCan) &&
                Objects.equals(cauldrons, level.cauldrons) && Objects.equals(ingredientBoxes, level.ingredientBoxes) &&
                Objects.equals(customers, level.customers) && Objects.equals(walls, level.walls) &&
                Objects.equals(copyCustomers, level.copyCustomers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameManager, player, bottleBox, trashCan, cauldrons, ingredientBoxes, customers, walls,
                copyCustomers, transitionTimer, levelCompleted);
    }

    @Override
    public String toString() {
        return "Level{" +
                "gameManager=" + gameManager +
                ", player=" + player +
                ", bottleBox=" + bottleBox +
                ", trashCan=" + trashCan +
                ", cauldrons=" + cauldrons +
                ", ingredientBoxes=" + ingredientBoxes +
                ", customers=" + customers +
                ", walls=" + walls +
                ", copyCustomers=" + copyCustomers +
                ", transitionTimer=" + transitionTimer +
                ", levelCompleted=" + levelCompleted +
                '}';
    }
}
