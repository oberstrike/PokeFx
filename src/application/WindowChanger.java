package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowChanger {
	
	public void changeWindow(String url, ActionEvent event) {
		Stage window = stageCreator(url, event);
		window.show();
	}
	public void changeWindow(String url, ActionEvent event, String title) {
		Stage window = stageCreator(url, event);
		window.setTitle(title);
		window.show();
	}
	
	public void changeWindow(String url, ActionEvent event, String title, double width, double heigt) {
		Stage window = stageCreator(url, event);
		window.setTitle(title);
		window.setWidth(width);
		window.setHeight(heigt);
		window.show();
	}
	
	
	public Stage stageCreator(String url, ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(url));
		Parent parent = null;
		Stage window = null;
		try {
			parent = loader.load();
			Scene scene = new Scene(parent);
			window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.hide();
			window.setScene(scene);
			window.setResizable(false);
			return window;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public WindowChanger() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
