package xml;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import field.Field;
import logic.Map;
import pokemon.Pokemon;
import pokemon.PokemonType;

public class XmlControll {
	
	XStream stream;
	private List<Pokemon> pokedex;
	private HashMap<String, HashMap<Integer,String>> evolutiondex;
	
	private static String pokeFileName = "pokedex.xml";
	private static String evolveFileName = "evolvingdex.xml";
	
	@SuppressWarnings("unchecked")
	public XmlControll() {
		stream = new XStream(new StaxDriver());
		stream.addPermission(NoTypePermission.NONE);
		stream.addPermission(NullPermission.NULL);
		stream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		stream.processAnnotations(Field.class);
		stream.processAnnotations(Map.class);
		stream.processAnnotations(Pokemon.class);
		stream.allowTypeHierarchy(Collection.class);
		stream.allowTypeHierarchy(Field.class);
		stream.allowTypeHierarchy(Pokemon.class);
		stream.allowTypeHierarchy(PokemonType.class);
		stream.allowTypeHierarchy(java.util.Map.class);
		stream.allowTypeHierarchy(String.class);
		stream.allowTypeHierarchy(Integer.class);
		stream.alias("map", java.util.Map.class);
		stream.allowTypeHierarchy(Map.class);
		
		pokedex = (List<Pokemon>) this.getObject(new File(pokeFileName));
		evolutiondex = (HashMap<String, HashMap<Integer, String>>) this.getObject(new File(evolveFileName));
	}
	
	public Pokemon getPokemonByName(String name) {
		return pokedex.stream().filter(each -> each.getName().equals(name)).findFirst().get();
	}
	
	public List<Pokemon> getPokemonsByType(PokemonType type){
		return pokedex.stream().filter(each -> each.getType().equals(type)).collect(Collectors.toList());
	}

	public Map getMap(File file) {
		Map map = (Map)getObject(file);
		map.getFields().forEach(each -> each.applyImage());
		return map;
	}
	
	public Object getObject(File file) {
		return stream.fromXML(file);
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

	public HashMap<String, HashMap<Integer,String>> getEvolutiondex() {
		return evolutiondex;
	}

	
}
