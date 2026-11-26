# New Application without Pom.xml

## cmd
```shell
mvn org.apache.maven.plugins:maven-archetype-plugin:3.4.1:generate -DgroupId=com.example -DartifactId=maven-db-practice -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.4 -DinteractiveMode=false
```

## powershell
```shell
mvn org.apache.maven.plugins:maven-archetype-plugin:3.4.1:generate "-DgroupId=com.example" "-DartifactId=maven-db-practice" "-DarchetypeGroupId=org.apache.maven.archetypes" "-DarchetypeArtifactId=maven-archetype-webapp" "-DarchetypeVersion=1.4" "-DinteractiveMode=false"
```

## Need to create folder for the Java

```text
application_name/
│
├── pom.xml
│
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
```
## create the folder like this

## Add MySQL JDBC Driver

We need MySQL Connector/J to establish communication between Java and MySQL.

For Maven projects, add this dependency inside `pom.xml`:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.5.0</version>
</dependency>
```

## Establishing Database Connection – Practical

Create a Java file:

`DBConnection.java`

Package:

`com.college.connection`

```java
package com.college.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/college";

    private static final String USER = "root";

    private static final String PASSWORD = "your_password";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
            URL, USER, PASSWORD
        );
    }
}
```
### Test the database connection

Create another file:

`ConnectionTest.java`

```java
package com.college.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()) {

            System.out.println(
                "Database Connected Successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                "Connection Failed: " + e.getMessage()
            );
        }
    }
}
```
