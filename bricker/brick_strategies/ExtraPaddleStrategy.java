package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A collision strategy that spawns an extra paddle when a brick is hit.
 * Extends the BasicCollisionStrategy with additional behavior to create an extra paddle.
 */
public class ExtraPaddleStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final GameTools gameTools;
    private final Vector2 windowDimensions;
    private final ImageReader imageReader;

    /**
     * Constructs a new ExtraPaddleStrategy instance.
     *
     * @param gameTools     Utility class for accessing game tools and resources.
     * @param gameObjects   The collection of game objects.
     * @param bricksCounter The counter for tracking the number of remaining bricks.
     */
    public ExtraPaddleStrategy(GameTools gameTools, GameObjectCollection gameObjects, Counter bricksCounter) {
        super(gameObjects, bricksCounter);
        this.gameTools = gameTools;
        this.gameObjects = gameTools.getGameObjects();
        this.windowDimensions = gameTools.getWindowDimensions();
        this.imageReader = gameTools.getImageReader();
    }

    /**
     * Handles the collision event for the brick.
     * If there are fewer than the maximum allowed extra paddles, it creates and spawns an extra paddle at
     * the center of the window.
     *
     * @param thisObj  The brick object that is involved in the collision.
     * @param otherObj The other game object that the brick collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (ExtraPaddle.getNumOfPaddles() < Constants.MAX_EXTRA_PADDLE){
            Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IM_PATH, false);
            ExtraPaddle extraPaddle = new ExtraPaddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                    Constants.PADDLE_HEIGHT), paddleImage, gameTools);
            extraPaddle.setCenter(windowDimensions.mult(Constants.CENTER_FACTOR));
            extraPaddle.setTag(Constants.EXTRA_PADDLE_TAG);
            gameObjects.addGameObject(extraPaddle);
        }
    }
}
