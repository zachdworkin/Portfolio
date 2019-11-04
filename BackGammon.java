public class BackGammon {
    public static void main(String[] args) {
        new BackGammon().run();
    }

    /** The associated game instance. */
    private BackGammonModel model;

    /** Plays the game. */
    public void run() {
        StdDraw.setXscale(0, 10);
        StdDraw.setYscale(0, 10);
        StdDraw.text(5.0, 8.0, "Focus");
        StdDraw.text(5.0, 6.0, "Get your pieces on top of the piles.");
        StdDraw.text(5.0, 4.0, "The first player unable to move loses.");
        StdDraw.text(5.0, 2.0, "Click to continue.");
        StdDraw.show();
        waitForClick();
        while (!model.isGameOver()) {
            draw(getCurrentPlayerName()
                    + ", click on one of your piles or your reserves.", null);
            Location source;
            do {
                source = waitForClick();
            } while (source == null || !model.isLegalSource(source));
            draw(getCurrentPlayerName() + ", click on destination square.", source);
            Location destination;
            do {
                destination = waitForClick();
            } while (destination == null
                    || !model.isLegalMove(source, destination));
            model.move(source, destination);
            model.toggleColorToPlay();
        }
        model.toggleColorToPlay();
        draw(getCurrentPlayerName() + " wins.", null);
    }
}
