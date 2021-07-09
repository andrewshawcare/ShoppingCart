FROM maven:3.8.1-openjdk-16-slim AS package
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn --offline package

FROM openjdk:16-alpine AS deploy
WORKDIR /usr/src/app
COPY --from=package \
  /usr/src/app/configuration.yml \
  /usr/src/app/target/ShoppingCart-1.0-SNAPSHOT.jar \
  ./
ENTRYPOINT ["java"]
CMD ["-jar", "ShoppingCart-1.0-SNAPSHOT.jar", "server", "configuration.yml"]