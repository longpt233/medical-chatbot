FROM maven:3.6.1-jdk-8-alpine as builder

WORKDIR /app

COPY . /app/ 

RUN mvn clean install -DskipTests

FROM openjdk:8-jdk-alpine

ENV TIMEZONE "Asia/Ho_Chi_Minh"

RUN apk add --no-cache tzdata \
        && cat /usr/share/zoneinfo/${TIMEZONE} > /etc/localtime \
        && echo "${TIMEZONE}" > /etc/timezone

WORKDIR /work

COPY --from=builder /app/target/*.jar /work/target/

EXPOSE 8080

CMD java -jar target/*.jar