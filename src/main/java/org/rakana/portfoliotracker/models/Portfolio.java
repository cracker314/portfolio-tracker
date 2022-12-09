package org.rakana.portfoliotracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Portfolio extends AbstractEntity {

    @ManyToOne
    @NotNull
    private Investor investor;

    @ManyToOne
    @NotNull
    private Security security;

    @NotNull
    @PositiveOrZero(message = "Quantity needs to be positive")
    private Integer quantity;

    @NotNull
    private Double currentPrice;

    private Double value;

    public Portfolio(Investor investor, Security security, Integer quantity, Double currentPrice, Double value) {
        this.investor = investor;
        this.security = security;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.value = value;
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

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "This portfolio id is " + this.getId();
    }

}
