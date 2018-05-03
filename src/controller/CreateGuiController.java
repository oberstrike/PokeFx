package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.Field;
import logic.FieldType;

public class CreateGuiController implements Initializable {

	@FXML
	private Canvas canvas;
	
	List<Field> fields;
	GraphicsContext context;
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
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
		fields = new ArrayList<>();
		context = canvas.getGraphicsContext2D();
		for(int i = 0; i < 600; i+=20) {
			for(int j = 0; j < 500; j+=20) {
				fields.add(new Field(i, j, FieldType.GRASS ));
			}
		}
		
		executor.schedule(()->this.fill(), 200, TimeUnit.MILLISECONDS); 
		
		
	}

}
