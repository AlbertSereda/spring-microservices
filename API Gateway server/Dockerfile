FROM openjdk:17 as build
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} gateway-server.jar
RUN java -Djarmode=layertools -jar gateway-server.jar extract

FROM openjdk:17
WORKDIR application
COPY --from=build application/dependencies/ ./
COPY --from=build application/spring-boot-loader/ ./
COPY --from=build application/snapshot-dependencies/ ./
COPY --from=build application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]