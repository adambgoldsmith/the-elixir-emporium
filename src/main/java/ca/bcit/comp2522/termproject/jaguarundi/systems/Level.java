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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import static ca.bcit.comp2522.termproject.jaguarundi.systems.SaveLoadDialog.updateSaveFile;

public class Level {
    public static final int TRANSITION_TIME = 3;
    private static final int MAX_LEVEL_INDEX = 2;
    public final static Image LEVEL_COMPLETE_BANNER = new Image(Objects.requireNonNull(Level.class.getResourceAsStream("level_complete.png")));
    public final static Image GAME_COMPLETE_BANNER = new Image(Objects.requireNonNull(Level.class.getResourceAsStream("game_complete.png")));

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
            double rubiesTextWidth = rubiesText.length() * 15;

            double rubiesX = 450 - rubiesTextWidth / 2;
            gc.fillText(rubiesText, rubiesX, 300);
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
                case E:
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

    private void interactWithIngredientBox(IngredientBox ingredientBox) {
        if (player.isNearInteractable(ingredientBox)) {
            player.handleIngredient(ingredientBox);
        }
    }

    private void interactWithCauldron(Cauldron cauldron) {
        if (player.isNearInteractable(cauldron)) {
            if (cauldron.getIngredient() == null && player.getInventory() instanceof Ingredient) {
                cauldron.addIngredient((Ingredient) player.getInventory());
                player.removeFromInventory();
            } else if (cauldron.getIngredient() != null &&
                    player.getInventory() instanceof Bottle &&
                    cauldron.getIngredient().getStage() == 1) {
                ((Bottle) player.getInventory()).addIngredient(cauldron.getIngredient());
                cauldron.removeIngredient();
            }
        }
    }

    private void interactWithCustomer(Customer customer) {
        if (customer.getPatience() < 100 && player.isNearInteractable(customer) && player.getInventory() instanceof Bottle) {
            this.verifyOrder(customer);
            player.removeFromInventory();
            copyCustomers.remove(customer);
        }
    }


    private void interactWithBottleBox() {
        if (player.isNearInteractable(bottleBox)) {
            player.handleBottle();
        }
    }

    private void interactWithTrashCan() {
        if (player.isNearInteractable(trashCan)) {
            player.removeFromInventory();
        }
    }

    public void verifyOrder(Customer customer) {
        Bottle playerBottle = (Bottle) player.getInventory();
        List<Ingredient> playerOrder = playerBottle.getIngredients();
        List<Ingredient> customerOrder = customer.getOrder();
        System.out.println(playerOrder);
        System.out.println(customerOrder);

        Set<Class<? extends Ingredient>> uniquePlayerIngredients = playerOrder.stream()
                .map(Ingredient::getClass)
                .collect(Collectors.toSet());

        long correctCount = customerOrder.stream()
                .map(Ingredient::getClass)
                .filter(uniquePlayerIngredients::contains)
                .count();

        long extraIngredientsCount = Math.max(0, playerOrder.size() - customerOrder.size());

        long adjustedCorrectCount = Math.max(0, correctCount - extraIngredientsCount);

        double satisfactionLevel = (adjustedCorrectCount / (double) customerOrder.size()) * 100;
        customer.setSatisfactionLevel(satisfactionLevel);

        gameManager.incrementRubies(customer.calculateRubies((int) adjustedCorrectCount));
    }


    public Player getPlayer() {
        return player;
    }
}
