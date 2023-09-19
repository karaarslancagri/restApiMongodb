package com.datassist.rest.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.datassist.rest.entities.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>{
	
	boolean existsByName(String name);
	
	@Query("{'name' : ?0 }")
	List<Employee> findByName(@Param("name") String name);
	
	@Query("{'age' : { $lt : ?0 } }")
	List<Employee> findBySmallerThanAge(@Param("age") int age);
	
	@Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
	List<Employee> findByString(@Param("str") String str);

}
