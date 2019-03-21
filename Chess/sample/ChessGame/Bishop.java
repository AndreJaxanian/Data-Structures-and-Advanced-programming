package sample.ChessGame;

public class Bishop extends Piece {

    public Bishop(boolean color) {
        super(color);
    }

    @Override
    protected MoveList[] getPieceMoves() {
        MoveList[] moveLists = {
                MoveList.UP_RIGHT ,
                MoveList.UP_LEFT ,
                MoveList.DOWN_RIGHT ,
                MoveList.DOWN_LEFT
        } ;
        return moveLists ;
    }


    protected boolean useSingleMove() {
        return false;
    }


    protected String getName() {
        return "Bishop";
    }
}
