FROM eclipse-temurin:21-jre

COPY target/semantic_log_classifier_api-0.0.1-SNAPSHOT.jar /project/

ENTRYPOINT ["java", "--enable-preview", "-jar", "/project/semantic_log_classifier_api-0.0.1-SNAPSHOT.jar"]
