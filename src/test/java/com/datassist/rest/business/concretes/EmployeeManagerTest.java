package com.datassist.rest.business.concretes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.datassist.rest.business.dto.requests.CreateEmployeeRequest;
import com.datassist.rest.business.dto.requests.UpdateEmployeeRequest;
import com.datassist.rest.business.dto.responses.GetAllEmployeesByName;
import com.datassist.rest.business.dto.responses.GetAllEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByIdEmployeeResponse;
import com.datassist.rest.business.dto.responses.GetBySmallerThanAgeEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByStringEmployeeResponse;
import com.datassist.rest.dataAccess.abstracts.EmployeeRepository;
import com.datassist.rest.entities.Employee;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmployeeManagerTest {

	@InjectMocks
	private EmployeeManager employeeManager;

	@Mock
	private EmployeeRepository employeeRepository;

	@BeforeEach
	public void setUp() {
		employeeRepository = mock(EmployeeRepository.class);
		employeeManager = new EmployeeManager(employeeRepository);
	}

	@Test
	public void testAdd() {
		// Giriş verileri
		CreateEmployeeRequest inputRequest = new CreateEmployeeRequest();
		inputRequest.setName("Mehmet");
		inputRequest.setAge(20);

		Employee savedEmployee = new Employee();
		BeanUtils.copyProperties(inputRequest, savedEmployee);

		// Mock repository davranışı
		when(employeeRepository.save(savedEmployee)).thenReturn(savedEmployee);

		CreateEmployeeRequest result = employeeManager.add(inputRequest);

		// Sonucu doğrula
		assertEquals(inputRequest.getName(), result.getName());
		assertEquals(inputRequest.getAge(), result.getAge());

	}

	@Test
	public void testUpdate() {
		UpdateEmployeeRequest updateRequest = new UpdateEmployeeRequest();
		updateRequest.setId("123");
		updateRequest.setName("UpdatedName");
		updateRequest.setAge(30);

		Employee copiedEmployee = new Employee();
		BeanUtils.copyProperties(updateRequest, copiedEmployee);

		when(employeeRepository.save(any(Employee.class))).thenReturn(copiedEmployee);

		employeeManager.update("123", updateRequest);

		verify(employeeRepository, times(1)).save(any(Employee.class));

		assertEquals("UpdatedName", copiedEmployee.getName());
		assertEquals(30, copiedEmployee.getAge());
	}

	@Test
	public void testDelete() {
		String employeeId = "123";
		employeeManager.delete(employeeId);
		verify(employeeRepository, times(1)).deleteById(employeeId);
	}

	@Test
	public void testGetAll() {
		EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

		Employee employee1 = new Employee();
		employee1.setId("1");
		employee1.setName("Mehmet");
		employee1.setAge(25);

		Employee employee2 = new Employee();
		employee2.setId("2");
		employee2.setName("Ahmet");
		employee2.setAge(30);

		List<Employee> mockEmployees = new ArrayList<>();
		mockEmployees.add(employee1);
		mockEmployees.add(employee2);

		when(employeeRepository.findAll()).thenReturn(mockEmployees);

		EmployeeManager employeeManager = new EmployeeManager(employeeRepository);

		List<GetAllEmployeesResponse> responses = employeeManager.getAll();

		assertEquals(2, responses.size());

		GetAllEmployeesResponse response1 = responses.get(0);
		assertEquals("1", response1.getId());
		assertEquals("Mehmet", response1.getName());
		assertEquals(25, response1.getAge());

		GetAllEmployeesResponse response2 = responses.get(1);
		assertEquals("2", response2.getId());
		assertEquals("Ahmet", response2.getName());
		assertEquals(30, response2.getAge());
	}

	@Test
	public void testGetBySmallerThanAge() {
		EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

		Employee employee1 = new Employee();
		employee1.setId("1");
		employee1.setName("John");
		employee1.setAge(25);

		Employee employee2 = new Employee();
		employee2.setId("2");
		employee2.setName("Alice");
		employee2.setAge(35);

		List<Employee> mockEmployees = new ArrayList<>();
		mockEmployees.add(employee1);
		mockEmployees.add(employee2);

		doReturn(mockEmployees).when(employeeRepository).findBySmallerThanAge(28);
		
		EmployeeManager employeeManager = new EmployeeManager(employeeRepository);
		List<GetBySmallerThanAgeEmployeesResponse> responses = employeeManager.getBySmallerThanAge(28);
		GetBySmallerThanAgeEmployeesResponse response1 = responses.get(0);
		try {
			BeanUtils.copyProperties(response1, employee1);
		} catch (Exception e) {
			
		}
		assertEquals("1", response1.getId());
		assertEquals("John", response1.getName());
		assertEquals(25, response1.getAge());

	}

	@Test
	public void testGetByStr() {
		EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

		Employee employee1 = new Employee();
		employee1.setId("1");
		employee1.setName("John");
		employee1.setAge(25);

		Employee employee2 = new Employee();
		employee2.setId("2");
		employee2.setName("Alice");
		employee2.setAge(35);

		List<Employee> mockEmployees = new ArrayList<>();
		mockEmployees.add(employee1);
		mockEmployees.add(employee2);

		doReturn(mockEmployees).when(employeeRepository).findByString("Joh");

		EmployeeManager employeeManager = new EmployeeManager(employeeRepository);
		List<GetByStringEmployeeResponse> responses = employeeManager.getByStr("Joh");
		GetByStringEmployeeResponse response1 = responses.get(0);
		try {
			BeanUtils.copyProperties(response1, employee1);
		} catch (Exception e) {
			System.out.println("Hata");
		}
		assertEquals("1", response1.getId());
		assertEquals("John", response1.getName());
		assertEquals(25, response1.getAge());

	}

	@Test
	public void testGetById() {
		EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

		Employee employee = new Employee();
		employee.setId("123");
		employee.setName("John");
		employee.setAge(30);

		when(employeeRepository.findById("123")).thenReturn(Optional.of(employee));

		EmployeeManager employeeManager = new EmployeeManager(employeeRepository);
		GetByIdEmployeeResponse response = employeeManager.getById("123");
		assertEquals("123", response.getId());
		assertEquals("John", response.getName());
		assertEquals(30, response.getAge());
	}

	@Test
	public void testGetAllByName() {
		EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

		Employee employee1 = new Employee();
		employee1.setId("1");
		employee1.setName("Çağrı");
		employee1.setAge(25);

		Employee employee2 = new Employee();
		employee2.setId("2");
		employee2.setName("Uğur");
		employee2.setAge(30);

		List<Employee> mockEmployees = new ArrayList<>();
		mockEmployees.add(employee1);
		mockEmployees.add(employee2);

		doReturn(mockEmployees).when(employeeRepository).findByName("Çağrı");

		EmployeeManager employeeManager = new EmployeeManager(employeeRepository);

		List<GetAllEmployeesByName> responses = employeeManager.getAllByName("Çağrı");

		GetAllEmployeesByName response1 = responses.get(0);
		try {
			BeanUtils.copyProperties(response1, employee1);
		} catch (Exception e) {

		}

		assertEquals("1", response1.getId());
		assertEquals("Çağrı", response1.getName());
		assertEquals(25, response1.getAge());
	}

}