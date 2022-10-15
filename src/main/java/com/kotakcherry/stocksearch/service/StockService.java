package com.kotakcherry.stocksearch.service;

import com.kotakcherry.stocksearch.dto.ResponseDto;
import com.kotakcherry.stocksearch.dto.SearchStocksResponseDto;
import com.kotakcherry.stocksearch.dto.StockRequestDto;
import com.kotakcherry.stocksearch.dto.StockResponseDto;
import com.kotakcherry.stocksearch.exception.AppErrorCodes;
import com.kotakcherry.stocksearch.exception.AppException;
import com.kotakcherry.stocksearch.helper.ExcelHelper;
import com.kotakcherry.stocksearch.model.Stock;
import com.kotakcherry.stocksearch.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.kotakcherry.stocksearch.constants.Constants.*;

@Service
@Slf4j
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> listAll() {
        return stockRepository.findAll();
    }

    public SearchStocksResponseDto search(String keywords) {
        MatchQueryBuilder searchByTickers = QueryBuilders.matchQuery("ticker", keywords);
        List<Stock> stocks = stockRepository.search(searchByTickers);
        return new SearchStocksResponseDto(HttpStatus.OK,STOCKS_FOUND,stocks);
    }

    public ResponseDto saveAllStocks(MultipartFile file) {
        try {
            List<Stock> stocks = ExcelHelper.excelToStocks(file.getInputStream());
            stockRepository.saveAll(stocks);
            return new ResponseDto(HttpStatus.OK, FILE_UPLOAD_SUCCESS);
        } catch (IOException e) {
            log.error("File Name: " + file.getOriginalFilename() + "-" + AppErrorCodes.UPLOAD_FAILED.getMessage());
            throw new AppException(AppErrorCodes.UPLOAD_FAILED);
        }
    }

    public ResponseDto deleteAll() {
        stockRepository.deleteAll();
        return new ResponseDto(HttpStatus.OK, STOCKS_DELETED);
    }

    public StockResponseDto addStock(StockRequestDto stockRequestDto) {
        if (stockRequestDto.getMarketCap() < 0) {
            log.error(AppErrorCodes.INVALID_MARKET_CAP.getMessage());
            throw new AppException(AppErrorCodes.INVALID_MARKET_CAP);
        }
        if (stockRequestDto.getClosePrice() < 0) {
            log.error(AppErrorCodes.INVALID_CLOSE_PRICE.getMessage());
            throw new AppException(AppErrorCodes.INVALID_CLOSE_PRICE);
        }
        if (stockRequestDto.getPeRatio() < 0) {
            log.error(AppErrorCodes.INVALID_PE_RATIO.getMessage());
            throw new AppException(AppErrorCodes.INVALID_PE_RATIO);
        }
        long count = stockRepository.count();
        Stock stock = new Stock(count + 1, stockRequestDto.getName(), stockRequestDto.getTicker(),
                stockRequestDto.getSector(), stockRequestDto.getMarketCap(), stockRequestDto.getClosePrice(),
                stockRequestDto.getPeRatio());
        stockRepository.save(stock);
        return new StockResponseDto(HttpStatus.OK, STOCK_ADDED, stock);
    }

    public StockResponseDto getStockById(Long id) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        if(optionalStock.isEmpty()) {
            log.error(AppErrorCodes.INVALID_STOCK_ID.getMessage());
            throw new AppException(AppErrorCodes.INVALID_STOCK_ID);
        }
        return new StockResponseDto(HttpStatus.OK,STOCK_FOUND,optionalStock.get());
    }
}
