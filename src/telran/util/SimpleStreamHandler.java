package telran.util;

import java.io.PrintStream;

public class SimpleStreamHandler implements Handler {
	private PrintStream stream;

	@Override
	public void publish(LoggerRecord logRec) {

		stream.println(logRec);
	}

	public SimpleStreamHandler(PrintStream stream) {
		this.stream = stream;
	}
}
