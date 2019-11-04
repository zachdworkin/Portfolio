/** A word game. */
public class Snowman {

	/**
	 * Returns true if word contains letter.
	 */
	public static boolean contains(String word, char letter) {
		// TODO You have to write this
		for (int i = 0; i < word.length(); i++) {
			if (letter == word.charAt(i)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Draws the state of the game.
	 *
	 * @param guesses
	 *            Number of guesses the player has left.
	 * @param known
	 *            What the player has already learned (letters or underscores).
	 * @param word
	 *            The correct word.
	 */
	public static void draw(int guesses, char[] known, String word) {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		// TODO Your drawing code goes here
		String s = "" + known[0];
		for (int i = 1; i < known.length; i++) {
			s += " " + known[i];
		}
		StdDraw.text(0.5, 0.2, s);
		if (guesses == 0) {
			StdDraw.text(0.5, 0.1, "The word was '" + word + "'. Press any key to play again.");
		} else if (isComplete(known)) {
			StdDraw.text(0.5, 0.1, "You win! Press any key to play again.");
		} else {
			StdDraw.text(0.5, 0.1, guesses + " guesses left. Type a letter.");
		}
		StdDraw.line(0.1, 0.3, 0.9, 0.3);
		double x=0; double y=0; double a=0.815;double b=0.0005; double c=0;double d=0;
		if (guesses < 6) {
			StdDraw.circle(0.5, 0.45, .15);//lower belly
			//butons
			StdDraw.filledCircle(0.5,0.425,0.01);
			StdDraw.filledCircle(0.5,0.375,0.01);
			StdDraw.filledCircle(0.5,0.475,0.01);
			StdDraw.filledCircle(0.5,0.3250,0.01);
			StdDraw.filledCircle(0.5,0.525,0.01);
			StdDraw.filledCircle(0.5,0.57,0.01);
		}
		if (guesses < 5) {
			StdDraw.circle(0.5, 0.7, .1);//middle belly
			//buttons
			StdDraw.filledCircle(0.5,0.625,0.01);
			StdDraw.filledCircle(0.5,0.675,0.01);
			StdDraw.filledCircle(0.5,0.725,0.01);
			StdDraw.filledCircle(0.5,0.775,0.01);
			//right arm
			StdDraw.line(0.6,0.7,0.73,0.7795);
			StdDraw.line(0.7,0.76,0.725,0.76);
			StdDraw.line(0.7,0.76,0.71,0.785);
			//left arm
			StdDraw.line(0.4,0.7,0.27,0.7795);
			StdDraw.line(0.3,0.76,0.275,0.76);
			StdDraw.line(0.3,0.76,0.29,0.785);
			StdDraw.setPenColor(StdDraw.BLACK);
			//scarf
			for (double i = .45; i < .47; i=i+0.001) {
				x=i;
				y=a;
				a=a-b;
				StdDraw.line(x,y,x,y-.04);
			}
			c=a;
			StdDraw.setPenColor(StdDraw.RED);
			for (double i = .47; i < .49; i=i+0.001) {
				x=i;
				y=a;
				a=a+b;
				StdDraw.line(x,y,x,y-.04);
			}
			StdDraw.setPenColor(StdDraw.BLACK);
			for (double i = .49; i < .51; i=i+0.001) {
				x=i;
				y=a;
				a=a-b;
				StdDraw.line(x,y,x,y-.04);
			}
			d=a;
			StdDraw.setPenColor(StdDraw.RED);
			for (double i = .51; i < .53; i=i+0.001) {
				x=i;
				y=a;
				a=a+b;
				StdDraw.line(x,y,x,y-.04);
			}
			StdDraw.setPenColor(StdDraw.BLACK);
			for (double i = .53; i < .55; i=i+0.001) {
				x=i;
				y=a;
				a=a-b;
				StdDraw.line(x,y,x,y-.04);
			}
		}
		if (guesses < 4) {
			StdDraw.circle(0.5, 0.85, 0.05);//head
			//redraw the parts of the scarf that got drawn over
			StdDraw.setPenColor(StdDraw.RED);
			for (double i = .47; i < .49; i=i+0.001) {
				x=i;
				y=c;
				c=c+0.0005;
				StdDraw.line(x,y,x,y-.04);
			}
			for (double i = .51; i < .53; i=i+0.001) {
				x=i;
				y=d;
				d=d+0.0005;
				StdDraw.line(x,y,x,y-.04);
			}
			StdDraw.setPenColor(StdDraw.BLACK);
		}
		if (guesses < 3) {
			StdDraw.filledSquare(0.48, 0.87, 0.01);//left eye
		}
		if (guesses < 2) {
			StdDraw.filledSquare(0.52, 0.87, 0.01);//right eye
		}
		if (guesses < 1) {//nose
			StdDraw.filledRectangle(0.5,0.9,0.06,0.004);//hat brim
			StdDraw.filledSquare(0.5,0.93,0.03);//hat
			StdDraw.setPenColor(StdDraw.ORANGE);
			double[] j = {0.5, 0.5, 0.575};
			double[] k = {0.85, 0.835, 0.8475};
			StdDraw.filledPolygon(j,k);//nose
		}
		StdDraw.show();
	}

	/**
	 * Modified known, filling in all instances of letter found in word. For
	 * example, if known is {'a', '_', '_', 'l', '_'}, word is "apple", and
	 * letter is 'p', known becomes {'a', 'p', 'p', 'l', '_'}.
	 */
	public static void fillIn(char[] known, String word, char letter) {
		// TODO You have to write this
		for(int i=0;i<word.length();i++){
			if(letter==word.charAt(i)){
				known[i]=letter;
			}
		}
	}

	/**
	 * Returns true if known contains no underscores.
	 *
	 * @param known
	 *            What the player has already learned (letters or underscores).
	 */
	public static boolean isComplete(char[] known) {
		// TODO You have to write this
		for(int i=0;i<known.length;i++){
			if(known[i]=='_'){
				return false;
			}
		}
		return true;
	}

	/** Runs the game repeatedly. */
	public static void main(String[] args) {
		StdDraw.enableDoubleBuffering();
		String[] dictionary = new In("enable1.txt").readAllLines();
		while (true) {
			String word = randomWord(dictionary);
			int guesses = 6;
			char[] known = new char[word.length()];
			for (int i = 0; i < known.length; i++) {
				known[i] = '_';
			}
			while (guesses > 0 && !isComplete(known)) {
				draw(guesses, known, word);
				while (!StdDraw.hasNextKeyTyped()) {
					// Wait for keypress
				}
				char letter = StdDraw.nextKeyTyped();
				if (contains(word, letter)) {
					fillIn(known, word, letter);
				} else {
					guesses--;
				}
			}
			draw(guesses, known, word);
			while (!StdDraw.hasNextKeyTyped()) {
				// Wait for keypress
			}
			StdDraw.nextKeyTyped(); // Use up the key typed
		}
	}

	/** Returns a random word from dictionary. */
	public static String randomWord(String[] dictionary) {
		// TODO You have to write this
		return dictionary[StdRandom.uniform(dictionary.length)];
	}

}
