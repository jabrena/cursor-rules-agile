# Process

This document could be used as a procedure to solve any latency problem

## 1. Onboarding

Intall the cursor rules which you are going to use in your workspace

```bash
sdk install jbang
jbang cache clear
jbang catalog list jabrena
jbang setup@jabrena init --cursor https://github.com/jabrena/cursor-rules-agile
jbang setup@jabrena init --cursor https://github.com/jabrena/cursor-rules-java
```

## 2. Requirements discovery

- 2.1 [ ] Generate the software requirements based on the initial problem description

```bash
Create an agile development checklist using @2000-agile-checklist
```

**Note:** Generate dynamically images in png format from your plantuml files

```bash
jbang puml-to-png@jabrena --watch problem5/docs/requirements
```

## 3. Implement the solution

- 3.1 [ ] Setup the Maven project

```bash
jbang setup@jabrena init --maven
jbang setup@jabrena init --spring-boot
jbang setup@jabrena init --quarkus
```

**Note:** Remove the `pluginManagement` section in the the pom.xml because it is not necessary in all cases.

```bash
Update the cursor rule using @101-java-maven-deps-and-plugins
Verify the changes with ./mvnw clean verify
```

- 3.2 [ ] Implement the Acceptance test ([Outside-in TDD London](https://outsidein.dev/concepts/outside-in-tdd/)) based on the Gherkin file

![](./docs/double-loop-tdd.png)

**Note:** Attach the User story & Gherkin file from requirements folder

```bash
Implement an acceptance tests in the package info.jab.latency for the scenario:
"Successfully retrieve all mythology gods data".
Don´t develop any source code, only implement the acceptance test.
It will fail because in this phase, doesn´t exist any implemention.
```

**Note:** if the REST/Cli development need to interact with a External third party service, it could be possible that the acceptance tests Stub the external integration also.

```xml
<properties>
    <wiremock.version>3.13.0</wiremock.version>
</properties>

<dependency>
    <groupId>org.wiremock</groupId>
    <artifactId>wiremock-standalone</artifactId>
    <version>${wiremock.version}</version>
    <scope>test</scope>
</dependency>
```

- 3.3 [ ] Implement the solution to pass the acceptance tests

```bash
Implement a solution in the package info.jab.latency from src.
Create a solution and later add test classes.
Dont´t change the acceptance tests.
Verify the changes with the command: ./mvnw clean verify
```

```bash
./mvnw clean verify surefire-report:report
jwebserver -p 8001 -d "$(pwd)/target/reports"
./mvnw clean verify jacoco:report -Pjacoco
jwebserver -p 8002 -d "$(pwd)/target/site/jacoco"
./mvnw clean verify -Dmaven.build.cache.enabled=false
```

- 3.4 [ ] Implement the tests

```bash
Review the coverage with ./mvnw clean verify jacoco:report -Pjacoco and increase the coverage in instructions, classes & branches until 80% @problem4
```

- 3.5 [ ] Refactor the initial stable solution

```bash
No recipe, this is the added value of a good SSE. ¯\_(ツ)_/¯
```
