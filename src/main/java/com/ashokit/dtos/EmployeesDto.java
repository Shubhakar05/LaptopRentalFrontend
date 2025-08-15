package com.ashokit.dtos;

import com.ashokit.model.User;

import lombok.Data;


@Data
public class EmployeesDto {
	 private Long id;
	  private String name;
	    private String email;
	    private String phone;
	    private Long organizationId; 

}
