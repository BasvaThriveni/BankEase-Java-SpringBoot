# BankEase – Banking Transaction Management System

A Java and Spring Boot banking application that provides REST APIs and a simple web interface for basic banking operations, with MySQL for data persistence.

## Features

- Create customer and bank accounts
- View account and balance
- Deposit and withdraw money
- Transfer funds between accounts
- View transaction history
- MySQL database persistence using JDBC
- REST APIs with JSON
- Input validation and custom exception handling
- Synchronized transfer operation for safer concurrent transactions
- Simple browser-based web interface

## Technologies

- Java 17
- Spring Boot
- Spring JDBC
- MySQL
- REST APIs
- Maven
- HTML
- CSS
- JavaScript

## Project Structure

BankEase/
|-- src/
|   |-- main/
|       |-- java/
|       |   |-- com/bankease/
|       |       |-- controller/
|       |       |-- exception/
|       |       |-- model/
|       |       |-- repository/
|       |       |-- service/
|       |       |-- BankEaseApplication.java
|       |
|       |-- resources/
|           |-- static/
|           |   |-- index.html
|           |-- application.properties
|           |-- schema.sql
|
|-- pom.xml
|-- README.md
|-- test-api.sh

## Requirements

- JDK 17 or later
- Maven 3.8 or later
- MySQL 8 or later
- Web browser

## Database Setup

Create a MySQL database named `bankease`.

```sql
CREATE DATABASE bankease;
```

The database schema is available in:

`src/main/resources/schema.sql`

Configure your MySQL username and password in:

`src/main/resources/application.properties`

Example:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

Do not commit your actual database password to GitHub.

## Running the Application

From the project directory, run:

```bash
mvn spring-boot:run
```

The application runs on:

`http://localhost:8081`

Open the above URL in a web browser to use the BankEase web interface.

## API Endpoints

### Create Account

POST `/api/accounts`

Example request:

```json
{
  "name": "Rahul Kumar",
  "email": "rahul@example.com",
  "phone": "9876543210",
  "accountType": "SAVINGS",
  "initialDeposit": 10000
}
```

### Get Account

GET `/api/accounts/1`

### Deposit

POST `/api/accounts/1/deposit?amount=2000`

### Withdraw

POST `/api/accounts/1/withdraw?amount=1000`

### Transfer Money

POST `/api/transactions/transfer`

Example request:

```json
{
  "fromAccount": 1,
  "toAccount": 2,
  "amount": 1500
}
```

### Transaction History

GET `/api/accounts/1/transactions`

## Application Architecture

Web Interface (HTML/CSS/JavaScript)
        |
        v
Spring Boot REST Controller
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
MySQL Database

## Testing

The REST APIs can be tested using:

- Browser
- curl
- Postman

The project also includes:

`test-api.sh`

for API testing.

## Web Interface

The application includes a browser-based interface located at:

`src/main/resources/static/index.html`

After starting the application, open:

`http://localhost:8081`

The interface provides options for:

- Creating accounts
- Depositing money
- Withdrawing money
- Transferring money
- Viewing transaction history

## Important Configuration

The default configuration used during local development is:

- Spring Boot application port: `8081`
- MySQL port: `3307`
- Database name: `bankease`

If your MySQL server uses a different port, update the database URL in:

`src/main/resources/application.properties`

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/bankease?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata
```

## Author

Thriveni Basva
