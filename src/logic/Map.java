package logic;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import field.Field;
import field.FieldType;
import pokemon.Pokemon;
import pokemon.PokemonType;
@XStreamAlias("PokemonMap")
public class Map {

	private List<Field> fields = new ArrayList<Field>();

	@XStreamOmitField
	private double width = 600;

	@XStreamOmitField
	private double height = 500;

	@XStreamOmitField
	private double sides = 40;
	
	private List<Pokemon> pokemons = new ArrayList<>();
	
	public Map(List<Field> fields) {
		this.fields = fields;
	}
	
	public Map() {
		this(FieldType.GRASS);
	}
	
	public Map(FieldType fieldType) {
		for(int i = 0; i < width; i+=sides) {
			for(int j = 0; j < height; j+=sides) {
				fields.add(new Field(i,j,fieldType));
			}
		}
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public void update() {
		fields.stream().forEach(each -> {
			each.applyImage();
		});
	}

	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
}
