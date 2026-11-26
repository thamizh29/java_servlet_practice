# Advanced Java – Module 2: JavaServer Pages (JSP)

**Duration:** 3 Days (Day 4, Day 5 and Day 6)

**Technologies:**
- Java 21
- Apache Tomcat 10.1
- JSP (Jakarta Server Pages)
- Jakarta Servlets
- JSTL
- Maven
- HTML and CSS

**Prerequisite:** Students should understand Java fundamentals, JDBC, MySQL, classes, objects, methods and exception handling.

---

# Module Overview

| Day | Topics |
|---|---|
| Day 4 | Introduction to JSP, JSP Lifecycle, Expression Language (EL) |
| Day 5 | Exception Handling in JSP, MVC Architecture, MVC using JSP and Servlets |
| Day 6 | JSTL, Advanced JSTL Tags, Custom Tags, Practical Exercise |

# Day 4 – Introduction to JSP

## 1. What is JSP?

JSP stands for **JavaServer Pages**.

JSP is a server-side technology used to create dynamic web pages using Java.

It allows developers to write HTML pages with JSP elements and expressions that can display dynamic information.

### Real-world example

Consider a student management website.

When a student logs in, the application displays:

- Student name
- Student ID
- Department
- Attendance percentage
- Examination results

These details are different for each student.

Instead of creating a separate HTML page for every student, JSP can dynamically display the information received from the Java application.

### Static page vs Dynamic page

**Static HTML:**

```html
<!DOCTYPE html>
<html>
<head>
    <title>Student Profile</title>
</head>
<body>

    <h1>Welcome Arun</h1>

</body>
</html>
```

The name is fixed.

**Dynamic JSP:**

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>Student Profile</title>
</head>
<body>

    <h1>Welcome ${studentName}</h1>

</body>
</html>
```

The name can change depending on the student who logs in.

### Why do we need JSP?

1. To create dynamic web pages.
2. To display information received from Java classes and Servlets.
3. To reduce the amount of Java code written inside HTML.
4. To separate presentation from business logic.
5. To develop Java-based web applications.

### JSP vs Servlet

| JSP | Servlet |
|---|---|
| Mainly used for presentation | Mainly used for request processing |
| HTML-friendly | Java-friendly |
| Supports JSP tags and EL | Uses Java methods and APIs |
| Converted into a Servlet by the container | Already a Java Servlet |
| Suitable for displaying data | Suitable for processing requests |

**Important:** JSP is internally translated into a Servlet by the web container. JSP and Servlets work together in Java web applications.

---

## 2. JSP Project Setup – Step by Step

We will create a Maven-based web application using Tomcat 10.1.

### Step 1: Create a Maven project

1. Open IntelliJ IDEA or VS Code.
2. Create a new Maven project.
3. Project name: `JSPPractice`.
4. Select Java 21.
5. Set packaging to `war` (Web Application Archive).

### Step 2: Understand the project structure

Create the following structure:

```text
JSPPractice/
│
├── pom.xml
│
└── src/
    └── main/
        │
        ├── java/
        │   └── com/
        │       └── college/
        │           ├── servlet/
        │           ├── model/
        │           └── controller/
        │
        ├── resources/
        │
        └── webapp/
            │
            ├── index.jsp
            │
            ├── views/
            │   └── home.jsp
            │
            ├── error/
            │   └── error.jsp
            │
            └── WEB-INF/
                └── web.xml
```

### Step 3: Configure pom.xml

Add the following configuration:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.college</groupId>
    <artifactId>JSPPractice</artifactId>
    <version>1.0</version>

    <packaging>war</packaging>

    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- Servlet API provided by Tomcat -->
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>

        <!-- JSP API provided by Tomcat -->
        <dependency>
            <groupId>jakarta.servlet.jsp</groupId>
            <artifactId>jakarta.servlet.jsp-api</artifactId>
            <version>3.1.1</version>
            <scope>provided</scope>
        </dependency>

        <!-- JSTL API -->
        <dependency>
            <groupId>jakarta.servlet.jsp.jstl</groupId>
            <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
            <version>3.0.0</version>
        </dependency>

        <!-- JSTL Implementation -->
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>3.0.1</version>
        </dependency>

    </dependencies>

    <build>
        <finalName>JSPPractice</finalName>
    </build>

</project>
```

### Understanding the dependencies

| Dependency | Purpose |
|---|---|
| jakarta.servlet-api | Provides Servlet interfaces and classes |
| jakarta.servlet.jsp-api | Provides JSP API |
| jakarta.servlet.jsp.jstl-api | Provides JSTL interfaces |
| jakarta.servlet.jsp.jstl | Provides JSTL implementation |

**Important:** Tomcat 10.1 already provides the Servlet and JSP APIs. Therefore, their Maven dependencies use `provided` scope. JSTL is added separately.

### Step 4: Create index.jsp

Location: `src/main/webapp/index.jsp`

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>JSP Application</title>
</head>
<body>

    <h1>Welcome to JSP Application</h1>

    <p>This is my first JSP page.</p>

</body>
</html>
```

### Step 5: Run the project

1. Build the Maven project.
2. Create a WAR deployment or configure a Tomcat server in your IDE.
3. Deploy the application to Tomcat 10.1.
4. Start Tomcat.
5. Open the browser.

URL:

```text
http://localhost:8080/JSPPractice/
```

Expected output:

```text
Welcome to JSP Application

This is my first JSP page.
```

---

## 3. JSP Elements and Basic Syntax

JSP provides different elements to embed Java-related functionality into a web page.

### A. JSP Declaration

Declaration is used to declare variables and methods at the Servlet class level.

Syntax:

```jsp
<%! declaration %>
```

Example:

```jsp
<%!
    int count = 0;

    public int add(int a, int b) {
        return a + b;
    }
%>

<html>
<body>

    <h2>Result: <%= add(10, 20) %></h2>

</body>
</html>
```

Output:

```text
Result: 30
```

### B. JSP Scriptlet

Scriptlet is used to write Java statements inside JSP.

Syntax:

```jsp
<% Java statements %>
```

Example:

```jsp
<html>
<body>

<%
    String name = "Arun";
    int age = 21;
%>

<h2>Name: <%= name %></h2>
<h2>Age: <%= age %></h2>

</body>
</html>
```

Output:

```text
Name: Arun
Age: 21
```

### C. JSP Expression

Expression is used to display the result of a Java expression directly in the response.

Syntax:

```jsp
<%= expression %>
```

Example:

```jsp
<html>
<body>

    <h2>Welcome <%= "Arun" %></h2>

    <h2>Total: <%= 10 + 20 %></h2>

</body>
</html>
```

Output:

```text
Welcome Arun

Total: 30
```

### Comparison of JSP elements

| JSP Element | Syntax | Purpose |
|---|---|---|
| Declaration | `<%! %>` | Declare variables and methods |
| Scriptlet | `<% %>` | Write Java statements |
| Expression | `<%= %>` | Display expression result |

**Best practice:** Avoid using Scriptlets and Java declarations in modern JSP applications. Prefer Servlets for request processing and EL/JSTL for displaying data.

---

## 4. JSP Lifecycle

### What is JSP Lifecycle?

JSP Lifecycle describes the different stages through which a JSP page passes from the time it is requested until it is removed by the web container.

The JSP container manages the entire lifecycle.

### JSP Lifecycle Steps

1. Translation
2. Compilation
3. Class Loading
4. Instantiation
5. Initialization
6. Request Processing
7. Destruction

### Lifecycle Explanation

**Step 1: Translation**

The JSP file is converted into a Java Servlet source file.

Example:

```text
index.jsp
    ↓
index_jsp.java
```

**Step 2: Compilation**

The generated Java Servlet source file is compiled into a `.class` file.

```text
index_jsp.java
    ↓
index_jsp.class
```

**Step 3: Class Loading**

The web container loads the compiled Servlet class.

**Step 4: Instantiation**

The container creates an object of the generated Servlet class.

**Step 5: Initialization**

The container calls the `jspInit()` method.

**Step 6: Request Processing**

For each request, the container invokes the `_jspService()` method.

**Step 7: Destruction**

When the JSP Servlet is being removed, the container invokes `jspDestroy()`.

### JSP Lifecycle Methods

| Method | Purpose |
|---|---|
| jspInit() | Called once when the JSP is initialized |
| _jspService() | Processes each request |
| jspDestroy() | Called before the JSP Servlet is destroyed |

### Practical Example – JSP Lifecycle

Create:

`lifecycle.jsp`

```jsp
<%!

    public void jspInit() {
        System.out.println("JSP Initialized");
    }

    public void jspDestroy() {
        System.out.println("JSP Destroyed");
    }

%>

<html>
<body>

    <h1>JSP Lifecycle Example</h1>

    <p>Check the Tomcat console for lifecycle messages.</p>

</body>
</html>
```

**Important:** Do not declare `_jspService()` yourself. It is generated and managed by the JSP container.

### Real-world understanding

When a JSP page is requested for the first time:

- The container translates it.
- Compiles it.
- Loads and initializes it.
- Processes the request.

For subsequent requests, the container generally reuses the compiled JSP Servlet.

The JSP may be translated and compiled again if its source changes.

---

# Day 4 – Expression Language (EL)

## 5. What is Expression Language?

Expression Language (EL) is a feature of JSP that allows us to access data without writing Java code or Scriptlets.

EL makes JSP pages simpler and easier to maintain.

Syntax:

```jsp
${expression}
```

### Why do we need EL?

Consider this example without EL:

```jsp
<%
    String name = (String) request.getAttribute("name");
%>

<h2>Welcome <%= name %></h2>
```

Using EL:

```jsp
<h2>Welcome ${name}</h2>
```

EL reduces unnecessary Java code inside JSP.

### Step 1: Create a Servlet

File: `StudentServlet.java`

Package: `com.college.servlet`

```java
package com.college.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("name", "Arun");

        request.setAttribute("age", 21);

        request.getRequestDispatcher(
            "/views/student.jsp"
        ).forward(request, response);
    }
}
```

### Step 2: Create student.jsp

Location: `src/main/webapp/views/student.jsp`

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>Student Details</title>
</head>
<body>

    <h1>Student Information</h1>

    <p>Name: ${name}</p>

    <p>Age: ${age}</p>

</body>
</html>
```

### Step 3: Run the application

URL:

```text
http://localhost:8080/JSPPractice/student
```

Expected output:

```text
Student Information

Name: Arun

Age: 21
```

### Understanding the flow

1. Browser requests `/student`.
2. StudentServlet receives the request.
3. Servlet stores the name and age using `request.setAttribute()`.
4. Servlet forwards the request to `student.jsp`.
5. JSP accesses the data using EL.
6. Browser displays the student information.

### Important EL features

**1. Accessing variables**

```jsp
${name}
```

**2. Accessing request attributes**

```jsp
${requestScope.name}
```

**3. Accessing session attributes**

```jsp
${sessionScope.username}
```

**4. Accessing application attributes**

```jsp
${applicationScope.companyName}
```

**5. Accessing request parameters**

```jsp
${param.username}
```

For example, if the URL is:

```text
/student?username=Arun
```

Then:

```jsp
<h2>Welcome ${param.username}</h2>
```

Displays:

```text
Welcome Arun
```

### EL Operators

| Operator | Purpose | Example |
|---|---|---|
| `+` | Addition | `${10 + 20}` |
| `-` | Subtraction | `${20 - 10}` |
| `*` | Multiplication | `${10 * 2}` |
| `/` | Division | `${20 / 2}` |
| `==` | Equality | `${10 == 10}` |
| `!=` | Not equal | `${10 != 20}` |
| `>` | Greater than | `${20 > 10}` |
| `<` | Less than | `${10 < 20}` |
| `&&` | Logical AND | `${true && true}` |
| `||` | Logical OR | `${true || false}` |
| `empty` | Checks whether a value is empty or null | `${empty name}` |

---

# Day 5 – Exception Handling in JSP

## 6. Exception Handling in JSP

### What is an exception?

An exception is an unexpected event that interrupts the normal execution of a program.

Examples:

- Division by zero.
- Invalid input.
- NullPointerException.
- Database connection failure.
- File not found.

### Why do we need exception handling in JSP?

Suppose a student opens a result page, but the database connection fails.

Instead of displaying a technical error page, we should display a meaningful error message.

JSP provides exception handling using:

1. `errorPage` directive.
2. `isErrorPage` directive.

### Step 1: Create an error-producing JSP

File: `divide.jsp`

```jsp
<%@ page errorPage="error.jsp" %>

<html>
<body>

    <h1>Division Example</h1>

    <%
        int a = 10;
        int b = 0;

        int result = a / b;
    %>

    <h2>Result: <%= result %></h2>

</body>
</html>
```

### Step 2: Create error.jsp

```jsp
<%@ page isErrorPage="true" %>

<html>
<body>

    <h1>Something Went Wrong!</h1>

    <p>An unexpected error occurred.</p>

    <p>Error: ${pageContext.exception.message}</p>

</body>
</html>
```

### Explanation

**errorPage directive**

```jsp
<%@ page errorPage="error.jsp" %>
```

Specifies the JSP page that should handle an uncaught exception from this JSP page.

**isErrorPage directive**

```jsp
<%@ page isErrorPage="true" %>
```

Identifies the JSP page as an error-handling page.

It also makes the implicit `exception` object available.

### Important note

For real-world applications, avoid exposing detailed exception messages or stack traces to users.

Log technical details on the server and display a user-friendly message.

---

# Day 5 – Introduction to MVC Architecture

## 7. What is MVC Architecture?

MVC stands for **Model – View – Controller**.

MVC is a software architectural pattern used to separate an application into three different components.

1. Model
2. View
3. Controller

### Real-world example

Consider an online shopping application.

A customer searches for a product.

- Model: Retrieves product information from the database.
- View: Displays the product information.
- Controller: Receives the search request and coordinates the operation.

### MVC Components

**1. Model**

Model represents application data and business logic.

Examples:

- Student.java
- Product.java
- Employee.java

Responsibilities:

- Represents data.
- Performs business operations.
- Communicates with the database through the data access layer.

**2. View**

View is responsible for displaying information to the user.

Examples:

- JSP pages.
- HTML pages.
- CSS.

Responsibilities:

- Display data.
- Accept user input.
- Present the application interface.

**3. Controller**

Controller receives requests from users and coordinates the application.

In Java web applications, Servlets are commonly used as controllers.

Responsibilities:

- Receive HTTP requests.
- Validate request input.
- Call the required business logic.
- Send data to JSP.
- Select the appropriate view.

### MVC Flow

```text
       USER / BROWSER
              |
              |
              v
         CONTROLLER
           Servlet
              |
              v
            MODEL
       Java Classes / DAO
              |
              v
           DATABASE
              |
              v
            MODEL
              |
              v
         CONTROLLER
              |
              v
             VIEW
             JSP
              |
              v
       USER / BROWSER
```

### Advantages of MVC

1. Separates application responsibilities.
2. Makes code easier to maintain.
3. Improves code reusability.
4. Simplifies testing.
5. Allows frontend and backend responsibilities to be separated.

### MVC vs Without MVC

| Without MVC | With MVC |
|---|---|
| Business logic may be written inside JSP | Business logic is separated |
| Difficult to maintain | Easier to maintain |
| Java and HTML are mixed | Clear separation of concerns |
| Difficult to reuse code | Better code reuse |

---

# Day 5 – Implementing MVC using JSP and Servlets

## 8. MVC Project Structure

We will create a simple Student Management application using MVC.

### Project structure

```text
StudentMVC/
│
├── pom.xml
│
└── src/
    └── main/
        │
        ├── java/
        │   └── com/
        │       └── college/
        │           │
        │           ├── model/
        │           │   └── Student.java
        │           │
        │           ├── dao/
        │           │   └── StudentDAO.java
        │           │
        │           └── controller/
        │               └── StudentController.java
        │
        └── webapp/
            │
            ├── index.jsp
            │
            └── WEB-INF/
                └── views/
                    └── student.jsp
```

### Step 1: Create Student Model

File: `Student.java`

Package: `com.college.model`

```java
package com.college.model;

public class Student {

    private int id;
    private String name;
    private String department;

    public Student(int id, String name, String department) {

        this.id = id;
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
```

Explanation:

- Student represents student information.
- Private variables provide encapsulation.
- Getters allow JSP EL to access the values.

### Step 2: Create StudentDAO

File: `StudentDAO.java`

Package: `com.college.dao`

For this first MVC demonstration, we will use sample data to understand the MVC flow without introducing JDBC again.

```java
package com.college.dao;

import java.util.ArrayList;
import java.util.List;

import com.college.model.Student;

public class StudentDAO {

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        students.add(
            new Student(1, "Arun", "CSE")
        );

        students.add(
            new Student(2, "Priya", "IT")
        );

        students.add(
            new Student(3, "Kumar", "ECE")
        );

        return students;
    }
}
```

Explanation:

- `ArrayList` stores student objects.
- `getAllStudents()` returns all student records.
- In a database-backed application, this class would retrieve records using JDBC.

### Step 3: Create StudentController

File: `StudentController.java`

Package: `com.college.controller`

```java
package com.college.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.college.dao.StudentDAO;
import com.college.model.Student;

@WebServlet("/students")
public class StudentController extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {

        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students =
            studentDAO.getAllStudents();

        request.setAttribute("students", students);

        request.getRequestDispatcher(
            "/WEB-INF/views/student.jsp"
        ).forward(request, response);
    }
}
```

Explanation:

1. `@WebServlet("/students")` maps the Servlet to the `/students` URL.
2. `init()` initializes the DAO object.
3. `doGet()` handles HTTP GET requests.
4. `getAllStudents()` retrieves student data.
5. `setAttribute()` sends the student list to the JSP.
6. `forward()` transfers control to the JSP page.

### Step 4: Create student.jsp

Location: `src/main/webapp/WEB-INF/views/student.jsp`

```jsp
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Student List</title>
</head>
<body>

    <h1>Student Management System</h1>

    <table border="1">

        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Department</th>
        </tr>

        <c:forEach var="student" items="${students}">

            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.department}</td>
            </tr>

        </c:forEach>

    </table>

</body>
</html>
```

For this JSP page, add the JSTL core taglib directive at the top:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

The complete beginning of the JSP file should therefore be:

```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

### Step 5: Create index.jsp

Location: `src/main/webapp/index.jsp`

```jsp
<!DOCTYPE html>
<html>
<head>
    <title>Student Application</title>
</head>
<body>

    <h1>Welcome to Student Management</h1>

    <a href="${pageContext.request.contextPath}/students">
        View Students
    </a>

</body>
</html>
```

### Step 6: Run the application

Deploy the WAR project to Tomcat 10.1.

Open:

```text
http://localhost:8080/StudentMVC/
```

Click:

```text
View Students
```

Expected output:

| ID | Name | Department |
|---|---|---|
| 1 | Arun | CSE |
| 2 | Priya | IT |
| 3 | Kumar | ECE |

### Understand the complete MVC execution

1. User opens the application.
2. User clicks View Students.
3. Browser sends GET request to `/students`.
4. StudentController receives the request.
5. Controller calls StudentDAO.
6. DAO returns the student list.
7. Controller stores the list in request scope.
8. Controller forwards the request to student.jsp.
9. JSP displays the student records using EL and JSTL.

**Important:** The JSP is placed inside `WEB-INF` so users cannot access it directly through a URL. They must go through the controller Servlet.

---

# Day 6 – JSTL (JavaServer Pages Standard Tag Library)

## 9. What is JSTL?

JSTL stands for **JavaServer Pages Standard Tag Library**.

JSTL is a collection of standard tags that simplifies JSP development.

It allows developers to perform common operations without writing Java Scriptlets inside JSP pages.

### Why do we need JSTL?

Consider displaying a list of students.

Without JSTL, developers might write Java loops using Scriptlets.

With JSTL, we can use tags such as:

```jsp
<c:forEach>
```

This makes JSP code cleaner and easier to understand.

### JSTL Libraries

The main JSTL tag libraries are:

| Library | Prefix | Purpose |
|---|---|---|
| Core | c | Conditions, loops, variables |
| Formatting | fmt | Date, number and message formatting |
| Functions | fn | String and collection functions |
| SQL | sql | SQL database operations |
| XML | x | XML processing |

**Note:** For modern MVC applications, use JDBC/DAO classes for database operations instead of JSTL SQL tags.

---

## 10. JSTL Core Tags

The Core library is the most commonly used JSTL library.

### Step 1: Import JSTL Core Library

Add this directive to your JSP page:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
```

The prefix `c` identifies the Core JSTL library.

### A. c:out

Used to display values.

Example:

```jsp
<c:out value="Welcome to JSP"/>
```

Output:

```text
Welcome to JSP
```

It also supports escaping XML-sensitive characters by default, which helps prevent HTML injection when displaying untrusted text.

### B. c:set

Used to create or update variables in a specified scope.

Example:

```jsp
<c:set var="name" value="Arun"/>

<h2>Name: <c:out value="${name}"/></h2>
```

Output:

```text
Name: Arun
```

### C. c:if

Used to execute content when a condition is true.

Example:

```jsp
<c:set var="age" value="21"/>

<c:if test="${age >= 18}">

    <h2>You are eligible to vote.</h2>

</c:if>
```

### D. c:choose, c:when and c:otherwise

Used to implement multiple conditional branches.

Example:

```jsp
<c:set var="marks" value="85"/>

<c:choose>

    <c:when test="${marks >= 90}">
        Grade A+
    </c:when>

    <c:when test="${marks >= 75}">
        Grade A
    </c:when>

    <c:when test="${marks >= 50}">
        Grade B
    </c:when>

    <c:otherwise>
        Fail
    </c:otherwise>

</c:choose>
```

Output:

```text
Grade A
```

### E. c:forEach

Used to iterate through collections, arrays or ranges.

Example:

```jsp
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:forEach var="i" begin="1" end="5">

    <p>Number: ${i}</p>

</c:forEach>
```

Output:

```text
Number: 1

Number: 2

Number: 3

Number: 4

Number: 5
```

### F. c:remove

Used to remove an attribute from a specified scope.

Example:

```jsp
<c:set var="username" value="Arun"/>

<h2>${username}</h2>

<c:remove var="username"/>

<h2>${username}</h2>
```

The second output will be empty because the variable has been removed.

### G. c:catch

Used to catch exceptions that occur inside its body.

Example:

```jsp
<c:catch var="error">

    <%
        int result = 10 / 0;
    %>

</c:catch>

<c:if test="${not empty error}">

    <p>An error occurred.</p>

</c:if>
```

**Teaching note:** This is useful for demonstrating JSTL exception capture, but application errors should normally be handled in the Servlet or service layer.

---

# Day 6 – Advanced JSTL Tags

## 11. JSTL Formatting Tags

The Formatting library is used to format dates, numbers and messages.

Import:

```jsp
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
```

### A. fmt:formatNumber

Used to format numbers.

Example:

```jsp
<fmt:formatNumber
    value="12500.50"
    type="currency"
    currencyCode="INR"/>
```

Possible output:

```text
₹12,500.50
```

### B. fmt:formatDate

Used to format dates.

Example:

```jsp
<%@ page import="java.util.Date" %>

<%
    request.setAttribute("today", new Date());
%>

<fmt:formatDate
    value="${today}"
    pattern="dd-MM-yyyy"/>
```

Possible output:

```text
25-11-2026
```

### C. fmt:setLocale

Used to set the locale for formatting.

Example:

```jsp
<fmt:setLocale value="en_IN"/>
```

This configures the formatting locale to English (India).

---

## 12. JSTL Functions

JSTL Functions library provides built-in functions to work with strings and collections.

Import:

```jsp
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
```

### Important JSTL Functions

| Function | Purpose |
|---|---|
| fn:length() | Returns the length |
| fn:toUpperCase() | Converts to uppercase |
| fn:toLowerCase() | Converts to lowercase |
| fn:contains() | Checks whether a string contains another string |
| fn:startsWith() | Checks the starting characters |
| fn:endsWith() | Checks the ending characters |
| fn:replace() | Replaces text |
| fn:trim() | Removes leading and trailing spaces |
| fn:substring() | Extracts a substring |
| fn:split() | Splits a string |

### Example

```jsp
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="name" value="arun kumar"/>

<h2>${fn:toUpperCase(name)}</h2>

<h2>Length: ${fn:length(name)}</h2>

<h2>${fn:contains(name, "arun")}</h2>
```

Output:

```text
ARUN KUMAR

Length: 10

true
```

---

# Day 6 – Developing Custom Tags in JSP

## 13. What are Custom Tags?

Custom Tags are user-defined JSP tags created to perform reusable operations.

JSTL already provides standard tags, but sometimes applications need their own reusable tags.

For those situations, we can develop custom tags.

### Real-world example

Suppose every page in a college website needs to display:

```text
Welcome to ABC College
```

Instead of writing the same HTML repeatedly, we can create a custom JSP tag.

Then we can reuse it:

```jsp
<college:welcome/>
```

### Advantages of Custom Tags

1. Code reusability.
2. Reduces repeated code.
3. Improves JSP readability.
4. Makes application maintenance easier.
5. Separates reusable presentation logic.

### Custom Tag Development Steps

1. Create a Java tag handler.
2. Create a Tag Library Descriptor (TLD).
3. Configure the tag library.
4. Use the custom tag inside JSP.

### Step 1: Create a Java Tag Handler

File: `WelcomeTag.java`

Package: `com.college.tags`

```java
package com.college.tags;

import java.io.IOException;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class WelcomeTag extends SimpleTagSupport {

    @Override
    public void doTag()
            throws JspException, IOException {

        getJspContext()
            .getOut()
            .write("Welcome to ABC College!");
    }
}
```

Explanation:

- `SimpleTagSupport` is a helper class for developing simple custom tags.
- `doTag()` executes when the JSP custom tag is invoked.
- `getJspContext()` accesses the JSP context.
- `getOut()` obtains the JSP output writer.
- `write()` writes the output to the response.

### Step 2: Create the TLD file

TLD stands for Tag Library Descriptor.

It defines the custom tags available in the application.

Create:

`college.tld`

Location:

```text
src/main/webapp/WEB-INF/college.tld
```

Code:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<taglib xmlns="https://jakarta.ee/xml/ns/jakartaee"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="
        https://jakarta.ee/xml/ns/jakartaee
        https://jakarta.ee/xml/ns/jakartaee/web-jsptaglibrary_3_1.xsd"
        version="3.1">

    <tlib-version>1.0</tlib-version>

    <short-name>college</short-name>

    <uri>http://college.com/tags</uri>

    <tag>

        <name>welcome</name>

        <tag-class>
            com.college.tags.WelcomeTag
        </tag-class>

        <body-content>empty</body-content>

    </tag>

</taglib>
```

### Step 3: Use the Custom Tag in JSP

Create:

`customTagExample.jsp`

```jsp
<%@ taglib prefix="college"
    uri="http://college.com/tags" %>

<html>
<body>

    <h1>Custom Tag Example</h1>

    <college:welcome/>

</body>
</html>
```

Expected output:

```text
Custom Tag Example

Welcome to ABC College!
```

### Understand the complete execution

1. JSP imports the custom tag library using the TLD URI.
2. The JSP container identifies the `welcome` tag.
3. The container invokes the corresponding Java tag handler.
4. `doTag()` executes.
5. The output is displayed in the browser.

**Important:** The TLD URI is an identifier that must match the URI used in the JSP taglib directive. It is not necessarily a real website URL.

---

# Day 6 – Practical Exercise

## 14. Student Management Web Application

### Project Objective

Develop a simple Student Management Web Application using:

- Java
- JSP
- Servlets
- JSTL
- JDBC
- MySQL
- Tomcat 10.1

### Application Requirements

The application should provide:

1. Add Student.
2. View All Students.
3. Search Student.
4. Update Student.
5. Delete Student.

### Project Architecture

```text
StudentWebApp/
│
├── pom.xml
│
└── src/
    └── main/
        │
        ├── java/
        │   └── com/
        │       └── college/
        │           │
        │           ├── model/
        │           │   └── Student.java
        │           │
        │           ├── dao/
        │           │   └── StudentDAO.java
        │           │
        │           ├── connection/
        │           │   └── DBConnection.java
        │           │
        │           └── controller/
        │               └── StudentServlet.java
        │
        └── webapp/
            │
            ├── index.jsp
            │
            └── WEB-INF/
                └── views/
                    ├── student-list.jsp
                    ├── student-form.jsp
                    └── error.jsp
```

### Application Workflow

```text
         USER / BROWSER
                |
                v
         STUDENT SERVLET
                |
                v
           STUDENT DAO
                |
                v
            DB CONNECTION
                |
                v
           MYSQL DATABASE
                |
                v
           STUDENT DAO
                |
                v
         STUDENT SERVLET
                |
                v
          JSP + JSTL + EL
                |
                v
         USER / BROWSER
```

### Student Tasks

**Task 1 – Create Student Form**

Create a JSP page with:

- Student Name
- Student Email
- Student Age
- Department
- Submit Button

**Task 2 – Add Student**

Use a Servlet to receive the form data and call StudentDAO to insert the record into MySQL.

**Task 3 – View Students**

Retrieve all students from MySQL and display them using JSTL `c:forEach`.

**Task 4 – Search Student**

Allow the user to search for a student by ID.

**Task 5 – Update Student**

Allow users to update student information.

**Task 6 – Delete Student**

Allow users to delete a student record.

**Task 7 – Error Handling**

Create an error page to display user-friendly error messages.

### Expected Final Application Pages

1. Home Page.
2. Add Student Page.
3. Student List Page.
4. Edit Student Page.
5. Error Page.

---

# 15. Student Practice Assignments

## Level 1 – Beginner

1. Create your first JSP page.
2. Display your name and department using JSP expressions.
3. Create a JSP page using Scriptlets.
4. Demonstrate JSP Lifecycle.
5. Create a JSP page using Expression Language.
6. Display request parameters using EL.

## Level 2 – Intermediate

1. Create a Servlet and forward data to JSP.
2. Implement JSP exception handling using errorPage.
3. Create a simple MVC application.
4. Display a list of students using JSTL `c:forEach`.
5. Implement conditions using `c:if` and `c:choose`.
6. Format salary using `fmt:formatNumber`.

## Level 3 – Advanced

1. Develop a custom JSP tag.
2. Create a custom tag with an attribute.
3. Implement MVC using JSP, Servlets and JDBC.
4. Develop a complete Student Management Web Application.
5. Implement form validation and error handling.
6. Separate Model, View, Controller and DAO responsibilities.

---

# 16. Module 2 – Knowledge Assessment

1. What is JSP?
2. What is the difference between JSP and Servlet?
3. Explain the JSP Lifecycle.
4. What is the purpose of `jspInit()`?
5. What is the purpose of `_jspService()`?
6. What is Expression Language?
7. What is the difference between Scriptlet and EL?
8. What is the purpose of errorPage?
9. What is MVC Architecture?
10. Explain Model, View and Controller.
11. Why do we use Servlets as Controllers?
12. What is JSTL?
13. What is the purpose of `c:forEach`?
14. What is the difference between `c:if` and `c:choose`?
15. What is the purpose of JSTL Formatting tags?
16. What are JSTL Functions?
17. What is a Custom Tag?
18. What is a TLD file?
19. What is the purpose of SimpleTagSupport?
20. Explain the complete MVC request flow.

---

# 17. Learning Outcomes

After completing Module 2, students should be able to:

- Understand JSP fundamentals.
- Explain the JSP Lifecycle.
- Use JSP declarations, scriptlets and expressions.
- Display dynamic information using EL.
- Handle JSP exceptions.
- Understand MVC Architecture.
- Develop Servlets as controllers.
- Forward data from Servlets to JSP.
- Use JSTL Core, Formatting and Functions libraries.
- Develop reusable Custom Tags.
- Build an MVC-based Student Management Web Application.

**Module 2 Completed – JavaServer Pages (JSP)**

---

**Next Module:** Advanced Java – Servlets and Web Application Development.
