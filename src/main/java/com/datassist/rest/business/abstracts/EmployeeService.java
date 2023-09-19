package com.datassist.rest.business.abstracts;

import java.util.List;

import com.datassist.rest.business.dto.requests.CreateEmployeeRequest;
import com.datassist.rest.business.dto.requests.UpdateEmployeeRequest;
import com.datassist.rest.business.dto.responses.GetAllEmployeesByName;
import com.datassist.rest.business.dto.responses.GetAllEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByIdEmployeeResponse;
import com.datassist.rest.business.dto.responses.GetBySmallerThanAgeEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByStringEmployeeResponse;

public interface EmployeeService {
	
	//Responses
	List<GetAllEmployeesResponse> getAll();
	List<GetByStringEmployeeResponse> getByStr(String str);
	List<GetBySmallerThanAgeEmployeesResponse> getBySmallerThanAge(int age);
	GetByIdEmployeeResponse getById(String id);
	List<GetAllEmployeesByName> getAllByName(String name);
	
	
	//Requests
	CreateEmployeeRequest add(CreateEmployeeRequest createEmployeeRequest);
	void delete(String id);
	void update(String id, UpdateEmployeeRequest updateEmployeeRequest);

}
