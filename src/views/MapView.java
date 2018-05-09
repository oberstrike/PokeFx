package views;

import java.io.File;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import application.Main;
import field.Field;
import field.FieldType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

@XStreamAlias("Map")
public class MapView extends AnchorPane {

	
	private List<Field> fields;
	private HashMap<Double, String> probabilities;
	
	
	public MapView(List<Field> fields) {
		this();
		for(Field field: fields) {
	//		System.out.println(field.getType());
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
	
	public MapView(FieldType valueOf) {
		fields = new ArrayList<>();
		for(int i = 0; i < 600; i+=40) {
			for(int j = 0; j < 500; j+=40) {
				fields.add(new Field(i,j,valueOf));
			}
		}
		for(Field field: fields) {
			ImageView image = new ImageView(field.getImage());
			image.setX(field.getX());
			image.setY(field.getY());
			image.setFitWidth(40);
			image.setFitHeight(40);
			this.getChildren().add(image);
		}
	}


	public List<Field> getFields() {
		return fields;
	}

	public void setField(List<Field> fields) {
		this.fields = fields;
	}

	public void update() {
		this.getChildren().clear();
		fields.stream().forEach(each -> {
			each.applyImage();
			ImageView image = new ImageView(each.getImage());
			image.setX(each.getX());
			image.setY(each.getY());
			image.setFitWidth(40);
			image.setFitHeight(40);
			this.getChildren().add(image);	
		});
	
		
	}
}
