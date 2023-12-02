package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;
import java.util.random.RandomGenerator;

public class Customer extends Interactable {

    private static final Map<String, Image> INGREDIENT_ICONS = new HashMap<>();
    static {
        INGREDIENT_ICONS.put("hogroot", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("hogroot.png"))));
        INGREDIENT_ICONS.put("frostfern", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("frostfern_leaves.png"))));
    }
    private static final Map<String, Image> CUSTOMER_SPRITE_MAP = new HashMap<>();
    static {
        CUSTOMER_SPRITE_MAP.put("idle", new Image(Objects.requireNonNull(Customer.class.getResourceAsStream("customer.png"))));
    }
    public final static int CUSTOMER_WIDTH = 150;
    public final static int CUSTOMER_HEIGHT = 50;

    public final static int CUSTOMER_ORDER_POSITION_1 = 275;
    public final static int CUSTOMER_ORDER_POSITION_2 = 350;
    public final static int CUSTOMER_ORDER_POSITION_3 = 425;

    public final static int CUSTOMER_FINAL_POSITION_Y = -50;
    public final static int CUSTOMER_MAX_PATIENCE = 100;
    public final static Color CUSTOMER_COLOR = Color.RED;
    private final static String[] CUSTOMER_INGREDIENT_TYPES = {"Hogroot", "Frostfern Leaves"};

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
        this.sprite =  CUSTOMER_SPRITE_MAP.get("idle");
        this.isWaiting = false;
        this.patienceText = new Text();
        generateOrder();
    }

    public void draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);
        // TODO: rework these magic numbers
        gc.drawImage(sprite, xPosition, yPosition , 75, 75);

        if (this.isWaiting) {
            double iconX = xPosition - 20; // Adjust this value based on your layout
            double iconY = yPosition;

            for (Ingredient ingredient : order) {
                if (ingredient instanceof Hogroot) {
                    gc.drawImage(INGREDIENT_ICONS.get("hogroot"), iconX, iconY, 20, 20);
                } else if (ingredient instanceof FrostfernLeaves) {
                    gc.drawImage(INGREDIENT_ICONS.get("frostfern"), iconX, iconY, 20, 20);
                }

                // Adjust the position for the next icon
                iconX += 15; // Adjust this value based on your layout
            }
        }

        gc.fillText(patienceText.getText(), xPosition, yPosition + 50);
    }

    public void setText(final String text) {
        this.patienceText.setText(text);
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

    public void incrementPatience(double delta, ArrayList<Customer> copyCustomers){
        if (this.isWaiting && patience < CUSTOMER_MAX_PATIENCE) {
            patience += delta;
            setText(String.format("%.2f", CUSTOMER_MAX_PATIENCE - this.patience));
        } else if (this.isWaiting && patience >= CUSTOMER_MAX_PATIENCE) {
            this.isWaiting = false;
            copyCustomers.remove(this);
            setText("");
            System.out.println("Customer left");
        }
    }

    public void generateOrder(){
        // number of ingredients should be between 2 and the customer level + 1
        Random random = new Random();
        int numberOfIngredients = random.nextInt(customerLevel - 1) + 2;
        for (int i = 0; i < numberOfIngredients; i++) {
            int ingredientIndex = random.nextInt(CUSTOMER_INGREDIENT_TYPES.length);
            String ingredientType = CUSTOMER_INGREDIENT_TYPES[ingredientIndex];
            if (ingredientType.equals("Hogroot")) {
                order.add(new Hogroot());
            } else if (ingredientType.equals("Frostfern Leaves")) {
                order.add(new FrostfernLeaves());
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

    public double getSpeed() {
        return speed;
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
}