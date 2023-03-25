package telran.net;

import java.net.*;

public class TcpServer extends AbstractServer {

	public TcpServer(Protocol protocol, int port) throws Exception {
		super(protocol, port, new ServerSocket(port));
	}

	@Override
	public void run() {
		System.out.println("Server listening on port " + this.port);
		while (true) {
			try {
				Socket clientSocket = ((ServerSocket) socket).accept();
				TcpServerClient serverClient = new TcpServerClient(clientSocket, protocol);
				serverClient.run();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

}