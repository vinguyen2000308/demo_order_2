FROM openjdk:11
WORKDIR /app
COPY target/demo_inventory_2-0.0.1-SNAPSHOT.jar app/app.jar
ENTRYPOINT ["java","-jar","app/app.jar"]
