FROM maven:3-amazoncorretto-21 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:resolve

COPY src ./src

RUN mvn clean package -DskipTests

FROM amazoncorretto:21 AS runner

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
