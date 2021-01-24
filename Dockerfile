FROM pw_env
WORKDIR /data/configservice
COPY . .
RUN mvn -B -f /data/configservice/pom.xml -s /usr/share/maven/ref/settings.xml clean package
CMD ["java", "-jar", "runner/target/config-runner-jar-with-dependencies.jar"]