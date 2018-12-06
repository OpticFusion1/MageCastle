
public abstract class Character {
	
	//variables
	public int defense;
	public int attack;
	public int maxHp;
	public int maxMp;
	public int currentHp;
	public int currentMp;
	public int level;
	
	//no-arg constructor
	public Character() {		
	}
	
	//setters and getters
	public int getDef() {
		return defense;
	}
	
	public void setDef(int modDef) {
		this.defense = modDef;
	}
	
	public int getAtk() {
		return attack;
	}
	
	public void setAtk(int modAtk) {
		this.attack = modAtk;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	
	public void setMaxHp(int modHp) {
		this.maxHp = modHp;
	}
	
	public int getMaxMp() {
		return maxMp;
	}
	
	public void setMaxMp(int modMp) {
		this.maxMp = modMp;
	}
	
	//hp-related methods
	public int getCurrentHp() {
		return currentHp;
	}
	
	
	public void deductHp(int damage) {
		this.currentHp = currentHp - (damage - defense);
	}
	
	public void healHp() {
		this.currentHp = currentHp + 25;
	}
	
	//mp-related methods
	public int getCurrentMp() {
		return currentMp;
	}
	
	public void deductMp(int mpUsed) {
		this.currentMp = currentMp - mpUsed;
	}
	
	public void restoreMp() {
		this.currentMp = currentMp + 25;
	}
	
	//level-related methods
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void levelUp() {
		this.attack = attack + 5;
		this.defense = defense + 5;
		this.maxHp = maxHp + 25;
		this.maxMp = maxMp + 15;
		this.currentHp = this.maxHp;
		this.currentMp = this.maxMp;
	}
	
	//tostring
	public abstract String toString();

}
