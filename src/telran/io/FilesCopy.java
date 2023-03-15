package telran.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesCopy extends Copy {

	public FilesCopy(String srcFilePath, String destFilePath, boolean overwrite) {
		super(srcFilePath, destFilePath, overwrite);
	}

	@Override
	public long copy() {
		Path source = Path.of(srcFilePath);
		Path targer = Path.of(destFilePath);
		try {
			return Files.size(Files.copy(source, targer, StandardCopyOption.REPLACE_EXISTING));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
