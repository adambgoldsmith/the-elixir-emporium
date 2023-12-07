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

public class Cauldron extends Interactable implements Collidable {
    private static final Map<String, Image> CAULDRON_SPRITE_MAP = new HashMap<>();
    static {
        CAULDRON_SPRITE_MAP.put("empty", new Image(Objects.requireNonNull(Cauldron.class.getResourceAsStream("empty_cauldron_v2.png"))));
        CAULDRON_SPRITE_MAP.put("hogroot", new Image(Objects.requireNonNull(Cauldron.class.getResourceAsStream("cauldron_hogroot.png"))));
        CAULDRON_SPRITE_MAP.put("frostfern", new Image(Objects.requireNonNull(Cauldron.class.getResourceAsStream("cauldron_frostfern_leaves.png"))));
        CAULDRON_SPRITE_MAP.put("scorch radish", new Image(Objects.requireNonNull(Cauldron.class.getResourceAsStream("cauldron_scorch_radish.png"))));
        CAULDRON_SPRITE_MAP.put("cobalt compound", new Image(Objects.requireNonNull(Cauldron.class.getResourceAsStream("cauldron_cobalt_compound.png"))));
        CAULDRON_SPRITE_MAP.put("fluorescent egg", new Image(Objects.requireNonNull(Cauldron.class.getResourceAsStream("cauldron_fluorescent_egg.png"))));
    }
    public final static int CAULDRON_WIDTH = 50;
    public final static int CAULDRON_HEIGHT = 50;
    public final static double BOIL_TIME = 5.0;
    public final static double CAULDRON_TEXT_Y_OFFSET = 50.0;

    private double xPosition;
    private double yPosition;
    private double width;
    private double height;
    private Ingredient ingredient;
    private boolean isBoiling;
    private double timer;
    private Image sprite;
    private Text text;

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

    public void draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);
        gc.setFont(javafx.scene.text.Font.font("Baskerville Old Face", FontWeight.BOLD, 20));
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.drawImage(sprite, xPosition, yPosition, width, height);
        gc.fillText(text.getText(), xPosition + width / 2 , yPosition + height / 2 + CAULDRON_TEXT_Y_OFFSET);
    }

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

    public void setText(final String text) {
        this.text.setText(text);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void addIngredient(final Ingredient ingredient) {
        if (this.ingredient == null) {
            this.ingredient = ingredient;
            System.out.println("Added ingredient to cauldron");
            this.isBoiling = true;
            System.out.println("Started boiling cauldron");
        }
    }

    public void boil(double delta) {
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

    public void removeIngredient() {
        this.ingredient = null;
        System.out.println("Removed ingredient from cauldron");
        setText("");
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setXPosition(final double xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(final double yPosition) {
        this.yPosition = yPosition;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}