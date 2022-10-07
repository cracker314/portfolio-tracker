package org.rakana.portfoliotracker.controllers;

import org.rakana.portfoliotracker.data.InvestorRepository;
import org.rakana.portfoliotracker.data.PortfolioRepository;
import org.rakana.portfoliotracker.data.SecurityRepository;
import org.rakana.portfoliotracker.data.TransactionRepository;
import org.rakana.portfoliotracker.models.*;
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
            return "transactions/add";
        }

        // setting the transaction capital value and updating transactionRepository
        Integer capital;
        Integer quantity = newTransaction.getQuantity();
        if (newTransaction.getAction().getDisplayName().equals("Sell")) {
            quantity = newTransaction.getQuantity() * -1;
            newTransaction.setQuantity(quantity);
        }
        capital = newTransaction.getQuantity() * newTransaction.getTransactedPrice() * -1;
        newTransaction.setCapital(capital);
        transactionRepository.save(newTransaction);

        // updating the portfolioRepository
        Investor investor = newTransaction.getInvestor();
        Security security = newTransaction.getSecurity();
        Optional<Portfolio> result = portfolioRepository.findByInvestorAndSecurity(investor, security);
        if (result.isEmpty()) {
            Portfolio newPortfolio = new Portfolio(investor, security, quantity);
            portfolioRepository.save(newPortfolio);
        } else {
            Portfolio portfolio = result.get();
            Integer existingQuantity = portfolio.getQuantity();
            portfolio.setQuantity(existingQuantity + quantity);
            portfolioRepository.save(portfolio);
        }

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
                transactionRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

}
