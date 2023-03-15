package telran.employees;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.temporal.ChronoField;
import java.util.*;

public class CompanyImpl implements Company {
	private static final long serialVersionUID = 2086812054571325587L;
	private Map<Long, Employee> map = new HashMap<>();

	@Override
	public Iterator<Employee> iterator() {
		return map.values().iterator();
	}

	@Override
	public boolean addEmployee(Employee empl) {
		return map.put(empl.id, empl) == null;
	}

	@Override
	public Employee removeEmployee(long id) {
		return map.remove(id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return map.values().stream().toList();
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return map.values().stream().filter(empl -> empl.birthDate.get(ChronoField.MONTH_OF_YEAR) == month).toList();
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return map.values().stream().filter(empl -> (empl.salary >= salaryFrom && empl.salary <= salaryTo)).toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return map.values().stream().filter(empl -> empl.department.equalsIgnoreCase(department)).toList();
	}

	@Override
	public Employee getEmployee(long id) {
		return map.get(id);
	}

	@Override
	public void save(String pathName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pathName))) {
			output.writeObject(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void restore(String pathName) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(pathName))) {
			Company company = (CompanyImpl) input.readObject();
			for (Field field : getClass().getDeclaredFields()) {
				int fieldMods = field.getModifiers();
				if (!Modifier.isStatic(fieldMods) && !Modifier.isFinal(fieldMods)) {
					field.set(this, field.get(company));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(map);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CompanyImpl))
			return false;
		CompanyImpl other = (CompanyImpl) obj;
		return Objects.equals(map, other.map);
	}

}