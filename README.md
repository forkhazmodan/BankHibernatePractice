### Create user
```
curl -XPOST -H "Content-type: application/json" -d '{
    "name":"Test username"
}' 'http://localhost:8080/users'
```

### Create account
```
curl -XPOST -H "Content-type: application/json" -d '{
    "currency":"UAH",
    "user_id":1
}' 'http://localhost:8080/accounts'
```

### Create transaction
```
curl -XPOST -H "Content-type: application/json" -d '{
    "fromAccount":3,
    "toAccount":4,
    "amount":10
}' 'http://localhost:8080/transactions'
```

### Get account balance by account Id and convert to requested currency
```
curl -XGET 'http://localhost:8080/accounts?accountId=3&currency=UAH'
```