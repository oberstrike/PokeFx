package pokemon;

import java.util.HashMap;
import java.util.Random;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Pokemon")
public class Pokemon {
	
	@XStreamAsAttribute
	private String name;
	private int id;
	private int level;
	private PokemonType type;
	private double att;
	private double deff;
	private double motivation;
	private double init;
	private double hp;
	
	
	public Pokemon() {
		super();
	}
	
	//--------------------------------------------------------
	
	// Konstruktor
	public Pokemon(int id, int level, PokemonType type, String name, double att, double deff, double motivation, double init,
			double hp) {
		this();
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

	public PokemonType getType() {
		return type;
	}

	public void setType(PokemonType type) {
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
		if (hp < 0) {
			hp = 0;
		}
		this.hp = hp;
	}
	
	public static Pokemon fight(Pokemon mon1, Pokemon mon2) {
		Pokemon winner;
		
		Effectives effekt = new Effectives();
		
		double multiplierMon1 = Effectives.table.get(mon2.type).get(mon1.type);
		double multiplierMon2 = Effectives.table.get(mon1.type).get(mon2.type);
		
		int attMon1 = (int) (mon1.calculateAtt() * multiplierMon1);
		int deffMon1 = mon1.calculateDeff();
		int attMon2 = (int) (mon2.calculateAtt() * multiplierMon2);
		int deffMon2 = mon2.calculateDeff();
		
		// wer beginnt?
		if (mon1.getInit() >= mon2.getInit()) {
			while (mon1.getHp() > 0 && mon2.getHp() > 0) {
				mon2.setHp(mon2.getHp() - (attMon1*randomCrit()-(deffMon1/2)));
				if (mon2.getHp() <= 0) {
					winner = mon1;
					return winner;
				}
				mon1.setHp(mon1.getHp() - (attMon2*randomCrit()-(deffMon1/2)));
				if (mon1.getHp() <= 0) {
					winner = mon2;
					return winner;
				}
			}
			return null;
		} else {
			while (mon1.getHp() > 0 && mon2.getHp() > 0) {
				mon1.setHp(mon1.getHp() - (attMon2*randomCrit()-(deffMon1/2)));
				if (mon1.getHp() <= 0) {
					winner = mon2;
					return winner;
				}
				mon2.setHp(mon2.getHp() - (attMon1*randomCrit()-(deffMon1/2)));
				if (mon2.getHp() <= 0) {
					winner = mon1;
					return winner;
				}
			}
			return null;
		}
	}
	
	public int calculateAtt() {
		int att = (int) (this.getMotivation()*(this.getAtt() * (1 + (this.getLevel()/10))));
		return att;
	}
	
	public int calculateDeff() {
		int deff = (int) (this.getMotivation()*(this.getDeff() * (1 + (this.getLevel()/10))));
		return deff;
	}
	
	public static float randomCrit() {
		float crit = 1;
		Random r = new Random();
		float chance = r.nextFloat();
		if (chance <= 0.05f) {
			crit = 2;
		} else if (chance >= 0.95) {
			crit = (float) 0.5;
		}
		return crit;
	}
	
}