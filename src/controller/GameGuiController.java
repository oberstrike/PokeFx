package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import logic.GameLogic;
import views.MapView;

public class GameGuiController implements Initializable {

	private GameLogic logic;
	private ExecutorService executor;
	
	@FXML
	void save(ActionEvent event) {
		String gameFileName = "Spielstand.xml";
		File file = new File(gameFileName);
		System.out.println(file.exists());
		try {
			FileWriter writer = new FileWriter(file);
			Main.xmlControll.saveGameData(Main.gameData, writer);
			System.out.println("Gespeichert");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private AnchorPane anchor2;

	@FXML
	private AnchorPane anchor;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Main.mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/musik/Route1.mp3").toExternalForm()));
		Main.mediaPlayer.setOnEndOfMedia(() -> {
			Main.mediaPlayer.seek(Duration.ZERO);
		});
		Main.mediaPlayer.setVolume(0.6);
		Main.mediaPlayer.play();
		MapView mapView = null;
		executor = Executors.newFixedThreadPool(1);
		
		
		if (Main.gameData.getMap() == null) {
			mapView = new MapView();
			Main.gameData.setMap(mapView.getMap());
		} else {
			mapView = new MapView();
			mapView.setMap(Main.gameData.getMap());
		}

		mapView.setPrefWidth(600);
		mapView.setPrefHeight(500);
		mapView.setLayoutY(37);
		anchor.getChildren().add(mapView);

		anchor.setFocusTraversable(false);
		anchor.requestFocus();

		logic = new GameLogic(mapView, anchor2, Main.gameData, executor);
		logic.setDaemon(true);
		executor.execute(logic);
		mapView.setOnKeyPressed(event -> {
			logic.moveEvent(event.getCode().getName());
		});
		mapView.setFocusTraversable(true);
		mapView.requestFocus();
	}

}
