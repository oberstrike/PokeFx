package controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.glass.events.MouseEvent;

import application.Main;
import field.Field;
import field.FieldType;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import logic.GameLogic;
import views.MapView;
import views.PokemonView;

public class GameGuiController implements Initializable {

	GameLogic logic;
	
	@FXML
	void save(ActionEvent event) {
		
	}
	
	
    @FXML
    private AnchorPane anchor2;
    
    @FXML
    private AnchorPane anchor;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MapView mapView = null;
		
		if(Main.gameData.getMap() == null) {
			mapView = new MapView();
			Main.gameData.setMap(mapView.getMap());
		}else {
			mapView = new MapView();
			mapView.setMap(Main.gameData.getMap());
		}
		
		
		mapView.setPrefWidth(600);
		mapView.setPrefHeight(500);
		anchor.getChildren().add(mapView);
		
		anchor.setFocusTraversable(false);
		anchor.requestFocus();
		
		logic = new GameLogic(mapView, anchor2);
		logic.setDaemon(true);
		logic.start();

		
		mapView.setOnKeyPressed(event -> {
			logic.moveEvent(event.getCode().getName());
		});
		mapView.setFocusTraversable(true);
		mapView.requestFocus();
		
		System.out.println("Startet");
		
	}

}
