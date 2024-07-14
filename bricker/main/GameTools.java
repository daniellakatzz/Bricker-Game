package bricker.main;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A utility class that encapsulates various tools and resources needed for the game.
 * This includes the game objects collection, window dimensions, input listener, image reader,
 * sound reader, and counters for bricks and lives.
 */
public class GameTools {

    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final UserInputListener userInputListener;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final Counter brickCounter;

    /**
     * Constructor for initializing the GameTools with required parameters.
     *
     * @param gameObjects The collection of game objects.
     * @param windowDimensions The dimensions of the game window.
     * @param userInputListener The listener for user input.
     * @param imageReader The reader for loading images.
     * @param soundReader The reader for loading sounds.
     * @param brickCounter The counter for tracking the number of bricks.
     */
    GameTools(GameObjectCollection gameObjects, Vector2 windowDimensions,
              UserInputListener userInputListener, ImageReader imageReader,
              SoundReader soundReader, Counter brickCounter) {

        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.userInputListener = userInputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.brickCounter = brickCounter;
    }

    /**
     * @return The collection of game objects.
     */
    public GameObjectCollection getGameObjects() {
        return gameObjects;
    }

    /**
     * @return The dimensions of the game window.
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }

    /**
     * @return The listener for user input.
     */
    public UserInputListener getUserInputListener() {
        return userInputListener;
    }

    /**
     * @return The reader for loading images.
     */
    public ImageReader getImageReader() {
        return imageReader;
    }

    /**
     * @return The reader for loading sounds.
     */
    public SoundReader getSoundReader() { return soundReader; }

    /**
     * @return The counter for tracking the number of bricks.
     */
    public Counter getBrickCounter() { return brickCounter; }

}
