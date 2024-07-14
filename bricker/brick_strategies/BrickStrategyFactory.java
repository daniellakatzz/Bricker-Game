package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.GameTools;
import bricker.main.Constants;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.Random;

/**
 * A factory for creating different collision strategies for bricks in the Bricker game.
 */
public class BrickStrategyFactory {
    private final BrickerGameManager gameManager;
    private final GameTools tools;
    private final GameObjectCollection gameObjects;
    private final Counter brickCounter;
    private boolean alreadyDouble = false;
    private final Random random = new Random();


    /**
     * Constructs a new BrickStrategyFactory instance.
     *
     * @param gameManager The game manager for the Bricker game.
     * @param tools       Utility class for accessing game tools and resources.
     */
    public BrickStrategyFactory(BrickerGameManager gameManager, GameTools tools){
        this.gameManager = gameManager;
        this.tools = tools;
        this.gameObjects = tools.getGameObjects();
        this.brickCounter = tools.getBrickCounter();

    }

    /**
     * Builds and returns a collision strategy based on the provided strategy number.
     *
     * @param stratNum The strategy number to determine which collision strategy to create.
     * @return The created CollisionStrategy.
     */
    public CollisionStrategy buildStrategy(int stratNum){
        switch (stratNum) {
            case Constants.PUCK_STRAT:
                return new PuckStrategy(tools);
            case Constants.EXTRA_PADDLE_STRAT:
                 return new ExtraPaddleStrategy(tools, gameObjects, brickCounter);
            case Constants.CAMERA_STRAT:
                return new CameraStrategy(gameObjects, brickCounter, tools, gameManager);
            case Constants.EXTRA_LIFE_STRAT:
                return new ExtraLifeStrategy(gameObjects, brickCounter, tools);
            case Constants.DOUBLE_STRAT:
                if(!alreadyDouble) {
                    alreadyDouble = true;
                    return new DoubleStrategy(buildStrategy(random.nextInt(
                            Constants.DOUBLE_STRAT_ALL_OPTIONS)),
                            buildStrategy(Constants.DOUBLE_STRAT_NO_DOUBLE));
                }
                alreadyDouble = false;
                return new DoubleStrategy(buildStrategy(random.nextInt(
                        Constants.DOUBLE_STRAT_NO_DOUBLE)),
                        buildStrategy(random.nextInt(Constants.DOUBLE_STRAT_NO_DOUBLE)));
            default:
                return new BasicCollisionStrategy(gameObjects, brickCounter);
        }
    }
}
