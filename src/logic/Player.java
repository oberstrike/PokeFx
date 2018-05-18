package logic;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import application.Main;
import field.Field;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import pokemon.Pokemon;

@XStreamAlias("player")
public class Player implements Entity {

	private String name;
	private Field field;
	private List<Boolean> wins = new ArrayList<>();

	@XStreamOmitField
	private Image image;

	@Override
	public String toString() {
		return "Player [name=" + name + ", field=" + field + ", pokemons=" + pokemons + "]";
	}

	private List<Pokemon> pokemons = new ArrayList<>();
	private Item[] item = new Item[5];
	private float motivation; // Teil der Kampfsimulation. Damit könnte man den Endgegner (oder generell Trainerpokemon) stärker als normale machen
	private List<Integer> pokedex = new ArrayList<>();
	
	public Player(){
		
	}

	public Player(double x, double y) {

	}
	
	public int getAverageLevel() {
		return (int) pokemons.stream().mapToInt(Pokemon::getLevel).average().getAsDouble();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pokemon> getPokemon() {
		return pokemons;
	}

	public void setPokemon(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
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
	

	public List<Integer> getPokedex() {
		return pokedex;
	}

	public void setPokedex(Pokemon mon, int type) {
		this.pokedex.set(mon.getId(), type);
	}

	public void setPokedex(int i, int type) {
		this.pokedex.set(i, type);
	}

	public void setPokedex(List<Integer> pokedex) {
		this.pokedex = pokedex;
	}

	public List<Boolean> getWins() {
		return wins;
	}
	
	public boolean getWins(int i) {
		return wins.get(i);
	}

	public void setWins(List<Boolean> wins) {
		this.wins = wins;
	}



}
