package logic;

public class Pokemon {

	private int id;
	private int level;
	private int type;
	private String name;
	private double att;
	private double deff;
	private double motivation;
	private double init;
	private double hp;
	
	// Konstruktor
	
	public Pokemon(int id, int level, int type, String name, double att, double deff, double motivation, double init,
			double hp) {
		super();
		this.id = id;
		this.level = level;
		this.type = type;
		this.name = name;
		this.att = att;
		this.deff = deff;
		this.motivation = motivation;
		this.init = init;
		this.hp = hp;
	}

	//Getter + Setter
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
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
	
	public Object fight(Pokemon mon1, Pokemon mon2) {
		Pokemon winner;
		
		int attMon1 = calculateAtt(mon1);
		int deffMon1 = calculateDeff(mon1);
		int attMon2 = calculateAtt(mon2);
		int deffMon2 = calculateDeff(mon2);
			
		// wer beginnt?
		if (mon1.getInit() >= mon2.getInit()) {
			while (mon1.getHp() > 0 && mon2.getHp() > 0) {
				mon2.setHp(mon2.getHp() - attMon1);
				if (mon2.getHp() <= 0) {
					winner = mon1;
					return winner;
				}
				mon1.setHp(mon1.getHp() - attMon2);
				if (mon1.getHp() <= 0) {
					winner = mon2;
					return winner;
				}
			}
		} else {
			while (mon1.getHp() > 0 && mon2.getHp() > 0) {
				mon1.setHp(mon1.getHp() - attMon2);
				if (mon1.getHp() <= 0) {
					winner = mon2;
					return winner;
				}
				mon2.setHp(mon2.getHp() - attMon1);
				if (mon2.getHp() <= 0) {
					winner = mon1;
					return winner;
				}
			}
		}
	}
	
	public int calculateAtt(Pokemon mon) {
		int att = (int) (mon.getMotivation()*(mon.getAtt() * (1 + (mon.getLevel()/10))));
		return att;
	}
	
	public int calculateDeff(Pokemon mon) {
		int deff = (int) (mon.getMotivation()*(mon.getDeff() * (1 + (mon.getLevel()/10))));
		return deff;
	}
	
}
