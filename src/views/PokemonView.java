package views;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import pokemon.Pokemon;

public class PokemonView extends AnchorPane {

	private Label hp;
	private Label name;
	private Label level;
	private Label xp;
	private ProgressBar hpBar;
	private Pokemon pokemon;
	private ProgressBar xpBar;
	private Button upButton;
	private Button downButton;
	private ImageView picture;

	public Button getUpButton() {
		return upButton;
	}

	public void setUpButton(Button upButton) {
		this.upButton = upButton;
	}

	public Button getDownButton() {
		return downButton;
	}

	public void setDownButton(Button downButton) {
		this.downButton = downButton;
	}

	public PokemonView(Pokemon pokemon, boolean isViewer) {
		if (pokemon != null) {
			hp = registerLabel(
					String.valueOf((int) pokemon.getHp() + "/" + String.valueOf((int) pokemon.calculateMaxHp())), 0,
					60);
			name = registerLabel(pokemon.getName(), 65, 15);
			level = registerLabel(String.valueOf(pokemon.getLevel()), 65, 35);
			xp = registerLabel(
					"XP: " + String.valueOf(pokemon.getXp() + "/" + String.valueOf(pokemon.getXpForNextLevel())), 0,
					85);
			if (!isViewer) {
				upButton = new Button("Up");
				upButton.setLayoutY(15);
				upButton.setMinSize(50, 40);
				upButton.setPrefSize(50, 40);
				upButton.setMaxSize(50, 40);
				upButton.setStyle(String.format("-fx-font-size: %dpx", (int) 0.45 * 40));
				this.getChildren().add(upButton);
			}

			hpBar = new ProgressBar();
			hpBar.setProgress(1);
			hpBar.setPrefWidth(140);
			hpBar.setPrefHeight(20);
			hpBar.setLayoutX(80);
			hpBar.setLayoutY(60);
			this.getChildren().add(hpBar);

			String pathToImg = "/pokemon/images/" + pokemon.getId() + ".png";
			picture = new ImageView(getClass().getResource(pathToImg).toExternalForm());
			picture.setLayoutX(0);
			picture.setLayoutY(0);
			picture.setFitWidth(75);
			picture.setFitHeight(75);
			this.getChildren().add(picture);
			this.pokemon = pokemon;

			xpBar = new ProgressBar();
			xpBar.setProgress(0);
			xpBar.setPrefWidth(140);
			xpBar.setPrefHeight(20);
			xpBar.setLayoutX(80);
			xpBar.setLayoutY(85);
			this.getChildren().add(xpBar);
			update();

		}
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Pokemon getPokemon() {
		return this.pokemon;
	}

	private Label registerLabel(String text, double x, double y) {
		Label label = new Label(text);
		label.setLayoutX(x);
		label.setLayoutY(y);
		this.getChildren().add(label);
		return label;
	}

	public void update() {
		try {
			Timeline timeline = new Timeline();
			KeyValue xpBarValue, hpBarValue;

			hpBarValue = new KeyValue(hpBar.progressProperty(),
					(double) pokemon.getHp() / (double) pokemon.calculateMaxHp(), Interpolator.EASE_OUT);
			timeline.getKeyFrames().add(new KeyFrame(new Duration(300), hpBarValue));

			hp.setText("HP: "
					+ String.valueOf((int) pokemon.getHp() + "/" + String.valueOf((int) pokemon.calculateMaxHp())));
			name.setText(pokemon.getName());
			level.setText("Lvl: " + String.valueOf(pokemon.getLevel()));

			xpBarValue = new KeyValue(xpBar.progressProperty(),
					(double) pokemon.getXp() / (double) pokemon.getXpForNextLevel(), Interpolator.EASE_OUT);
			timeline.getKeyFrames().add(new KeyFrame(new Duration(300), xpBarValue));
			timeline.play();

			xp.setText("XP: " + String.valueOf(pokemon.getXp() + "/" + String.valueOf(pokemon.getXpForNextLevel())));
			String pathToImg = "/pokemon/images/" + pokemon.getId() + ".png";
			picture.setImage(new Image(getClass().getResource(pathToImg).toExternalForm()));

			if(upButton != null) {
				Platform.runLater(() -> {
					upButton.setLayoutX(name.getLayoutX() + name.prefWidth(-1) + 20);
				});
			}
		} catch (Exception e) {
			System.err.println(pokemon);
			e.printStackTrace();
		}

	}

}
