package org.rakana.portfoliotracker.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Transaction extends AbstractEntity {

    @ManyToOne
    @NotNull(message = "Investor selection is required")
    private Investor investor;

    @ManyToOne
    @NotNull(message = "Security selection is required")
    private Security security;

    @NotNull(message = "Transacted quantity is required")
    private Integer quantity;

    @Positive(message = "Transacted price needs to be a positive number")
    private Integer transactedPrice;

    @NotNull(message = "Action selection is required")
    private TransactionAction action;

    private Integer capital;

    public Transaction(Investor investor, Security security, Integer quantity, Integer transactedPrice, TransactionAction action) {
        this.investor = investor;
        this.security = security;
        this.quantity = quantity;
        this.transactedPrice = transactedPrice;
        this.action = action;
        this.capital = quantity * transactedPrice * action.getValue();
    }

    public Transaction() {}

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

    public Integer getTransactedPrice() {
        return transactedPrice;
    }

    public void setTransactedPrice(Integer transactedPrice) {
        this.transactedPrice = transactedPrice;
    }

    public TransactionAction getAction() {
        return action;
    }

    public void setAction(TransactionAction action) {
        this.action = action;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "This transaction id is " + this.getId();
    }

}
