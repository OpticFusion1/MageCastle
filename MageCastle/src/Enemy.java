
public class Enemy extends Character {
	
	//create a dice object 
	Dice dice = new Dice();
	
	//variable and constructor that creates an enemy with specified name and stats. 
	public String enemyName;
	
	public Enemy(String enemyName, int attack, int defense, int maxHp) {
		this.enemyName = enemyName;
		this.attack = attack;
		this.defense = defense;
		this.maxHp = maxHp;
		this.currentHp = maxHp;
	}
	
	//getter 
	public String getEnemyName() {
		return enemyName;
	}
	
	//method that determines what enemy is attacking and then returns the damage that specific enemy deals. 
	public int enemyAttack(String enemyName) {
		int damage = 0;
		switch (enemyName) {
		case "Skeleton": damage = dice.rollDie(6) + attack;
			 break;
		case "Zombie": damage = dice.rollDie(6) + attack;
			 break;
		case "Necromancer": damage = dice.rollDie(8) + attack;
			 break;
		case "Skeleton Knight": damage = dice.rollDie(8) + attack;
			 break;
		case "Dark Mage": damage = dice.rollDie(10) + attack;
			 break;
		case "Stone Golem": damage = dice.rollDie(12) + attack;
			 break;
		case "Vengeful Wraith": damage = dice.rollDie(8) + attack;
			 break;
		case "Vampire Assassin": damage = dice.rollDie(8) + attack;
			 break;
		case "Zombie Hulk": damage = dice.rollDie(16) + attack;
			 break;
		case "Demonspawn": damage = dice.rollDie(6) + attack;
			 break;
		case "Master Necromancer": damage = dice.rollDie(12) + attack;
			 break;
		case "Rotting Dragon": damage = dice.rollDie(16) + attack;
			 break;
		}
		return damage;
	}
	
	//tostring
	@Override
	public String toString() {
		String enemyStats = "Enemy: " + enemyName + " HP: " + currentHp;
		return enemyStats;
	}
	
	
}
