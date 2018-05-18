package logic;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javafx.scene.image.Image;
import pokemon.Pokemon;
@XStreamAlias("Trainer")
public class Trainer implements Entity{

	@XStreamOmitField
	private Image image;
	
	private String name;
	private boolean win;
	private List<Pokemon> pokemons = new ArrayList<>();
	
	public Trainer(Image image, String name, List<Pokemon> pokemons) {
		this.setImage(image);
		this.setName(name);
		this.getPokemons().addAll(pokemons);
		this.pokemons.forEach(each -> {
			each.setTrained(true); 
			each.setLevel(11);
		});
		
	}
	
	@Override
	public Image getImage() {
		return image;
	}


	@Override
	public String toString() {
		return "Trainer [image=" + image + ", name=" + name + ", pokemons=" + pokemons + "]";
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<Pokemon> getPokemons() {
		return pokemons;
	}



	public void setPokemons(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}



	public void setImage(Image image) {
		this.image = image;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

}
