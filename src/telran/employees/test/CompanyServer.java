package telran.employees.test;

import telran.net.*;

import static telran.employees.test.CompanyTestConstants.*;

public class CompanyServer {

	public static void main(String[] args) throws Exception {
		AbstractServer server = new UdpServer(new CompanyProtocol(), PORT);
		server.run();
	}
}