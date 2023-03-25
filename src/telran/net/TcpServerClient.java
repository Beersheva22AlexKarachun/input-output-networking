package telran.net;

import java.io.*;
import java.net.*;

public class TcpServerClient extends AbstractServer {
	private ObjectInputStream input;
	private ObjectOutputStream output;

	public TcpServerClient(Socket socket, Protocol protocol) throws IOException {
		super(protocol, 0, socket);
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			try {
				Request request = (Request) input.readObject();
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (EOFException e) {
				System.out.println("client closed connection");
				running = false;
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
	}

}