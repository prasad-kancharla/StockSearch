package com.kotakcherry.stocksearch.controller;

import com.kotakcherry.stocksearch.constants.Constants;
import com.kotakcherry.stocksearch.helper.ExcelHelper;
import com.kotakcherry.stocksearch.model.Stock;
import com.kotakcherry.stocksearch.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.kotakcherry.stocksearch.constants.Constants.FILE_UPLOAD_SUCCESS;
import static com.kotakcherry.stocksearch.constants.Constants.FILE_UPLOAD_FAILED;
import static com.kotakcherry.stocksearch.constants.Constants.INVALID_FILE_FORMAT;

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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                stockService.save(file);
                return ResponseEntity.status(HttpStatus.OK).body(FILE_UPLOAD_SUCCESS);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(FILE_UPLOAD_FAILED + file.getOriginalFilename() + "!");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(INVALID_FILE_FORMAT);
    }

    @GetMapping(path = "/search")
    public List<Stock> searchStocks(@RequestParam String keywords) {
        return stockService.search(keywords);
    }
}
