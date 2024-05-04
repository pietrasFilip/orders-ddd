# orders-ddd-project

This project helps to manage orders. Orders contain information about customer, product, quantity and date
of placing an order. You can simply add orders from .json, .txt files as well as from MySql database.
Due to implemented `abstract factory` design pattern there is an easy way to implement more sources from where you can
add your orders.
Project comes up with functionality of registering users to database, security and signing in.

# Set-up

- Java 22,
- Spring Boot,
- Hibernate,
- Maven,
- Docker,
- Lombok
- Jjwt,
- Gson,
- iText 7 Core,
- Junit,
- Mockito.

# Software design approach
- Domain Driven Design.

# Design patterns
- Abstract factory
- Observer
- Builder
- Singleton

# How to install?

Use command below to install the package into your local repository.
```
mvn clean install
```
If you don't want to create local repository use:
```
mvn clean package -DskipTests
```

# How to run?

Before running application with docker, fill missing fields inside [application.yml](src/main/resources/application.yml).

Run with docker:
```
docker-compose up -d --build
```

When running inside IntelliJ, change config inside:
- [application.yml](src/main/resources/application.yml) to:
```
repository:
  path:
    json:
      customers: 'src/main/resources/data/json/customers.json'
      orders: 'src/main/resources/data/json/orders.json'
      products: 'src/main/resources/data/json/products.json'
    txt:
      customers: 'src/main/resources/data/txt/customers.txt'
      orders: 'src/main/resources/data/txt/orders.txt'
      products: 'src/main/resources/data/txt/products.txt'
```
- [application.properties](src/main/resources/application.properties) to:
```
pdf.path=src/main/resources/pdf/
```

# Running unit tests

To run unit tests properly go to project destination in terminal and then execute below command:
```
mvn test
```

# Change data source

To change data source to .json, .txt or db go to [application.yml](src/main/resources/application.yml) file 
and choose source, that you want to use.

# Sending mails

To send mails properly go to [application.yml](src/main/resources/application.yml) and fill the values with proper data.
Orders service contains method for sending mail to customer email with generated pdf attachment which consist all
products bought by this customer.

# Generating .pdf files

[PdfService](src/main/java/com/app/application/service/pdf/PdfService.java) allows creating pdf files with one method
called generatePdf.

# Abstract factory

Abstract factory is based in [abstract_factory](src/main/java/com/app/domain/policy/abstract_factory).
To create objects using abstract factory you have to fill [application.properties](src/main/resources/application.properties)
and [application.yml](src/main/resources/application.yml) file config.
Choose what processor type you want to use: db, json or txt.

# Docker-compose

Docker-compose file provides two containers:
- database,
- application.

# REST API

Api has prepared following controllers:
- OrdersRouting,
- UserRouting.

Filter checks if current user is role user, admin or is unauthorized.
Endpoints can be found [here](src/main/java/com/app/infrastructure/api/controllers).