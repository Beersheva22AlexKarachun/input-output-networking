package telran.net;

import java.io.*;
import java.net.*;

public class TcpClient extends AbstractClient {
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public TcpClient(String hostname, int port) throws Exception {
		super(hostname, port, new Socket(hostname, port));
		output = new ObjectOutputStream(((Socket) socket).getOutputStream());
		input = new ObjectInputStream(((Socket) socket).getInputStream());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T send(String type, Serializable requestData) {
		Request request = new Request(type, requestData);
		T res = null;
		try {
			output.writeObject(request);
			Response response = (Response) input.readObject();
			if (response.code != ResponseCode.OK) {
				throw new Exception(response.data.toString());
			}
			res = (T) response.data;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return res;
	}
}