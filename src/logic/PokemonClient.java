package logic;

import java.util.function.Consumer;

import application.Main;
import tcp.TcpClient;

public class PokemonClient extends TcpClient {

	private Consumer<Map> onReceive;
	
	public PokemonClient(String host, int port) {
		super(host, port);
	}

	@Override
	public void onDisconnect() {
		System.out.println("Ich habe die Verbindung verloren");
	}

	@Override
	public void onReceive(Object obj) {
		if(this.getOnReceive() != null) {
			if(obj.getClass().equals(String.class))
				onReceive.accept(Main.xmlControll.getObject((String)obj));
		}
	}

	synchronized public Consumer<Map> getOnReceive() {
		return onReceive;
	}

	synchronized public void setOnReceive(Consumer<Map> onReceive) {
		this.onReceive = onReceive;
	}
	
}
