package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Represents a basic collision strategy for bricks in the Bricker game.
 * When a brick collides with another object, it is removed from the game, and the bricks counter is
 * decremented.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    GameObjectCollection gameObjects;
    private Counter bricksCounter;

    /**
     * Constructs a new BasicCollisionStrategy instance.
     *
     * @param gameObjects   The collection of game objects.
     * @param bricksCounter The counter for tracking the number of remaining bricks.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter bricksCounter) {

        this.gameObjects = gameObjects;
        this.bricksCounter = bricksCounter;
    }

    /**
     * Handles the collision event for the brick.
     * Removes the brick from the game and decrements the bricks counter.
     *
     * @param thisObj  The brick object that is involved in the collision.
     * @param otherObj The other game object that the brick collided with.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if(gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS)) {
            bricksCounter.decrement();
        }
    }

}
