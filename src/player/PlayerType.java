package player;

import application.Main;
import javafx.scene.image.Image;

public enum PlayerType {

	LEFT(Main.player_left), RIGHT(Main.player_right), BACK(Main.player_back), STRAIGHT(Main.player_straight);

	private final Image image;
	
	public Image getImage() {
		return this.image;
	}
	
	private PlayerType(Image image) {
		this.image = image;
	}
	
	
}
