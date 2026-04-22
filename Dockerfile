# 第一阶段：编译
FROM eclipse-temurin:8-jdk AS builder

WORKDIR /app

COPY src/main/java /app/src/main/java

RUN javac -d /app/out /app/src/main/java/org/example/App.java

# 第二阶段：运行
FROM eclipse-temurin:8-jre

WORKDIR /app

COPY --from=builder /app/out /app/out

CMD ["java", "-cp", "/app/out", "org.example.App"]