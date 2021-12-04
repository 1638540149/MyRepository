package qq.util;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketUtil {

	private static SocketUtil socketUtil=null;
	
	private SocketUtil() {
		
	}
	
	public static SocketUtil getSocketUtil() {
		if(socketUtil==null) {
			socketUtil=new SocketUtil();
		}
		return socketUtil;
	}
	
	public void writeMessage(Socket socket,Message requestMessage) throws Exception{
		OutputStream outputStream=socket.getOutputStream();
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
		objectOutputStream.writeObject(requestMessage);
	}
	
	public Message readMessage(Socket socket) throws Exception{
		InputStream inputStream=socket.getInputStream();
		ObjectInputStream objectInputStream=new ObjectInputStream(inputStream);
		Message responseMessage=(Message)objectInputStream.readObject();
		return responseMessage;
	}
}
