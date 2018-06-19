package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.PokemonClient;
import logic.PokemonServer;
import resource.XmlControll;
import xml.GameData;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

	public static Image haus_1a;
	public static Image haus_1b;
	public static Image haus_1c;
	public static Image haus_1d;

	public static Image haus_2a;
	public static Image haus_2b;
	public static Image haus_2c;
	public static Image haus_2d;
	public static Image haus_2e;
	public static Image haus_2f;

	public static Image haus_3a;
	public static Image haus_3b;
	public static Image haus_3c;
	public static Image haus_3d;
	public static Image haus_3e;
	public static Image haus_3f;
	public static Image haus_3g;
	public static Image haus_3h;
	public static Image haus_3i;

	public static Image haus_4a;
	public static Image haus_4b;
	public static Image haus_4c;
	public static Image haus_4d;
	public static Image haus_4e;
	public static Image haus_4f;

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
	public static Image man_2_straight;
	public static XmlControll xmlControll;
	public static WindowChanger changer;
	public static MediaPlayer routeMediaPlayer;
	public static MediaPlayer fightMediaPlayer;
	public static boolean allowViewer;
	public static PokemonClient client;
	public static PokemonServer server;
	
	@Override
	public void start(Stage primaryStage) {
		try {

			changer = new WindowChanger(primaryStage);

			AnchorPane root = FXMLLoader.load(getClass().getResource("/guis/MenuGui.fxml"));

			Scene scene = new Scene(root, 330, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("/images/Pokeball.png"));
			primaryStage.setTitle("PokeFx");
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

	public void init() {
		
		//Ressourcen laden
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

		haus_1a = new Image("/images/haus_1a.png");
		haus_1b = new Image("/images/haus_1b.png");
		haus_1c = new Image("/images/haus_1c.png");
		haus_1d = new Image("/images/haus_1d.png");

		haus_2a = new Image("/images/haus_2a.png");
		haus_2b = new Image("/images/haus_2b.png");
		haus_2c = new Image("/images/haus_2c.png");
		haus_2d = new Image("/images/haus_2d.png");
		haus_2e = new Image("/images/haus_2e.png");
		haus_2f = new Image("/images/haus_2f.png");

		haus_3a = new Image("/images/haus_3a.png");
		haus_3b = new Image("/images/haus_3b.png");
		haus_3c = new Image("/images/haus_3c.png");
		haus_3d = new Image("/images/haus_3d.png");
		haus_3e = new Image("/images/haus_3e.png");
		haus_3f = new Image("/images/haus_3f.png");
		haus_3g = new Image("/images/haus_3g.png");
		haus_3h = new Image("/images/haus_3h.png");
		haus_3i = new Image("/images/haus_3i.png");

		haus_4a = new Image("/images/haus_4a.png");
		haus_4b = new Image("/images/haus_4b.png");
		haus_4c = new Image("/images/haus_4c.png");
		haus_4d = new Image("/images/haus_4d.png");
		haus_4e = new Image("/images/haus_4e.png");
		haus_4f = new Image("/images/haus_4f.png");

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
		
		//Pfad
		String documentBase = getHostServices().getDocumentBase();
		
		//Musik ausserhalb des Kampfes
		Media media = new Media(documentBase + "/ressources/musik/Route1.mp3");
		Main.routeMediaPlayer = new MediaPlayer(media);
		
		//Musik innerhalb des Kampfes
		media = new Media(documentBase + "/ressources/musik/Kampf.mp3");
		Main.fightMediaPlayer = new MediaPlayer(media);
		
		
		Main.routeMediaPlayer.setOnEndOfMedia(() -> {
			Main.routeMediaPlayer.seek(Duration.ZERO);
		});
		
		Main.fightMediaPlayer.setOnEndOfMedia(() -> {
			Main.fightMediaPlayer.seek(Duration.ZERO);
		});
		
		xmlControll = new XmlControll();
		man_1_straight = new Image("/images/man_1_straight.png");
		man_2_straight = new Image("/images/man_2_straight.png");

	}
}
