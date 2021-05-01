
mvn clean

mvn clean install -Dmaven.test.skip=true
mvn sonar:sonar \
  -Dsonar.projectKey=account-ms \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=aba548301482cf488880af0e5754c02243e06580
