package bricker.main;

import danogl.util.Vector2;

/**
 * A class to hold constant values used throughout the Bricker game.
 * This includes game settings, object properties, file paths, and game prompts.
 */
public class Constants {
    // main constants
    /**
     * The title of the game window.
     */
    public static final String WINDOW_TITLE = "Bricker";
    /**
     * The valid length of user input arguments
     */
    public static final int VALID_USER_INPUT_LEN = 2;
    /**
     * The dimensions of the game window
     */
    public static final Vector2 WINDOW_DIMENSIONS = new Vector2(700, 500);
    /**
     * Default number of bricks in a row (if not provided by user)
     */
    public static final int NUM_BRICKS_IN_ROW = 0;
    /**
     * Default number of rows of bricks (if not provided by user)
     */
    public static final int NUM_ROWS = 1;

    // ball constants
    /**
     *  Tag for ball objects
     */
    public static final String BALL_TAG = "ball";
    /**
     *  File path for the ball image
     */
    public static final String BALL_PATH = "assets/ball.png";
    /**
     * File path for the ball collision sound
     */
    public static final String BALL_SOUND_PATH = "assets/blop.wav";
    /**
     * Speed of the ball
     */
    public static final int BALL_SPEED = 200;
    /**
     * Size of the ball (width and height)
     */
    public static final int BALL_SIZE = 20;
    /**
     * Multiplier to flip the ball's velocity
     */
    public static final int VELOCITY_FLIP = -1;

    // puck constants
    /**
     *  Tag for puck objects
     */
    public static final String PUCK_TAG = "puck";
    /**
     *  File path for the puck image
     */
    public  static final String PUCK_IM_PATH = "assets/mockBall.png";
    /**
     *  Relative size of the puck compared to the ball
     */
    public static final float PUCK_RELATIVE_SIZE = 0.75f;
    /**
     * Number of pucks
     */
    public static final int PUCK_NUMBER = 2;

    // paddle constants
    /**
     * Tag for paddle objects
     */
    public static final String PADDLE_TAG = "paddle";
    /**
     *  Tag for extra paddle objects
     */
    public static final String EXTRA_PADDLE_TAG = "extraPaddle";
    /**
     * File path for the paddle image
     */
    public static final String PADDLE_IM_PATH = "assets/paddle.png";
    /**
     *  Movement speed of the paddle
     */
    public static final float PADDLE_MOVE_SPEED = 300;
    /**
     * Width of the paddle
     */
    public static final int PADDLE_WIDTH = 100;
    /**
     * Height of the paddle
     */
    public static final int PADDLE_HEIGHT = 15;
    /**
     * Minimum distance of the paddle from the bottom of the window
     */
    public static final int MIN_DISTANCE = 30;
    /**
     * Maximum number of collisions for the paddle
     */
    public static final int MAX_COLLISIONS = 4;
    /**
     * Maximum number of extra paddles
     */
    public static final int MAX_EXTRA_PADDLE = 1;

    // background and walls constants
    /**
     * Tag for wall objects
     */
    public static final String WALL_TAG = "wall";
    /**
     * File path for the background image
     */
    public static final String BACKGROUND_IM_PATH = "assets/DARK_BG2_small.jpeg";
    /**
     *  Width of the walls
     */
    public static final int WALL_WIDTH = 10;

    // bricks constants
    /**
     * File path for the brick image
     */
    public static final String BRICK_IM_PATH = "assets/brick.png";
    /**
     * Default number of bricks in a row
     */
    public static final int DEF_NUM_BRICKS_IN_ROW = 8;
    /**
     * Default number of rows of bricks
     */
    public static final int DEF_NUM_ROWS = 7;
    /**
     * Height of each brick
     */
    public static final int BRICK_HEIGHT = 15;
    /**
     * Spacing between rows of bricks
     */
    public static final float WINDOW_HEIGHT_SPACING  = 100;
    /**
     * Number of collision strategies for bricks
     */
    public static final int NUM_STRATEGIES = 10;
    /**
     * Number of options for the double strategy bricks, when double strategy hasn't been picked already
     */
    public static final int DOUBLE_STRAT_ALL_OPTIONS = 5;

    /**
     * Number of options for the double strategy bricks, when double strategy has already been picked
     */
    public static final int DOUBLE_STRAT_NO_DOUBLE = 4;
    /**
     * Puck strategy random number
     */
    public static final int PUCK_STRAT = 0;
    /**
     * Extra paddle strategy random number
     */
    public static final int EXTRA_PADDLE_STRAT = 1;
    /**
     * Camera strategy random number
     */
    public static final int CAMERA_STRAT = 2;
    /**
     * Extra life strategy random number
     */
    public static final int EXTRA_LIFE_STRAT = 3;
    /**
     * double strategy random number
     */
    public static final int DOUBLE_STRAT = 4;

    // game prompts constants
    /**
     * Message displayed when the player wins
     */
    public static final String WIN_PROMPT = "You win!";
    /**
     * Message displayed when the player loses
     */
    public static final String LOSE_PROMPT = "You lose!";
    /**
     * Message prompting the player to play again
     */
    public static final String PLAY_AGAIN_PROMPT = " Play again?";

    // counter constants
    /**
     * Initial number of lives for the player
     */
    public static final int INIT_LIVES = 3;
    /**
     * Factor to center objects in the window
     */
    public static final float CENTER_FACTOR = 0.5f;

    // graphic life counter constants
    /**
     * Maximum number of lives displayed
     */
    public static final int MAX_LIVES = 4;
    /**
     * Size of the counter display
     */
    public static final float NUM_COUNTER_SIZE = 20;
    /**
     * Space for numeric life counter
     */
    public static final int SPACE = 1;

    // heart constants
    /**
     * Tag for falling heart objects
     */
    public static final String FALLING_HEART_TAG = "fallingHeart";
    /**
     * Horizontal speed of falling hearts
     */
    public static final float FALLING_HEART_SPEED_X = 0;
    /**
     * Vertical speed of falling hearts
     */
    public static final float FALLING_HEART_SPEED_Y = 100;
    /**
     * File path for the heart image
     */
    public static final String HEART_PATH = "assets/heart.png";
    /**
     * Size of the heart image
     */
    public static final float HEART_SIZE = 20;

    // camera constants
    /**
     * Multiplication factor for camera movements
     */
    public static final float CAMERA_MULT_FACTOR = 1.2f;
    /**
     * Number of collisions to reset the camera
     */
    public static final int COLLISIONS_RESET_CAMERA = 4;
}
