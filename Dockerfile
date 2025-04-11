FROM amazoncorretto:17-al2-jdk AS pgw_builder

WORKDIR /app

COPY ./gradle/ ./gradle

COPY ./build.gradle ./gradlew ./settings.gradle .

RUN ./gradlew :wrapper --no-daemon

COPY . .

RUN ./gradlew bootJar --no-daemon

FROM amazoncorretto:17-al2-jdk AS pgw_runtime

EXPOSE 5080

WORKDIR /app

COPY --from=pgw_builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
