package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.sun.glass.events.MouseEvent;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import logic.Field;
import logic.FieldType;
import logic.GameLogic;

public class GameGuiController implements Initializable {

	GameLogic logic;
	
	@FXML
	private Canvas canvas;
	
    @FXML
    void move(KeyEvent event) {
    	logic.moveEvent(event.getCode().getName());
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Field> fields = new ArrayList<>();
		Random random = new Random();
		for(int i = 0; i < 600; i+=20) {
			for(int j = 0; j < 500; j+=20) {
				fields.add(new Field(i, j, (random.nextFloat() * 100 - 1) > 10 ? FieldType.GRASS : FieldType.BLOCKED));
			}
		}
		logic = new GameLogic(canvas.getGraphicsContext2D(), fields);
		logic.setDaemon(true);
		logic.start();
		canvas.setOnMouseClicked(event -> {
			canvas.requestFocus();
		});
		
	}

}
