package views;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pokemon.Pokemon;

public class PokemonView extends AnchorPane {
	
	public PokemonView(Pokemon pokemon) {
		if(pokemon != null) {
			registerLabel(String.valueOf(pokemon.getHp()), 15, 15);
			registerLabel(pokemon.getName(), 65, 35);
			registerLabel(String.valueOf(pokemon.getLevel()), 65, 50);

			ProgressBar hpBar = new ProgressBar();
			hpBar.setProgress(1);
			hpBar.setPrefWidth(140);
			hpBar.setPrefHeight(20);
			hpBar.setLayoutX(45);
			hpBar.setLayoutY(15);
			this.getChildren().add(hpBar);
			
			ImageView picture = new ImageView();
			picture.setLayoutX(15);
			picture.setLayoutY(35);
			picture.setFitWidth(50);
			picture.setFitHeight(50);
		}
	}
	
	
	private void registerLabel(String text, double x, double y) {
		Label label = new Label(text);
		label.setLayoutX(x);
		label.setLayoutY(y);
		this.getChildren().add(label);
	}
	
	
}
