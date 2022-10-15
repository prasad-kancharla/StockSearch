package com.kotakcherry.stocksearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kotakcherry.stocksearch.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponseDto {

    private HttpStatus status;
    private String message;
    @JsonProperty("Stock")
    private Stock stock;
}
