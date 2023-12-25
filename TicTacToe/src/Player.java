/**
 * Represents a player in a tic-tac-toe game.
 */
abstract class Player implements Constants{
    private String name;
    private char mark;
    private Player opponent;
    private Board board;

    /**
     * Initializes a player with a name and a game mark (X or O).
     *
     * @param name The name of the player.
     * @param mark The game mark for the player (X or O).
     */
    protected Player(String name, char mark) {
        this.name = name;
        this.setMark(mark);
    }

    /**
     * Initiates the player's turn, allowing them to make a move and displaying the updated game board.
     */
    protected void play() {
        makeMove();
        System.out.println("\n");
        board.display();
    }

    /**
     * Allows the player to make a move on the game board by selecting a row and column.
     */
    abstract public void makeMove();

    /**
     * Sets the opponent player for this player.
     *
     * @param p The opponent player.
     */
    protected void setOpponent(Player p) {
        opponent = p;
    }
    
    protected Board getBoard() {
    	return board;
    }

    /**
     * Sets the game board for this player.
     *
     * @param theBoard The game board.
     */
    protected void setBoard(Board theBoard) {
        board = theBoard;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    protected String getName() {
        return name;
    }

    /**
     * Gets the opponent player.
     *
     * @return The opponent player.
     */
    protected Player getOpponent() {
        return opponent;
    }

    protected char getMark() {
		return mark;
	}

    protected void setMark(char mark) {
		this.mark = mark;
	}
}
