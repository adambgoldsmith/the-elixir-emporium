package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.scene.canvas.GraphicsContext;
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
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class TitleScreen {

    private GameApp gameApp;
    private GameManager gameManager;
    private double buttonWidth = 200;
    private double buttonHeight = 40;

    public TitleScreen(GameApp gameApp, GameManager gameManager) {
        this.gameApp = gameApp;
        this.gameManager = gameManager;
    }

    public void draw(GraphicsContext gc) {
        // Draw the title and buttons (unchanged from the previous code)
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        gc.fillText("The Elixir Emporium", 250, 100);

        // Start button
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(400, 200, buttonWidth, buttonHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("Start", 480, 235);

        // Quit button
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(400, 300, buttonWidth, buttonHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("Quit", 480, 335);

        gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
    }

    public void update(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        // Check if the mouse is within the bounds of the Start button
        if (isMouseInButton(mouseX, mouseY, 400, 200, buttonWidth, buttonHeight)) {
            // Invoke Start button's method
            handleStartButtonClick();
        }

        // Check if the mouse is within the bounds of the Quit button
        else if (isMouseInButton(mouseX, mouseY, 400, 300, buttonWidth, buttonHeight)) {
            // Invoke Quit button's method
            handleQuitButtonClick();
        }
    }

    private boolean isMouseInButton(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private void handleStartButtonClick() {
        List<String> namesList = readNamesFromFile("saves.txt");
        // code goes here.
        // read the names from saves.txt and add them to the namesList
        Optional<String> result = SaveLoadDialog.showSaveLoadDialog(namesList);

        // If the user entered a name, process it
        result.ifPresent(name -> {
            System.out.println("User entered name: " + name);
            // Add logic to handle the entered name
            if (!name.isEmpty()) {
                appendNameToFile(name, "saves.txt");
            }
            gameManager.setCurrentLevelIndex(0);
            gameApp.setGameStarted(true);
        });
    }

    private void handleQuitButtonClick() {
        // Add logic for Quit button click
        System.out.println("Quit button clicked");
    }

    private List<String> readNamesFromFile(String fileName) {
        List<String> namesList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                TitleScreen.class.getResourceAsStream("/ca/bcit/comp2522/termproject/jaguarundi/" + fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (!line.trim().isEmpty()) {
                    namesList.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return namesList;
    }


    private void appendNameToFile(String name, String fileName) {
        try {
            // Get the file path
            URI uri = TitleScreen.class.getResource("/ca/bcit/comp2522/termproject/jaguarundi/" + fileName).toURI();
            Path path = Paths.get(uri);

            // Append the entered name as a new line, including a line separator
            Files.write(
                    path,
                    (name + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,  // Create the file if it does not exist
                    StandardOpenOption.APPEND   // Append to the file
            );
        } catch (IOException | URISyntaxException e) {
            // Handle the exception appropriately, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }



}