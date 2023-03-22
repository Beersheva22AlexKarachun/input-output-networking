package telran.net;

import java.io.*;
import java.net.*;

public class TcpClient implements NetworkClient {
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public TcpClient(String hostname, int port) throws Exception {
		socket = new Socket(hostname, port);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

	@Override
	public <T> T send(String type, Serializable requestData) {
		T res = null;
		Request request = new Request(type, requestData);
		try {
			output.writeObject(request);
			Response response = (Response) input.readObject();
			
			if (!response.code.equals(ResponseCode.OK)) {
				throw new RuntimeException("Respinse from server is " + response.code);
			}
			
			res = (T) response.data;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

}