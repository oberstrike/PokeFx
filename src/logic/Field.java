package logic;

import javafx.scene.image.Image;

public class Field {
	private FieldType type;
	private double x;
	private double y;
	public Field() {};
	private Image image;
	
	public Field(double x, double y, FieldType type){
		this();
		this.setType(type);
		this.setX(x);
		this.setY(y);
	}


	public FieldType getType() {
		return type;
	}


	public void setType(FieldType type) {
		this.type = type;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}



	public Image getImage() {
		return image;
	}



	public void setImage(Image image) {
		this.image = image;
	}
}
