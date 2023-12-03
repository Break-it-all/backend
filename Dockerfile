# 1단계: 소스 코드를 빌드하여 JAR 파일 생성
FROM gradle:8.4 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# 2단계: 최종 이미지에 JAR 파일 복사
FROM eclipse-temurin:17
WORKDIR /backend
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
VOLUME /tmp
ENTRYPOINT ["java","-jar","app.jar"]