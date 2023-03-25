package telran.employees.test;

import org.junit.jupiter.api.*;

import telran.employees.NetworkCompany;
import telran.net.TcpClient;
import static telran.employees.test.CompanyTestConstants.*;

import java.io.IOException;

class TcpCompanyTest extends CompanyTest {

	@BeforeAll
	static void getCompany() throws Exception {
		company = new NetworkCompany(new TcpClient(HOST, PORT));
	}

	@AfterAll
	static void closeConnection() throws IOException {
		((NetworkCompany) company).close();
	}
}
