# Advanced Java – Module 1: Java Database Connectivity (JDBC)

**Duration:** 3 Days  
**Programming Language:** Java  
**Database:** MySQL  
**JDBC Driver:** MySQL Connector/J

---

# Day 1 – Introduction to JDBC

## 1. Introduction to JDBC

### What is JDBC?

JDBC stands for **Java Database Connectivity**.

JDBC is a Java API that allows Java applications to communicate with relational databases such as MySQL, Oracle, PostgreSQL and SQL Server.

In simple words, JDBC acts as a bridge between a Java application and a database.

### Real-world example

Consider a college management application.

A student registers through a Java application. The application needs to store the student's information in a MySQL database.

JDBC helps the Java application communicate with MySQL to perform database operations.

### Why do we need JDBC?

1. To connect Java applications with databases.
2. To insert records into a database.
3. To retrieve records from a database.
4. To update existing records.
5. To delete records.
6. To execute SQL queries from Java applications.

### Important JDBC components

| Component | Purpose |
|---|---|
| DriverManager | Establishes database connections |
| Connection | Represents a database connection |
| Statement | Executes normal SQL statements |
| PreparedStatement | Executes parameterized SQL statements |
| CallableStatement | Executes stored procedures |
| ResultSet | Holds the result of SELECT queries |
| SQLException | Handles database-related exceptions |

### Types of JDBC Drivers

There are four types of JDBC drivers:

1. Type 1 – JDBC-ODBC Bridge Driver
2. Type 2 – Native API Driver
3. Type 3 – Network Protocol Driver
4. Type 4 – Thin Driver

**Note:** MySQL Connector/J is a Type 4 JDBC driver.

---

# Day 2 – Establishing Database Connection

## 2. Understanding Database Connection

### What is a database connection?

A database connection is a communication link between a Java application and a database server.

Java uses the `Connection` interface to represent this connection.

### What happens when we don't close a database connection?

Consider the following example:

```java
Connection con = DriverManager.getConnection(
    url, username, password
);
```

Once this statement executes successfully, a connection is established between Java and MySQL.

If we don't close the connection, several problems can occur:

**1. Resource consumption**

The database continues maintaining resources for the open connection.

**2. Connection exhaustion**

Every database server has a maximum connection limit.

If applications continuously create connections without closing them, the database may eventually reject new connection requests.

**3. Memory and network resource usage**

Open connections consume database-side resources and network resources.

**4. Transaction problems**

If a connection has an unfinished transaction, locks or uncommitted changes may remain active until the transaction is completed or the connection is cleaned up.

**5. Application performance issues**

Too many unused connections can reduce application performance.

### Real-world example

Imagine a college application with 500 students accessing the system.

Every student requests database information.

If the application opens a new connection for every request but never closes it, the database may run out of available connections.

New students may receive a connection error.

### Incorrect approach

```java
Connection con = DriverManager.getConnection(
    url, username, password
);

// Perform database operations

// Connection is not closed!
```

### Correct approach

```java
Connection con = DriverManager.getConnection(
    url, username, password
);

try {
    // Perform database operations

} finally {
    con.close();
}
```

### Recommended approach – Try-with-resources

Java provides try-with-resources to automatically close resources.

```java
try (Connection con = DriverManager.getConnection(
        url, username, password)) {

    System.out.println("Database Connected!");

} catch (SQLException e) {

    System.out.println(e.getMessage());

}
```

**Important:** Always close JDBC resources. Prefer try-with-resources because it automatically closes the connection, even when an exception occurs.

---

## 3. Requirements for JDBC Connection

Before connecting Java with MySQL, we need:

1. Java JDK.
2. MySQL Server.
3. MySQL JDBC Driver.
4. Database username and password.

### Step 1: Install MySQL

Make sure MySQL Server is installed and running.

Default MySQL port: `3306`

### Step 2: Create a database

Open MySQL Workbench and execute:

```sql
CREATE DATABASE college;

USE college;
```

### Step 3: Create a students table

```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    age INT,
    department VARCHAR(50)
);
```

### Step 4: Insert sample data

```sql
INSERT INTO students
(name, email, age, department)
VALUES
('Arun', 'arun@gmail.com', 21, 'CSE'),
('Priya', 'priya@gmail.com', 22, 'IT'),
('Kumar', 'kumar@gmail.com', 20, 'ECE');
```

### Step 5: Add MySQL JDBC Driver

We need MySQL Connector/J to establish communication between Java and MySQL.

For Maven projects, add this dependency inside `pom.xml`:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.5.0</version>
</dependency>
```

Maven automatically downloads the required JDBC driver.

For a non-Maven project, download the MySQL Connector/J JAR and add it to the project classpath.

---

## 4. Project Creation – Step by Step

We will create a Maven Java project.

### Step 1: Create a new project

1. Open IntelliJ IDEA or VS Code.
2. Create a new Maven project.
3. Enter the project name: `JDBCPractice`.
4. Select Java 21 as the JDK.
5. Finish project creation.

### Step 2: Create the project structure

Create the following folders and files:

```text
JDBCPractice/
│
├── pom.xml
│
└── src/
    └── main/
        └── java/
            └── com/
                └── college/
                    │
                    ├── connection/
                    │   └── DBConnection.java
                    │
                    ├── statement/
                    │   ├── StatementExample.java
                    │   ├── PreparedStatementExample.java
                    │   └── CallableStatementExample.java
                    │
                    ├── resultset/
                    │   └── ResultSetExample.java
                    │
                    ├── exception/
                    │   └── ExceptionHandlingExample.java
                    │
                    └── crud/
                        ├── Student.java
                        ├── StudentDAO.java
                        └── StudentManagementApp.java
```

### Step 3: Understand the project structure

| File | Responsibility |
|---|---|
| DBConnection.java | Establishes MySQL connection |
| StatementExample.java | Demonstrates Statement |
| PreparedStatementExample.java | Demonstrates PreparedStatement |
| CallableStatementExample.java | Demonstrates CallableStatement |
| ResultSetExample.java | Demonstrates ResultSet |
| ExceptionHandlingExample.java | Demonstrates JDBC exception handling |
| Student.java | Represents student information |
| StudentDAO.java | Contains database CRUD operations |
| StudentManagementApp.java | Runs the student management application |

**Important:** Each concept is implemented in a separate Java file so that students can understand and practice independently.

---

## 5. Establishing Database Connection – Practical

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

### Explanation

**1. Import JDBC classes**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
```

- `Connection`: Represents a database connection.
- `DriverManager`: Establishes the connection.
- `SQLException`: Handles database-related errors.

**2. Database URL**

```java
private static final String URL =
    "jdbc:mysql://localhost:3306/college";
```

| Part | Meaning |
|---|---|
| jdbc | JDBC protocol |
| mysql | Database type |
| localhost | Database server |
| 3306 | MySQL port |
| college | Database name |

**3. Establish connection**

```java
DriverManager.getConnection(URL, USER, PASSWORD);
```

This method establishes the connection and returns a Connection object.

**4. Why static method?**

```java
public static Connection getConnection()
```

We can call this method directly using the class name without creating a DBConnection object.

Example:

```java
Connection con = DBConnection.getConnection();
```

### Step 4: Test the database connection

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

**Expected output:**

```text
Database Connected Successfully!
```

If the connection fails, verify:

- Is MySQL Server running?
- Is the database name correct?
- Is the username correct?
- Is the password correct?
- Is MySQL Connector/J added to the project?

---

# Day 2 – JDBC Statements

## 6. Statement

### What is Statement?

Statement is an interface in JDBC used to execute normal SQL queries.

It is suitable for executing SQL statements that do not require parameter placeholders.

### Example

Create:

`StatementExample.java`

Package:

`com.college.statement`

```java
package com.college.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.college.connection.DBConnection;

public class StatementExample {

    public static void main(String[] args) {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                System.out.println(
                    "ID: " + rs.getInt("id")
                );

                System.out.println(
                    "Name: " + rs.getString("name")
                );

                System.out.println("----------------");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
}
```

### Important methods

| Method | Purpose |
|---|---|
| createStatement() | Creates a Statement object |
| executeQuery() | Executes SELECT queries |
| executeUpdate() | Executes INSERT, UPDATE and DELETE |
| execute() | Executes general SQL statements |
| close() | Closes the Statement |

### When to use Statement?

Example:

```java
String sql = "SELECT * FROM students";
```

When the SQL query is fixed and does not need user-provided values, Statement can be used.

---

## 7. PreparedStatement

### What is PreparedStatement?

PreparedStatement is an interface used to execute parameterized SQL queries.

It allows us to pass values dynamically using `?` placeholders.

It is generally preferred over Statement when working with user input.

### Real-world example

Suppose a student enters their email address to search their information.

Instead of manually constructing the SQL query, we can use PreparedStatement.

### Example

Create:

`PreparedStatementExample.java`

```java
package com.college.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.college.connection.DBConnection;

public class PreparedStatementExample {

    public static void main(String[] args) {

        String sql =
            "SELECT * FROM students WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "arun@gmail.com");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    System.out.println(
                        rs.getString("name")
                    );

                    System.out.println(
                        rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
}
```

### Explanation

**1. SQL query with placeholder**

```java
String sql =
    "SELECT * FROM students WHERE email = ?";
```

The question mark represents a parameter.

**2. Create PreparedStatement**

```java
PreparedStatement ps = con.prepareStatement(sql);
```

**3. Set parameter value**

```java
ps.setString(1, "arun@gmail.com");
```

The first parameter is assigned the email address.

Remember: JDBC parameter indexing starts from 1, not 0.

### Important PreparedStatement methods

| Method | Purpose |
|---|---|
| setInt() | Sets an integer value |
| setString() | Sets a string value |
| setDouble() | Sets a decimal value |
| setBoolean() | Sets a boolean value |
| setDate() | Sets a date value |
| setNull() | Sets SQL NULL |

### SQL Injection

SQL Injection is a security vulnerability where malicious input changes the intended SQL query.

PreparedStatement helps prevent SQL injection by treating parameter values as data instead of SQL code.

---

## 8. CallableStatement

### What is CallableStatement?

CallableStatement is an interface used to execute stored procedures in a database.

### What is a stored procedure?

A stored procedure is a group of SQL statements stored inside the database that can be executed whenever required.

### Step 1: Create a stored procedure in MySQL

```sql
DELIMITER //

CREATE PROCEDURE getStudentById(IN studentId INT)
BEGIN

    SELECT * FROM students
    WHERE id = studentId;

END //

DELIMITER ;
```

### Step 2: Create Java file

`CallableStatementExample.java`

```java
package com.college.statement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.college.connection.DBConnection;

public class CallableStatementExample {

    public static void main(String[] args) {

        String sql = "{CALL getStudentById(?)}";

        try (Connection con = DBConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, 1);

            try (ResultSet rs = cs.executeQuery()) {

                while (rs.next()) {

                    System.out.println(
                        "Name: " + rs.getString("name")
                    );

                    System.out.println(
                        "Email: " + rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
}
```

### Important methods

| Method | Purpose |
|---|---|
| prepareCall() | Creates a CallableStatement |
| setInt() | Sets an input parameter |
| registerOutParameter() | Registers an output parameter |
| execute() | Executes a stored procedure |
| executeQuery() | Executes a procedure that returns a result set |

### Difference between Statement types

| Feature | Statement | PreparedStatement | CallableStatement |
|---|---|---|---|
| Purpose | Normal SQL queries | Parameterized SQL queries | Stored procedures |
| Parameters | No placeholders | Yes | Yes |
| Example | SELECT * FROM students | SELECT * FROM students WHERE id = ? | CALL getStudentById(?) |

---

# Day 3 – ResultSet Handling

## 9. What is ResultSet?

ResultSet is an interface in JDBC that represents the data returned by a SELECT query.

Example:

```sql
SELECT * FROM students;
```

Suppose the database contains:

| id | name | age | department |
|---|---|---|---|
| 1 | Arun | 21 | CSE |
| 2 | Priya | 22 | IT |
| 3 | Kumar | 20 | ECE |

When Java executes the SELECT query, JDBC returns the result through a ResultSet object.

### How does ResultSet work?

Initially, the ResultSet cursor is positioned before the first row.

```java
while (rs.next()) {

    System.out.println(rs.getInt("id"));

    System.out.println(rs.getString("name"));
}
```

`rs.next()` moves the cursor to the next row.

- If a row exists, it returns `true`.
- If no more rows exist, it returns `false`.

### Important ResultSet methods

| Method | Purpose |
|---|---|
| next() | Moves to the next row |
| previous() | Moves to the previous row (scrollable ResultSet) |
| first() | Moves to the first row (scrollable ResultSet) |
| last() | Moves to the last row (scrollable ResultSet) |
| getInt() | Retrieves integer values |
| getString() | Retrieves string values |
| getDouble() | Retrieves decimal values |
| getDate() | Retrieves date values |
| getObject() | Retrieves values as objects |

By default, ResultSet is forward-only. To use previous(), first() and last(), create a scrollable ResultSet.

### Practical Example

Create:

`ResultSetExample.java`

```java
package com.college.resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.college.connection.DBConnection;

public class ResultSetExample {

    public static void main(String[] args) {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");

                String name = rs.getString("name");

                int age = rs.getInt("age");

                String department =
                    rs.getString("department");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Age: " + age);
                System.out.println("Department: " + department);

                System.out.println("----------------");
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }
}
```

---

# Day 3 – Exception Handling in JDBC

## 10. What is SQLException?

SQLException is an exception class in Java used to handle database-related errors.

Common JDBC errors:

1. Incorrect username or password.
2. MySQL Server is not running.
3. Incorrect SQL syntax.
4. Duplicate primary key or unique value.
5. Database or table does not exist.
6. Connection failure.

### Example

Create:

`ExceptionHandlingExample.java`

```java
package com.college.exception;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExceptionHandlingExample {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/college";

        String username = "root";

        String password = "wrong_password";

        try (Connection con =
                DriverManager.getConnection(
                    url, username, password
                )) {

            System.out.println(
                "Database Connected Successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                "Database Error: " + e.getMessage()
            );

            System.out.println(
                "SQL State: " + e.getSQLState()
            );

            System.out.println(
                "Error Code: " + e.getErrorCode()
            );
        }
    }
}
```

### Important SQLException methods

| Method | Purpose |
|---|---|
| getMessage() | Returns error message |
| getSQLState() | Returns SQL state |
| getErrorCode() | Returns database error code |
| printStackTrace() | Prints detailed exception information |
| getNextException() | Returns the next chained SQLException |

---

# Day 3 – Best Practices in JDBC

## 11. JDBC Best Practices

When developing real-world Java applications, follow these practices.

| Best practice | Reason |
|---|---|
| Prefer PreparedStatement | Helps prevent SQL injection |
| Use try-with-resources | Automatically closes resources |
| Avoid hardcoding passwords | Improves security |
| Handle SQLException | Helps identify database errors |
| Use connection pooling | Reuses database connections |
| Use transactions | Maintains data consistency |
| Avoid unnecessary connections | Reduces resource consumption |
| Use meaningful variable names | Improves code readability |
| Use batch processing | Reduces repeated database communication |

### What is Connection Pooling?

Connection pooling maintains a collection of reusable database connections.

Instead of repeatedly creating new database connections, the application can reuse existing connections.

Example library: HikariCP.

### What is a Transaction?

A transaction is a group of database operations treated as a single logical unit.

Real-world example: Bank money transfer.

- Deduct ₹1,000 from Account A.
- Add ₹1,000 to Account B.

Both operations should succeed together. If one operation fails, the transaction should be rolled back.

Important JDBC methods:

| Method | Purpose |
|---|---|
| setAutoCommit(false) | Disables automatic commit |
| commit() | Saves transaction changes |
| rollback() | Undoes uncommitted changes |

Example:

```java
try (Connection con = DBConnection.getConnection()) {

    con.setAutoCommit(false);

    try {

        // Execute first SQL operation

        // Execute second SQL operation

        con.commit();

    } catch (SQLException e) {

        con.rollback();

        throw e;
    }

} catch (SQLException e) {

    System.out.println(e.getMessage());
}
```

---

# Day 3 – Practical: Student Database CRUD Program

## 12. Student Management System

### Project Requirements

Develop a Java application connected to MySQL.

The application should support:

1. Add Student.
2. View All Students.
3. Search Student by ID.
4. Update Student Details.
5. Delete Student.

### Step 1: Create the Student Model

File: `Student.java`

Package: `com.college.crud`

```java
package com.college.crud;

public class Student {

    private int id;
    private String name;
    private String email;
    private int age;
    private String department;

    public Student(int id, String name, String email,
                   int age, String department) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.department = department;
    }

    public Student(String name, String email,
                   int age, String department) {

        this.name = name;
        this.email = email;
        this.age = age;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }
}
```

### Step 2: Create StudentDAO

File: `StudentDAO.java`

Package: `com.college.crud`

DAO stands for Data Access Object.

It separates database operations from application logic.

```java
package com.college.crud;

import java.sql.*;

import com.college.connection.DBConnection;

public class StudentDAO {

    // CREATE
    public void addStudent(Student student)
            throws SQLException {

        String sql = "INSERT INTO students " +
            "(name, email, age, department) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getDepartment());

            int rows = ps.executeUpdate();

            System.out.println(rows + " student added!");
        }
    }

    // READ
    public void viewStudents() throws SQLException {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                    "ID: " + rs.getInt("id")
                );

                System.out.println(
                    "Name: " + rs.getString("name")
                );

                System.out.println(
                    "Email: " + rs.getString("email")
                );

                System.out.println(
                    "Age: " + rs.getInt("age")
                );

                System.out.println(
                    "Department: " +
                    rs.getString("department")
                );

                System.out.println("----------------");
            }
        }
    }

    // SEARCH
    public void searchStudent(int id)
            throws SQLException {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    System.out.println(
                        "Name: " + rs.getString("name")
                    );

                    System.out.println(
                        "Email: " + rs.getString("email")
                    );

                } else {

                    System.out.println("Student not found!");
                }
            }
        }
    }

    // UPDATE
    public void updateStudent(int id, String name, int age)
            throws SQLException {

        String sql =
            "UPDATE students SET name = ?, age = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            System.out.println(rows + " student updated!");
        }
    }

    // DELETE
    public void deleteStudent(int id)
            throws SQLException {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            System.out.println(rows + " student deleted!");
        }
    }
}
```

### Step 3: Create StudentManagementApp

File: `StudentManagementApp.java`

Package: `com.college.crud`

This class provides the menu and accepts user input.

```java
package com.college.crud;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentManagementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO dao = new StudentDAO();

        while (true) {

            System.out.println("\nSTUDENT MANAGEMENT SYSTEM");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {

                switch (choice) {

                    case 1:

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Department: ");
                        String department = sc.nextLine();

                        Student student = new Student(
                            name, email, age, department
                        );

                        dao.addStudent(student);

                        break;

                    case 2:

                        dao.viewStudents();

                        break;

                    case 3:

                        System.out.print("Enter Student ID: ");
                        int searchId = sc.nextInt();

                        dao.searchStudent(searchId);

                        break;

                    case 4:

                        System.out.print("Enter Student ID: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter New Age: ");
                        int newAge = sc.nextInt();

                        dao.updateStudent(
                            updateId, newName, newAge
                        );

                        break;

                    case 5:

                        System.out.print("Enter Student ID: ");
                        int deleteId = sc.nextInt();

                        dao.deleteStudent(deleteId);

                        break;

                    case 6:

                        System.out.println("Application Closed!");

                        sc.close();

                        return;

                    default:

                        System.out.println("Invalid Choice!");
                }

            } catch (SQLException e) {

                System.out.println(
                    "Database Error: " + e.getMessage()
                );
            }
        }
    }
}
```

### Expected Output

```text
STUDENT MANAGEMENT SYSTEM

1. Add Student
2. View Students
3. Search Student
4. Update Student
5. Delete Student
6. Exit

Enter your choice: 1

Enter Name: Arun
Enter Email: arun@gmail.com
Enter Age: 21
Enter Department: CSE

1 student added!
```

---

# 13. Student Practice Assignments

## Level 1 – Beginner

1. Create a database named `school`.
2. Create a table named `teachers`.
3. Connect Java with MySQL.
4. Insert five teacher records.
5. Display all teacher records.

## Level 2 – Intermediate

1. Search a teacher by ID.
2. Update teacher salary.
3. Delete a teacher by ID.
4. Search students by department.
5. Display students whose age is greater than 20.

## Level 3 – Advanced

1. Create a menu-driven Student Management System.
2. Use Scanner to accept user input.
3. Use PreparedStatement for all CRUD operations.
4. Create and execute a stored procedure using CallableStatement.
5. Implement a transaction for two related database operations.

---

# 14. Module 1 – Knowledge Check

1. What is JDBC?
2. What is the purpose of JDBC Driver?
3. What are the four types of JDBC drivers?
4. What is the purpose of DriverManager?
5. What happens if a database connection is not closed?
6. What is the difference between Statement and PreparedStatement?
7. What is SQL Injection?
8. What is CallableStatement?
9. What is a stored procedure?
10. What is ResultSet?
11. What is the purpose of rs.next()?
12. What is SQLException?
13. What is try-with-resources?
14. What is connection pooling?
15. What is a database transaction?

---

# 15. Learning Outcomes

After completing Module 1, students should be able to:

- Understand JDBC fundamentals.
- Identify the four types of JDBC drivers.
- Configure MySQL Connector/J.
- Create a Maven JDBC project.
- Connect Java applications with MySQL.
- Use Statement, PreparedStatement and CallableStatement.
- Retrieve records using ResultSet.
- Handle JDBC exceptions.
- Follow JDBC best practices.
- Develop a Student Management System using CRUD operations.

**Module 1 Completed – Java Database Connectivity (JDBC)**
