package telran.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class IoTest {
	InputOutput io = new StandardInputOutput();

	@Test
	void test() {
		Set<String> departments = new HashSet<>();

		departments.add("dep3");
		departments.add("dep2");
		departments.add("dep1");

		StringBuilder departmentMsg = new StringBuilder("Enter a number of department:\n");
		String[] deps = departments.stream().sorted().toArray(String[]::new);
		for (int i = 0; i < deps.length; i++) {
			departmentMsg.append(String.format("%d - %s\n", i + 1, deps[i]));
		}

		System.out.println(departmentMsg);
	}

}
