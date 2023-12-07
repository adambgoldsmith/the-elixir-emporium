package ca.bcit.comp2522.termproject.jaguarundi.systems;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SaveLoadDialog {

    public static void updateSaveFile(String currentUser, int currentLevelIndex) {
        try {
            // Get the file path
            URI uri = SaveLoadDialog.class.getResource("/ca/bcit/comp2522/termproject/jaguarundi/saves.txt").toURI();
            Path savesFilePath = Paths.get(uri);

            // Read all lines from the file
            List<String> lines = Files.readAllLines(savesFilePath);
            // Find and update the specified user's level
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith(currentUser + "=")) {
                    lines.set(i, currentUser + "=" + (currentLevelIndex + 1));
                    break;
                }
            }

            Files.write(
                    savesFilePath,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,  // Create the file if it does not exist
                    StandardOpenOption.TRUNCATE_EXISTING  // Truncate the file before writing
            );
        } catch (IOException | URISyntaxException e) {
            // Handle the exception appropriately, e.g., log it or throw a custom exception
            e.printStackTrace();
        }
    }

    public static Optional<String> showSaveLoadDialog(Map<String, Integer> previousSaves) {
        // Create a dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Save/Load Progress");

        // Create a custom dialog pane
        DialogPane dialogPane = new DialogPane();
        dialog.setDialogPane(dialogPane);

        // create a close button
        dialogPane.getButtonTypes().add(ButtonType.CLOSE);


        // Create UI components
        TextField nameTextField = new TextField();
        Button enterButton = new Button("Enter");
        HBox inputBox = new HBox(10, nameTextField, enterButton);

        // Create a horizontal bar
        javafx.scene.shape.Line line = new javafx.scene.shape.Line(0, 0, 300, 0);

        // Create buttons for previous saves
        List<Button> saveButtons = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : previousSaves.entrySet()) {
            String name = entry.getKey();
            int level = entry.getValue();
            Button saveButton = new Button(name + "    Level: " + level);

            // Handle the action when a name button is clicked
            saveButton.setOnAction(event -> {
                System.out.println(name + " button clicked");
                dialog.setResult("*" + name + "*" + level);
                dialog.close();
            });

            saveButtons.add(saveButton);
        }
        VBox savesBox = new VBox(saveButtons.toArray(new Button[0]));

// Set up the layout
        VBox layout = new VBox(10, inputBox, line, savesBox);
        dialogPane.setContent(layout);

// Set the result converter for the dialog
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                System.out.println("Enter button clicked");
                return nameTextField.getText();
            }
            return null;
        });
        enterButton.setOnAction(event -> dialog.setResult(nameTextField.getText()));

// Show the dialog and wait for user input
        return dialog.showAndWait();

    }
}
