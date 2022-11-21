package org.rakana.portfoliotracker.service;

import org.rakana.portfoliotracker.models.Security;
import org.rakana.portfoliotracker.models.StockWrapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class RefreshService {

//    private final List<Security> securities;
//
//    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//    private static final Duration refreshPeriod = Duration.ofSeconds(15);
//
//    public RefreshService() {
//        securities = new ArrayList<>();
//        setRefreshEvery15Minutes();
//    }
//
//    private void setRefreshEvery15Minutes() {
//        scheduler.scheduleAtFixedRate(() ->
//                securities.forEach((security) -> {
//                    StockWrapper stock = StockService.findStock(security.getTicker());
//                    if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshPeriod))) {
//                        System.out.println("Setting should refresh " + stock.getStock().getSymbol());
//                    }
//                });
//        );
//    }

}
