package resource;

import java.io.File;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import field.Field;
import logic.Map;
import logic.Trainer;
import player.Player;
import pokemon.Pokemon;
import pokemon.PokemonType;
import xml.GameData;

public class XmlControll {
	

	private XStream stream;
	private final List<Pokemon> pokedex;
	private final HashMap<String, HashMap<Integer, String>> evolutiondex;
	private final HashMap<PokemonType, HashMap<PokemonType, Double>> effectives;

	private static String pokeFileName = "pokedex.xml";
	private static String evolveFileName = "evolvingdex.xml";
	private static String effectivesFileName = "effectives.xml";

	@SuppressWarnings("unchecked")
	public XmlControll() {
		Class<?>[] arrayOfClasses = {Field.class, Map.class, PokemonType.class, Player.class,
				Trainer.class, Collection.class, java.util.Map.class, Integer.class, GameData.class, Pokemon.class, String.class, Vector.class};
		stream = new XStream(new StaxDriver());
		stream.addPermission(NoTypePermission.NONE);
		stream.addPermission(NullPermission.NULL);
		stream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		
		for(Class<?> cl: arrayOfClasses) {
			stream.processAnnotations(cl);
			stream.allowTypeHierarchy(cl);
		}
		
		stream.alias("map", java.util.Map.class);
		stream.alias("list", ArrayList.class);
		
		InputStream is1 = this.getClass().getResourceAsStream(pokeFileName);
		InputStream is2 = this.getClass().getResourceAsStream(evolveFileName);
		InputStream is3 = this.getClass().getResourceAsStream(effectivesFileName);
		
		
		pokedex = (List<Pokemon>) this.getObject(is1);
		evolutiondex = (HashMap<String, HashMap<Integer, String>>) this.getObject(is2);
		effectives = (HashMap<PokemonType, HashMap<PokemonType, Double>>) stream.fromXML(is3);

	}

	public Pokemon getPokemonByName(String name) {
		if (pokedexContains(name))
			return new Pokemon(pokedex.stream().filter(each -> each.getName().equals(name)).findFirst().get());
		return null;
	}

	public boolean pokedexContains(String name) {
		return pokedex.stream().anyMatch(each -> each.getName().equals(name));
	}

	public List<Pokemon> getPokemonsByType(PokemonType type) {
		List<Pokemon> pokemons = pokedex.stream().filter(each -> each.getType().equals(type))
				.collect(Collectors.toList());
		pokemons.forEach(each -> new Pokemon(each));
		return pokemons;
	}

	public Map getMap(File file) {
		Map map = (Map) getObject(file);
		map.getFields().forEach(each -> each.applyImage());
		return map;
	}

	public GameData getGameData(File file) {
		GameData data = (GameData) getObject(file);
		return data;
	}

	public Object getObject(File file) {
		return stream.fromXML(file);
	}
	
	public Object getObject(InputStream is) {
		return stream.fromXML(is);
	}

	public void saveGameData(GameData gameData, FileWriter writer) {
		this.saveObject(gameData, writer);
	}

	public void saveObject(Object object, FileWriter writer) {
		String string = this.stream.toXML(object);
		try {
			writer.write(string);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMap(Map map, FileWriter writer) {
		this.saveObject(map, writer);
	}

	public List<Pokemon> getPokedex() {
		return pokedex;
	}

	public HashMap<String, HashMap<Integer, String>> getEvolutiondex() {
		return evolutiondex;
	}

	public Pokemon getPokemonsById(int id) {
		return new Pokemon(pokedex.get(id - 1));
	}

	public HashMap<PokemonType, HashMap<PokemonType, Double>> getEffectives() {
		return effectives;
	}

	public Serializable toXml(Object obj) {
		try {
			return stream.toXML(obj);
		}catch (Exception e) {
			return null;
		}
	}

	public Map getObject(String xml) {
		try {
			return (Map) stream.fromXML(xml);
		}catch (Exception e) {
			return null;
		}
	}

}
