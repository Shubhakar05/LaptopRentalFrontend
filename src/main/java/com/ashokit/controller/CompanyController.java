package com.ashokit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.dtos.EmployeeLaptopAllocationDto;
import com.ashokit.dtos.EmployeesDto;
import com.ashokit.dtos.PaymentCallBackRequest;
import com.ashokit.dtos.PaymentResponseDto;
import com.ashokit.dtos.RentalRequestDto;
import com.ashokit.model.EmployeeLaptopAllocation;
import com.ashokit.model.Employees;
import com.ashokit.model.RentalOrder;
import com.ashokit.service.EmployeeLaptopAllocationService;
import com.ashokit.service.EmployeeService;
import com.ashokit.service.PaymentService;
import com.ashokit.service.RentalService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	

    @Autowired
    private RentalService rentalService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeLaptopAllocationService allocationService;

  

    @PostMapping("/laptops/createRentalOrder")
    public ResponseEntity<?> requestRentalOrder(@RequestBody RentalRequestDto requestDto) {
        RentalOrder rentalOrder = rentalService.createRentalOrder(requestDto);
        return ResponseEntity.ok(rentalOrder);
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeesDto employeeDto) {
        Employees savedEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.ok(savedEmployee);
    }

    @PostMapping("/laptop-allocations/allocate")
    public ResponseEntity<EmployeeLaptopAllocation> allocateLaptop(@RequestBody EmployeeLaptopAllocationDto dto) {
        return ResponseEntity.ok(
            allocationService.allocateLaptop(dto.getEmployeeId(), dto.getLaptopId(), dto.getAllocationStart(), dto.getAllocationEnd())
        );
    }

    @PostMapping("/laptop-allocations/deallocate")
    public ResponseEntity<EmployeeLaptopAllocation> deallocateLaptop(@RequestBody EmployeeLaptopAllocationDto dto) {
        return ResponseEntity.ok(
            allocationService.deallocateLaptop(dto.getId(), dto.getAllocationEnd())
        );
    }
    

   
    
    @GetMapping("/{companyId}")
    public ResponseEntity<List<RentalOrder>> getCompanyOrders(@PathVariable Long companyId) {
        List<RentalOrder> orders = rentalService.rentalOrders(companyId);

        if (orders.isEmpty()) {
           
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(orders);
    }
}

