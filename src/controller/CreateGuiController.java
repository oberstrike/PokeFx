package controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
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

import application.Main;
import application.WindowChanger;
import field.Field;
import field.FieldType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.Map;
import pokemon.PokemonType;
import javafx.stage.Stage;
import views.MapView;
import xml.XmlControll;

public class CreateGuiController implements Initializable {

	XStream stream;
	String active = "";
	MapView mapView;
	ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

	@FXML
	private TextField name;

	@FXML
	private ListView<PokemonType> pokeTypeList;

	@FXML
	private ComboBox<PokemonType> pokeComboBox;

	@FXML
	private ListView<String> liste;

	@FXML
	private AnchorPane anchor;

	@FXML
	private Button addBtn;
	
    @FXML
    void back(ActionEvent event) {
    	WindowChanger changer = new WindowChanger();
    	changer.changeWindow("/guis/MenuGui.fxml", event);
    }

	@FXML
	void addPokeType(ActionEvent event) {
		ObservableList<PokemonType> listOfPokemonTypes = pokeTypeList.getItems();
		PokemonType pokemonType = pokeComboBox.getValue();
		if (!listOfPokemonTypes.contains(pokemonType)) {
			pokeTypeList.getItems().add(pokemonType);
			mapView.getMap().getPokemonTypes().add(pokemonType);
		}
		if (pokeTypeList.getItems().size() > 2) {
			addBtn.setDisable(true);
		}
	}

	@FXML
	void deletePokeType(ActionEvent event) {
		PokemonType pokemonType = pokeTypeList.getSelectionModel().getSelectedItem();
		if (pokemonType != null) {
			pokeTypeList.getItems().remove(pokemonType);
			mapView.getMap().getPokemonTypes().remove(pokemonType);
			addBtn.setDisable(false);
		}

	}

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
		String selected = liste.getSelectionModel().getSelectedItem();

		if (selected != null) {
			Field field = Field.findFieldNextTo(event.getX(), event.getY(), mapView.getFields());
			field.setType(FieldType.valueOf(selected));
			mapView.update();
		}

	}

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
			try {
				Map map = Main.xmlControll.getMap(selectedFile);
				mapView.setMap(map);
				mapView.update();
				new Alert(AlertType.INFORMATION, "Geladen").show();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@FXML
	private void save(ActionEvent event) {
		FileWriter writer;
		if (this.name.getText().length() < 1 || this.name.getText().length() > 12) {
			new Alert(AlertType.ERROR, "Bitte geben Sie einen Namen ein, der zwischen 4-12 Zeichen besitzt").show();
		} else {
			String pathname = this.name.getText() + ".xml";
			File file = new File(pathname);
			if (file.exists()) {
				Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION, "Wollen Sie die Datei überschreiben?")
						.showAndWait();
				ButtonType type = result.get();
				if (type != null) {
					if (!type.getButtonData().equals(ButtonData.CANCEL_CLOSE)) {
						if (pokeTypeList.getItems().size() == 0) {
							Optional<ButtonType> oButtonType = new Alert(AlertType.CONFIRMATION,
									"Sie haben kein Pokemon Type ausgewählt.").showAndWait();
							if (oButtonType.isPresent()) {
								if (!oButtonType.get().getButtonData().equals(ButtonData.CANCEL_CLOSE)) {
									write(file);
								}
							}
						} else {
							write(file);
						}
					}
				}
			} else {
				if (pokeTypeList.getItems().size() == 0) {
					Optional<ButtonType> oButtonType = new Alert(AlertType.CONFIRMATION,
							"Sie haben kein Pokemon Type ausgewählt.").showAndWait();
					if (oButtonType.isPresent()) {
						if (!oButtonType.get().getButtonData().equals(ButtonData.CANCEL_CLOSE)) {
							write(file);
						}
					}
				} else {
					write(file);
				}
			}
		}
	}

	public void write(File file) {
		FileWriter writer;
		try {
			writer = new FileWriter(file, false);
			Main.xmlControll.saveMap(mapView.getMap(), writer);
			new Alert(AlertType.INFORMATION, "Erfolgreich gespeichert!").show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void newMap(ActionEvent event) {
		String selected = liste.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Map map = new Map(FieldType.valueOf(selected));
			mapView.setMap(map);
			mapView.update();
		}

	}

	@FXML
	void saveAs(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open XML");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
		if (selectedFile == null) {

		} else {
			if (pokeTypeList.getItems().size() == 0) {
				Optional<ButtonType> oButtonType = new Alert(AlertType.CONFIRMATION,
						"Sie haben kein Pokemon Type ausgewählt.").showAndWait();
				if (oButtonType.isPresent()) {
					if (!oButtonType.get().getButtonData().equals(ButtonData.CANCEL_CLOSE)) {
						write(selectedFile);
					}
				}
			} else {
				write(selectedFile);
			}

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> data = FXCollections.observableArrayList();
		for (FieldType type : FieldType.values()) {
			data.add(type.toString());
		}

		liste.setItems(data);
		mapView = new MapView();
		mapView.setOnMouseClicked(this::setMaterial);
		anchor.getChildren().add(mapView);

		pokeComboBox.getItems().addAll(Arrays.asList(PokemonType.values()));
		pokeComboBox.getSelectionModel().select(11);
	}

}
