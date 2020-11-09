FROM openjdk:8
WORKDIR /app
COPY Opg.java Utils.java /app/
RUN javac -encoding utf8 Opg.java