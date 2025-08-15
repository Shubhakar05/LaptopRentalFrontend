package com.ashokit.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.dtos.EmployeesDto;
import com.ashokit.model.Employees;
import com.ashokit.model.User;
import com.ashokit.repository.EmployeesRepository;
import com.ashokit.repository.UserRepo;
import com.ashokit.service.EmployeeService;


@Service
public class EmployeesServiceImpl implements EmployeeService {
	
	@Autowired
	UserRepo userRepo;

	
	@Autowired
	EmployeesRepository employeeRepo;
	@Override
	public Employees createEmployee(EmployeesDto employeeDto) {
		
		   User organization = userRepo.findById(employeeDto.getOrganizationId())
				              .orElseThrow(()->new RuntimeException("Orgaqnization Not Found"));
		 
		     Employees employee = new Employees();

		     BeanUtils.copyProperties(employeeDto,employee);
		     employee.setOrganization(organization);
		     employee.setCreatedAt(LocalDateTime.now());
		     employee.setUpdatedAt(LocalDateTime.now());
		                  
		     Employees save = employeeRepo.save(employee);
		     
		     if(save.getId()!=null) {
		    	 return save;
		     }
		     return null;
		     	
	}

}
