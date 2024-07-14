package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import bricker.main.Constants;
import bricker.main.GameTools;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * A collision strategy that spawns multiple pucks when a brick is hit.
 * Extends the BasicCollisionStrategy with additional behavior to create pucks.
 */
public class PuckStrategy extends BasicCollisionStrategy implements CollisionStrategy {
    private final GameTools gameTools;
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * Constructs a new PuckStrategy instance.
     *
     * @param gameTools Utility class for accessing game tools and resources.
     */
    public PuckStrategy(GameTools gameTools) {
        super(gameTools.getGameObjects(), gameTools.getBrickCounter());
        this.gameTools = gameTools;
        this.gameObjects = gameTools.getGameObjects();
        this.imageReader = gameTools.getImageReader();
        this.soundReader = gameTools.getSoundReader();
    }

    /**
     * Handles the collision event for the brick.
     * Creates and spawns multiple pucks at the brick's location.
     *
     * @param thisObj  The brick object that is involved in the collision.
     * @param otherObj The other game object that the brick collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        Renderable puckImage = imageReader.readImage(Constants.PUCK_IM_PATH, true);
        Sound puckSound = soundReader.readSound(Constants.BALL_SOUND_PATH);
        float puckSize = Constants.PUCK_RELATIVE_SIZE * Constants.BALL_SIZE;
        for (int i = 0; i < Constants.PUCK_NUMBER; i++) {
            Puck puck = new Puck(Vector2.ZERO, new Vector2(puckSize, puckSize), puckImage, puckSound,
                    gameTools);
            puck.setCenter(thisObj.getCenter());
            puck.setPuckMovement();
            gameObjects.addGameObject(puck);
        }
    }
}
