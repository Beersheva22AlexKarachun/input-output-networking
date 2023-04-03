package telran.employees.application;

import java.util.*;

import telran.employees.*;
import telran.view.*;

public class CompanyApp {
	private static final String FILE_PATH = "company.txt";

	public static void main(String[] args) {
		Company company = new CompanyImpl();
		company.restore(FILE_PATH);
		Set<String> departments = new HashSet<String>(Arrays.stream(new String[] { "dep1", "dep2", "dep3" }).toList());
		CompanyControllerItems controller = new CompanyControllerItems(company, departments);
		Menu menu = new Menu("Company app", controller.getAdminMenu(), controller.getUserMenu(), Item.of("Exit", io -> {
			company.save(FILE_PATH);
			io.writeLine("Company has been saved");
		}, true));
		menu.perform(new StandardInputOutput());
	}
}
