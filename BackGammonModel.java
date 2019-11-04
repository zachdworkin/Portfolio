//import com.sun.tools.javac.code.Type;

/**
 * Logic for the BackGammon game. Where stacks are used in this game, the front is
 * down. This is somewhat confusing, but helpful in BackGammonGUI,
 * where piles being drawn from the bottom up can be iterated through.
 */
public class BackGammonModel {

    /** The black player. Equal to 0 for array indexing. */
    public static final int BLACK = 0;
    /** The white player. Equal to 1 for array indexing. */
    public static final int WHITE = 1;

    /**
     * The board width. Always 13, but defined as a constant to avoid magic
     * numbers in code.
     */
    public static final int BOARD_WIDTH = 13;

    private int move=0;//used for determining which die has been used

    private Location click;//used for saving clicks

    private int[] games={0,0,0};//black,white,total

    private int[] score={0,0};//black,white,total


    /** Reserves locations, indexed by !color.
     * CAPTURED_LOCATION[0]=BLACK
     * CAPTURED_LOCATION[1]=WHITE */
    public static final Location[] CAPTURED_LOCATION = { new Location(6, 12),
            new Location(7, 12) };

    /** Bins locations, indexed by color.
     * BINS_LOCATION[0]=BLACK
     * BINS_LOCATION[1]=WHITE */
    public static final Location[] BINS_LOCATION = { new Location(0, 0),
            new Location(12, 0) };

    /**Double location*/
    public static final Location DOUBLE_LOCATION = new Location(6,10);

    /**Accept Location*/
    public static final Location ACCEPT_LOCATION = new Location(6,4);

    /**Reject Location*/
    public static final Location REJECT_LOCATION = new Location(6,6);

    /** Player's dice*/
    Die Die1=new Die();
    Die Die2=new Die();
    Die Die3=new Die();

    public boolean doub=false;


    /**
     * Piles of pieces on each square. The entries for the missing corner
     * squares are null.
     */
    private Stack<Integer>[][] board;



    /** BLACK or WHITE. */
    private int currentPlayer;

    /** Reserves, indexed by player color. */
    private Stack<Integer>[] captured;

    // The SuppressWarning tag is necessary because of the "unsafe" generic
    // cast.
    @SuppressWarnings("unchecked")
    public BackGammonModel() {
        // Set up the board
        board = new Stack[13][13];
        //top side
        for (int r = 0; r < BOARD_WIDTH; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                board[r][c] = new Stack<Integer>();
                //for(int i=0; i<2; i++){ board[r][c].addFront(WHITE); }
            }
        }
        captured = new Stack[] { new Stack<Integer>(), new Stack<Integer>() };
    }

    /** sets up the board before a game with the pips in the correct locations*/
    public void setBoard(){
        // Set up the board
        //top side
        for (int r = 0; r < BOARD_WIDTH; r++) {
            for (int c = 0; c < BOARD_WIDTH; c++) {
                Location k = new Location(r, c);
                if (getPile(k).size() > 0) {
                    for (int i = getPile(k).size(); i >=1; i--) {
                        getPile(k).removeBack();
                    }
                }
            }
        }
        //zero out bins
        if(getPile(BINS_LOCATION[0]).size()>0){
            for(int i=getPile(BINS_LOCATION[0]).size();i>=1;i--){
                getPile(BINS_LOCATION[0]).removeBack();
            }
        }
        //zero out bins
        if(getPile(BINS_LOCATION[1]).size()>0){
            for(int i=getPile(BINS_LOCATION[1]).size();i>0;i--){
                getPile(BINS_LOCATION[1]).removeBack();
            }
        }
        //zero out captured locations
        if(getPile(CAPTURED_LOCATION[0]).size()>0){
            for(int i=getPile(CAPTURED_LOCATION[0]).size();i>0;i--){
                getPile(CAPTURED_LOCATION[0]).removeBack();
            }
        }
        //zero out captured locations
        if(getPile(CAPTURED_LOCATION[1]).size()>0){
            for(int i=getPile(CAPTURED_LOCATION[1]).size();i>0;i--){
                getPile(CAPTURED_LOCATION[1]).removeBack();
            }
        }
        //start setup
        for(int i=0; i<2; i++){ board[0][1].addFront(BLACK); }
        for(int i=0; i<5; i++){ board[0][6].addFront(WHITE);}
        for(int i=0; i<3;i++){ board[0][8].addFront(WHITE);}
        for(int i=0; i<5;i++){ board[0][12].addFront(BLACK);}
        //bot side
        for(int i=0; i<2; i++){ board[12][1].addFront(WHITE); }
        for(int i=0; i<5; i++){ board[12][6].addFront(BLACK);}
        for(int i=0; i<3;i++){ board[12][8].addFront(BLACK);}
        for(int i=0; i<5;i++){ board[12][12].addFront(WHITE);}
    }

    /**
     sets the current player
     */
    public void setCurrentPlayer(int player){
        currentPlayer=player;
    }

    /** BLACK or WHITE. */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /** Returns the pile of pieces at a particular location. */
    public Stack<Integer> getPile(Location location) {
        return board[location.getRow()][location.getColumn()];
    }

    /**
     * Returns true if the game is over, i.e., the current player has moved all
     * pips into their bin
     */
    public boolean isGameOver() {
        //if statements simply check if all of the pips are in the bin
        if(getPile(BINS_LOCATION[BLACK]).size()==15){
            return true;
        }
        if(getPile(BINS_LOCATION[WHITE]).size()==15){
            return true;
        }
        return false;
    }

    /**
     * Returns true if it is legal to move from source to destination. Assumes
     * source is a legal source.
     */
    public boolean isLegalMove(Location source, Location destination) {
        if(destination==ACCEPT_LOCATION||destination==REJECT_LOCATION){
            return true;
        }
        int d = source.getDistanceTo(destination);
        //if they are trying to move out of the captured location, update the source location to be from the bin on the correct side of the board for moving purposes
        if((source==CAPTURED_LOCATION[currentPlayer]) && (getPile(CAPTURED_LOCATION[currentPlayer]).size()>0)) {
            if(currentPlayer==BLACK){
                Location A = new Location(0,0);
                return isLegalMove(A,destination);
            }else{
                Location A = new Location(12,0);
                return isLegalMove(A,destination);
            }
        }
        //only checks for end game scenarios
        if( (d <= Die1.getValue()) || (d <= Die2.getValue()) ){
            if( (destination.getColumn()==0) && (isLegalMoveToBin(source,destination)) && (isEndScenario())){// && (isLegalBinMove(source,destination))) ){
                return true;
            }
        }
        //checks normal game moves
        if (((d <= Die1.getValue() + Die2.getValue()) && (d == Die1.getValue()) || (d == Die2.getValue()))) {
            if(getPile(destination).size()!=0) {//checks for null pointers for next statements
                int top = getPile(destination).removeBack();//finds the color value of the stack
                getPile(destination).addBack(top);
                if ((!isLonePiece(destination)) && ((top != currentPlayer))) {//if we are not trying to move onto a capturable piece
                    //check to make sure that we are moving to a stack of our own color
                    return false;
                }
            }
            return movePattern(source, destination);
        }
        return false;

    }


    /**
     if we are in an end game scenario, see if it is okay to move our pip into the bin
     */
    public boolean isLegalMoveToBin(Location source, Location destination){
        int d = source.getDistanceTo(destination);
        int f=0;
        for(int x=1;x<=6;x++){//finds the greatest column with pieces
            if(currentPlayer==BLACK) {
                Location A = new Location(12, x);
                if (getPile(A).size() > 0) {
                    int top=getPile(A).removeBack();
                    getPile(A).addBack(top);
                    if(top==BLACK) {
                        f = x;//save the greatest column
                    }
                }
            }else{
                Location A = new Location(0, x);
                if (getPile(A).size() > 0) {
                    int top=getPile(A).removeBack();
                    getPile(A).addBack(top);
                    if(top==WHITE) {
                        f = x;//save the greatest column
                    }
                }
            }
        }
        if(getDie1Value()>=getDie2Value()){
            if(getDie1Value()>=f){
                return true;
            }
        }
        if(getDie1Value()<=getDie2Value()) {
            if (getDie2Value() >= f) {
                return true;
            }
        }
        if ((getDie1Value()>=f) || (getDie2Value()>=f) ){
            if( (d>=Die1.getValue()) || (d>=Die2.getValue()) ){
                return true;
            }
        }else{
            if( ( (d==Die1.getValue()) || (d==Die2.getValue()) )){// && (source.getColumn()==d)) {
                return true;
            }
        }
        return false;
    }

    /**
     checks every possible location for a current player on the board, and sees if they can move anywhere based off of their dice roll
     */
    public boolean isNoMoves(){
        if(isEndScenario()){//over arching game ending scenario
            return false;
        }else{
            for(int i=0;i<13;i+=12) {//get every source location
                for (int j = 1; j < 13; j++) {
                    Location S = new Location(i, j);
                    if(getPile(S).size()!=0) {//rids of null pointer errors
                        int top = getPile(S).removeBack();//finds the color of the stack
                        getPile(S).addBack(top);
                        if (top == currentPlayer) {
                            if (isLegalSource(S)) {//if there is a legal source, then there is a move, meaning we return false
                                return false;
                            }
                        }
                    }
                    //checks captured locations because if we can move out of the captured location then we have a legal move
                    //also checks to see if we can't move from a captured location
                    if(getPile(CAPTURED_LOCATION[currentPlayer]).size()!=0){
                        int top=getPile(CAPTURED_LOCATION[currentPlayer]).removeBack();
                        getPile(CAPTURED_LOCATION[currentPlayer]).addBack(top);
                        if(top == currentPlayer) {
                            if (isLegalMove(CAPTURED_LOCATION[currentPlayer], S)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;//return true if every other check for falsity fails
    }

    /**finds if there is a legal move to a bin, if not, it is a legal end scenario*/
    public boolean isEndScenario(){//if we are trying to move to a bin
        int counter=0;//counts pips outside of the final 6 triangle area on your side
        if (currentPlayer == BLACK) {//find current player
            if (getPile(CAPTURED_LOCATION[BLACK]).size() != 0) {//look in captured locations to see if there are pips there
                counter++;
            }
            for (int i = 0; i < 13; i += 12) {//check every space
                if (i == 12) {
                    for (int j = 7; j < 13; j++) {//check every space outside of the 6 next to your bin on your side
                        Location A = new Location(i, j);
                        if (getPile(A).size() != 0) {//makes it so we can't get a null pointer error
                            int top=getPile(A).removeBack();
                            getPile(A).addBack(top);
                            if(top==currentPlayer) {
                                counter++;
                            }
                        }
                    }
                } else {
                    for (int j = 1; j < 13; j++) {//check every space on the opposite side of your bin
                        Location A = new Location(i, j);
                        if (getPile(A).size() != 0) {//makes it so we can't get a null pointer error
                            int top=getPile(A).removeBack();
                            getPile(A).addBack(top);
                            if(top==currentPlayer) {
                                counter++;
                            }
                        }
                    }
                }
            }
        } else {
            if (getPile(CAPTURED_LOCATION[WHITE]).size() != 0) {//check to see if there are pips in the captured areas
                counter++;
            }
            for (int i = 0; i < 13; i += 12) {//check every space
                if (i == 0) {
                    for (int j = 7; j < 13; j++) {
                        Location A = new Location(i, j);
                        if (getPile(A).size() != 0) {//get rid of null pionter errors
                            int top=getPile(A).removeBack();
                            getPile(A).addBack(top);
                            if(top==currentPlayer) {
                                counter++;
                            }
                        }
                    }
                } else {
                    for (int j = 1; j < 13; j++) {//check every space
                        Location A = new Location(i, j);
                        if (getPile(A).size() != 0) {//get rid of null pointer errors
                            int top=getPile(A).removeBack();
                            getPile(A).addBack(top);
                            if(top==currentPlayer) {
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        if(counter>0) {//if there was a pip outside of the final 6 triangles on your side, its not legal to move into the bin
            return false;
        }
        return true;
    }

    /** finds patter of moving for a color (clockwise for black) (counter clockwise for white)*/
    public boolean movePattern(Location source, Location destination){
        if(isEndScenario()) {
            if ((currentPlayer == BLACK)) {//if im am the black player
                if ((destination.getRow() == 0) && (source.getRow() == 0)) {//if we are on the top row, and I am moving the correct way
                    if (destination.getColumn() > source.getColumn()) {
                        return true;
                    }
                }
                if ((destination.getRow() == 12) && (source.getRow() == 12)) {//if we are on the bottom row, and I am moving in the corret way
                    if (destination.getColumn() < source.getColumn()) {
                        return true;
                    }
                }
                if ((destination.getRow() == 12) && (source.getRow() == 0)) {//if I am on the top going to the bot
                    return true;
                }
            }
            if ((currentPlayer == WHITE)) {
                if ((destination.getRow() == 0) && (source.getRow() == 0)) {//if on the top moving on the top
                    if (destination.getColumn() < source.getColumn()) {
                        return true;
                    }
                }
                if ((destination.getRow() == 12) && (source.getRow() == 12)) {//if on the bot moving on the bot
                    if (destination.getColumn() > source.getColumn()) {
                        return true;
                    }
                }
                if ((destination.getRow() == 0) && (source.getRow() == 12)) {//if on the bot moving to the top
                    return true;
                }
            }
            return false;
        }else{
            if ((currentPlayer == BLACK)) {//if im am the black player
                if ((destination.getRow() == 0) && (source.getRow() == 0)) {//if we are on the top row, and I am moving the correct way
                    if (destination.getColumn() > source.getColumn()) {
                        return true;
                    }
                }
                if ((destination.getRow() == 12) && (source.getRow() == 12)) {//if we are on the bottom row, and I am moving in the corret way
                    if (destination.getColumn() < source.getColumn()&&(destination.getColumn()!=0) ){
                        return true;
                    }
                }
                if ((destination.getRow() == 12) && (source.getRow() == 0)) {//if I am on the top going to the bot
                    return true;
                }
            }
            if ((currentPlayer == WHITE)) {
                if ((destination.getRow() == 0) && (source.getRow() == 0)) {//if on the top moving on the top
                    if (destination.getColumn() < source.getColumn()&&(destination.getColumn()!=0)) {
                        return true;
                    }
                }
                if ((destination.getRow() == 12) && (source.getRow() == 12)) {//if on the bot moving on the bot
                    if (destination.getColumn() > source.getColumn()&&(destination.getColumn()!=0)) {
                        return true;
                    }
                }
                if ((destination.getRow() == 0) && (source.getRow() == 12)) {//if on the bot moving to the top
                    return true;
                }
            }
            return false;
        }
    }

    /** Returns true if you are moving onto a lone piece of the opposite color*/
    public boolean isLonePiece(Location destination) {
        if (getPile(destination).size() == 1) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the current player can move from source (which might be
     * the CAPTURED_LOCATION).
     */
    public boolean isLegalSource(Location source) {
        if(click==DOUBLE_LOCATION && move==0 && Die3.getValue()!=64){
            return true;
        }
        if(currentPlayer==BLACK){
            Location A = new Location(12,0);
            int top=0;
            if(getPile(source).size()!=0){
                top=getPile(source).removeBack();
                getPile(source).addBack(top);
            }
            if ((source.getColumn() <= 6) && (source.getColumn()>0) && (source.getRow()==12) && (isEndScenario()) && (isLegalMoveToBin(source,A)) && ((source.getColumn()<=Die1.getValue()) || (source.getColumn()<=Die2.getValue())) && (getPile(source).size()!=0) && (top==BLACK) ) {//if we are in a column less than 6 and are trying to move into a bin
                return true;
            }
        }else{
            int top=1;
            if(getPile(source).size()!=0){
                top=getPile(source).removeBack();
                getPile(source).addBack(top);
            }
            Location A = new Location(0,0);
            if ((source.getColumn() <= 6) && (source.getColumn()>0) && (source.getRow()==0) && (isEndScenario()) && (isLegalMoveToBin(source,A)) && ((source.getColumn()<=Die1.getValue()) || (source.getColumn()<=Die2.getValue()))  && (getPile(source).size()!=0) && (top==WHITE) ) {//if we are in a column less than 6 and are trying to move into a bin
                return true;
            }
        }
        //check if source is in the captured location
        if(getPile(CAPTURED_LOCATION[currentPlayer]).size()>0){//check the captured locations, if there is one in there, return true if source equals captured location
            return source==CAPTURED_LOCATION[currentPlayer];
        }
        //check the source pile
        Stack<Integer> pile = getPile(source);
        if (pile == null || pile.isEmpty()) {
            return false;//if it was empty
        }
        //if it wasn't empty we can find out what color it is
        int top = pile.removeBack();
        pile.addBack(top);
        if(top==currentPlayer) {//if it is our color stack, check every possible move from that location
            for (int i = 0; i < BOARD_WIDTH; i += 12) {
                for (int j = 1; j < BOARD_WIDTH; j++) {
                    Location A = new Location(i, j);
                    if (isLegalMove(source, A)) {//if there is a move we can make, conditional on dice values, it means we move from there and we can click there
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Returns true if location is on the board. */
    public boolean isOnBoard(Location location) {
        return location.getRow() >= 0 && location.getRow() < BOARD_WIDTH
                && location.getColumn() >= 0 && location.getColumn() < BOARD_WIDTH
                && getPile(location) != null;
    }

    /**
     * Moves pieces from source (which might be the CAPTURED_LOCATION) to
     * destination and resolves any consequences.
     */
    public void move(Location source, Location destination) {
        if(isLegalMove(source,destination)) {
            if(isLonePiece(destination)){//if we are trying to move onto a capturable piece
                int top = getPile(destination).removeBack();//finds the color value of the stack
                if(top!=currentPlayer){//if it is a lone piece and not our color, send it to the captured location
                    getPile(CAPTURED_LOCATION[Math.abs(currentPlayer-1)]).addBack(Math.abs(currentPlayer-1));
                }else{
                    getPile(destination).addBack(top);
                }
            }
            getPile(destination).addBack(currentPlayer);
            getPile(source).removeBack();
        }
        if(source==CAPTURED_LOCATION[currentPlayer]) {//has to reset source if your original source was a captured location
            if (currentPlayer == BLACK) {
                Location A = new Location(0, 0);//black moving onto the board from (captured) whites bin
                move = A.getDistanceTo(destination);
            }
            if(currentPlayer == WHITE){
                Location A = new Location(12, 0);//white moving onto the board from (captured)blacks bin
                move = A.getDistanceTo(destination);
            }
        }else {
            move = source.getDistanceTo(destination);//updates move for backgammon to know which dice value got used
        }
    }

    /** Toggles the current color to play between BLACK and WHITE. */
    public void toggleColorToPlay() {
        currentPlayer = 1 - currentPlayer;
    }

    /** Rolls the Dice*/
    public void roll() {
        int n = StdRandom.uniform(1, 7);
        int m = StdRandom.uniform(1, 7);
        Die1.setValue(n);
        Die2.setValue(m);
    }

    /** returns value of die 1*/
    public int getDie1Value() {
        return Die1.getValue();
    }

    /** returns value of die 2*/
    public int getDie2Value() {
        return Die2.getValue();
    }

    /** returns value of move*/
    public int getMoveValue() {
        return move;
    }

    /**sets a value of move*/
    public void setMoveValue(int a){
        move=a;
    }

    /*
    sets the vaue of click
     */
    public void setClick(Location result) {
        click=result;
    }

    /*returns the x-value of click*/
    public int getClickX(){
        return click.getColumn();
    }
    /*returns the y-value of click*/
    public int getClickY(){
        return click.getRow();
    }

    /** sets the total number of games*/
    public void setTotalGames(int total){
        this.games[2]=total;
    }

    /**increases the number of wins a player has*/
    public void setWins(int player){
        boolean flag=false;
        if(isEndScenario()) {
            toggleColorToPlay();
            if (!isEndScenario()) {
                if (doub == false) {
                    //backgammons
                    if (player == 0) {
                        for (int i = 1; i < 7; i++) {
                            Location A = new Location(12, i);
                            if (getPile(A).size() != 0) {
                                int top = getPile(A).removeBack();
                                getPile(A).addBack(top);
                                if (top != player) {
                                    flag = true;
                                    if (Die3.getValue() == 1) {
                                        this.games[player] += 3;
                                    } else {
                                        this.games[player] += 3 + Die3.getValue();
                                    }
                                }
                            }
                        }
                        if (flag != true) {
                            //gammons
                            if (Die3.getValue() == 1) {
                                this.games[player] += 2;
                            } else {
                                this.games[player] += 2 + Die3.getValue();
                            }
                        }
                    } else {
                        for (int i = 1; i < 7; i++) {
                            Location A = new Location(0, i);
                            if (getPile(A).size() != 0) {
                                int top = getPile(A).removeBack();
                                getPile(A).addBack(top);
                                if (top != player) {
                                    flag = true;
                                    if (Die3.getValue() == 1) {
                                        this.games[player] += 3;
                                    } else {
                                        this.games[player] += 3 + Die3.getValue();
                                    }
                                }
                            }
                        }
                        if (flag != true) {
                            //gammons
                            if (Die3.getValue() == 1) {
                                this.games[player] += 2;
                            } else {
                                this.games[player] += 2 + Die3.getValue();
                            }
                        }
                    }
                }
                toggleColorToPlay();
            }
        }else {
            toggleColorToPlay();
            this.games[player] += Die3.getValue();
        }

    }

    /**returns the number of games a player has won, or the total number of games they are playing to*/
    public int getGames(int arrayValue){
        return games[arrayValue];
    }

    /**returns the number of games a player has won, or the total number of games they are playing to*/
    public String getScore(int player){
        return Integer.toString(score[player]);
    }

    public void doubleDie(){
        int j=Die3.getValue();
        if(j==1){
            j++;
            Die3.setValue(j);
        }else if(j==64){
            //nothing
        }else{
            j=j*2;//double j;
            Die3.setValue(j);
        }
    }

    /** counts up the score for each player*/
    public void countScore(){
        //reinitialize score every time so that it doesn't keep increasing off of the last score
        score[0]=0;
        score[1]=0;
        for(int i=0;i<BOARD_WIDTH;i+=12){
            for(int j=1;j<BOARD_WIDTH;j++){
                Location k=new Location(i,j);
                if(getPile(k).size()!=0){
                    int top=getPile(k).removeBack();
                    getPile(k).addBack(top);
                    if(top==0){//if its a black piece
                        int d=k.getDistanceTo(BINS_LOCATION[1]);
                        score[0]=score[0]+(getPile(k).size()*d);
                    }
                    if(top==1){//if its a white piece
                        int d=k.getDistanceTo(BINS_LOCATION[0]);
                        score[1]=score[1]+ (getPile(k).size()*d);
                    }
                }
            }
        }
        //captured locations add 25 points to score
        if(getPile(CAPTURED_LOCATION[1]).size()!=0){
            score[1]=score[1]+(25*getPile(CAPTURED_LOCATION[1]).size());
        }
        if(getPile(CAPTURED_LOCATION[0]).size()!=0){
            score[0]=score[0]+(25*getPile(CAPTURED_LOCATION[0]).size());
        }
    }

    /** returns the value of doub*/
    public boolean getDoub(){
        return doub;
    }

    /** returns the value of doub*/
    public void setDoub(boolean doub){
        this.doub=doub;
    }
}

