# Webapp for codeing test

This is a simple webapp, it is just for test. It uses a embedded database H2.
Once the webapp is started (even if in junit test), the embedded database is reinitiated. A backend initiator(task) will retrieves data from https://zalora.com, and put 60 results, i.e. Product(id,name,brand,price,and default image) in the local database.

## Start webapp
> mvn jetty:run

8080 is http port by default.
The user can access these data stored in the database using restful api:
http://localhost:8080/myapp/rest/v1/products

There are some optional query parameters:
  - pageSize: integer, 5 by default  
  - pageNumber: integer, 0 by default
  - sort.direction: enum, [ASC,DESC], case sensitive, ASC by default 
  - sort.property: string, [name,brand,price,image]

usage:
> http://localhost:8080/myapp/rest/v1/products?pageSize=10&pageNumber=1&sort.direction=DESC&sort.property=name

## @dev
This test project is using Spring, Spring-Data-JPA, CXF, and H2.
We uses lombok to generate setter,getter in javabeans. The lombok is used just only in compile-time. If you wanna avoid error highlight in IDE, please install lombok in your IDE. See [lombok] for more information.

[lombok]: https://projectlombok.org/

