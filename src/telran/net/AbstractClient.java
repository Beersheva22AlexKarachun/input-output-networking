package telran.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;

public abstract class AbstractClient implements NetworkClient {
	protected String host;
	protected int port;
	protected Closeable socket;

	public AbstractClient(String host, int port, Closeable socket) {
		this.host = host;
		this.port = port;
		this.socket = socket;
	}

	public AbstractClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
