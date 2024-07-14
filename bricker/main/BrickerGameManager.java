package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.BrickStrategyFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The BrickerGameManager class manages the main game logic and setup for the Bricker game.
 */
public class BrickerGameManager extends GameManager {

    private int numBricksInRow = Constants.DEF_NUM_BRICKS_IN_ROW;
    private int numRows = Constants.DEF_NUM_ROWS;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private GameTools tools;
    private Paddle paddle;
    private Ball ball;
    private Counter lifeCounter;
    private Counter bricksCounter;
    private GraphicLifeCounter graphicLifeCounter;
    private boolean cameraActiveFlag = false;
    private int curCollisForCamera = 0;

    /**
     * Constructor for the BrickerGameManager class if no parameters were given by the user.
     * @param windowTitle The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        initCounters();
    }

    /**
     * Constructor for the BrickerGameManager class with customizable brick rows and columns.
     * @param windowTitle The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param numBricksInRow The number of bricks in a row.
     * @param numRows The number of rows of bricks.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numBricksInRow, int numRows) {
        super(windowTitle, windowDimensions);
        this.numBricksInRow = numBricksInRow;
        this.numRows = numRows;
        initCounters();

    }

    /**
     * This method initializes a new game. It creates all game objects,
     * sets their values and initial positions and allow the start of a game.
     * @param imageReader an object used to read images from the disc and render them.
     * @param soundReader an object used to read sound files from the disc and render them.
     * @param inputListener a listener capable of reading user keyboard inputs.
     * @param windowController a controller used to control the window and its attributes.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.tools = new GameTools(this.gameObjects(), this.windowDimensions, this.inputListener,
                this.imageReader, this.soundReader, bricksCounter);


        // create background
        createBackground();
        // create walls
        createWalls();
        // create ball
        createBall();
        // create paddle
        createPaddle();
        // create bricks
        createBricks();
        // create graphic life counter
        createGraphicLifeCounter();
    }

    /**
     * This method overrides the GameManager update method.
     * It checks for game status, and triggers a new game popup.
     * @param deltaTime used in the super's update method.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        shouldAddLife();
        checkCamera();
        checkForGameEnd();
    }

    /**
     * Checks if an extra life should be added and updates the graphic life counter accordingly.
     * Resets the addLife flag on the paddle after processing.
     */
    private void shouldAddLife() {
        if(paddle.isAddLife()) {
            graphicLifeCounter.addLife();
        }
        paddle.setAddLife(false);
    }

    /**
     * This method checks if the game is over - if we lost or won.
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight > windowDimensions.y()) {
            lifeCounter.decrement();
            graphicLifeCounter.removeHeart();

            if(lifeCounter.value() <= 0) {
                prompt = Constants.LOSE_PROMPT;
            }
            else {
                ball.setBallMovement();
            }
        }
        // check if the user won or the W key was pressed
        if(bricksCounter.value() == 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            // we won
            prompt = Constants.WIN_PROMPT;
        }

        if(!(prompt.isEmpty())){
            prompt += Constants.PLAY_AGAIN_PROMPT;
            if(windowController.openYesNoDialog(prompt)) {
                // if the player chose to play another game, reset the required variables
                windowController.resetGame();
                bricksCounter = new Counter(numBricksInRow * numRows);
                lifeCounter = new Counter(Constants.INIT_LIVES);
                ExtraPaddle.setNumPaddles(0);
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * This method checks if the camera need to be turned on if it's off and a Camera strategy was activated,
     * start counting collisions of the main ball and turn off when reached the amount to reset
     */
    private void checkCamera(){
        if(camera() != null && !(cameraActiveFlag)) {
            curCollisForCamera = ball.getCollisionCounter();
            cameraActiveFlag = true;
        }
        if (camera() != null && cameraActiveFlag){
            if (ball.getCollisionCounter() == curCollisForCamera + Constants.COLLISIONS_RESET_CAMERA){
                setCamera(null);
                cameraActiveFlag = false;
            }
        }
    }

    /**
     * Creates bricks for the game and adds them to the game objects.
     * Each brick is assigned a random collision strategy from a set of strategies.
     */
    private void createBricks() {
        Renderable brickImage = imageReader.readImage(Constants.BRICK_IM_PATH, false);
        float brickWidth = windowDimensions.x() / numBricksInRow;
        float brickSpacing = windowDimensions.y() / Constants.WINDOW_HEIGHT_SPACING;
        CollisionStrategy collisionStrategy;
        BrickStrategyFactory strategyFactory = new BrickStrategyFactory(this, tools);
        Random random = new Random();

        for (int i = 1; i <= numRows; i++) {
            for (int j = 0; j < numBricksInRow; j++) {
                int stratNum = random.nextInt(Constants.NUM_STRATEGIES);
                collisionStrategy = strategyFactory.buildStrategy(stratNum);
                Brick brick = new Brick(new Vector2(j*brickWidth,
                        i*(Constants.BRICK_HEIGHT + brickSpacing)),
                        new Vector2(brickWidth, Constants.BRICK_HEIGHT), brickImage, collisionStrategy);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    /**
     * Creates the background for the game and adds it to the game objects.
     */
    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage(Constants.BACKGROUND_IM_PATH,false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Creates the walls for the game and adds them to the game objects.
     * The walls are positioned at the left, right, and top edges of the window.
     */
    private void createWalls() {
        // Define the dimensions of the side walls (left and right) with a fixed width and the height of the
        // window.
        Vector2 sideWallsDimensions = new Vector2(Constants.WALL_WIDTH, windowDimensions.y());
        // Create the left wall at the top-left corner of the window.
        GameObject leftWall = new GameObject(Vector2.ZERO, sideWallsDimensions, null);
        // Create the right wall at the top-right corner of the window.
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - Constants.WALL_WIDTH, 0),
                sideWallsDimensions, null);
        GameObject upWall = new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                Constants.WALL_WIDTH), null);
        // Set the tag for each wall to identify them as walls.
        leftWall.setTag(Constants.WALL_TAG);
        rightWall.setTag(Constants.WALL_TAG);
        upWall.setTag(Constants.WALL_TAG);
        // Add the walls to the game objects in the STATIC_OBJECTS layer.
        gameObjects().addGameObject(leftWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(rightWall, Layer.STATIC_OBJECTS);
        gameObjects().addGameObject(upWall, Layer.STATIC_OBJECTS);
    }

    /**
     * Creates the paddle for the game and adds it to the game objects.
     */
    private void createPaddle() {
        Renderable paddleImage = imageReader.readImage(Constants.PADDLE_IM_PATH, true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage, tools);
        paddle.setCenter(new Vector2(windowDimensions.x() * Constants.CENTER_FACTOR,
                windowDimensions.y() - Constants.MIN_DISTANCE));
        gameObjects().addGameObject(paddle);
        this.paddle = paddle;
    }

    /**
     * Creates the ball for the game and adds it to the game objects.
     */
    private void createBall() {
        Renderable ballImage = imageReader.readImage(Constants.BALL_PATH, true);
        Sound collisionSound = soundReader.readSound(Constants.BALL_SOUND_PATH);
        Ball ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_SIZE, Constants.BALL_SIZE), ballImage,
                collisionSound, tools);
        this.gameObjects().addGameObject(ball);
        this.ball = ball;
    }

    /**
     * Initializes the counters for the game, including the bricks counter and the life counter.
     */
    private void initCounters() {
        bricksCounter = new Counter(numBricksInRow * numRows);
        lifeCounter = new Counter(Constants.INIT_LIVES);
    }

    /**
     * Creates the graphical life counter for the game.
     * The graphical life counter displays the player's remaining lives.
     */
    private void createGraphicLifeCounter() {
        graphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO, Vector2.ZERO, null,
                Constants.INIT_LIVES, lifeCounter, tools);
    }

    /**
     * The main method to start the Bricker game.
     * It initializes the BrickerGameManager with appropriate parameters and runs the game.
     * @param args Command line arguments for customizing the game setup.
     *             If valid arguments are provided, they are used to set the number of bricks in a row and
     *             the number of rows.
     */
    public static void main(String[] args) {
        BrickerGameManager brickerGameManager;
        // Check if the correct number of command line arguments is provided.
        if (args.length == Constants.VALID_USER_INPUT_LEN) {
            // Initialize the game manager with the window title, dimensions,
            // number of bricks in a row, and number of rows from the command line arguments.
            brickerGameManager = new BrickerGameManager(Constants.WINDOW_TITLE, Constants.WINDOW_DIMENSIONS
                    , Integer.parseInt(args[Constants.NUM_BRICKS_IN_ROW]),
                    Integer.parseInt(args[Constants.NUM_ROWS]));
        }
        else {
            // Initialize the game manager with the default window title and dimensions.
            brickerGameManager = new BrickerGameManager(Constants.WINDOW_TITLE,
                    Constants.WINDOW_DIMENSIONS);
        }
        brickerGameManager.run();
    }
}
