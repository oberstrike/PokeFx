package logic;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import application.Main;
import field.Field;
import field.FieldType;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import views.MapView;

public class GameLogic extends Thread {
	MapView mapView;
	Player player;
	long lastMovement = 0;

	public GameLogic(MapView mapView) {
		this.mapView = mapView;
		List<Field> field = this.mapView.getFields().stream().filter(each -> !each.isBlocked())
				.collect(Collectors.toList());

		Field f = field.get(new Random().nextInt(field.size()));
		player = new Player();
		player.setImage(new Image("/images/player_straight.png"));
		player.setField(f);
		f.setEntity(player);
		mapView.update();
	}

	@Override
	public void run() {
		while (true) {
			Platform.runLater(() -> this.mapView.update());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void contextMenu() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Aktions Fenster");
		alert.setHeaderText("Ein wildes Pokemon ist erschienen");
		alert.setContentText("Bitte waehle deine Aktion.");
	
		ButtonType kampfButton = new ButtonType("Angreifen");
		ButtonType fangButton = new ButtonType("Fangen");
		ButtonType fliehButton = new ButtonType("Fliehen");
	
		alert.getButtonTypes().setAll(kampfButton, fliehButton);
		if(player.getPokemon().length != 0) {
			alert.getButtonTypes().add(fangButton);
		}
		
		Optional<ButtonType> result = alert.showAndWait();
		System.out.println(result.get());
	}
	

	public void moveEvent(String keyName) {
		double newX = player.getField().getX();
		double newY = player.getField().getY();

		switch (keyName) {
		case "W":
			newY -= 40;
			player.setImage(Main.player_straight);
			break;
		case "D":
			newX += 40;
			player.setImage(Main.player_right);
			break;
		case "S":
			newY += 40;
			player.setImage(Main.player_back);
			break;
		case "A":
			newX -= 40;
			player.setImage(Main.player_left);
			break;
		default:
			break;
		}
		double x = newX;
		double y = newY;
		
		Optional<Field> newField = mapView.getFields().stream().filter(each -> each.getX() == x && each.getY() == y)
				.findFirst();
		if (newField.isPresent()) {
			if (!newField.get().isBlocked()) {
				int difference = player.getField().getType().equals(FieldType.TIEFERSAND) ? 250 : 110;
				if (lastMovement == 0 || System.currentTimeMillis() - lastMovement > difference) {
					lastMovement = System.currentTimeMillis();
					Field newF = newField.get();
					player.getField().setEntity(null);
					newF.setEntity(player);
					player.setField(newField.get());
					Platform.runLater(() -> mapView.update());
			//		mapView.update();
					if(newF.getType().equals(FieldType.HOHESGRASS)) {
						int randDig = new Random().nextInt(100);
						if(randDig < 14) {
							System.out.println("Ein wildes Pokemon erscheint...");
							contextMenu();
						}
					}
					mapView.update();
				}

			}
		}

	}
}
