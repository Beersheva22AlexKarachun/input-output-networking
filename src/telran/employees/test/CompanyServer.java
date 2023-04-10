package telran.employees.test;

import telran.net.*;

import static telran.employees.test.CompanyTestConstants.*;

import telran.employees.CompanyImpl;

public class CompanyServer {

	public static void main(String[] args) throws Exception {
		AbstractServer server = new UdpServer(new CompanyProtocol(new CompanyImpl()), PORT);
		server.run();
	}
}