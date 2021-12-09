# Music library RESTful API

### Context

This application allows an administrator:
1. to curate users and albums
2. to curate users’ libraries

### Functionalities

**Manage the users of the application**
- Each user will have at least a name
- List (search), create, update and delete users

**Manage the global album library**
It's the list of all the albums available in the application and that users can add to their personal libraries
- Each album have at least the following fields: title, artist, year
- create, update, delete and search (see details below) albums
- Albums are searchable using full-text queries on all fields

**Manage users’ librairies: manage users’ personal album library**
- Add/remove existing albums to a user’s library
- List albums for a given user

### Requirements

- Java 11 or later, Maven 3, Elasticsearch instance running on port 9200. If you have docker insatlled, you can run an elasticsearch container with the following command:

		$ sudo docker run -p 127.0.0.1:9200:9200 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.15.2

- build and run the aplication

		$ mvn package
		$ mvn spring-boot:run
		$ java -jar <jar> com.halj.music.library.LibraryApplication

### Api documentation 
- Swagger <http://localhost:8080/swagger-ui/index.html>
- Open API descriptions <http://localhost:8080/api-docs/>


