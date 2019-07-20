# Installment Loan Calculator
## About the solution
Test code coverage has been checked with Jacoco.
The first installment date is the next month of Loan_Start_Date 


## Libraries & Tools
-	Spring Boot 2.1.6.RELEASE
-	Swagger API
-	Maven


### Main Class :
```
installment_calculator\src\main\java\com\abs\installment_calculator
com.abs.installment_calculator.InstallmentCalculatorApplication.java
```
### Controller:
```
com.abs.installment_calculator.controller.InstallmentCalculatorController
test:
installment_calculator\src\test\java\com\abs\installment_calculator\controller
```
### Model: 
```
com.abs.installment_calculator.model
Instalment
Loan
```
### Service: 
```
com.abs.installment_calculator.service.InstallmentCalculatorServiceImpl
test:
installment_calculator\src\test\java\com\abs\installment_calculator\service
```
### Utility: 
```
com.abs.installment_calculator.utility.LoanUtility
test:
installment_calculator\src\test\java\com\abs\installment_calculator\utility
```

### Input Validator:
```
javax.validation
```
### to run the application:
```
mvn spring-boot:run
```
### to run the test:
```
mvnw test
```
### to check the test code coverage (JACOCO):
```
installment_calculator\target\site\jacoco\index.html 
```
### To See the result:

-	Run the application
-	In browser with swagger:
```
	http://localhost:8080
```
-	With Postman
```
	POST http://localhost:8080/installment-calculator
	Body: {
          "loanAmount": "5000",
          "nominalRate": "5.0",
          "duration": 24,
          "startDate": "2018-01-01T00:00:01Z"
          }
```


