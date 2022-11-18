package org.rakana.portfoliotracker.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Investor extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max= 10, message = "Name cannot be more than 10 characters long")
    private String name;

    @Positive(message = "Please add the investment amount")
    private Integer investment;

    private Integer value;

    @OneToMany(mappedBy = "investor")
    private final List<Portfolio> portfolio = new ArrayList<>();

    @OneToMany(mappedBy = "investor")
    private final List<Transaction> transactions = new ArrayList<>();

    public Investor(String name, Integer investment, Integer value) {
        this.name = name;
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

    public Integer getInvestment() {
        return investment;
    }

    public void setInvestment(Integer investment) {
        this.investment = investment;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
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
