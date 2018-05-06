package views;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PokemonView extends AnchorPane {
	
	public PokemonView() {
		registerLabel("Hp", 15, 15);
		registerLabel("Name", 65, 35);
		registerLabel("Level", 65, 50);

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
	
	
	private void registerLabel(String text, double x, double y) {
		Label label = new Label(text);
		label.setLayoutX(x);
		label.setLayoutY(y);
		this.getChildren().add(label);
	}
	
	
}
