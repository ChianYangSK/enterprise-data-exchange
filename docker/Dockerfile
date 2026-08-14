FROM maven:3.8.8-eclipse-temurin-8 AS build
WORKDIR /workspace
COPY enterprise-cloud/enterprise-modules/enterprise-dmz-exchange/pom.xml ./pom.xml
RUN mvn -B -q dependency:go-offline
COPY enterprise-cloud/enterprise-modules/enterprise-dmz-exchange/src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:8-jre
WORKDIR /app
RUN apt-get update \
 && apt-get install -y --no-install-recommends wget \
 && rm -rf /var/lib/apt/lists/* \
 && useradd --system --uid 10001 exchange
COPY --from=build /workspace/target/enterprise-dmz-exchange-*.jar /app/app.jar
USER 10001
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 CMD ["sh", "-c", "wget -q -O - http://localhost:8080/actuator/health | grep -q UP"]
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
