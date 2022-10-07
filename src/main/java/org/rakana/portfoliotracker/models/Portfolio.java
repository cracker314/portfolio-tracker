package org.rakana.portfoliotracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Portfolio extends AbstractEntity {

    @ManyToOne
    @NotNull
    private Investor investor;

    @ManyToOne
    @NotNull
    private Security security;

    @NotNull
    private Integer quantity;

    public Portfolio(Investor investor, Security security, Integer quantity) {
        this.investor = investor;
        this.security = security;
        this.quantity = quantity;
    }

    public Portfolio() {}

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "This portfolio id is " + this.getId();
    }

}
