package com.pk.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.main.dao.EmployeeDao;
import com.pk.main.model.Employee;

/**
 * @author PranaySK
 */

@Service("employeeService")
public class EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	public List<Employee> getEmployees() {
		return employeeDao.getEmployees();
	}

	public Employee getEmployeeById(Long employeeId) {
		return employeeDao.getEmployeeById(employeeId);
	}

	public Integer deleteEmployee(Long employeeId) {
		return employeeDao.deleteEmployee(employeeId);
	}

	public Integer updateEmployee(Employee employee) {
		return employeeDao.updateEmployee(employee);
	}

	public Integer createEmployee(Employee employee) {
		return employeeDao.createEmployee(employee);
	}
}
