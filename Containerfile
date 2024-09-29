FROM openjdk:11-jdk-slim

WORKDIR /app

RUN apt-get update \
&&  apt-get install -y wget unzip --no-install-recommends \
&&  wget --progress=dot:giga https://services.gradle.org/distributions/gradle-7.2-bin.zip \
&&  unzip gradle-7.2-bin.zip \
&&  rm gradle-7.2-bin.zip \
&&  mv gradle-7.2 /opt/gradle \
&&  ln -s /opt/gradle/bin/gradle /usr/bin/gradle

COPY . .

RUN gradle build --no-daemon \
&&  mv ./desktop/build/libs/desktop*.jar ./desktop.jar

#ENTRYPOINT ["java", "-jar", "./desktop.jar"]
ENTRYPOINT ["./entrypoint"]
