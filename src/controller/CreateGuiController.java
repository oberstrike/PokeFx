package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import logic.Field;
import logic.FieldType;

public class CreateGuiController implements Initializable {
	
	XStream stream;
	
    @FXML
    void load(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "Wollen Sie den aktuellen Zustand verwerfen?");
    	alert.showAndWait().ifPresent((button) ->{
    		if(button.getText().equals("OK")) {
    			load();
    			System.out.println("Neu geladen");
    		}
    	}); 
    	
    }
	
    @SuppressWarnings("unchecked")
	private void load() {
    	FileReader reader;
    	
    	try {
			reader = new FileReader("save.xml");
			System.out.println(stream.fromXML(reader).getClass().getName());
		//	fields = (List<Field>) stream.fromXML(reader);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	
    }
    
	@FXML
	private void save(ActionEvent event) {
		
		
		FileWriter writer;
		try {
			writer = new FileWriter("save.xml", false);
			
			System.out.println("Speichere..");
			stream.toXML(this.fields, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private Canvas grass;

	@FXML
	private Canvas stein;

	@FXML
	private Canvas hohesGrass;
	@FXML
	private Canvas canvas;

	List<Field> fields;
	GraphicsContext context;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	private void fill() {
		for (Field field : fields) {
			if (field.getType().equals(FieldType.GRASS)) {
				context.setFill(Color.GREEN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
			} else {
				context.setFill(Color.BROWN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		stream = new XStream(new StaxDriver());
		stream.addPermission(NoTypePermission.NONE);
		stream.addPermission(NullPermission.NULL);
		stream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		stream.autodetectAnnotations(true);
		stream.allowTypeHierarchy(Collection.class);
		stream.allowTypeHierarchy(Field.class);
		
		GraphicsContext gc = grass.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
		gc.fillRect(0,0,20,20);
		 
		gc = hohesGrass.getGraphicsContext2D();
		gc.setFill(Color.DARKGREEN);
		gc.fillRect(0,0,20,20);
		
		gc = stein.getGraphicsContext2D();
		gc.setFill(Color.BROWN);
		gc.fillRect(0,0,20,20);
		
		fields = new ArrayList<>();
		context = canvas.getGraphicsContext2D();
		for (int i = 0; i < 600; i += 20) {
			for (int j = 0; j < 500; j += 20) {
				fields.add(new Field(i, j, FieldType.GRASS));
			}
		}

		executor.schedule(() -> this.fill(), 200, TimeUnit.MILLISECONDS);

	}

}
