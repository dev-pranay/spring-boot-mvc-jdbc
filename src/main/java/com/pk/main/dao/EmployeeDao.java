package com.pk.main.dao;

import java.util.List;

import com.pk.main.model.Employee;

/**
 * @author PranaySK
 */

public interface EmployeeDao {

	public List<Employee> getEmployees();

	public Employee getEmployeeById(Long employeeId);

	public Integer deleteEmployee(Long employeeId);

	public Integer updateEmployee(Employee employee);

	public Integer createEmployee(Employee employee);
}
