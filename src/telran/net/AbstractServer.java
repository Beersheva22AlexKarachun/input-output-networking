package telran.net;

import java.io.Closeable;

public abstract class AbstractServer implements Runnable {
	protected Protocol protocol;
	protected final int port;
	protected Closeable socket;

	public AbstractServer(Protocol protocol, int port) {
		this.protocol = protocol;
		this.port = port;
	}

	public AbstractServer(Protocol protocol, int port, Closeable socket) {
		this.protocol = protocol;
		this.port = port;
		this.socket = socket;
	}

	final public int getPort() {
		return port;
	}

}
