package com.pk.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pk.main.constants.ErrorConstants;
import com.pk.main.exceptions.DataInsertionException;
import com.pk.main.exceptions.DbException;
import com.pk.main.model.Employee;
import com.pk.main.service.EmployeeService;

/**
 * @author PranaySK
 */

@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	/** Loading home page */
	@GetMapping("/")
	public ModelAndView home(Model model) {
		ModelAndView view = new ModelAndView();
		view.setViewName("index");
		return view;
	}

	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> employees() {
		HttpHeaders headers = new HttpHeaders();
		List<Employee> employees = employeeService.getEmployees();
		if (employees == null) {
			throw new DbException(ErrorConstants.DATA_NOT_FOUND_ERROR, "Data not found");
		}
		headers.add("Number of records found", String.valueOf(employees.size()));
		return new ResponseEntity<>(employees, headers, HttpStatus.OK);
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") Long employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		if (employee == null) {
			throw new DbException(ErrorConstants.DATA_NOT_FOUND_ERROR, "Requested employee details not found");
		}
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@DeleteMapping("/employee/delete/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long employeeId) {
		HttpHeaders headers = new HttpHeaders();
		Employee employee = employeeService.getEmployeeById(employeeId);
		if (employee == null) {
			throw new DbException(ErrorConstants.DATA_NOT_FOUND_ERROR,
					"Operation not possible. Employee details not found.");
		}
		employeeService.deleteEmployee(employeeId);
		headers.add("Employee Deleted ", String.valueOf(employeeId));
		return new ResponseEntity<>(employee, headers, HttpStatus.OK);
	}

	@PostMapping("/employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		HttpHeaders headers = new HttpHeaders();
		if (employee == null) {
			throw new DataInsertionException(ErrorConstants.INVALID_DATA, "Invalid request data. Cannot be null.");
		}
		employeeService.createEmployee(employee);
		headers.add("Employee Created ", String.valueOf(employee.getEmployeeId()));
		return new ResponseEntity<>(employee, headers, HttpStatus.CREATED);
	}

	@PutMapping("/employee/update/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId,
			@RequestBody Employee employee) {
		HttpHeaders headers = new HttpHeaders();
		Employee isExist = employeeService.getEmployeeById(employeeId);
		if (isExist == null) {
			throw new DbException(ErrorConstants.DATA_NOT_FOUND_ERROR,
					"Operation not possible. Employee details not found.");
		} else if (employee == null) {
			throw new DataInsertionException(ErrorConstants.INVALID_DATA, "Invalid request data. Cannot be null.");
		}
		employeeService.updateEmployee(employee);
		headers.add("Employee Updated ", String.valueOf(employeeId));
		return new ResponseEntity<>(employee, headers, HttpStatus.OK);
	}
}
