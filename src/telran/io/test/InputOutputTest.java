package telran.io.test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class InputOutputTest {
	static final String OFFSET = " ".repeat(3);
	static final String FOLDER = ".";
	static final int MAX_LEVEL = 0;

	String fileName = "myFile";
	String directoryName = "myDirectory1/myDirectory2";

	@BeforeEach
	void setUp() throws Exception {
		new File(fileName).delete();
		new File(directoryName).delete();
	}

	@Test
	@Disabled
	void testFile() throws IOException {
		File f1 = new File(fileName);
		assertTrue(f1.createNewFile());
		File dir1 = new File(directoryName);
		assertTrue(dir1.mkdirs());
		System.out.println(dir1.getAbsolutePath());
	}

	@Test
//	@Disabled
	void printDirectoryFileTest() throws IOException {
		printDirectoryFile(FOLDER, MAX_LEVEL);
	}

	void printDirectoryFile(String directory, int maxLevel) throws IOException {
		File dirFile = new File(directory).getCanonicalFile();
		maxLevel = maxLevel > 0 ? maxLevel : Integer.MAX_VALUE;
		printDirectoryFile(dirFile, 0, maxLevel);
	}

	void printDirectoryFile(File file, int level, int maxLevel) {
		printFile(file.getName(), file.isDirectory(), level);
		if (level < maxLevel && file.isDirectory()) {
			for (File file1 : file.listFiles()) {
				printDirectoryFile(file1, level + 1, maxLevel);
			}
		}
	}

	private void printFile(String fileName, boolean isDir, int level) {
		String indent = OFFSET + "|";
		System.out.printf("|%s- %s - %s\n", indent.repeat(level), fileName, isDir ? "dir" : "file");
	}

	@Test
//	@Disabled
	void testFiles() {
		Path path = Path.of(".");
		System.out.println(path.toAbsolutePath().getNameCount());
	}

	@Test
//	@Disabled
	void printDirectoryFilesTest() throws IOException {
		printDirectoryFiles(FOLDER, MAX_LEVEL);
	}

	void printDirectoryFiles(String directory, int maxLevel) throws IOException {
		Path dirPath = Path.of(directory).toAbsolutePath().normalize();
		int dirPathCount = dirPath.getNameCount();
		maxLevel = maxLevel > 0 ? maxLevel : Integer.MAX_VALUE;

		Files.walk(dirPath, maxLevel).forEach(path -> printFile(path.getFileName().toString(), Files.isDirectory(path),
				path.getNameCount() - dirPathCount));
	}
}
