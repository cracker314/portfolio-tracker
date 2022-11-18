package org.rakana.portfoliotracker.service;

import org.rakana.portfoliotracker.models.StockWrapper;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;

@Service
public class StockService {

    public static StockWrapper findStock(final String ticker) {
        try {
            return new StockWrapper(YahooFinance.get(ticker));
        }
        catch (IOException e) {
            System.out.println("Error");
        }
        return null;
    }

    // method not being used presently
    // public static BigDecimal findPrice(final StockWrapper stock) throws IOException {
    //    return stock.getStock().getQuote(true).getPrice();
    // }
}
