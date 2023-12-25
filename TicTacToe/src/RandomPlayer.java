
public class RandomPlayer extends Player {

	public RandomPlayer(String name, char mark) {
		super(name, mark);
	}

	@Override
	public void makeMove() {
		randomMove();
        
	}
	
	public void randomMove() {
		RandomGenerator ran = new RandomGenerator();
        int row = ran.discrete(0, 2), col = ran.discrete(0, 2);

        if (getBoard().getMark(row, col) != SPACE_CHAR) {
            randomMove();
        } else {
        	getBoard().addMark(row, col, getMark());
        }
	}

}
