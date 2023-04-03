FROM openjdk:18
COPY ./build/libs/web_07.jar ./app/web.jar
CMD java -jar ./app/web.jar