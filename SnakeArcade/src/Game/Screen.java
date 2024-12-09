package Game;

import com.codeforall.online.simplegraphics.graphics.Canvas;
import com.codeforall.online.simplegraphics.graphics.Color;
import com.codeforall.online.simplegraphics.graphics.Rectangle;
import com.codeforall.online.simplegraphics.graphics.Text;
import ScoreSystem.ScoreSystem;
import com.codeforall.online.simplegraphics.pictures.Picture;

public class Screen {
    private Text scoreText = new Text(0,0," ");
    private boolean newGame = false;
    private Grid grid;
    private ScoreSystem scoreSystem;
    Picture logo;
    Rectangle gameTypeSelectionRect;

    Text statsTitle;
    Text gameOverText;
    Text HighScore;
    Text stats1;
    Text stats2;
    Text stats3;
    Text stats4;
    Text stats5;

    public Screen(ScoreSystem scoreSystem, Grid grid) {
        this.scoreSystem = scoreSystem;
        this.grid = grid;
    }

    public void startMenu(){ //sponsored by microsoft xD
        Canvas.setMaxX(grid.getCellSize()* grid.getCols()+Grid.PADDING);
        Canvas.setMaxY(grid.getCellSize()* grid.getRows()+Grid.PADDING);
        logo = new Picture();
        logo.load("resources/StartScreen.png");
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
        if(!newGame){
            this.scoreText.grow(20,15);
            this.scoreText.translate(grid.getCols()*grid.getCellSize()-(5*grid.getCellSize()),grid.rowToY(grid.getRows())-7);
        }

        this.scoreText.setText("Score: " + scoreSystem.getScore());
        this.scoreText.setColor(Color.GREEN);
        this.scoreText.draw();
    }

    public void endScreen(){


        Text info;

        gameOverText = new Text(((double) grid.columnToX(grid.getCols()) /2), 0, "GAME OVER");
        gameOverText.translate((double) -gameOverText.getWidth() ,0);
        gameOverText.setColor(Color.RED);
        gameOverText.grow(300,70);
        gameOverText.translate(0,grid.getRows()+((double) gameOverText.getHeight() /2));

        statsTitle = new Text(gameOverText.getX(), gameOverText.getY()+ gameOverText.getHeight(), "Stats: ");

        HighScore =  new Text(statsTitle.getX(), statsTitle.getY()+statsTitle.getHeight(), "High Score: ");

        stats1 = new Text(HighScore.getX(), HighScore.getY()+HighScore.getHeight(), scoreSystem.printHighScoreList(0));
        stats2 = new Text(HighScore.getX(), stats1.getY()+stats1.getHeight()*2, scoreSystem.printHighScoreList(1));
        stats3 = new Text(HighScore.getX(), stats1.getY()+stats1.getHeight()*3, scoreSystem.printHighScoreList(2));
        stats4 = new Text(HighScore.getX(), stats1.getY()+stats1.getHeight()*4, scoreSystem.printHighScoreList(3));
        stats5= new Text(HighScore.getX(), stats1.getY()+stats1.getHeight()*5, scoreSystem.printHighScoreList(4));

        info = new Text(gameOverText.getX(), stats1.getY()+stats1.getHeight()*6, "Press space to start again");

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
         statsTitle.delete();
         gameOverText.delete();
         HighScore.delete();
         stats1.delete();
         stats2.delete();
         stats3.delete();
         stats4.delete();
         stats5.delete();
    }
}
