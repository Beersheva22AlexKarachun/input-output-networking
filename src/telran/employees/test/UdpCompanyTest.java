package telran.employees.test;

import org.junit.jupiter.api.*;

import telran.employees.NetworkCompany;
import telran.net.UdpClient;
import static telran.employees.test.CompanyTestConstants.*;

import java.io.IOException;

class UdpCompanyTest extends CompanyTest {

	@BeforeAll
	static void getCompany() throws Exception {
		company = new NetworkCompany(new UdpClient(HOST, PORT));
	}

	@AfterAll
	static void closeConnection() throws IOException {
		((NetworkCompany) company).close();
	}
}
