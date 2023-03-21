package telran.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClientHandler implements Handler {
	private PrintStream output;
	private BufferedReader input;
	private Socket socket;

	@Override
	public void publish(LoggerRecord logRec) {
		output.println("log#" + logRec);
	}

	public TcpClientHandler(String host, int port) {
		try {
			socket = new Socket(host, port);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
