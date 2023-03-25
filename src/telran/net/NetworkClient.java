package telran.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

public interface NetworkClient extends Closeable {
	<T> T send(String type, Serializable requestData);
}
