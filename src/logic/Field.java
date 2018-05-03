package logic;

import com.sun.javafx.geom.Vec2d;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javafx.scene.image.Image;

@XStreamAlias("FIELD")
public class Field {
	private FieldType type;
	private double x;
	private double y;
	
	@XStreamOmitField
	private Image image;
	
	public Field() {};
	
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

	public Vec2d toVector() {
		return new Vec2d(this.x+10, this.y+10);
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
}
