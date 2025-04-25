FROM amazoncorretto:21 AS builder

WORKDIR /app

RUN yum install -y tar gzip

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
