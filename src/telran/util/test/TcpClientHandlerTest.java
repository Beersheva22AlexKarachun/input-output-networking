package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.jupiter.api.*;

import telran.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TcpClientHandlerTest {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 8189;
	private static final int AMOUNTS_OF_ERROR = 4;
	private static final int AMOUNTS_OF_WARNING = 3;
	private static final int AMOUNTS_OF_INFO = 2;
	private static final int AMOUNTS_OF_DEBUG = 1;
	private static final int AMOUNTS_OF_TRACE = 0;

	static Handler handler;
	static Logger log;
	static ServerLog server;
	static Thread serverThread;
	static Socket socket;

	@BeforeAll
	static void setUp() {
		server = new ServerLog(PORT);
		serverThread = new Thread(server);
		serverThread.start();
		handler = new TcpClientHandler(HOST, PORT);
		log = new Logger(handler, LoggerTest.class.getName());
		log.setLevel(Level.TRACE);
	}

	@AfterAll
	static void close() throws IOException {
		handler.close();
		socket.close();
		serverThread.stop();
	}

	@Test
	@Order(1)
	void serverTest() throws IOException {
		for (int i = 0; i < AMOUNTS_OF_ERROR; i++) {
			log.error("Error msg");
		}
		for (int i = 0; i < AMOUNTS_OF_WARNING; i++) {
			log.warning("Warning msg");
		}
		for (int i = 0; i < AMOUNTS_OF_INFO; i++) {
			log.info("Info msg");
		}
	}

	@Test
	@Order(2)
	void clientTest() throws IOException {
		handler.close();

		socket = new Socket(HOST, PORT);
		PrintStream writer = new PrintStream(socket.getOutputStream());
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		writer.println("log#wronglog");
		assertEquals("NOT OK", reader.readLine());

		writer.println(
				"log#21.03.202314:20:49:2049Asia/Jerusalem|Logger:telran.util.test.LoggerTest|Level:DEBUG|Message:Tracemsg");
		assertEquals("OK", reader.readLine());

		checkLogsCounter("error", AMOUNTS_OF_ERROR, reader, writer);
		checkLogsCounter("warning", AMOUNTS_OF_WARNING, reader, writer);
		checkLogsCounter("info", AMOUNTS_OF_INFO, reader, writer);
		checkLogsCounter("debug", AMOUNTS_OF_DEBUG, reader, writer);
		checkLogsCounter("trace", AMOUNTS_OF_TRACE, reader, writer);

	}

	private void checkLogsCounter(String log, int expectedCounter, BufferedReader reader, PrintStream writer)
			throws IOException {
		writer.println("counter#" + log);
		assertEquals(String.valueOf(expectedCounter), reader.readLine());
	}
}
