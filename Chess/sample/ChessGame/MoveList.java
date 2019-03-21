package sample.ChessGame;

/**
 * Why do we use Enum for our moves?
 * Advantages :
can:
 Set of Constant declaration
 Restrict input parameter in method
 be usable in switch-case
 Type safety. You can declare a function argument, return type, class member or local variable to be a particular Enum type and the compiler will enforce type safety;
 Enums are basically classes. They can implement interfaces, have behaviour and so on.
 */
public enum MoveList {

    UP (0,1),
    UP_RIGHT(1, 1),
    RIGHT(1, 0),
    DOWN_RIGHT(1, -1),
    DOWN(0,-1),
    DOWN_LEFT(-1, -1),
    LEFT(-1, 0),
    UP_LEFT(-1, 1),

    KNIGHT_LEFT_UP(-2, 1),
    KNIGHT_UP_LEFT(-1, 2),
    KNIGHT_UP_RIGHT(1, 2),
    KNIGHT_RIGHT_UP(2, 1),

    KNIGHT_RIGHT_DOWN(2, -1),
    KNIGHT_DOWN_RIGHT(1, -2),
    KNIGHT_DOWN_LEFT(-1, -2),
    KNIGHT_LEFT_DOWN(-2, -1),

    DOUBLE_UP(0, 2),
    DOUBLE_DOWN(0, -2);


    private int x ;
    private int y ;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equal(MoveList m) {
        return (this.x == m.getX()  && this.y == m.getY()) ;
    }

    MoveList(int x, int y) {
        this.x = x;
        this.y = y;
    }

}