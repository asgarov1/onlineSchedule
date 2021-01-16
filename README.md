# University Schedule

This is a university schedule application built with Spring Boot and Thymeleaf

How to run: 

Start database with:
`docker build -t oracle-db . && docker run -d --name oracle-db -p 1521:1521 oracle-db`

you need to set the gui user in the database (no start up script possible with oracle docker image)
 -> therefore just run the sql script that you will find in resources

after that start the application:
`mvn clean install spring-boot:run`
