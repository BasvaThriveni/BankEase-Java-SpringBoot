package com.bankease.repository;

import com.bankease.model.Account;
import com.bankease.model.Customer;
import com.bankease.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class BankRepository {
    private final JdbcTemplate jdbc;

    public BankRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public long createCustomer(Customer c) {
        jdbc.update("INSERT INTO customers(name,email,phone) VALUES(?,?,?)",
                c.getName(), c.getEmail(), c.getPhone());
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public long createAccount(Long customerId, String type, BigDecimal initial) {
        jdbc.update("INSERT INTO accounts(customer_id,account_type,balance,status) VALUES(?,?,?,'ACTIVE')",
                customerId, type, initial);
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public Optional<Account> findAccount(Long id) {
        List<Account> list = jdbc.query(
                "SELECT account_id,customer_id,account_type,balance,status FROM accounts WHERE account_id=?",
                (rs, row) -> new Account(
                        rs.getLong("account_id"), rs.getLong("customer_id"),
                        rs.getString("account_type"), rs.getBigDecimal("balance"),
                        rs.getString("status")), id);
        return list.stream().findFirst();
    }

    public int updateBalance(Long id, BigDecimal amount) {
        return jdbc.update("UPDATE accounts SET balance=? WHERE account_id=?",
                amount, id);
    }

    public void insertTransaction(Long from, Long to, BigDecimal amount,
                                   String type, String status) {
        jdbc.update("""
            INSERT INTO transactions(from_account,to_account,amount,
            transaction_type,transaction_date,status)
            VALUES(?,?,?,?,?,?)
            """, from, to, amount, type, Timestamp.valueOf(java.time.LocalDateTime.now()), status);
    }

    public List<Transaction> findTransactions(Long accountId) {
        return jdbc.query("""
            SELECT transaction_id,from_account,to_account,amount,
                   transaction_type,transaction_date,status
            FROM transactions
            WHERE from_account=? OR to_account=?
            ORDER BY transaction_date DESC
            """,
            (rs, row) -> new Transaction(
                    rs.getLong("transaction_id"),
                    rs.getObject("from_account", Long.class),
                    rs.getObject("to_account", Long.class),
                    rs.getBigDecimal("amount"),
                    rs.getString("transaction_type"),
                    rs.getTimestamp("transaction_date").toLocalDateTime(),
                    rs.getString("status")),
            accountId, accountId);
    }
}
