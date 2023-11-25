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
    private GameManager gameManager;

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
        canvas = new Canvas(800, 600); // Set your preferred dimensions
        root.getChildren().add(canvas);

        // Initialize your game objects and set up your game loop here
        gameManager = new GameManager();

        // Handle key events
        scene.setOnKeyPressed(event -> {
            gameManager.handleKeyPress(event);
        });

        scene.setOnKeyReleased(event -> {
            gameManager.handleKeyRelease(event);
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

            // 60 FPS
            long targetFrameTime = 1_000_000_000L / 60;
            if (elapsedTime >= targetFrameTime) {
                // Update your game logic here and pass elapsed time to the GameManager
                gameManager.update(elapsedTime / 1e9); // Convert nanoseconds to seconds

                // Clear the canvas
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Draw your game objects
                gameManager.drawObjects(gc);

                lastUpdateTime = now;
            }
        }
    }
}