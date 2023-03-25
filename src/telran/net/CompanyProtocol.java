package telran.net;

import java.io.Serializable;

import telran.employees.*;

public class CompanyProtocol implements Protocol {
	private final Company company = new CompanyImpl();

	@Override
	public Response getResponse(Request request) {
		return switch (request.type) {
		case "getEmployee" -> getEmployee(request.data);
		case "addEmployee" -> addEmployee(request.data);
		case "removeEmployee" -> removeEmployee(request.data);
		case "getAllEmployees" -> getAllEmployees(request.data);
		case "getEmployeesByMonthBirth" -> getEmployeesByMonthBirth(request.data);
		case "getEmployeesBySalary" -> getEmployeesBySalary(request.data);
		case "getEmployeesByDepartment" -> getEmployeesByDepartment(request.data);
		case "save" -> save(request.data);
		case "restore" -> restore(request.data);
		default -> new Response(ResponseCode.WRONG_REQUEST, request.type + " wrong request");
		};
	}

	private Response restore(Serializable data) {
		company.restore(data.toString());
		return new Response(ResponseCode.OK, "");
	}

	private Response save(Serializable data) {
		company.save(data.toString());
		return new Response(ResponseCode.OK, "");
	}

	private Response getEmployeesByDepartment(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable) company.getEmployeesByDepartment(data.toString()));
	}

	private Response getEmployeesBySalary(Serializable data) {
		int[] salaries = (int[]) data;
		return new Response(ResponseCode.OK, (Serializable) company.getEmployeesBySalary(salaries[0], salaries[1]));
	}

	private Response getEmployeesByMonthBirth(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable) company.getEmployeesByMonthBirth((int) data));
	}

	private Response getAllEmployees(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable) company.getAllEmployees());
	}

	private Response removeEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.removeEmployee((long) data));
	}

	private Response addEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.addEmployee((Employee) data));
	}

	private Response getEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.getEmployee((long) data));
	}
}