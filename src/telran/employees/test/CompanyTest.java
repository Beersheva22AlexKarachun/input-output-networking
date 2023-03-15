package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.employees.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyTest {
	Company company;
	String filePath = "company.txt";
	int[] expectedEmplsIndexesByMonth = { 2, 3 };
	int[] expectedEmplsIndexesBySalary = { 0, 3, 4 };
	int[] expectedEmplsIndexesByDepartment = { 3, 4 };

	Employee[] empls = { new Employee(10, "John", LocalDate.of(1990, 10, 13), "accounts", 12500),
			new Employee(15, "Rebecca", LocalDate.of(2000, 7, 6), "accounts", 9500),
			new Employee(17, "Yuri", LocalDate.of(1999, 3, 26), "HR", 5000),
			new Employee(22, "Alex", LocalDate.of(1980, 3, 20), "development", 10800),
			new Employee(26, "Kira", LocalDate.of(1987, 1, 3), "development", 11000),
			new Employee(31, "Madison", LocalDate.of(2002, 12, 8), "sales", 15000) };

	@BeforeEach
	void setUp() {
		company = new CompanyImpl();
		Arrays.stream(empls).forEach(company::addEmployee);
	}

	@Test
	void companySerrializeTest() {
	}

	@Test
	void addTest() {
		Employee newEmpl = new Employee(100, "Sara", LocalDate.of(1990, 10, 13), "sales", 7000);
		assertNull(company.getEmployee(newEmpl.getId()));
		assertTrue(company.addEmployee(newEmpl));
		assertEquals(newEmpl, company.getEmployee(newEmpl.getId()));
	}

	@Test
	void removeTest() {
		Employee removedImpl = empls[0];
		assertEquals(removedImpl, company.removeEmployee(removedImpl.getId()));
		assertNull(company.getEmployee(removedImpl.getId()));
	}

	@Test
	void getAllTest() {
		List<Employee> list = company.getAllEmployees();
		assertEquals(list.size(), empls.length);
		list.stream().forEach(empl -> company.getEmployee(empl.getId()));
	}

	@Test
	void getTest() {
		Arrays.stream(empls).forEach(empl -> assertEquals(company.getEmployee(empl.getId()), empl));
	}

	@Test
	void getEmployeesByMonthBirthTest() {
		Map<Long, Employee> expectedEmpls = getEmployees(expectedEmplsIndexesByMonth);
		List<Employee> list = company.getEmployeesByMonthBirth(3);

		assertEquals(expectedEmpls.size(), list.size());
		list.stream().forEach(empl -> assertEquals(empl, expectedEmpls.get(empl.getId())));
	}

	@Test
	void getEmployeesBySalaryTest() {
		Map<Long, Employee> expectedEmpls = getEmployees(expectedEmplsIndexesBySalary);
		List<Employee> list = company.getEmployeesBySalary(10500, 13000);

		assertEquals(expectedEmpls.size(), list.size());
		list.stream().forEach(empl -> assertEquals(empl, expectedEmpls.get(empl.getId())));
	}

	@Test
	void getEmployeesByDepartmentTest() {
		Map<Long, Employee> expectedEmpls = getEmployees(expectedEmplsIndexesByDepartment);
		List<Employee> list = company.getEmployeesByDepartment("development");

		assertEquals(expectedEmpls.size(), list.size());
		list.stream().forEach(empl -> assertEquals(empl, expectedEmpls.get(empl.getId())));
	}

	private Map<Long, Employee> getEmployees(int[] indexes) {
		Map<Long, Employee> expectedEmpls = new HashMap<>();
		Arrays.stream(indexes).forEach(index -> expectedEmpls.put(empls[index].getId(), empls[index]));
		return expectedEmpls;
	}

	@Test
	void serializeTest() {
		company.save(filePath);
		Company newCompany = new CompanyImpl();
		newCompany.restore(filePath);
		assertEquals(company, newCompany);
	}
}
