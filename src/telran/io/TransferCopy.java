package telran.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferCopy extends Copy {

	public TransferCopy(String srcFilePath, String destFilePath, boolean overwrite) {
		super(srcFilePath, destFilePath, overwrite);
	}

	@Override
	public long copy() {
		try (InputStream source = new FileInputStream(srcFilePath);
				OutputStream target = new FileOutputStream(destFilePath)) {
			return source.transferTo(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
