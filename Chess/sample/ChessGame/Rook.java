package sample.ChessGame;

public class Rook extends Piece  {

    public Rook(boolean color) {
        super(color);
    }

    @Override
    protected MoveList[] getPieceMoves() {
        MoveList[] moves = {
                MoveList.UP ,
                MoveList.LEFT ,
                MoveList.DOWN ,
                MoveList.RIGHT
        } ;
        return moves ;
    }

    @Override
    protected boolean useSingleMove() {
        return false;
    }

    @Override
    protected String getName() {
        return "Rook";
    }
}
