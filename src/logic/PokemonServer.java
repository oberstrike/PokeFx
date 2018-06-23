package logic;


import java.net.InetAddress;

import tcp.TcpServer;

public class PokemonServer extends TcpServer {

	public PokemonServer(int port, InetAddress inetAddress) {
		super(inetAddress, port);
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
