package logic;

public class Player {

	private String name;
	private double x;
	private double y;
	private Pokemon[] pokemon = new Pokemon[3];
	private Item[] item = new Item[5];
	private float motivation; // Teil der Kampfsimulation. Damit könnte man den Endgegner (oder generell Trainerpokemon) stärker als normale machen
	
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
	
}
