package Orbs;

/**
 * Represents an interface for snake orbs in the game.
 * Orbs have methods to manage their spawning, deletion, and state,
 * as well as methods to retrieve their properties.
 */
public interface SnakeOrbs {
    /**
     * Spawns the orb at a random location.
     */
    void randomSpawn();

    /**
     * Deletes the orb, removing it from the game.
     */
    void delete();

    /**
     * Checks if the orb is currently active in the game.
     *
     * @return {@code true} if the orb is active, {@code false} otherwise.
     */
    boolean active();

    /**
     * Gets the score value of the orb.
     *
     * @return the score value associated with this orb.
     */
    int getScore();

    /**
     * Gets the buffer value of the orb, to increment or decrement the snake
     *
     * @return the buffer value associated with this orb.
     */
    int getBuffer();

    /**
     * Gets the x-coordinate of the orb's position.
     *
     * @return the x-coordinate of the orb.
     */
    int getX();

    /**
     * Gets the y-coordinate of the orb's position.
     *
     * @return the y-coordinate of the orb.
     */
    int getY();

}
