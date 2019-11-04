/** Generates a bunch of random scores and writes them to a file. */
public class RandomScoreGenerator {

	public static void main(String[] args) {
		StdOut.print("Enter filename: ");
		Out out = new Out(StdIn.readLine());
		StdOut.print("Enter number of scores to generate: ");
		int n = StdIn.readInt();
		for (int i = 0; i < n; i++) {
			out.println(StdRandom.uniform(10 * n));
		}
	}

}
