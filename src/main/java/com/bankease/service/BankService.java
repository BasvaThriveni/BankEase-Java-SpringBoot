package com.bankease.service;

import com.bankease.exception.AccountNotFoundException;
import com.bankease.exception.InsufficientBalanceException;
import com.bankease.model.Account;
import com.bankease.model.CreateAccountRequest;
import com.bankease.model.Transaction;
import com.bankease.model.TransactionRequest;
import com.bankease.model.Customer;
import com.bankease.repository.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {
    private final BankRepository repository;

    public BankService(BankRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Account createAccount(CreateAccountRequest request) {
        long customerId = repository.createCustomer(
                new Customer(null, request.getName(), request.getEmail(), request.getPhone()));

        long accountId = repository.createAccount(
                customerId, request.getAccountType(), request.getInitialDeposit());

        return repository.findAccount(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account could not be created"));
    }

    public Account getAccount(Long id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new AccountNotFoundException("Account " + id + " not found"));
    }

    @Transactional
    public synchronized String transfer(TransactionRequest request) {
        if (request.getFromAccount().equals(request.getToAccount())) {
            throw new IllegalArgumentException("Source and destination accounts must differ");
        }

        Account from = getAccount(request.getFromAccount());
        Account to = getAccount(request.getToAccount());

        if (!"ACTIVE".equalsIgnoreCase(from.getStatus()) ||
            !"ACTIVE".equalsIgnoreCase(to.getStatus())) {
            throw new IllegalArgumentException("Both accounts must be active");
        }

        BigDecimal amount = request.getAmount();
        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in account " + from.getAccountId());
        }

        repository.updateBalance(from.getAccountId(), from.getBalance().subtract(amount));
        repository.updateBalance(to.getAccountId(), to.getBalance().add(amount));

        repository.insertTransaction(from.getAccountId(), to.getAccountId(),
                amount, "TRANSFER", "SUCCESS");

        return "Transfer completed successfully";
    }

    @Transactional
    public Account deposit(Long id, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        Account account = getAccount(id);
        BigDecimal newBalance = account.getBalance().add(amount);
        repository.updateBalance(id, newBalance);
        repository.insertTransaction(null, id, amount, "DEPOSIT", "SUCCESS");

        return getAccount(id);
    }

    @Transactional
    public Account withdraw(Long id, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        Account account = getAccount(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in account " + id);
        }

        repository.updateBalance(id, account.getBalance().subtract(amount));
        repository.insertTransaction(id, null, amount, "WITHDRAWAL", "SUCCESS");

        return getAccount(id);
    }

    public List<Transaction> transactions(Long id) {
        getAccount(id);
        return repository.findTransactions(id);
    }
}
