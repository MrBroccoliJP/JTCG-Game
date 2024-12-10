package Game.Types;

import Game.Map;
import Game.Screen;
import Input.Movements;
import Orbs.*;
import Snake.Snake;
import ScoreSystem.ScoreSystem;
import Sound.Sound;
import Sound.SoundManager;
import com.codeforall.online.simplegraphics.graphics.Color;

import java.util.LinkedList;

public class NormalDifficulty implements GameType {
    private Snake snake;
    private final Map map;
    private final ScoreSystem scoreSystem;
    private int cycleCount;
    private OrbManager orbManager;
    private Screen screen;
    //game speed and movement control
    private int baseSpeed = 200;
    private int speed = 200;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;
    private int rainbowCycleDuration = 0;
    private boolean konamiMode = false;

    //Orb Objects
    private LinkedList<SnakeOrbs> activeOrbs = new LinkedList<>();
    private GoodOrb goodOrb;
    private GoodOrb goodOrb2;
    private GoodOrb goodOrb3;
    private BadOrb badOrb;
    private BonusOrb bonusOrb;
    private BonusOrb bonusOrb2;
    private BonusOrb bonusOrb3;
    private RainbowOrb rainbowOrb;
    private SoundManager soundManager;


    public NormalDifficulty(Map map, Screen screen, ScoreSystem scoreSystem, SoundManager soundManager, boolean konamiMode) {
        this.map = map;
        this.snake = new Snake(map.columnToX(25), map.rowToY(15));
        this.screen = screen;
        this.scoreSystem = scoreSystem;
        this.konamiMode = konamiMode;
        this.soundManager = soundManager;
        orbManager = new OrbManager(this, snake, this.scoreSystem, this.soundManager);
        goodOrb = new GoodOrb(map);
        goodOrb2 = new GoodOrb(map);
        goodOrb3 = new GoodOrb(map);
        badOrb = new BadOrb(map);
        bonusOrb = new BonusOrb(map);
        bonusOrb2 = new BonusOrb(map);
        bonusOrb3 = new BonusOrb(map);
        rainbowOrb = new RainbowOrb(map);

        activeOrbs.add(goodOrb);
        activeOrbs.add(goodOrb2);
        activeOrbs.add(goodOrb3);
        activeOrbs.add(badOrb);
        activeOrbs.add(bonusOrb);
        activeOrbs.add(bonusOrb2);
        activeOrbs.add(bonusOrb3);
        activeOrbs.add(rainbowOrb);
    }

    public void start(){
        try {
            if(konamiMode) {
                setKonamiMode();
            }

            while (true) {  //infinite loop
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
                    //endScreen();
                    break;
                }

                for(SnakeOrbs orbs : activeOrbs) {
                    orbManager.orbCheck(orbs, cycleCount);
                }

                if(konamiMode){
                    rainbowCycleDuration = 2;
                    scoreSystem.addScore(500);
                }

                cycleCount++;
                Thread.sleep(speed);
            }
            end();
        }
        catch (InterruptedException e) {
            System.err.println("process interrupted" + e.getMessage());
        }
    }
    @Override
    public void end() {
        System.out.println("Game Over");
    }

    /**
     * Receives keyboard input for game movement
     */
    @Override
    public void gameKeyboardInput(Movements movement){
        //this method recieves the keyboard input to make it synchronous with the game
        this.movement = movement;
    }

    /**
     * Calculates game speed based on current score
     * Increases difficulty as score progresses
     */
    private void speedStepsCalc(){
        int score = scoreSystem.getScore();

        // Base speed adjustment logic
        if (score < 1000) {
            speed = baseSpeed; // Default speed for scores below 1000
        } else {
            if(speed > 100) {
                speed = (int) (baseSpeed - (score - 1000) * 0.01); // Increase speed for scores above 1000
            }
            //System.out.println("speed change: " + speed);
        }
    }

    /**
     * Checks if snake has had any collision
     * Itself or with the map borders
     */
    public boolean CollisionCheck() {
        return boundsCollisionCheck() || snake.selfCollisionCheck();
    }

    /**
     * Checks if snake has hit game boundaries
     */
    public boolean boundsCollisionCheck() {

        int leftBound = Map.PADDING;
        int topBound = Map.PADDING;
        int rightBound = (map.getCols() * map.getCellSize()) - Map.PADDING+1;
        int bottomBound = (map.getRows() * map.getCellSize()) - Map.PADDING+1;

        return snake.getHeadX() < leftBound || snake.getHeadY() < topBound ||
                snake.getHeadX() > rightBound || snake.getHeadY() > bottomBound;
    }


    @Override
    public void delete() {
        snake.delete();
        deleteOrbs();
    }

    /**
     * Deletes active orbs from the game
     */
    private void deleteOrbs(){
        for(SnakeOrbs orb : activeOrbs) {
            if(orb != null && orb.active()) {
                orb.delete();
            }
        }
    }

    @Override
    public void setRainbowCycleDuration(int addToRainbowCycleDuration) {
        this.rainbowCycleDuration+= addToRainbowCycleDuration;
    }

    private void rainbowEffectManager(){
        if(rainbowCycleDuration > 0 && !snake.getRainbowStatus()){
            soundManager.stopAllSounds();
            soundManager.toggleRainbowSound();
            snake.rainbowEffectToggle();
            rainbowCycleDuration--;
            if(!map.isRainbowModeActive() && !konamiMode){
                map.rainbowModeToggle();
            }
        }
        else if(rainbowCycleDuration <= 0 && snake.getRainbowStatus()){
            snake.rainbowEffectToggle();
            soundManager.toggleRainbowSound();
            soundManager.toggleNormalGameStartSound();

            if(map.isRainbowModeActive() && !konamiMode){
                map.rainbowModeToggle();
            }
        }
        else if(snake.getRainbowStatus()){
            rainbowCycleDuration--;
        }
    }

    private void setKonamiMode(){
        if(!snake.getRainbowStatus()) {
            snake.rainbowEffectToggle();
        }
        map.setMapColor(new Color(255,225,225));
    }
}
