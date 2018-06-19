package logic;


import tcp.TcpServer;

public class PokemonServer extends TcpServer {

	public PokemonServer(int i) {
		super(i);
	}

	@Override
	public void onReceive(Handler handler, Object obj) {
		System.out.println(handler.getName() + " hat " + obj.getClass().getSimpleName() + " gesendet.");
	}

	@Override
	public void onDisconnect(Handler handler) {
		System.out.println(handler + " hat die Verbindung verloren bzw. geschlossen");
	}

	@Override
	public void run() {
		while(true){
			
		}
	}

}
