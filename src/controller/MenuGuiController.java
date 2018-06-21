package controller;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import views.MapView;
import xml.GameData;
import logic.PokemonClient;

public class MenuGuiController implements Initializable {

	@FXML
	private Button loadBtn;
	
	@FXML
	void loadGame(ActionEvent event) {
		File selectedFile = new File("ressources/Spielstand.xml");
		if(selectedFile.exists()) {
			Main.gameData = Main.xmlControll.getGameData(selectedFile);
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
		File selectedFile = new File("ressources/maps/start.xml");
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
	void view(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("Ip-Adddresse");
		dialog.setTitle("Verbindung");
		dialog.setHeaderText("Bitte geben Sie die Ip-Addresse ein");
		dialog.setContentText("Ip: ");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(ip -> {
			if(ip.length() > 0) {
				Main.client = new PokemonClient(ip, 3333);
				Main.client.start();
				if(Main.client.isConnected())
					Main.changer.changeWindow("/guis/ViewerGui.fxml");
				else {
					new Alert(AlertType.ERROR, "Verbindung konnte nicht hergestellt werden.");
				}
			}
		});
	}

	@FXML
	private RadioButton viewerOptn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Main.gameData == null)
			Main.gameData = new GameData();
	}

}
