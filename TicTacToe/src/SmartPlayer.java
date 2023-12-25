
public class SmartPlayer extends BlockingPlayer{

	public SmartPlayer(String name, char mark) {
		super(name, mark);
	}
	
	@Override
	public void makeMove() {
		
		boolean block = false;
		int rowb = 0, colb = 0;
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (testForWinning(row, col)) {
					getBoard().addMark(row, col, getMark());
					return;
				}
				
				if (testForBlocking(row, col)) {
					block = true;
					rowb = row;
					colb = col;
				}
			}
		}
		
		if (block) {
			getBoard().addMark(rowb, colb, getMark());
		}else {
			randomMove();
		}
	}
	
	private boolean testForWinning(int row, int col) {
		boolean win = false;
		
		if (getBoard().getMark(row, col) == SPACE_CHAR) {
			getBoard().addMark(row, col, getMark());
			
			if (getBoard().oWins() || getBoard().xWins()) {
				win = true;
			}
			getBoard().removeMark(row, col);
		}
		return win;
	}

}
