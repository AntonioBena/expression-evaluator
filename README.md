# Expression Evaluator
## Short description
This application is a simple REST service that evaluates simple and complex expressions
without using any third party library

## Running the application

1. Go to the source folder of expression evaluator
2. Inside folder open cmd or shell and type: mvn spring-boot:run

## Expressions

### Rest Endpoint
Base url: http://localhost:8009

**name** and **value** are mandatory. Expression with same name can not be created

| METHOD | URL |                         DESCRIPTION                         |
|--------|:-------------|:-----------------------------------------------------------:|
| Post   | /expression | Creates expression with expression request and returns uuid |

### Expression format

You can use simple or complex expressions

Post will return created expression uuid eg. 469870fa-e542-4cbd-a8ad-1b76f2fc0d54

Simple expression format: "(variableName CO variableValue LO variableName CO variableValue)"

Complex expression format:
"(variableName CO variableValue LO variableName CO variableValue) LON (variableName CO variableValue LO variableName CO variableValue)"

#### CO - comparison operator (==, !=, <, >, <=, >=)
#### LO - logical operator (&&, ||, !)
#### LON - logical operator with name (AND, OR, NOT)

### Example JSON

Simple Expression:
```json
{
  "name": "simple expression",
  "value": "(customer.firstName == JOHN && customer.salary < 100)"
}
```
Complex Expression:
```json
{
  "name": "complex expression",
  "value": "(customer.firstName == JOHN && customer.salary < 100) NOT (customer.address != null && customer.address.city == Washington)"
}
```

## Evaluation

### Rest Endpoint
Base url: http://localhost:8009

| METHOD | URL | PARAMETER       | REQUEST BODY | DESCRIPTION                                                                                                          |
|--------|-----|-----------------|--------------|----------------------------------------------------------------------------------------------------------------------|
| Post   | /evaluate | expression UUID | json object  | Evaluates json request with given expression - returnes string with expression and evaluation result (true or false) |

### Example JSON

```json
{
  "customer":
  {
    "firstName": "John",
    "lastName": "DOE",
    "address":
    {
      "city": "Chicago",
      "zipCode": "12345",
      "street": "56th",
      "houseNumber": 23
    },
    "salary": 99,
    "type": "BUSINESS"
  }
}
```
type also can be "INDIVIDUAL"

## Database

Database used in this project is H2 in memory database with create-drop parameter.

**Note that this is for educational purposes and credentials are written in application.properties**


## Authors
Antonio Benković