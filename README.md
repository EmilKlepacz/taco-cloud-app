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

---
* REST api (added manually by org.springframework.web.bind.annotation) <br>
  examples: <br><br> 

firstly please generate access_token with **taco-cloud-auth-server** project.

<b>GET</b>:
```
curl "http://localhost:8080/api/tacos?recent" \
-H "Authorization: Bearer $access_token | jq
```

```
curl "http://localhost:8080/api/tacos?recent&page=0&size=5" \
-H "Authorization: Bearer $access_token" | jq
``` 

```
curl -i "http://localhost:8080/api/tacos/95" \
-H "Authorization: Bearer $access_token" | jq
``` 

<b>POST</b>:
```
curl -X POST http://localhost:8080/api/tacos \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $access_token" \
-d '{
"name": "Veggie Blast",
"ingredients": [
{ "id": "FLTO", "name": "Flour Tortilla", "type": "WRAP" },
{ "id": "TMTO", "name": "Diced Tomatoes", "type": "VEGGIES" },
{ "id": "CHED", "name": "Cheddar", "type": "CHEESE" }
]
}' | jq
 ``` 

<b>PUT</b>:
```
curl -X PUT http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $access_token" \
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
  }' | jq
```

<b>PATCH</b>:
```
curl -X PATCH http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $access_token" \
  -d '{
    "deliveryCity": "Berlin",
    "tacos": [
      {
        "id": 2,
        "name": "Veggie Fiesta",
        "ingredients": [
          { "id": "COTO", "name": "Corn Tortilla", "type": "WRAP" },
          { "id": "LETC", "name": "Lettuce", "type": "VEGGIES" },
          { "id": "SLSA", "name": "Salsa", "type": "SAUCE" }
        ]
      }
    ]
  }' | jq
```

<b>DELETE</b>:
```
curl -s -X DELETE http://localhost:8080/api/orders/1  \
-H "Authorization: Bearer $access_token | jq
```

---

* Spring Data REST: <br>
  to see generated endpoints look at:

```
curl http://localhost:8080/data-api
```

example of usage for template based generated endpoint:
```
curl "http://localhost:8080/data-api/tacoOrders?page=0&size=5"
```

* Mappings generation with MapStruct (binding for Lombok and MapStruct):
    * DTO objects work with REST (REST controllers manually created) 
    * mappings between entities and DTOs. (important: DTOs stored in separate project taco-cloud-dto!)
* REST Controller Tests using junit + Mockito
* Web Controller Tests using junit + @WebMvcTest

* **OAuth2 resource server** for API filtering, ensuring that
  requests for resources that require authorization include a valid access token with the
  required scope. 
* CORS and security configuration (for example You can run
  local Spring Boot app (**taco-cloud-client**) to call http://localhost:8080/api without being blocked.)