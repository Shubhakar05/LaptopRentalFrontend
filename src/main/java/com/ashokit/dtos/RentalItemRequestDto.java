package com.ashokit.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalItemRequestDto {
    private Long laptopId;
//    private int quantity;
//    private int rentalMonths;
}


