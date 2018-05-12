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
	private double maxHp;
	private int xp;
	

	private double spawn;
	
	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", type=" + type + ", spawn=" + spawn + "]";
	}

	public Pokemon() {
		super();
	}
	
	//--------------------------------------------------------
	
	// Konstruktor
	public Pokemon(int id, int level, PokemonType type, String name, double att, double deff, double motivation, double init,
			double hp, int xp) {
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
		this.setMaxHp(hp);
		this.xp = xp;
	}
	
	public Pokemon newPokemon() {
		Pokemon mon2 = new Pokemon();
		mon2.setId(this.getId());
		mon2.setLevel(this.getLevel());
		mon2.setType(this.getType());
		mon2.setName(this.getName());
		mon2.setAtt(this.getAtt());
		mon2.setDeff(this.getDeff());
		mon2.setMotivation(this.getMotivation());
		mon2.setInit(this.getInit());
		mon2.setHp(this.getHp());
		mon2.setMaxHp(this.getMaxHp());
		mon2.setXp(this.getXp());
		return mon2;
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
	
	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		if (this.maxHp == 0.0) {
			this.maxHp = (1+(level/10))*hp;
		}
	}
	
	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
		updateLevel();
	}
	
	public double getSpawn() {
		return spawn;
	}

	public void setSpawn(double spawn) {
		this.spawn = spawn;
	}

	public static Pokemon fight(Pokemon mon1, Pokemon mon2) {
		Pokemon winner;
		
		Effectives effekt = new Effectives();
		
		if (mon2.getHp() <= 0) {
			return mon1;
		}
		
		double multiplierMon1 = 1;
		if(effekt.table.get(mon2.getType()).containsKey(mon1.getType()))		
			multiplierMon1 = effekt.table.get(mon2.getType()).get(mon1.getType());
		
		double multiplierMon2 = 1;
		if(effekt.table.get(mon1.getType()).containsKey(mon2.getType()))		
			multiplierMon2 = effekt.table.get(mon1.getType()).get(mon2.getType());
		
		

		
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
					mon2.setXp(mon2.getXp() + calcXp(mon1, mon2));
					mon2.updateLevel();
					System.out.println("Dein Pokemon " + mon2.getName() + " hat " + calcXp(mon1, mon2) + " Erfahrung erhalten.");
					return winner;
				}
				System.out.println(mon1.getName() + " HP: " + mon1.getHp());
				System.out.println(mon2.getName() + " HP: " + mon2.getHp());
			}
			return null;
		} else {
			while (mon1.getHp() > 0 && mon2.getHp() > 0) {
				mon1.setHp(mon1.getHp() - (attMon2*randomCrit()-(deffMon1/2)));
				if (mon1.getHp() <= 0) {
					winner = mon2;
					mon2.setXp(mon2.getXp() + calcXp(mon1, mon2));
					mon2.updateLevel();
					System.out.println("Dein Pokemon " + mon2.getName() + " hat " + calcXp(mon1, mon2) + " Erfahrung erhalten.");
					return winner;
				}
				mon2.setHp(mon2.getHp() - (attMon1*randomCrit()-(deffMon1/2)));
				if (mon2.getHp() <= 0) {
					winner = mon1;
					return winner;
				}
				System.out.println(mon1.getName() + " HP: " + mon1.getHp());
				System.out.println(mon2.getName() + " HP: " + mon2.getHp());
			}
			return null;
		}
	}
	
	public int calculateAtt() {
		int att = (int) ((this.getMotivation()*0.01)*(this.getAtt() * (1 + (this.getLevel()/5)))*0.2);
		System.out.println(this.getName() + " Angriff: " + att + " HP: " + this.getHp());
		return att;
	}
	
	public int calculateDeff() {
		int deff = (int) ((this.getMotivation()*0.01)*(this.getDeff() * (1 + (this.getLevel()/5)))*0.2);
		System.out.println(this.getName() + " Verteidigung: " + deff + " HP: " + this.getHp());
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
	
	public static int calcXp(Pokemon mon1, Pokemon mon2) {
		int xp = 0;
		int levelDiff = mon1.getLevel() - mon2.getLevel()/2;
		if (levelDiff <= 0) {
			levelDiff = 1/(Math.abs(levelDiff)+1);
		}
		int attDiff = mon1.calculateAtt() - mon2.calculateAtt()/2;
		if (attDiff <= 0) {
			attDiff = 1/(Math.abs(attDiff)+1);
		}
		int deffDiff = mon1.calculateDeff() - mon2.calculateDeff()/2;
		if (deffDiff <= 0) {
			deffDiff = 1/(Math.abs(deffDiff)+1);
		}
		xp = (int) Math.sqrt(mon1.getLevel() + Math.pow(mon2.getLevel(), 3) * (levelDiff + attDiff + deffDiff));
		return xp;
	
	}
	
	public void updateLevel() {
		int xp = this.getXp();
		int level = (int) Math.floor(Math.sqrt(xp)/4);
		this.setLevel(level);
	}
	
}
