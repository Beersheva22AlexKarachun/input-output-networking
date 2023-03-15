package telran.util.test;

import org.junit.jupiter.api.*;

import telran.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoggerTest {
	Handler handler = new SimpleStreamHandler(System.out);
	Logger log = new Logger(handler, LoggerTest.class.getName());

	@AfterEach
	void logging() {
		log.error("error was rised");
		log.warning("warning was rised");
		log.info("info was rised");
		log.debug("debug was rised");
		log.trace("trace was rised");
		System.out.println();
	}

	@Test
	@Order(1)
	void defaultInfoTest() {
		System.out.println("---Default info Level---");
	}

	@Test
	void errorTest() {
		log.setLevel(Level.ERROR);
		System.out.println("---Error Level---");
	}

	@Test
	void warningTest() {
		log.setLevel(Level.WARNING);
		System.out.println("---Warning Level---");
	}

	@Test
	void infoTest() {
		log.setLevel(Level.INFO);
		System.out.println("---Info Level---");
	}

	@Test
	void debugTest() {
		log.setLevel(Level.DEBUG);
		System.out.println("---Debug Level---");
	}

	@Test
	void traceTest() {
		log.setLevel(Level.TRACE);
		System.out.println("---Trace Level---");
	}

}
