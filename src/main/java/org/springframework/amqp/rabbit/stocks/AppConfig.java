package org.springframework.amqp.rabbit.stocks;

import org.springframework.amqp.rabbit.stocks.gateway.MarketDataGateway;
import org.springframework.amqp.rabbit.stocks.gateway.RabbitMarketDataGateway;
import org.springframework.amqp.rabbit.stocks.gateway.RabbitStockServiceGateway;
import org.springframework.amqp.rabbit.stocks.gateway.StockServiceGateway;
import org.springframework.amqp.rabbit.stocks.service.CreditCheckService;
import org.springframework.amqp.rabbit.stocks.service.ExecutionVenueService;
import org.springframework.amqp.rabbit.stocks.service.TradingService;
import org.springframework.amqp.rabbit.stocks.service.stubs.CreditCheckServiceStub;
import org.springframework.amqp.rabbit.stocks.service.stubs.ExecutionVenueServiceStub;
import org.springframework.amqp.rabbit.stocks.service.stubs.TradingServiceStub;
import org.springframework.amqp.rabbit.stocks.web.QuoteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public QuoteController quoteController(@Autowired StockServiceGateway stockServiceGateway) {
        QuoteController quoteController = new QuoteController();
        quoteController.setStockServiceGateway(stockServiceGateway);
        return quoteController;
    }

    @Bean
    public ExecutionVenueService executionVenueService(){
        return new ExecutionVenueServiceStub();
    }

    @Bean
    public CreditCheckService creditCheckService(){
        return new CreditCheckServiceStub();
    }

    @Bean
    public TradingService tradingService(){
        return new TradingServiceStub();
    }

    @Bean
    public MarketDataGateway marketDataGateway(){
        RabbitMarketDataGateway marketDataGateway = new RabbitMarketDataGateway();
        return marketDataGateway;
    }

    @Bean
    public StockServiceGateway stockServiceGateway() {
        RabbitStockServiceGateway stockServiceGateway = new RabbitStockServiceGateway();
        return stockServiceGateway;
    }
}
