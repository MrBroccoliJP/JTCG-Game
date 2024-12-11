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
    protected Movements currentMovement = Movements.NONE;
    protected int rainbowCycleDuration = 0;
    protected boolean konamiMode = false;

    protected LinkedList<SnakeOrbs> activeOrbs = new LinkedList<>();
    protected SoundManager soundManager;

    public BaseDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        this.map = map;
        this.screen = screen;
        this.scoreSystem = scoreSystem;
        this.konamiMode = konamiMode;
        this.soundManager = soundManager;
        this.snake = new Snake(map.columnToX(25), map.rowToY(15));
        this.orbManager = new OrbManager(this, snake, scoreSystem, soundManager);
    }

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

    @Override
    public void gameKeyboardInput(Movements movement) {
        this.movement = movement;
    }

    private void speedStepsCalc() {
        int score = scoreSystem.getScore();
        if (score < 1000) {
            speed = baseSpeed;
        } else if (speed > 100) {
            speed = (int) (baseSpeed - (score - 1000) * 0.01);
        }
    }

    private boolean CollisionCheck() {
        return boundsCollisionCheck() || snake.selfCollisionCheck();
    }

    private boolean boundsCollisionCheck() {
        int leftBound = Map.PADDING;
        int topBound = Map.PADDING;
        int rightBound = (map.getCols() * map.getCellSize()) - Map.PADDING + 1;
        int bottomBound = (map.getRows() * map.getCellSize()) - Map.PADDING + 1;

        return snake.getHeadX() < leftBound || snake.getHeadY() < topBound ||
                snake.getHeadX() > rightBound || snake.getHeadY() > bottomBound;
    }

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

    private void setKonamiMode() {
        if (!snake.getRainbowStatus()) {
            snake.rainbowEffectToggle();
        }
        map.setMapColor(new Color(255, 225, 225));
    }

    @Override
    public void delete() {
        snake.delete();
        deleteOrbs();
    }

    private void deleteOrbs() {
        for (SnakeOrbs orb : activeOrbs) {
            if (orb != null && orb.active()) {
                orb.delete();
            }
        }
    }

    @Override
    public void setRainbowCycleDuration(int duration) {
        this.rainbowCycleDuration += duration;
    }

    @Override
    public void end() {
        System.out.println("Game Over");
    }

    protected abstract void configureOrbs();
}
