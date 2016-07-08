FROM java:8
MAINTAINER "Spencer Bliven"
RUN apt-get -y clean && apt-get -y update && apt-get -y update && apt-get install -y --fix-missing  git maven
RUN update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java
WORKDIR /usr/src
RUN git clone "https://github.com/biojava/biojava" biojava
RUN git clone "https://github.com/biojava/biojava-http" biojava-http
WORKDIR /usr/src/biojava
RUN mvn install -DskipTests
WORKDIR /usr/src/biojava-http
RUN mvn install -DskipTests
#CMD java -version
EXPOSE 8080
CMD java -jar target/biojava-http-full*.jar
