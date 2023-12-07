package ca.bcit.comp2522.termproject.jaguarundi.systems;

import ca.bcit.comp2522.termproject.jaguarundi.boxes.*;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.Item;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.Cauldron;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.Customer;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.TrashCan;
import ca.bcit.comp2522.termproject.jaguarundi.interactables.Wall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Objects;

public class GameManager {
    private ArrayList<Level> levels;
    private int currentLevelIndex;
    private int rubies;
    private Text inventoryText;
    private String currentUser;
    private long lastUpdateTime;

    public GameManager() {
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.rubies = 0;
        this.inventoryText = new Text();
        this.currentUser = "";
        this.lastUpdateTime = 0;
        generateLevels();
        initializeInventoryText();
    }

    public void generateLevels() {
        Level level1 = new Level(
                this,
                new Player(300, 400, 400),
                new BottleBox(450, 250),
                new TrashCan(500, 50),
                createCauldrons(3),
                createIngredientBoxes(1),
                createCustomers(5, 1),
                createWalls(0)
        );
        level1.initializeObjectPositions(
                new double[][]{{600, 150}, {300, 350}, {300, 150}},
                new double[][]{{200, 50}, {700, 450}, {700, 50}},
                new double[][]{{100, 550}, {100,550}, {100, 550}, {100,550}, {100, 550}},
                new double[][]{{50, 0}, {0, 500}, {0, 0}, {750, 0}, {150, 50}}
        );
        levels.add(level1);
        Level level2 = new Level(
                this,
                new Player(300, 400, 400),
                new BottleBox(200, 50),
                new TrashCan(700, 50),
                createCauldrons(4),
                createIngredientBoxes(2),
                createCustomers(5, 2),
                createWalls(9)
        );
        level2.initializeObjectPositions(
                new double[][]{{400, 150}, {400, 350}, {300, 250}, {500, 250}},
                new double[][]{{350, 50}, {700, 250}, {700, 350}, {300, 450}},
                new double[][]{{100, 550}, {100,550}, {100, 550}, {100,550}, {100, 550}},
                new double[][]{{50, 0}, {0, 500}, {0, 0}, {750, 0}, {150, 50,},
                        {300, 50}, {300, 100}, {350, 100}, {400, 100}, {450, 100},
                        {600, 300}, {600, 350}, {650, 300}, {700, 300}}
        );
        levels.add(level2);
        Level level3 = new Level(
                this,
                new Player(300, 400, 400),
                new BottleBox(700, 50),
                new TrashCan(650, 300),
                createCauldrons(5),
                createIngredientBoxes(3),
                createCustomers(5, 3),
                createWalls(17)
        );
        level3.initializeObjectPositions(
                new double[][]{{300, 150}, {400, 50}, {450, 200}, {500, 50}, {600, 150}},
                new double[][]{{200, 50}, {300, 450}, {400, 450}, {700, 450}, {550, 300}},
                new double[][]{{100, 550}, {100,550}, {100, 550}, {100,550}, {100, 550}},
                new double[][]{{50, 0}, {0, 500}, {0, 0}, {750, 0}, {150, 50},
                        {250, 50}, {250, 100}, {250, 150},
                        {450, 50}, {450, 100}, {450, 150},
                        {350, 400}, {350, 450},
                        {650, 50}, {650, 100}, {650, 150}, {650, 200}, {650, 250},
                        {600, 250}, {600, 300}, {600, 350}, {550, 350}}
        );
        levels.add(level3);
    }

    public ArrayList<Cauldron> createCauldrons(int quantity) {
        ArrayList<Cauldron> cauldrons = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            cauldrons.add(new Cauldron());
        }
        return cauldrons;
    }

    public ArrayList<IngredientBox> createIngredientBoxes(int level) {
        ArrayList<IngredientBox> ingredientBoxes = new ArrayList<>();
        ingredientBoxes.add(new HogrootBox());
        ingredientBoxes.add(new FrostfernLeavesBox());
        ingredientBoxes.add(new ScorchRadishBox());
        if (level == 2) {
            ingredientBoxes.add(new CobaltCompoundBox());
        }
        if (level == 3) {
            ingredientBoxes.add(new CobaltCompoundBox());
            ingredientBoxes.add(new FluorescentEggBox());
        }
        return ingredientBoxes;
    }

    public ArrayList<Customer> createCustomers(int quantity, int level) {
        ArrayList<Customer> customers = new ArrayList<>();
        // TODO: Add more customers
        for (int i = 0; i < quantity; i++) {
            customers.add(new Customer(200, 50, level));
        }
        return customers;
    }

    public ArrayList<Wall> createWalls(int quantity) {
        ArrayList<Wall> walls = new ArrayList<>();
        walls.add(new Wall(700, 50, "top"));
        walls.add(new Wall(800, 50, "bottom"));
        walls.add(new Wall(50, 500, "side"));
        walls.add(new Wall(50, 500, "side"));
        walls.add(new Wall(50, 450, "counter"));

        for (int i = 0; i < quantity; i++) {
            walls.add(new Wall(50, 50, "side"));
        }
        return walls;
    }

    public void registerKeyPress(KeyEvent event) {
        // Hand off key presses to the current level
        levels.get(currentLevelIndex).handleKeyPress(event);
    }

    public void registerKeyRelease(KeyEvent event) {
        // Hand off key releases to the current level
        levels.get(currentLevelIndex).handleKeyRelease(event);
    }

    public void update() {
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.nanoTime();
        }
        long currentTime = System.nanoTime();
        double delta = (currentTime - lastUpdateTime) / 1e9;
        lastUpdateTime = currentTime;

        // Call updateLevel of the current level
        levels.get(currentLevelIndex).updateLevel(delta);
    }

    public void drawObjects(GraphicsContext gc) {
        // Draw side panel
        gc.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 15));
        drawSidePanel(gc);

        // Call drawLevel of the current level
        gc.setFill(Color.BLACK);
        levels.get(currentLevelIndex).drawLevel(gc);
    }

    // TODO: Create static variables to store the images for the side panel and the ruby. Split into atomic methods.
    public void drawSidePanel(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.setImageSmoothing(false);
        gc.drawImage(new Image(Objects.requireNonNull(GameManager.class.getResourceAsStream("sidebar_v2.png"))), 800, 0, 200, 550);
        // draw rubies
        gc.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 30));
        gc.fillText(String.valueOf(rubies), 915, 155);
        // draw inventory item
        gc.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 15));
        Item currentInventory = levels.get(currentLevelIndex).getPlayer().getInventory();
        if (currentInventory != null) {
            gc.fillText(currentInventory.getClass().getSimpleName(), 900, 310);
        } else {
            gc.fillText("Empty", 900, 310);
        }
        //draw current level
        gc.setFont(Font.font("Baskerville Old Face", FontWeight.BOLD, 30));
        gc.fillText(String.valueOf(currentLevelIndex + 1), 900, 450);
    }

    public void advanceLevel() {
        currentLevelIndex++;
    }

    public void incrementRubies(int rubies) {
        this.rubies += rubies;
    }

    public void initializeInventoryText() {
        inventoryText.setX(50);
        inventoryText.setY(50);
    }

    public void setCurrentLevelIndex(int index) {
        this.currentLevelIndex = index;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public int getRubies() {
        return rubies;
    }

    public String getCurrentUser() {
        return this.currentUser;
    }

    public int getCurrentLevelIndex() {
        return this.currentLevelIndex;
    }
}