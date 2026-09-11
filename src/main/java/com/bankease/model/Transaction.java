package com.bankease.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Long transactionId;
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime transactionDate;
    private String status;

    public Transaction() {}

    public Transaction(Long transactionId, Long fromAccount, Long toAccount,
                       BigDecimal amount, String transactionType,
                       LocalDateTime transactionDate, String status) {
        this.transactionId = transactionId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.status = status;
    }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }
    public Long getFromAccount() { return fromAccount; }
    public void setFromAccount(Long fromAccount) { this.fromAccount = fromAccount; }
    public Long getToAccount() { return toAccount; }
    public void setToAccount(Long toAccount) { this.toAccount = toAccount; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
