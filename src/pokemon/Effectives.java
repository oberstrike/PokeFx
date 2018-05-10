package pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Effectives {
	
	//
	
	
	private HashMap<PokemonType, Double> boden;
	private HashMap<PokemonType, Double> drache;
	private HashMap<PokemonType, Double> eis;
	private HashMap<PokemonType, Double> elektrizitaet;
	private HashMap<PokemonType, Double> feuer;
	private HashMap<PokemonType, Double> flug;
	private HashMap<PokemonType, Double> geist;
	private HashMap<PokemonType, Double> gestein;
	private HashMap<PokemonType, Double> gift;
	private HashMap<PokemonType, Double> kaefer;
	private HashMap<PokemonType, Double> kampf;
	private HashMap<PokemonType, Double> normal;
	private HashMap<PokemonType, Double> pflanze;
	private HashMap<PokemonType, Double> psycho;
	private HashMap<PokemonType, Double> wasser;
	
	public HashMap<PokemonType, HashMap<PokemonType, Double>> table = new HashMap<>();
	
	public Effectives() {
		super();
		
		//Verteidiger || Angreifer
		this.boden = new HashMap<PokemonType, Double>();
		this.boden.put(PokemonType.KAMPF, 2.0);
		this.boden.put(PokemonType.GEIST, 0.0);
		table.put(PokemonType.BODEN, boden);
		
		this.drache = new HashMap<PokemonType, Double>();
		this.drache.put(PokemonType.FEUER, 0.5);
		this.drache.put(PokemonType.WASSER, 0.5);
		this.drache.put(PokemonType.PFLANZE, 0.5);
		this.drache.put(PokemonType.ELEKTRIZITAET, 0.5);
		this.drache.put(PokemonType.EIS, 2.0);
		this.drache.put(PokemonType.DRACHE, 2.0);
		table.put(PokemonType.DRACHE, drache);
		
		this.eis = new HashMap<PokemonType, Double>();
		this.eis.put(PokemonType.FEUER, 2.0);
		this.eis.put(PokemonType.EIS, 0.5);
		this.eis.put(PokemonType.KAMPF, 2.0);
		this.eis.put(PokemonType.GESTEIN, 2.0);
		table.put(PokemonType.EIS, eis);
		
		this.elektrizitaet = new HashMap<PokemonType, Double>();
		this.elektrizitaet.put(PokemonType.ELEKTRIZITAET, 0.5);
		this.elektrizitaet.put(PokemonType.BODEN, 2.0);
		this.elektrizitaet.put(PokemonType.FLUG, 0.5);
		table.put(PokemonType.ELEKTRIZITAET, elektrizitaet);
		
		this.feuer = new HashMap<PokemonType, Double>();
		this.feuer.put(PokemonType.FEUER, 0.5);
		this.feuer.put(PokemonType.WASSER, 2.0);
		this.feuer.put(PokemonType.PFLANZE, 0.5);
		this.feuer.put(PokemonType.BODEN, 2.0);
		this.feuer.put(PokemonType.KAEFER, 0.5);
		this.feuer.put(PokemonType.GESTEIN, 2.0);
		table.put(PokemonType.FEUER, feuer);
		
		this.flug = new HashMap<PokemonType, Double>();
		this.flug.put(PokemonType.PFLANZE, 0.5);
		this.flug.put(PokemonType.ELEKTRIZITAET, 2.0);
		this.flug.put(PokemonType.EIS, 2.0);
		this.flug.put(PokemonType.KAMPF, 0.5);
		this.flug.put(PokemonType.BODEN, 0.0);
		this.flug.put(PokemonType.KAEFER, 0.5);
		this.flug.put(PokemonType.GESTEIN, 2.0);
		table.put(PokemonType.FLUG, flug);
		
		this.geist = new HashMap<PokemonType, Double>();
		this.geist.put(PokemonType.NORMAL, 0.0);
		this.geist.put(PokemonType.KAMPF, 0.0);
		this.geist.put(PokemonType.GIFT, 0.5);
		this.geist.put(PokemonType.KAEFER, 0.5);
		this.geist.put(PokemonType.GEIST, 2.0);
		table.put(PokemonType.GEIST, geist);
		
		this.gestein = new HashMap<PokemonType, Double>();
		this.gestein.put(PokemonType.NORMAL, 0.5);
		this.gestein.put(PokemonType.FEUER, 0.5);
		this.gestein.put(PokemonType.WASSER, 2.0);
		this.gestein.put(PokemonType.PFLANZE, 2.0);
		this.gestein.put(PokemonType.KAMPF, 2.0);
		this.gestein.put(PokemonType.GIFT, 0.5);
		this.gestein.put(PokemonType.BODEN, 2.0);
		this.gestein.put(PokemonType.FLUG, 0.5);
		table.put(PokemonType.GESTEIN, gestein);
		
		this.gift = new HashMap<PokemonType, Double>();
		this.gift.put(PokemonType.PFLANZE, 0.5);
		this.gift.put(PokemonType.KAMPF, 0.5);
		this.gift.put(PokemonType.GIFT, 0.5);
		this.gift.put(PokemonType.BODEN, 2.0);
		this.gift.put(PokemonType.PSYCHO, 2.0);
		this.gift.put(PokemonType.KAEFER, 2.0);
		table.put(PokemonType.GIFT, gift);
		
		this.kaefer = new HashMap<PokemonType, Double>();
		this.kaefer.put(PokemonType.FEUER, 2.0);
		this.kaefer.put(PokemonType.PFLANZE, 0.5);
		this.kaefer.put(PokemonType.KAMPF, 0.5);
		this.kaefer.put(PokemonType.GIFT, 2.0);
		this.kaefer.put(PokemonType.BODEN, 0.5);
		this.kaefer.put(PokemonType.FLUG, 2.0);
		this.kaefer.put(PokemonType.GESTEIN, 2.0);
		table.put(PokemonType.KAEFER, kaefer);
		
		this.kampf = new HashMap<PokemonType, Double>();
		this.kampf.put(PokemonType.FLUG, 2.0);
		this.kampf.put(PokemonType.PSYCHO, 2.0);
		this.kampf.put(PokemonType.KAEFER, 0.5);
		this.kampf.put(PokemonType.GESTEIN, 0.5);
		table.put(PokemonType.KAMPF, kampf);
		
		this.normal = new HashMap<PokemonType, Double>();
		this.normal.put(PokemonType.KAMPF, 2.0);
		this.normal.put(PokemonType.GEIST, 0.0);
		table.put(PokemonType.NORMAL, normal);
		
		this.pflanze = new HashMap<PokemonType, Double>();
		this.pflanze.put(PokemonType.FEUER, 2.0);
		this.pflanze.put(PokemonType.WASSER, 0.5);
		this.pflanze.put(PokemonType.PFLANZE, 0.5);
		this.pflanze.put(PokemonType.ELEKTRIZITAET, 0.5);
		this.pflanze.put(PokemonType.EIS, 2.0);
		this.pflanze.put(PokemonType.GIFT, 2.0);
		this.pflanze.put(PokemonType.BODEN, 0.5);
		this.pflanze.put(PokemonType.FLUG, 2.0);
		this.pflanze.put(PokemonType.KAEFER, 2.0);
		table.put(PokemonType.PFLANZE, pflanze);
		
		this.psycho = new HashMap<PokemonType, Double>();
		this.psycho.put(PokemonType.KAMPF, 0.5);
		this.psycho.put(PokemonType.PSYCHO, 0.5);
		this.psycho.put(PokemonType.KAEFER, 2.0);
		this.psycho.put(PokemonType.GEIST, 0.0);
		table.put(PokemonType.PSYCHO, psycho);
		
		this.wasser = new HashMap<PokemonType, Double>();
		this.wasser.put(PokemonType.FEUER, 0.5);
		this.wasser.put(PokemonType.WASSER, 0.5);
		this.wasser.put(PokemonType.PFLANZE, 2.0);
		this.wasser.put(PokemonType.ELEKTRIZITAET, 2.0);
		this.wasser.put(PokemonType.EIS, 0.5);
		table.put(PokemonType.WASSER, wasser);
	}

	
	
}
