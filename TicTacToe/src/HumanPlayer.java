import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer(String name, char mark) {
		super(name, mark);
	}
	
    public void makeMove() {
        Scanner scanner = new Scanner(System.in);
        int row = 3, col = 3;

        System.out.print("\n" + getName() + ", what row should your next " + getMark() + " be placed in? ");
        row = scanner.nextInt();
        System.out.print("\n" + getName() + ", what column should your next " + getMark() + " be placed in? ");
        col = scanner.nextInt();

        if (row > 2 || row < 0 || col > 2 || col < 0) {
            System.out.println("Invalid entry, enter numbers between 0 and 2. ");
            this.makeMove();
        } else {
            if (getBoard().getMark(row, col) != SPACE_CHAR) {
                System.out.println("There is already a mark there, please select another location. ");
                this.makeMove();
            } else {
            	getBoard().addMark(row, col, getMark());
            }
        }

        if (getBoard().xWins() || getBoard().oWins() || getBoard().isFull()) {
            scanner.close();
        }
    }

}
