package telran.employees.application;

import java.time.LocalDate;
import java.util.*;

import telran.employees.*;
import telran.view.*;

public class CompanyControllerItems {
	private static final String ERROR_MSG = "Wrong input";
	private static final String DATE_FORMAT = "dd.MM.yyyy";
	private Company company;
	private Set<String> departments;

	public CompanyControllerItems(Company company, Set<String> departments) {
		this.company = company;
		this.departments = departments;
	}

	public Menu getAdminMenu() {
		return new Menu("Admin Menu", Item.of("Add Employee", this::addEmployee),
				Item.of("Remove Employee", this::removeEmployee), Item.exit());
	}

	private void addEmployee(InputOutput io) {
		Employee empl = requestEmployee(io);
		company.addEmployee(empl);
		io.writeLine(String.format("%s has been added", empl));
	}

	private void removeEmployee(InputOutput io) {
		long id = io.readLong("Enter an employee's ID", ERROR_MSG, 0, Long.MAX_VALUE);
		Employee removed = company.removeEmployee(id);
		io.writeLine(removed != null ? removed + " has been removed" : "There is no employee with id#" + id);
	}

	private Employee requestEmployee(InputOutput io) {
		long id = getNewID(io);

		String name = io.readString("Enter the employee's name");

		LocalDate birthDate = io.readDate("Enter the employee's date of birth in format " + DATE_FORMAT.toLowerCase(),
				ERROR_MSG, DATE_FORMAT, LocalDate.of(1900, 1, 1), LocalDate.now());

		String department = getDepartment(io);
		int salary = io.readInt("Enter a salary", ERROR_MSG);
		return new Employee(id, name, birthDate, department, salary);
	}

	private long getNewID(InputOutput io) {
		long res = io.readLong("Enter the employee's ID", ERROR_MSG, 0, Long.MAX_VALUE);
		while (company.getEmployee(res) != null) {
			res = io.readLong("Employee with ID#" + res + " is exists. Enter another ID", ERROR_MSG, 0, Long.MAX_VALUE);
		}
		return res;
	}

	private String getDepartment(InputOutput io) {
		io.writeLine("Choose a department");
		String[] deps = departments.stream().sorted().toArray(String[]::new);
		for (int i = 0; i < deps.length; i++) {
			io.writeLine(String.format("%d - %s\n", i + 1, deps[i]));
		}
		return deps[io.readInt("Enter a number of department", ERROR_MSG, 1, departments.size()) - 1];

	}

	public Menu getUserMenu() {
		return new Menu("User Menu", Item.of("Get Employee", this::getEmployee),
				Item.of("Get all Employees", this::getAllEmployees),
				Item.of("Get Employees by month of birth", this::getEmployeesByMonth),
				Item.of("Get Employees by salary", this::getEmployeesBySalary),
				Item.of("Get Employees by department", this::getEmployeesByDepartment), Item.exit());
	}

	private void getEmployee(InputOutput io) {
		long id = io.readLong("Enter an employee's ID", ERROR_MSG, 0, Long.MAX_VALUE);
		Employee emlp = company.getEmployee(id);
		io.writeLine(emlp != null ? emlp : String.format("There is no employee with id#%d in the company", id));
	}

	private void getAllEmployees(InputOutput io) {
		writeEmployees(io, company.getAllEmployees());
	}

	private void getEmployeesByMonth(InputOutput io) {
		int month = io.readInt("Enter number of month", ERROR_MSG, 1, 12);
		writeEmployees(io, company.getEmployeesByMonthBirth(month));
	}

	private void getEmployeesBySalary(InputOutput io) {
		int salaryFrom = io.readInt("Enter a lower value of salary", ERROR_MSG);
		int salaryTo = io.readInt("Enter a upper value of salary", ERROR_MSG, salaryFrom, Integer.MAX_VALUE);
		writeEmployees(io, company.getEmployeesBySalary(salaryFrom, salaryTo));
	}

	private void getEmployeesByDepartment(InputOutput io) {
		String department = getDepartment(io);
		writeEmployees(io, company.getEmployeesByDepartment(department));
	}

	private void writeEmployees(InputOutput io, List<Employee> empls) {
		Collections.sort(empls);
		empls.stream().forEach(empl -> io.writeLine(empl));
	}
}
