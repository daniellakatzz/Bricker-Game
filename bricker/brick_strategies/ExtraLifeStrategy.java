package bricker.brick_strategies;

import bricker.gameobjects.FallingHeart;
import bricker.gameobjects.Heart;
import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A collision strategy that creates a falling heart when a brick is hit, granting an extra life if caught
 * by the paddle.
 * Extends the BasicCollisionStrategy with additional behavior to spawn a falling heart.
 */
public class ExtraLifeStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final ImageReader imageReader;
    private final GameObjectCollection gameObjects;
    private final GameTools tools;
    private Heart fallingHeart;

    /**
     * Constructs a new ExtraLifeStrategy instance.
     *
     * @param gameObjects   The collection of game objects.
     * @param bricksCounter The counter for tracking the number of remaining bricks.
     * @param tools         Utility class for accessing game tools and resources.
     */
    public ExtraLifeStrategy(GameObjectCollection gameObjects, Counter bricksCounter, GameTools tools) {
        super(gameObjects, bricksCounter);
        this.tools = tools;
        this.imageReader = tools.getImageReader();
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision event for the brick.
     * Creates and spawns a falling heart at the brick's location, which grants an extra life if
     * caught by the paddle.
     *
     * @param thisObj  The brick object that is involved in the collision.
     * @param otherObj The other game object that the brick collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        Renderable heartImage = imageReader.readImage(Constants.HEART_PATH, false);
        fallingHeart = new FallingHeart(Vector2.ZERO, new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE),
                heartImage, tools);
        fallingHeart.setCenter(thisObj.getCenter());
        fallingHeart.setVelocity(new Vector2(Constants.FALLING_HEART_SPEED_X,
                Constants.FALLING_HEART_SPEED_Y));
        fallingHeart.setTag(Constants.FALLING_HEART_TAG);
        gameObjects.addGameObject(fallingHeart);

    }


}
