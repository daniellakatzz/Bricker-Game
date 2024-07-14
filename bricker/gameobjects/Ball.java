package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents a ball in the Bricker game.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private final GameTools tools;
    private int collisionCounter;

    /**
     * Constructs a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionSound Sound to play upon collision.
     * @param tools         Utility class for accessing game tools and resources.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                GameTools tools) {
        super(topLeftCorner, dimensions, renderable);
        this.tools = tools;
        this.collisionCounter = 0;
        this.collisionSound = collisionSound;
        setBallMovement();
        setTag(Constants.BALL_TAG);
    }

    /**
     * Handles the event when the ball collides with another game object.
     *
     * @param other     The other game object that this ball collided with.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setVelocity(getVelocity().flipped(collision.getNormal()));
        collisionSound.play();
        collisionCounter++;
    }

    /**
     * @return The number of collisions the ball has had.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

    /**
     * Sets the initial movement of the ball.
     * The ball starts at the center of the window and moves in a random direction.
     */
    public void setBallMovement() {
        setCenter(tools.getWindowDimensions().mult(Constants.CENTER_FACTOR));
        float ballVelX = Constants.BALL_SPEED;
        float ballVellY = Constants.BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean())
            ballVelX *= Constants.VELOCITY_FLIP;
        if(rand.nextBoolean())
            ballVellY *= Constants.VELOCITY_FLIP;
        setVelocity(new Vector2(ballVelX, ballVellY));
    }
}
