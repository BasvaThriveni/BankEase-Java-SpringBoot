#!/bin/bash
BASE="http://localhost:8080/api"

echo "Creating account 1..."
curl -s -X POST "$BASE/accounts" \
  -H "Content-Type: application/json" \
  -d '{"name":"Rahul Kumar","email":"rahul@example.com","phone":"9876543210","accountType":"SAVINGS","initialDeposit":10000}'
echo

echo "Creating account 2..."
curl -s -X POST "$BASE/accounts" \
  -H "Content-Type: application/json" \
  -d '{"name":"Priya Sharma","email":"priya@example.com","phone":"9876543211","accountType":"SAVINGS","initialDeposit":5000}'
echo

echo "Account 1..."
curl -s "$BASE/accounts/1"
echo

echo "Transfer..."
curl -s -X POST "$BASE/transactions/transfer" \
  -H "Content-Type: application/json" \
  -d '{"fromAccount":1,"toAccount":2,"amount":1500}'
echo

echo "Transactions for account 1..."
curl -s "$BASE/accounts/1/transactions"
echo
