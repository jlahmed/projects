
public class BlockingPlayer extends RandomPlayer {

	public BlockingPlayer(String name, char mark) {
		super(name, mark);
	}
	
	@Override
	public void makeMove() {
				
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (testForBlocking(row, col)) {
					getBoard().addMark(row, col, getMark());
					return;
				}
			}
		}
		super.makeMove();
	}
	
	protected boolean testForBlocking(int row, int col) {
		boolean block = false;
		
		if (getBoard().getMark(row, col) == SPACE_CHAR) {
			getBoard().addMark(row, col, getOpponent().getMark());
			
			if (getBoard().oWins() || getBoard().xWins()) {
				block = true;
			}
			getBoard().removeMark(row, col);
		}
		return block;
	}

}
