### Demo SCC camel kafka bug

Scenario:
As a developer
I want to create a tests that reads messages from a kafka topic using camel
So that I can validate services that communicate using kafka

Expected:
SCC should be able to execute a test that validates that a message is written to a kafka topic

Actual:
The test fails because SCC is unable to read the message from kafka the first time is run

Steps to reproduce:

```bash
./mvnw clean verify
```

Context:
The problem is only happening the first time the tests are run, which can be demonstrated with the following commands:
- Start kafka and run the tests which fail the first time
```bash
./mvnw docker:start failsafe:integration-test
```
- Run the tests again and they will pass
```bash
./mvnw failsafe:integration-test
```
- Stop kafka docker container
```bash
./mvnw docker:stop
```
