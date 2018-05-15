package logic;

import javafx.scene.image.Image;

public interface Entity{	
	public Image getImage();
	public<T extends Entity> void interact(T entity);
;
}
