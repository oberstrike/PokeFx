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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import views.MapView;
import xml.GameData;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.GenericBuilder;
import pokemon.Pokemon;
import tcp.TcpServer;
import tcp.TcpServer.Handler;

public class MenuGuiController implements Initializable {

	@FXML
	private Button loadBtn;
	
	@FXML
	void loadGame(ActionEvent event) {
		File selectedFile = new File("Spielstand.xml");
		if(selectedFile.exists()) {
			Main.gameData = Main.xmlControll.getGame(selectedFile);
			System.out.println(Main.gameData.getPlayer());
			Main.allowViewer = this.viewerOptn.isSelected();
			Main.changer.changeWindow("/guis/GameGui.fxml");
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Kein Spielstand vorhanden");
			alert.show();
			this.loadBtn.setDisable(true);
		}
	}

	@FXML
	void createGame(ActionEvent event) {
		File selectedFile = new File("maps/start.xml");
		MapView mapView = new MapView();
		mapView.setMap(Main.xmlControll.getMap(selectedFile));
		Main.gameData = new GameData(mapView.getMap());
		Main.allowViewer = this.viewerOptn.isSelected();
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

	@FXML
	private RadioButton viewerOptn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Main.gameData == null)
			Main.gameData = new GameData();

	}

}
