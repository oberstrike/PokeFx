package application;

import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowChanger {
	
	private Stage stage;
	
	public WindowChanger() {}
	
	public void changeWindow(String url) {
		this.changeWindow(url, (loader) ->{
			
		});
	}
	
	public void changeWindow(String url, String title) {
		this.changeWindow(url, (loader) -> {});
		this.stage.setTitle(title);
	}
	
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void changeWindow(String url, Consumer<FXMLLoader> consumer) {
		FXMLLoader loader = new FXMLLoader();
		if(consumer != null)
			consumer.accept(loader);
		loader.setLocation(getClass().getResource(url));
		Parent parent = null;
		try{
			parent = loader.load();
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.stage.close();
			this.stage.setScene(scene);
			this.stage.setResizable(false);
			this.stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public WindowChanger(Stage stage) {
		this.stage = stage;
	}
	
	
}
