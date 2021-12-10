# Music library RESTful API

### Context

This application allows an administrator:
1. to curate users and albums
2. to curate users libraries

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

### Requirements and config

- Java 11 or later, Maven 3, Elasticsearch instance running. 

If you have docker installed, you can run an elasticsearch container with the following command:

		$ sudo docker run -p 127.0.0.1:9200:9200 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.15.2

Elasticsearch client's config can be changed with the application.properties file (default values below):
 
		elasticsearch.host:localhost
		elasticsearch.port:9200
		elasticsearch.auth.enable:false
		elasticsearch.auth.username:username
		elasticsearch.auth.password:password
		
At startup, Elasticsearch client will run and try to connect to the Elasticsearch instance. Once connected, it will automatically load the album dataset and proceed with a bulk insert into Elasticsearch index 'albums'; The default dataset file location is : /resources/data/elastic/albums_sample.json and can be configured in the application properties file:

		library.albums.load:true 
		library.albums.dataset:data/elastic/albums_sample.json

Users are saved in a H2 database and Elasticsearch albums index is reset between executions, so all data created is only kept alive while the music library application is running and then cleared after execution.
		
### Build and run the application

		$ mvn package
		$ mvn spring-boot:run

The API is accessible on <http://localhost:8080> and can be tested via a Swagger ui on  <http://localhost:8080/swagger-ui/index.html> 

Open API descriptions are available on <http://localhost:8080/api-docs/>


