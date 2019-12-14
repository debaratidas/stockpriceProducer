package com.stockprice.producer.stockPriceProducer.repo;


import com.stockprice.producer.stockPriceProducer.model.UserStock;
import com.stockprice.producer.stockPriceProducer.model.UserStockId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepo extends CrudRepository<UserStock, UserStockId> {
}
