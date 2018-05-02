package logic;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameLogic extends Thread{
	List<Field> fields;
	GraphicsContext context;
	
	public GameLogic(GraphicsContext context, List<Field> fields) {
		this.fields = fields;
		this.context = context;
	}
	
	@Override
	public void run() {
		Platform.runLater(() -> {
			this.reset();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Fülle");
			this.fill();
		});
	}
	
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
	
	private void reset() {
		context.clearRect(0, 0, 400, 400);
	}
	
	
}
