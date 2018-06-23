package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

abstract public class TcpServer extends Thread {

	//Abstrakte Klasse fuer das Aufbauen eines Servers
	
	private Vector<Handler> listOfHandler;
	private ExecutorService executor;
	private ServerSocket serverSocket;
	private Thread listener;
	
	public void shutdown() {
		for(Handler handler: listOfHandler)
			handler.setRuns(false);
		executor.shutdownNow();
	}

	/***
	 * Creates a TCP Server on Port 9999. (Standard Port)
	 * @throws UnknownHostException 
	 */
	
	public TcpServer() throws UnknownHostException {
		this(Inet4Address.getLocalHost(), 9999);
	}

	/***
	 * Creates a TCP Server on Port with max 10 Clients
	 * @param port : Integer
	 */
	
	public TcpServer(InetAddress inetAddress, int port) {
		listOfHandler = new Vector<>();
		executor = Executors.newFixedThreadPool(10);
		listener = new Thread(() -> {
			try {
				serverSocket = new ServerSocket(port, 50, inetAddress);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (true) {
				Socket client;
				try {
					client = serverSocket.accept();
					Handler handler = new Handler(client);
					executor.execute(handler);
					listOfHandler.addElement(handler);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		});
		listener.setDaemon(true);
		executor.execute(listener);
	}
	
	
	public abstract void onReceive(Handler handler, Object obj);

	public abstract void onDisconnect(Handler handler);

	public abstract void run();
	
	public void addHandler(Handler handler) {
		listOfHandler.addElement(handler);
	}
	
	public void sendAll(Serializable obj) {
		for (Handler handler : listOfHandler) {
			this.send(handler, obj);
		}
	}

	public boolean send(Handler handler, Object obj) {
		try {
			handler.send(obj);
			return true;
		} catch (IOException e) {
			this.getListOfHandler().remove(handler);
			return false;
		}
	} 

	public Handler getHandler(SocketAddress address) {
		for (Handler handler : listOfHandler) {
			if (handler.handlerClient.getRemoteSocketAddress().equals(address)) {
				return handler;
			}
		}
		return null;
	}
	public class Handler extends Thread {

		private Socket handlerClient;
		private DataInputStream input;
		private DataOutputStream output;
		private UUID handlerId;
		private boolean runs = true;

		public Socket getHandlerClient() {
			return this.handlerClient;
		}

		public Handler(Socket client) {
			this.setDaemon(true);
			this.handlerClient = client;
			this.setHandlerId(UUID.randomUUID());
			try {
				this.input = new DataInputStream(handlerClient.getInputStream());
				this.output = new DataOutputStream(handlerClient.getOutputStream());
			} catch (IOException e) {
				System.out.println(e.getClass().getSimpleName());

			}
		}

		public void send(Object obj) throws IOException {
			this.output.writeInt(Utilis.serialize(obj).length);
			this.output.write(Utilis.serialize(obj));
			this.output.flush();
		}

		@Override
		public void run() {
			while (runs) {
				try {
					int length = this.input.readInt();
					if (length > 0) {
						byte[] message = new byte[length];
						input.readFully(message, 0, message.length);
						Object obj = Utilis.deserialize(message);
						onReceive(this, obj);				
					}
				} catch (IOException e) {
					if (e.getClass().getSimpleName().equals("SocketException")) {
						onDisconnect(this);
						break;
					}
				}
			}
		}

		public UUID getHandlerId() {
			return handlerId;
		}

		public void setHandlerId(UUID uuid) {
			this.handlerId = uuid;
		}

		public boolean isRuns() {
			return runs;
		}

		public void setRuns(boolean runs) {
			this.runs = runs;
		}

		public TcpServer getOuterType() {
			return TcpServer.this;
		}
		
	}

	public List<Handler> getListOfHandler() {
		return listOfHandler;
	}

	public void setListOfHandler(Vector<Handler> listOfHandler) {
		this.listOfHandler = listOfHandler;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}


}
