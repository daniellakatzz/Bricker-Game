package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents a graphical life counter in the Bricker game.
 * This counter displays the player's remaining lives as both graphical hearts and a numeric counter.
 */
public class GraphicLifeCounter extends GameObject {
    private final Counter livesCounter;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final ImageReader imageReader;
    private final Heart[] hearts;
    private TextRenderable textRenderable;
    private GameObject numericLifeCounter;

    /**
     * Constructs a new GraphicLifeCounter instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param InitNumOfLives Initial number of lives to display.
     * @param livesCounter  Counter for tracking the player's remaining lives.
     * @param gameTools     Utility class for accessing game tools and resources.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                              int InitNumOfLives, Counter livesCounter, GameTools gameTools) {
        super(topLeftCorner, dimensions, renderable);
        this.livesCounter = livesCounter;
        this.gameObjects = gameTools.getGameObjects();
        this.windowDimensions = gameTools.getWindowDimensions();
        this.imageReader = gameTools.getImageReader();

        this.hearts = new Heart[Constants.MAX_LIVES];
        for (int i = 0; i < InitNumOfLives; i++) {
            Heart heart = addHeart(i);
            hearts[i] = heart;
        }
        initNumericCounter();
    }

    /**
     * Removes a heart from the graphical life counter.
     */
    public void removeHeart() {
        gameObjects.removeGameObject(hearts[livesCounter.value()], Layer.UI);
        updateNumericLifeCounter();
    }

    /**
     * Adds a life to the graphical life counter.
     */
    public void addLife() {
        if(livesCounter.value() < Constants.MAX_LIVES) {
            hearts[livesCounter.value()] = addHeart(livesCounter.value());
            livesCounter.increment();
            updateNumericLifeCounter();
        }
    }

    /**
     * Initializes the numeric life counter.
     */
    private void initNumericCounter() {
        TextRenderable textRenderable = new TextRenderable(String.valueOf(livesCounter.value()));
        this.textRenderable = textRenderable;
        GameObject numericLifeCounter =
                new GameObject(new Vector2((livesCounter.value() + Constants.SPACE) * Constants.MIN_DISTANCE,
                        windowDimensions.y() - Constants.MIN_DISTANCE),
                        new Vector2(Constants.NUM_COUNTER_SIZE, Constants.NUM_COUNTER_SIZE), textRenderable);
        gameObjects.addGameObject(numericLifeCounter, Layer.UI);
        this.numericLifeCounter = numericLifeCounter;
        updateNumericLifeCounter();
    }

    /**
     * Updates the numeric life counter based on the current number of lives.
     */
    private void updateNumericLifeCounter() {
        textRenderable.setString(String.valueOf(livesCounter.value()));
        switch (livesCounter.value()) {
            case 2:
                textRenderable.setColor(Color.yellow);
                break;
            case 1:
                textRenderable.setColor(Color.red);
                break;
            default:
                textRenderable.setColor(Color.green);
        }
        numericLifeCounter.renderer().setRenderable(textRenderable);
    }

    /**
     * Adds a heart to the graphical life counter.
     *
     * @param heartInd The index of the heart to add.
     * @return The created Heart object.
     */
    private Heart addHeart(int heartInd) {
        Renderable heartImage = imageReader.readImage(Constants.HEART_PATH, false);
        Heart heart = new Heart(new Vector2(heartInd * Constants.MIN_DISTANCE,
                windowDimensions.y() - Constants.MIN_DISTANCE), new Vector2(Constants.HEART_SIZE,
                Constants.HEART_SIZE), heartImage);
        gameObjects.addGameObject(heart, Layer.UI);
        return heart;
    }


}
