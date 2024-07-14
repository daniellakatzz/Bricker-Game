package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a falling heart in the Bricker game.
 */
public class FallingHeart extends Heart{
    private final Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a new FallingHeart instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param tools         Utility class for accessing game tools and resources.
     */
    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameTools tools) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimensions = tools.getWindowDimensions();
        this.gameObjects = tools.getGameObjects();
    }

    /**
     * Determines if the heart should collide with the specified game object.
     *
     * @param other The other game object.
     * @return True if the other object is a paddle; false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(Constants.PADDLE_TAG);
    }

    /**
     * Updates the state of the falling heart. Removes the heart if it falls below the bottom of the window.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float heartLocation = getCenter().y();
        if (heartLocation > windowDimensions.y()) {
            gameObjects.removeGameObject(this);
        }
    }
}
