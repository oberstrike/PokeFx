package design;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

public final class Utilis {
	
	public static byte[] serialize(Object obj)  {
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo;
		try {
			oo = new ObjectOutputStream(bStream);
			oo.writeObject(obj);
			oo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bStream.toByteArray();
	}
	
	public static Object deserialize(DatagramPacket packet) {
		ObjectInputStream iStream = null;
		try {
			iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
			return iStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object deserialize(byte[] b) {
		ObjectInputStream iStream = null;
		try {
			iStream = new ObjectInputStream(new ByteArrayInputStream(b));
			return iStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


}
