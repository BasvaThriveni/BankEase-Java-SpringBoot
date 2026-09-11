# BankEase – Banking Transaction Management System

A Java + Spring Boot + MySQL backend application for basic banking operations.

## Features
- Create customer and bank accounts
- View account and balance
- Deposit and withdraw
- Transfer funds between accounts
- Transaction history
- JDBC/MySQL persistence
- REST APIs with JSON
- Validation and custom exception handling
- Synchronized transfer operation for safe concurrent access

## Requirements
- JDK 17+
- Maven 3.8+
- MySQL 8+
- IDE such as IntelliJ IDEA, Eclipse, or VS Code

## Database
Create a MySQL database named `bankease`. The application schema script creates the required tables automatically.

If your MySQL root password is not empty, edit:
`src/main/resources/application.properties`

Set:
`spring.datasource.password=YOUR_PASSWORD`

## Run
From the project directory:

```bash
mvn spring-boot:run
```

The server starts at:
`http://localhost:8080`

## API examples

### Create account
POST `http://localhost:8080/api/accounts`

```json
{
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "phone": "9876543210",
  "accountType": "SAVINGS",
  "initialDeposit": 10000
}
```

### Get account
GET `http://localhost:8080/api/accounts/1`

### Deposit
POST `http://localhost:8080/api/accounts/1/deposit?amount=2000`

### Withdraw
POST `http://localhost:8080/api/accounts/1/withdraw?amount=1000`

### Transfer
POST `http://localhost:8080/api/transactions/transfer`

```json
{
  "fromAccount": 1,
  "toAccount": 2,
  "amount": 1500
}
```

### Transaction history
GET `http://localhost:8080/api/accounts/1/transactions`

## Suggested testing
Use Postman or curl to test the REST endpoints.
