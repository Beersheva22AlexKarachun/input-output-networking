package telran.employees;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import telran.net.*;

public class NetworkCompany implements Company {

	private static final long serialVersionUID = -8723099921124132918L;
	private NetworkClient client;

	public NetworkCompany(NetworkClient client) {
		this.client = client;
	}

	public void close() throws IOException {
		client.close();
	}

	@Override
	public boolean addEmployee(Employee empl) {
		return client.send("addEmployee", empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		return client.send("removeEmployee", id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return client.send("getAllEmployees", null);
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return client.send("getEmployeesByMonthBirth", month);
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return client.send("getEmployeesBySalary", new int[] { salaryFrom, salaryTo });
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return client.send("getEmployeesByDepartment", department);
	}

	@Override
	public Employee getEmployee(long id) {
		return client.send("getEmployee", id);
	}

	@Override
	public void save(String pathName) {
		client.send("save", pathName);
	}

	@Override
	public void restore(String pathName) {
		client.send("restore", pathName);
	}
}
