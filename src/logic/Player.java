package logic;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("player")
public class Player {

	private String name;
	private double x;
	private double y;
	private Field field;


	private Pokemon[] pokemon = new Pokemon[3];
	private Item[] item = new Item[5];
	private float motivation; // Teil der Kampfsimulation. Damit könnte man den Endgegner (oder generell Trainerpokemon) stärker als normale machen
	//private Item[] item = new Item[5];
	
	public Player(){
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
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


}
