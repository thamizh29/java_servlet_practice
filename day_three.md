# Advanced Java – Module 3: Servlets in Depth

## Module Overview

**Module:** 3 – Servlets in Depth  
**Duration:** Day 7 and Day 8  
**Technology:** Java 21, Jakarta Servlets, Apache Tomcat 10.1, Maven

### Learning Objectives

By the end of this module, students will be able to:

- Understand Servlet architecture and its request-processing lifecycle.
- Handle HTTP requests and generate HTTP responses.
- Configure Servlets using annotations and deployment descriptors.
- Understand ServletConfig and ServletContext.
- Implement communication between multiple Servlets.
- Understand and implement session-tracking techniques.
- Follow best practices when developing Servlets.
- Build a Login and Logout System using Servlets.

---

# Day 7 – Servlet Fundamentals in Depth

## 1. Introduction to Servlets

### 1.1 What is a Servlet?

A **Servlet** is a Java class that runs on a web server or Servlet container and handles client requests, usually HTTP requests.

Servlets are mainly used to develop the backend of Java web applications.

For example, consider an online shopping application.

When a user:
- Logs in.
- Searches for a product.
- Adds a product to the cart.
- Places an order.

The browser sends HTTP requests to the server. Servlets receive those requests, process the required operations, and send responses back to the browser.

### 1.2 Real-World Example

Consider a student registration application.

1. The student opens the registration page.
2. The student enters their name, email, and mobile number.
3. The browser sends the information to the server.
4. A Servlet receives the request.
5. The Servlet validates the submitted information.
6. The Servlet calls the required business logic or database layer.
7. The Servlet sends a response or forwards the user to another page.

**Important:** A Servlet does not have to directly communicate with the database. In a properly structured application, it delegates database operations to a DAO or service class.

### 1.3 Servlet vs JSP

| Servlet | JSP |
|---|---|
| Java class | JSP page that is translated into a Servlet |
| Mainly handles request processing | Mainly handles presentation |
| Suitable for controller logic | Suitable for displaying dynamic content |
| Uses Java methods | Uses HTML, EL, and JSP tags |
| Generates HTML programmatically if needed | Makes writing HTML easier |

**Industry practice:** Use Servlets as controllers and JSP as the view layer in a traditional Java web application.

---

## 2. Servlet Architecture

### 2.1 What is Servlet Architecture?

Servlet architecture explains how a browser communicates with a Servlet through a web server and Servlet container.

The Servlet container manages the Servlet's lifecycle, receives requests, creates request and response objects, and invokes the appropriate Servlet method.

### 2.2 Main Components

**1. Client (Browser)**

The client is the application that sends HTTP requests.

Examples:
- Google Chrome
- Mozilla Firefox
- Microsoft Edge

**2. Web Server**

The web server accepts incoming HTTP connections and serves web resources. It can also forward requests to a Servlet container.

**3. Servlet Container**

The Servlet container manages Servlet execution.

Apache Tomcat is an example of a Servlet container.

Its responsibilities include:

- Loading Servlet classes.
- Creating Servlet instances.
- Managing the Servlet lifecycle.
- Creating request and response objects.
- Mapping URLs to Servlets.
- Managing sessions.
- Handling Servlet configuration.

**4. Servlet**

The Servlet processes the incoming request and prepares the response.

**5. Database**

The Servlet may communicate with a database through a DAO or service layer.

### 2.3 Request Processing Flow

```text
       Client Browser
             |
             | HTTP Request
             v
       Web Server
             |
             v
     Servlet Container
             |
             | URL Mapping
             v
        Servlet
             |
             | Business Logic
             v
       Service / DAO
             |
             v
          Database
             |
             | Result
             v
        Servlet
             |
             | HTTP Response
             v
       Client Browser
```

### 2.4 How the Servlet Container Works

Suppose a browser sends this request:

```http
GET /student-app/students HTTP/1.1
Host: localhost:8080
```

The processing steps are:

1. The browser sends an HTTP request.
2. Tomcat receives the request.
3. Tomcat identifies the web application using the context path `/student-app`.
4. Tomcat identifies the URL mapping `/students`.
5. Tomcat finds the corresponding Servlet.
6. Tomcat creates `HttpServletRequest` and `HttpServletResponse` objects.
7. Tomcat invokes the Servlet's `service()` method.
8. The Servlet processes the request.
9. The Servlet writes or forwards the response.
10. Tomcat sends the HTTP response to the browser.

### 2.5 Servlet Class Hierarchy

A Servlet is built around the Servlet API.

```text
                 Servlet
                    |
             GenericServlet
                    |
                HttpServlet
                    |
             Your Servlet Class
```

**Servlet interface**

Defines the basic Servlet lifecycle methods:

- `init()`
- `service()`
- `destroy()`
- `getServletConfig()`
- `getServletInfo()`

**GenericServlet**

An abstract class that provides a protocol-independent implementation of the Servlet interface.

**HttpServlet**

An abstract class designed specifically for HTTP-based applications.

It provides methods such as:

- `doGet()`
- `doPost()`
- `doPut()`
- `doDelete()`
- `doHead()`
- `doOptions()`

### 2.6 Basic Servlet Example

**File: `HelloServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");

        response.getWriter().println(
            "<h1>Hello from Servlet!</h1>"
        );
    }
}
```

Run the application and open:

```text
http://localhost:8080/your-app/hello
```

**Explanation:**

- `@WebServlet("/hello")` maps the URL `/hello` to this Servlet.
- `HttpServlet` provides HTTP-specific request handling.
- `doGet()` handles HTTP GET requests.
- `HttpServletRequest` contains information about the incoming request.
- `HttpServletResponse` is used to create the outgoing response.
- `setContentType()` specifies the response content type.
- `getWriter()` obtains a writer for sending character data.

---

## 3. Handling Servlet Requests and Responses

### 3.1 What is an HTTP Request?

An HTTP request is a message sent by a client to a server to request a resource or perform an operation.

Example:

```http
POST /student-app/register HTTP/1.1
Content-Type: application/x-www-form-urlencoded

name=Arun&email=arun@example.com
```

The request contains:
- HTTP method.
- Request URL.
- Headers.
- Optional request body.

### 3.2 HttpServletRequest

`HttpServletRequest` is an object provided by the Servlet container. It contains information about the client's request.

Common methods:

| Method | Purpose |
|---|---|
| `getParameter("name")` | Reads a submitted form parameter |
| `getParameterNames()` | Returns parameter names |
| `getParameterMap()` | Returns all request parameters |
| `getMethod()` | Gets the HTTP method |
| `getRequestURI()` | Gets the request URI |
| `getContextPath()` | Gets the application context path |
| `getHeader("User-Agent")` | Reads a request header |
| `getCookies()` | Reads cookies sent by the browser |
| `getSession()` | Gets or creates an HTTP session |
| `getSession(false)` | Gets an existing session or returns null |
| `getRemoteAddr()` | Gets the client's network address |

### 3.3 Reading Form Data

**File: `register.html`**

```html
<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>
</head>
<body>

    <h2>Student Registration</h2>

    <form action="register" method="post">

        <label>Name:</label>
        <input type="text" name="name" required>

        <br><br>

        <label>Email:</label>
        <input type="email" name="email" required>

        <br><br>

        <button type="submit">Register</button>

    </form>

</body>
</html>
```

**File: `RegisterServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println(
            "<h2>Registration Successful</h2>"
        );

        response.getWriter().println(
            "<p>Name: " + escapeHtml(name) + "</p>"
        );

        response.getWriter().println(
            "<p>Email: " + escapeHtml(email) + "</p>"
        );
    }

    private String escapeHtml(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#x27;");
    }
}
```

**Important:** Never directly insert untrusted user input into HTML. Escape it or use a suitable output-encoding library. For normal applications, JSP with `<c:out>` is preferable for displaying user-provided text.

### 3.4 GET vs POST

| GET | POST |
|---|---|
| Used mainly to retrieve data | Used to submit data or perform operations |
| Parameters commonly appear in the URL | Data is commonly sent in the request body |
| Can be bookmarked | Not normally bookmarked with submitted data |
| Suitable for searches and page navigation | Suitable for registration and login submissions |
| Should not change server state | Can change server state |

Example GET:

```text
/students?id=101
```

Example POST:

```http
POST /register
Content-Type: application/x-www-form-urlencoded

name=Arun&email=arun@example.com
```

**Security note:** POST does not automatically make data secure. Use HTTPS to protect information in transit. Passwords must never be sent through plain HTTP.

### 3.5 Handling Different HTTP Methods

```java
@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.getWriter().println("Get Products");
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.getWriter().println("Create Product");
    }

    @Override
    protected void doPut(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.getWriter().println("Update Product");
    }

    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.getWriter().println("Delete Product");
    }
}
```

This example demonstrates HTTP method handling. A production application should also validate IDs, authenticate users, authorize operations, and use an appropriate data-access layer.

### 3.6 What is an HTTP Response?

An HTTP response is the message sent by the server to the client after processing a request.

It contains:
- Status code.
- Response headers.
- Response body.

Example:

```http
HTTP/1.1 200 OK
Content-Type: text/html

<h1>Welcome</h1>
```

### 3.7 HttpServletResponse

`HttpServletResponse` is used to create the response sent to the client.

Common methods:

| Method | Purpose |
|---|---|
| `setContentType()` | Sets the response content type |
| `setCharacterEncoding()` | Sets response character encoding |
| `getWriter()` | Writes character data |
| `getOutputStream()` | Writes binary data |
| `sendRedirect()` | Redirects the browser to another URL |
| `sendError()` | Sends an HTTP error response |
| `setStatus()` | Sets the HTTP status code |
| `setHeader()` | Sets a response header |
| `addCookie()` | Adds a cookie to the response |
| `setContentLength()` | Sets response body length |

### 3.8 HTTP Status Codes

| Status | Meaning | Example |
|---|---|---|
| 200 | OK | Request successful |
| 201 | Created | New resource created |
| 302 | Found | Temporary redirect |
| 400 | Bad Request | Invalid request |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | User does not have permission |
| 404 | Not Found | Resource does not exist |
| 405 | Method Not Allowed | Unsupported HTTP method |
| 500 | Internal Server Error | Unexpected server error |

### 3.9 sendRedirect()

`sendRedirect()` tells the browser to make a new request to another URL.

```java
response.sendRedirect("login.jsp");
```

Flow:

```text
Browser
   |
   | Request: /login
   v
LoginServlet
   |
   | Redirect response
   v
Browser
   |
   | New request: /login.jsp
   v
Login Page
```

Important points:

- The browser makes a new HTTP request.
- The URL in the browser changes.
- The original request attributes are not automatically available in the new request.
- Request parameters are not automatically preserved unless included in the redirect URL or otherwise carried forward.

### 3.10 RequestDispatcher

`RequestDispatcher` allows one server-side resource to forward a request to another resource or include another resource's output.

**Forward example:**

```java
request.setAttribute("message", "Welcome to Student Portal");

request.getRequestDispatcher("/welcome.jsp")
       .forward(request, response);
```

Flow:

```text
Browser
   |
   | Request
   v
Servlet
   |
   | Forward
   v
JSP
   |
   | Response
   v
Browser
```

Important points:

- No new browser request is created.
- The browser URL does not change.
- Request attributes remain available.
- The same request and response objects are passed to the target resource.

**Include example:**

```java
request.getRequestDispatcher("/header.jsp")
       .include(request, response);
```

The included resource contributes its output to the current response.

### 3.11 sendRedirect() vs forward()

| sendRedirect() | forward() |
|---|---|
| Browser makes a new request | Server forwards the existing request |
| URL changes | URL does not change |
| Original request attributes are not preserved automatically | Request attributes are available |
| Can redirect to another website | Normally targets a resource in the same web application |
| Uses `HttpServletResponse` | Uses `RequestDispatcher` |

**When to use:**

- Use `forward()` when a Servlet needs to display a JSP with data stored in request attributes.
- Use `sendRedirect()` after successful operations such as login, registration, or form submission when you want a fresh request.

This is commonly used in the Post/Redirect/Get (PRG) pattern.

---

## 4. Servlet Configuration and Context

Servlet configuration explains how a Servlet receives its initialization parameters and how the complete web application shares common information.

Two important interfaces are:

1. `ServletConfig`
2. `ServletContext`

### 4.1 ServletConfig

`ServletConfig` stores configuration information for an individual Servlet.

Each Servlet has its own ServletConfig object.

Use it when you need to provide initialization values to a specific Servlet.

**Example use cases:**

- Page size for a particular Servlet.
- Report type.
- Servlet-specific configuration.
- A feature flag used by one Servlet.

#### Configure using Annotation

**File: `ReportServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
    urlPatterns = "/report",
    initParams = {
        @WebInitParam(
            name = "reportType",
            value = "monthly"
        )
    }
)
public class ReportServlet extends HttpServlet {

    private String reportType;

    @Override
    public void init(ServletConfig config) {

        this.reportType =
            config.getInitParameter("reportType");
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.getWriter().println(
            "Report Type: " + reportType
        );
    }
}
```

Output:

```text
Report Type: monthly
```

#### Important ServletConfig Methods

| Method | Purpose |
|---|---|
| `getInitParameter(name)` | Gets a Servlet initialization parameter |
| `getInitParameterNames()` | Gets all initialization parameter names |
| `getServletName()` | Gets the Servlet name |
| `getServletContext()` | Gets the application ServletContext |

### 4.2 ServletContext

`ServletContext` represents the web application environment.

There is one ServletContext per web application per JVM.

It can be used by multiple Servlets in the same application to share application-level information.

**Example use cases:**

- Application-wide configuration.
- Application name.
- Shared configuration values.
- Application-level attributes.
- Access to resources available inside the web application.

#### Example: Sharing Data Between Servlets

**File: `AppInfoServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/app-info")
public class AppInfoServlet extends HttpServlet {

    @Override
    public void init() {

        ServletContext context =
            getServletContext();

        context.setAttribute(
            "appName",
            "Student Management System"
        );
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        ServletContext context =
            getServletContext();

        String appName =
            (String) context.getAttribute("appName");

        response.getWriter().println(appName);
    }
}
```

The application-wide attribute can be accessed by other Servlets through the same ServletContext.

**Important:** Application attributes are shared between requests and users. Do not store private user-specific information in ServletContext.

### 4.3 ServletConfig vs ServletContext

| ServletConfig | ServletContext |
|---|---|
| Belongs to an individual Servlet | Belongs to the web application |
| One per Servlet | One per web application per JVM |
| Used for Servlet-specific configuration | Used for application-wide information |
| Parameters are configured for a particular Servlet | Context parameters are configured for the application |
| Obtained using `getServletConfig()` | Obtained using `getServletContext()` |

### 4.4 Configuring Using web.xml

Servlets can be configured using annotations or the deployment descriptor `web.xml`.

**File: `src/main/webapp/WEB-INF/web.xml`**

```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app
    xmlns="https://jakarta.ee/xml/ns/jakartaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
        https://jakarta.ee/xml/ns/jakartaee
        https://jakarta.ee/xml/ns/jakartaee/web-app_6_0.xsd"
    version="6.0">

    <context-param>
        <param-name>applicationName</param-name>
        <param-value>Student Management System</param-value>
    </context-param>

    <servlet>
        <servlet-name>ReportServlet</servlet-name>
        <servlet-class>
            com.example.servlet.ReportServlet
        </servlet-class>

        <init-param>
            <param-name>reportType</param-name>
            <param-value>monthly</param-value>
        </init-param>
    </servlet>

    <servlet-mapping>
        <servlet-name>ReportServlet</servlet-name>
        <url-pattern>/report</url-pattern>
    </servlet-mapping>

</web-app>
```

Read the context parameter:

```java
String appName =
    getServletContext().getInitParameter("applicationName");
```

Read the Servlet initialization parameter:

```java
String reportType =
    getServletConfig().getInitParameter("reportType");
```

**Important:** For a given Servlet mapping, avoid defining conflicting annotation and `web.xml` configurations. Choose a clear configuration approach.

---

# Day 8 – Advanced Servlet Concepts

## 5. Collaboration Between Servlets

### 5.1 What is Servlet Collaboration?

Servlet collaboration means multiple Servlets work together to process a single user operation.

A large web application should not place all business logic inside one Servlet.

Instead, responsibilities can be divided between multiple Servlets.

**Example: Online Shopping Application**

- LoginServlet – Handles login.
- ProductServlet – Displays products.
- CartServlet – Manages the shopping cart.
- OrderServlet – Processes orders.
- LogoutServlet – Ends the user session.

These Servlets can collaborate using request dispatching, shared request attributes, session attributes, and application attributes.

### 5.2 Techniques for Servlet Collaboration

1. RequestDispatcher forward.
2. RequestDispatcher include.
3. Request attributes.
4. Session attributes.
5. ServletContext attributes.

#### Method 1: Collaboration Using forward()

**File: `FirstServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        request.setAttribute(
            "message",
            "Data sent from FirstServlet"
        );

        try {
            request.getRequestDispatcher("/second")
                   .forward(request, response);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
```

**File: `SecondServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/second")
public class SecondServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String message =
            (String) request.getAttribute("message");

        response.setContentType("text/plain");

        response.getWriter().println(message);
    }
}
```

Open:

```text
http://localhost:8080/your-app/first
```

Output:

```text
Data sent from FirstServlet
```

**Explanation:**

1. Browser requests `/first`.
2. FirstServlet receives the request.
3. FirstServlet stores data in a request attribute.
4. FirstServlet forwards the request to SecondServlet.
5. SecondServlet reads the request attribute.
6. SecondServlet sends the response.

#### Method 2: Collaboration Using include()

```java
request.getRequestDispatcher("/header")
       .include(request, response);
```

The included Servlet can generate part of the response, and the calling Servlet can continue writing to the same response.

### 5.3 Request Attributes vs Session Attributes vs Context Attributes

| Attribute type | Scope | Available to |
|---|---|---|
| Request attribute | One request | Resources participating in that request |
| Session attribute | One user's session | Requests associated with that session |
| Context attribute | Entire web application | All Servlets and users in that application |

**Example:**

```java
// Request attribute
request.setAttribute("username", "Arun");

// Session attribute
request.getSession().setAttribute("username", "Arun");

// Application attribute
getServletContext().setAttribute("appName", "Student Portal");
```

---

## 6. Session Tracking Techniques

### 6.1 What is Session Tracking?

HTTP is a stateless protocol.

This means the server does not automatically remember previous requests from the same user.

For example:

1. A user logs in.
2. The user opens the dashboard.
3. The user opens the profile page.
4. The user opens the settings page.

The application needs a way to identify that these requests belong to the same logged-in user.

Session tracking techniques help maintain information across multiple HTTP requests.

### 6.2 Session Tracking Techniques

Common techniques:

1. Cookies.
2. HttpSession.
3. URL Rewriting.
4. Hidden Form Fields.

### 6.3 Cookies

A cookie is a small piece of data stored in the browser.

The server sends a cookie through the HTTP response. The browser can send it back in later requests when its cookie rules match.

**Example:**

**File: `CookieServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/cookie")
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Cookie cookie =
            new Cookie("username", "Arun");

        cookie.setMaxAge(60 * 60);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);

        response.getWriter().println(
            "Cookie created successfully"
        );
    }
}
```

**Important:** `setSecure(true)` means the browser sends the cookie only over HTTPS. For local HTTP testing, the browser will not send this cookie back. Use HTTPS when testing secure cookies.

Read a cookie:

```java
Cookie[] cookies = request.getCookies();

if (cookies != null) {

    for (Cookie cookie : cookies) {

        if ("username".equals(cookie.getName())) {

            response.getWriter().println(
                cookie.getValue()
            );
        }
    }
}
```

Delete a cookie:

```java
Cookie cookie =
    new Cookie("username", "");

cookie.setMaxAge(0);
cookie.setPath("/");

response.addCookie(cookie);
```

To delete a cookie, the path and other relevant cookie attributes must match those used when it was created.

**Cookie limitations:**

- Users can disable cookies.
- Cookies have size and browser storage limits.
- Cookies are stored on the client side.
- Sensitive information should not be stored directly in cookies.

### 6.4 HttpSession

`HttpSession` is the most commonly used Servlet API mechanism for maintaining user-specific session data.

The Servlet container manages the session and associates it with the client through a session identifier, commonly stored in a cookie named `JSESSIONID`.

**Example: Create a Session**

```java
HttpSession session = request.getSession();

session.setAttribute("username", "Arun");
session.setAttribute("role", "student");
```

**Example: Read a Session**

```java
HttpSession session = request.getSession(false);

if (session != null) {

    String username =
        (String) session.getAttribute("username");

    response.getWriter().println(
        "Welcome " + username
    );

} else {

    response.sendRedirect("login.jsp");
}
```

**Example: Remove a Session Attribute**

```java
session.removeAttribute("username");
```

**Example: Invalidate a Session**

```java
session.invalidate();
```

### 6.5 Important HttpSession Methods

| Method | Purpose |
|---|---|
| `getSession()` | Gets an existing session or creates one |
| `getSession(false)` | Gets an existing session or returns null |
| `setAttribute()` | Stores information in the session |
| `getAttribute()` | Retrieves session information |
| `removeAttribute()` | Removes a session attribute |
| `invalidate()` | Invalidates the session |
| `getId()` | Returns the session ID |
| `getCreationTime()` | Returns the session creation time |
| `getLastAccessedTime()` | Returns the last access time |
| `setMaxInactiveInterval()` | Sets the inactive timeout in seconds |
| `getMaxInactiveInterval()` | Gets the inactive timeout |

### 6.6 Session Timeout

A session should not remain active forever.

You can configure the timeout in Java:

```java
session.setMaxInactiveInterval(15 * 60);
```

This sets the maximum inactive interval to 15 minutes.

You can also configure it in `web.xml`:

```xml
<session-config>
    <session-timeout>15</session-timeout>
</session-config>
```

The timeout value in `web.xml` is in minutes.

### 6.7 URL Rewriting

URL rewriting passes a session identifier through the URL when cookies are unavailable.

Example:

```java
String url =
    response.encodeURL("dashboard");

response.sendRedirect(url);
```

The Servlet container can append the session ID when URL-based session tracking is enabled and needed.

Example URL:

```text
/dashboard;jsessionid=ABC123...
```

**Important:** Avoid manually adding session IDs to URLs. Use `encodeURL()` or `encodeRedirectURL()` when URL rewriting is required. URLs can be exposed through browser history, bookmarks, logs, and referrer information.

### 6.8 Hidden Form Fields

Hidden form fields can carry values from one form submission to another.

**Example:**

```html
<form action="next" method="post">

    <input
        type="hidden"
        name="username"
        value="Arun"
    >

    <button type="submit">Continue</button>

</form>
```

Read the value in a Servlet:

```java
String username =
    request.getParameter("username");
```

**Limitations:**

- Only works when the user navigates through forms that carry the hidden field.
- Values are visible in the HTML source.
- Users can modify hidden values.
- It does not automatically maintain a session across arbitrary requests.

Never trust hidden fields for authentication, authorization, or sensitive information.

### 6.9 Comparison of Session Tracking Techniques

| Technique | Where data is stored | Main use |
|---|---|---|
| Cookies | Browser | Client-side preferences or session identifier |
| HttpSession | Server-side session storage | User-specific session data |
| URL Rewriting | URL | Session tracking when cookies are unavailable |
| Hidden Form Fields | HTML form | Passing data between form submissions |

**Recommended approach:** Use `HttpSession` for ordinary user login state. Keep sensitive user information on the server, and use secure session cookies over HTTPS.

---

## 7. Best Practices for Servlet Development

Professional Java applications should follow good development practices.

### 7.1 Separate Responsibilities

Avoid writing all application logic inside one Servlet.

Recommended architecture:

```text
Browser
   |
   v
Servlet (Controller)
   |
   v
Service Layer (Business Logic)
   |
   v
DAO Layer (Database Operations)
   |
   v
Database
```

Example:

- Servlet: receives HTTP requests.
- Service: validates and processes business operations.
- DAO: communicates with the database.
- JSP: displays information.

### 7.2 Do Not Store Request-Specific Data in Servlet Instance Variables

Servlets are generally shared by multiple requests and may process concurrent requests.

**Incorrect:**

```java
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private String username;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) {

        username = request.getParameter("username");
    }
}
```

Two users may access this Servlet at the same time. One user's value can overwrite another user's value.

**Correct:**

```java
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) {

        String username =
            request.getParameter("username");
    }
}
```

Use local variables for request-specific data.

### 7.3 Use PreparedStatement

Avoid building SQL queries using string concatenation.

**Incorrect:**

```java
String sql =
    "SELECT * FROM users WHERE email = '" + email + "'";
```

This can allow SQL injection.

**Correct:**

```java
String sql =
    "SELECT * FROM users WHERE email = ?";

PreparedStatement ps =
    connection.prepareStatement(sql);

ps.setString(1, email);
```

### 7.4 Always Close Database Resources

Close JDBC resources such as:

- Connection.
- PreparedStatement.
- ResultSet.

Prefer try-with-resources.

```java
String sql =
    "SELECT * FROM users WHERE email = ?";

try (
    Connection con = dataSource.getConnection();
    PreparedStatement ps = con.prepareStatement(sql)
) {

    ps.setString(1, email);

    try (ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            // Process result
        }
    }

}
```

A `DataSource` with connection pooling is generally preferred over creating a new physical database connection for every request.

### 7.5 Validate User Input

Always validate data received from the browser.

```java
String email =
    request.getParameter("email");

if (email == null || email.isBlank()) {

    response.sendError(
        HttpServletResponse.SC_BAD_REQUEST,
        "Email is required"
    );

    return;
}
```

Client-side validation improves user experience, but server-side validation is mandatory.

### 7.6 Use Proper HTTP Methods

- GET: retrieve information.
- POST: submit data or perform operations.
- PUT: update resources.
- DELETE: delete resources.

Do not use GET requests for operations that change application state.

### 7.7 Use Proper Error Handling

Avoid showing internal exception messages or stack traces to users.

```java
try {

    // Application logic

} catch (Exception e) {

    getServletContext().log(
        "Unexpected application error",
        e
    );

    response.sendError(
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
    );
}
```

Log detailed errors on the server, and show users a safe error page.

### 7.8 Use Secure Session Management

- Use HTTPS.
- Set the session cookie to `HttpOnly`.
- Use `Secure` cookies in HTTPS deployments.
- Configure an appropriate `SameSite` policy.
- Invalidate sessions during logout.
- Regenerate the session ID after authentication to prevent session fixation.
- Apply authorization checks to protected resources.
- Protect state-changing requests against CSRF.

### 7.9 Do Not Use System.out.println() for Production Logging

Use a logging framework such as SLF4J with a suitable logging implementation.

Logging helps developers identify errors and monitor application behavior.

### 7.10 Best Practices Summary

| Practice | Reason |
|---|---|
| Use MVC architecture | Separates application responsibilities |
| Avoid shared mutable Servlet fields | Prevents concurrency problems |
| Use PreparedStatement | Helps prevent SQL injection |
| Close JDBC resources | Prevents resource leaks |
| Validate inputs | Prevents invalid data |
| Use HttpSession carefully | Maintains user-specific state |
| Use HTTPS | Protects information in transit |
| Handle errors properly | Prevents information leakage |
| Use logging | Helps debugging and monitoring |
| Use connection pooling | Improves database connection management |

---

# 8. Practical Project – Login System Using Servlets

## 8.1 Project Overview

**Project Name:** Student Login and Authentication System

### Objective

Build a login system using Java Servlets, JSP, JDBC, and MySQL.

The user should be able to:

1. Register an account.
2. Log in using email and password.
3. Access a protected dashboard.
4. View their profile.
5. Log out.
6. Be redirected to the login page when their session expires or is invalid.

### 8.2 Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Backend programming |
| Maven | Dependency management and build |
| Apache Tomcat 10.1 | Servlet container |
| Jakarta Servlet 6.0 | Request processing |
| JSP | User interface |
| JSTL | Dynamic JSP rendering |
| JDBC | Database connectivity |
| MySQL | Database |

### 8.3 Database Design

Create a database:

```sql
CREATE DATABASE student_portal;

USE student_portal;
```

Create the users table:

```sql
CREATE TABLE users (

    id INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(100) NOT NULL,

    email VARCHAR(150) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);
```

**Important security requirement:** Store password hashes, not plain-text passwords. Use a suitable password-hashing algorithm such as BCrypt or Argon2 through a trusted library. Never store passwords using MD5 or SHA-256 alone.

### 8.4 Project Structure

```text
student-login-system/
│
├── pom.xml
│
└── src/
    └── main/
        │
        ├── java/
        │   └── com/
        │       └── example/
        │           │
        │           ├── model/
        │           │   └── User.java
        │           │
        │           ├── dao/
        │           │   └── UserDAO.java
        │           │
        │           ├── service/
        │           │   └── AuthService.java
        │           │
        │           ├── servlet/
        │           │   ├── RegisterServlet.java
        │           │   ├── LoginServlet.java
        │           │   ├── DashboardServlet.java
        │           │   ├── ProfileServlet.java
        │           │   └── LogoutServlet.java
        │           │
        │           └── filter/
        │               └── AuthenticationFilter.java
        │
        └── webapp/
            │
            ├── index.jsp
            ├── assets/
            │   └── style.css
            │
            └── WEB-INF/
                │
                ├── web.xml
                │
                └── views/
                    ├── login.jsp
                    ├── register.jsp
                    ├── dashboard.jsp
                    └── profile.jsp
```

### 8.5 Application Flow

```text
                  Browser
                     |
                     v
                  Login.jsp
                     |
                     | Email + Password
                     v
                 LoginServlet
                     |
                     v
                 AuthService
                     |
                     v
                  UserDAO
                     |
                     v
                  MySQL
                     |
              Validate Password
                     |
             +-------+-------+
             |               |
          Invalid          Valid
             |               |
             v               v
         Login.jsp       Create Session
                             |
                             v
                       DashboardServlet
                             |
                             v
                        Dashboard.jsp
                             |
                             v
                         LogoutServlet
                             |
                             v
                      Invalidate Session
                             |
                             v
                          Login.jsp
```

### 8.6 Step 1 – Login Page

**File: `login.jsp`**

```jsp
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Student Login</title>
</head>
<body>

    <h2>Student Login</h2>

    <form action="${pageContext.request.contextPath}/login"
          method="post">

        <label>Email:</label>
        <input type="email" name="email" required>

        <br><br>

        <label>Password:</label>
        <input type="password" name="password" required>

        <br><br>

        <button type="submit">Login</button>

    </form>

    <p>
        ${error}
    </p>

    <a href="${pageContext.request.contextPath}/register">
        Create an account
    </a>

</body>
</html>
```

### 8.7 Step 2 – User Model

**File: `User.java`**

```java
package com.example.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String passwordHash;

    public User() {
    }

    public User(
            int id,
            String name,
            String email,
            String passwordHash) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
```

### 8.8 Step 3 – UserDAO

**File: `UserDAO.java`**

Responsibilities:

- Find a user by email.
- Insert a new user.
- Retrieve user information.
- Communicate with MySQL using JDBC.

Example method signature:

```java
public User findByEmail(String email) throws SQLException {
    // Write JDBC query here
}
```

The DAO should use a `DataSource` or another properly managed connection provider and `PreparedStatement`.

### 8.9 Step 4 – Authentication Service

**File: `AuthService.java`**

Responsibilities:

- Validate the submitted email and password.
- Find the user using UserDAO.
- Verify the submitted password against the stored password hash.
- Return the authenticated user when the credentials are valid.

Example method signature:

```java
public User authenticate(
        String email,
        String password) {

    // 1. Find user by email.
    // 2. Verify password hash.
    // 3. Return User if valid.
    // 4. Return null if invalid.
}
```

Use BCrypt or Argon2 for password verification. The service must never compare a plain-text password directly against a stored password hash.

### 8.10 Step 5 – Login Servlet

**File: `LoginServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import com.example.model.User;
import com.example.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {

        authService = new AuthService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
            "/WEB-INF/views/login.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email =
            request.getParameter("email");

        String password =
            request.getParameter("password");

        User user =
            authService.authenticate(email, password);

        if (user != null) {

            HttpSession oldSession =
                request.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session =
                request.getSession(true);

            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getName());

            response.sendRedirect(
                request.getContextPath() + "/dashboard"
            );

        } else {

            request.setAttribute(
                "error",
                "Invalid email or password"
            );

            request.getRequestDispatcher(
                "/WEB-INF/views/login.jsp"
            ).forward(request, response);
        }
    }
}
```

**Important teaching point:** The example invalidates an old session and creates a new one after successful authentication. This helps prevent session fixation. A production application should also configure secure session cookies and appropriate CSRF protections.

### 8.11 Step 6 – Authentication Filter

A Filter can check whether a user is logged in before allowing access to protected pages.

**File: `AuthenticationFilter.java`**

```java
package com.example.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter({"/dashboard", "/profile"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(
            jakarta.servlet.ServletRequest request,
            jakarta.servlet.ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
            (HttpServletRequest) request;

        HttpServletResponse res =
            (HttpServletResponse) response;

        HttpSession session =
            req.getSession(false);

        boolean loggedIn =
            session != null &&
            session.getAttribute("userId") != null;

        if (loggedIn) {

            chain.doFilter(request, response);

        } else {

            res.sendRedirect(
                req.getContextPath() + "/login"
            );
        }
    }
}
```

**Explanation:**

- `Filter` intercepts requests before they reach the target Servlet.
- `getSession(false)` checks for an existing session without creating a new one.
- The filter checks whether the session contains a user ID.
- If authenticated, `chain.doFilter()` allows the request to continue.
- Otherwise, the user is redirected to the login page.

### 8.12 Step 7 – Dashboard Servlet

**File: `DashboardServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
            request.getSession(false);

        request.setAttribute(
            "username",
            session.getAttribute("username")
        );

        request.getRequestDispatcher(
            "/WEB-INF/views/dashboard.jsp"
        ).forward(request, response);
    }
}
```

### 8.13 Step 8 – Dashboard JSP

**File: `dashboard.jsp`**

```jsp
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

    <h1>Welcome, <c:out value="${username}"/>!</h1>

    <p>You have successfully logged in.</p>

    <a href="${pageContext.request.contextPath}/profile">
        View Profile
    </a>

    <br><br>

    <form action="${pageContext.request.contextPath}/logout"
          method="post">

        <button type="submit">Logout</button>

    </form>

</body>
</html>
```

### 8.14 Step 9 – Logout Servlet

**File: `LogoutServlet.java`**

```java
package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
            request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect(
            request.getContextPath() + "/login"
        );
    }
}
```

**Why use POST for logout?**

Logging out changes the user's authentication state. It should not be triggered by a normal GET request. In production, protect the logout POST request with CSRF protection.

### 8.15 Step 10 – Testing the Application

Test these scenarios:

| Test Case | Expected Result |
|---|---|
| Open login page | Login page appears |
| Enter valid credentials | Dashboard appears |
| Enter invalid credentials | Error message appears |
| Open dashboard without login | Redirect to login |
| Open profile without login | Redirect to login |
| Click logout | Session is invalidated |
| Open dashboard after logout | Redirect to login |
| Wait for session timeout | User must log in again |
| Submit empty email or password | Validation error |

**Note:** The code above demonstrates the Servlet flow and application structure. The complete application requires implementing the JDBC DAO, password hashing, registration flow, profile flow, and the Maven dependencies before it can run end to end.

---

# 9. Practical Assignments

## Assignment 1 – Request and Response

Create a Servlet that accepts:

- Student name.
- Student email.
- Student age.
- Course name.

Requirements:

1. Accept data using POST.
2. Read form data using `getParameter()`.
3. Validate the fields.
4. Display the submitted information.
5. Return a suitable HTTP status for invalid input.

## Assignment 2 – ServletConfig and ServletContext

Create two Servlets:

- `AdminServlet`
- `StudentServlet`

Requirements:

1. Configure an initialization parameter for AdminServlet.
2. Read the parameter using ServletConfig.
3. Store the application name using ServletContext.
4. Read the application name from StudentServlet.
5. Compare ServletConfig and ServletContext.

## Assignment 3 – Servlet Collaboration

Create:

- FirstServlet
- SecondServlet
- ThirdServlet

Requirements:

1. FirstServlet receives student information.
2. FirstServlet stores the information in request attributes.
3. FirstServlet forwards the request to SecondServlet.
4. SecondServlet processes the information.
5. SecondServlet forwards to ThirdServlet.
6. ThirdServlet displays the result.

## Assignment 4 – Session Tracking

Create a simple shopping cart application.

Requirements:

1. Add products to a cart.
2. Store the cart in HttpSession.
3. Display all cart products.
4. Remove a product from the cart.
5. Clear the cart.
6. Maintain the cart when navigating between pages.

## Assignment 5 – Login and Logout

Develop the complete Login System described in this module.

Requirements:

- Registration.
- Login.
- Password hashing.
- Session creation.
- Protected dashboard.
- Authentication filter.
- Logout.
- Session timeout.
- Server-side validation.

---

# 10. Interview Questions

1. What is a Servlet?
2. What is the role of a Servlet container?
3. What is the difference between GenericServlet and HttpServlet?
4. Explain the Servlet lifecycle.
5. What is the difference between doGet() and doPost()?
6. What is HttpServletRequest?
7. What is HttpServletResponse?
8. What is the difference between forward() and sendRedirect()?
9. What is RequestDispatcher?
10. What is ServletConfig?
11. What is ServletContext?
12. What is the difference between ServletConfig and ServletContext?
13. Why is HTTP called a stateless protocol?
14. What is session tracking?
15. What is HttpSession?
16. What is the difference between getSession() and getSession(false)?
17. What is the difference between cookies and HttpSession?
18. What is URL rewriting?
19. What is session timeout?
20. Why should a Servlet avoid storing request-specific data in instance variables?
21. What is Servlet collaboration?
22. What is a Servlet Filter?
23. What is session fixation?
24. Why should passwords be hashed?
25. Why should logout use POST rather than GET?

---

# 11. Module 3 – Final Learning Outcomes

After completing Module 3, students should be able to:

- Explain the Servlet architecture.
- Understand how Tomcat processes HTTP requests.
- Create Servlets using Jakarta Servlet API.
- Handle GET and POST requests.
- Read request parameters and create HTTP responses.
- Use forward and redirect correctly.
- Configure ServletConfig and ServletContext.
- Implement communication between multiple Servlets.
- Use cookies and HttpSession.
- Understand URL rewriting and hidden form fields.
- Apply Servlet development best practices.
- Develop a secure foundation for a Login and Logout System.

## Module 3 Completion Checklist

- [ ] Understand Servlet architecture.
- [ ] Create and run a basic Servlet.
- [ ] Handle GET and POST requests.
- [ ] Read request parameters.
- [ ] Generate HTTP responses.
- [ ] Use RequestDispatcher.
- [ ] Understand forward vs redirect.
- [ ] Configure ServletConfig.
- [ ] Configure ServletContext.
- [ ] Implement Servlet collaboration.
- [ ] Understand cookies.
- [ ] Implement HttpSession.
- [ ] Understand URL rewriting.
- [ ] Understand hidden form fields.
- [ ] Implement authentication filters.
- [ ] Complete the Login and Logout System.
