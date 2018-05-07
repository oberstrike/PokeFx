package logic;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import field.Field;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import pokemon.Pokemon;

@XStreamAlias("player")
public class Player implements Entity {

	private String name;
	private Field field;
	private Image image;

	private Pokemon[] pokemon = new Pokemon[3];
	private Item[] item = new Item[5];
	private float motivation; // Teil der Kampfsimulation. Damit könnte man den Endgegner (oder generell Trainerpokemon) stärker als normale machen
	//private Item[] item = new Item[5];
	
	public Player(){
		
	}

	public Player(double x, double y) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pokemon[] getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon[] pokemon) {
		this.pokemon = pokemon;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public Image getImage() {
		return this.image;
	}


}
