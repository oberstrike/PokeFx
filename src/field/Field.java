package field;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.sun.javafx.geom.Vec2d;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import application.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import logic.Entity;
import logic.Player;
import xml.GameData;

@XStreamAlias("FIELD")
public class Field {

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
<<<<<<< HEAD
		return this.type.equals(FieldType.STEIN)||
				this.type.equals(FieldType.WASSER)||
				this.type.equals(FieldType.MTLF_GRASS)||
				this.type.equals(FieldType.MTLF_SAND)||
				this.type.equals(FieldType.MTLF_WATER)||
				this.type.equals(FieldType.MTRF_GRASS)||
				this.type.equals(FieldType.MTRF_SAND)||
				this.type.equals(FieldType.MTRF_WATER)||
				this.type.equals(FieldType.MTLB_GRASS)||
				this.type.equals(FieldType.MTLB_SAND)||
				this.type.equals(FieldType.MTLB_WATER)||
				this.type.equals(FieldType.MTRB_GRASS)||
				this.type.equals(FieldType.MTRB_SAND)||
				this.type.equals(FieldType.MTRB_WATER)||
				this.type.equals(FieldType.MTIRB_WATER)||
				this.type.equals(FieldType.MTIRF_WATER)||
				this.type.equals(FieldType.MTILB_WATER)||
				this.type.equals(FieldType.MTILF_WATER)||
				this.type.equals(FieldType.CACTUS_BOTTOM)||
				this.type.equals(FieldType.CACTUS_TOP)||
				this.type.equals(FieldType.BAUM_BOTTOM)||
				this.type.equals(FieldType.BAUM_TOP);
=======
		return this.getType().isBlocked();
>>>>>>> eae0be5c315c28424600bc3bd386003e2ae7c5a0
	}
	
	public void applyImage() {
		switch (type) {
		case GRASS:
			setImage(Main.grass);
			break;
		case HOHESGRASS:
			setImage(Main.hohesgrass);
			break;
		case SAND:
			setImage(Main.sand);
			break;
		case TIEFERSAND:
			setImage(Main.tiefersand);
			break;
		case STEIN:
			setImage(Main.stein);
			break;
		case WASSER:
			setImage(Main.wasser);
			break;
		case UEBERGANG:
			setImage(Main.uebergang);
			break;
		case CACTUS_BOTTOM:
			setImage(Main.cactus_bottom);
			break;
		case MTLB_GRASS:
			setImage(Main.mtlb_grass);
			break;
		case MTLF_GRASS:
			setImage(Main.mtlf_grass);
			break;
		case MTLF_SAND:
			setImage(Main.mtlf_sand);
			break;
		case MTLB_SAND:
			setImage(Main.mtlb_sand);
			break;
		case MTRB_GRASS:
			setImage(Main.mtrb_grass);
			break;
		case MTRF_GRASS:
			setImage(Main.mtrf_grass);
			break;
		case MTRF_SAND:
			setImage(Main.mtrf_sand);
			break;
		case MTRB_SAND:
			setImage(Main.mtrb_sand);
			break;
		case MTLF_WATER:
			setImage(Main.mtlf_water);
			break;
		case MTLB_WATER:
			setImage(Main.mtlb_water);
			break;
		case MTRF_WATER:
			setImage(Main.mtrf_water);
			break;
		case MTRB_WATER:
			setImage(Main.mtrb_water);
			break;
		case MTR_SAND:
			setImage(Main.mtr_sand);
			break;
		case MTL_SAND:
			setImage(Main.mtl_sand);
			break;
		case MTB_SAND:
			setImage(Main.mtb_sand);
			break;
		case MTF_SAND:
			setImage(Main.mtf_sand);
			break;
		default:
			setImage(Main.grass);
			break;
		}	
		if (entity != null) {
			BufferedImage image = SwingFXUtils.fromFXImage(getImage(), null);
			Image eImage = entity.getImage();
			if(entity.getClass().equals(Player.class)) {
				if(eImage==null) {
					eImage = Main.player_straight;
				}
			}
			
			BufferedImage entitysImage = SwingFXUtils.fromFXImage(eImage, null);
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
