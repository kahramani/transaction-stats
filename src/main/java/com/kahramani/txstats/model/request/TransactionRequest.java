package com.kahramani.txstats.model.request;

public class TransactionRequest {

    private Double amount;
    private Long timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" + "amount=" + amount + ", timestamp=" + timestamp + '}';
    }
}
