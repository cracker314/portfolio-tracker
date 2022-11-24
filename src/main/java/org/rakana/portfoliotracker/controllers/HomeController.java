package org.rakana.portfoliotracker.controllers;

import org.rakana.portfoliotracker.data.InvestorRepository;
import org.rakana.portfoliotracker.data.PortfolioRepository;
import org.rakana.portfoliotracker.data.SecurityRepository;
import org.rakana.portfoliotracker.models.Investor;
import org.rakana.portfoliotracker.models.Portfolio;
import org.rakana.portfoliotracker.models.Security;
import org.rakana.portfoliotracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @GetMapping
    public String index() {

        // updates security prices in security and portfolio repository on starting the app
        Iterable<Security> securities = securityRepository.findAll();

        for (Security security : securities) {
            if (!security.getName().equals("Cash")) {

                Double price = StockService.findStock(security.getTicker()).getStock().getQuote().getPrice().doubleValue();

                security.setCurrentPrice(price);
                securityRepository.save(security);

                List<Portfolio> portfolio = security.getPortfolio();
                for (Portfolio portfolioItem : portfolio) {
                    portfolioItem.setCurrentPrice(price);
                    portfolioItem.setValue(portfolioItem.getQuantity() * price);
                    portfolioRepository.save(portfolioItem);
                }

            }
        }

        // updates portfolio value of investor
        Iterable<Investor> investors = investorRepository.findAll();

        for (Investor investor : investors) {

            Double value = 0.0;
            List<Portfolio> portfolio = investor.getPortfolio();

            for (Portfolio portfolioItem : portfolio) {
                value = value + portfolioItem.getValue();
            }

            investor.setValue(value);
            investorRepository.save(investor);
        }

        return "index";
    }

}
