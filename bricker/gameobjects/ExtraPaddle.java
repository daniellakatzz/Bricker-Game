package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents an extra paddle in the Bricker game.
 * This paddle is temporary and will be removed after a certain number of collisions.
 */
public class ExtraPaddle extends Paddle {
    private static int numOfPaddles = 0;
    private int numOfCollisions = 0;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a new ExtraPaddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param tools         Utility class for accessing game tools and resources.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameTools tools) {
        super(topLeftCorner, dimensions, renderable, tools);
        this.gameObjects = tools.getGameObjects();
        numOfPaddles ++;
        setTag(Constants.EXTRA_PADDLE_TAG);
    }

    /**
     * Handles the event when the extra paddle collides with another game object.
     * Increments the collision count and removes the paddle if it exceeds the maximum allowed collisions.
     *
     * @param other     The other game object that this paddle collided with.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (!other.getTag().equals(Constants.WALL_TAG)) {
            numOfCollisions++;
        }
        if (numOfCollisions >= Constants.MAX_COLLISIONS){
            gameObjects.removeGameObject(this);
            numOfPaddles--;
        }
    }

    /**
     * @return The current number of extra paddles.
     */
    public static int getNumOfPaddles() {
        return numOfPaddles;
    }

    /**
     * Sets the number of extra paddles.
     *
     * @param num The number of extra paddles to set.
     */
    public static void setNumPaddles(int num) {
         numOfPaddles = num;
    }
}
