package com.example.poc.masterspreparation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinanceDto {
  private String revenueName;

  private Integer totalPrice;
}
