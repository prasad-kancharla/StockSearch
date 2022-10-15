package com.kotakcherry.stocksearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "stocks")
@Setting(settingPath = "es-config/elastic-analyzer.json")
@Data
public class Stock {

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private String name;
    private String sector;
    private Double marketCap;
    private Double closePrice;
    private Double peRatio;
}
