package telran.net.application;

import telran.net.*;

public class ServerTcpExampleAppl {

	private static final int PORT = 4000;

	public static void main(String[] args) throws Exception {
		AbstractServer server = new UdpServer(new ExampleProtocol(), PORT);
		server.run();
	}
}