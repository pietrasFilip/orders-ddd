FROM openjdk:22
EXPOSE 8080
WORKDIR /web

ADD src/main/resources/data data

ADD target/orders_web.jar orders_web.jar
ENTRYPOINT ["java", "-jar", "orders_web.jar"]