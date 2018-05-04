package logic;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
public class GameLogic extends Thread{
	List<Field> fields;
	GraphicsContext context;
	Player player;
	long lastMovement = 0;
	
	public GameLogic(GraphicsContext context, List<Field> fields) {
		this.fields = fields;
		this.context = context;
		player = new Player();
		List<Field> grassFields = fields.stream().filter(each -> each.getType().equals(FieldType.GRASS)).collect(Collectors.toList());
		System.out.println(grassFields.size());
		Field field = grassFields.get(new Random().nextInt(grassFields.size()) - 1);
		System.out.println(field);
		player.setField(field);
	}
	
	public GameLogic(Player player, List<Field> fields) {
		this.player = player;
		this.fields = fields;
	}
	
	@Override
	public void run() {
		while(true) {
			Platform.runLater(() -> this.fill());	
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(() -> this.reset());
		}
	}
	
	private void fill() {
		for(Field field: fields) {
			context.setFill(field.getType().getColor());
			context.fillRect(field.getX(), field.getY(), 20, 20);
			if(field.equals(player.getField())) {
				context.setFill(Color.YELLOW);
				context.fillOval(field.getX(), field.getY(), 15, 15);
			}
		}
		
	}
	
	public void moveEvent(String keyName) {
		double newX = player.getX();
		double newY = player.getY();
		
		switch (keyName) {
			case "W":
				newY -= 20;
				break;
			case "D":
				newX += 20;
				break;
			case "S":
				newY += 20;
				break;
			case "A":
				newX -= 20;
				break;
		default:
			break;
		}
		double x = newX;
		double y = newY;
		Optional<Field> newField = fields.stream().filter(each -> each.getX() == x && each.getY() == y).findFirst();
		if(newField.isPresent()) {
			if(!newField.get().getType().equals(FieldType.BLOCKED)) {
				if(lastMovement == 0 || System.currentTimeMillis() - lastMovement > 120) {
					lastMovement = System.currentTimeMillis();
					player.setX(newX);
					player.setY(newY);
				}
				
			}
		}

	}
	
	private void reset() {
		context.clearRect(0, 0, 400, 400);
	}
	
	
}
