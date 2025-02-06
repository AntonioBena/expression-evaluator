# Expression Evaluator
## Short description
This application is a simple REST service that evaluates simple and complex expressions
without using any third party library

## Running the application
1. Go to the source folder of expression evaluator
2. Inside folder open cmd or shell and type: **mvn spring-boot:run**

### Requirements
1. Maven 3 and Java 21

## Expressions

### Rest Endpoint
Base url: http://localhost:8009

**name** and **value** are mandatory. Expression with same name can not be created

| METHOD  | URL | REQUEST BODY    |  DESCRIPTION |
|---|-----|-----------------|---|
| Post  | /expression   | expression json | Creates expression with expression request and returns uuid  |


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
```json
{
  "name": "complex expression long with not",
  "value": "(vehicle.model == Mercedes && vehicle.kmDriven < 10000) AND (vehicle.vehicleExtendedInfo.wheelsSize != 20 && vehicle.vehicleExtendedInfo.frontTyreInfo.tireType == MUD_AND_SNOW)"
}
```

## Evaluation

### Rest Endpoint
Base url: http://localhost:8009

| METHOD | URL | PARAMETER       | REQUEST BODY | DESCRIPTION                                                                                                          |
|--------|-----|-----------------|--------------|----------------------------------------------------------------------------------------------------------------------|
| Post   | /evaluate | expression UUID | json object  | Evaluates json request with given expression - returnes string with expression and evaluation result (true or false) |

### Example JSON

#### Evaluation jsons are model independent

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
```json
{
  "vehicle": {
    "model": "Mercedes",
    "kmDriven": "1000",
    "isNew": "isNew",
    "vehicleOwner": "owner",
    "vehicleExtendedInfo": {
      "wheelsSize": 19,
      "exhaustType": "SPORT",
      "isKmh": true,
      "frontTyreInfo": {
        "tireType": "MUD_AND_SNOW",
        "tireWidth": 225,
        "tireHeight": 40,
        "tireDiameter": 19
      },
      "backTyreInfo": {
        "frontTyreInfo": {
          "tireType": "MUD_AND_SNOW",
          "tireWidth": 255,
          "tireHeight": 35,
          "tireDiameter": 19
        }
      }
    }
  }
}
```

## Database

Database used in this project is H2 in memory database with create-drop parameter.

**Note that this is for educational purposes and credentials are written in application.properties**


## Authors
Antonio Benković