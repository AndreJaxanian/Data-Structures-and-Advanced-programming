package sample.ChessGame;

import javafx.scene.image.Image;

public abstract class Piece  {

    protected boolean isMove ; // if it is moved , this value is true ..
    protected boolean color ; // map (boolean , String ) --> (true , white) , (false , black)
    protected Image image ;

    public Piece(boolean color){
        this.color = color ;
        // Pawn for the first move has two choices : 1.one move 2.double move ..
        // If rook and king has no moves we can move as castling ...
        isMove = false ;
        String pathDir = "sample/Assets/Pieces/" ;
        String pathPieces = this.getColor() + "_" + this.getName() + ".png" ;
        this.image = new Image(pathDir + pathPieces) ;
    }

    // getter and setter for the variable ( isMove )
    public boolean getIsmove() {
        return this.isMove;
    }

    public void setMove(boolean move) { this.isMove = move; }

    // get image of piece
    public Image getImage(){
        return this.image ;
    }

    // map (boolean , String ) --> (true , white) , (false , black)
    public String getColor(){
        if(this.color){
            return "white" ;
        }else return "black" ;
    }

    // return the boolean color of the piece
    public boolean isColor() {
        return this.color;
    }


    public String toString() {
        return (this.getName() + " " + this.getColor());
    }

    protected abstract MoveList[] getPieceMoves();
    protected abstract boolean useSingleMove();
    protected abstract String getName(); // the name of the piece ..
}