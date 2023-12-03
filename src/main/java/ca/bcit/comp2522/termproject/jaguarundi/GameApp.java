package ca.bcit.comp2522.termproject.jaguarundi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class GameApp extends Application {
    private Canvas canvas;  // Define canvas as an instance variable
    private TitleScreen titleScreen;
    private GameManager gameManager;
    private boolean isGameStarted = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Elixir Emporium");

        // Create a root pane
        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Create a canvas to render your game
        canvas = new Canvas(1000, 550); // Set your preferred dimensions
        root.getChildren().add(canvas);

        // Initialize your game objects and set up your game loop here
        gameManager = new GameManager();
        titleScreen = new TitleScreen(this, gameManager);

        // Handle key events
        scene.setOnKeyPressed(event -> {
            gameManager.registerKeyPress(event);
        });

        scene.setOnKeyReleased(event -> {
            gameManager.registerKeyRelease(event);
        });

        primaryStage.show();

        // Start the game loop
        GameLoop gameLoop = new GameLoop();
        gameLoop.start();
    }

    private class GameLoop extends AnimationTimer {

        private long lastUpdateTime = 0;

        @Override
        public void handle(long now) {
            if (lastUpdateTime == 0) {
                lastUpdateTime = now;
            }

            long elapsedTime = now - lastUpdateTime;

            // Update your game logic here and pass elapsed time to the GameManager
            if (isGameStarted) {
                gameManager.update(elapsedTime / 1e9); // Convert nanoseconds to seconds
            } else {
                // add mouse event handler
                canvas.setOnMouseClicked(mouseEvent -> {
                    titleScreen.update(mouseEvent);
                });
            }

            // Clear the canvas
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Draw game objects
            if (isGameStarted) {
                gameManager.drawObjects(gc);
            } else {
                titleScreen.draw(gc);
            }

            lastUpdateTime = now;
        }
    }

    public void setGameStarted(boolean isGameStarted) {
        this.isGameStarted = isGameStarted;
    }
}
