# Getting Started
#### Run Application, it will run on 9100 port
```aidl
$ mvn spring-boot:run
```

#### Run Test, it will run 6 unit testing
```aidl
$ mvn test
```

## H2 
### The table schme in the /resource folder
 
# API

### GET /currency
Get the currency from database

---

### POST /currency
Add currencies to database

Request Body:
```json
[
    {
        "countryName": "英鎊",
        "countryCode": "GBP"
    },
    {
        "countryName": "歐元",
        "countryCode": "EUR"
    },
    {
        "countryName": "美金",
        "countryCode": "USD"
    }
]
```

---


### PUT /currency
update the currency from database

Request Body:
```json
{
  "countryName": "英鎊1",
  "countryCode": "GBP"
}
```
---

### DELETE /currency?code=USD
delete the currency from database

---

### GET /coindesk
Get coindesk data from coindesk API

---

### GET /coindesk/transfer
Get transferred coindesk data from coindesk API

---
