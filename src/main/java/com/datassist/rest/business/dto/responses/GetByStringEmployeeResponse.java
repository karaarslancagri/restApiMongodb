package com.datassist.rest.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByStringEmployeeResponse {
	
	private String id;
	private String name;
	private int age;
}
