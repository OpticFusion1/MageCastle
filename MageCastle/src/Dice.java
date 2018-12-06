import java.util.Random;

public class Dice {

	// simple dice class
	public Dice() {
	}
	
	public int rollDie(int sides) {
		Random rand = new Random();

		int roll = rand.nextInt(sides) + 1;
		
		return roll;
	}
}
