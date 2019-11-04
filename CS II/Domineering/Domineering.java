/** A domino-placing game invented by Goran Andersson. */
//Program by Zach Dworkin
public class Domineering {

	/**
	 * Draws the current state of the game, including instructions to the user.
	 */
	public static void draw(boolean[][] board, boolean verticalToPlay) {
		// TODO You have to write this
		StdDraw.clear();
		int k=0;
		for(int i=0; i<8; i++){//goes through all the x values
			for(int j=0;j<8;j++){//goes through all of the y values
				if((k%2==0)){
					StdDraw.setPenColor(StdDraw.BLUE);//sets the pen color
				}else{
					StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);//sets the pen color
				}
				//changes it to black afterwards because somebodu could have already played there
				if(board[i][j]==true){//changes it to black if someone has already played there
					StdDraw.setPenColor(StdDraw.BLACK);
				}
				StdDraw.filledRectangle(i,j,.5,.5);
				k++;
			}
			k++;
		}
		StdDraw.setPenColor(StdDraw.BLACK);//sets the pen to black to write in
		if ((gameOver(board,verticalToPlay)==true)){//if the game has been won, print stuff
			if(verticalToPlay==true) {
				StdDraw.text(3.5, 8.0, "Vertical Cannot Play so, Horizontal Wins!");
			}else{
				StdDraw.text(3.5, 8.0, "Horizontal Cannot Play so, Vertical Wins!");
			}
		} else {
			if(verticalToPlay==true) {//if the game has not been won, print who's turn it is
				StdDraw.text(3.5, 8.0, "Vertical Player, Click on Upper Square to Place Domino.");
			}else{
				StdDraw.text(3.5, 8.0, "Horizontal Player, Click on Left Square to Place Domino.");
			}
		}
		StdDraw.show();
	}

	/**
	 * Returns true if the game is over, i.e., the current player has no legal
	 * move.
	 */
	public static boolean gameOver(boolean[][] board, boolean verticalToPlay) {
		// TODO You have to write this
		//run a double loop that checks the vertical plays and horizontal plays
		boolean flag=true;
		if(verticalToPlay==true){
			//write for is legal in the vertical play
			//StdOut.println();
			for(int i=0;i<8;i++){
				for(int j=1;j<8;j++) {//starts at 1 in the y-axis because you cant play vertically in any of the 0 rows
					if(flag==true) {//makes it so that if there is already an open space it wont change the flag anymore
						if ((board[i][j] == false)&&(isLegal(i,j,board,verticalToPlay)==true)) {//only checks open places to play
							flag=false;//means there is still space left to play
						}
					}
				}
			}
		}else{
			//write for is legal in the horizontal play
			for(int i=0;i<7;i++){//ends at 7 in the x-axis because you cant play horizontally in any of the 0 rows
				for(int j=0;j<8;j++) {
					if(flag==true) {//makes it so that if there is already an open space it wont change the flag anymore
						if ((board[i][j] == false)&&(isLegal(i,j,board,verticalToPlay)==true)) {//only checks open places to play
							flag=false;//means there is still space left to play
						}
					}
				}
			}
		}
		if(flag==false){
			return false;//returns false because the game is not over yet
		}else{
			return true;//returns true because the game is won by someone
		}
	}

	/**
	 * Plays a move as specified by the user's mouse click. Returns true if
	 * vertical is to play next, false otherwise. If an illegal move is
	 * attempted, this method has no effect and returns the value passed in for
	 * verticalToPlay.
	 */
	public static boolean handleMouseClick(boolean[][] board, boolean verticalToPlay) {
		// TODO You have to write this
		while(!StdDraw.isMousePressed()){
			// Wait for mouse press
		}
		double x = Math.round(StdDraw.mouseX());
		double y = Math.round(StdDraw.mouseY());
		while (StdDraw.isMousePressed()) {
			// Wait for mouse release
		}
		int a = (int) x;
		int b = (int) y;
		if((a>=0&&a<=7)&&(b>=0&&b<=7)) {
			if (isLegal(a, b, board, verticalToPlay) == true) {//if the click is a legal play
				placeDomino(a, b, board, verticalToPlay);//update the behind the scenes board to place it
				//opposite(verticalToPlay);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	/**
	 * Returns true if playing at x, y is legal for the current player. Assumes
	 * that x, y is on the board but verifies that the other half of the domino
	 * is on the board.
	 */
	public static boolean isLegal(int x, int y, boolean[][] board, boolean verticalToPlay) {
		// TODO You have to write this
		// works
		boolean flag=false;
		if(verticalToPlay==true){//check vertical play
			if(y-1>=0){//checks to make sure you dont play off of the bottom of the board.
				if((board[x][y]==true)||(board[x][y-1]==true)){//if either spot is true, that means someone has played there already
					flag = true;//makes it return a false because the play is illegal
				}
			}else{//means y-1 is greater than 0
				flag=true;//makes it return false because the play is illegal since you would play off the board
			}
		}else{//check horizontal play
			if(x+1<8) {//makes sure you donnt play off of the right side of the board
				if ((board[x][y] == true)||(board[x + 1][y] == true)){//if either spot is true, that means someone has played there already
					flag = true;//makes it return a false because the play is illegal
				}
			}else{//means x+1 is less than 8
				flag=true;//makes it return false because the play is illegal since you would play off the board
			}
		}
		if (flag==true){
			return false;//means the play is illegal
		}else{
			return true;//means the play is legal
		}
	}

	/** Plays the game. */
	public static void main(String[] args) {
		// TODO You have to write this
		boolean[][] board = new boolean[8][8];
		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-1.5,8.5);
		boolean verticalToPlay=true;
		draw(board,verticalToPlay);
		while(gameOver(board,verticalToPlay)!=true){//only enters the loop if the person can play
			if((handleMouseClick(board,verticalToPlay))==true){
				draw(board,opposite(verticalToPlay));
				verticalToPlay=opposite(verticalToPlay);//switches turns behind the scenes
			}
		}
		if(gameOver(board,verticalToPlay)==true){
			draw(board,verticalToPlay);
		}


	}

	/**
	 * Returns the opposite value for verticalToPlay. For example,
	 * opposite(true) is false.
	 */
	public static boolean opposite(boolean verticalToPlay) {
		// TODO You have to write this
		//works
		verticalToPlay=!verticalToPlay;//switches the value of verticalToPlay
		return verticalToPlay;
	}

	/**
	 * Places a domino at x, y with the specified orientation. Assumes the
	 * placement is legal.
	 */
	public static void placeDomino(int x, int y, boolean[][] board, boolean verticalToPlay) {
		// TODO You have to write this
		//works
		boolean flag=false;
		if(verticalToPlay==true) {
			//plays vertically
			board[x][y] = true;
			board[x][y - 1] = true;
		}else{
			//plays horizontally
			board[x][y] = true;
			board[x + 1][y] = true;
		}
	}

}
