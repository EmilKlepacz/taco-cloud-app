### Taco Cloud

This app is created as a reference or some codebase for lookup
while creating some other 'serious' apps.

It contains some features of spring that every starting project could
have for further development with some examples.

#### What have been added here:

* db connection with jpa
* MVC design pattern (view rendered by Thymeleaf)
* validation for form fields
* validation at form binding
* displaying validation errors
* preloading data into tables
* definition for repository interfaces
* Spring Security:
    * authentication
    * o2auth by third party (github)
    * securing web request with SecurityFilterChain
    * custom login page
    * preventing CSRF
    * applying method level security
    * usage of @AuthenticationPrincipal for taking information about authenticated user
* fine-tuning autoconfiguration
* own configuration properties
* REST api (added manually by org.springframework.web.bind.annotation) <br>
  examples:

```bash
curl -s "http://localhost:8080/api/tacos?recent" | jq
```

```bash
curl "http://localhost:8080/api/tacos?recent&page=0&size=5" | jq
``` 

```bash
curl -s "http://localhost:8080/api/tacos/95" | jq 
``` 

```bash
taco-cloud % curl -X POST http://localhost:8080/api/tacos \
-H "Content-Type: application/json" \
-d '{
"name": "Veggie Blast",
"ingredients": [
{ "id": "FLTO", "name": "Flour Tortilla", "type": "WRAP" },
{ "id": "TMTO", "name": "Diced Tomatoes", "type": "VEGGIES" },
{ "id": "CHED", "name": "Cheddar", "type": "CHEESE" }
]
}' 
 ``` 

```bash
curl -X PUT http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{
    "deliveryName": "Emil Klepacz",
    "deliveryStreet": "Main St",
    "deliveryCity": "Warsaw",
    "deliveryState": "MA",
    "deliveryZip": "12345",
    "ccNumber": "4111111111111111",
    "ccExpiration": "12/25",
    "ccCVV": "123",
    "tacos": [
      {
        "id": 1,
        "name": "This one is updated!",
        "ingredients": [
          { "id": "FLTO", "name": "Flour Tortilla", "type": "WRAP" },
          { "id": "CARN", "name": "Carnitas", "type": "PROTEIN" },
          { "id": "CHED", "name": "Cheddar", "type": "CHEESE" }
        ]
      }
    ]
  }'
```

```bash
curl -s -X DELETE http://localhost:8080/api/orders/1 
```

* Spring Data REST:
  to see generated endpoints look at:

```bash
curl http://localhost:8080/data-api
```

example of usage for template based generated endpoint:
```bash
curl "http://localhost:8080/data-api/tacoOrders?page=0&size=5"
```