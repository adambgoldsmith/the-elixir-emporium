package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.*;

/**
 * A customer.
 *
 * @author Vivian , Adam
 * @version 2023
 */
public class Customer extends Interactable {
    /**
     * The width of the customer.
     */
    public static final int CUSTOMER_WIDTH = 150;
    /**
     * The height of the customer.
     */
    public static final int CUSTOMER_HEIGHT = 50;
    /**
     * The x position of the first customer.
     */
    public static final int CUSTOMER_ORDER_POSITION_1 = 175;
    /**
     * The x position of the second customer.
     */
    public static final int CUSTOMER_ORDER_POSITION_2 = 275;
    /**
     * The x position of the third customer.
     */
    public static final int CUSTOMER_ORDER_POSITION_3 = 375;
    /**
     * The y position of the customer.
     */
    public static final int CUSTOMER_FINAL_POSITION_Y = -50;
    /**
     * The maximum patience of the customer.
     */
    public static final int CUSTOMER_MAX_PATIENCE = 100;
    /**
     * The patience test offset.
     */
    public static final int PATIENCE_TEST_OFFSET = 30;
    /**
     * The ingredient types that the customer can order.
     */
    private static final String[] CUSTOMER_INGREDIENT_TYPES = {"Hogroot", "Frostfern Leaves",
            "Scorch Radish", "Cobalt Compound", "Fluorescent Egg"};

    private static final Map<String, Image> SATISFACTION_FACES = new HashMap<>();
    static {
        SATISFACTION_FACES.put("happy", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("happy_face.png"))));
        SATISFACTION_FACES.put("mid", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("mid_face.png"))));
        SATISFACTION_FACES.put("sad", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("sad_face.png"))));
    }
    private static final Map<String, Image> ORDER_BUBBLES = new HashMap<>();
    static {
        ORDER_BUBBLES.put("size_one", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("order_bubble_1.png"))));
        ORDER_BUBBLES.put("size_two", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("order_bubble_2.png"))));
        ORDER_BUBBLES.put("size_three", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("order_bubble_3.png"))));
    }
    private static final Map<String, Image> INGREDIENT_ICONS = new HashMap<>();
    static {
        INGREDIENT_ICONS.put("hogroot", new Image(Objects.requireNonNull(Hogroot.class.
                getResourceAsStream("hogroot.png"))));
        INGREDIENT_ICONS.put("frostfern", new Image(Objects.requireNonNull(FrostfernLeaves.class.
                getResourceAsStream("frostfern_leaves.png"))));
        INGREDIENT_ICONS.put("scorch radish", new Image(Objects.requireNonNull(ScorchRadish.class.
                getResourceAsStream("scorch_radish.png"))));
        INGREDIENT_ICONS.put("cobalt compound", new Image(Objects.requireNonNull(CobaltCompound.class.
                getResourceAsStream("cobalt_compound.png"))));
        INGREDIENT_ICONS.put("fluorescent egg", new Image(Objects.requireNonNull(FluorescentEgg.class.
                getResourceAsStream("fluorescent_egg.png"))));
    }
    private static final Map<String, Image> CUSTOMER_SPRITE_MAP = new HashMap<>();
    static {
        CUSTOMER_SPRITE_MAP.put("customer1", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("customer.png"))));
        CUSTOMER_SPRITE_MAP.put("customer2", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("hobgoblin.png"))));
        CUSTOMER_SPRITE_MAP.put("customer3", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("bow_tie_guy.png"))));
        CUSTOMER_SPRITE_MAP.put("customer4", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("red_boy.png"))));
        CUSTOMER_SPRITE_MAP.put("customer5", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("dark_mage.png"))));
        CUSTOMER_SPRITE_MAP.put("customer6", new Image(Objects.requireNonNull(Customer.class.
                getResourceAsStream("scientist.png"))));
    }

    private final double speed;
    private double patience;
    private boolean isWaiting;
    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private final ArrayList<Ingredient> order;
    private final int customerLevel;
    private final Text patienceText;
    private boolean isFinished;
    private double satisfactionLevel;
    private final Image sprite;
    /**
     * Constructs a customer.
     *
     * @param speed the speed of the customer
     * @param patience the patience of the customer
     * @param customerLevel the level of the customer
     */
    public Customer(final double speed, final double patience, final int customerLevel) {
        this.speed = speed;
        this.patience = patience;
        this.xPosition = 0;
        this.yPosition = 0;
        this.width = CUSTOMER_WIDTH;
        this.height = CUSTOMER_HEIGHT;
        this.order = new ArrayList<>();
        this.customerLevel = customerLevel;
        this.sprite = getRandomSprite();
        this.isWaiting = false;
        this.isFinished = false;
        this.patienceText = new Text();
        this.satisfactionLevel = 0;
        generateOrder();
    }
    /**
     * Draws the customer.
     *
     * @param gc the graphics context
     */
    public void draw(final GraphicsContext gc) {
        gc.setImageSmoothing(false);
        gc.drawImage(sprite, xPosition, yPosition , 50, 50);

        if (this.isWaiting) {
            if (order.size() == 2) {
                gc.drawImage(ORDER_BUBBLES.get("size_one"), xPosition - 50, yPosition);
            } else if (order.size() == 3) {
                gc.drawImage(ORDER_BUBBLES.get("size_two"), xPosition - 70, yPosition);
            } else if (order.size() == 4) {
                gc.drawImage(ORDER_BUBBLES.get("size_three"), xPosition - 90, yPosition);
            }

            double bubbleWidth = 30;
            double iconX = xPosition - bubbleWidth;
            double iconY = yPosition;

            for (Ingredient ingredient : order) {
                if (ingredient instanceof Hogroot) {
                    gc.drawImage(INGREDIENT_ICONS.get("hogroot"), iconX, iconY, 20,20);
                } else if (ingredient instanceof FrostfernLeaves) {
                    gc.drawImage(INGREDIENT_ICONS.get("frostfern"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof ScorchRadish) {
                    gc.drawImage(INGREDIENT_ICONS.get("scorch radish"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof CobaltCompound) {
                    gc.drawImage(INGREDIENT_ICONS.get("cobalt compound"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof FluorescentEgg) {
                    gc.drawImage(INGREDIENT_ICONS.get("fluorescent egg"), iconX, iconY, 20, 20);
                }
                iconX -= 20;
            }
        }

        if (this.isFinished) {
            if (satisfactionLevel >= 66) {
                gc.drawImage(SATISFACTION_FACES.get("happy"), xPosition - 25, yPosition, 30, 30);
            } else if (satisfactionLevel >= 33) {
                gc.drawImage(SATISFACTION_FACES.get("mid"), xPosition - 25, yPosition, 30, 30);
            } else {
                gc.drawImage(SATISFACTION_FACES.get("sad"), xPosition - 25, yPosition, 30, 30);
            }
        }
        gc.setFont(javafx.scene.text.Font.font("Baskerville Old Face", FontWeight.BOLD, 20));
        gc.fillText(patienceText.getText(), (xPosition + width / 2) - 50,
                (yPosition + height / 2) - PATIENCE_TEST_OFFSET);
    }
    /**
     * Sets the text.
     * @param text the text
     */

    public void setText(final String text) {
        this.patienceText.setText(text);
    }
    /**
     * Get a random customer sprite.
     * @return the sprite
     */
    public Image getRandomSprite() {
        Random random = new Random();
        int spriteIndex = random.nextInt(CUSTOMER_SPRITE_MAP.size());
        return CUSTOMER_SPRITE_MAP.get("customer" + (spriteIndex + 1));
    }
    /**
     * Moves the customer.
     * @param delta the time
     * @param copyCustomers the customers
     */

    public void move(final double delta, final ArrayList<Customer> copyCustomers) {
        int customerIndex = copyCustomers.indexOf(this);
        int patienceFactor = switch (customerLevel) {
            case 3 -> 14;
            case 2 -> 12;
            default -> 10;
        };

        if (!copyCustomers.isEmpty() && customerIndex >= 0 && customerIndex <= 2
                && this.yPosition > getCustomerOrderPosition(copyCustomers)) {
            if (customerIndex == 0) {
                this.yPosition -= delta * speed;

                if (this.yPosition <= getCustomerOrderPosition(copyCustomers) && !isWaiting) {
                    isWaiting = true;
                }
            } else {
                Customer nextCustomer = copyCustomers.get(customerIndex - 1);
                if (nextCustomer.isWaiting && nextCustomer.getPatience() > this.patience + patienceFactor) {
                    this.yPosition -= delta * speed;

                    if (this.yPosition <= getCustomerOrderPosition(copyCustomers) && !isWaiting) {
                        isWaiting = true;
                    }
                }
            }

        } else if (!copyCustomers.contains(this) && this.yPosition > CUSTOMER_FINAL_POSITION_Y) {
            isWaiting = false;
            this.isFinished = true;
            this.yPosition -= delta * speed;
            setText("");
        }
    }
    /**
     * Gets the customer order position.
     * @param copyCustomers the customers
     * @return the position
     */

    private double getCustomerOrderPosition(final ArrayList<Customer> copyCustomers) {
        return switch (copyCustomers.indexOf(this)) {
            case 1 -> CUSTOMER_ORDER_POSITION_2;
            case 2 -> CUSTOMER_ORDER_POSITION_3;
            default -> CUSTOMER_ORDER_POSITION_1;
        };
    }
    /**
     * Increments the patience.
     * @param delta the time
     * @param copyCustomers the customers
     */

    public void incrementPatience(final double delta, final ArrayList<Customer> copyCustomers) {
        if (this.isWaiting && patience < CUSTOMER_MAX_PATIENCE) {
            patience += delta;
            setText(String.valueOf((int) (CUSTOMER_MAX_PATIENCE - this.patience)));
        } else if (this.isWaiting && patience >= CUSTOMER_MAX_PATIENCE) {
            this.isWaiting = false;
            this.isFinished = true;
            copyCustomers.remove(this);
            setText("");
        }
    }
    /**
     * Generates a random order.
     */

    public void generateOrder() {
        Random random = new Random();
        int numberOfIngredients;

        if (customerLevel == 3) {
            numberOfIngredients = random.nextDouble() < 0.6 ? 3 : 4;
        } else if (customerLevel == 2) {
            numberOfIngredients = random.nextDouble() < 0.75 ? 3 : 2;
        } else {
            numberOfIngredients = 2;
        }

        for (int i = 0; i < numberOfIngredients; i++) {
            int ingredientIndex;
            if (customerLevel == 3) {
                ingredientIndex = random.nextInt(CUSTOMER_INGREDIENT_TYPES.length);
            } else if (customerLevel == 2) {
                ingredientIndex = random.nextInt(CUSTOMER_INGREDIENT_TYPES.length - 1);
            } else {
                ingredientIndex = random.nextInt(3);
            }

            String ingredientType = CUSTOMER_INGREDIENT_TYPES[ingredientIndex];
            switch (ingredientType) {
                case "Hogroot" -> order.add(new Hogroot());
                case "Frostfern Leaves" -> order.add(new FrostfernLeaves());
                case "Scorch Radish" -> order.add(new ScorchRadish());
                case "Cobalt Compound" -> order.add(new CobaltCompound());
                case "Fluorescent Egg" -> order.add(new FluorescentEgg());
            }
        }
    }

    /**
     * Calculates the rubies.
     * @param correctCount the correct count
     * @return the rubies
     */
    public int calculateRubies(final int correctCount) {
        int correctnessScore = correctCount * 100;
        double timeScore = patience / CUSTOMER_MAX_PATIENCE;
        return (int) (correctnessScore * timeScore);
    }
    /**
     * Gets the order.
     * @return the order
     */

    public ArrayList<Ingredient> getOrder() {
        return order;
    }
    /**
     * Gets the customer satisfaction level.
     * @return the satisfaction level
     */

    public double getSatisfactionLevel() {
        return satisfactionLevel;
    }
    /**
     * Gets the customer patience level.
     * @return the patience level
     */

    public double getPatience() {
        return patience;
    }
    /**
     * Gets the X position.
     * @return xPosition the position
     */

    public double getXPosition() {
        return xPosition;
    }
    /**
     * Get the Y position.
     * @return yPosition the position
     */

    public double getYPosition() {
        return yPosition;
    }
    /**
     * Sets the X position.
     * @param xPosition the position
     */

    public void setXPosition(final double xPosition) {
        this.xPosition = xPosition;
    }
    /**
     * Sets the Y position.
     * @param yPosition the position
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
    /**
     * Sets the satisfaction level.
     * @param satisfactionLevel the satisfaction level
     */

    public void setSatisfactionLevel(final double satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }
}
