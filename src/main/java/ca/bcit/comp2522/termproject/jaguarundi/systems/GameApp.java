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

/**
 * GameApp Class to drive the game loop.
 *
 * @author Vivian , Adam
 * @version 2023
 */
public class GameApp extends Application {
    /**
     * Media player.
     */
    private static MediaPlayer mediaPlayer;

    /**
     * Background music player.
     */
    private static MediaPlayer backgroundMusicPlayer;

    private Canvas canvas;
    private TitleScreen titleScreen;
    private GameManager gameManager;
    private boolean isGameStarted = false;

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the game.
     *
     * @param primaryStage the stage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Elixir Emporium");

        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        canvas = new Canvas(1000, 550);
        root.getChildren().add(canvas);

        gameManager = new GameManager();
        titleScreen = new TitleScreen(this, gameManager);

        scene.setOnKeyPressed(event -> {
            gameManager.registerKeyPress(event);
        });

        scene.setOnKeyReleased(event -> {
            gameManager.registerKeyRelease(event);
        });

        primaryStage.show();

        GameLoop gameLoop = new GameLoop();
        gameLoop.start();

        playBackgroundMusic();
    }

    /**
     * Game loop class.
     *
     * @author Vivian , Adam
     * @version 2023
     */
    private class GameLoop extends AnimationTimer {

        private long lastUpdateTime = 0;

        /**
         * Handles the game loop.
         *
         * @param now the current time
         */
        @Override
        public void handle(long now) {
            if (lastUpdateTime == 0) {
                lastUpdateTime = now;
            }

            if (isGameStarted) {
                gameManager.update();
            } else {
                canvas.setOnMouseClicked(mouseEvent -> {
                    titleScreen.update(mouseEvent);
                });
            }

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            if (isGameStarted) {
                gameManager.drawObjects(gc);
            } else {
                titleScreen.draw(gc);
            }

            lastUpdateTime = now;
        }
    }

    /**
     * Plays a sound.
     *
     * @param soundFileName the sound file name
     */
    public static void playSound(String soundFileName) {
        String soundFile = Objects.requireNonNull(GameApp.class.getResource(soundFileName)).toExternalForm();
        Media sound = new Media(soundFile);

        mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.play();
    }

    /**
     * Plays background music.
     */
    private static void playBackgroundMusic() {
        String musicFile = Objects.requireNonNull(GameApp.class.getResource("diminished_chords.mp3")).toExternalForm();
        Media backgroundMusic = new Media(musicFile);

        backgroundMusicPlayer = new MediaPlayer(backgroundMusic);

        backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        backgroundMusicPlayer.play();
    }

    /**
     * Set the game state.
     */
    public void setGameStarted(boolean isGameStarted) {
        this.isGameStarted = isGameStarted;
    }

    /**
     * Get the game state.
     */
    public boolean getIsGameStarted() {
        return isGameStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameApp gameApp = (GameApp) o;
        return isGameStarted == gameApp.isGameStarted && Objects.equals(canvas, gameApp.canvas) && Objects.equals(titleScreen, gameApp.titleScreen) && Objects.equals(gameManager, gameApp.gameManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(canvas, titleScreen, gameManager, isGameStarted);
    }

    @Override
    public String toString() {
        return "GameApp{" +
                "canvas=" + canvas +
                ", titleScreen=" + titleScreen +
                ", gameManager=" + gameManager +
                ", isGameStarted=" + isGameStarted +
                '}';
    }
}
