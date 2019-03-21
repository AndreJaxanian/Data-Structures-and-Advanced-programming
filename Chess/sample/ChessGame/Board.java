package sample.ChessGame;

import javafx.scene.layout.GridPane;

public class Board extends GridPane {

    private final int NUMBER_ROW_COLUMN = 8 ;
    private Spot[][] board = new Spot[NUMBER_ROW_COLUMN][NUMBER_ROW_COLUMN] ;
    public Spot activeSpot = null ; // check the last spot is clicked ..

    public Board(boolean colorPlayer){
        super();
        // initialize the array 8*8 of spots ...
        for(int i=0 ; i<board[0].length ; i++){
            for(int j=0 ; j<board[1].length ; j++){

                boolean colorSpot = ( (i + j) % 2 != 0 );
                board[i][j] = new Spot(colorSpot , i , j) ;
                // for make a gridPane for each player should rotate 180( degree )
                // for rotate the sum of rowIndexes and columnIndexes for a specified spot are 7 ..
                if(colorPlayer)
                    this.add(board[i][j] ,i ,7 - j) ; // make a gridPane for white player ...
                else
                    this.add(board[i][j]  , 7 - i , j); // make a gridPane for black player ...

                // get values into a eventHandler .
                final int iVal = i ;
                final int jVal = j ;
                // when click on spots do the methods ...
                board[i][j].setOnAction(e -> clickOnSpot(iVal , jVal));
            }
        }
        // now we should set pieces on spots ...
        this.addPieces() ;
    }

    // a method that set pieces at the start of the game ...
    public void addPieces(){

        //for white player ... ( in vision black player )
        for(int i=0 ; i<this.board.length ; i++){
            this.board[i][1].setPiece(new Pawn(true));
        }
        this.board[0][0].setPiece( new Rook  (true) );
        this.board[1][0].setPiece( new Knight(true) );
        this.board[2][0].setPiece( new Bishop(true) );
        this.board[3][0].setPiece( new Queen (true) );
        this.board[4][0].setPiece( new King  (true) );
        this.board[5][0].setPiece( new Bishop(true) );
        this.board[6][0].setPiece( new Knight(true) );
        this.board[7][0].setPiece( new Rook  (true) );

        // for black player ... ( in vision white player )
        for(int i=0 ; i<this.board.length ; i++){
            this.board[i][6].setPiece(new Pawn(false));
        }
        this.board[0][7].setPiece( new Rook  (false) );
        this.board[1][7].setPiece( new Knight(false) );
        this.board[2][7].setPiece( new Bishop(false) );
        this.board[3][7].setPiece( new Queen (false) );
        this.board[4][7].setPiece( new King  (false) );
        this.board[5][7].setPiece( new Bishop(false) );
        this.board[6][7].setPiece( new Knight(false) );
        this.board[7][7].setPiece( new Rook  (false) );
    }


    public Spot getSpotOfBoard(int x_Pos , int y_pos){
        return this.board[x_Pos][y_pos] ;
    }

    public void setActiveSpot(Spot s){

        // remove style
        if(this.activeSpot != null){
            this.activeSpot.getStyleClass().removeAll("active_spot") ;
        }

        this.activeSpot = s ;

        // add style
        if(this.activeSpot != null){
            this.activeSpot.getStyleClass().add("active_spot") ;
        }
    }
    public void setAvailableSpace(Spot s){
        this.activeSpot = s;
        if (this.activeSpot != null)
        this.activeSpot.getStyleClass().add("available_spot");
    }

    public Spot getActiveSpot()
    {
        return this.activeSpot;
    }

    public void clickOnSpot(int iVal, int jVal) {

        Spot clickedSpot = board[iVal][jVal] ;

        // if piece is selected and player didn't click on his own piece ...
        if(activeSpot != null &&
                activeSpot.getPiece() != null &&
                clickedSpot.getPieceColor() != activeSpot.getPieceColor()){
            MoveInfo p;
            p = new MoveInfo(activeSpot.getX_pos(),activeSpot.getY_pos(),iVal,jVal);

        boolean offlineMode = (ChessGUI.connection == null);

            //update gameBoard
            if(this.processMove(p) && !offlineMode){
                // send move to other player
                if(this.sendMove(p)){
                    this.setDisable(true); // the scene of the player that is moved will be disable ...
                }
            }
            this.setActiveSpot(null); // the style of the activeSpot removed and changed to the normal spot ..
        }else {
            // if we selected the piece that it's spot is not active
            if(board[iVal][jVal].getPiece() != null){
                setActiveSpot(board[iVal][jVal]);
            }

        }


    }

    protected boolean pawnValidityCheck(MoveInfo p) {

        Spot lastSpot ;
        Spot newSpot ;
        Piece piece ;

        lastSpot = board[p.getOldX()][p.getOldY()] ;
        newSpot = board[p.getNewX()][p.getNewY()] ;
        piece = lastSpot.getPiece() ;

        if(!piece.getName().equals("Pawn")) {
            return true ;
        }

        if(p.getGapX() == 0){
            // this if is for straight move
            // white piece -> 1 && black piece -> -1
            int colorPieceMode = p.getGapY() / Math.abs(p.getGapY()) ;

            // if there is a piece in front of pawn can't move
            for(int i=1 ; i<=Math.abs(p.getGapY()) ; i++){

                if(board[p.getOldX()][p.getOldY() +(i*colorPieceMode)].isBusy()){
                    return false ;
                }
            }
        }else {
            // this if is for diagonal move
            if(!newSpot.isBusy() || piece.getColor()==newSpot.getPiece().getColor()){
                return false ;
            }
        }
        return true ;
    }

    public boolean canMove(MoveInfo p){

        Spot lastSpot ; // active spot
        Spot nextSpot ; // clicked spot
        Piece piece ;
        MoveList[] moveLists ; // store the moves of the piece ...

        // check the exceptions

        // 1. if p is null
        if(p == null) {return false ;}

        // 2 . last and next spot are in the range
        try {
            lastSpot = board[p.getOldX()][p.getOldY()] ;

        }catch (NullPointerException e){
            return false ;
        }
        try {
            nextSpot = board[p.getNewX()][p.getNewY()] ;
        }catch (NullPointerException e){
            return false ;
        }

        // 3. if last spot ( active spot ) is empty so we cannot move
        if(!lastSpot.isBusy()) {return false ;}

        // exception finished

        // check move of piece in last spot
        piece = lastSpot.getPiece() ;
        moveLists = piece.getPieceMoves() ;
        boolean matchesPieceMoves = false;

        // for ( rook , bishop , queen )
        int numberOfSpotMove  ; // max = 8 ;
        int hMove ; // horizontal
        int vMove ; // vertical

        moves :
        for(MoveList m : moveLists){

            numberOfSpotMove = 1 ;
            if(piece.useSingleMove()==false) {numberOfSpotMove = 8 ;}

            boolean collision = false ;

            for(int i=1 ; i<=numberOfSpotMove ; i++){

                if(collision){ break ;}

                //stretches a base move out to see if it matches the move made
                hMove = m.getX() * i ;
                vMove = m.getY() * i ;
                // the spot that piece can be there
                Spot TempSpot ;
                try{
                    TempSpot = board[p.getOldX() + hMove][p.getOldY() + vMove] ;
                }catch (Exception e){
                    break ;
                }

                // if newSpot is busy collision is occured and if sameColor is not true it can kill that spot
                if(TempSpot.isBusy()){
                    collision = true ;
                    boolean sameColorPiece = TempSpot.getPiece().getColor()==lastSpot.getPiece().getColor() ;
                    if(sameColorPiece) break ;
                }

                // if clicked move is these made moves
                if(p.getGapX() == hMove && p.getGapY() == vMove){
                    matchesPieceMoves = true ;
                    if (!pawnValidityCheck(p)) {return false;}
                    piece.setMove(true);
                    break moves;
                }
            }
        }
        if (!matchesPieceMoves) { return false; }
        return true;
    }

    // return true if move is valid and process is successful
    public boolean processMove(MoveInfo p){
        if(this.canMove(p)){

            Spot lastSpot = board[p.getOldX()][p.getOldY()] ;
            Spot newSpot = board[p.getNewX()][p.getNewY()] ;
            newSpot.setPiece(lastSpot.deletePiece());   // delete piece from the activeSpot and transfer its piece to newSpot
            return true ;

        }else {
            return false ; // invalid move
        }

    }

    public void processOpponentMove(MoveInfo p) {
        if(processMove(p)){
            this.setDisable(false);
        }
    }

    public boolean sendMove(MoveInfo p) {
        try {
            ChessGUI.connection.send(p);
        }catch (Exception e){
            System.err.println("Error: failed to send move");
            return false;
        }
        return true;
    }

}
