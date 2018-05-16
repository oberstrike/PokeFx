package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import com.thoughtworks.xstream.XStream;
import application.Main;
import application.WindowChanger;
import field.Field;
import field.FieldType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;
import logic.Map;
import pokemon.Pokemon;
import javafx.stage.Stage;
import views.MapView;
import xml.GameData;

public class CreateGuiController implements Initializable {

	XStream stream;
	String active = "";
	MapView mapView;

	@FXML
	private TextField name;

	@FXML
	private ListView<Pokemon> pokeTypeList;

	@FXML
	private ComboBox<Pokemon> pokeComboBox;

	@FXML
	private TextField pokemonName;

	@FXML
	private ListView<String> liste;

	@FXML
	private AnchorPane anchor;

	@FXML
	private Button addBtn;
	
    @FXML
    void entityMenu(ActionEvent event) {
    	
    	
    }
	

	@FXML
	void back(ActionEvent event) {
		Main.gameData = new GameData(mapView.getMap());
		WindowChanger changer = new WindowChanger();
		changer.changeWindow("/guis/MenuGui.fxml", event);
	}

	@FXML
	void addPokeType(ActionEvent event) {

		ObservableList<Pokemon> listOfpokemons = pokeTypeList.getItems();
		Pokemon pokemon = pokeComboBox.getValue();

		if (!listOfpokemons.contains(pokemon)) {
			addPoke(pokemon);
		}
		if (pokeTypeList.getItems().size() > 20) {
			addBtn.setDisable(true);
		}
	}

	void addPoke(Pokemon pokemon) {
		pokeTypeList.getItems().add(pokemon);
		mapView.getMap().getPokemons().add(pokemon);

	}

	@FXML
	void deletePoke(ActionEvent event) {
		Pokemon pokemon = pokeTypeList.getSelectionModel().getSelectedItem();
		if (pokemon != null) {
			pokeTypeList.getItems().remove(pokemon);
			mapView.getMap().getPokemons().remove(pokemon);
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
			if(field.getType().equals(FieldType.UEBERGANG)) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Map Name");
				dialog.setHeaderText("Fuellen Sie den Map-Namen ein");
				dialog.setContentText("Name: ");
				
				Optional<String> result = dialog.showAndWait();
				if(result.isPresent()) {
					String string = result.get();
					File file = new File("maps/" + string + ".xml");
					System.out.println(file.exists());
					System.out.println(file.getAbsolutePath());
					if(file.exists()) {
						field.setNextMap("maps/" + string + ".xml");
					}
				}
				
				
			}
			
			mapView.update();
		}

	}

	private void loadFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose XML");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(".xml", "*.xml"));

		File selectedFile = fileChooser.showOpenDialog(Main.kprimaryStage);
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
									"Sie haben kein Pokemon ausgewählt.").showAndWait();
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
		fileChooser.setTitle("Choose XML");
		fileChooser.getExtensionFilters().add(new ExtensionFilter(".xml", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog(Main.kprimaryStage);
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
		if (Main.gameData.getMap() == null) {
			mapView = new MapView();
			Main.gameData.setMap(mapView.getMap());
		} else {
			mapView = new MapView();
			mapView.setMap(Main.gameData.getMap());
		}

		mapView.setLayoutY(30);
		mapView.setOnMouseClicked(this::setMaterial);
		anchor.getChildren().add(mapView);

		pokeTypeList.setCellFactory(param -> new ListCell<Pokemon>() {
			@Override
			protected void updateItem(Pokemon item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName());
				}
			}
		});

		pokeComboBox.setConverter(new StringConverter<Pokemon>() {

			@Override
			public String toString(Pokemon object) {
				if (object == null)
					return null;
				else
					return object.getName();
			}

			@Override
			public Pokemon fromString(String string) {
				return null;
			}
		});
		pokeComboBox.getItems().addAll(Main.xmlControll.getPokedex());
		pokeComboBox.getSelectionModel().select(1);

		pokemonName.setOnKeyReleased(event -> {
			Optional<Pokemon> oPokemon = pokeComboBox.getItems().stream()
					.filter(each -> each.getName().toLowerCase().equals(pokemonName.getText().toLowerCase()))
					.findFirst();
			if (!oPokemon.isPresent())
				oPokemon = pokeComboBox.getItems().stream()
						.filter(each -> each.getName().toLowerCase().indexOf(pokemonName.getText().toLowerCase()) != -1)
						.findFirst();
			if (oPokemon.isPresent()) {
				Pokemon pokemon = oPokemon.get();
				if (pokemon != null) {
					pokeComboBox.getSelectionModel().select(pokemon);
				}
			}
		});

		mapView.update();

	}

}
