package pokemon;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("PokemonType")
public enum PokemonType {
	BODEN, DRACHE, EIS, 
	ELEKTRIZITAET, FEUER, 
	FLUG, GEIST, GESTEIN, GIFT, 
	KAEFER, KAMPF, NORMAL, 
	PFLANZE, PSYCHO, WASSER;
}
