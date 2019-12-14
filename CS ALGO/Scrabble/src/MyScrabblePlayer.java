import java.util.ArrayList;

/**
 * Scrabble AI player that plays up to 4 tiles per turn other than the first
 * Code by Zach Dworkin, Ben Schulman, and Alex Lotero
 */

public class MyScrabblePlayer implements ScrabbleAI {

    private static final boolean[] ALL_TILES = {true, true, true, true, true, true, true};

    private GateKeeper gate;

    /**
     * considers every permutation of letters in the current hand and plays the best word on the best spot on the board that also hits the center tile
     * this could be improved by only considering the center cross on the board as possible plays rather than every location on the board
     * @return best possible move
     */
    private ScrabbleMove findFistMove(){
        ArrayList<Character> hand = gate.getHand();
        PlayWord[] bestMove = new PlayWord[7];
        int[] bestScore = new int[7] ;
        for (int i=0;i<7;i++){//initialize the arays
            bestScore[i]=-1;
            bestMove[i]=null;
        }
        //needs a 7 nested for loop to consider every possible permutation of letters
        for (int p = 0; p < hand.size(); p++) {
            for(int q = 0; q < hand.size(); q++) {
                for (int m = 0; m < hand.size(); m++) {
                    for (int n = 0; n < hand.size(); n++) {
                        for (int i = 0; i < hand.size(); i++) {
                            for (int j = 0; j < hand.size(); j++) {
                                for (int k = 0; k < hand.size(); k++) {
                                    //checks to make sure we don't try and use the same tiles from our hand at the same time
                                    if (p != q && p != m && p != n && p != i && p != j && p != k &&
                                                q != m && q != n && q != i && q != j && q != k &&
                                                m != n && m != i && m != j && m != k &&
                                                n != i && n != j && n != k &&
                                                i != j && i != k &&
                                                j != k) {
                                        //the following considers a blank to always be the letter e
                                        char a = hand.get(p);
                                        if (a == '_') {
                                            a = 'E'; // This could be improved slightly by trying all possibilities for the blank
                                        }
                                        char b = hand.get(q);
                                        if (b == '_') {
                                            b = 'E'; // This could be improved slightly by trying all possibilities for the blank
                                        }
                                        char c = hand.get(m);
                                        if (c == '_') {
                                            c = 'E'; // This could be improved slightly by trying all possibilities for the blank
                                        }
                                        char d = hand.get(n);
                                        if (d == '_') {
                                            d = 'E';
                                        }
                                        char f = hand.get(i);
                                        if (f == '_') {
                                            f = 'E';
                                        }
                                        char g = hand.get(j);
                                        if (g == '_') {
                                            g = 'E';
                                        }
                                        char h = hand.get(k);
                                        if (h == '_') {
                                            h = 'E';
                                        }
                                        //creates possible word strings
                                        String[] words = new String[6];
                                        words[0] = ("" + a + b);
                                        words[1] = ("" + a + b + c);
                                        words[2] = ("" + a + b + c + d);
                                        words[3] = ("" + a + b + c + d + f);
                                        words[4] = ("" + a + b + c + d + f + g);
                                        words[5] = ("" + a + b + c + d + f + g + h);
                                        for (int kk = 0; kk < 6; kk++) {
                                            String word = words[kk];
                                            for (int row = 0; row < Board.WIDTH; row++) {
                                                for (int col = 0; col < Board.WIDTH; col++) {
                                                    Location location = new Location(row, col);
                                                    for (Location direction : new Location[]{Location.HORIZONTAL, Location.VERTICAL}) {
                                                        try {//trys every possible word string
                                                            gate.verifyLegality(word, location, direction);
                                                            int score = gate.score(word, location, direction);
                                                            if (score > bestScore[kk]) {
                                                                bestScore[kk] = score;
                                                                bestMove[kk] = new PlayWord(word, location, direction);
                                                            }
                                                        } catch (IllegalMoveException e) {
                                                            // It wasn't legal; go on to the next one
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int best=0;
        int index=0;
        for(int i=0;i<7;i++){//finds best playable word using the scores
            if(bestScore[i]>best){
                best=bestScore[i];
                index=i;
            }
        }
        if (bestMove[index] != null) {
            return bestMove[index];
        }
        return new ExchangeTiles(ALL_TILES);//in the case that we cannot make a word, which should never be the case

    }

    /**
     * finds the best possible move through playing up to 4 tiles on the board
     * cannot play between two columns or rows that have a gap between them that plays across both rows/columns
     * @return best move
     */
    private ScrabbleMove findAllMoves(){
        boolean found = false;//check to see if we need to exchange tiles a.k.a. pass
        ArrayList<Character> hand = gate.getHand();
        PlayWord[] bestMove = new PlayWord[5];
        int[] bestScore = new int[5] ;
        for (int i=0;i<5;i++){//initialize the arrays
            bestScore[i]=-1;
            bestMove[i]=null;
        }

        //creates 4 nested for loops to consider different permutations of playing up to 4 tiles
        for (int p = 0; p < hand.size(); p++) {
            for (int i = 0; i < hand.size(); i++) {
                for (int j = 0; j < hand.size(); j++) {
                    for (int k = 0; k < hand.size(); k++) {
                            if (p!=i && p!=j && p!=k
                                     && i!=j && i!=k
                                             && j!=k) {
                                //treats every blank tile as the letter e
                                char a = hand.get(p);
                                if (a == '_') {
                                    a = 'E'; // This could be improved slightly by trying all possibilities for the blank
                                }
                                char b = hand.get(i);
                                if (b == '_') {
                                    b = 'E'; // This could be improved slightly by trying all possibilities for the blank
                                }
                                char c = hand.get(j);
                                if (c == '_') {
                                    c = 'E'; // This could be improved slightly by trying all possibilities for the blank
                                }
                                char d = hand.get(k);
                                if (d == '_') {
                                    d = 'E';
                                }
                                //creates word strings
                                String[][] words = new String[4][];
                                words[0]=new String[]{" "+ a , a + " ","  "+a,"   "+a, a+"  ",a+"   "};
                                words[1]=new String[]{"" + a + b, a + b + " ", a + " " + b, " " + a + b,"  "+a+b,"   "+a+b,a+b+"  ", a+b+"   ",a+"  "+b,a+"   "+b};
                                words[2]=new String[]{"" + a + b + c, a + b + c + " ", a + b + " " + c, a + " " + b + c, " " + a + b + c,a+b+c+"  ",a+b+c+"   ",a+b+c+"    ",a+b+c+"     ","  "+a+b+c,"   "+a+b+c,"    "+a+b+c,"     "+a+b+c};
                                words[3]=new String[]{"" + a + b + c + d, a + b + c + d + " ", a + b + c + " " + d, a + b + " " + c + d, a + " " + b + c + d, " " + a + b + c + d,"  "+a+b+c+d,"   "+a+b+c+d,"     "+a+b+c+d};
                                int count=hand.size();
                                if(count>4){ count=4; }
                                //tries every possible word string on every location
                                for (int kk = 0; kk < count; kk++) {
                                    for (String word : words[kk]) {
                                        for (int row = 0; row < Board.WIDTH; row++) {
                                            for (int col = 0; col < Board.WIDTH; col++) {
                                                Location location = new Location(row, col);
                                                for (Location direction : new Location[]{Location.HORIZONTAL, Location.VERTICAL}) {
                                                    try {
                                                        gate.verifyLegality(word, location, direction);
                                                        found=true;
                                                        int score = gate.score(word, location, direction);
                                                        if (score > bestScore[kk]) {
                                                            bestScore[kk] = score;
                                                            bestMove[kk] = new PlayWord(word, location, direction);
                                                        }
                                                    } catch (IllegalMoveException e) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
//            }
        }
        int best=0;
        int index=0;
        for(int i=0;i<4;i++){//finds best playable word using the scores
            if(bestScore[i]>best){
                best=bestScore[i];
                index=i;
            }
        }
        //checks if we have found a word and that our word choice is not null
        if (bestMove[index] != null && found) {
            return bestMove[index];
        }
        return new ExchangeTiles(ALL_TILES);
    }

    /**
     *
     * @return best first move or best move in general depending on if the center tile is open
     */
    private ScrabbleMove findBestMove() {
        if (gate.getSquare(Location.CENTER) == Board.DOUBLE_WORD_SCORE){ return findFistMove(); }
        return findAllMoves();
    }

    @Override
    public void setGateKeeper(GateKeeper gateKeeper) { gate = gateKeeper; }

    @Override
    public ScrabbleMove chooseMove() { return findBestMove(); }

}
