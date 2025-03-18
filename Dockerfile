FROM maven:3.9.9-eclipse-temurin-23
ADD . /work
WORKDIR /work
RUN mvn clean install -DskipITs
RUN mkdir /app && \
    cp target/courier-tracking-*.jar /app/courier-tracking.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/courier-tracking.jar"]