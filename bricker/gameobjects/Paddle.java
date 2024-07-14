package bricker.gameobjects;

import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a paddle in the Bricker game.
 * The paddle is controlled by the player and moves left and right within the game window.
 */
public class Paddle extends GameObject {
    private Vector2 windowDimensions;
    private UserInputListener inputListener;
    private boolean addLife = false;

    /**
     * Constructs a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param tools         Utility class for accessing game tools and resources.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameTools tools) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = tools.getUserInputListener();
        this.windowDimensions = tools.getWindowDimensions();
        setTag(Constants.PADDLE_TAG);
    }

    /**
     * Updates the state of the paddle based on player input.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(Constants.PADDLE_MOVE_SPEED));

        // if paddle surpasses the window from left then change its x
        if(getTopLeftCorner().x() < 0) {
            transform().setTopLeftCornerX(0);
        }

        // if paddle surpasses the window from right then change its x
        if(getTopLeftCorner().x() > (windowDimensions.x() - getDimensions().x())) {
            transform().setTopLeftCornerX(windowDimensions.x() - getDimensions().x());
        }
    }

    /**
     * Handles the event when the paddle collides with another game object.
     *
     * @param other     The other game object that this paddle collided with.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other.getTag().equals(Constants.FALLING_HEART_TAG) && other.shouldCollideWith(this)) {
            addLife = true;
        }
    }

    /**
     * Checks if an extra life should be added due to a collision with a falling heart.
     *
     * @return True if an extra life should be added; false otherwise.
     */
    public boolean isAddLife() {
        return addLife;
    }

    /**
     * Sets whether an extra life should be added.
     *
     * @param addLife True if an extra life should be added; false otherwise.
     */
    public void setAddLife(boolean addLife) {
        this.addLife = addLife;
    }
}
