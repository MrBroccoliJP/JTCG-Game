package Game;

import Game.Types.GameType;
import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.graphics.Text;
import ScoreSystem.ScoreSystem;
import com.codeforall.online.simplegraphics.pictures.Picture;


public class Screen {
    private Text scoreText = new Text(0,0," ");
    private boolean newGame = false;
    private Map map;
    private ScoreSystem scoreSystem;
    Picture logo;
    Rectangle gameTypeSelectionRect;

    Text thisScore;
    Text statsTitle;
    Text gameOverText;
    Text HighScore;
    Text stats1;
    Text stats2;
    Text stats3;
    Text stats4;
    Text stats5;
    Text info;

    public Screen(ScoreSystem scoreSystem, Map map) {
        this.scoreSystem = scoreSystem;
        this.map = map;

        this.scoreText.grow(20,15);
        this.scoreText.translate(map.getCols()* map.getCellSize()-(5* map.getCellSize()), map.rowToY(map.getRows())-7);
    }

    public void startMenu(){ //sponsored by microsoft xD
        Canvas.setMaxX(map.getCellSize()* map.getCols()+ Map.PADDING);
        Canvas.setMaxY(map.getCellSize()* map.getRows()+ Map.PADDING);
        logo = new Picture();
        logo.load(ResourcePath.RESOURCE_PATH + "StartScreen.png");
        logo.draw();
        drawStartMenuRect(-1);
    }
    public void drawStartMenuRect(int gameTypeSelection){

        switch (gameTypeSelection) {
            case 0:
                gameTypeSelectionRect.delete();
                gameTypeSelectionRect = new Rectangle(413,487,172,50);
                break;
            case 1:
                gameTypeSelectionRect.delete();
                gameTypeSelectionRect = new Rectangle(413,539,172,52);
                break;
            case 2:
                gameTypeSelectionRect.delete();
                gameTypeSelectionRect = new Rectangle(413,591,172,50);
                break;
            default:
                gameTypeSelectionRect = new Rectangle(420,489,50,172);
                break;
        }
        gameTypeSelectionRect.setColor(Color.BLUE);
        gameTypeSelectionRect.draw();
    }
    public void startMenuDelete(){
        logo.delete();
        gameTypeSelectionRect.delete();
    }

    /**
     * Draws current score on screen
     */
    public void drawScore() {
        this.scoreText.setText(" ");
        this.scoreText.setText("Score: " + scoreSystem.getScore());
        this.scoreText.setColor(Color.GREEN);
        this.scoreText.draw();
    }

    public void endScreen(GameType gameType){

        gameOverText = new Text(((double) map.columnToX(map.getCols()) /2), 0, "GAME OVER");
        gameOverText.translate((double) -gameOverText.getWidth() ,0);
        gameOverText.setColor(Color.RED);
        gameOverText.grow(300,70);
        gameOverText.translate(0, map.getRows()+((double) gameOverText.getHeight() /2));

        thisScore = new Text(gameOverText.getX(), gameOverText.getY()+ gameOverText.getHeight(), "Your Score: " + scoreSystem.getStat());

        statsTitle = new Text(thisScore.getX(), thisScore.getY()+ thisScore.getHeight()*2, "High Score Table:");

        HighScore =  new Text(statsTitle.getX(), statsTitle.getY()+statsTitle.getHeight(), "All time Greatest Score: ");

        stats1 = new Text(HighScore.getX()+ HighScore.getWidth(), HighScore.getY(), scoreSystem.printHighScoreList(gameType,0));
        stats2 = new Text(stats1.getX(), stats1.getY()+stats1.getHeight(), scoreSystem.printHighScoreList(gameType,1));
        stats3 = new Text(stats2.getX(), stats1.getY()+stats1.getHeight()*2, scoreSystem.printHighScoreList(gameType,2));
        stats4 = new Text(stats3.getX(), stats1.getY()+stats1.getHeight()*3, scoreSystem.printHighScoreList(gameType, 3));
        stats5= new Text(stats4.getX(), stats1.getY()+stats1.getHeight()*4, scoreSystem.printHighScoreList(gameType, 4));

        info = new Text(gameOverText.getX(), stats1.getY()+stats1.getHeight()*6, "Press space to start again");

        thisScore.draw();
        gameOverText.draw();
        statsTitle.draw();
        HighScore.draw();
        stats1.draw();
        stats2.draw();
        stats3.draw();
        stats4.draw();
        stats5.draw();
        info.draw();

    }

    public void clear(){
         thisScore.delete();
         statsTitle.delete();
         gameOverText.delete();
         HighScore.delete();
         stats1.delete();
         stats2.delete();
         stats3.delete();
         stats4.delete();
         stats5.delete();
         info.delete();
         scoreText.delete();
    }
}
