package com.ashokit.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalRequestDto {
    private Long userId;
    private List<RentalItemRequestDto> items;
    private LocalDate startDate;
    private int rentalMonths;
}

