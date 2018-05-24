package logic;

import javafx.animation.TimelineBuilder;

public class Item {
	
	private int id;
	private String name;
	private String effect;
	private String entity;
	private double rate;
	
	// Konstruktor
	
	public Item(int id, String name, String effect, String entity, double rate) {
		super();
		this.id = id;
		this.name = name;
		this.effect = effect;
		this.entity = entity;
		this.rate = rate;
	}
	
	//Getter + Setter
	
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
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	

}
