package org.rakana.portfoliotracker.models;

import yahoofinance.Stock;

import java.time.LocalDateTime;

// Object to hold the stock data received from Yahoo Finance API which will be used by StockService
public class StockWrapper {

    private final Stock stock;
    private final LocalDateTime lastAccessed;

    public StockWrapper(final Stock stock) {
        this.stock = stock;
        this.lastAccessed = LocalDateTime.now();
    }

    public Stock getStock() {
        return stock;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }
}
