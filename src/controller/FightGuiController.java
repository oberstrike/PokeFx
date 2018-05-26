package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.Main;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import logic.GenericBuilder;
import pokemon.Pokemon;

public class FightGuiController implements Initializable {

	private Vector<Pokemon> myPokemons;
	int actMyPokemon = 0;

	private Vector<Pokemon> enemyPokemons;
	int actEnemyPokemon = 0;

	private boolean isTrainerFight = false;

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
		System.out.println(Thread.activeCount());
		Main.changer.changeWindow("/guis/GameGui.fxml");
	}

	@FXML
	void fight(MouseEvent event) {
		block();
		Timeline faster = new Timeline();
		Timeline slower = new Timeline();
		Timeline dead = new Timeline();
		KeyValue keyHpBar, keyText, keyDead;
		KeyFrame frameHpBar, frameText, frameDead;
		String text = "";

		if (actMyPokemon < this.myPokemons.size() && actEnemyPokemon < this.enemyPokemons.size()) {
			Pokemon myPokemon = this.myPokemons.get(actMyPokemon);
			Pokemon enemyPokemon = this.enemyPokemons.get(actEnemyPokemon);
			Pokemon fasterPokemon = myPokemon.faster(enemyPokemon);
			Pokemon slowerPokemon = fasterPokemon.equals(myPokemon) ? enemyPokemon : myPokemon;
			String deadText = "Was soll " + myPokemon.getName() + " tun?";

			slowerPokemon.setHp(slowerPokemon.getHp() - fasterPokemon.getDamage(slowerPokemon));

			// Animation
			if (slowerPokemon.equals(myPokemon)) {
				keyHpBar = new KeyValue(myPokemonHealthBar.progressProperty(),
						(double) myPokemon.getHp() / (double) myPokemon.calculateHp(), Interpolator.EASE_OUT);
				text = "Das gegnerische " + enemyPokemon.getName() + " greift an!";
			} else {
				keyHpBar = new KeyValue(enemyHealthBar.progressProperty(),
						(double) enemyPokemon.getHp() / (double) enemyPokemon.calculateHp(), Interpolator.EASE_OUT);
				text = myPokemon.getName() + " greift an!";
			}
			keyText = new KeyValue(wsidLabel.textProperty(), text);
			faster.getKeyFrames().add(new KeyFrame(new Duration(100), keyText));
			faster.getKeyFrames().add(new KeyFrame(new Duration(400), keyHpBar));

			if (slowerPokemon.isDead()) {
				if (slowerPokemon.equals(myPokemon)) {
					actMyPokemon++;
					deadText = myPokemon.getName() + "wurde besiegt";
				} else {
					actEnemyPokemon++;
					deadText = "Das gegnerische " + enemyPokemon.getName() + " wurde besiegt";
					myPokemon.addXp(slowerPokemon.calcXp());
				}
			} else {
				fasterPokemon.setHp(fasterPokemon.getHp() - fasterPokemon.getDamage(slowerPokemon));

				if (fasterPokemon.equals(myPokemon)) {
					keyHpBar = new KeyValue(myPokemonHealthBar.progressProperty(),
							(double) myPokemon.getHp() / (double) myPokemon.calculateHp(), Interpolator.EASE_OUT);
					text = "Das gegnerische " + enemyPokemon.getName() + " greift an!";

				} else {
					keyHpBar = new KeyValue(enemyHealthBar.progressProperty(),
							(double) enemyPokemon.getHp() / (double) enemyPokemon.calculateHp(), Interpolator.EASE_OUT);
					text = myPokemon.getName() + " greift an!";
				}
				keyText = new KeyValue(wsidLabel.textProperty(), text);
				slower.getKeyFrames().add(new KeyFrame(new Duration(100), keyText));
				slower.getKeyFrames().add(new KeyFrame(new Duration(400), keyHpBar));

				if (fasterPokemon.isDead()) {
					if (fasterPokemon.equals(myPokemon)) {
						actMyPokemon++;
						deadText = myPokemon.getName() + " wurde besiegt";
					} else {
						actEnemyPokemon++;
						deadText = "Das gegnerische " + enemyPokemon.getName() + " wurde besiegt";
						myPokemon.addXp(slowerPokemon.calcXp());
					}
				}
			}
			keyDead = new KeyValue(wsidLabel.textProperty(), deadText);
			dead.getKeyFrames().add(new KeyFrame(new Duration(400), keyText));
			PauseTransition pause = new PauseTransition(Duration.millis(2000));
			PauseTransition pause2 = new PauseTransition(Duration.millis(2000));
			PauseTransition pause3 = new PauseTransition(Duration.millis(4000));
			SequentialTransition sequence = new SequentialTransition(faster, pause, slower, pause2, dead, pause3);
			sequence.play();
		} else {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			swapBack();
		}
	}

	@FXML
	void escape(MouseEvent event) {
		swapBack();
	}
	
	@FXML
	private Label fightLabel;
	
	void block() {
		Thread thread = new Thread(() ->{
			try {
				
				Platform.runLater(() ->{
					this.catchLabel.setDisable(true);
					this.escapeLabel.setDisable(true);
					this.fightLabel.setDisable(true);
				}); 
				Thread.sleep(1000);
				
				Platform.runLater(() ->{
					this.catchLabel.setDisable(false);
					this.escapeLabel.setDisable(false);
					this.fightLabel.setDisable(false);
				}); 
				
			}catch (Exception e) {
				e.printStackTrace();
			}
	
			
		});
		thread.setDaemon(true);
		thread.start();
		
	}

	@FXML
	void catchEvent(MouseEvent event) {
		//Blockieren der Auswahl
		block();
		
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
				myPokemon.setHp(enemyPokemon.getDamage(myPokemon));
			}
		}
	}

	@FXML
	private Label catchLabel;

	@FXML
	private Label escapeLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		executor = Executors.newScheduledThreadPool(1);
		Main.routeMediaPlayer.stop();
		Main.fightMediaPlayer.setVolume(0.6);
		Main.fightMediaPlayer.play();

		if (isTrainerFight) {
			this.catchLabel.setDisable(true);
			this.escapeLabel.setDisable(true);
		}
		executor.scheduleAtFixedRate(() -> {
			Platform.runLater(() -> {
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

					myPokemonHealthBar.setProgress((double) myPokemon.getHp() / (double) myPokemon.calculateHp());

					myPokemonHpLabel.setText((int) myPokemon.getHp() + "/" + myPokemon.calculateHp());
					if (wsidLabel.getText() == "") {
						wsidLabel.setText("Was soll " + myPokemon.getName() + " tun?");
					}
				}
				
				

				if (enemyPokemon != null) {
					enemyPokemonView.setImage(enemyPokemon.getFrontImage());
					enemyPokemonNameLabel.setText(enemyPokemon.getName());
					enemyLevelLabel.setText("Lvl." + enemyPokemon.getLevel());

					enemyHealthBar.setProgress((double) enemyPokemon.getHp() / (double) enemyPokemon.calculateHp());

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
