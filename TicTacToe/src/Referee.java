/**
 * Manages the tic-tac-toe game by coordinating the actions of players and the game board.
 */
public class Referee {
    private Board board;
    private Player xPlayer;
    private Player oPlayer;

    /**
     * Runs the tic-tac-toe game, coordinating player actions and checking for a winner or a draw.
     */
    public void runTheGame() {
        xPlayer.setOpponent(oPlayer);
        oPlayer.setOpponent(xPlayer);
        
        System.out.println("\nReferee started the game...\n");
        board.display();

        while (!board.xWins() && !board.oWins() && !board.isFull()) {
            xPlayer.play();

            if (board.xWins()) {
                System.out.println("THE GAME IS OVER: " + xPlayer.getName() + " is the winner!\nGame Ended...");
                break;
            }

            if (!board.isFull()) {
                oPlayer.play();

                if (board.oWins()) {
                    System.out.println("THE GAME IS OVER: " + oPlayer.getName() + " is the winner!\nGame Ended...");
                    break;
                }
            }

            if (board.isFull()) {
                System.out.println("Game over, it is a draw!");
            }
        }
    }

    /**
     * Sets the game board for the referee.
     *
     * @param board The game board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the 'X' player for the referee.
     *
     * @param xPlayer The 'X' player.
     */
    public void setxPlayer(Player xPlayer) {
        this.xPlayer = xPlayer;
    }

    /**
     * Sets the 'O' player for the referee.
     *
     * @param oPlayer The 'O' player.
     */
    public void setoPlayer(Player oPlayer) {
        this.oPlayer = oPlayer;
    }
}
