package com.stockprice.producer.stockPriceProducer.model;

import javax.persistence.Column;
import java.io.Serializable;


public class UserStockId implements Serializable {

    @Column(name="user_id")
    private  String userId;
    @Column(name="stock_id")
    private String  stockId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
