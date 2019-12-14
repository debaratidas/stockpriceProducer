package com.stockprice.producer.stockPriceProducer.repo;

import com.stockprice.producer.stockPriceProducer.model.StockPrice;
import com.stockprice.producer.stockPriceProducer.model.UserStock;
import com.stockprice.producer.stockPriceProducer.model.UserStockId;
import org.springframework.data.repository.CrudRepository;

public interface StockPriceRepo extends CrudRepository<StockPrice, Integer> {
}
