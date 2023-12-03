package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameManager {
    private long lastUpdateTime = 0;

    public static final int DAY_LENGTH = 60;

    private ArrayList<Level> levels;
    private int currentLevelIndex;
    private int rubies;
    private double timeRemaining;
    private int day;
    private Text inventoryText;

    public GameManager() {
        this.levels = new ArrayList<>();
        this.currentLevelIndex = 0;
        this.rubies = 0;
        this.timeRemaining = DAY_LENGTH;
        this.day = 0;
        this.inventoryText = new Text();
        generateLevels();
        initializeInventoryText();
    }

    public void generateLevels() {
        Level level1 = new Level(
                this,
                new Player(300, 400, 400),
                new BottleBox(450, 250),
                new TrashCan(500, 50),
                createCauldrons(2),
                createIngredientBoxes(),
                createCustomers(),
                createWalls()
        );
        level1.initializeObjectPositions(
                new double[][]{{600, 150}, {300, 350}},
                new double[][]{{200, 50}, {700, 450}},
                new double[][]{{100, 550}, {100,550}, {100, 550}, {100,550}, {100, 550}},
                new double[][]{{50, 0}, {0, 500}, {0, 0}, {750, 0}, {150, 50}}
        );
        levels.add(level1);
        Level level2 = new Level(
                this,
                new Player(300, 400, 400),
                new BottleBox(450, 250),
                new TrashCan(200, 600),
                createCauldrons(3),
                createIngredientBoxes(),
                createCustomers(),
                createWalls()
        );
        level2.initializeObjectPositions(
                new double[][]{{600, 350}, {300, 150}, {300, 400}},
                new double[][]{{700, 150}, {200, 300}},
                new double[][]{{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
                new double[][]{{50, 0}, {0, 500}, {0, 0}, {750, 0}, {150, 50}}
        );
        levels.add(level2);
    }

    public ArrayList<Cauldron> createCauldrons(int quantity) {
        ArrayList<Cauldron> cauldrons = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            cauldrons.add(new Cauldron());
        }
        return cauldrons;
    }

    public ArrayList<IngredientBox> createIngredientBoxes() {
        ArrayList<IngredientBox> ingredientBoxes = new ArrayList<>();
        ingredientBoxes.add(new HogrootBox());
        ingredientBoxes.add(new FrostfernLeavesBox());
        if (day == 2) {
            // Add new ingredient box
        }
        if (day == 3) {
            // Add new ingredient box
        }
        return ingredientBoxes;
    }

    public ArrayList<Customer> createCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        // TODO: Add more customers
        for (int i = 0; i < 5; i++) {
            customers.add(new Customer(200, 50, 3));
        }
        return customers;
    }

    public ArrayList<Wall> createWalls() {
        ArrayList<Wall> walls = new ArrayList<>();
        walls.add(new Wall(700, 50, "top"));
        walls.add(new Wall(800, 50, "bottom"));
        walls.add(new Wall(50, 500, "side"));
        walls.add(new Wall(50, 500, "side"));
        walls.add(new Wall(50, 450, "counter"));
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

    public void update(double elapsedTime) {
        if (lastUpdateTime == 0) {
            lastUpdateTime = System.nanoTime();
        }
        long currentTime = System.nanoTime();
        double delta = (currentTime - lastUpdateTime) / 1e9;
        lastUpdateTime = currentTime;

        // Call updateLevel of the current level
        levels.get(currentLevelIndex).updateLevel(delta);

        // Update inventory text
//        inventoryText.setText(updateInventoryText());



    }

    public void drawObjects(GraphicsContext gc) {
        // Draw side panel
        drawSidePanel(gc);

        // Call drawLevel of the current level
        gc.setFill(Color.BLACK);
        levels.get(currentLevelIndex).drawLevel(gc);
    }

    // TODO: Create static variables to store the images for the side panel and the ruby. Split into atomic methods.
    public void drawSidePanel(GraphicsContext gc) {
        gc.setImageSmoothing(false);
        gc.drawImage(new Image(Objects.requireNonNull(GameManager.class.getResourceAsStream("sidebar.png"))), 800, 0, 200, 550);
        gc.setFill(Color.BLACK);
        gc.drawImage(new Image(Objects.requireNonNull(GameManager.class.getResourceAsStream("ruby.png"))), 850, 75, 15, 31);
        gc.fillText("" + rubies, 900, 100);
        gc.fillText(String.format("%.2f", timeRemaining), 850, 125);
        gc.fillText("Day: " + day, 850, 150);
        Item currentInventory = levels.get(currentLevelIndex).getPlayer().getInventory();
        gc.fillText("Inventory: ", 850, 200);
        if (currentInventory != null) {
            gc.fillText(currentInventory.getClass().getSimpleName(), 850, 225);
        } else {
            gc.fillText("Empty", 850, 225);
        }
        gc.fillText("Current Order: ", 850, 300);
    }

    // TODO: remove day since it is redundant?
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

//    // TODO: Refactor inventory text for efficiency and move to current level (or find work around)
//    public String updateInventoryText() {
//        Item currentInventory = player.getInventory();
//        String text = "";
//        if (currentInventory != null) {
//            text += currentInventory.getClass().getSimpleName();
//        } else {
//            text += "Empty";
//        }
//        return text;
//    }

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