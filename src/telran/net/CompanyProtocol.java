package telran.net;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import telran.employees.*;

public class CompanyProtocol implements Protocol {

	private final Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Response response;
		try {
			Method method = CompanyProtocol.class.getDeclaredMethod(request.type, Serializable.class);
			response = new Response(ResponseCode.OK, (Serializable) method.invoke(this, request.data));

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			response = new Response(ResponseCode.WRONG_REQUEST, "This type of request isn't supported: " + e);
		} catch (IllegalArgumentException e) {
			response = new Response(ResponseCode.WRONG_DATA, "Wrong data");
		}

		return response;
	}

	private String restore(Serializable data) {
		company.restore(data.toString());
		return "Company has been restored";
	}

	private String save(Serializable data) {
		company.save(data.toString());
		return "Company has been saved";
	}

	private List<Employee> getEmployeesByDepartment(Serializable data) {
		return company.getEmployeesByDepartment(data.toString());
	}

	private List<Employee> getEmployeesBySalary(Serializable data) {
		int[] salaries = (int[]) data;
		return company.getEmployeesBySalary(salaries[0], salaries[1]);
	}

	private List<Employee> getEmployeesByMonthBirth(Serializable data) {
		return company.getEmployeesByMonthBirth((int) data);
	}

	private List<Employee> getAllEmployees(Serializable data) {
		return company.getAllEmployees();
	}

	private Employee removeEmployee(Serializable data) {
		return company.removeEmployee((long) data);
	}

	private boolean addEmployee(Serializable data) {
		return company.addEmployee((Employee) data);
	}

	private Employee getEmployee(Serializable data) {
		return company.getEmployee((long) data);
	}
}