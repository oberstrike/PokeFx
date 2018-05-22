package logic;

import java.util.function.Consumer;

public class GenericBuilder<T> {

	private T instance;
	
	public GenericBuilder(Class<T> type){
		try {
			instance = type.newInstance();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GenericBuilder<T> apply(Consumer<T> consumer) {
		consumer.accept(instance);
		return this;
	}
	
	public T build() {
		return instance;
	}
	
	
}
