package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
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
	public static Image baum_bottom;
	public static Image baum_top;
	
	public static Image fels_water;
	public static Image fels_grass;
	public static Image fels_sand;
	public static Image grass_bl;
	
	public static Image mtr_grass;
	public static Image mtb_grass;
	public static Image mtf_grass;
	public static Image mtl_grass;
	public static Image mtlb_grass;
	public static Image mtlf_grass;
	public static Image mtrb_grass;
	public static Image mtrf_grass;
	public static Image mtirf_grass;
	public static Image mtirb_grass;
	public static Image mtilf_grass;
	public static Image mtilb_grass;
	
	public static Image mtr_water;
	public static Image mtb_water;
	public static Image mtf_water;
	public static Image mtl_water;
	public static Image mtlb_water;
	public static Image mtlf_water;
	public static Image mtrb_water;
	public static Image mtrf_water;
	public static Image mtirf_water;
	public static Image mtirb_water;
	public static Image mtilf_water;
	public static Image mtilb_water;
	
	public static Image mtr_sand;
	public static Image mtb_sand;
	public static Image mtf_sand;
	public static Image mtl_sand;
	public static Image mtlb_sand;
	public static Image mtlf_sand;
	public static Image mtrf_sand;
	public static Image mtrb_sand;
	public static Image mtirf_sand;
	public static Image mtirb_sand;
	public static Image mtilf_sand;
	public static Image mtilb_sand;
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
			baum_bottom = new Image("/images/baum_bottom.png");
			baum_top = new Image("/images/baum_top.png");
			
			fels_water = new Image("/images/fels_water.png");
			fels_grass = new Image("/images/fels_grass.png");
			fels_sand = new Image("/images/fels_sand.png");
			grass_bl = new Image("/images/grass_bl.png");
			
			mtr_grass = new Image("/images/mtr_grass.png");
			mtl_grass = new Image("/images/mtl_grass.png");
			mtb_grass = new Image("/images/mtb_grass.png");
			mtf_grass = new Image("/images/mtf_grass.png");
			mtlb_grass = new Image("/images/mtlb_grass.png");
			mtlf_grass = new Image("/images/mtlf_grass.png");
			mtrb_grass = new Image("/images/mtrb_grass.png");
			mtrf_grass = new Image("/images/mtrf_grass.png");
			mtilf_grass = new Image("/images/mtilf_grass.png");
			mtilb_grass = new Image("/images/mtilb_grass.png");
			mtirf_grass = new Image("/images/mtirf_grass.png");
			mtirb_grass = new Image("/images/mtirb_grass.png");
			
			mtr_water = new Image("/images/mtr_water.png");
			mtl_water = new Image("/images/mtl_water.png");
			mtb_water = new Image("/images/mtb_water.png");
			mtf_water = new Image("/images/mtf_water.png");
			mtlf_water = new Image("/images/mtlf_water.png");
			mtlb_water = new Image("/images/mtlb_water.png");
			mtrf_water = new Image("/images/mtrf_water.png");
			mtrb_water = new Image("/images/mtrb_water.png");
			mtilf_water = new Image("/images/mtilf_water.png");
			mtilb_water = new Image("/images/mtilb_water.png");
			mtirf_water = new Image("/images/mtirf_water.png");
			mtirb_water = new Image("/images/mtirb_water.png");
			
			mtr_sand = new Image("/images/mtr_sand.png");
			mtl_sand = new Image("/images/mtl_sand.png");
			mtb_sand = new Image("/images/mtb_sand.png");
			mtf_sand = new Image("/images/mtf_sand.png");
			mtlf_sand = new Image("/images/mtlf_sand.png");
			mtrb_sand = new Image("/images/mtrb_sand.png");
			mtlb_sand = new Image("/images/mtlb_sand.png");
			mtrf_sand = new Image("/images/mtrf_sand.png");
			mtilf_sand = new Image("/images/mtilf_sand.png");
			mtilb_sand = new Image("/images/mtilb_sand.png");
			mtirf_sand = new Image("/images/mtirf_sand.png");
			mtirb_sand = new Image("/images/mtirb_sand.png");
			
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
