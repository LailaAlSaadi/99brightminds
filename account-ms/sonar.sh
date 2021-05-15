
mvn clean

mvn clean install -Dmaven.test.skip=true
mvn sonar:sonar    -Dsonar.projectKey=bank-java-service    -Dsonar.host.url=http://localhost:9000    -Dsonar.login=33a3d3a43d0c2093c88408a603d20f05fd4da1f1


mvn sonar:sonar   -Dsonar.projectKey=bank-java-service   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=4d10334568601482f02fb97b011e8a13aafae520
