package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import views.MapView;

public class ViewerController implements Initializable {

	private MapView mapView;

	@FXML
	private AnchorPane anchor;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(Main.gameData.getMap()!=null)
			mapView.setMap(Main.gameData.getMap());
		if(Main.client != null) {
			Main.client.setOnReceive(each -> {
				Platform.runLater(() -> {
					mapView = new MapView(each.getFields());
					mapView.setLayoutX(0);
					mapView.setLayoutY(32);
					mapView.update();
					anchor.getChildren().add(mapView);
				});
			});
		}
	}

}
