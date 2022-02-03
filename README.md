## API Tests

#### Pre-requisites:
- Command line Terminal / IDE (IntelliJ IDEA, Eclipse) with Gradle
- For IDE, Lombok plugin and enable annotation processing in IDE

### Steps to setup the project:
- Clone this repository
- Open the build.gradle file as a project in IDE
- Rebuild the project

### Steps to run tests:
- To run all the tests, run the below command in terminal ( by default it takes uat6-tcs api env)
``` 
./gradlew runAllTests -Denv=<env>
```
e.g.
``` 
   ./gradlew runAllTests -Denv=uat6-tcs
```

- To run specific group of tests in command line, run the below command
```
./gradlew runTests -Dtags=<Test group> -Denv=<env>
```
 e.g.  
 ```
 ./gradlew runTests -Dtags=SMOKE -Denv=uat6-tcs
 ```
### Test groups:
- UNIT
- SMOKE
- REGRESSION
- CONTRACT
- BUSINESS_VALIDATION
- WORKFLOW

### Prerequisite to run test(s) in New Environment:
- Add/Update relative environment .properties file in src/main/resources
- Add/Update relative Product data class in src/main/java/testdata/productData