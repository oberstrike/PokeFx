package logic;

import javafx.scene.paint.Color;

public enum FieldType {
	BLOCKED(Color.BROWN),
	GRASS(Color.GREEN),
	HOHESGRASS(Color.DARKGREEN);

	private final Color color;
	
	private FieldType(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
