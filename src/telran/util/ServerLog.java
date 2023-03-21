package telran.util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerLog implements Runnable {
	private int port = 8189;
	private Map<Level, Integer> logs = new HashMap<>();

	public ServerLog(int port) {
		this.port = port;
	}

	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getLocalAddress() + " connected");
				runServerClient(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void runServerClient(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream writer = new PrintStream(socket.getOutputStream());
		while (true) {
			String request = reader.readLine();
			if (request == null) {
				break;
			}
			writer.println(getResponse(request));
		}
		System.out.println("client closed connection");
	}

	private String getResponse(String request) {
		String res = "Wrong Request";
		String tokens[] = request.split("#");
		if (tokens.length == 2) {
			res = switch (tokens[0]) {
			case "log" -> getLevel(tokens[1].replaceAll("\\s", "")) ? "OK" : "NOT OK";
			case "counter" -> getLogsCounter(tokens[1]);
			default -> "Wrong type " + tokens[0];
			};
		}
		return res;
	}

	private String getLogsCounter(String lvl) {
		try {
			Level level = Level.valueOf(lvl.toUpperCase());
			return logs.getOrDefault(level, 0).toString();
		} catch (IllegalArgumentException e) {
			return "Illegal Argument";
		}
	}

	private boolean getLevel(String logRec) {
		String tokens[] = logRec.split("\\|");
		for (String str : tokens) {
			if (str.startsWith("Level:")) {
				try {
					Level level = Level.valueOf(str.split(":")[1]);
					logs.merge(level, 1, Integer::sum);
					return true;
				} catch (IllegalArgumentException e) {
					System.out.println(e);
				}
			}
		}
		return false;
	}

}
