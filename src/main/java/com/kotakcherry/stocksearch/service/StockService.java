package com.kotakcherry.stocksearch.service;

import com.kotakcherry.stocksearch.helper.ExcelHelper;
import com.kotakcherry.stocksearch.model.Stock;
import com.kotakcherry.stocksearch.repository.StockRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<Stock>listAll() {
        return stockRepository.findAll();
    }

    public List<Stock> search(String keywords) {
        MatchQueryBuilder searchByNames = QueryBuilders.matchQuery("name", keywords);
        return stockRepository.search(searchByNames);
    }

//    public void search(String keywords) {
//        Query searchQuery = new StringQuery(
//                "{\"match\":{\"name\":{\"query\":\"" + keywords + "\"}}}\"");
//        SearchHits<Stock> stocks = elasticsearchOperations.search(
//                searchQuery,
//                Stock.class,
//                IndexCoordinates.of()
//        );
//    }

    public void save(MultipartFile file) {
        try {
            List<Stock> stocks = ExcelHelper.excelToStocks(file.getInputStream());
            stockRepository.saveAll(stocks);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}
