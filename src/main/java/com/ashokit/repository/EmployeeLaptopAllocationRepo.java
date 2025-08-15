package com.ashokit.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashokit.model.EmployeeLaptopAllocation;

public interface EmployeeLaptopAllocationRepo extends JpaRepository<EmployeeLaptopAllocation, Long> {
	
	 @Query("SELECT e FROM EmployeeLaptopAllocation e WHERE e.laptop.id = :laptopId " +
	           "AND e.allocationStart <= :checkDate " +
	           "AND (e.allocationEnd IS NULL OR e.allocationEnd >= :checkDate)")
	    List<EmployeeLaptopAllocation> findCurrentAllocations(
	        @Param("laptopId") Long laptopId,
	        @Param("checkDate") LocalDate checkDate);
	}

