package Game.Types;

import Game.Grid;
import Game.Game;
import Game.Screen;
import Input.Movements;
import Orbs.*;
import ScoreSystem.ScoreSystem;
import Snake.Snake;

import java.util.LinkedList;

public class MediumDifficulty implements GameType{
    private Snake snake;
    private final Grid grid;
    private final ScoreSystem scoreSystem;
    private int cycleCount;
    private OrbManager orbManager;
    private Screen screen;
    //game speed and movement control
    private int baseSpeed = 400;
    private int speed = 400;
    private Movements movement = Movements.NONE;
    private Movements currentMovement = Movements.NONE;
    private int rainbowCycleDuration = 0;

    //Orb Objects
    private LinkedList<SnakeOrbs> activeOrbs = new LinkedList<>();
    private GoodOrb goodOrb;
    private BadOrb badOrb;
    private BonusOrb bonusOrb;
    private RainbowOrb rainbowOrb;


    public MediumDifficulty(Grid grid, Screen screen, ScoreSystem scoreSystem) {
        this.grid = grid;
        this.snake = new Snake(grid.columnToX(25), grid.rowToY(15));
        this.screen = screen;
        this.scoreSystem = scoreSystem;
        orbManager = new OrbManager(this, snake, scoreSystem);
        goodOrb = new GoodOrb(grid);
        badOrb = new BadOrb(grid);
        bonusOrb = new BonusOrb(grid);
        rainbowOrb = new RainbowOrb(grid);
        activeOrbs.add(goodOrb);
        activeOrbs.add(badOrb);
        activeOrbs.add(bonusOrb);
        activeOrbs.add(rainbowOrb);
    }

    public void start(){
        try {
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
                //orbManager.orbCheck(badOrb, cycleCount);
                //orbManager.orbCheck(bonusOrb, cycleCount);

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
            speed = 200; // Default speed for scores below 1000
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

        int leftBound = Grid.PADDING;
        int topBound = Grid.PADDING;
        int rightBound = (grid.getCols() * grid.getCellSize()) - Grid.PADDING+1;
        int bottomBound = (grid.getRows() * grid.getCellSize()) - Grid.PADDING+1;

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

    public void rainbowEffectManager(){
        if(rainbowCycleDuration > 0 && !snake.getRainbowStatus()){
            snake.rainbowEffectToggle();
            grid.rainbowModeToggle();
            rainbowCycleDuration--;
        }
        else if(rainbowCycleDuration <= 0 && snake.getRainbowStatus()){
            snake.rainbowEffectToggle();
            grid.rainbowModeToggle();
        }
        else if(snake.getRainbowStatus()){
            rainbowCycleDuration--;
        }
    }
}
