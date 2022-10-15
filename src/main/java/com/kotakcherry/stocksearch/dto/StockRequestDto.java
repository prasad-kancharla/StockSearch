package com.kotakcherry.stocksearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDto {

    @NonNull private String name;
    @NonNull private String ticker;
    @NonNull private String sector;
    @NonNull private Double marketCap;
    @NonNull private Double closePrice;
    @NonNull private Double peRatio;
}
