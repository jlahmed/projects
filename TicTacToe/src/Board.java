/**
 * Represents a tic-tac-toe game board.
 */
public class Board implements Constants {
    private char theBoard[][];
    private int markCount;

    /**
     * Initializes an empty tic-tac-toe game board.
     */
    public Board() {
        markCount = 0;
        theBoard = new char[3][];
        for (int i = 0; i < 3; i++) {
            theBoard[i] = new char[3];
            for (int j = 0; j < 3; j++)
                theBoard[i][j] = SPACE_CHAR;
        }
    }

    /**
     * Gets the mark (X, O, or space) at the specified row and column.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The mark at the specified position.
     */
    public char getMark(int row, int col) {
        return theBoard[row][col];
    }

    /**
     * Checks if the board is full.
     *
     * @return True if the board is full, false otherwise.
     */
    public boolean isFull() {
        return markCount == 9;
    }

    /**
     * Checks if player X wins the game.
     *
     * @return True if player X wins, false otherwise.
     */
    public boolean xWins() {
        return checkWinner(LETTER_X) == 1;
    }

    /**
     * Checks if player O wins the game.
     *
     * @return True if player O wins, false otherwise.
     */
    public boolean oWins() {
        return checkWinner(LETTER_O) == 1;
    }

    /**
     * Displays the current state of the tic-tac-toe game board.
     */
    public void display() {
        displayColumnHeaders();
        addHyphens();
        for (int row = 0; row < 3; row++) {
            addSpaces();
            System.out.print("    row " + row + ' ');
            for (int col = 0; col < 3; col++)
                System.out.print("|  " + getMark(row, col) + "  ");
            System.out.println("|");
            addSpaces();
            addHyphens();
        }
    }

    /**
     * Adds a player's mark (X or O) to the specified row and column.
     *
     * @param row  The row index.
     * @param col  The column index.
     * @param mark The player's mark to add (X or O).
     */
    public void addMark(int row, int col, char mark) {
        theBoard[row][col] = mark;
        markCount++;
    }

    /**
     * Clears the game board, resetting all positions to empty.
     */
    public void clear() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                theBoard[i][j] = SPACE_CHAR;
        markCount = 0;
    }

    /**
     * Checks if a player with the specified mark wins the game.
     *
     * @param mark The player's mark to check (X or O).
     * @return 1 if the player wins, 0 otherwise.
     */
    int checkWinner(char mark) {
        int row, col;
        int result = 0;

        for (row = 0; result == 0 && row < 3; row++) {
            int row_result = 1;
            for (col = 0; row_result == 1 && col < 3; col++)
                if (theBoard[row][col] != mark)
                    row_result = 0;
            if (row_result != 0)
                result = 1;
        }

        for (col = 0; result == 0 && col < 3; col++) {
            int col_result = 1;
            for (row = 0; col_result != 0 && row < 3; row++)
                if (theBoard[row][col] != mark)
                    col_result = 0;
            if (col_result != 0)
                result = 1;
        }

        if (result == 0) {
            int diag1Result = 1;
            for (row = 0; diag1Result != 0 && row < 3; row++)
                if (theBoard[row][row] != mark)
                    diag1Result = 0;
            if (diag1Result != 0)
                result = 1;
        }
        if (result == 0) {
            int diag2Result = 1;
            for (row = 0; diag2Result != 0 && row < 3; row++)
                if (theBoard[row][3 - 1 - row] != mark)
                    diag2Result = 0;
            if (diag2Result != 0)
                result = 1;
        }
        return result;
    }

    /**
     * Displays the column headers for the game board.
     */
    void displayColumnHeaders() {
        System.out.print("          ");
        for (int j = 0; j < 3; j++)
            System.out.print("|col " + j);
        System.out.println();
    }

    /**
     * Adds hyphens to separate rows in the game board display.
     */
    void addHyphens() {
        System.out.print("          ");
        for (int j = 0; j < 3; j++)
            System.out.print("+-----");
        System.out.println("+");
    }

    /**
     * Adds spaces to separate rows in the game board display.
     */
    void addSpaces() {
        System.out.print("          ");
        for (int j = 0; j < 3; j++)
            System.out.print("|     ");
        System.out.println("|");
    }
    
    /**
     * Removes a player's mark (X or O) to the specified row and column.
     *
     * @param row  The row index.
     * @param col  The column index.
     */
    public void removeMark(int row, int col) {
        theBoard[row][col] = SPACE_CHAR;
        markCount--;
    }

}
