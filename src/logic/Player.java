package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

	
	private Vector<Pokemon> pokemons = new Vector<>();
	
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

	public Vector<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(Vector<Pokemon> pokemons) {
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
