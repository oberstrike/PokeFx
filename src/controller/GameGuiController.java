package controller;

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
    private AnchorPane anchor2;
    
    @FXML
    private AnchorPane anchor;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(Main.mapView == null) {
			 Main.mapView = new MapView();
		}
		
		Main.mapView.setPrefWidth(600);
		Main.mapView.setPrefHeight(500);
		anchor.getChildren().add(Main.mapView);
		
		anchor.setFocusTraversable(false);
		anchor.requestFocus();
		
		logic = new GameLogic(Main.mapView, anchor2);
		logic.setDaemon(true);
		logic.start();

		
		Main.mapView.setOnKeyPressed(event -> {
			logic.moveEvent(event.getCode().getName());
		});
		Main.mapView.setFocusTraversable(true);
		Main.mapView.requestFocus();
		
		System.out.println("Startet");
		
	}

}
