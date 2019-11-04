import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void getColumn() {
        Location A = new Location (12, 1);
        assertEquals(A.getColumn(), 1);
        Location B = new Location (12, 5);
        assertEquals(A.getColumn(), 5);
    }


    @Test
    void getsDistanceTo() {
        Location A = new Location (12,1);
        Location B = new Location (0, 1);
        assertEquals(A.getDistanceTo(B), 23);
        Location C = new Location (12,1);
        Location D = new Location (12, 1);
        assertEquals(C.getDistanceTo(D), 0);
        Location E = new Location (12,1);
        Location F = new Location (12, 12);
        assertEquals(E.getDistanceTo(F), 11);
        Location G = new Location (12,1);
        Location H = new Location (0, 5);
        assertEquals(G.getDistanceTo(H), 19);
        Location X = new Location(12, 0);
        Location Z = new Location (12,5);
        assertEquals(X.getDistanceTo(Z), 5);

    }

    @Test
    void getRow() {
        Location A = new Location (12, 1);
        assertEquals(A.getRow(), 12);
        Location B = new Location (0, 5);
        assertEquals(A.getRow(), 0);
    }
}
