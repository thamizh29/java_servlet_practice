# Run the database check

From the project directory, run:

```powershell
mvn exec:java
```

The Maven Exec plugin runs `com.example.TestDataBase` and includes the MySQL Connector/J dependency from `pom.xml` on the runtime classpath. Do not use a classpath containing only `target\classes`; it omits the database driver.

## It will help to test java application
    <plugins>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>3.7.0</version>
        <configuration>
          <mainClass>com.example.TestDataBase</mainClass>
        </configuration>
      </plugin>
    </plugins>
