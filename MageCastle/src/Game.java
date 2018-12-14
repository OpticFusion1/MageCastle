
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {
	
	// declare scenes beforehand
	Scene introScene, creationScene, battleScene, battleScene2, gameOverScene, winScene;
	
	
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
		
		Label playerNameLbl = new Label();
		
		// button that switches scenes
		Button continueBtn2 = new Button("Continue");
		continueBtn2.setOnAction(e -> {
			playerNameLbl.setText(nameHeroTxt.getText());
			window.setScene(battleScene);
		});
		
		Player player = new Player();
		
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
		Button spellBtn1 = new Button("Fireball");
		Button spellBtn2 = new Button("Lightning");
		Button spellBtn3 = new Button("Ice Spike");
		Button spellBtn4 = new Button("Stone Fist");
		Button spellBtn5 = new Button("Judgement");
		
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
		
		// pressing spell button should take player to an alternate scene that lists the spells available
		spellBtn.setOnAction(e -> {
			window.setScene(battleScene2);
		});
			
			
			
			// listeners for each spell
			spellBtn1.setOnAction(e -> {
				 window.setScene(battleScene);
				 if (dice.rollDie(20) > 10) {
	        		 int damage = player.castSpell(0);
	        		 playerTextLbl.setText("A roaring ball of flame bursts from your hand! Hit for " + (damage - skeleton.defense)); 
	        		 skeleton.deductHp(damage);	
	 				 skeletonStatusLbl.setText(skeleton.enemyName + " HP: " + skeleton.currentHp);
	        	 }
				 else {
						playerTextLbl.setText("All you manage to do is make a small flame in the palm of your hand.");
				 }
				 
				 mpLbl.setText("MP: " + player.currentMp);
				 
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
			
			spellBtn2.setOnAction(e -> {
				 window.setScene(battleScene);
				 if (dice.rollDie(20) > 10) {
	        		 int damage = player.castSpell(1);
	        		 playerTextLbl.setText("Searing lightning strikes your foe with a loud crack! Hit for " + (damage - skeleton.defense)); 
	        		 skeleton.deductHp(damage);	
	 				 skeletonStatusLbl.setText(skeleton.enemyName + " HP: " + skeleton.currentHp);
	        	 }
				 else {
						playerTextLbl.setText("You manage to make a lot of noise, but your spell fails! ");
				 }
				 
				 mpLbl.setText("MP: " + player.currentMp);
				 
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
			
			spellBtn3.setOnAction(e -> {
				 window.setScene(battleScene);
				 if (dice.rollDie(20) > 10) {
	        		 int damage = player.castSpell(2);
	        		 playerTextLbl.setText("Vicious blades of ice burst from the ground beneath the enemy! Hit for " + (damage - skeleton.defense)); 
	        		 skeleton.deductHp(damage);	
	 				 skeletonStatusLbl.setText(skeleton.enemyName + " HP: " + skeleton.currentHp);
	        	 }
				 else {
						playerTextLbl.setText("Try as you might, the spell fizzles between your fingertips!");
				 }
				 
				 mpLbl.setText("MP: " + player.currentMp);
				 
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
			
			spellBtn4.setOnAction(e -> {
				 window.setScene(battleScene);
				 if (dice.rollDie(20) > 10) {
	        		 int damage = player.castSpell(3);
	        		 playerTextLbl.setText("The earth rises up to encase your hand as you strike the enemy with your fist! Hit for " + (damage - skeleton.defense)); 
	        		 skeleton.deductHp(damage);	
	 				 skeletonStatusLbl.setText(skeleton.enemyName + " HP: " + skeleton.currentHp);
	        	 }
				 else {
						playerTextLbl.setText("You try to make the stones rise, but your magic fails you! ");
				 }
				 
				 mpLbl.setText("MP: " + player.currentMp);
				 
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
			
			spellBtn5.setOnAction(e -> {
				 window.setScene(battleScene);
				 if (dice.rollDie(20) > 10) {
	        		 int damage = player.castSpell(4);
	        		 playerTextLbl.setText("A beam of pure energy bursts forth from the heavens and rains upon your foe! Hit for " + (damage - skeleton.defense)); 
	        		 skeleton.deductHp(damage);	
	 				 skeletonStatusLbl.setText(skeleton.enemyName + " HP: " + skeleton.currentHp);
	        	 }
				 else {
						playerTextLbl.setText("Through impossible odds, the enemy managed to dodge your unfathomable wrath!");
				 }
				 
				 mpLbl.setText("MP: " + player.currentMp);
				 
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
		
		// potion button listeners
		hpPotBtn.setOnAction(e -> {
			if (player.hpPotions > 0 && player.currentHp < player.maxHp) {
				player.healHp();
				playerTextLbl.setText("You down a health potion and recover 25 HP.");
				hpLbl.setText("HP: " + player.currentHp);
				player.useHpPotion();
				hpPotionsLbl.setText("HP Potions: " + player.hpPotions);
				
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
					
					// set scene to game over scene if Player HP drops to 0
					if (player.currentHp <= 0) {
						window.setScene(gameOverScene);
					}
			}
			else {
				playerTextLbl.setText("You do not have anymore health potions, or you are at maximum HP.");
			}
		});
		
		mpPotBtn.setOnAction(e -> {
			if (player.mpPotions > 0 && player.currentMp < player.maxMp) {
				player.restoreMp();
				playerTextLbl.setText("You down a mana potion and recover 25 MP.");
				mpLbl.setText("MP: " + player.currentMp);
				player.useMpPotion();
				mpPotionsLbl.setText("MP Potions: " + player.mpPotions);
				
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
					
					// set scene to game over scene if Player HP drops to 0
					if (player.currentHp <= 0) {
						window.setScene(gameOverScene);
					}
			}
			else {
				playerTextLbl.setText("You do not have anymore mana potions, or you area at maximum MP.");
			}
		});
		
		// declare BorderPane for pane 3
		BorderPane pane3 = new BorderPane();
		
		// add nodes to boxes
		HBox playerStatus = new HBox(20);
		playerStatus.setAlignment(Pos.BASELINE_CENTER);
		playerStatus.setSpacing(10);
		playerStatus.getChildren().addAll(playerNameLbl, hpLbl, mpLbl, hpPotionsLbl, mpPotionsLbl);
		
		HBox playerActions = new HBox(20);
		playerActions.setAlignment(Pos.BASELINE_CENTER);
		playerActions.setSpacing(10);
		playerActions.getChildren().addAll(attackBtn, spellBtn, hpPotBtn, mpPotBtn);
		
		HBox spellActions =  new HBox(20);
		spellActions.setAlignment(Pos.BASELINE_CENTER);
		spellActions.setSpacing(10);
		spellActions.getChildren().addAll(spellBtn1, spellBtn2, spellBtn3, spellBtn4, spellBtn5);
		
		VBox battleText = new VBox(20);
		battleText.setAlignment(Pos.BASELINE_CENTER);
		battleText.getChildren().addAll(battleTextLbl, playerTextLbl, enemyTextLbl, skeletonStatusLbl);
		
		// add boxes to pane 3
		pane3.setTop(playerStatus);
		pane3.setCenter(battleText);
		pane3.setBottom(playerActions);
		
		// add pane 3 to battle scene
		battleScene = new Scene(pane3, 500, 200); 
		
		// label for game over scene
		Label gameOverLbl = new Label("You have died. Exit the dungeon.");
		
		// declare pane 4, and add label to it
		VBox pane4 = new VBox(20);
		pane4.setAlignment(Pos.BASELINE_CENTER);
		pane4.getChildren().add(gameOverLbl);
		
		// add game over scene to pane 4
		gameOverScene = new Scene(pane4, 500, 200);
		
		// label for win scene
		Label wonBattleLbl = new Label("You have won the battle!");
		
		// add a button that will continue to the next battle
		Button nextBattleBtn = new Button("Continue"); // add listener code
		
		// declare pane 5, add label and button to it
		VBox pane5 = new VBox(20);
		pane5.setAlignment(Pos.BASELINE_CENTER);
		pane5.getChildren().addAll(wonBattleLbl, nextBattleBtn);
		
		// add pane 5 to win scene
		winScene = new Scene(pane5, 500, 200);
		
		BorderPane pane6 = new BorderPane();
		
		pane6.setBottom(spellActions);
		
		battleScene2 = new Scene(pane6, 500, 200);
		
		// set window title, starting scene, and then show the window
		window.setTitle(gameTitle);
		window.setScene(introScene);
		window.show();
		
		
	}
	public static void main(String[] args) {
		
		launch(args);
	} 
	
	
}
