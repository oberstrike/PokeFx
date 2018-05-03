package logic;

public class Player {

	private String name;
	private double x;
	private double y;
<<<<<<< HEAD
	private Pokemon pokemon;
	private Field field;

	public Player() {

=======
	private Pokemon[] pokemon = new Pokemon[3];
	private Item[] item = new Item[5];
	private float motivation;
	
	public Player(){
		
>>>>>>> d7c928c7349a7a287304ba5eb387d7dccd917c1b
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
<<<<<<< HEAD

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

=======
	
>>>>>>> d7c928c7349a7a287304ba5eb387d7dccd917c1b
}
