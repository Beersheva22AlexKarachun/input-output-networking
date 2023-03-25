package telran.net.application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.*;

import telran.net.*;

class NetworkTest {
	static NetworkClient client;
	static String[] strings = { "Hello", "World", "Java", "TcpServer", "1231qtuwoiffo" };

	@BeforeAll
	static void connection() throws Exception {
		client = new UdpClient("localhost", 4000);
	}

	@Test
	void resersTest() {
		Arrays.stream(strings)
				.forEach(str -> assertEquals(new StringBuilder(str).reverse().toString(), client.send("reverse", str)));
	}

	@Test
	void lengthTest() {
		Arrays.stream(strings).forEach(str -> assertEquals(Integer.valueOf(str.length()), client.send("length", str)));
	}

	@AfterAll
	static void disconnection() throws IOException {
		client.close();
	}

}