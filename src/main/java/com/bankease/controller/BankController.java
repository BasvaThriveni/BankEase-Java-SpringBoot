package com.bankease.controller;

import com.bankease.model.Account;
import com.bankease.model.CreateAccountRequest;
import com.bankease.model.Transaction;
import com.bankease.model.TransactionRequest;
import com.bankease.service.BankService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BankController {
    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @PostMapping("/accounts")
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return service.createAccount(request);
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable Long id) {
        return service.getAccount(id);
    }

    @GetMapping("/accounts/{id}/balance")
    public Map<String, Object> getBalance(@PathVariable Long id) {
        Account account = service.getAccount(id);
        return Map.of("accountId", id, "balance", account.getBalance());
    }

    @PostMapping("/accounts/{id}/deposit")
    public Account deposit(@PathVariable Long id,
                           @RequestParam BigDecimal amount) {
        return service.deposit(id, amount);
    }

    @PostMapping("/accounts/{id}/withdraw")
    public Account withdraw(@PathVariable Long id,
                            @RequestParam BigDecimal amount) {
        return service.withdraw(id, amount);
    }

    @PostMapping("/transactions/transfer")
    public Map<String, String> transfer(@Valid @RequestBody TransactionRequest request) {
        return Map.of("status", "SUCCESS", "message", service.transfer(request));
    }

    @GetMapping("/accounts/{id}/transactions")
    public List<Transaction> transactions(@PathVariable Long id) {
        return service.transactions(id);
    }
}
