package org.rakana.portfoliotracker.controllers;

import org.rakana.portfoliotracker.data.InvestorRepository;
import org.rakana.portfoliotracker.data.PortfolioRepository;
import org.rakana.portfoliotracker.data.SecurityRepository;
import org.rakana.portfoliotracker.data.TransactionRepository;
import org.rakana.portfoliotracker.models.*;
import org.rakana.portfoliotracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private InvestorRepository investorRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @GetMapping
    public String displayTransactions(Model model) {
        model.addAttribute("title", "List of Transactions");
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions/index";
    }

    @GetMapping("add")
    public String displayAddTransactionForm(Model model) {
        model.addAttribute("title", "Add Transaction");
        model.addAttribute("investors", investorRepository.findAll());
        model.addAttribute("securities", securityRepository.findAll());
        model.addAttribute("actions", TransactionAction.values());
        model.addAttribute("transaction", new Transaction());
        return "transactions/add";
    }

    @PostMapping("add")
    public String processAddTransactionForm(@ModelAttribute @Valid Transaction newTransaction, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Add Transaction");
            return "redirect:";
        }

        // Initial declarations
        Integer quantity = newTransaction.getQuantity();
        Integer transactedPrice = newTransaction.getTransactedPrice();
        TransactionAction action = newTransaction.getAction();
        Investor investor = newTransaction.getInvestor();
        Security security = newTransaction.getSecurity();
        Security cashSecurity = securityRepository.findByName("Cash");

        // update transaction repository
        Integer transactionValue = quantity * transactedPrice * action.getValue() * -1;
        newTransaction.setValue(transactionValue);
        transactionRepository.save(newTransaction);

        // update portfolio repository after checking if security already exists in investor portfolio
        // update investment and value in investor repository if security is cash
        Optional<Portfolio> result = portfolioRepository.findByInvestorAndSecurity(investor, security);
        // if result is empty it means the security is an actual stock and not cash
        if (result.isEmpty()) {
            Integer price = StockService.findStock(security.getTicker()).getStock().getQuote().getPrice().intValue();
            Portfolio newPortfolio = new Portfolio(investor, security, quantity, price, quantity * price);
            portfolioRepository.save(newPortfolio);
        } else if (!security.getName().equals("Cash")) {
            Portfolio portfolio = result.get();
            Integer existingQuantity = portfolio.getQuantity();
            portfolio.setQuantity(existingQuantity + (quantity * action.getValue())); // TODO: insert limitation of not being able to sell more than existing quantity
            portfolioRepository.save(portfolio);
        } else {
            investor.setInvestment(investor.getInvestment() + transactionValue);
            investor.setValue(investor.getValue() + transactionValue);
            investorRepository.save(investor);
        }

        // updating the cash for the investor in portfolio repository
        Portfolio cashPortfolio = portfolioRepository.findByInvestorAndSecurity(investor, cashSecurity).get();
        Integer cashPortfolioQuantity = cashPortfolio.getQuantity();
        cashPortfolio.setQuantity(cashPortfolioQuantity + transactionValue);
        cashPortfolio.setValue(cashPortfolioQuantity + transactionValue);
        portfolioRepository.save(cashPortfolio);

        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteTransactionForm(Model model) {
        model.addAttribute("title", "Delete Transactions");
        model.addAttribute("transactions", transactionRepository.findAll());
        return "transactions/delete";
    }

    @PostMapping("delete")
    public String processDeleteTransactionsForm(@RequestParam(required = false) int[] transactionIds) {
        if (transactionIds != null) {
            for(int id : transactionIds) {
                Optional<Transaction> result = transactionRepository.findById(id);
                Transaction transaction = result.get();

                Integer quantity = transaction.getQuantity();
                TransactionAction action = transaction.getAction();
                Investor investor = transaction.getInvestor();
                Security security = transaction.getSecurity();
                Security cashSecurity = securityRepository.findByName("Cash");
                Integer transactionValue = transaction.getValue();

                // updating the stock security in portfolioRepository
                Portfolio portfolio = portfolioRepository.findByInvestorAndSecurity(investor, security).get();
                Integer existingQuantity = portfolio.getQuantity();
                portfolio.setQuantity(existingQuantity + (quantity * action.getValue()) * -1);

                // updating the cash security in portfolioRepository
                Portfolio cashPortfolio = portfolioRepository.findByInvestorAndSecurity(investor, cashSecurity).get();
                Integer cashPortfolioQuantity = cashPortfolio.getQuantity();
                cashPortfolio.setQuantity(cashPortfolioQuantity - transactionValue);

                portfolioRepository.save(portfolio);
                portfolioRepository.save(cashPortfolio);
                transactionRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

}
