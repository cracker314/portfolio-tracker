package org.rakana.portfoliotracker.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Security extends AbstractEntity {

    @NotBlank(message = "Name is required")
    @Size(max= 20, message = "Name cannot be more than 20 characters long")
    private String name;

    @NotBlank(message = "Ticker symbol is required")
    private String ticker;

    @OneToMany(mappedBy = "security")
    private List<Portfolio> portfolio;

    @OneToMany(mappedBy = "security")
    private List<Transaction> transactions;

    public Security(String name, String ticker) {
        this.name = name;
        this.ticker = ticker;
    }

    public Security() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() { return ticker; }

    public void setTicker(String ticker) { this.ticker = ticker; }

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
