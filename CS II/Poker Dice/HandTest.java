import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HandTest {

	private Hand hand;

	@BeforeEach
	public void setUp() throws Exception {
		hand = new Hand();
	}

	@Test
	public void allRollsAreEquallyLikely() {
		// counts[i][j] is the number of times die i has come up with value j
		int[][] counts = new int[5][6];
		// Roll the whole hand many times
		for (int i = 0; i < 10000; i++) {
			hand.roll();
			for (int j = 0; j < 5; j++) {
				counts[j][hand.get(j)]++;
			}
		}
		// Each number should be about equally likely for each die
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 6; j++) {
				assertTrue(counts[i][j] > 0.75 * (10000 / 6));
				assertTrue(counts[i][j] < 1.25 * (10000 / 6));
			}
		}
	}
	
	@Test
	public void storesKeptFlags() {
		assertFalse(hand.isKept(1));
		hand.setKept(1, true);
		assertTrue(hand.isKept(1));
		assertFalse(hand.isKept(0));
		hand.setKept(1, false);
		assertFalse(hand.isKept(1));
	}
	
	@Test
	public void detectsAllKept() {
		for (int i = 1; i < 5; i++) {
			hand.setKept(i, true);
		}
		assertFalse(hand.allKept());
		hand.setKept(0, true);
		assertTrue(hand.allKept());
	}

	@Test
	public void unkeptDiceAreRerolled() {
		// Mark dice 0 and 3 for keeping
		hand.setKept(0, true);
		hand.setKept(3, true);
		// counts[i][j] is the number of times die i has come up with value j
		int[][] counts = new int[5][6];
		// Roll the hand many times
		for (int i = 0; i < 10000; i++) {
			hand.roll();
			for (int j = 0; j < 5; j++) {
				counts[j][hand.get(j)]++;
			}
		}
		// The kept dice should stay at 0.
		for (int i : new int[] {0, 3}) {
			assertEquals(10000, counts[i][0]);
		}		
		// Each number should be about equally likely for the rerolled dice
		for (int i : new int[] {1, 2, 4}) {
			for (int j = 0; j < 6; j++) {
				assertTrue(counts[i][j] > 0.75 * (10000 / 6));
				assertTrue(counts[i][j] < 1.25 * (10000 / 6));
			}
		}
	}

	@Test
	public void scoresFiveOfAKind() {
		hand.set(2, 3, 2, 2, 2);
		assertEquals(-1, hand.fiveOfAKindScore());
		hand.set(4, 4, 4, 4, 4);
		assertEquals(740000, hand.fiveOfAKindScore());
	}

	@Test
	public void scoresFourOfAKind() {
		hand.set(2, 3, 2, 2, 2);
		assertEquals(623000, hand.fourOfAKindScore());
		hand.set(2, 3, 3, 2, 2);
		assertEquals(-1, hand.fourOfAKindScore());
	}

	@Test
	public void scoresFullHouse() {
		hand.set(4, 0, 0, 0, 4);
		assertEquals(504000, hand.fullHouseScore());
		hand.set(3,2,2,3,2);
		assertEquals(523000, hand.fullHouseScore());
		hand.set(1,2,3,3,3);
		assertEquals(-1, hand.fullHouseScore());
		hand.set(2, 3, 2, 2, 2);
		assertEquals(-1, hand.fullHouseScore());
	}

	@Test
	public void scoresStraight() {
		hand.set(2, 1, 4, 5, 0);
		assertEquals(-1, hand.straightScore());
		hand.set(5,4,3,2,1);
		assertEquals(450000, hand.straightScore());
		hand.set(4,3,2,1,0);
		assertEquals(440000, hand.straightScore());
		hand.set(0,1,2,3,4);
		assertEquals(440000, hand.straightScore());
		hand.set(1,2,3,4,5);
		assertEquals(450000, hand.straightScore());
		hand.set(4,3,2,1,1);
		assertEquals(-1, hand.straightScore());
	}

	@Test
	public void scoresThreeOfAKind() {
		hand.set(4,1,4,1,3);
		assertEquals(-1, hand.threeOfAKindScore());
		hand.set(4,4,4,2,3);
		assertEquals(343200, hand.threeOfAKindScore());
	}

	@Test
	public void scoresTwoPair() {
		hand.set(4,1,4,1,3);
		assertEquals(241300, hand.twoPairScore());
		hand.set(4,4,4,2,3);
		assertEquals(-1, hand.twoPairScore());
	}

	@Test
	public void scoresOnePair() {
		hand.set(4,1,4,2,3);
		assertEquals(243210, hand.onePairScore());
		hand.set(3,1,3,2,2);
		assertEquals(332100, hand.twoPairScore());
		hand.set(4,1,4,2,3);
		assertEquals(243210, hand.onePairScore());
		hand.set(4,4,4,2,3);
		assertEquals(-1, hand.onePairScore());
	}

	@Test
	public void scoresHighCard() {
		hand.set(0,1,2,3,4);
		assertEquals(43210, hand.highCardScore());
		hand.set(5,5,4,3,2);
		assertEquals(432, hand.highCardScore());
		hand.set(2, 1, 4, 5, 0);
		assertEquals(54210, hand.highCardScore());
		hand.set(5,5,4,4,4);
		assertEquals(-1, hand.highCardScore());




	}

	@Test
	public void scoresGenerally() {
		hand.set(2, 1, 4, 5, 0);
		assertEquals(54210, hand.getScore());
		hand.set(2, 2, 2, 2, 2);
		assertEquals(720000, hand.getScore());
		hand.set(4, 0, 0, 0, 4);
		assertEquals(504000, hand.getScore());
		hand.set(3, 2, 3, 3, 5);
		assertEquals(335200, hand.getScore());

	}

}
