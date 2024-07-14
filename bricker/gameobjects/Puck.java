package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a puck in the Bricker game.
 * The puck is a specialized type of ball with specific behavior, such as random movement and removal when it
 * falls below the window.
 */
public class Puck extends Ball{
    private Vector2 windowDimensions;
    private final GameObjectCollection gameObjects;

    /**
     * Constructs a new Puck instance.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound Sound to play upon collision.
     * @param tools          Utility class for accessing game tools and resources.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                GameTools tools) {
        super(topLeftCorner, dimensions, renderable, collisionSound, tools);
        this.gameObjects = tools.getGameObjects();
        this.windowDimensions = tools.getWindowDimensions();
        setTag(Constants.PUCK_TAG);
    }

    /**
     * Updates the state of the puck. Removes the puck if it falls below the bottom of the window.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float puckHeight = getCenter().y();
        if (puckHeight > windowDimensions.y()) {
            gameObjects.removeGameObject(this);
        }
    }

    /**
     * Sets the initial movement of the puck with a random direction.
     */
    public void setPuckMovement() {
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float)Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float)Math.sin(angle) * Constants.BALL_SPEED;
        setVelocity(new Vector2(velocityX, velocityY));

    }
}
