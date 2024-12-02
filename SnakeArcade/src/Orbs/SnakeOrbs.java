package Orbs;

public interface SnakeOrbs {
    void randomSpawn();
    void spawn(int x, int y);
    void delete();
    boolean active();
    int getScore();
    int getBuffer();
    int getX();
    int getY();
    int getCol();
    int getRow();
}
