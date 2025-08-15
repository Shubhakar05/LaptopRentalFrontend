package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.model.Employees;

public interface EmployeesRepository extends JpaRepository<Employees,Long>{

}
