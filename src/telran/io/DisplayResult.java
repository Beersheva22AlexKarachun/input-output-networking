package telran.io;

public class DisplayResult {
	private long fileSize;
	private long copyTime;

	public DisplayResult(long fileSize, long copyTime) {
		this.fileSize = fileSize;
		this.copyTime = copyTime;
	}

	public String toString() {
		return String.format("file size: %d, copying time: %d; ", fileSize, copyTime);
	}

}
