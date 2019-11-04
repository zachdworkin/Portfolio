//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import static org.junit.jupiter.api.Assertions.*;
//
//class BackGammonModelTest {
//
//    /** The black player. Equal to 0 for array indexing. */
//    public static final int BLACK = 0;
//    /** The white player. Equal to 1 for array indexing. */
//    public static final int WHITE = 1;
//    private Stack<Integer>[][] board;
//    /**
//     * The board width. Always 13, but defined as a constant to avoid magic
//     * numbers in code.
//     */
//    public static final int BOARD_WIDTH = 13;
//
//    @BeforeEach
//    BackGammonModel model = new BackGammonModel();
//
//
//
//    @Test
//    void getCurrentPlayer() {
//        model.setCurrentPlayer(1);
//        assertEquals(model.getCurrentPlayer(), 1);
//    }
//
////    @Test
////    void getPile() {
////        Location location = new Location(12,1);
////        assertTrue(model.getPile(location), []);
////    }
//
//
//    @Test
//    void isGameOver() {
//        assertEquals(model.isGameOver(), false);
//
//    }
//
//    @Test
//    void isLegalMove() {
//        Location A = new Location(0,5);
//        Location B = new Location(0,0);
//        Location C = new Location (0, 2);
//        model.Die1.setValue(5);
//        assertEquals(model.isLegalMove(A, B), true);
//        assertEquals(model.isLegalMove(C, B), false);
//    }
//
//    @Test
//    void isLonePiece() {
//        BackGammonModel model = new BackGammonModel();
//        Location a = new Location(0,1) ;
//        assertEquals(model.isLonePiece(a), false);
//    }
//
//    @Test
//    void isLegalSource() {
//        Location A = new Location(0,5);
//        Location B = new Location(0,0);
//        Location C = new Location (0, 2);
//        model.Die1.setValue(5);
//        assertEquals(model.isLegalSource(A), true);
//        assertEquals(model.isLegalSource(B), false);
//    }
//
//    @Test
//    void isOnBoard() {
//        Location A = new Location(0,5);
//        Location B = new Location(0,25);
//        assertEquals(model.isOnBoard(A), true);
//        assertEquals(model.isOnBoard(B), false);
//    }
//
//    @Test
//    void move() {
//        Location A = new Location(0,5);
//        Location B = new Location(0,0);
//        model.move();
//        assertEquals(model.getPile(A).size(), 0);
//
//    }
//
//
//    @Test
//    void getDie1Value() {
//        model.Die1.setValue(3);
//        assertEquals(model.getDie1Value(), 3);
//    }
//
//    @Test
//    void checksiflegalbinmove(){
//        BackGammonModel model = new BackGammonModel();
//        assertEquals(model.isLegalBinMove(), true);
//    }
//
//    @Test
//    void checksisnomove() {
//        BackGammonModel model = new BackGammonModel();
//        assertEquals(model.isNoMoves(), true);
//    }
//
//    @Test
//    void checksIsLegalMoveToBin(){
//        BackGammonModel model = new BackGammonModel();
//        Location A = new Location(12, 1);
//        Location B = new Location (12,5);
//        Location X = new Location(12,0);
//        model.Die1.setValue(6);
//        model.Die2.setValue(5);
//        assertEquals(model.isLegalMoveToBin(A,X), false);
//        assertEquals(model.isLegalMoveToBin(B,X), true);
//    }
//}
