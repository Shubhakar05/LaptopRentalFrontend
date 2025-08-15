package com.ashokit.service;

import com.ashokit.dtos.EmployeesDto;
import com.ashokit.model.Employees;

public interface EmployeeService {
	
	   public Employees createEmployee(EmployeesDto employeeDto);
}
