package bricker.brick_strategies;

import danogl.GameObject;

/**
 * A collision strategy that combines two other collision strategies.
 * When a collision occurs, both strategies are executed.
 */
public class DoubleStrategy implements CollisionStrategy {
    private final CollisionStrategy firstStrat;
    private final CollisionStrategy secondStrat;

    /**
     * Constructs a new DoubleStrategy instance.
     *
     * @param firstStrat  The first collision strategy to execute.
     * @param secondStrat The second collision strategy to execute.
     */
    DoubleStrategy(CollisionStrategy firstStrat, CollisionStrategy secondStrat) {
        this.firstStrat = firstStrat;
        this.secondStrat = secondStrat;

    }

    /**
     * Handles the collision event by executing both strategies.
     *
     * @param thisObj  The game object that this strategy is associated with.
     * @param otherObj The other game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        firstStrat.onCollision(thisObj, otherObj);
        secondStrat.onCollision(thisObj, otherObj);

    }
}
