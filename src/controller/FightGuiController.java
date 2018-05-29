package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Node;

import application.Main;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import logic.GenericBuilder;
import pokemon.Pokemon;

public class FightGuiController implements Initializable {

	private Vector<Pokemon> myPokemons;
	int actMyPokemon = 0;

	private Vector<Pokemon> enemyPokemons;
	int actEnemyPokemon = 0;

	private boolean isTrainerFight = false;
	private boolean busy = false;

	private ScheduledExecutorService executor;

	public void setMyPokemons(Vector<Pokemon> pokemons) {
		this.myPokemons = pokemons;
	}

	public void setEnemyPokemons(Vector<Pokemon> pokemons) {
		this.enemyPokemons = pokemons;
	}

	public void setIsTrainerFight(boolean isTrainerFight) {
		this.isTrainerFight = isTrainerFight;
	}

	@FXML
	private ImageView enemyPokemonView;

	@FXML
	private ImageView myPokemonView;

	@FXML
	private Label myPokemonNameLabel;

	@FXML
	private ProgressBar myPokemonHealthBar;

	@FXML
	private Label myPokemonLevelLabel;

	@FXML
	private Label myPokemonHpLabel;

	@FXML
	private ProgressBar enemyHealthBar;

	@FXML
	private Label enemyPokemonNameLabel;

	@FXML
	private Label enemyLevelLabel;

	@FXML
	private Label wsidLabel;

	private void swapBack() {
		Main.fightMediaPlayer.stop();
		executor.shutdownNow();
		Main.changer.changeWindow("/guis/GameGui.fxml");
	}

	@FXML
	void fight(MouseEvent event) {
		block(3000);
		busy = true;
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				busy = false;
			}
		}, 3500);
		
		Timeline firstHp = new Timeline();
		Timeline secondHp = new Timeline();
		Timeline firstText = new Timeline();
		Timeline secondText = new Timeline();
		Timeline dead = new Timeline();
		KeyValue keyHpFirst, keyHpSecond;
		String text = "";

		if (actMyPokemon < this.myPokemons.size() && actEnemyPokemon < this.enemyPokemons.size()) {
			Pokemon myPokemon = this.myPokemons.get(actMyPokemon);
			Pokemon enemyPokemon = this.enemyPokemons.get(actEnemyPokemon);
			Pokemon fasterPokemon = myPokemon.faster(enemyPokemon);
			Pokemon slowerPokemon = fasterPokemon.equals(myPokemon) ? enemyPokemon : myPokemon;
			String deadText = "Was soll " + myPokemon.getName() + " tun?";

			// Schadensberechnung
			slowerPokemon.setHp(slowerPokemon.getHp() - fasterPokemon.getDamage(slowerPokemon));
			
			text = slowerPokemon.equals(myPokemon) ? "Das gegnerische " + enemyPokemon.getName() + " greift an!" : myPokemon.getName() + " greift an!";			
			firstText.getKeyFrames().add(new KeyFrame(new Duration(500), new KeyValue(wsidLabel.textProperty(), text)));
			
			// Todesnachrichten
			if (slowerPokemon.isDead()) {
				if (slowerPokemon.equals(myPokemon)) {
					actMyPokemon++;
					deadText = myPokemon.getName() + " wurde besiegt";
				} else {
					actEnemyPokemon++;
					deadText = "Das gegnerische " + enemyPokemon.getName() + " wurde besiegt";
					System.out.println("Das gegnerische " + enemyPokemon.getName() + " wurde besiegt");
					myPokemon.addXp(enemyPokemon.calcXp());
				}
			} else {
				
				// Schadensberechnung
				fasterPokemon.setHp(fasterPokemon.getHp() - slowerPokemon.getDamage(fasterPokemon));
				
				text = fasterPokemon.equals(myPokemon) ? "Das gegnerische " + enemyPokemon.getName() + " greift an!" : myPokemon.getName() + " greift an!";
				secondText.getKeyFrames().add(new KeyFrame(new Duration(500), new KeyValue(wsidLabel.textProperty(), text)));

				// Todesnachrichten die Zweite
				if (fasterPokemon.isDead()) {
					if (fasterPokemon.equals(myPokemon)) {
						actMyPokemon++;
						deadText = myPokemon.getName() + " wurde besiegt";
					} else {
						actEnemyPokemon++;
						deadText = "Das gegnerische " + enemyPokemon.getName() + " wurde besiegt";
						System.out.println("Enemy = faster; Das gegnerische " + enemyPokemon.getName() + " wurde besiegt");
						myPokemon.addXp(enemyPokemon.calcXp());
					}
				}
			}

			
			// Animationen vorbereiten
			if (slowerPokemon.equals(myPokemon)) {
				keyHpFirst = new KeyValue(myPokemonHealthBar.progressProperty(), (double) slowerPokemon.getHp() / (double) slowerPokemon.calculateHp(), Interpolator.EASE_OUT);
				keyHpSecond = new KeyValue(enemyHealthBar.progressProperty(), (double) fasterPokemon.getHp() / (double) fasterPokemon.calculateHp(), Interpolator.EASE_OUT);
			} else {
				keyHpFirst = new KeyValue(enemyHealthBar.progressProperty(), (double) slowerPokemon.getHp() / (double) slowerPokemon.calculateHp(), Interpolator.EASE_OUT);
				keyHpSecond = new KeyValue(myPokemonHealthBar.progressProperty(), (double) fasterPokemon.getHp() / (double) fasterPokemon.calculateHp(), Interpolator.EASE_OUT);
			}
		
			firstHp.getKeyFrames().add(new KeyFrame(new Duration(500), keyHpFirst));
			secondHp.getKeyFrames().add(new KeyFrame(new Duration(500), keyHpSecond));
			dead.getKeyFrames().add(new KeyFrame(new Duration(1000), new KeyValue(wsidLabel.textProperty(), deadText)));
			
			// Bewegung eigenes Pokemon
			Timeline myMoveBack = new Timeline();
			Timeline myMove = new Timeline();
			if (!myPokemon.isDead()) {
				myMove = movement(myPokemonView, myPokemon, 30, -30);
				myMoveBack = movement(myPokemonView, myPokemon, 0, 0);
			}
			
			// Bewegung gegnerisches Pokemon
			Timeline enemyMove = new Timeline();
			Timeline enemyMoveBack = new Timeline();
			if (!enemyPokemon.isDead()) {
				enemyMove = movement(enemyPokemonView, enemyPokemon, -30, 30);
				enemyMoveBack = movement(enemyPokemonView, enemyPokemon, 0, 0);
			}
					
			// Sequenz abspielen
			if (slowerPokemon.equals(myPokemon)) {
				SequentialTransition sequence = new SequentialTransition(firstText, enemyMove, enemyMoveBack, firstHp, secondText, myMove, myMoveBack, secondHp, dead);
				sequence.play();
			} else {
				SequentialTransition sequence = new SequentialTransition(firstText, myMove, myMoveBack, firstHp, secondText, enemyMove, enemyMoveBack, secondHp, dead);
				sequence.play();
			}
			
		} else {
			swapBack();
		}
	}

	@FXML
	void escape(MouseEvent event) {
		swapBack();
	}
	
	@FXML
	private Label fightLabel;
	
	void block(int duration) {
		Thread thread = new Thread(() ->{
			try {				
				Platform.runLater(() ->{
					this.catchLabel.setDisable(true);
					this.escapeLabel.setDisable(true);
					this.fightLabel.setDisable(true);
				}); 
				Thread.sleep(duration);
				
				Platform.runLater(() ->{
					this.catchLabel.setDisable(false);
					this.escapeLabel.setDisable(false);
					this.fightLabel.setDisable(false);
				}); 
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
		thread.start();
	}

	@FXML
	void catchEvent(MouseEvent event) {
		//Blockieren der Auswahl
		block(3000);
		busy = true;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				busy = false;
			}
		}, 3000);
		
		Pokemon enemyPokemon = this.enemyPokemons.get(actEnemyPokemon);
		enemyPokemon.setXp(0);
		if (this.myPokemons != null) {
			int randInt = new Random().nextInt(100) + 1;
			int pokeInt = (int) (20 + (int) (enemyPokemon.getSpawn() * 2) + 3 / (enemyPokemon.getHpRealtion()));
			if (randInt <= pokeInt) {
				this.myPokemons.add(enemyPokemon);
				swapBack();
			} else {
				Pokemon myPokemon = myPokemons.get(actMyPokemon);
				
				Timeline text1 = new Timeline();
				Timeline text2 = new Timeline();
				text1.getKeyFrames().add(new KeyFrame(new Duration(1000), new KeyValue(wsidLabel.textProperty(), "Du hast den Pokeball daneben geworfen!")));
				text2.getKeyFrames().add(new KeyFrame(new Duration(500), new KeyValue(wsidLabel.textProperty(), "Das gegnerische " + enemyPokemon.getName() + " greift an!")));
				
				Timeline enemyMove = movement(enemyPokemonView, enemyPokemon, -30, 30);
				Timeline enemyMoveBack = movement(enemyPokemonView, enemyPokemon, 0, 0);
								
				myPokemon.setHp(myPokemon.getHp() - enemyPokemon.getDamage(myPokemon));
				KeyValue keyHpFirst = new KeyValue(myPokemonHealthBar.progressProperty(), (double) myPokemon.getHp() / (double) myPokemon.calculateHp(), Interpolator.EASE_OUT);
				Timeline firstHp = new Timeline();
				firstHp.getKeyFrames().add(new KeyFrame(new Duration(500), keyHpFirst));
				
				Timeline deadText = new Timeline();
				if (myPokemon.isDead()) {
					deadText.getKeyFrames().add(new KeyFrame(new Duration(1000), new KeyValue(wsidLabel.textProperty(), myPokemon.getName() + " wurde besiegt!")));
					actMyPokemon++;				
				} else {
					deadText.getKeyFrames().add(new KeyFrame(new Duration(1000), new KeyValue(wsidLabel.textProperty(), "Was soll " + myPokemon.getName() + " tun?")));
				}
								
				SequentialTransition sequence = new SequentialTransition(text1, text2, enemyMove, enemyMoveBack, firstHp, deadText);
				sequence.play();
			}
		}
	}
	
	public Timeline movement(ImageView view, Pokemon mon, double x, double y) {
		Timeline move = new Timeline();
		move.getKeyFrames().add(new KeyFrame(new Duration(200), new KeyValue(view.layoutXProperty(), view.getLayoutX() + x, Interpolator.EASE_OUT)));
		move.getKeyFrames().add(new KeyFrame(new Duration(200), new KeyValue(view.layoutYProperty(), view.getLayoutY() + y, Interpolator.EASE_OUT)));
		return move;
	}

	@FXML
	private Label catchLabel;


	@FXML
	private Label escapeLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		executor = Executors.newScheduledThreadPool(1);
		if(!Main.routeMediaPlayer.isMute()) {
			Main.routeMediaPlayer.stop();
			Main.fightMediaPlayer.setVolume(0.6);
			Main.fightMediaPlayer.play();
		}
			


		if (isTrainerFight) {
			this.catchLabel.setDisable(true);
			this.escapeLabel.setDisable(true);
		}
		executor.scheduleAtFixedRate(() -> {
			Platform.runLater(() -> {
				if (busy == false) {
					Pokemon myPokemon = null;
					Pokemon enemyPokemon = null;
					try {
						myPokemon = this.myPokemons.get(actMyPokemon);
						enemyPokemon = this.enemyPokemons.get(actEnemyPokemon);
					} catch (Exception e) {
						swapBack();
					}
					if (myPokemon != null) {
						myPokemonView.setImage(myPokemon.getBackImage());					
						myPokemonNameLabel.setText(myPokemon.getName());
						myPokemonLevelLabel.setText("Lvl." + myPokemon.getLevel());
	
						if (myPokemonHealthBar.getProgress() <= (double) (myPokemon.getHp() / myPokemon.calculateHp())) {
							myPokemonHealthBar.setProgress((double) myPokemon.getHp() / (double) myPokemon.calculateHp());
						}
	
						myPokemonHpLabel.setText((int) myPokemon.getHp() + "/" + myPokemon.calculateHp());
						wsidLabel.setText("Was soll " + myPokemon.getName() + " tun?");
					}
	
					if (enemyPokemon != null) {
						enemyPokemonView.setImage(enemyPokemon.getFrontImage());
						enemyPokemonNameLabel.setText(enemyPokemon.getName());
						enemyLevelLabel.setText("Lvl." + enemyPokemon.getLevel());
	
						if (myPokemonHealthBar.getProgress() <= (double) (myPokemon.getHp() / myPokemon.calculateHp())) {
							enemyHealthBar.setProgress((double) enemyPokemon.getHp() / (double) enemyPokemon.calculateHp());
						}
	
					}
				}

			});

		}, 0, 200, TimeUnit.MILLISECONDS);
	}

	public static FightGuiController create(Vector<Pokemon> pokemon, Vector<Pokemon> pokemon2, boolean isTrainerFight) {
		FightGuiController controller = new GenericBuilder<>(FightGuiController.class)
				.apply((con) -> con.setMyPokemons(pokemon)).apply((con) -> con.setEnemyPokemons(pokemon2))
				.apply((con) -> con.setIsTrainerFight(isTrainerFight)).build();
		return controller;
	}

}
