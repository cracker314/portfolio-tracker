package org.rakana.portfoliotracker.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Investor extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max= 10, message = "Name cannot be more than 10 characters long")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Positive(message = "Please add the investment amount")
    private Integer investment;

    private Double value;

    @OneToMany(mappedBy = "investor")
    private final List<Portfolio> portfolio = new ArrayList<>();

    @OneToMany(mappedBy = "investor")
    private final List<Transaction> transactions = new ArrayList<>();

    public Investor(String name, LocalDate date, Integer investment, Double value) {
        this.name = name;
        this.date = date;
        this.investment = investment;
        this.value = value;
    }

    public Investor() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getInvestment() {
        return investment;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<Portfolio> getPortfolio() {
        return portfolio;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return name;
    }

}
