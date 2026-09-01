FROM eclipse-temurin:25-jre

WORKDIR /app

# DONE 3: 复制 Maven 构建出的 jar，设置运行环境变量，并用 java -jar 启动应用。
COPY target/learning-java-1.0-SNAPSHOT.jar app.jar
ENV SERVER_PORT=8080
ENV APP_DISPLAY_NAME=Learning-Java-Container
ENV SPRING_PROFILES_ACTIVE=lesson32
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
