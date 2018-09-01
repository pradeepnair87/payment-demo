# payment-demo
Transfer fund Demo
==================
This is a SpringBoot application.
IDE used: IntelliJ
Refer https://projectlombok.org/setup/eclipse (if you are using eclipse) 

Assumptions:
Account IDs are numeric and unique.
Account names should be unique.

Validations:
1.From and To Accounts should exist in the system.
2.For fund transfer, From and To Accounts can't be same.
3.Amount should positive long value.

Apis:
1. Create User Account
  uri: /v1/createAccount
  request type: PUT
  Paramerters:
   String guestName
   Long intialDeposit
   
2.View User Account
  uri: /v1/viewAccount/{name}
  request type: GET
  Parameters:
    name
    
3.Transfer Fund
  uri: /v1/transferFund
  request Type: POST
  Parameters:
    {
      "fromAccount" : "",
      "toAccount" : "",
      "amount" :
    }
    
 4.View History of Transaction
  uri : /v1/transferFund
  request type: GET
  
  How to run:
  
  1. Navigate to the project folder
  2.  ./gradlew clean build
  3.  java -jar build/libs/payment-core-1.0-SNAPSHOT.jar
  
  application starts on 8080 port
  
  
  
  


