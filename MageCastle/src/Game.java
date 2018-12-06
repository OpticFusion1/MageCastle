
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
	
	// declare scenes beforehand
	Scene introScene, creationScene, battleScene, gameOverScene, winScene;
	
	// create dice
	static Dice dice = new Dice();
	
	// start method
	@Override
	public void start(Stage window) throws Exception {
		
		// declare all strings for the GUI
		String intro1 = "Today, you travel to the castle with your brother Elzho to acquire the blessings of the King in your final day of training as an Imperial Mage.";
		String intro2 = "As you arrive at the castle, you and your brother are ushered into the throne room, where His Majesty, old and withered, awaits.";
		String intro3 = "Before you can react, Elzho summons a torrent of dark energy, and the guards of the castle transform into all manner of gruesome creatures.";
		String intro4 = "With nothing but an evil cackle, Elzho makes the ground beneath you disappear as you tumble into the depths of the castle's dungeon.";
		String gameTitle = "Mage Castle";
		String nameHero = "Enter a name for your hero: ";
		String skeletonBattle = "The bones of the dead rise to oppose you!";
		
		// declare labels for intro scene
		Label gameTitleLbl = new Label(gameTitle);
		Label gameIntroLbl1 = new Label(intro1);
		Label gameIntroLbl2 = new Label(intro2);
		Label gameIntroLbl3 = new Label(intro3);
		Label gameIntroLbl4 = new Label(intro4);
		
		// declare button that switches scenes
		Button continueBtn = new Button("Continue");
		continueBtn.setOnAction(e -> window.setScene(creationScene));
		
		// declare pane 1
		VBox pane1 = new VBox(500);
		pane1.setAlignment(Pos.BASELINE_CENTER);
		pane1.setSpacing(10);
		pane1.setPadding(new Insets(0,10,20,10));
		pane1.getChildren().addAll(gameTitleLbl, gameIntroLbl1, gameIntroLbl2, gameIntroLbl3, gameIntroLbl4, continueBtn);
		
		// put pane 1 in intro scene
		introScene = new Scene(pane1, 800, 200);
		
		// label for creation scene
		Label nameHeroLbl = new Label(nameHero);
		
		// create text field for name input
		TextField nameHeroTxt = new TextField();
		nameHeroTxt.setPrefColumnCount(20);
		
		// create new instance of Player with name from text box
		Player player = new Player(nameHeroTxt.getText()); // this code isn't working properly yet; figure out why
		
		// button that switches scenes
		Button continueBtn2 = new Button("Continue");
		continueBtn2.setOnAction(e -> {
			window.setScene(battleScene);
		});
		
		// declare pane 2
		VBox pane2 = new VBox(20);
		pane2.setAlignment(Pos.BASELINE_CENTER);
		pane2.setSpacing(10);
		pane2.setPadding(new Insets(0,10,20,10));
		pane2.getChildren().addAll(nameHeroLbl, nameHeroTxt, continueBtn2);
		
		// put pane 2 in creation scene
		creationScene = new Scene(pane2, 400, 200);
		
		// create the first instance of Enemy
		Enemy skeleton = new Enemy("Skeleton", 15, 5, 30);
		
		// declare labels for battle scene
		Label playerNameLbl = new Label(player.playerName);
		Label hpLbl = new Label("HP: " + player.currentHp);
		Label mpLbl = new Label("MP: " + player.currentMp);
		Label hpPotionsLbl = new Label("HP Potions: " + player.hpPotions);
		Label mpPotionsLbl = new Label("MP Potions: " + player.mpPotions);
		Label battleTextLbl = new Label(skeletonBattle);
		Label playerTextLbl = new Label();
		Label enemyTextLbl = new Label();
		Label skeletonStatusLbl = new Label(skeleton.enemyName + " HP: " + skeleton.currentHp);
		
		// declare buttons for player actions
		Button attackBtn = new Button("Attack");
		Button spellBtn = new Button("Cast Spell");
		Button hpPotBtn = new Button("Use HP Potion");
		Button mpPotBtn = new Button("Use MP Potion");
		
		// attack button listener code
		attackBtn.setOnAction(e -> {
			// roll dice to see if player hits the attack. If they do, calculate damage, deduct from Enemy, and update Enemy's HP label
			if (dice.rollDie(20) >= 10) {
				int damage = player.playerAttack();
				playerTextLbl.setText("You swipe at the enemy with your dagger! Hit for " + (damage - skeleton.defense));
				skeleton.deductHp(damage);	
				skeletonStatusLbl.setText(skeleton.enemyName + " HP: " + skeleton.currentHp);
			}
			else {
				playerTextLbl.setText("You swipe at the enemy with your dagger, but you miss!");
			}
			
			// every time an action is performed, Enemy should attack back. Roll die, calculate, deduct, update label
			if (skeleton.currentHp > 0) {
				if (dice.rollDie(20) > 10) {
					int damage = skeleton.enemyAttack("Skeleton");
					enemyTextLbl.setText("The enemy hits you for " + (damage - player.defense));
					player.deductHp(damage);
					hpLbl.setText("HP: " + player.currentHp);
       	 		}
       	 		else {
       	 			enemyTextLbl.setText("The enemy attacks, but misses!");
       	 		}
			}
			// set scene to win scene if Enemy HP drops to 0
			else {
				window.setScene(winScene);
			}
			
			// set scene to game over scene if Player HP drops to 0
			if (player.currentHp <= 0) {
				window.setScene(gameOverScene);
			}
			
		});
		
		// declare BorderPane for pane 3
		BorderPane pane3 = new BorderPane();
		
		// add nodes to boxes
		HBox playerStatus = new HBox(20);
		playerStatus.setSpacing(10);
		playerStatus.getChildren().addAll(playerNameLbl, hpLbl, mpLbl, hpPotionsLbl, mpPotionsLbl);
		
		HBox playerActions = new HBox(20);
		playerActions.setSpacing(10);
		playerActions.getChildren().addAll(attackBtn, spellBtn, hpPotBtn, mpPotBtn);
		
		VBox battleText = new VBox(20);
		battleText.getChildren().addAll(battleTextLbl, playerTextLbl, enemyTextLbl, skeletonStatusLbl);
		
		// add boxes to pane 3
		pane3.setTop(playerStatus);
		pane3.setCenter(battleText);
		pane3.setBottom(playerActions);
		
		// add pane 3 to battle scene
		battleScene = new Scene(pane3, 400, 200); 
		
		// label for game over scene
		Label gameOverLbl = new Label("You have died. Exit the dungeon.");
		
		// declare pane 4, and add label to it
		VBox pane4 = new VBox(20);
		pane4.getChildren().add(gameOverLbl);
		
		// add game over scene to pane 4
		gameOverScene = new Scene(pane4, 400, 200);
		
		// label for win scene
		Label wonBattleLbl = new Label("You have won the battle!");
		
		// add a button that will continue to the next battle
		Button nextBattleBtn = new Button("Continue"); // add listener code
		
		// declare pane 5, add label and button to it
		VBox pane5 = new VBox(20);
		pane5.getChildren().addAll(wonBattleLbl, nextBattleBtn);
		
		// add pane 5 to win scene
		winScene = new Scene(pane5, 400, 200);
		
		// set window title, starting scene, and then show the window
		window.setTitle(gameTitle);
		window.setScene(introScene);
		window.show();
		
		
	}
	public static void main(String[] args) {
		
		launch(args);
	} 
	
	/*
	public static boolean fight(Enemy enemy, Player player) {
		
		
	    boolean won = false;
		int actionSpell = 0;
		int damage = 0;
		
		String playerMissed = "You missed!";
		String enemyMissed = enemy.getEnemyName() + " missed!";
		String playerSpellDmg = "";
		String playerDmg = "";
		String enemyDmg = "";
		String hpString = "You down a Potion of Health and regain 25 HP";
		String mpString = "You down a Potion of Mana and regain 25 MP";
		String noPotions = "You have run out of this kind of potion!";
		String inquiry = "What will you do?";
		String actionOp1 = "0. Attack";
		String actionOp2 = "1. Cast Spell";
		String actionOp3 = "2. Use HP potion";
		String actionOp4 = "3. Use MP potion";
		String invalidInput = "Invalid input. Try again.";
		String inquiry2 = "What spell will you cast?";
		String spellOp1 = "0. " + player.spells[0];
		String spellOp2 = "1. " + player.spells[1];
		String spellOp3 = "2. " + player.spells[2];
		String spellOp4 = "3. " + player.spells[3];
		String spellOp5 = "4. " + player.spells[4];
		
		// loop that keeps repeating as long as both the enemy and player are alive
		while (enemy.currentHp > 0 & player.currentHp > 0) {
			System.out.println(enemy.toString()); //test
			System.out.println(player.toString()); //test
			System.out.println(inquiry); //test
			System.out.println(actionOp1); //test
			System.out.println(actionOp2); //test
			System.out.println(actionOp3); //test
			System.out.println(actionOp4); //test
			int action = input.nextInt();
			
			if (action < 0 | action > 3) {
				System.out.println(invalidInput); //test
				action = input.nextInt();
			}
			
			switch (action) {
			//case for regular attack
			case 0: if (dice.rollDie(20) > 10) {
					damage = player.playerAttack();
					playerDmg = "You swipe at the enemy with your dagger! Hit for " + damage;
					System.out.println(playerDmg); //test
					enemy.deductHp(damage);
					}
					else {
						System.out.println(playerMissed); //test
					}
					break;
			
					//case for cast spell
			case 1: System.out.println(inquiry2); //test
					//this switch statement makes sure player only sees the spells that their level allows, keeps track of which one is selected, and then determines the damage done.
					switch (player.getLevel()) {
					case 0: System.out.println(spellOp1); //test
					        actionSpell = input.nextInt(); //test
					        
					        //input validation
					        if (actionSpell != 0) {
					        	 System.out.println(invalidInput); //test
					        	 actionSpell = input.nextInt(); //test
					        }
					        else {
					        	 
					        	 if (dice.rollDie(20) > 10) {
					        		 damage = player.castSpell(actionSpell);
					        		 playerSpellDmg = "You cast " + player.spells[actionSpell] + "! Hit for " + damage; 
					        		 System.out.println(playerSpellDmg); //test
					        		 enemy.deductHp(damage);
					        	 }
					        	 else {
					        		 System.out.println(playerMissed); //test
					        	 }
					        	 
					        }
					        break;
					        
					case 1: System.out.println(spellOp1); //test
							System.out.println(spellOp2); //test
							actionSpell = input.nextInt(); //test
							if (actionSpell > 1 | actionSpell < 0) {
								 System.out.println(invalidInput); //test
					        	 actionSpell = input.nextInt(); //test
							}
					        else {
					        	 if (dice.rollDie(20) > 10) {
					        		 damage = player.castSpell(actionSpell);
					        		 playerSpellDmg = "You cast " + player.spells[actionSpell] + "! Hit for " + damage;
					        		 System.out.println(playerSpellDmg); //test
						        	 enemy.deductHp(damage);
					        	 }
					        	 else {
					        		 System.out.println(playerMissed); //test
					        	 }
					        }
							break;
							
					case 2:  System.out.println(spellOp1); //test
							 System.out.println(spellOp2); //test
							 System.out.println(spellOp3); //test
							 actionSpell = input.nextInt(); //test
							 
							 if (actionSpell > 2 | actionSpell < 0) {
								 System.out.println(invalidInput); //test
					        	 actionSpell = input.nextInt(); //test
							 }
					         else {
					        	 if (dice.rollDie(20) > 10) {
					        		 damage = player.castSpell(actionSpell);
					        		 playerSpellDmg = "You cast " + player.spells[actionSpell] + "! Hit for " + damage;
					        		 System.out.println(playerSpellDmg); //test
						        	 enemy.deductHp(damage);
					        	 }
					        	 else {
					        		 System.out.println(playerMissed); //test
					        	 }
					         }
							 break;
							 
					case 3: System.out.println(spellOp1); //test
					 		System.out.println(spellOp2); //test
					 		System.out.println(spellOp3); //test
					 		System.out.println(spellOp4); //test
					 		actionSpell = input.nextInt(); //test
					 		
					 		if (actionSpell > 3 | actionSpell < 0) {
					 			System.out.println(invalidInput); //test
					 			actionSpell = input.nextInt(); //test
					        }
					 		else {
					 			if (dice.rollDie(20) > 10) {
					        		 damage = player.castSpell(actionSpell);
					        		 playerSpellDmg = "You cast " + player.spells[actionSpell] + "! Hit for " + damage; 
					        		 System.out.println(playerSpellDmg); //test
						        	 enemy.deductHp(damage);
					        	 }
					        	 else {
					        		 System.out.println(playerMissed); //test
					        	 }
			                }
					 		break;
					 		
					case 4:  System.out.println(spellOp1); //test
			 				 System.out.println(spellOp2); // |
			 				 System.out.println(spellOp3); // |
			 				 System.out.println(spellOp4); // |
			 				 System.out.println(spellOp5); // V
			 				 actionSpell = input.nextInt(); //test
			 				 
			 				 if (actionSpell > 3 | actionSpell < 0) {
			 					 System.out.println(invalidInput); //test
			 					 actionSpell = input.nextInt(); //test
			 				 }
			 				 else {
			 					if (dice.rollDie(20) > 10) {
					        		 damage = player.castSpell(actionSpell);
					        		 playerSpellDmg = "You cast " + player.spells[actionSpell] + "! Hit for " + damage;
					        		 System.out.println(playerSpellDmg); //test
						        	 enemy.deductHp(damage);
					        	 }
					        	 else {
					        		 System.out.println(playerMissed); //test
					        	 }
			 				 }
			 				 break;
			 				 
					}
					break;
					
			case 2: if (player.hpPotions > 0) {
						player.healHp();
						System.out.println(hpString); //test
						player.useHpPotion();
					}
					else {
						System.out.println(noPotions); //test
					}
					break;
					
			case 3: if (player.mpPotions > 0) {
						player.restoreMp();
						System.out.println(mpString); //test
						player.useMpPotion();
					}
					else {
						System.out.println(noPotions); //test
					}
					break;
			}
			
			if (enemy.currentHp > 0) {
				if (dice.rollDie(20) > 10) {
					damage = enemy.enemyAttack(enemy.enemyName);
					enemyDmg = "The enemy hits you for " + damage;
					System.out.println(enemyDmg); //test
					player.deductHp(damage);
       	 		}
       	 		else {
       	 			System.out.println(enemyMissed); //test
       	 		}
			}
			
		}
		// if loop is broken, check to see who's hp dropped below zero, and then return true or false if the player is still alive
		if (player.currentHp <= 0) {
			won = false;
		}
		else {
			won = true;
		}
		
		return won; 

	} */
	
	
}
