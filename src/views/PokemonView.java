package views;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pokemon.Pokemon;

public class PokemonView extends AnchorPane {
	
	
	private Label hp;
	private Label name;
	private Label level;
	
	public PokemonView(Pokemon pokemon) {
		if(pokemon != null) {
			hp = registerLabel(String.valueOf(pokemon.getHp()), 15, 15);
			name = registerLabel(pokemon.getName(), 65, 35);
			level = registerLabel(String.valueOf(pokemon.getLevel()), 65, 50);

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
	
	
	private Label registerLabel(String text, double x, double y) {
		Label label = new Label(text);
		label.setLayoutX(x);
		label.setLayoutY(y);
		this.getChildren().add(label);
		return label;
	}


	public void setPokemon(Pokemon pokemon) {
		hp.setText(String.valueOf(pokemon.getHp()));
		name.setText(pokemon.getName());
		level.setText(String.valueOf(pokemon.getLevel()));
		
	}
	
	
}
