package telran.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BufferCopy extends Copy {
	private int bufferSize;

	public BufferCopy(String srcFilePath, String destFilePath, boolean overwrite, int bufferSize) {
		super(srcFilePath, destFilePath, overwrite);
		this.bufferSize = bufferSize;
	}

	@Override
	public long copy() {
		long res = 0L;
		try (InputStream source = new FileInputStream(srcFilePath);
				OutputStream target = new FileOutputStream(srcFilePath)) {
			byte[] buffer = new byte[bufferSize];
			int length = source.read(buffer);
			while (length > 0) {
				target.write(buffer);
				res += length;
				length = source.read(buffer);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public DisplayResultBuffer getDisplayResult(long fileSize, long copyTime) {
		return new DisplayResultBuffer(fileSize, copyTime, bufferSize);
	}
}
