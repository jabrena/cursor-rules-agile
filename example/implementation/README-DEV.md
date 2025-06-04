./mvnw clean verify jacoco:report -Pjacoco
jwebserver -p 8004 -d "$(pwd)/target/site/jacoco"