package xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import field.Field;
import pokemon.Pokemon;
import views.MapView;

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
		stream.processAnnotations(Pokemon.class);
		stream.allowTypeHierarchy(Collection.class);
		stream.allowTypeHierarchy(Field.class);
		stream.allowTypeHierarchy(Pokemon.class);
		stream.allowTypeHierarchy(Map.class);
		stream.allowTypeHierarchy(String.class);
		stream.allowTypeHierarchy(Integer.class);
		stream.alias("map", Map.class);
		
		pokedex = (List<Pokemon>) this.getObject(new File(pokeFileName));
		evolutiondex = (HashMap<String, HashMap<Integer, String>>) this.getObject(new File(evolveFileName));
	}
	
	public Pokemon getPokemonByName(String name) {
		return pokedex.stream().filter(each -> each.getName().equals(name)).findFirst().get();
	}

	@SuppressWarnings("unchecked")
	public MapView getMap(File file) {
		List<Field> fields = (List<Field>)getObject(file);
		fields.forEach(each -> each.applyImage());
		MapView mapView = new MapView();
		mapView.setField(fields);
		System.out.println(mapView.getFields());
		
		return mapView;
	}
	
	public Object getObject(File file) {
		return stream.fromXML(file);
	}
	
	public void saveObject(Object object, FileWriter writer) {
		this.stream.toXML(object,writer);
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void saveMap(MapView fields, FileWriter writer) {
		this.saveObject(fields.getFields(), writer);
	}

	public List<Pokemon> getPokedex() {
		return pokedex;
	}

	public HashMap<String, HashMap<Integer,String>> getEvolutiondex() {
		return evolutiondex;
	}

	
}
