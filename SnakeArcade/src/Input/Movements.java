package Input;

public enum Movements{
    UP, DOWN, LEFT, RIGHT, NONE;

    public Movements getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return NONE; // NONE has no opposite
        }
    }
}
