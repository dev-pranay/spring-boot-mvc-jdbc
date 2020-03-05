package com.pk.main.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pk.main.constants.ErrorConstants;
import com.pk.main.exceptions.DataInsertionException;
import com.pk.main.exceptions.DbException;
import com.pk.main.model.Employee;

/**
 * @author PranaySK
 */

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	static final String SQL_SELECT_ALL = "SELECT * FROM Employee";
	static final String SQL_SELECT_ON_ID = "SELECT * FROM Employee WHERE Employee_Id = ?";
	static final String SQL_DELETE_ON_ID = "DELETE FROM Employee WHERE Employee_Id = ?";
	static final String SQL_UPDATE_ON_ID = "UPDATE Employee SET First_Name = ?, Last_Name = ? WHERE Employee_Id = ?";
	static final String SQL_INSERT = "INSERT INTO Employee (Employee_Id, First_Name, Last_Name) VALUES(?, ?, ?)";

	@Override
	public List<Employee> getEmployees() {
		List<Employee> employees = null;
		try {
			employees = jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_RET_ERROR, ex.getMessage());
		}
		return employees;
	}

	@Override
	public Employee getEmployeeById(Long employeeId) {
		Employee employee = null;
		try {
			employee = jdbcTemplate.queryForObject(SQL_SELECT_ON_ID, new Object[] { employeeId },
					new BeanPropertyRowMapper<Employee>(Employee.class));
		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_RET_ERROR, "Operation not possible. Employee details not found.");
		}
		return employee;
	}

	@Override
	public Integer deleteEmployee(Long employeeId) {
		try {
			return jdbcTemplate.update(SQL_DELETE_ON_ID, employeeId);
		} catch (DataAccessException ex) {
			throw new DbException(ErrorConstants.DATA_DELETION_ERROR, ex.getMessage());
		}
	}

	@Override
	public Integer updateEmployee(Employee employee) {
		try {
			return jdbcTemplate.update(SQL_UPDATE_ON_ID, employee.getFirstName(), employee.getLastName(),
					employee.getEmployeeId());
		} catch (DataAccessException ex) {
			throw new DataInsertionException(ErrorConstants.INVALID_DATA, ex.getMessage());
		}
	}

	@Override
	public Integer createEmployee(Employee employee) {
		try {
			return jdbcTemplate.update(SQL_INSERT, employee.getEmployeeId(), employee.getFirstName(),
					employee.getLastName());
		} catch (DataAccessException ex) {
			throw new DataInsertionException(ErrorConstants.INSERTION_ERROR, ex.getMessage());
		}
	}
}
