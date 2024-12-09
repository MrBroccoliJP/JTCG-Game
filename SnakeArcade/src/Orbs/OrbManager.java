package Orbs;
import ScoreSystem.ScoreSystem;
import Snake.Snake;

public class OrbManager {
    //orb cycle variables
    private int badOrbCycleDuration = 0;
    private int bonusOrbCycleNextAppearance = 0;
    private int bonusOrbCycleDuration = 0;

    private final Snake snake;
    private final ScoreSystem scoreSystem;

    public OrbManager(Snake snake, ScoreSystem scoreSystem) {
        this.snake = snake;
        this.scoreSystem = scoreSystem;
    }
    /**
     * Manages orb spawning, duration, and interactions
     */
    public void orbCheck(SnakeOrbs orb, int cycleCount){
        if(orb != null)//this is to check if the head of the snake "ate" the orb
        {
            if (orb instanceof GoodOrb && !orb.active()) {
                orb.randomSpawn();
            }
            if (orb instanceof BonusOrb && !orb.active()) {
                if (bonusOrbCycleNextAppearance == cycleCount) {
                    bonusOrbCycleDuration = 50 + (int) (Math.random() * 29) + 1;
                    orb.randomSpawn();
                    bonusOrbCycleNextAppearance = -1;
                } else if (bonusOrbCycleNextAppearance == -1) {
                    bonusOrbCycleNextAppearance = cycleCount + 15 + (int) (Math.random() * 29) + 1;
                }
            } else if (orb instanceof BonusOrb && orb.active()) {
                if (bonusOrbCycleDuration == 0) {
                    orb.delete();
                    bonusOrbCycleNextAppearance = -1;
                }
                bonusOrbCycleDuration--;

            }

            if (orb instanceof BadOrb && !orb.active()) {
                badOrbCycleDuration = 30 + (int) (Math.random() * 29) + 1;
                orb.randomSpawn();
            } else if (orb instanceof BadOrb && orb.active()) {
                if (badOrbCycleDuration == 0) {
                    orb.delete();
                }
                badOrbCycleDuration--;
                //System.out.println(badOrbCycleDuration);
            }

            if (orb.active() && (orb.getX() == snake.getHeadX() && orb.getY() == snake.getHeadY())) {
                orb.delete();
                snake.setBlockBuffer(orb.getBuffer());
                scoreSystem.addScore(orb.getScore());
                System.out.println("SNAKE ATE THE AN ORB :" + orb.getScore());

                if (orb instanceof BadOrb) {
                    scoreSystem.addBadOrbsEaten(1);
                } else {
                    scoreSystem.addGoodOrbsEaten(1);
                }
            }
        }
    }
}
