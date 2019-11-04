import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GoModelTest {

    private GoModel model;

    @BeforeEach
    public void setUp() throws Exception {
        model = new GoModel(5);
    }

    //tests getBoardWidth()
    /** checks that getBoardWidth() returns the proper board width set when initializing GoModel(boardWidth)*/
    @Test
    public void storesWidth() {
        assertEquals(5, model.getBoardWidth());
        model = new GoModel(9);
        assertEquals(9, model.getBoardWidth());
    }

    //tests getCurrentPlayer() and toggleCurrentPlayer()
    /** tests whether toggleCurrentPlayer() switches from black to white*/
    @Test
    public void togglesCurrentPlayer() {
        assertEquals(StdDraw.BLACK, model.getCurrentPlayer());
        model.toggleCurrentPlayer();
        assertEquals(StdDraw.WHITE, model.getCurrentPlayer());
        model.toggleCurrentPlayer();
        assertEquals(StdDraw.BLACK, model.getCurrentPlayer());
    }

    //tests isValidCoordinate
    /** returns true if valid coordinate */
    @Test
    public void trueIfValidCoordinate() {
        for (int i = 0; i < 5; i++) {
            assertTrue(model.isValidCoordinate(i));
        }
    }
    /** returns false if not valid coordinate */
    @Test
    public void falseIfNotValidCoordinate() {
        assertFalse(model.isValidCoordinate(-1));
        assertFalse(model.isValidCoordinate(5));
        assertFalse(model.isValidCoordinate(100));
    }

    //tests isOnBoard(point)
    /** if point is off of the board, returns false */
    @Test
    public void falseIfOffBoard() {
        Location point = new Location(7,7);
        assertFalse(model.isOnBoard(point));
    }
    /** if point is on the board, returns true */
    @Test
    public void trueIfOnBoard() {
        Location point = new Location(0,0);
        assertTrue(model.isOnBoard(point));
    }

    //tests gameOver() and pass()
    /** if passes < 2 returns false	 */
    @Test
    public void falseIfGameNotOver() {
        assertFalse(model.gameOver());
        model.pass();
        assertFalse(model.gameOver());
    }
    /** if passes = 2 returns false	 */
    @Test
    public void trueIfGameOver() {
        model.pass();
        model.pass();
        assertTrue(model.gameOver());
    }

    //tests play
    /** if try to play a point off of the board, returns false */
    @Test
    public void falseIfPlayOffBoard() {
        assertFalse(model.play(6, 6));
    }
    /** returns true if you try and play an unoccupied point on the board */
    @Test
    public void trueIfUnoccupied() {
        assertTrue(model.play(1, 1));
        assertTrue(model.play(0, 4));
    }
    /** returns false if point is occupied */
    @Test
    public void falseIfPointIsOccupied() {
        model.play(1, 2);
        assertFalse(model.play(1,2));
        model.play(3, 0);
        assertFalse(model.play(3, 0));
    }

    //tests the two "gets"
    //tests get(int,int)
    /** tests get color from column and row by setting color with play */
    @Test
    public void getsColorFromCoordinatesWithPlay() {
        model.play(1,2);
        assertEquals(StdDraw.BLACK, model.get(1,2));
        model.play(1,3);
        assertEquals(StdDraw.WHITE, model.get(1,3));
    }

    //tests get(point)
    /** tests get color from a point */
    @Test
    public void getsColorFromPoint() {
        Location point = new Location(1,2);
        model.play(1, 2);
        assertEquals(StdDraw.BLACK, model.get(point));
        Location pointB = new Location(1,3);
        model.play(1, 3);
        assertEquals(StdDraw.WHITE, model.get(pointB));
    }

    //tests owner
    /** if the space is blank, returns null*/
    @Test
    public void nullIfBlankBoard() {
        assertEquals(null, model.owner(1, 2));
    }
    /** if the space is black, returns black*/
    @Test
    public void blackIfBlackPoint() {
        model.play(1,2);
        assertEquals(StdDraw.BLACK, model.owner(1, 2));
    }
    /** if the point is surrounded by black, returns black*/
    @Test
    public void blackIfSurroundedByBlack() {
        Location point = new Location (2,3);
        model.play(1,2);
        model.pass();
        model.play(1,3);
        model.pass();
        model.play(1,4);
        model.pass();
        model.play(2,1);
        model.pass();
        model.play(2,4);
        model.pass();
        model.play(3,2);
        model.pass();
        model.play(3,3);
        model.pass();
        model.play(3,4);
        model.capture(point);
        assertEquals(StdDraw.BLACK, model.owner(2, 3));
    }
    /** if the point is white, returns white*/
    @Test
    public void whiteIfWhitePoint() {
        model.toggleCurrentPlayer();
        model.play(1,3);
        assertEquals(StdDraw.WHITE, model.owner(1, 3));
    }
    /** if the point is surrounded by black, returns black*/
    @Test
    public void whiteIfSurroundedByWhite() {
        model.toggleCurrentPlayer();
        model.play(1,2);
        model.pass();
        model.play(1,3);
        model.pass();
        model.play(1,4);
        model.pass();
        model.play(2,1);
        model.pass();
        model.play(2,4);
        model.pass();
        model.play(3,2);
        model.pass();
        model.play(3,3);
        model.pass();
        model.play(3,4);
        assertEquals(StdDraw.WHITE, model.owner(1, 2));
    }

    //tests canReach
    @Test
    public void firstIfStatementFromOwnerIfNull() {
        Location point = new Location(1,2);
        assertFalse(model.canReach(point, null, StdDraw.BLACK, new HashSet<Location>()));
    }
    @Test
    public void secondIfStatementFromOwnerIfNull() {
        Location point = new Location(1,2);
        assertFalse(model.canReach(point, null, StdDraw.WHITE, new HashSet<Location>()));
    }
    @Test
    public void trueIfPointIsTargetColor() {
        //from owner
        model.play(1,2);
        Location point = new Location(1,2);
        assertTrue(model.canReach(point, null, StdDraw.BLACK, new HashSet<Location>()));
        model.play(0, 4);
        Location pointB = new Location(0,4);
        assertTrue(model.canReach(pointB, null, StdDraw.WHITE, new HashSet<Location>()));
        //from capture
        Location pointC = new Location(3,1);
        Set<Location> visited = new HashSet<>();
        assertTrue(model.canReach(pointC, model.get(pointC), null, visited));
    }

    //tests capture
    @Test
    public void trueIfNotSurrounded() {
        model.play(1,2);
        Location point = new Location(1,2);
        model.capture(point);
        assertEquals(StdDraw.BLACK, model.get(point));
    }
    //tests toString
    @Test
    public void givesProperString() {
        assertEquals(".....\n.....\n.....\n.....\n.....\n", model.toString());
    }
    @Test
    public void givesProperStringAfterPlaying() {
        model.play(0,0);
        model.play(1,2);
        assertEquals("#....\n"+"..O..\n"+".....\n"+".....\n"+".....\n", model.toString());
    }


}
