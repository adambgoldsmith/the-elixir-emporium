package ca.bcit.comp2522.termproject.jaguarundi.systems;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class TitleScreen {
    public final static Image TITLE_SCREEN_ART = new Image(Objects.requireNonNull(TitleScreen.class.getResourceAsStream("TitleScreenArt.png")));
    public final static double BUTTON_WIDTH = 300;
    public final static double BUTTON_HEIGHT = 75;
    private static final String NAME_LEVEL_REGEX = "\\*.*\\*\\d+";

    private GameApp gameApp;
    private GameManager gameManager;

    public TitleScreen(GameApp gameApp, GameManager gameManager) {
        this.gameApp = gameApp;
        this.gameManager = gameManager;
    }

    public void draw(final GraphicsContext gc) {
        // Draw the background image
        gc.drawImage(TITLE_SCREEN_ART, 0, 0, 1000, 550);

        // Set color and font
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 40));

        // Start button
        gc.setFill(Color.MEDIUMPURPLE);
        gc.fillRect(100, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        gc.setFill(Color.WHITE);
        gc.fillText("Start", 140 + BUTTON_WIDTH / 2 - gc.getFont().getSize() * 2, 265 + BUTTON_HEIGHT / 2);

        // Quit button
        gc.setFill(Color.MEDIUMPURPLE);
        gc.fillRect(100, 350, BUTTON_WIDTH, BUTTON_HEIGHT);
        gc.setFill(Color.WHITE);
        gc.fillText("Quit", 140 + BUTTON_WIDTH / 2 - gc.getFont().getSize() * 2, 365 + BUTTON_HEIGHT / 2);
    }

    public void update(final MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        if (isMouseInButton(mouseX, mouseY, 100, 250, BUTTON_WIDTH, BUTTON_HEIGHT)) {
            handleStartButtonClick();
        }

        else if (isMouseInButton(mouseX, mouseY, 100, 350, BUTTON_WIDTH, BUTTON_HEIGHT)) {
            handleQuitButtonClick();
        }
    }

    private boolean isMouseInButton(final double mouseX, final double mouseY, final double buttonX, final double buttonY, final double buttonWidth, final double buttonHeight) {
        return mouseX >= buttonX && mouseX <= buttonX + buttonWidth && mouseY >= buttonY && mouseY <= buttonY + buttonHeight;
    }

    private void handleStartButtonClick() {
        Map<String, Integer> namesAndNumbers = readNamesFromFile("saves.txt");
        Optional<String> result = SaveLoadDialog.showSaveLoadDialog(namesAndNumbers);

        result.ifPresent(this::processUserInput);
    }

    private void processUserInput(final String name) {
        if (name.matches(NAME_LEVEL_REGEX)) {
            handleNamedLevelInput(name);
        } else {
            handleRegularNameInput(name);
        }

        gameApp.setGameStarted(true);
    }

    private void handleNamedLevelInput(final String name) {
        String[] nameParts = name.split("\\*");

        if (nameParts.length == 3) {
            String enteredName = nameParts[1];
            int level = Integer.parseInt(nameParts[2]);

            gameManager.setCurrentLevelIndex(level - 1);

            if (!enteredName.isEmpty()) {
                gameManager.setCurrentUser(enteredName);
                System.out.println("Current user: " + enteredName);
            }
        }
    }

    private void handleRegularNameInput(final String name) {
        if (!name.isEmpty()) {
            appendNameToFile(name, "saves.txt");
        }

        gameManager.setCurrentLevelIndex(0);

        if (!name.isEmpty()) {
            gameManager.setCurrentUser(name);
            System.out.println("Current user: " + name);
        }
    }

    private void handleQuitButtonClick() {
        // TODO: Add quit button functionality
        System.out.println("Quit button clicked");
    }

    private Map<String, Integer> readNamesFromFile(final String fileName) {
        Map<String, Integer> namesAndNumbers = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                TitleScreen.class.getResourceAsStream("/ca/bcit/comp2522/termproject/jaguarundi/" + fileName), StandardCharsets.UTF_8))) {
            reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .forEach(line -> {
                        String[] parts = line.split("=");
                        if (parts.length == 2) {
                            namesAndNumbers.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                        } else {
                            System.err.println("Invalid line format: " + line);
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return namesAndNumbers;
    }

    private void appendNameToFile(final String name, final String fileName) {
        try {
            Path path = Paths.get(TitleScreen.class.getResource("/ca/bcit/comp2522/termproject/jaguarundi/" + fileName).toURI());
            Files.write(path, (name + "=1" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}