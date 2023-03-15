package telran.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Logger {
	private Level level = Level.INFO;
	private Handler handler;
	private String name;

	public Logger(Handler handler, String name) {
		this.handler = handler;
		this.name = name;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void error(String msg) {
		publish(msg, Level.ERROR);
	}

	public void warning(String msg) {
		publish(msg, Level.WARNING);
	}

	public void info(String msg) {
		publish(msg, Level.INFO);
	}

	public void debug(String msg) {
		publish(msg, Level.DEBUG);
	}

	public void trace(String msg) {
		publish(msg, Level.TRACE);
	}

	private LoggerRecord getNewLoggerRecord(String msg, Level level) {
		return new LoggerRecord(Instant.now(), ZoneId.systemDefault().toString(), level, name, msg);
	}

	private void publish(String msg, Level level) {
		if (this.level.ordinal() <= level.ordinal()) {
			handler.publish(getNewLoggerRecord(msg, level));
		}
	}
}
