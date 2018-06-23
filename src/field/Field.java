package field;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.javafx.geom.Vec2d;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import application.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import logic.Entity;
import player.Player;

@XStreamAlias("FIELD")
public class Field implements Serializable{

	private static final long serialVersionUID = -2002801737277339736L;

	@Override
	public String toString() {
		return "Field [type=" + type + ", x=" + x + ", y=" + y + ", image=" + image + "]";
	}

	private FieldType type;
	private double x;
	private double y;
	private Entity entity;
	private String nextMap;
	
	@XStreamOmitField
	private Image image;

	public Field() {};

	public Field(double x, double y, FieldType type) {
		this();
		this.setType(type);
		this.setX(x);
		this.setY(y);
		applyImage();
	}
	

	public static Field findFieldNextTo(double x, double y, List<Field> listOfFields) {
    	Vec2d localPoint = new Vec2d(x, y);
    	List<Vec2d> listOfVector = listOfFields.stream().map(Field::toVector).collect(Collectors.toList());	
    	double distance = listOfVector.stream().map(each -> localPoint.distance(each)).sorted().findFirst().get();
    	Vec2d vec = listOfVector.stream().filter(each -> each.distance(localPoint) == distance).findFirst().get(); ;
    	Field field = listOfFields.stream().filter(each -> each.getX() == vec.x-15 && each.getY() == vec.y-15).findFirst().get();
    	return field;
	}
	
	public FieldType getType() {
		return type;
	}

	public void setType(FieldType type) {
		this.type = type;
		applyImage();
	}

	public Vec2d toVector() {
		return new Vec2d(this.x + 15, this.y + 15);
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
	
	public boolean isBlocked() {
		return this.getType().isBlocked() || this.getEntity()!=null;

	}
	
	public void applyImage() {
		this.image = getType().getImage();
		if (entity != null) {
			BufferedImage image = SwingFXUtils.fromFXImage(getImage(), null);
			Image eImage = entity.getImage();
			if(entity.getClass().equals(Player.class)) {
				if(eImage==null) {
					eImage = ((Player)entity).getType().getImage();
				}
			}
			BufferedImage entitysImage = null;
			if(eImage!=null)
				 entitysImage = SwingFXUtils.fromFXImage(eImage, null);
			else
				entitysImage = SwingFXUtils.fromFXImage(Main.man_1_straight,null);
	//		BufferedImage inGrass = SwingFXUtils.fromFXImage(Main.inGrass, null);
			
			
			BufferedImage combined = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
			Graphics g = combined.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.drawImage(entitysImage, 0, 0, null);
//			if(type.equals(FieldType.HOHESGRASS) ) {
//				g.drawImage(inGrass, 0, 0, null);
//			}
//			
			
			Image combinedImage = SwingFXUtils.toFXImage(combined, null);
			setImage(combinedImage);
			
		}

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

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * @return the nextMap
	 */
	public String getNextMap() {
		return nextMap;
	}

	/**
	 * @param nextMap the nextMap to set
	 */
	public void setNextMap(String nextMap) {
		this.nextMap = nextMap;
	}

}
