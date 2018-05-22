package controller;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;

import application.Main;
import application.WindowChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import views.MapView;
import xml.GameData;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.GenericBuilder;
import pokemon.Pokemon;

public class MenuGuiController implements Initializable {

	@FXML
	void loadGame(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save as xml");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Xml Files", "*.xml"));
		File selectedFile = fileChooser.showOpenDialog((Stage) ((Button) event.getSource()).getScene().getWindow());
		if (selectedFile == null) {

		} else {

			Main.gameData = Main.xmlControll.getGame(selectedFile);
			System.out.println(Main.gameData.getPlayer());
			Main.changer.changeWindow("/guis/GameGui.fxml");
		}
	}

	@FXML
	void load(ActionEvent event) {
		
//		Pokemon pikachu = Main.xmlControll.getPokemonByName("Pikachu");
//		pikachu.setLevel(10);
//		
//		
//		Vector<Pokemon> v1 = new Vector<>(Arrays.asList(Main.xmlControll.getPokemonsById(2), Main.xmlControll.getPokemonsById(4)));
//
//		Vector<Pokemon> v2 = new Vector<>(Arrays.asList(pikachu));
//		
//		FightGuiController controller = FightGuiController.create(v1, v2, false);
//		
//		Main.changer.changeWindow("/guis/FightGui.fxml", (loader) -> loader.setController(controller) ); 
//		
		
		 File selectedFile = new File("maps/start.xml");
		 // File selectedFile = new File("save.xml");
		 MapView mapView = new MapView();
		 mapView.setMap(Main.xmlControll.getMap(selectedFile));
		 Main.gameData = new GameData(mapView.getMap());
		
		 Main.changer.changeWindow("/guis/GameGui.fxml");

	}

	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void create(ActionEvent event) {
		Main.changer.changeWindow("/guis/CreateGui.fxml", "Erstellen einer Karte");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Main.gameData == null)
			Main.gameData = new GameData();
	}

}
