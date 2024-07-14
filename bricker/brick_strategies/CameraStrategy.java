package bricker.brick_strategies;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A collision strategy that activates a camera following the ball upon collision.
 * Extends the BasicCollisionStrategy with additional behavior to set the camera.
 */
public class CameraStrategy extends BasicCollisionStrategy implements CollisionStrategy{
    private final GameManager gameManager;
    private final Vector2 windowDimension;

    /**
     * Constructs a new CameraStrategy instance.
     *
     * @param gameObjects   The collection of game objects.
     * @param bricksCounter The counter for tracking the number of remaining bricks.
     * @param tools         Utility class for accessing game tools and resources.
     * @param gameManager   The game manager for the Bricker game.
     */
    public CameraStrategy(GameObjectCollection gameObjects, Counter bricksCounter, GameTools tools,
                          GameManager gameManager) {
        super(gameObjects, bricksCounter);
        this.gameManager = gameManager;
        this.windowDimension = tools.getWindowDimensions();
    }

    /**
     * Handles the collision event for the brick.
     * If the camera is not already set and the colliding object is a ball, it sets the camera to follow the
     * ball.
     *
     * @param thisObj  The brick object that is involved in the collision.
     * @param otherObj The other game object that the brick collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if (gameManager.camera() == null) {
            if (otherObj.getTag().equals(Constants.BALL_TAG)){
                gameManager.setCamera(new Camera(otherObj, Vector2.ZERO,
                        windowDimension.mult(Constants.CAMERA_MULT_FACTOR), windowDimension));
            }
        }
    }
}
