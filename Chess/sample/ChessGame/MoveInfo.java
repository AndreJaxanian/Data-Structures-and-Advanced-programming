package sample.ChessGame;
import java.io.Serializable;

/**
 *  when you need to store a copy of the object,
 *  send them to another process which runs on the same system or over the network.
 *  Because you want to store or send an object. It makes storing and sending objects easy.
 *  It has nothing to do with security.
 */
public class MoveInfo implements Serializable {

    int oldX;
    int newX;
    int oldY;
    int newY;

    public MoveInfo() {
        oldX = 0;
        oldY = 0;
        newX = 1;
        newY = 1;

    }

    public MoveInfo(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public String toString()
    {
        return (getCharLabel(oldX+1) + (oldY+1) + " to " + getCharLabel(newX+1) + (newY+1));
    }

    public int getOldX(){return this.oldX;}
    public int getOldY(){return this.oldY;}
    public int getNewX(){return this.newX;}
    public int getNewY(){return this.newY;}

    public void setOldX(int oldX){this.oldX = oldX;}
    public void setOldY(int oldY){this.oldY = oldY;}
    public void setNewX(int newX){this.newX = newX;}
    public void setNewY(int newY){this.newY = newY;}
    public int getGapX(){return this.newX - this.oldX;}
    public int getGapY(){return this.newY - this.oldY;}

    // convert the number 1-26 to char a-z by castling the int to char and adding 64
    public String getCharLabel(int i)
    {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }



}


