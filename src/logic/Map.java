package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import com.sun.javafx.geom.Vec2d;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import application.Main;
import field.Field;
import field.FieldType;
import player.Player;
import pokemon.Pokemon;

@XStreamAlias("PokemonMap")
public class Map implements Serializable {
	private static final long serialVersionUID = -1775940921375005636L;

	private Vector<Field> fields = new Vector<>();

	@XStreamOmitField
	private double width = 600;

	@XStreamOmitField
	private double height = 500;

	@XStreamOmitField
	private static final double sides = 30;

	private List<Pokemon> pokemons = new ArrayList<>();

	public Map(Vector<Field> fields) {
		this.fields = fields;
	}

	public Map() {
		this(FieldType.GRASS);
	}

	public Map(FieldType fieldType) {

		for (int i = 0; i < width; i += sides) {
			for (int j = 0; j < height; j += sides) {
				fields.add(new Field(i, j, fieldType));
			}
		}
	}

	public Player getPlayer() {
		return (Player) fields.stream().filter(each -> each.getEntity() != null)
				.filter(each -> each.getEntity().getClass().equals(Player.class)).findFirst().get().getEntity();

	}

	public List<Field> getSuccesors(Field field) {
		List<Optional<Field>> oList = new ArrayList<>();
		oList.add(leftField(field));
		oList.add(rightField(field));
		oList.add(upField(field));
		oList.add(bottomField(field));
		return oList.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
	}

	public double distanceTo(Field field1, Field field2) {
		Vec2d v1 = new Vec2d(field1.getX(), field1.getY());
		Vec2d v2 = new Vec2d(field2.getX(), field2.getY());
		return v1.distance(v2);
	}

	public Optional<Field> leftField(Field field) {
		return getFieldWithCoordinates((field.getX() - 30), field.getY());
	}

	public Optional<Field> rightField(Field field) {
		return getFieldWithCoordinates((field.getX() + 30), field.getY());
	}

	public Optional<Field> bottomField(Field field) {
		return getFieldWithCoordinates(field.getX(), (field.getY() + 30));
	}

	public Optional<Field> upField(Field field) {
		return getFieldWithCoordinates(field.getX(), (field.getY() - 30));
	}

	public Optional<Field> getFieldWithCoordinates(double x, double y) {
		System.out.println(x + " " + y);
		Optional<Field> ofield = Optional.empty();

		for (Field field : getFields()) {
			if (field.getX() == x && field.getY() == y)
				ofield = Optional.of(field);
		}
		return ofield;

	}

	public Vector<Field> getFields() {
		return fields;
	}

	public void setFields(Vector<Field> fields) {
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
