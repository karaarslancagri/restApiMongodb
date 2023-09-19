package com.datassist.rest.webApi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datassist.rest.business.abstracts.EmployeeService;
import com.datassist.rest.business.dto.requests.CreateEmployeeRequest;
import com.datassist.rest.business.dto.requests.UpdateEmployeeRequest;
import com.datassist.rest.business.dto.responses.GetAllEmployeesByName;
import com.datassist.rest.business.dto.responses.GetAllEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByIdEmployeeResponse;
import com.datassist.rest.business.dto.responses.GetBySmallerThanAgeEmployeesResponse;
import com.datassist.rest.business.dto.responses.GetByStringEmployeeResponse;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeControllers {
	
	private final EmployeeService employeeService;

	@GetMapping("/getall")
	public ResponseEntity<List<GetAllEmployeesResponse>> getAll(){
		List<GetAllEmployeesResponse> getAllEmployeesResponses = employeeService.getAll();
		return ResponseEntity.ok(getAllEmployeesResponses);
	}
	
	@GetMapping("/getallsmallerthanage {age}")
	public List<GetBySmallerThanAgeEmployeesResponse> getAllSmallerThanAge(@PathVariable int age){
		return employeeService.getBySmallerThanAge(age);
	}
	
	@GetMapping("/getbyid {id}")
	public GetByIdEmployeeResponse getById(@PathVariable String id) {
		return employeeService.getById(id); 
	}
	
	@GetMapping("/getbystr {str}")
	public List<GetByStringEmployeeResponse> getByStr(@PathVariable String str) {
		return employeeService.getByStr(str);	
	}
	
	@GetMapping("/getbyname {name}")
	public List<GetAllEmployeesByName> getAllByName(@PathVariable String name){
		
		return employeeService.getAllByName(name);
	}
	
	@PostMapping("/add")
    public ResponseEntity<CreateEmployeeRequest> add(@RequestBody CreateEmployeeRequest createEmployeeRequest) {
		CreateEmployeeRequest employeeDto = this.employeeService.add(createEmployeeRequest);
		return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }
	
	@PutMapping("/update {id} ")
	public void update(@PathVariable String id ,@RequestBody UpdateEmployeeRequest updateEmployeeRequest) {
		this.employeeService.update(id,updateEmployeeRequest);
		
	}
	
	@DeleteMapping("/delete {id}")
	public void delete(@PathVariable String id) {
		this.employeeService.delete(id);
	}
	

}
