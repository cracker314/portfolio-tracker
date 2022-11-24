package org.rakana.portfoliotracker.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Transaction extends AbstractEntity {

    @ManyToOne
    @NotNull(message = "Investor selection is required")
    private Investor investor;

    @ManyToOne
    @NotNull(message = "Security selection is required")
    private Security security;

//    @NotNull(message = "Date is required")
//    @PastOrPresent(message= "Input needs to be a date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Transacted quantity is required")
    private Integer quantity;

    @NotNull(message = "Transacted price needs to be a positive number")
    private Double transactedPrice;

    @NotNull(message = "Action selection is required")
    private TransactionAction action;

    private Double value;

    public Transaction(Investor investor, Security security, LocalDate date, Integer quantity, Double transactedPrice, TransactionAction action, Double value) {
        this.investor = investor;
        this.security = security;
        this.date = date;
        this.quantity = quantity;
        this.transactedPrice = transactedPrice;
        this.action = action;
        this.value = value;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTransactedPrice() {
        return transactedPrice;
    }

    public void setTransactedPrice(Double transactedPrice) {
        this.transactedPrice = transactedPrice;
    }

    public TransactionAction getAction() {
        return action;
    }

    public void setAction(TransactionAction action) {
        this.action = action;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "This transaction id is " + this.getId();
    }

}
