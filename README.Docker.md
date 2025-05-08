### Building and running taco-cloud:

this app has dependency of th <b>taco-cloud-dto.jar</b> which can be produced
by another project: <b>taco-cloud-dto</b>.

This can be optimized but for now, this .jar needs to be build locally
and then copy to the taco-cloud directory to be used later by Docker
to install this in container's .m2.

Please run firstly:

````bash
cp taco-cloud-dto/target/taco-cloud-dto-1.0-SNAPSHOT.jar taco-cloud/ && \
cp taco-cloud-dto/pom.xml taco-cloud/taco-cloud-dto-pom.xml
````

Then:

````bash
cd taco-cloud
docker compose up --build 
````