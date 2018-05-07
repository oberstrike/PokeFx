package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import views.MapView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

	public static MapView mapView;
	public static Image stein;
	public static Image wasser;
	public static Image grass;
	public static Image hohesgrass;
	public static Image sand;
	public static Image tiefersand;
	public static Image player_left;
	public static Image player_right;
	public static Image player_straight;
	public static Image player_back;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane root = FXMLLoader.load(getClass().getResource("/guis/MenuGui.fxml"));
			System.out.println("Bilder werden geladen:");

			stein = new Image("/images/stein.png");
			wasser = new Image("/images/wasser.png");
			grass = new Image("/images/grass.png");
			hohesgrass = new Image("/images/hohesgrass.png");
			sand = new Image("/images/sand.png");
			tiefersand = new Image("/images/tiefersand.png");
			
			player_left = new Image("/images/player_left.png");
			player_right = new Image("/images/player_right.png");
			player_straight = new Image("/images/player_straight.png");
			player_back = new Image("/images/player_back.png");
			
			Scene scene = new Scene(root, 330, 400);
			// Scene scene = new Scene(root,900,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
