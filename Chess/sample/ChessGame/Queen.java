package sample.ChessGame;

public class Queen extends Piece {

    public Queen(boolean color) {
        super(color);
    }

    @Override
    protected MoveList[] getPieceMoves() {
        MoveList[] moveLists = {
                MoveList.UP,
                MoveList.UP_RIGHT,
                MoveList.RIGHT,
                MoveList.DOWN_RIGHT,
                MoveList.DOWN,
                MoveList.DOWN_LEFT,
                MoveList.LEFT,
                MoveList.UP_LEFT,
        } ;
        return moveLists ;
    }

    @Override
    protected boolean useSingleMove() {
        return false;
    }

    @Override
    protected String getName() {
        return "Queen";
    }
}
