FROM bellsoft/liberica-openjdk-alpine:17 AS build
WORKDIR /app
COPY . .

RUN apk add --no-cache bash
RUN chmod +x gradlew && ./gradlew --version && ./gradlew clean build -x test --stacktrace

FROM bellsoft/liberica-openjdk-alpine:17
ENV SPRING_PROFILES_ACTIVE=prod-profile
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

VOLUME /tmp
VOLUME /logs
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]