package bricker.brick_strategies;

import danogl.GameObject;

/**
 * An interface for defining collision strategies in the Bricker game.
 * Implementing classes will define specific behaviors to execute when a collision occurs.
 */
public interface CollisionStrategy {
    /**
     * Defines the behavior to execute when a collision occurs.
     *
     * @param thisObj  The game object that this strategy is associated with.
     * @param otherObj The other game object involved in the collision.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
