package sample.ChessGame;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Spot extends Button {

    private int x_pos ; // X-position for each spot
    private int y_pos ; // Y-position for each spot
    private Piece piece ; // if the piece is not in this spot , it is null ..

    public Spot(boolean color , int x_pos , int y_pos){

        super();
        this.x_pos = x_pos ;
        this.y_pos = y_pos ;
        this.piece = null ;
        this.getStyleClass().add("spot_chess");


        if(color) {
            this.getStyleClass().add("light_spot");
        }else {
            this.getStyleClass().add("dark_spot");
        }

    }

    public void setPiece(Piece piece) {

        this.piece = piece;

        if (this.piece != null){
            this.setGraphic(new ImageView(piece.getImage()));
        }
        else{
            this.setGraphic(new ImageView());
        }
    }

    public Piece getPiece() {
        return this.piece;
    }

    // if this spot is busy ( not available ) return true else return false
    public boolean isBusy() {

        return (this.piece !=null);
    }
    // when piece in spot moves , piece on this spot should remove ..
    public Piece deletePiece(){
        Piece temp = this.piece ;
        setPiece(null);
        return temp ;
    }
    public String getPieceColor()
    {
        if(getPiece()!=null){
            return getPiece().getColor();
        }else {
            return "";
        }
    }


    public int getX_pos() {
        return this.x_pos;
    }

    public void setX_pos(int xIn) {
        this.x_pos = xIn;
    }

    public int getY_pos() {
        return this.y_pos;
    }

    public void setY_pos(int yIn) {
        this.y_pos = yIn;
    }
}