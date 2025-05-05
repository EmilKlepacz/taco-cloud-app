### Taco Cloud Client

This app is created as a reference or some codebase for lookup
while creating some other 'serious' apps.

It contains some features of spring that every starting project could
have for further development with some examples.

...basically it is client using other reference project <b>taco-cloud</b>

* It is running by default on <b>8081</b> port
* during startup the real HTTP Call is performed for <b>dev</b> profile (CommandLineRunner Bean). 
to build without errors simply do for regular builds:

```bash
mvn clean install 
```

* If You want to quickly test API Calls to the <b>taco-cloud</b> server app firstly 
run the server and then
````bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
````

#### What have been added here:
* Clients with the RestTemplate
* httpclient5 added to support more Java 11+ features
