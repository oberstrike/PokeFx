package logic;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
	XStream stream;
	
	public GameLogic(GraphicsContext context, List<Field> fields) {
		this.fields = fields;
		this.context = context;
		player = new Player();
		player.setField(fields.stream().filter(each -> each.getType().equals(FieldType.GRASS)).findAny().get());
		XStream stream = new XStream(new StaxDriver());
		stream.addPermission(NoTypePermission.NONE);
		
		
		stream.addPermission(NullPermission.NULL);
		stream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		stream.allowTypeHierarchy(Collection.class);
		stream.allowTypeHierarchy(Player.class);
		
		
		stream.processAnnotations(Field.class);
		stream.processAnnotations(Player.class);
		System.out.println(stream.toXML(fields));
		String s  = stream.toXML(player);
		Object obj = stream.fromXML(s);
		System.out.println(obj.getClass().getName());
		
		Player fies = (Player)obj;
		
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
			if(field.getType().equals(FieldType.GRASS)) {
				context.setFill(Color.GREEN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
				if(field.equals(new Field(player.getX(), player.getY(), FieldType.GRASS))) {
					context.setFill(Color.YELLOW);
					context.fillOval(field.getX(), field.getY(), 15, 15);
				}
			}else {
				context.setFill(Color.BROWN);
				context.fillRect(field.getX(), field.getY(), 20, 20);
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
