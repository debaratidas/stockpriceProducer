package com.stockprice.producer.stockPriceProducer;


import com.stockprice.producer.stockPriceProducer.model.StockPrice;
import com.stockprice.producer.stockPriceProducer.model.UserStock;
import com.stockprice.producer.stockPriceProducer.repo.StockPriceRepo;
import com.stockprice.producer.stockPriceProducer.repo.UserStockRepo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by abykuruvilla on 2019-09-10.
 */
@Component
public class StockQuoteGeneratorAndSender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private UserStockRepo  userStockRepo;
    private StockPriceRepo stockPriceRepo;

    public StockQuoteGeneratorAndSender(RabbitTemplate rabbitTemplate,
                                        UserStockRepo  userStockRepo,
                                        StockPriceRepo stockPriceRepo
                                        ) {
        this.rabbitTemplate = rabbitTemplate;
        this.userStockRepo = userStockRepo;
        this.stockPriceRepo= stockPriceRepo;

    }

    /**
     * Gets the stock price and sends to the stock-quote-queue
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

        while(true) {
            System.out.println("***************************************");
            System.out.println("Checking and Publishing stock quote ...");
            Iterable<UserStock> userStocks =userStockRepo.findAll();
            Set<String> stocks =  new HashSet<>();
            userStocks.forEach(userStock -> {
                stocks.add(userStock.getStockId());
            });

            stocks.forEach(s-> {
                try {
                    Stock stock = YahooFinance.get(s);
                    System.out.println("getting the price for "+s+" is "+ stock.getQuote().getPrice());
                    rabbitTemplate.convertAndSend(StockPriceProducerApplication.topicExchangeName,
                            "stock.quote.AAPL", stock.toString());
                    StockPrice stockPrice = new StockPrice();
                    stockPrice.setStockId(s);
                    stockPrice.setStockName(stock.getSymbol());
                    stockPrice.setStockPrice(stock.getQuote().getPrice());
                    stockPrice.setTime(LocalDateTime.now());
                    stockPriceRepo.save(stockPrice);
                }catch (Exception e) {

                }
            });

            Thread.sleep(60000);
        }
    }
}
