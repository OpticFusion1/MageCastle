import javafx.scene.control.TextField;

public class Player extends Character {
	
	//create dice object
	Dice dice = new Dice();
	
	//variables
	public String playerName;
	//array to hold spells
	public String[] spells = {"Fireball", "Lightning", "Ice Spike", "Stone Fist", "Judgement"};
	public int hpPotions;
	public int mpPotions;
	
	//constructor to create player after a name is given
	public Player(String playerName) {
		this.playerName = playerName;
		attack = 10;
		defense = 5;
		maxHp = 50;
		maxMp = 100;
		currentHp = maxHp;
		currentMp = maxMp;
		level = 0;
		hpPotions = 5;
		mpPotions = 5;
	}
	
	//getter for playername
	public String getPlayerName() {
		return playerName;
	}
	
	//potion-related methods
	public void useHpPotion() {
		this.hpPotions = hpPotions - 1;
	}
	
	public void useMpPotion() {
		this.mpPotions = mpPotions - 1;
	}
	
	//method to determine player damage for a regular attack
	public int playerAttack() {
		int damage = dice.rollDie(4) + attack;
		return damage;
	}
	
	//method to determine damage for each spell
	public int castSpell(int index) {
		int damage = 0;
		switch (index) {
		case 0: damage = dice.rollDie(6) + attack;
				deductMp(10);
				break;
		case 1: damage = dice.rollDie(12) + attack;
				deductMp(25);
				break;
		case 2: damage = dice.rollDie(8) + attack;
				deductMp(20);
				break;
		case 3: damage = dice.rollDie(20) + attack;
				deductMp(40);
				break;
		case 4: damage = dice.rollDie(20) + attack + 100;
				deductMp(100);
				break;
		}
		
		return damage;
	}
	
	//tostring
	@Override
	public String toString() {
		String playerStats = playerName + " HP: " + currentHp + " MP: " + currentMp  + " HP Potions: " + hpPotions + " MP Potions: " + mpPotions;
		return playerStats;
	}
}
