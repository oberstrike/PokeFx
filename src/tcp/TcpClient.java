package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

 abstract public class TcpClient extends Thread{
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private boolean running = true;
	private boolean isConnected = false;
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public TcpClient(String host, int port) {
		try {
			socket = new Socket(host, port);
			this.input = new DataInputStream(socket.getInputStream());
			this.output = new DataOutputStream(socket.getOutputStream());
			isConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if(e.getClass().getSimpleName().equals("ConnectException")) {
				System.err.println("Keine Verbindung möglich.");
			}
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(running) {
			int length;
			try {
				length = this.input.readInt();
				if(length > 0) {
					byte[] message = new byte[length];
					input.readFully(message, 0, message.length);
					onReceive(Utilis.deserialize(message));
				}
			} catch (IOException e) {
				break;
			} catch (Exception e) {
				onDisconnect();
				e.printStackTrace();
				break;
			}
		}
	}
	
	public abstract void onDisconnect();
	public abstract void onReceive(Object obj);
	
	public boolean send(Object obj) {
		try {
			byte[] array = Utilis.serialize(obj);
			this.output.writeInt(array.length);
			this.output.write(array);
			this.output.flush();
			Thread.sleep(200);
			return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
