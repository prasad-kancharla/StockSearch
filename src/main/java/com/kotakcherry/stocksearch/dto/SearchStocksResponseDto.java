package com.kotakcherry.stocksearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kotakcherry.stocksearch.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchStocksResponseDto {
    private HttpStatus status;
    private String message;
    @JsonProperty("Stocks")
    private List<Stock> stocks;
}
