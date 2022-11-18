package org.rakana.portfoliotracker.models;

public enum TransactionAction {

    // enum value related to quantity ie selling leads to lesser quantity and vice versa; the opposite is true for transaction value
    BUY("Buy/Redeem", 1),
    SELL("Sell/Invest", -1);

    private final String displayName;

    private final Integer value;

    TransactionAction(String displayName, Integer value) {
        this.displayName = displayName;
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Integer getValue() {
        return value;
    }
}
