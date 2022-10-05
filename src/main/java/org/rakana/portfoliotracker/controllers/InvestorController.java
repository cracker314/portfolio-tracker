package org.rakana.portfoliotracker.controllers;

import org.rakana.portfoliotracker.data.InvestorRepository;
import org.rakana.portfoliotracker.models.Investor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("investors")
public class InvestorController {

    @Autowired  // checks for instances of investorRepository object in the database
    private InvestorRepository investorRepository;

    @GetMapping
    public String displayInvestors(Model model) {
        model.addAttribute("title", "List of Investors");
        model.addAttribute("investors", investorRepository.findAll());
        return "investors/index";
    }

    @GetMapping("add")
    public String displayAddInvestorForm(Model model) {
        model.addAttribute("title", "Add Investor");
        model.addAttribute("investor", new Investor());
        return "investors/add";
    }

    @PostMapping("add")
    public String processAddInvestorForm(@ModelAttribute @Valid Investor newInvestor, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Investor");
            return "investors/add";
        }

        investorRepository.save(newInvestor);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteInvestorsForm(Model model) {
        model.addAttribute("title", "Delete Investors");
        model.addAttribute("investors", investorRepository.findAll());
        return "investors/delete";
    }

    @PostMapping("delete")
    public String processDeleteInvestorsForm(@RequestParam(required = false) int[] investorIds) {
        if (investorIds != null) {
            for(int id : investorIds) {
                investorRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

}
