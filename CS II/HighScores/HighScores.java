import java.util.Arrays;

/** Sorts a list of high scores. */
public class HighScores {

	/** Sorts scores in increasing order. */
	public static void insertionSort(int[] scores) {
		for (int i = 1; i < scores.length; i++) {
			for (int j = i; j > 0; j--) {
				if (scores[j - 1] > scores[j]) {
					int temp = scores[j];
					scores[j] = scores[j - 1];
					scores[j - 1] = temp;
				} else {
					break;
				}
			}
		}
	}

	/**
	 * Reads the scores from a file and sorts them using two different
	 * algorithms, printing the score lists before and after.
	 */
	public static void main(String[] args) {
		int[] scores = new In("scores.txt").readAllInts();
		StdOut.println("Before insertion sorting: " + Arrays.toString(scores));
		insertionSort(scores);
		StdOut.println("After insertion sorting: " + Arrays.toString(scores));
		scores = new In("scores.txt").readAllInts();
		StdOut.println("Before mergesorting: " + Arrays.toString(scores));
		mergeSort(scores);
		StdOut.println("After mergesorting: " + Arrays.toString(scores));
//        int[] arr = new int[]{ 2,5,1,4,3,6 };
//        StdOut.println("Before mergesorting arr: " + Arrays.toString(arr));
//        mergeSort(arr);
//        StdOut.println("After mergesorting arr: " + Arrays.toString(arr));
	}

	/**
	 * Merges the regions of scores from lo (inclusive) to hi (exclusive).
	 * Assumes that each of the regions lo through mid and mid through hi (with
	 * the second index exclusive in each case) is already sorted.
	 * 
	 * @return A new array containing the elements from the merged regions, now
	 *         in increasing order.
	 */
	public static int[] merge(int[] scores, int lo, int hi, int mid) {
		int[] merged = new int[hi - lo];
		int a = lo; // Index into sorted left side
		int b = mid; // Index into sorted right side
		for (int i = 0; i < merged.length; i++) {
			if (b >= hi) {
				merged[i] = scores[a];
				a++;
			} else if (a >= mid || scores[b] < scores[a]) {
				merged[i] = scores[b];
				b++;
			} else {
				merged[i] = scores[a];
				a++;
			}
		}
		return merged;
	}

	/** Sorts scores in increasing order. */
	public static void mergeSort(int[] scores) {
		mergeSort(scores, 0, scores.length);
	}

	/**
	 * Sorts the region of scores from lo (inclusive) to hi (exclusive) in
	 * increasing order.
	 */
	public static void mergeSort(int[] scores, int lo, int hi) {
		if (hi <= lo + 1) { // No elements to sort
			return;
		}
		int mid = lo + (hi - lo) / 2;
		// Sort both halves
		mergeSort(scores, lo, mid);
		//StdOut.println("1: "+scores[0]+","+scores[1]+","+scores[2]+","+scores[3]+","+scores[4]+","+scores[5]);
		mergeSort(scores, mid, hi);
        //StdOut.println("2: "+scores[0]+","+scores[1]+","+scores[2]+","+scores[3]+","+scores[4]+","+scores[5]);
		// Merge the two sorted halves
		int[] merged = merge(scores, lo, hi, mid);
		// Copy elements from merged back into scores
		for (int i = lo; i < hi; i++) {
			scores[i] = merged[i - lo];
		}
	}

}
