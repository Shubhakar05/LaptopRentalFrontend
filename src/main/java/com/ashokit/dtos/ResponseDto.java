package com.ashokit.dtos;


import java.util.List;

import com.ashokit.model.Laptop;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ResponseDto {
    private String token;
    private String status;
    
    private String role;

   private List<Laptop>laptops;
    
    

  
}
