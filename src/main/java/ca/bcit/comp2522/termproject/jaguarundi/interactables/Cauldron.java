package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Collidable;
import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

/**
 * A cauldron.
 *
 * @author Vivian , Adam
 * @version 2023
 */

public class Cauldron extends Interactable implements Collidable {
    /**
     * The width of the cauldron.
     */
    public final static int CAULDRON_WIDTH = 50;
    /**
     * The height of the cauldron.
     */
    public final static int CAULDRON_HEIGHT = 50;
    /**
     * The time it takes to boil the cauldron.
     */
    public final static double BOIL_TIME = 5.0;
    /**
     * The y offset of the text.
     */
    public final static double CAULDRON_TEXT_Y_OFFSET = 50.0;
    /**
     * The sprite map of the cauldron.
     */

    private static final Map<String, Image> CAULDRON_SPRITE_MAP = new HashMap<>();
    static {
        CAULDRON_SPRITE_MAP.put("empty", new Image(Objects.requireNonNull(Cauldron.class.
                getResourceAsStream("empty_cauldron_v2.png"))));
        CAULDRON_SPRITE_MAP.put("hogroot", new Image(Objects.requireNonNull(Cauldron.class.
                getResourceAsStream("cauldron_hogroot.png"))));
        CAULDRON_SPRITE_MAP.put("frostfern", new Image(Objects.requireNonNull(Cauldron.class.
                getResourceAsStream("cauldron_frostfern_leaves.png"))));
        CAULDRON_SPRITE_MAP.put("scorch radish", new Image(Objects.requireNonNull(Cauldron.class.
                getResourceAsStream("cauldron_scorch_radish.png"))));
        CAULDRON_SPRITE_MAP.put("cobalt compound", new Image(Objects.requireNonNull(Cauldron.class.
                getResourceAsStream("cauldron_cobalt_compound.png"))));
        CAULDRON_SPRITE_MAP.put("fluorescent egg", new Image(Objects.requireNonNull(Cauldron.class.
                getResourceAsStream("cauldron_fluorescent_egg.png"))));
    }

    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private Ingredient ingredient;
    private boolean isBoiling;
    private double timer;
    private Image sprite;
    private final Text text;
    /**
     * Constructs a cauldron.
     */
    public Cauldron() {
        this.xPosition = 0;
        this.yPosition = 0;
        this.width = CAULDRON_WIDTH;
        this.height = CAULDRON_HEIGHT;
        this.ingredient = null;
        this.isBoiling = false;
        this.timer = 0.0;
        this.sprite = CAULDRON_SPRITE_MAP.get("empty");
        this.text = new Text();
    }
    /**
     * Constructs a cauldron.
     * @param gc the graphics context
     */
    public void draw(final GraphicsContext gc) {
        gc.setImageSmoothing(false);
        gc.setFont(javafx.scene.text.Font.font("Baskerville Old Face", FontWeight.BOLD, 20));
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.drawImage(sprite, xPosition, yPosition, width, height);
        gc.fillText(text.getText(), xPosition + width / 2,yPosition + height / 2 + CAULDRON_TEXT_Y_OFFSET);
    }
    /**
     * Animates the cauldron.
     */

    public void animate() {
        if (ingredient != null) {
            if (ingredient.getClass() == Hogroot.class) {
                this.sprite = CAULDRON_SPRITE_MAP.get("hogroot");
            } else if (ingredient.getClass() == FrostfernLeaves.class) {
                this.sprite = CAULDRON_SPRITE_MAP.get("frostfern");
            } else if (ingredient.getClass() == ScorchRadish.class) {
                this.sprite = CAULDRON_SPRITE_MAP.get("scorch radish");
            } else if (ingredient.getClass() == CobaltCompound.class) {
                this.sprite = CAULDRON_SPRITE_MAP.get("cobalt compound");
            } else if (ingredient.getClass() == FluorescentEgg.class) {
                this.sprite = CAULDRON_SPRITE_MAP.get("fluorescent egg");
            }
        } else {
            this.sprite = CAULDRON_SPRITE_MAP.get("empty");
        }
    }
    /**
     * Gets the text.
     * @param text the text
     */
    public void setText(final String text) {
        this.text.setText(text);
    }
    /**
     * Gets the ingredient.
     * @return the ingredient
     */
    public Ingredient getIngredient() {
        return ingredient;
    }
    /**
     * Sets the ingredient.
     * @param ingredient the ingredient
     */
    public void addIngredient(final Ingredient ingredient) {
        if (this.ingredient == null) {
            this.ingredient = ingredient;
            System.out.println("Added ingredient to cauldron");
            this.isBoiling = true;
            System.out.println("Started boiling cauldron");
        }
    }
    /**
     * Gets the boiling status.
     * @param delta the time
     */

    public void boil(final double delta) {
        if (this.isBoiling && this.timer < BOIL_TIME) {
            this.timer += delta;
            setText(String.valueOf((int) (BOIL_TIME - this.timer)));
        } else if (this.isBoiling && this.timer >= BOIL_TIME) {
            this.isBoiling = false;
            this.timer = 0.0;
            System.out.println("Finished boiling cauldron");
            setText("Ready!");
            this.ingredient.setStage(this.ingredient.getStage() + 1);
        }
    }
    /**
     * Removes the ingredient.
     */

    public void removeIngredient() {
        this.ingredient = null;
        System.out.println("Removed ingredient from cauldron");
        setText("");
    }
    /**
     * Gets the X Position.
     * @return the X Position
     */

    public double getXPosition() {
        return xPosition;
    }
    /**
     * Gets the Y Position.
     * @return the Y Position
     */
    public double getYPosition() {
        return yPosition;
    }
    /**
     * Sets the X Position.
     * @param xPosition the X Position
     */

    public void setXPosition(final double xPosition) {
        this.xPosition = xPosition;
    }
    /**
     * Sets the Y Position.
     * @param yPosition the Y Position
     */

    public void setYPosition(final double yPosition) {
        this.yPosition = yPosition;
    }
    /**
     * Gets the width.
     * @return the width
     */

    public double getWidth() {
        return width;
    }
    /**
     * Gets the height.
     * @return the height
     */

    public double getHeight() {
        return height;
    }
}
