package com.kotakcherry.stocksearch.repository;

import com.kotakcherry.stocksearch.model.Stock;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends ElasticsearchRepository<Stock, Long> {

    List<Stock> findAll();

    List<Stock> search(QueryBuilder query);
}
