package telran.util;

public interface Handler {
	public void publish(LoggerRecord logRec);

	default void close() {
	};
}
