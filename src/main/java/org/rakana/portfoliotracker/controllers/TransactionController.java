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
            return "redirect:";
        }

        Integer quantity = newTransaction.getQuantity();
        Integer transactedPrice = newTransaction.getTransactedPrice();
        TransactionAction action = newTransaction.getAction();
        Investor investor = newTransaction.getInvestor();
        Security security = newTransaction.getSecurity();

        // to check why capital field is not getting populated already
        Integer capital = quantity * transactedPrice * action.getValue() * -1;
        newTransaction.setCapital(capital);
        transactionRepository.save(newTransaction);

        // updating the portfolioRepository
        Optional<Portfolio> result = portfolioRepository.findByInvestorAndSecurity(investor, security);
        if (result.isEmpty()) {
            Portfolio newPortfolio = new Portfolio(investor, security, quantity * action.getValue());
            portfolioRepository.save(newPortfolio);
        } else {
            Portfolio portfolio = result.get();
            Integer existingQuantity = portfolio.getQuantity();
            portfolio.setQuantity(existingQuantity + (quantity * action.getValue()));
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
                Optional<Transaction> result = transactionRepository.findById(id);
                Transaction transaction = result.get();

                Integer quantity = transaction.getQuantity();
                TransactionAction action = transaction.getAction();
                Investor investor = transaction.getInvestor();
                Security security = transaction.getSecurity();

                // updating the portfolioRepository
                Optional<Portfolio> portfolioOptional = portfolioRepository.findByInvestorAndSecurity(investor, security);
                Portfolio portfolio = portfolioOptional.get();
                Integer existingQuantity = portfolio.getQuantity();
                portfolio.setQuantity(existingQuantity + (quantity * action.getValue()) * -1);

                portfolioRepository.save(portfolio);
                transactionRepository.deleteById(id);
            }
        }
        return "redirect:";
    }

}
