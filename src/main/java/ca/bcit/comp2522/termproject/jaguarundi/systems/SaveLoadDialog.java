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

/**
 * SaveLoadDialog Class to show the save/load dialog and update the save file.
 *
 * @author Vivian , Adam
 * @version 2023
 */
public class SaveLoadDialog {

    /**
     * Updates the save file with the current user and level.
     *
     * @param currentUser the current user
     * @param currentLevelIndex the current level index
     */
    public static void updateSaveFile(String currentUser, int currentLevelIndex) {
        try {
            URI uri = SaveLoadDialog.class.getResource("/ca/bcit/comp2522/termproject/jaguarundi/saves.txt").toURI();
            Path savesFilePath = Paths.get(uri);

            List<String> lines = Files.readAllLines(savesFilePath);
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
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the save/load dialog.
     *
     * @param previousSaves the previous saves
     * @return the event of the dialog as an Optional
     */
    public static Optional<String> showSaveLoadDialog(Map<String, Integer> previousSaves) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Save/Load Progress");

        DialogPane dialogPane = new DialogPane();
        dialog.setDialogPane(dialogPane);

        dialogPane.getButtonTypes().add(ButtonType.CLOSE);


        TextField nameTextField = new TextField();
        Button enterButton = new Button("Enter");
        HBox inputBox = new HBox(10, nameTextField, enterButton);

        javafx.scene.shape.Line line = new javafx.scene.shape.Line(0, 0, 300, 0);

        List<Button> saveButtons = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : previousSaves.entrySet()) {
            String name = entry.getKey();
            int level = entry.getValue();
            Button saveButton = new Button(name + "    Level: " + level);

            saveButton.setOnAction(event -> {
                dialog.setResult("*" + name + "*" + level);
                dialog.close();
            });

            saveButtons.add(saveButton);
        }
        VBox savesBox = new VBox(saveButtons.toArray(new Button[0]));

        VBox layout = new VBox(10, inputBox, line, savesBox);
        dialogPane.setContent(layout);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return nameTextField.getText();
            }
            return null;
        });
        enterButton.setOnAction(event -> dialog.setResult(nameTextField.getText()));

        return dialog.showAndWait();

    }
}
