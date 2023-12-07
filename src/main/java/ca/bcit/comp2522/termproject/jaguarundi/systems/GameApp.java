package ca.bcit.comp2522.termproject.jaguarundi.systems;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;


public class GameApp extends Application {
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer backgroundMusicPlayer;

    private Canvas canvas;
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

        playBackgroundMusic();
    }

    private class GameLoop extends AnimationTimer {

        private long lastUpdateTime = 0;

        @Override
        public void handle(long now) {
            if (lastUpdateTime == 0) {
                lastUpdateTime = now;
            }

            // Update your game logic here and pass elapsed time to the GameManager
            if (isGameStarted) {
                gameManager.update(); // Convert nanoseconds to seconds
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

    public static void playSound(String soundFileName) {
        String soundFile = Objects.requireNonNull(GameApp.class.getResource(soundFileName)).toExternalForm();
        Media sound = new Media(soundFile);

        mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.play();
    }

    private static void playBackgroundMusic() {
        // Load the background music file
        String musicFile = Objects.requireNonNull(GameApp.class.getResource("diminished_chords.mp3")).toExternalForm();
        Media backgroundMusic = new Media(musicFile);

        // Create a media player for the background music
        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);

        // Set the music to loop indefinitely
        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Play the background music
        backgroundMusicPlayer.play();
    }

    public static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setGameStarted(boolean isGameStarted) {
        this.isGameStarted = isGameStarted;
    }

    public boolean getIsGameStarted() {
        return isGameStarted;
    }
}
