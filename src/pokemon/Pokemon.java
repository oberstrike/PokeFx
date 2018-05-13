package pokemon;

import java.util.HashMap;
import java.util.Random;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import application.Main;

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
	private int xp;
	private double spawn;
	
	@Override
	public String toString() {
		return "Pokemon [name=" + name + ", level=" + level + ", hp=" + hp + ", xp=" + xp + ", spawn=" + spawn + "]";
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
		this.xp = xp;
	}
	

	
	
	public void setXp(int xp) {
		this.xp = xp;
	}

	//Copy Konstruktor
	public Pokemon(Pokemon pokemon) {
		this();
		this.id = pokemon.id;
		this.level = pokemon.level;
		this.type = pokemon.type;
		this.name = pokemon.name;
		this.att = pokemon.att;
		this.deff = pokemon.deff;
		this.motivation = pokemon.motivation;
		this.init = pokemon.init;
		this.hp = pokemon.hp;
		this.spawn = pokemon.spawn;
		this.xp = pokemon.xp;
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
		this.hp = hp < 0 ? 0 : hp;
	}
	
	public double getMaxHp() {
		return (1 + level/11)*Main.xmlControll.getPokemonsById(id).getHp(); 
	}

	public int getXp() {
		return xp;
	}

	public void addXp(int xp) {
		this.xp += xp;
		updateLevel();
	}
	
	public double getSpawn() {
		return spawn;
	}

	public void setSpawn(double spawn) {
		this.spawn = spawn;
	}

	//Kampfsimulator
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
					mon2.addXp(mon1.calcXp());
					System.out.println("Dein Pokemon " + mon2.getName() + " hat " + mon1.calcXp() + " Erfahrung erhalten.");
					return winner;
				}
			}
			return null;
		} else {
			while (mon1.getHp() > 0 && mon2.getHp() > 0) {
				mon1.setHp(mon1.getHp() - (attMon2*randomCrit()-(deffMon2/2)));
				if (mon1.getHp() <= 0) {
					winner = mon2;
					mon2.addXp(mon1.calcXp());
					mon2.updateLevel();
					System.out.println("Dein Pokemon " + mon2.getName() + " hat " + mon1.calcXp() + " Erfahrung erhalten.");
					return winner;
				}
				mon2.setHp(mon2.getHp() - (attMon1*randomCrit()-(deffMon2/2)));
				if (mon2.getHp() <= 0) {
					winner = mon1;
					return winner;
				}
			}
			return null;
		}
	}
	
	public int calculateAtt() {
		int att = (int) ((this.getMotivation()*0.01)*(this.getAtt() * (1 + (this.getLevel()/5)))*0.15);
		System.out.println(this.getName() + " Angriff: " + att + " HP: " + this.getHp());
		return att;
	}
	
	public int calculateDeff() {
		int deff = (int) ((this.getMotivation()*0.01)*(this.getDeff() * (1 + (this.getLevel()/5)))*0.15);
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
	
	/* Orginal Pokemon 1 bis 4. Generation
	 * Erfahrungsrechner
	 * 
	 */
	
	public int calcXp() {
		int a = 1; //Wildes Pokemon
		int t = 1; //Eigenes Pokemon
		int b = Main.xmlControll.getPokemonByName(name).getXp(); //Basiserfahrung
		System.out.println(Main.xmlControll.getPokemonsById(id));
		int e = 1; //Glückseifaktor
		int l = level; //Level des gegnerischen Pokemons;
		int s = 1; //Anzahl an Pokemon die beteiligt waren;
		
		
		return (a * t * b * e * l) / (7 * s);
	}
	
	public void updateLevel() {
		int xp = this.getXp();
		if(xp >= getXpForNextLevel()) {
			xp = (getXpForNextLevel() - xp);
			this.level++;
			
		}
	}

	public int getXpForNextLevel() {
		return (int) (Math.pow(level, 3));
	}

	
}
