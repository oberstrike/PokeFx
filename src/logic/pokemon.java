package logic;

public class pokemon {

	private int id;
	private String name;
	private double att;
	private double deff;
	private double motivation;
	private double init;
	private double hp;
	
	// Konstruktor
	
	public pokemon(int id, String name, double att, double deff, double motivation, double init, double hp) {
		super();
		this.id = id;
		this.name = name;
		this.att = att;
		this.deff = deff;
		this.motivation = motivation;
		this.init = init;
		this.hp = hp;
	}
	
	//Getter + Setter
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAtt() {
		return att;
	}
	public void setAtt(double att) {
		this.att = att;
	}
	public double getDeff() {
		return deff;
	}
	public void setDeff(double deff) {
		this.deff = deff;
	}
	public double getMotivation() {
		return motivation;
	}
	public void setMotivation(double motivation) {
		this.motivation = motivation;
	}
	public double getInit() {
		return init;
	}
	public void setInit(double init) {
		this.init = init;
	}
	public double getHp() {
		return hp;
	}
	public void setHp(double hp) {
		this.hp = hp;
	}
}
