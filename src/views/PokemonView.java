package views;


import application.Main;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pokemon.Pokemon;

public class PokemonView extends AnchorPane {
	
	
	private Label hp;
	private Label name;
	private Label level;
	private Label xp;
	private ProgressBar hpBar;
	private Pokemon pokemon;
	private ProgressBar xpBar;
	
	public PokemonView(Pokemon pokemon) {
		if(pokemon != null) {
			hp = registerLabel(String.valueOf((int)pokemon.getHp() + "/" + String.valueOf((int)pokemon.calculateHp())) , 0, 60);
			name = registerLabel(pokemon.getName(), 65, 15);
			level = registerLabel(String.valueOf(pokemon.getLevel()), 65, 35);
			xp = registerLabel("XP: " + String.valueOf(pokemon.getXp() + "/" + String.valueOf(pokemon.getXpForNextLevel())), 0, 85);
			hpBar = new ProgressBar();
			hpBar.setProgress(1);
			hpBar.setPrefWidth(140);
			hpBar.setPrefHeight(20);
			hpBar.setLayoutX(80);
			hpBar.setLayoutY(60);
			this.getChildren().add(hpBar);
			
			ImageView picture = new ImageView();
			picture.setLayoutX(15);
			picture.setLayoutY(35);
			picture.setFitWidth(50);
			picture.setFitHeight(50);
			this.pokemon = pokemon;
			
			xpBar = new ProgressBar();
			xpBar.setProgress(0);
			xpBar.setPrefWidth(140);
			xpBar.setPrefHeight(20);
			xpBar.setLayoutX(80);
			xpBar.setLayoutY(85);
			this.getChildren().add(xpBar);
		}
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
			hpBar.setProgress(pokemon.getHp()/pokemon.calculateHp());
			hp.setText("HP: " + String.valueOf((int)pokemon.getHp() + "/" + String.valueOf((int)pokemon.calculateHp())));
			name.setText(pokemon.getName());
			level.setText("Lvl: " + String.valueOf(pokemon.getLevel()));
			xpBar.setProgress((double)pokemon.getXp()/(double)pokemon.getXpForNextLevel());
			
			xp.setText("XP: " + String.valueOf(pokemon.getXp() + "/" + String.valueOf(pokemon.getXpForNextLevel())));
		}catch (Exception e) {
			System.err.println(pokemon);
			e.printStackTrace();
		}

	}
	
	
}
