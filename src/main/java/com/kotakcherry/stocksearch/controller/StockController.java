package com.kotakcherry.stocksearch.controller;

import com.kotakcherry.stocksearch.dto.ResponseDto;
import com.kotakcherry.stocksearch.dto.SearchStocksResponseDto;
import com.kotakcherry.stocksearch.dto.StockRequestDto;
import com.kotakcherry.stocksearch.dto.StockResponseDto;
import com.kotakcherry.stocksearch.exception.AppErrorCodes;
import com.kotakcherry.stocksearch.exception.AppException;
import com.kotakcherry.stocksearch.helper.ExcelHelper;
import com.kotakcherry.stocksearch.model.Stock;
import com.kotakcherry.stocksearch.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@Slf4j
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return this.stockService.listAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
                ResponseDto uploadFileResponseDto = stockService.saveAllStocks(file);
                return ResponseEntity.ok(uploadFileResponseDto);
        }
        log.error(AppErrorCodes.INVALID_FILE_FORMAT.getMessage());
        throw new AppException(AppErrorCodes.INVALID_FILE_FORMAT);
    }

    @PostMapping("/add")
    public ResponseEntity<StockResponseDto> addStock(@RequestBody StockRequestDto stockRequestDto) {
        StockResponseDto stockResponseDto = stockService.addStock(stockRequestDto);
        return ResponseEntity.ok(stockResponseDto);
    }

    @GetMapping("/search")
    public SearchStocksResponseDto searchStocks(@RequestParam String keywords) {
        return stockService.search(keywords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDto> getStockById(@PathVariable Long id) {
        StockResponseDto stockResponseDto = stockService.getStockById(id);
        return ResponseEntity.ok(stockResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteStocks() {
        ResponseDto deleteStocksResponseDto = stockService.deleteAll();
        return ResponseEntity.ok(deleteStocksResponseDto);
    }
}
