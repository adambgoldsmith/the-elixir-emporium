package ca.bcit.comp2522.termproject.jaguarundi;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Level {
    private GameManager gameManager;

    private Player player;
    private BottleBox bottleBox;
    private TrashCan trashCan;
    private ArrayList<Cauldron> cauldrons;
    private ArrayList<IngredientBox> ingredientBoxes;
    private ArrayList<Customer> customers;
    private ArrayList<Wall> walls;

    private ArrayList<Customer> copyCustomers;

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
    }
