FROM pw_env
WORKDIR /data/configservice
COPY runner/target/action-runner-jar-with-dependencies.jar .
# RUN mvn -B -f /data/configservice/pom.xml -s /usr/share/maven/ref/settings.xml clean package
CMD ["java", "-jar", "config-runner-jar-with-dependencies.jar"]