package com.pk.main;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.pk.main.dao.EmployeeDao;
import com.pk.main.model.Employee;
import com.pk.main.service.EmployeeService;

/**
 * @author PranaySK
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBootMvcJdbcApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
class SpringBootMvcJdbcApplicationTests {

	@Mock
	private EmployeeDao mockDao;

	@InjectMocks
	private EmployeeService employeeService;

	@BeforeAll
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void contextLoads() {
		assertTrue(true);
	}

	@Test
	public void testGetAllEmployees() {
		Employee e0 = new Employee(1l, "Emp1", "Emp1");
		Employee e1 = new Employee(2l, "Emp2", "Emp2");
		Employee e2 = new Employee(3l, "Emp3", "Emp3");

		List<Employee> mockEmployeeList = new ArrayList<>();
		mockEmployeeList.add(e0);
		mockEmployeeList.add(e1);
		mockEmployeeList.add(e2);

		when(mockDao.getEmployees()).thenReturn(mockEmployeeList);

		assertThat(employeeService.getEmployees(), is(notNullValue()));
		assertEquals(2, mockEmployeeList.get(1).getEmployeeId());
		assertEquals(3, mockEmployeeList.size());

		verify(mockDao, times(1)).getEmployees();
	}

	@Test
	public void testGetEmployeeById() {
		Long id = 1l;
		Employee employee = new Employee(id, "Emp1", "Java");
		when(mockDao.getEmployeeById(id)).thenReturn(employee);

		assertThat(employeeService.getEmployeeById(id), is(notNullValue()));
		assertEquals(id, employee.getEmployeeId());

		verify(mockDao, times(1)).getEmployeeById(Mockito.anyLong());
	}

	@Test
	public void testAddEmployee() {
		Employee employee = new Employee(1l, "Emp1", "Java");

		when(mockDao.createEmployee(employee)).thenReturn(1);

		assertThat(employeeService.createEmployee(employee), is(notNullValue()));

		verify(mockDao, times(1)).createEmployee(Mockito.any(Employee.class));
	}

	@Test
	public void testUpdateEmployee() {
		Long id = 3l;
		Employee employee = new Employee(id, "Emp6", "Java");

		when(mockDao.updateEmployee(employee)).thenReturn(1);

		assertThat(employeeService.updateEmployee(employee), is(notNullValue()));
		assertEquals(id, employee.getEmployeeId());

		verify(mockDao, times(1)).updateEmployee(Mockito.any(Employee.class));
	}

	@Test
	void testDeleteEmployee() {
		Long id = 3l;
		when(mockDao.deleteEmployee(id)).thenReturn(1);

		assertThat(employeeService.deleteEmployee(id), is(notNullValue()));

		verify(mockDao, times(1)).deleteEmployee(Mockito.anyLong());
		verify(mockDao, never()).createEmployee(Mockito.any(Employee.class));
	}

}
