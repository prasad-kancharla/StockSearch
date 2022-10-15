package com.kotakcherry.stocksearch.repository;

import com.kotakcherry.stocksearch.model.Stock;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends ElasticsearchRepository<Stock, Long> {

    List<Stock> findAll();

    long count();

    Optional<Stock> findById(Long id);

    List<Stock> search(QueryBuilder query);
}
