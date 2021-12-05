# customer-rewards-rest-service
This API resolves below problem

# Problem statement:
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a three-month period, calculate the reward points earned for each customer per month and total.

---

# Technical Notes
+ This API is developed using Java 1.8, Spring boot 2.6.1, Maven and REST standards
+ H2 in-memory database is used to store the data. On application boot up, tables will be created and data will be loaded.
+ All endpoints are authenticated using Basic Auth. These authentication credentials are added in application.properties files. 
+ Unit test cases were written using JUnit5 and Mockito frameworks which covers around 98% of the code.
+ Swagger docs are uploaded in root folder for reference

---

# Steps to setup

**1. Clone the application**
```bash
git clone https://github.com/saikumarKodakanchi/customer-rewards-rest-service.git
```
**2. Run app using below command**
```bash
mvn spring-boot: run
```
Application will start at <http://localhost:8080> and can be accessed using basic auth credentials admin/secret

# REST API's
**Request URI**
http://localhost:8080/v1/customers/{customerId}/rewardsummary?startDate={YYYY-MM-DD}&endDate={YYYY-MM-DD}

**Sample Request cURL:**
```bash
curl --location --request GET 'http://localhost:8080/v1/customers/1/rewardsummary?startDate=2021-09-01&endDate=2021-12-01' \
--header 'Authorization: Basic YWRtaW46c2VjcmV0' \
```
**Validations on request - Bad Request**
1. customer id should be non-negative natural number
2. start and end dates should be in YYYY-MM-DD format
3. start Date should be less than end Date
4. A customer with the customer id should be present in database

**Successful Response:**
```json
{
    "message": {
        "code": "200",
        "type": "OK",
        "description": "Reward Summary successfully retrieved"
    },
    "rewards": {
        "customerId": 1,
        "name": "John Doe",
        "totalRewardCount": 832,
        "monthlyRewards": [
            {
                "month": "Sep-2021",
                "rewardsCount": 32
            },
            {
                "month": "Oct-2021",
                "rewardsCount": 365
            },
            {
                "month": "Nov-2021",
                "rewardsCount": 435
            }
        ]
    }
}
```

**Bad Request Response:**
```json
{
    "message": {
        "code": "400",
        "type": "Bad Request",
        "description": "Customer not found."
    }
}
```

**Not found Response - when there are no transactions for the provided search date range:**
```json
{
    "message": {
        "code": "404",
        "type": "Not Found",
        "description": "No transactions found for the customer with provided search date range."
    }
}
```
