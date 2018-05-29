package design;

import java.util.HashMap;

public class HashMapBuilder {

	private HashMap<String, Object> hashMap;
	
	public HashMapBuilder() {
		hashMap = new HashMap<>();
	}
	
	public HashMapBuilder add(String string, Object object) {
		this.hashMap.put(string, object);
		return this;
	}
	
	public HashMap<String, Object> build(){
		return hashMap;
	}
	
	
}
