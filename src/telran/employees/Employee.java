package telran.employees;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	long id;
	String name;
	LocalDate birthDate;
	String department;
	int salary;

	public Employee(long id, String name, LocalDate birthDate, String department, int salary) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.department = department;
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public int getSalary() {
		return salary;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, department, id, name, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(department, other.department)
				&& id == other.id && Objects.equals(name, other.name) && salary == other.salary;
	}

}