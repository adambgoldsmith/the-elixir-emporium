package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class TitleScreen {
    public final static Image TITLE_SCREEN_ART = new Image(Objects.requireNonNull(TitleScreen.class.getResourceAsStream("TitleScreenArt.png")));

    private GameApp gameApp;
    private GameManager gameManager;
    private double buttonWidth = 300;
    private double buttonHeight = 75;

    public TitleScreen(GameApp gameApp, GameManager gameManager) {
        this.gameApp = gameApp;
        this.gameManager = gameManager;
    }

    public void draw(GraphicsContext gc) {
        // Draw the background image
        gc.drawImage(TITLE_SCREEN_ART, 0, 0, 1000, 550);

        // Set color and font
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 40));


        // Start button
        gc.setFill(Color.MEDIUMPURPLE);
        gc.fillRect(100, 250, buttonWidth, buttonHeight);
        gc.setFill(Color.WHITE);
        gc.fillText("Start", 140 + buttonWidth / 2 - gc.getFont().getSize() * 2, 265 + buttonHeight / 2);

        // Quit button
        gc.setFill(Color.MEDIUMPURPLE);
        gc.fillRect(100, 350, buttonWidth, buttonHeight);
        gc.setFill(Color.WHITE);
        gc.fillText("Quit", 140 + buttonWidth / 2 - gc.getFont().getSize() * 2, 365 + buttonHeight / 2);
    }

    public void update(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        // Check if the mouse is within the bounds of the Start button
        if (isMouseInButton(mouseX, mouseY, 100, 250, buttonWidth, buttonHeight)) {
            // Invoke Start button's method
            handleStartButtonClick();
        }

        // Check if the mouse is within the bounds of the Quit button
        else if (isMouseInButton(mouseX, mouseY, 100, 350, buttonWidth, buttonHeight)) {
            // Invoke Quit button's method
            handleQuitButtonClick();
        }
    }

    private boolean isMouseInButton(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void handleStartButtonClick() {
        Map<String, Integer> namesAndNumbers = readNamesFromFile("saves.txt");
        Optional<String> result = SaveLoadDialog.showSaveLoadDialog(namesAndNumbers);

        // If the user entered a name, process it
        result.ifPresent(name -> {
            System.out.println("User entered name: " + name);

            // Check if the name is in the format *name*level
            if (name.matches("\\*.*\\*\\d+")) {
                String[] parts = name.split("\\*");
                String enteredName = parts[1];
                int level = Integer.parseInt(parts[2]);
                System.out.println(Arrays.toString(parts));

                // Add logic to handle the entered name and level
                // (You might want to store the name and level in your game data)

                gameManager.setCurrentLevelIndex(level-1);
                if (!name.isEmpty()) {
                    gameManager.setCurrentUser(enteredName);
                    System.out.println("Current user: " + enteredName);
                }
            } else {
                // If the name is just a name with no *, append it to the file
                if (!name.isEmpty()) {
                    appendNameToFile(name, "saves.txt");
                }
                // Set the level index to 0
                gameManager.setCurrentLevelIndex(0);
                if (!name.isEmpty()) {
                    gameManager.setCurrentUser(name);
                    System.out.println("Current user: " + name);
                }
            }

            // Set game started to true in both cases
            gameApp.setGameStarted(true);
        });
    }

    private void handleQuitButtonClick() {
        // Add logic for Quit button click
        System.out.println("Quit button clicked");
    }

    private Map<String, Integer> readNamesFromFile(String fileName) {
        Map<String, Integer> namesAndNumbers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                TitleScreen.class.getResourceAsStream("/ca/bcit/comp2522/termproject/jaguarundi/" + fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (!line.trim().isEmpty()) {
                    // Split the line into name and number using '=' as the delimiter
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String name = parts[0].trim();
                        int number = Integer.parseInt(parts[1].trim());
                        namesAndNumbers.put(name, number);
                    } else {
                        // Handle lines that do not follow the expected format
                        System.err.println("Invalid line format: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return namesAndNumbers;
    }



    private void appendNameToFile(String name, String fileName) {
        try {
            // Get the file path
            URI uri = TitleScreen.class.getResource("/ca/bcit/comp2522/termproject/jaguarundi/" + fileName).toURI();
            Path path = Paths.get(uri);

            // Append the entered name as a new line, including a line separator
            Files.write(
                    path,
                    (name + "=1" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,  // Create the file if it does not exist
                    StandardOpenOption.APPEND   // Append to the file
            );
        } catch (IOException | URISyntaxException e) {
            // Handle the exception appropriately, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }



}