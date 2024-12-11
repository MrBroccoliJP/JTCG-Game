package Game.Types;

import Game.Map;
import Game.Screen;
import Input.Movements;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Snake.Snake;
import Sound.SoundManager;
import com.codeforall.online.simplegraphics.graphics.Color;

import java.util.LinkedList;

/**
 * BaseDifficulty provides a base for different difficulty levels in the game.
 * It manages the game loop, player inputs, orb interactions, and snake behaviors.
 */
public abstract class BaseDifficulty implements GameType {
    protected Snake snake;
    protected final Map map;
    protected final ScoreSystem scoreSystem;
    protected int cycleCount;
    protected OrbManager orbManager;
    protected Screen screen;

    protected int baseSpeed;
    protected int speed;
    protected Movements movement = Movements.NONE;
    protected Movements currentMovement = Movements.RIGHT;
    protected int rainbowCycleDuration = 0;
    protected boolean konamiMode = false;

    protected LinkedList<SnakeOrbs> activeOrbs = new LinkedList<>();
    protected SoundManager soundManager;

    /**
     * Constructs a BaseDifficulty object with the provided game components.
     *
     * @param map         The game map.
     * @param screen      The screen renderer.
     * @param scoreSystem The scoring system.
     * @param soundManager The sound manager.
     * @param konamiMode  Whether Konami mode (cheat mode) is active.
     */
    public BaseDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        this.map = map;
        this.screen = screen;
        this.scoreSystem = scoreSystem;
        this.konamiMode = konamiMode;
        this.soundManager = soundManager;
        this.snake = new Snake(map.columnToX(25), map.rowToY(15));
        this.orbManager = new OrbManager(this, snake, scoreSystem, soundManager);
    }


    /**
     * Starts the game loop and handles core gameplay mechanics.
     */
    public void start() {
        if (konamiMode) {
            setKonamiMode();
        }

        try {
            while (true) {
                speedStepsCalc();
                screen.drawScore();
                rainbowEffectManager();

                if (movement != Movements.NONE && movement != currentMovement.getOpposite()) {
                    snake.moveSnake(movement);
                    currentMovement = movement;
                    movement = Movements.NONE;
                } else {
                    snake.moveSnake();
                    movement = Movements.NONE;
                }

                if (CollisionCheck()) {
                    break;
                }

                for (SnakeOrbs orb : activeOrbs) {
                    orbManager.orbCheck(orb, cycleCount);
                }

                cycleCount++;
                Thread.sleep(speed);
            }
            end();
        } catch (InterruptedException e) {
            System.err.println("process interrupted: " + e.getMessage());
        }
    }

    /**
     * Handles player keyboard input for controlling the snake.
     *
     * @param movement The movement direction input by the player.
     */
    @Override
    public void gameKeyboardInput(Movements movement) {
        this.movement = movement;
    }


    /**
     * Adjusts the game speed based on the player's score.
     */
    private void speedStepsCalc() {
        int score = scoreSystem.getScore();
        if (score < 1000) {
            speed = baseSpeed;
        } else if (speed > 100) {
            speed = (int) (baseSpeed - (score - 1000) * 0.01);
        }
    }

    /**
     * Checks for collisions with map boundaries or the snake itself.
     *
     * @return True if a collision occurred, false otherwise.
     */
    private boolean CollisionCheck() {
        return boundsCollisionCheck() || snake.selfCollisionCheck();
    }

    /**
     * Checks if the snake has collided with the map boundaries.
     *
     * @return True if a boundary collision occurred, false otherwise.
     */
    private boolean boundsCollisionCheck() {
        int leftBound = Map.PADDING;
        int topBound = Map.PADDING;
        int rightBound = (map.getCols() * map.getCellSize()) - Map.PADDING + 1;
        int bottomBound = (map.getRows() * map.getCellSize()) - Map.PADDING + 1;

        return snake.getHeadX() < leftBound || snake.getHeadY() < topBound ||
                snake.getHeadX() > rightBound || snake.getHeadY() > bottomBound;
    }


    /**
     * Manages the activation and deactivation of the rainbow effect.
     */
    private void rainbowEffectManager() {
        if (rainbowCycleDuration > 0 && !snake.getRainbowStatus()) {
            soundManager.stopAllSounds();
            soundManager.toggleRainbowSound();
            snake.rainbowEffectToggle();
            rainbowCycleDuration--;
            if (!map.isRainbowModeActive() && !konamiMode) {
                map.rainbowModeToggle();
            }
        } else if (rainbowCycleDuration <= 0 && snake.getRainbowStatus()) {
            snake.rainbowEffectToggle();
            soundManager.toggleRainbowSound();
            soundManager.toggleNormalGameStartSound();

            if (map.isRainbowModeActive() && !konamiMode) {
                map.rainbowModeToggle();
            }
        } else if (snake.getRainbowStatus()) {
            rainbowCycleDuration--;
        }
    }

    /**
     * Activates Konami mode [cheat mode] with some visual effects
     * Forces the permanent state of the rainbow effect on the snake
     * Sets a specific color to the map
     */
    private void setKonamiMode() {
        if (!snake.getRainbowStatus()) {
            snake.rainbowEffectToggle();
        }
        map.setMapColor(new Color(255, 225, 225));
    }

    /**
     * Deletes the snake and all active orbs from the game.
     */
    @Override
    public void delete() {
        snake.delete();
        deleteOrbs();
    }

    /**
     * Deletes all active orbs in the game.
     */
    private void deleteOrbs() {
        for (SnakeOrbs orb : activeOrbs) {
            if (orb != null && orb.active()) {
                orb.delete();
            }
        }
    }

    /**
     * Sets the duration for the rainbow cycle effect.
     *
     * @param duration The duration to set.
     */
    @Override
    public void setRainbowCycleDuration(int duration) {
        this.rainbowCycleDuration += duration;
    }

    /**
     * Ends the game
     */
    @Override
    public void end() {
        System.out.println("Game Over");
    }

    /**
     * Configures orbs for the specific difficulty level.
     */
    protected abstract void configureOrbs();
}
