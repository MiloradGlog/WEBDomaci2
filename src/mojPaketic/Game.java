package mojPaketic;

public class Game {

	private int[] table;
	private int[] resultTable;
	private boolean[] guessTable;
	
	public Game(int[] table) {
		this.resultTable = new int[6];
		resetResultTable();
		this.guessTable = new boolean[6];
		resetGuessTable();
		this.table = table;
		
		playRound(0);
		
	}
	
	public int[] playGame() {
		playRound(0);
		printResultTable();
		return resultTable;
	}
	
	
	private void playRound(int playerIndex) {
		for (int i = 0; i < 6; i++) {
			if (i == playerIndex)	
				continue;
			guess(i);
		}
		boolean drawnShort = draw(playerIndex);
		
		calculatePoints(playerIndex);
		
		if (drawnShort) {
			System.out.println("izvukao kratku");
		} else {
			System.out.println("izvukao dugu");
			int next = playerIndex + 1;
			if (next >= 6) next = 0;
			playRound(next);
		}
		
	}
	
	
	private boolean draw(int index) {
		boolean result = getRandomBoolean(15);

		guessTable[index] = result;
		
		if (result) {
			System.out.println("Igrac ["+ table[index] +"] izvlaci kraci stapic!");
		} else {
			System.out.println("Igrac ["+ table[index] +"] izvlaci duzi stapic");
		}
		
		return result;
	}
	
	private void guess(int index) {
		boolean result = getRandomBoolean(30);
		
		guessTable[index] = result;
		
		if (result) {
			System.out.println("Igrac ["+ table[index] +"] nagadja da ce biti izvucen kraci stapic");
		} else {
			System.out.println("Igrac ["+ table[index] +"] nagadja da ce biti izvucen duzi stapic");
		}
	}
	
	private void calculatePoints(int playerIndex) {
		for (int i = 0; i < 6; i++) {
			if (i == playerIndex)	
				continue;
			if (guessTable[i] == guessTable[playerIndex]) {
				resultTable[i]++;
			}
		}
	}
	
	private boolean getRandomBoolean(int chance) {
		if ((int)(Math.random()*100) < chance) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void resetTable() {
		for (int i = 0; i < 6; i++) {
			table[i] = -1;
		}
	}
	
	private void resetResultTable() {
		for (int i = 0; i < 6; i++) {
			resultTable[i] = 0;
		}
	}
	
	private void resetGuessTable() {
		for (int i = 0; i < 6; i++) {
			guessTable[i] = false;
		}
	}
	   
	private boolean checkIfTableIsFull() {
		for (int i = 0; i < 6; i++) {
			if (table[i] == -1) {
				return false;
			}
		}
		return true;
	}
	private void printResultTable() {
		   StringBuilder sb = new StringBuilder();
		   sb.append("-----REZULTATI-----\n");
		   for (int i = 0; i < 6; i++) {
			   switch (i) {
			   case(0): {
				   sb.append("     [" + resultTable[i] + "]\n");	break;
			   }case(1): {
				   sb.append("[" + resultTable[i] + "]/----\\");	break;
			   }case(2): {
				   sb.append("[" + resultTable[i] + "]\n");	break;
			   }case(3): {
				   sb.append("[" + resultTable[i] + "]\\----/");	break;
			   }case(4): {
				   sb.append("[" + resultTable[i] + "]\n");	break;
			   }case(5): {
				   sb.append("     [" + resultTable[i] + "]");	break;
			   }
			   }
		   }
		   System.out.println(sb.toString());
	   }

	
}
