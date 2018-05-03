package controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.sun.javafx.geom.Vec2d;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import logic.Field;
import logic.FieldType;

public class CreateGuiController implements Initializable {

	XStream stream;
	String active = "";

	@FXML
	void load(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Wollen Sie den aktuellen Zustand verwerfen?");
		alert.showAndWait().ifPresent((button) -> {
			if (button.getText().equals("OK")) {
				loadFile(event);
			}
		});
	}
	
    @FXML
    void setMaterial(MouseEvent event) {
    	if(active.length() > 0) {
        	Vec2d localPoint = new Vec2d(event.getX(), event.getY());
        	List<Vec2d> listOfVector = this.fields.stream().map(Field::toVector).collect(Collectors.toList());	
        	double distance = listOfVector.stream().map(each -> localPoint.distance(each)).sorted().findFirst().get();
        	Vec2d vec = listOfVector.stream().filter(each -> each.distance(localPoint) == distance).findFirst().get();
 ;
        	Field field = fields.stream().filter(each -> each.getX() == vec.x-10 && each.getY() == vec.y-10).findFirst().get();
        	field.setType(FieldType.valueOf(active));
        	System.out.println(field.getType());
    	}

   }

	@FXML
	private TextField name;

	@SuppressWarnings("unchecked")
	private void loadFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open XML");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));

		File selectedFile = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
		if (selectedFile == null) {
			System.out.println("Keine Datei geladen");
		} else {
			String name = selectedFile.getName();
			System.out.println(name);
			FileReader reader;
			try {
				reader = new FileReader(name);
				Object object = stream.fromXML(reader);
				fields = (ArrayList<Field>) object;
				reader.close();
				new Alert(AlertType.INFORMATION, "Geladen").show();
			} catch (IOException e) {
				new Alert(AlertType.ERROR, "Wrong File");
			}

		}

	}

	@FXML
	private void save(ActionEvent event) {
		FileWriter writer;
		if (this.name.getText().length() < 1 || this.name.getText().length() > 12) {
			new Alert(AlertType.ERROR, "Bitte geben Sie einen Namen ein, der zwischen 4-12 Zeichen besitzt").show();
		} else {
			try {
				writer = new FileWriter(this.name.getText() + ".xml", false);
				System.out.println("Speichere..");
				stream.toXML(this.fields, writer);
				writer.flush();
				writer.close();
				new Alert(AlertType.INFORMATION, "Erfolgreich gespeichert!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private Label activeMaterial;

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
			} else if(field.getType().equals(FieldType.BLOCKED)) {
				context.setFill(Color.BROWN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
			}else {
				context.setFill(Color.DARKGREEN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
			}
		}
	}

	private EventHandler<MouseEvent> onClickEvent(String name) {
		return (event) -> {
			if (!this.active.equals(name)) {
				active = name;
				activeMaterial.setText(name);
			}
		};
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		stream = new XStream(new StaxDriver());
		stream.addPermission(NoTypePermission.NONE);
		stream.addPermission(NullPermission.NULL);
		stream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		stream.processAnnotations(Field.class);
		stream.allowTypeHierarchy(Collection.class);
		stream.allowTypeHierarchy(Field.class);

		GraphicsContext gc = grass.getGraphicsContext2D();
		grass.setOnMouseClicked(onClickEvent("GRASS"));
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, 20, 20);

		gc = hohesGrass.getGraphicsContext2D();
		hohesGrass.setOnMouseClicked(onClickEvent("HOHESGRASS"));
		gc.setFill(Color.DARKGREEN);
		gc.fillRect(0, 0, 20, 20);

		gc = stein.getGraphicsContext2D();
		stein.setOnMouseClicked(onClickEvent("BLOCKED"));
		gc.setFill(Color.BROWN);
		gc.fillRect(0, 0, 20, 20);

		fields = new ArrayList<>();
		context = canvas.getGraphicsContext2D();
		for (int i = 0; i < 600; i += 20) {
			for (int j = 0; j < 500; j += 20) {
				fields.add(new Field(i, j, FieldType.GRASS));
			}
		}

		executor.scheduleAtFixedRate(() -> this.fill(),0 ,200, TimeUnit.MILLISECONDS);
	}

}
