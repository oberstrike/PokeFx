package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import views.MapView;
import xml.GameData;
import xml.XmlControll;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

	public static GameData gameData;
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
	public static Image uebergang;
	public static Image inGrass;
	public static Image cactus_bottom;
	public static Image cactus_top;
	public static Image mtlb_grass;
	public static Image mtlb_sand;
	public static Image mtlb_water;
	public static Image mtlf_grass;
	public static Image mtlf_sand;
	public static Image mtlf_water;
	public static Image mtrb_grass;
	public static Image mtrb_sand;
	public static Image mtrb_water;
	public static Image mtrf_grass;
	public static Image mtrf_sand;
	public static Image mtrf_water;
	public static Image man_1_straight;
	public static XmlControll xmlControll;
	public static Stage kprimaryStage;
	
	
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
			uebergang = new Image("/images/uebergang.png");
			inGrass = new Image("/images/inGrass.png");
			cactus_bottom = new Image("/images/cactus_bottom.png");
			cactus_top = new Image("/images/cactus_top.png");
			mtlb_grass = new Image("/images/mtlb_grass.png");
			mtlb_sand = new Image("/images/mtlb_sand.png");
			mtlf_grass = new Image("/images/mtlf_grass.png");
			mtlf_sand = new Image("/images/mtlf_sand.png");
			mtlf_water = new Image("/images/mtlf_water.png");
			mtlb_water = new Image("/images/mtlb_water.png");
			mtrf_water = new Image("/images/mtrf_water.png");
			mtrb_water = new Image("/images/mtrb_water.png");
			mtrb_sand = new Image("/images/mtrb_sand.png");
			mtrb_grass = new Image("/images/mtrb_grass.png");
			mtrb_sand = new Image("/images/mtrb_sand.png");
			mtrf_grass = new Image("/images/mtrf_grass.png");
			mtrf_sand = new Image("/images/mtrf_sand.png");
			
			player_left = new Image("/images/player_left.png");
			player_right = new Image("/images/player_right.png");
			player_straight = new Image("/images/player_straight.png");
			player_back = new Image("/images/player_back.png");
			
			man_1_straight = new Image("/images/man_1_straight.png");
			
			
			
			xmlControll = new XmlControll();
			
			Scene scene = new Scene(root, 330, 400);
			// Scene scene = new Scene(root,900,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			kprimaryStage = primaryStage;
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
