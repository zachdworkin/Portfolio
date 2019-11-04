import java.awt.*;

/** Graphic user interface for the Back Gammon game. */
public class BackGammon {

    /**
     * Color used to draw the squares.
     */
    public static final java.awt.Color TAN = new java.awt.Color(245, 222, 179);

    /**
     * Color used to draw the squares.
     */
    public static final java.awt.Color BROWN = new java.awt.Color(160, 82, 42);

    /**
     * Hightlight Color
     */
    public static final java.awt.Color PINK = new java.awt.Color(255, 165, 165);

    /**
     * Used for background.
     */
    public static final java.awt.Color DARK_GREEN = new java.awt.Color(0, 64,0);

    /**
     * Used for Bins.
     */
    public static final java.awt.Color BLACK = new java.awt.Color(0, 0, 0);

    /**
     * Used for Bins.
     */
    public static final java.awt.Color WHITE = new java.awt.Color(250,250,250);

    /**
     * Color used to draw the Dice
     */
    public static void main(String[] args) {
        new BackGammon().run();
    }

    /**
     * The associated game instance.
     */
    private BackGammonModel model;

    public BackGammon() {
        model = new BackGammonModel();
    }

    /**
     * Draws the state of the model, including instructions.
     *
     * @param source If non-null, drawing highlights all legal moves from source.
     */
    public void draw(String instructions, Location source) {
        StdDraw.clear();
        StdDraw.setPenColor(DARK_GREEN);
        StdDraw.filledSquare(0, 0, 13);
        Color color = null;
        for (int c = 1; c < BackGammonModel.BOARD_WIDTH; c++) {
            for (int r = 0; r < BackGammonModel.BOARD_WIDTH; r += 12) {
                Location destination = new Location(r, c);
                if (c % 2 != 0) {
                    color = TAN;
                } else {
                    color = BROWN;
                }
                drawTriangle(model.getPile(destination), c, 13 - r, source != null && model.isLegalMove(source, destination), color);
            }
        }
        StdDraw.setPenColor(BROWN);//makes background
        StdDraw.filledRectangle(0, 0, 1, 13);//background area
        StdDraw.setPenColor(BLACK);//makes dice area
        StdDraw.filledRectangle(0.5, 6.5, .4, 1);//background of dice area
        StdDraw.rectangle(11.25, 6.5, 1.65, 1.5);//captured area outline
        StdDraw.filledRectangle(12.5, 6.5, .4, 0.05);//inner divider of captured area
        StdDraw.setPenColor(BROWN);//dice divider
        StdDraw.filledRectangle(7, 6.5, 0.025, 6.5);//middle line
        StdDraw.filledRectangle(0, 6.5, 1, 0.05);//inner divider of dice area
        StdDraw.filledRectangle(11.25, 6.5, 1.65, 1.5);//captured area brown
        StdDraw.setPenColor(BLACK);
        StdDraw.rectangle(11.25, 6.5, 1.65, 1.5);//captured area outline
        StdDraw.rectangle(12.5, 6.5, .4, 1.5);//captured area outline
        StdDraw.line(9.6,6.5,12.25,6.5);//points line
        StdDraw.setPenColor(TAN);//captured area
        StdDraw.filledRectangle(12.5, 6.5, .275, 1.3);//captured area tan
        StdDraw.setPenColor(BROWN);//captured area divider
        StdDraw.filledRectangle(12.5, 6.5, .4, 0.1);//captured area inner rectangle
        StdDraw.setPenColor(BLACK);
        StdDraw.filledRectangle(10.85,6.5,1.25,.4);//double area
        StdDraw.setPenColor(TAN);
        StdDraw.filledRectangle(0.5, 2.75, .4, 2.5);//lower bin
        StdDraw.filledRectangle(0.5, 10.25, .4, 2.5);//upper bin
        Location destination1 = new Location(0, 0);//white bin
        drawBins(model.getPile(destination1), 12, source != null && model.isLegalMove(source, destination1));
        Location destination2 = new Location(12, 0);//black bin
        drawBins(model.getPile(destination2), 0, source != null && model.isLegalMove(source, destination2));
        //creates captured locations and draws them
        Location capturedB = new Location(6, 12);//white captured
        drawCaptured(model.getPile(capturedB), 7.5);//draw white pieces in captured location
        Location capturedW = new Location(7, 12);//black captured
        drawCaptured(model.getPile(capturedW), 5.5);//draw black pieces in captured location
        //creates dice locations and draws dice
        StdDraw.setPenColor(Color.BLACK);
        int n = model.getDie1Value();//get die value
        drawDie(.5, 6.0, n);//draw die with correct value
        n = model.getDie2Value();//get next die value
        drawDie(.5, 7.0125, n);//draw next die with correct value
        drawDoubleDie(11.75,6.5,model.Die3.getValue());
        drawDouble(10.56,6.5);
        StdDraw.setPenColor(WHITE);
        StdDraw.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 18));
        StdDraw.text(5.5, 6.5, instructions);//draw the instructions from the original method arguement
        StdDraw.show();
    }

    /**
     * Draws captured pips in correct areas
     */
    public void drawCaptured(Stack<Integer> pile, double y) {
        for (int c : pile) {//for every pip in the pile
            StdDraw.setPenColor(c == BackGammonModel.BLACK ? java.awt.Color.BLACK : java.awt.Color.WHITE);//draw outline
            StdDraw.filledCircle(12.5, y, .25);
            StdDraw.setPenColor(c == BackGammonModel.BLACK ? java.awt.Color.WHITE : java.awt.Color.BLACK);//draw outline
            StdDraw.circle(12.5, y, .25);
            if (y >= 13 / 2) {//if we are on the top half
                if (pile.size() > 2) {//if the size of the pile is greater than two, start overlapping the pieces
                    y -= (1.3 / (pile.size() + (pile.size() / 2)));
                } else {
                    y -= 0.5;
                }
            } else {
                if (pile.size() > 2) {//if the pile size is greater than two, start overlapping the pieces
                    y += (1.3 / (pile.size() + (pile.size() / 2)));
                } else {
                    y += 0.5;
                }
            }
        }
    }

    /*Draws double die*/
    public void drawDoubleDie(double x, double y, int n) {
        StdDraw.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 14));//sets die font
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(x, y, 0.25);//draws background of die
        StdDraw.setPenColor(StdDraw.BLACK);//draws the correct number on the die
        if (n == 2) {
            StdDraw.text(x, y, "2");
        } else if (n == 4) {
            StdDraw.text(x, y, "4");
        } else if (n == 8) {
            StdDraw.text(x, y, "8");
        } else if (n == 16) {
            StdDraw.text(x, y, "16");
        } else if (n == 32) {
            StdDraw.text(x, y, "32");
        } else if (n == 64) {
            StdDraw.text(x, y, "64");
        } else {
            StdDraw.text(x, y, "1");
        }
    }

    /**draws double click location*/
    public void drawDouble(double x, double y) {
        StdDraw.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 12));//sets die font
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(x+.275,y+1.25,"White Games/Score");
        StdDraw.text(x+.275,y-.65,"Black Games/Score");
        StdDraw.filledRectangle(x, y, 0.75,0.25);//draws background of die
        StdDraw.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 18));//sets die font
        StdDraw.text(x-.2,y+.65,Integer.toString(model.getGames(1)));
        StdDraw.text(x+.3,y+0.65,"/");
        StdDraw.text(x+.8,y+.65,model.getScore(1));
        StdDraw.text(x-.2,y-1.25,Integer.toString(model.getGames(0)));
        StdDraw.text(x+.3,y-1.25,"/");
        StdDraw.text(x+.8,y-1.25,model.getScore(0));
        StdDraw.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 14));//sets die font
        StdDraw.setPenColor(StdDraw.BLACK);//draws the correct number on the button
        StdDraw.text(x, y, "Double");
    }

    /**
     * Draws a die at x, y, with face n showing.
     *
     * @param n 1-6, with 1 representing a 1 and 6 a 6, and a 0 with a 0.
     */
    public void drawDie(double x, double y, int n) {
        StdDraw.setFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 18));//sets die font
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(x, y, 0.3);//draws background of die
        StdDraw.setPenColor(StdDraw.BLACK);//draws the correct number on the die
        if (n == 1) {
            StdDraw.text(x, y, "1");
        } else if (n == 2) {
            StdDraw.text(x, y, "2");
        } else if (n == 3) {
            StdDraw.text(x, y, "3");
        } else if (n == 4) {
            StdDraw.text(x, y, "4");
        } else if (n == 5) {
            StdDraw.text(x, y, "5");
        } else if (n == 6) {
            StdDraw.text(x, y, "6");
        } else {
            StdDraw.text(x, y, "0");
        }
    }

    /**
     * Draws bins area
     */
    public void drawBins(Stack<Integer> pile, double y, boolean highlight) {
        if(highlight){
            StdDraw.setPenColor(PINK);
            if(model.getCurrentPlayer()==1){
                StdDraw.filledRectangle(0.5, 10.25, .4, 2.5);//upper bin
            }
            if(model.getCurrentPlayer()==0){
                StdDraw.filledRectangle(0.5, 2.75, .4, 2.5);//lower bin
            }
        }
        if (y > 13 / 2) {//if we are on the top, adjust to be in the tan area
            y += .45;//top
        } else {//if we are on the bot, adjust to be in the tan area
            y += .55;//bot
        }
        int n = 0;
        for (int c : pile) {//draw the pips
            StdDraw.setPenColor(c == BackGammonModel.BLACK ? java.awt.Color.BLACK : java.awt.Color.WHITE);//draw the correct color pip
            StdDraw.filledCircle(0.5, y, .25);
            StdDraw.setPenColor(c == BackGammonModel.BLACK ? java.awt.Color.WHITE : java.awt.Color.BLACK);//outline the pip
            StdDraw.circle(0.5, y, .25);
            if (y > 6.5) {
                if (pile.size() > 9) {//if we get too many, start overlapping so they fit
                    y -= (5.0 / (pile.size() + 1));
                } else {
                    y -= 0.5;
                }
            } else {
                if (pile.size() > 9) {
                    y += (5.0 / (pile.size() + 1));
                } else {
                    y += 0.5;
                }
            }
        }
    }

    /**
     * Draws one square (and any pieces piled there) at coordinates x, y.
     *
     * @param highlight If true, highlights this square as a legal move.
     */
    public void drawTriangle(Stack<Integer> pile, double x, double y, boolean highlight, Color color) {
        if (pile != null) {
            if (highlight) {
                StdDraw.setPenColor(PINK);
            } else {
                StdDraw.setPenColor(color);
            }
            //logic for creating triangles correctly
            double a = (1);
            double[] l = {x, x + (a / 2), x + a};
            double[] w = new double[3];
            if (y > 5) {
                w[0] = 13;
                w[1] = 8;
                w[2] = 13;
            } else {
                w[0] = 0;
                w[1] = 5;
                w[2] = 0;
            }
            StdDraw.filledPolygon(l, w);
            x += (a / 2);
            if (y > 13 / 2) {
                y -= 0.25;
            } else {
                y += 0.25;
                y -= 1;
            }
            for (int c : pile) {
                StdDraw.setPenColor(c == BackGammonModel.BLACK ? java.awt.Color.BLACK : java.awt.Color.WHITE);
                StdDraw.filledCircle(x, y, .25);
                StdDraw.setPenColor(c == BackGammonModel.BLACK ? java.awt.Color.WHITE : java.awt.Color.BLACK);
                StdDraw.circle(x, y, .25);
                if (y > 13 / 2) {//overlap the pips if they would go outside the height of a triangle
                    if (pile.size() > 10) {
                        y -= (5.0 / (pile.size() + 1));
                    } else {
                        y -= 0.5;
                    }
                } else {
                    if (pile.size() > 10) {
                        y += (5.0 / (pile.size() + 1));
                    } else {
                        y += 0.5;
                    }
                }
            }
        }
    }

    /**
     * Returns "Black" or "White" as appropriate.
     */
    public String getCurrentPlayerName() {
        return model.getCurrentPlayer() == BackGammonModel.BLACK ? "Black" : "White";
    }

    /**
     * Plays the game.
     */
    public void run() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 13);
        StdDraw.setYscale(0, 13);
        model.Die3.setValue(1);
        StdDraw.text(6.5, 9.5, "Back Gammon");
        StdDraw.text(6.5, 7.5, "Get your pieces off of the board.");
        StdDraw.text(6.5, 5.5, "The first player to do this wins.");
        StdDraw.text(6.5, 3.5, "Click to continue");
        StdDraw.show();
        waitForClick();
        StdDraw.clear();
        StdDraw.text(7, 9.5, "How Many Games would you like to play to?");
        StdDraw.text(7, 7.5, "Click on a "+"best of number"+".");
        StdDraw.text(3.5,5.5,"1");
        StdDraw.text(4.5,5.5,"3");
        StdDraw.text(5.5,5.5,"5");
        StdDraw.text(6.5,5.5,"7");
        StdDraw.text(7.5,5.5,"9");
        StdDraw.text(8.5,5.5,"11");
        StdDraw.text(9.5,5.5,"13");
        StdDraw.text(10.5,5.5,"15");
        StdDraw.show();
        waitForClick();
        setGames();
        while((model.getGames(0)<((model.getGames(2)+1)/2) &&( model.getGames(1)<(model.getGames(2)+1)/2))) {
            StdDraw.clear();
            //display before a game starts, what has happened previously
            StdDraw.setPenColor(BLACK);
            StdDraw.text(6.5, 8.5, "This is a best of "+model.getGames(2));
            StdDraw.text(6.5,6.5,"Black: "+model.getGames(0)+" White: "+model.getGames(1));
            StdDraw.text(6.5, 4.5, "Click to see who goes first.");
            StdDraw.show();
            waitForClick();
            StdDraw.clear();
            StdDraw.text(6.5, 10.5, "Back Gammon");
            model.roll();
            while (model.getDie1Value() == model.getDie2Value()) {//roll again if they rolled the same number
                model.roll();
            }
            //display the who goes first dice rolls
            StdDraw.text(6.5, 8.5, "Black Rolled: " + model.getDie1Value());
            StdDraw.text(6.5, 6.5, "White Rolled: " + model.getDie2Value());
            if (model.getDie1Value() > model.getDie2Value()) {
                model.setCurrentPlayer(0);//Black
            } else {
                model.setCurrentPlayer(1);//White
            }
            StdDraw.text(6.5, 4.5, getCurrentPlayerName() + " goes first with roll: " + model.getDie1Value() + "," + model.getDie2Value());
            StdDraw.text(6.5, 2.5, "Click to continue.");
            StdDraw.show();
            waitForClick();
            //initialize things
            int n = 0;
            int o = 0;
            int d1 = 0;
            int d2 = 0;
            model.Die3.setValue(1);
            boolean flag;
            boolean flag2=false;
            model.setBoard();
            model.countScore();
            while (!model.isGameOver()) {
                d1 = model.Die1.getValue();
                d2 = model.Die2.getValue();
                flag = false;
                Location m = new Location(10, 8);//location of writing instructions for player
                //if you have no moves, tell you that you have no moves, and make them
                //click to change players, reroll the dice between player changes
                if (model.isNoMoves()) {
                    draw(getCurrentPlayerName() + ", has no moves and forfeits turn", m);
                    StdDraw.text(6, 5.5, "Click to continue.");
                    StdDraw.show();
                    waitForClick();
                    model.toggleColorToPlay();
                    model.setMoveValue(0);
                    model.roll();
                    n = 0;
                    o = 0;
                    continue;
                }
                draw(getCurrentPlayerName() + ", click on one of your pips to move.", m);
                Location source;
                Location destination;
                do {
                    source = waitForClick();//get the stack they clicked on
                }
                while (source == null || !model.isLegalSource(source));//only let them click there if they can move from that source
                if(source==model.DOUBLE_LOCATION){
                    model.toggleColorToPlay();
                    model.setDoub(true);
                    draw(getCurrentPlayerName() + ", do you accept?", source);
                    StdDraw.text(4.5,5.5,"Accept?");
                    StdDraw.text(6.5,5.5,"Reject?");
                }else{
                    draw(getCurrentPlayerName() + ", click on destination or,", source);
                    StdDraw.text(5.5, 5.75, "press 'space' to undo source click");
                }
                StdDraw.show();
                if (secondAction()) {//see if they click first or hit the space bar first
                    do {//get the click if they clicked on a legal place
                        destination = waitForClick();//disregard the undo if they clicked first
                    } while (destination == null || !model.isLegalMove(source, destination));
                } else {
                    continue;//return to the top of the entire while statement if they undid, (doest change your roll)
                }
                if(destination==model.ACCEPT_LOCATION){//if they accept
                    model.doubleDie();
                    model.toggleColorToPlay();
                    n=0;
                    o=0;
                    continue;
                }else if(destination==model.REJECT_LOCATION) {//if they reject
                    flag2=true;
                    model.toggleColorToPlay();
                    break;
                }else{
                    model.move(source, destination);//move the move
                    model.countScore();
                }
                n++;
                if (n % 2 != 0) {//if they rolled doubles, reset the value of the opposite dice roll through saved values
                    if (model.getMoveValue() == model.getDie1Value()) {//the flag is so the other tests dont happen if an earlier one succeeds
                        model.Die1.setValue(0);
                        flag = true;
                    }
                    if (model.getMoveValue() == model.getDie2Value() && flag != true) {
                        model.Die2.setValue(0);
                        flag = true;
                    }
                    if (model.getDie1Value() > model.getDie2Value() && flag != true) {
                        if (model.getDie2Value() < model.getDie1Value()) {
                            model.Die1.setValue(0);
                            flag = true;
                        }
                    }
                    if (model.getDie2Value() > model.getDie1Value() && flag != true) {
                        if (model.getMoveValue() < model.getDie2Value()) {
                            model.Die2.setValue(0);
                        }
                    }
                }
                //for doubles
                if ((model.Die2.getValue() == d1) || model.Die1.getValue() == d2) {
                    model.Die1.setValue(d2);
                    model.Die2.setValue(d1);
                    o++;//increase the doubles tracker
                }
                if ((n % 2 == 0) && (o % 4 == 0)) {//if you didnt roll doubles, and you used both dice rolls
                    model.setMoveValue(0);//reset move in the model
                    model.toggleColorToPlay();//change who is playing
                }
                if ((n % 2 == 0) && (o % 4 == 0)) {//roll and reset if you did all of your moves
                    model.roll();
                    n = 0;
                    o = 0;
                }
            }
            model.setWins(model.getCurrentPlayer());
            if(flag2){//if there was a double that they rejected, the other player wins
                model.toggleColorToPlay();
            }
            draw(getCurrentPlayerName() + " Wins.", null);
            StdDraw.text(5.5,6,"Cick to continue.");
            StdDraw.show();
            waitForClick();
        }
        StdDraw.clear();
        //display before a game starts, what has happened previously
        StdDraw.setPenColor(BLACK);
        StdDraw.text(6.5, 8.5, "This was a best of "+model.getGames(2));
        StdDraw.text(6.5,6.5,"Black: "+model.getGames(0)+" White: "+model.getGames(1));
        if(model.getCurrentPlayer()==0){
            StdDraw.text(6.5,4.5,"Black won with "+model.getGames(model.getCurrentPlayer())+" games!");
        }else{
            StdDraw.text(6.5,4.5,"White won with "+model.getGames(model.getCurrentPlayer())+" games!");
        }
        StdDraw.show();
    }

    /**
     * Waits for the user to click and returns the location where they clicked
     * (which might be one of Game.CAPTURED_LOCATIONS). For invalid locations,
     * may return null.
     */
    public Location waitForClick() {
        while (!StdDraw.mousePressed()) {
            // Wait for mouse press
        }
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        while (StdDraw.mousePressed()) {
            // Wait for mouse release
        }
        // This catches  clicks on the captured spaces
        if ((y <= 8) && (y >= 6.5) && (x >= 12)) {
            return BackGammonModel.CAPTURED_LOCATION[0];
        } else {
            if ((y <= 6.5) && (y >= 5) && (x >= 12)) {
                return BackGammonModel.CAPTURED_LOCATION[1];
            }
        }
        if(model.getDoub()) {
            //accept location
            if ((x >= 5.5) && (x <= 7.25) && (y >= 5.2) && (y <= 5.7)) {
                model.setDoub(false);
                return model.REJECT_LOCATION;
            }
            //reject location
            if ((x >= 3.7) && (x <= 5.35) && (y >= 5.2) && (y <= 5.7)) {
                model.setDoub(false);
                return model.ACCEPT_LOCATION;
            }
        }
        //if they clicked on the double button
        if((y <= 6.8) && (y >= 6) && (x>=9.75) && (x<11.3)){
            model.setClick(model.DOUBLE_LOCATION);
            return model.DOUBLE_LOCATION;
        }
        //adjusts for location to get correct locations of game pieces on triangles
        if (y > 6.5) {
            y = 0;
        } else {
            y = 12;
        }
        //change the values to ints for returning columns and such
        Location result = new Location((int) (y), (int) (x));
        model.setClick(result);
        if (model.isOnBoard(result)) {//if they clicked on the board, return the click, else return null
            return result;
        }
        return null;
    }

    /**
     looks for a click, returns true as soon as you cick
     */
    public boolean isClick(){
        if(StdDraw.isMousePressed()){
            return true;
        }else{
            return false;
        }
    }

    /**
     looks for a key press of 'space' if you hit space, return true immediately
     */
    public boolean isKey(){
        if(StdDraw.isKeyPressed(32)){
            return true;
        }else{
            return false;
        }
    }


    /**
     determines if you click or press a key first
     */
    public boolean secondAction() {
        while(!isKey()){
            if(isClick()){
                return true;
            }
        }
        return false;
    }

    /**sets the number of games*/
    public void setGames(){
        if(model.getClickX()<=3){
            model.setTotalGames(1);
        }
        if(model.getClickX()==4){
            model.setTotalGames(3);
        }
        if(model.getClickX()==5){
            model.setTotalGames(5);
        }
        if(model.getClickX()==6){
            model.setTotalGames(7);
        }
        if(model.getClickX()==7){
            model.setTotalGames(9);
        }
        if(model.getClickX()==8){
            model.setTotalGames(11);
        }
        if(model.getClickX()==9){
            model.setTotalGames(13);
        }
        if(model.getClickX()>=10){
            model.setTotalGames(15);
        }
    }
}
