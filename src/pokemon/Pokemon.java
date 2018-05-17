package pokemon;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import application.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

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
	private double base_hp;
	private double spawn;
	private boolean trained;

	@Override
	public String toString() {
		return "Pokemon [name=" + name + "]";
	}

	public Pokemon() {
		super();
	}

	// --------------------------------------------------------

	// Konstruktor
	public Pokemon(int id, int level, PokemonType type, String name, double att, double deff, double motivation,
			double init, int base_hp, int xp) {
		this();
		this.id = id;
		this.level = level;
		this.type = type;
		this.name = name;
		this.att = att;
		this.deff = deff;
		this.motivation = motivation;
		this.init = init;
		this.base_hp = base_hp;
		this.xp = xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	// Copy Konstruktor
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
		this.base_hp = pokemon.base_hp;
		this.spawn = pokemon.spawn;
		this.xp = pokemon.xp;
		this.hp = calculateHp();
		this.trained = false;
	}

	// Getter + Setter

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

	// Kampfsimulator
	public Pokemon fight(Pokemon mon2) {
		Pokemon winner;

		Effectives effekt = new Effectives();

		if (mon2.getHp() <= 0) {
			return this;
		}
		String output = "";
		String output2 = "";

		double multiplierMon1 = 1;
		if (effekt.table.get(mon2.getType()).containsKey(this.getType()))
			multiplierMon1 = effekt.table.get(mon2.getType()).get(this.getType());
		
		// System.out.println(effekt.table.get(this.getType()).get(this.getType()));
		output = "==================== \n\nDein Pokemon:\n" + this.getName() + " Lvl: " + this.getLevel() + "\n-------------- \n Effektivität: " + multiplierMon1 + "\n HP: " + this.getHp();

		double multiplierMon2 = 1;
		if (effekt.table.get(this.getType()).containsKey(mon2.getType()))
			multiplierMon2 = effekt.table.get(this.getType()).get(mon2.getType());
		
		// System.out.println(effekt.table.get(mon2.getType()).get(mon2.getType()));
		output2 = "___________________ \n\nGegnerisches Pokemon:\n" + mon2.getName() + " Lvl: " + mon2.getLevel() + "\n-------------- \n Effektivität: " + multiplierMon2 + "\n HP: " + mon2.getHp();

		int attMon1 = (int) (this.calculateAtt() * multiplierMon1);
		int deffMon1 = this.calculateDeff();
		int attMon2 = (int) (mon2.calculateAtt() * multiplierMon2);
		int deffMon2 = mon2.calculateDeff();
		
		output += "\n ATK: " + attMon1 + " || DEF: " + deffMon1 + " || MOT: " + this.motivation + "\n-------------- \n";
		output2 += "\n ATK: " + attMon2 + " || DEF: " + deffMon2 + " || MOT " + mon2.motivation + "\n-------------- \n";

		// wer beginnt?
		if (this.getInit() >= mon2.getInit()) {
			while (this.getHp() > 0 && mon2.getHp() > 0) {
				double crit = randomCrit();
				double damage = (attMon1 * crit - (deffMon2 * 0.75));
				if (damage <= 0) { damage = 1; }
				mon2.setHp(mon2.getHp() - damage);
				output2 += "\n HP: " + mon2.getHp() + " (-" + damage + ") || Crit: " + crit;
				if (mon2.getHp() <= 0) {
					System.out.println(output + "\n" + output2);
					this.updateMotivation(true);
					return this;
				}
				damage = (attMon2 * randomCrit() - (deffMon1 * 0.5));
				if (damage <= 0) { damage = 1; }
				this.setHp(this.getHp() - damage);
				output += "\n HP: " + this.getHp() + " (-" + damage + ") || Crit: " + crit;
				if (this.getHp() <= 0) {
					System.out.println(output + "\n" + output2);
					winner = mon2;
					this.updateMotivation(false);
					return winner;
				}
			}
			return null;
		} else {
			while (this.getHp() > 0 && mon2.getHp() > 0) {
				double crit = randomCrit();
				double damage = (attMon2 * crit - (deffMon1 * 0.5));
				if (damage <= 0) { damage = 1; }
				this.setHp(this.getHp() - damage);
				output += "\n HP: " + this.getHp() + " (-" + damage + ") || Crit: " + crit;
				if (this.getHp() <= 0) {
					System.out.println(output + "\n" + output2);
					winner = mon2;
					this.updateMotivation(false);
					return winner;
				}
				damage = (attMon1 * randomCrit() - (deffMon2 * 0.5));
				if (damage <= 0) { damage = 1; }
				mon2.setHp(mon2.getHp() - damage);
				output2 += "\n HP: " + mon2.getHp() + " (-" + damage + " ||) Crit: " + crit;
				if (mon2.getHp() <= 0) {
					System.out.println(output + "\n" + output2);
					winner = this;
					this.updateMotivation(true);
					return winner;
				}
			}
			return null;
		}
	}

	public int calculateHp() {
		int hp = (int) (((base_hp + 8) * 2 + (1 / 4)) * level / 100) + level + 10;
		return hp;
	}

	public int calculateAtt() {
		int att = (int) ((level + 0.5) / 50 * this.att * (this.motivation/100));
		// int att = (int) (((this.att + 8) * 2 + (1/4)) * level / 100) + 5;
		if (this.trained == true) {
			att = (int) (att * 1.5);
		}
		return att;
	}

	public int calculateDeff() {
		int deff = (int) ((level + 0.5) / 50 * this.deff * (this.motivation/100));
		// int deff = (int) (((this.deff + 8) * 2 + (1/4)) * level / 100) + 5;
		if (this.trained == true) {
			deff = (int) (deff * 1.5);
		}
		System.out.println("\nTrainerpokemon: " + this.trained + "\n");
		return deff;
	}

	public float randomCrit() {
		float crit = 1;
		Random r = new Random();
		float chance = r.nextFloat();
		if (chance <= init / 512) {
			crit = 2;
		} else if (chance >= 0.95) {
			crit = (float) 0.5;
		}
		return crit;
	}

	/*
	 * Orginal Pokemon 1 bis 4. Generation Erfahrungsrechner
	 * 
	 */

	public int calcXp() {
		int a = 1; // Wildes Pokemon
		int t = 1; // Eigenes Pokemon
		int b = Main.xmlControll.getPokemonByName(name).getXp(); // Basiserfahrung
		int e = 1; // Glückseifaktor
		int l = level; // Level des gegnerischen Pokemons;
		int s = 1; // Anzahl an Pokemon die beteiligt waren;

		return (a * t * b * e * l) / (7 * s);
	}
	
	public void evolveTo(Pokemon pokemon) {
		this.id = pokemon.id;
		this.type = pokemon.type;
		this.name = pokemon.name;
		this.att = pokemon.att;
		this.deff = pokemon.deff;
		this.motivation = pokemon.motivation;
		this.init = pokemon.init;
		this.base_hp = pokemon.base_hp;
		this.hp = pokemon.hp;
	}

	public void updateLevel() {
		int xp = this.getXp();
		int xpForNextLevel = getXpForNextLevel();
		if (xp >= xpForNextLevel) {
			System.out.println(this.name + " ist eine Stufe aufgestiegen.");
			this.xp = (xpForNextLevel - xp) < 0 ? 0 : (xpForNextLevel - xp); 
			this.level++;
			
			HashMap<Integer, String> evolvingdex = Main.xmlControll.getEvolutiondex().get(name);
			if (evolvingdex != null) {
				Optional<Integer> key = evolvingdex.keySet().stream().filter(each -> each.intValue() <= level).findFirst();
				if(key.isPresent()) {
					Pokemon pokemon = Main.xmlControll.getPokemonByName(evolvingdex.get(key.get()));
					evolveTo(pokemon);
				}	
			}
		}
	}
	
	public void updateMotivation(boolean win) {
		if (win == true) {
			if (this.motivation < 100) {
				this.setMotivation(100);
			} else if (this.motivation < 119) {
				this.setMotivation(this.motivation + 2);
			}
		} else {
			if (this.motivation > 100) {
				this.setMotivation(100);
			} else if (this.motivation > 81) {
				this.setMotivation(this.motivation - 2);
			}
		}
	}

	public int getXpForNextLevel() {
		return (int) (Math.pow(level, 3));
	}

	public double getBase_hp() {
		return base_hp;
	}

	public void setBase_hp(int base_hp) {
		this.base_hp = base_hp;
	}
	
	public boolean isTrained() {
		return trained;
	}

	public void setTrained(boolean trained) {
		this.trained = trained;
	}

}
