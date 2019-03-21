package sample.ChessGame;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece  {


    public Pawn(boolean color) {
        super(color);
    }


    protected MoveList[] getPieceMoves() {
        boolean IsWhite = this.color;
        MoveList[] moves = {} ;

        if(IsWhite) {
            ArrayList<MoveList> whiteMoves = new ArrayList<>();
            whiteMoves.add(MoveList.UP);
            whiteMoves.add(MoveList.UP_RIGHT);
            whiteMoves.add(MoveList.UP_LEFT);
            if (!isMove) {
                whiteMoves.add(MoveList.DOUBLE_UP); // at first the pawn has 2 moves ( one or double moves )
            }
                moves = whiteMoves.toArray(moves);
            }


            else{
                ArrayList<MoveList> blackMoves = new ArrayList<>();
                blackMoves.add(MoveList.DOWN);
                blackMoves.add(MoveList.DOWN_RIGHT);
                blackMoves.add(MoveList.DOWN_LEFT);

                if(!isMove){
                    blackMoves.add((MoveList.DOUBLE_DOWN));}

                    moves = blackMoves.toArray(moves);
                }

                return moves;
            }



    protected boolean useSingleMove(){return true;}
    protected String getName(){return "pawn";}
}
