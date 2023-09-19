package com.datassist.rest.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datassist.rest.business.abstracts.EmployeeService;
import com.datassist.rest.business.dto.requests.CreateEmployeeRequest;
import com.datassist.rest.business.dto.requests.UpdateEmployeeRequest;
import com.datassist.rest.business.dto.responses.GetAllEmployeesByName;
import com.datassist.rest.business.dto.responses.GetAllEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByIdEmployeeResponse;
import com.datassist.rest.business.dto.responses.GetBySmallerThanAgeEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByStringEmployeeResponse;
import com.datassist.rest.dataAccess.abstracts.EmployeeRepository;
import com.datassist.rest.entities.Employee;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeManager implements EmployeeService{
	
	@Autowired
	private final EmployeeRepository employeeRepository;
	
	//Requests
	
	@Override
	public CreateEmployeeRequest add(final CreateEmployeeRequest createEmployeeRequest) {
		Employee employee = new Employee();
		
		// BeanUtils.copyProperties kullanarak alanları kopyala
	    BeanUtils.copyProperties(createEmployeeRequest, employee);

	    // Employee nesnesini veritabanına kaydet
	    employee = employeeRepository.save(employee);

	    CreateEmployeeRequest result = new CreateEmployeeRequest();

	    // BeanUtils.copyProperties kullanarak alanları geri kopyala
	    BeanUtils.copyProperties(employee, result);

		return result;
	}

	@Override
	public void update(String id,final UpdateEmployeeRequest updateEmployeeRequest) {
		Employee employee = new Employee();
	    BeanUtils.copyProperties(updateEmployeeRequest, employee);
	    this.employeeRepository.save(employee);
	}
	
	@Override
	public void delete(final String id) {
		this.employeeRepository.deleteById(id);
		
	}
		 
	//Responses
	
	
	@Override
	public List<GetAllEmployeesResponse> getAll() {
		 List<Employee> employees = employeeRepository.findAll();
		    List<GetAllEmployeesResponse> responses = new ArrayList<>();
		    for (Employee employee : employees) {
		        GetAllEmployeesResponse response = new GetAllEmployeesResponse();
		        BeanUtils.copyProperties(employee, response);
		        responses.add(response);
		    }
		    return responses;
	}
	
	@Override
	public List<GetBySmallerThanAgeEmployeesResponse> getBySmallerThanAge(final int age) {
		List<Employee> employees = employeeRepository.findBySmallerThanAge(age);
	    List<GetBySmallerThanAgeEmployeesResponse> responses = new ArrayList<>();
	    for (Employee employee : employees) {
	        GetBySmallerThanAgeEmployeesResponse response = new GetBySmallerThanAgeEmployeesResponse();
	        BeanUtils.copyProperties(employee, response);
	        responses.add(response);
	    }
	    return responses;
		
	}

	@Override
	public List<GetByStringEmployeeResponse> getByStr(final String str) {
		 List<Employee> employees = employeeRepository.findByString(str);
		    List<GetByStringEmployeeResponse> responses = new ArrayList<>();
		    for (Employee employee : employees) {
		        GetByStringEmployeeResponse response = new GetByStringEmployeeResponse();
		        BeanUtils.copyProperties(employee, response);
		        responses.add(response);
		    }
		    return responses;
	}

	
	@Override
	public GetByIdEmployeeResponse getById(final String id) {
		Employee employee = employeeRepository.findById(id).orElseThrow();
	    GetByIdEmployeeResponse response = new GetByIdEmployeeResponse();
	    BeanUtils.copyProperties(employee, response);
	    return response;
		 
	}

	@Override
	public List<GetAllEmployeesByName> getAllByName(final String name) {
		List<Employee> employees = employeeRepository.findByName(name);
	    List<GetAllEmployeesByName> responses = new ArrayList<>();
	    for (Employee employee : employees) {
	        GetAllEmployeesByName response = new GetAllEmployeesByName();
	        BeanUtils.copyProperties(employee, response);
	        responses.add(response);
	    }
	    return responses;
	}

	
	
	
	

	



}
