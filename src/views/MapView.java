package views;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import field.Field;
import field.FieldType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.Map;

@XStreamAlias("Map")
public class MapView extends AnchorPane {

	
	private Map map;
	

	public MapView(List<Field> fields) {
		map = new Map(fields);
		for(Field field: map.getFields()) {
			ImageView image = new ImageView(field.getImage());
			image.setX(field.getX());
			image.setY(field.getY());
			image.setFitWidth(40);
			image.setFitHeight(40);
			this.getChildren().add(image);
		}
	}
	
	public MapView() {
		this(FieldType.GRASS);
	}
	
	public MapView(FieldType fields) {
		map = new Map(fields);
		
		for(Field field: map.getFields()) {
			ImageView image = new ImageView(field.getImage());
			image.setX(field.getX());
			image.setY(field.getY());
			image.setFitWidth(40);
			image.setFitHeight(40);
			this.getChildren().add(image);
		}
	}

	public void update() {
		this.getChildren().clear();
		map.getFields().stream().forEach(each -> {
			each.applyImage();
			ImageView image = new ImageView(each.getImage());
			image.setX(each.getX());
			image.setY(each.getY());
			image.setFitWidth(40);
			image.setFitHeight(40);
			this.getChildren().add(image);	
		});	
	}

	public List<Field> getFields() {
		return this.map.getFields();
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

}
