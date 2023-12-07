package ca.bcit.comp2522.termproject.jaguarundi.interactables;

import ca.bcit.comp2522.termproject.jaguarundi.systems.Interactable;
import ca.bcit.comp2522.termproject.jaguarundi.holdables.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.util.*;

public class Customer extends Interactable {


    private static final Map<String, Image> SATISFACTION_FACES = new HashMap<>();
    static {
        SATISFACTION_FACES.put("happy", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("happy_face.png"))));
        SATISFACTION_FACES.put("mid", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("mid_face.png"))));
        SATISFACTION_FACES.put("sad", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("sad_face.png"))));
    }
    private static final Map<String, Image> ORDER_BUBBLES = new HashMap<>();
    static {
        ORDER_BUBBLES.put("size_one", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("order_bubble_1.png"))));
        ORDER_BUBBLES.put("size_two", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("order_bubble_2.png"))));
        ORDER_BUBBLES.put("size_three", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("order_bubble_3.png"))));
    }
    private static final Map<String, Image> INGREDIENT_ICONS = new HashMap<>();
    static {
        INGREDIENT_ICONS.put("hogroot", new Image(Objects.requireNonNull(Hogroot.class.getResourceAsStream("hogroot.png"))));
        INGREDIENT_ICONS.put("frostfern", new Image(Objects.requireNonNull(FrostfernLeaves.class.getResourceAsStream("frostfern_leaves.png"))));
        INGREDIENT_ICONS.put("scorch radish", new Image(Objects.requireNonNull(ScorchRadish.class.getResourceAsStream("scorch_radish.png"))));
        INGREDIENT_ICONS.put("cobalt compound", new Image(Objects.requireNonNull(CobaltCompound.class.getResourceAsStream("cobalt_compound.png"))));
        INGREDIENT_ICONS.put("fluorescent egg", new Image(Objects.requireNonNull(FluorescentEgg.class.getResourceAsStream("fluorescent_egg.png"))));
    }
    private static final Map<String, Image> CUSTOMER_SPRITE_MAP = new HashMap<>();
    static {
        CUSTOMER_SPRITE_MAP.put("customer1", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("customer.png"))));
        CUSTOMER_SPRITE_MAP.put("customer2", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("hobgoblin.png"))));
        CUSTOMER_SPRITE_MAP.put("customer3", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("bow_tie_guy.png"))));
        CUSTOMER_SPRITE_MAP.put("customer4", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("red_boy.png"))));
        CUSTOMER_SPRITE_MAP.put("customer5", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("dark_mage.png"))));
        CUSTOMER_SPRITE_MAP.put("customer6", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("scientist.png"))));
    }
    public final static int CUSTOMER_WIDTH = 150;
    public final static int CUSTOMER_HEIGHT = 50;

    public final static int CUSTOMER_ORDER_POSITION_1 = 275;
    public final static int CUSTOMER_ORDER_POSITION_2 = 350;
    public final static int CUSTOMER_ORDER_POSITION_3 = 425;

    public final static int CUSTOMER_FINAL_POSITION_Y = -50;
    public final static int CUSTOMER_MAX_PATIENCE = 100;
    public final static int PATIENCE_TEST_OFFSET = 30;
    private final static String[] CUSTOMER_INGREDIENT_TYPES = {"Hogroot", "Frostfern Leaves", "Scorch Radish", "Cobalt Compound", "Fluorescent Egg"};

    private double speed;
    private double patience;
    private boolean isWaiting;
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;
    private ArrayList<Ingredient> order;
    private int customerLevel;
    private Text patienceText;
    private boolean isFinished;
    private double satisfactionLevel;
    private Image sprite;


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

    public void draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);
        // TODO: rework these magic numbers
        gc.drawImage(sprite, xPosition, yPosition , 50, 50);

        if (this.isWaiting) {
            if (order.size() == 2) {
                gc.drawImage(ORDER_BUBBLES.get("size_one"), xPosition - 50, yPosition);
            } else if (order.size() == 3) {
                gc.drawImage(ORDER_BUBBLES.get("size_two"), xPosition - 70, yPosition);
            } else if (order.size() == 4) {
                gc.drawImage(ORDER_BUBBLES.get("size_three"), xPosition - 90, yPosition);
            }

            double bubbleWidth = 30; // Adjust this value based on your layout
            double iconX = xPosition - bubbleWidth; // Start with the right side of the order bubble
            double iconY = yPosition; // Adjust this value based on your layout

            for (Ingredient ingredient : order) {
                if (ingredient instanceof Hogroot) {
                    gc.drawImage(INGREDIENT_ICONS.get("hogroot"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof FrostfernLeaves) {
                    gc.drawImage(INGREDIENT_ICONS.get("frostfern"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof ScorchRadish) {
                    gc.drawImage(INGREDIENT_ICONS.get("scorch radish"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof CobaltCompound) {
                    gc.drawImage(INGREDIENT_ICONS.get("cobalt compound"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof FluorescentEgg) {
                    gc.drawImage(INGREDIENT_ICONS.get("fluorescent egg"), iconX, iconY, 20, 20);
                }

                // Adjust the position for the next icon
                iconX -= 20; // Adjust this value based on your layout
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

        gc.fillText(patienceText.getText(), (xPosition + width / 2) - 50, (yPosition + height / 2) - PATIENCE_TEST_OFFSET);
    }

    public void setText(final String text) {
        this.patienceText.setText(text);
    }

    public Image getRandomSprite() {
        Random random = new Random();
        int spriteIndex = random.nextInt(CUSTOMER_SPRITE_MAP.size());
        return CUSTOMER_SPRITE_MAP.get("customer" + (spriteIndex + 1));
    }

    public void move(double delta, ArrayList<Customer> copyCustomers) {
        int customerIndex = copyCustomers.indexOf(this);

        if (!copyCustomers.isEmpty() && customerIndex >= 0 && customerIndex <= 2 && this.yPosition > getCustomerOrderPosition(copyCustomers)) {
            if (customerIndex == 0) {
                this.yPosition -= delta * speed;

                if (this.yPosition <= getCustomerOrderPosition(copyCustomers) && !isWaiting) {
                    isWaiting = true;
                    System.out.println("Customer is waiting for order at position " + (customerIndex + 1));
                }
            } else {
                Customer nextCustomer = copyCustomers.get(customerIndex - 1);
                if (nextCustomer.isWaiting && nextCustomer.getPatience() > CUSTOMER_MAX_PATIENCE / 2 + 10) {
                    this.yPosition -= delta * speed;

                    if (this.yPosition <= getCustomerOrderPosition(copyCustomers) && !isWaiting) {
                        isWaiting = true;
                        System.out.println("Customer is waiting for order at position " + (customerIndex + 1));
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

    private double getCustomerOrderPosition(ArrayList<Customer> copyCustomers) {
        return switch (copyCustomers.indexOf(this)) {
            case 0 -> CUSTOMER_ORDER_POSITION_1;
            case 1 -> CUSTOMER_ORDER_POSITION_2;
            case 2 -> CUSTOMER_ORDER_POSITION_3;
            default -> CUSTOMER_ORDER_POSITION_1; // Default to the first position
        };
    }

    public void incrementPatience(double delta, ArrayList<Customer> copyCustomers) {
        if (this.isWaiting && patience < CUSTOMER_MAX_PATIENCE) {
            patience += delta;
            setText(String.valueOf((int) (CUSTOMER_MAX_PATIENCE - this.patience)));
        } else if (this.isWaiting && patience >= CUSTOMER_MAX_PATIENCE) {
            this.isWaiting = false;
            this.isFinished = true;
            copyCustomers.remove(this);
            setText("");
            System.out.println("Customer left");
        }
    }

    public void generateOrder() {
        Random random = new Random();
        int numberOfIngredients;

        if (customerLevel == 3) {
            numberOfIngredients = random.nextInt(5 - 2) + 2;
        } else if (customerLevel == 2) {
            numberOfIngredients = random.nextInt(4 - 2) + 2;
        } else {
            numberOfIngredients = random.nextInt(3 - 2) + 2;
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
            if (ingredientType.equals("Hogroot")) {
                order.add(new Hogroot());
            } else if (ingredientType.equals("Frostfern Leaves")) {
                order.add(new FrostfernLeaves());
            } else if (ingredientType.equals("Scorch Radish")) {
                order.add(new ScorchRadish());
            } else if (ingredientType.equals("Cobalt Compound")) {
                order.add(new CobaltCompound());
            } else if (ingredientType.equals("Fluorescent Egg")) {
                order.add(new FluorescentEgg());
            }
        }

        System.out.println("Customer order: " + order);
    }


    public int calculateRubies(int correctCount) {
        int correctnessScore = correctCount * 100;
        double timeScore = patience / CUSTOMER_MAX_PATIENCE;
        System.out.println("Correctness score: " + correctnessScore);
        return (int) (correctnessScore * timeScore);
    }

    public ArrayList<Ingredient> getOrder() {
        return order;
    }

    public double getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public double getPatience() {
        return patience;
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setSatisfactionLevel(double satisfactionLevel) {
        this.satisfactionLevel = satisfactionLevel;
    }
}