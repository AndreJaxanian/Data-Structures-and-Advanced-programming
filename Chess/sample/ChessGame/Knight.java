package sample.ChessGame;

public class Knight extends Piece {

    public Knight(boolean color) {
        super(color);
    }

    @Override
    protected MoveList[] getPieceMoves() {
        MoveList[] moveLists = {
                MoveList.KNIGHT_DOWN_LEFT ,
                MoveList.KNIGHT_DOWN_RIGHT ,
                MoveList.KNIGHT_UP_LEFT ,
                MoveList.KNIGHT_UP_RIGHT ,
                MoveList.KNIGHT_RIGHT_DOWN ,
                MoveList.KNIGHT_RIGHT_UP ,
                MoveList.KNIGHT_LEFT_DOWN ,
                MoveList.KNIGHT_LEFT_UP
        } ;
        return moveLists ;
    }

    @Override
    protected boolean useSingleMove() {
        return true;
    }

    @Override
    protected String getName() {
        return "Knight";
    }
}
