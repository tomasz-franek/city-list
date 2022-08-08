# Prerequisites
Tested on ubuntu with 
* Maven version 8.3.6
* Java version 18
* MySql version 8.0.30
* Npm version 8.11.0

## Instalation

1. Create schema __city__ in MySql db.
   
2. Set __username__ and __password__ in file __liquibase/src/main/resources/liquibase.properties__. The user should have a grant to create tables. Modify the __url__ to the MySql server if needed.
   
3. Run maven command with profile liquibase to create db structures and fill the data.

    ```mvn install -P liquibase```

4. Set __spring.datasource.username__ and __spring.datasource.password__ in file __backend/src/main/resources/application.properties__. The user should have a grant to select/update data in the table in __city__ schema. Modify the __url__ to the MySql server if needed.
   
5. Run maven command with default profile to build application

   ```mvn install -P default```

6. Copy __war__ file from __/backend/target/__ directory to the web server. Application base path is __/city-list__. On tomcat the application URL should be http://localhost:8080/city-list/index.html

## Development

Start backend spring boot application
```shell
cd /backend
mvn spring-boot:run
```

Spring boot application is running on the port 9040

Start frontend Angular
```shell
cd /frontend
ng serve
```
Frontend angular is running on the port 4200 with configured proxy to backend port 9040 

Run angular tests

```shell
cd /frontend
ng test
```

## Login

Two users created
* __user__ with password __user__ ( role __USER__ )
* __editor__ with password __editor__ ( roles __USER__ and __EDITOR__ )
