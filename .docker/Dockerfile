FROM amazoncorretto:17-alpine3.17-jdk as builder
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} fintech-backend.jar

RUN java -Djarmode=layertools -jar fintech-backend.jar extract

FROM openjdk:17-jdk-alpine
COPY --from=builder ./dependencies/ ./
COPY --from=builder ./spring-boot-loader/ ./
COPY --from=builder ./snapshot-dependencies/ ./
COPY --from=builder ./application/ ./
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "org.springframework.boot.loader.launch.JarLauncher"]