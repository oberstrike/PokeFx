package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.GenericBuilder;
import pokemon.Pokemon;

public class FightGuiController implements Initializable, Controller {

	private Pokemon myPokemon;

	private Pokemon enemyPokemon;

	public void setMyPokemon(Pokemon pokemon) {
		this.myPokemon = pokemon;
	}

	public void setEnemyPokemon(Pokemon pokemon) {
		this.enemyPokemon = pokemon;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (myPokemon != null) {
			myPokemonView.setImage(myPokemon.getBackImage());
			myPokemonNameLabel.setText(myPokemon.getName());
			myPokemonLevelLabel.setText("Lvl." + myPokemon.getLevel());
			myPokemonHealthBar.setProgress((double) myPokemon.getHp() / (double) myPokemon.calculateHp());
			myPokemonHpLabel.setText((int) myPokemon.getHp() + "/" + myPokemon.calculateHp());
			wsidLabel.setText("Was soll " + myPokemon.getName() + " tun?");
		}

		if (enemyPokemon != null) {
			enemyPokemonView.setImage(enemyPokemon.getFrontImage());
			enemyPokemonNameLabel.setText(enemyPokemon.getName());
			enemyLevelLabel.setText("Lvl." + enemyPokemon.getLevel());
			enemyHealthBar.setProgress((double) enemyPokemon.getHp() / (double) enemyPokemon.calculateHp());
		}
	}
	
	public static FightGuiController create(Pokemon pokemon, Pokemon pokemon2) {
		FightGuiController controller = new GenericBuilder<>(FightGuiController.class)
				.apply((con) -> con.setMyPokemon(pokemon))
				.apply((con) -> con.setEnemyPokemon(pokemon2))
				.build();
		return controller;
	}
	

}
