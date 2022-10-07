package org.rakana.portfoliotracker.models;

public enum TransactionAction {

    BUY("Buy"),
    SELL("Sell");

    private final String displayName;

    TransactionAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
