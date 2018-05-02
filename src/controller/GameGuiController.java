package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import logic.Field;
import logic.FieldType;

public class GameGuiController implements Initializable {

	List<Field> fields = new ArrayList<>();
	GraphicsContext context;
	
	@FXML
	private Canvas canvas;
	
	
	private void fill() {
		for(Field field: fields) {
			if(field.getType().equals(FieldType.GRASS)) {
				context.setFill(Color.GREEN);
				context.fillRect(field.getX(), field.getY(), 20, 20);

			}else {
				context.setFill(Color.BROWN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
			}
		}
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		context = canvas.getGraphicsContext2D();
		Random random = new Random();
		for(int i = 0; i < 400; i+=20) {
			for(int j = 0; j < 400; j+=20) {
				fields.add(new Field(i, j, (random.nextFloat() * 100 - 1) > 20 ? FieldType.GRASS : FieldType.BLOCKED));
			}
		}
		this.fill();
		
		
		
		
		
	}

}
