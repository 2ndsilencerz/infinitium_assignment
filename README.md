How to run the project

1. Make sure JDK 21 is installed
2. Run command ```./gradlew bootRun``` in the project root directory (where gradlew file reside)
    * If opened from IntelliJ IDEA, you can just click play button (usually on top right position)
    * If you run it on Windows, use ```./gradlew.bat bootRun``` instead
3. Access the API via port 8080
    * Or you can use the integrated Swagger by accessing ```http://localhost:8080/swagger-ui.html``` via browser

List of account that can be used are in ```account_list.json``` file

Conversion rate are set on ```model/Currency.java``` which based on USD

The conversion value:

1. USD : IDR = 1 : 15000
2. USD : MYR = 1 : 5

Sample case

```
Send from accountID 1 with amount 1 USD
accountID 1 have 1000000 balance and its main currency is IDR
```


Sample request
```
{
   "accountID": 1,         // Long type
   "amount": 1,            // Double type
   "currency": "USD",      // One of this value: IDR, USD, MYR. Any other value would failing the transaction. The value should be the receiver currency
}
```

Sample response

```
{
   "account": {
      "accountID": 1,      // Long type, value is the sender account ID
      "amount": 985000,    // Double type, value is the remaining balance
      "currency": "IDR"    // One of this value: IDR, USD, MYR. Value are the account main currency
   },
   "status": "SUCCESS",    // Value is either SUCCESS or FAILED
   "message": "",          // String type, only filled if status is FAILED to explain the failure
}
```

The remaining balance is saved to the database which is in ```account_list.json``` file 