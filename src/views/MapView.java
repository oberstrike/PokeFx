package views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import field.Field;
import field.FieldType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MapView extends AnchorPane {

	private List<Field> fields;
	private XStream stream;
	
	public void init() {
		stream = new XStream(new StaxDriver());
		stream.addPermission(NoTypePermission.NONE);
		stream.addPermission(NullPermission.NULL);
		stream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		stream.processAnnotations(Field.class);
		stream.allowTypeHierarchy(Collection.class);
		stream.allowTypeHierarchy(Field.class);
		
	}
	

	public MapView(List<Field> fields) {
		this();
		for(Field field: fields) {
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
	
	public MapView(File file) {
		init();
		this.loadFile(file);
		
	}
	
	public MapView(FieldType valueOf) {
		init();
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

	public void loadFile(File file) {
		Object obj = stream.fromXML(file);
		fields = (List<Field>) obj;
		for(Field field: fields) {
			field.applyImage();
		}
		update();
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




	public void save(FileWriter writer) {
		stream.toXML(fields, writer);
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
