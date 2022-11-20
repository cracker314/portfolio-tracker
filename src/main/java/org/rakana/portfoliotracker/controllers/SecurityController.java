package org.rakana.portfoliotracker.controllers;

import org.rakana.portfoliotracker.data.SecurityRepository;
import org.rakana.portfoliotracker.models.Security;
import org.rakana.portfoliotracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("securities")
public class SecurityController {

    @Autowired
    private SecurityRepository securityRepository;

    @GetMapping
    public String displaySecurities(Model model) {
        model.addAttribute("title", "List of Securities");
        model.addAttribute("securities", securityRepository.findAll());
        return "securities/index";
    }

    @GetMapping("add")
    public String displayAddSecurityForm(Model model) {
        model.addAttribute("title", "Add Security");
        model.addAttribute("security", new Security());
        return "securities/add";
    }

    @PostMapping("add")
    public String processAddSecurityForm(@ModelAttribute @Valid Security newSecurity, Errors errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Add Security");
            return "securities/add";
        }

        if (newSecurity.getName().equals("Cash")) {
            newSecurity.setCurrentPrice(1);
        }
        else {
            Integer price = StockService.findStock(newSecurity.getTicker()).getStock().getQuote().getPrice().intValue();
            newSecurity.setCurrentPrice(price);
        }

        securityRepository.save(newSecurity);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteSecuritiesForm(Model model) {
        model.addAttribute("title", "Delete Securities");
        model.addAttribute("securities", securityRepository.findAll());
        return "securities/delete";
    }

    @PostMapping("delete")
    public String processDeleteSecuritiesForm(@RequestParam(required = false) int[] securityIds) {
        if (securityIds != null) {
            for(int id : securityIds) {
                securityRepository.deleteById(id);
            }
        }

        return "redirect:";
    }

}
