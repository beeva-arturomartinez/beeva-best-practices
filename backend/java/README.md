# JAVA Best Practices

This is how we work with Java at BEEVA.

![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")
![JAVA](static/java.png "JAVA")

## Index

* [Introduction](#java-introduction)
* [Choosing proper names inside our code](#choosing-proper-names-inside-our-code)
* [Function's design](#function's-design)
* [Comments and documentation](#comments-and-documentation)
* [Code styling and formatting](#code-styling-and-formatting)
* [Encapsulation](#encapsulation)
* [Exception handling](#exception-handling)
* [Log traces](#log-traces)
* [Code testing](#code-testing)
* [Hints about code optimization](#hints-about-code-optimization)
* [References](#references)

## Introduction

This document set some principles and recommendations for developing JAVA applications.

The most important thing that we, as developers, must have in our minds is that our code is written for other persons, and not for compilers. For this reason, we really care about readability, clarity and good structure of our code.

It is also very important to backup our code with a good automated battery test, raising the level of confidence when complex refactors or simply improvements in code are needed.

This is not an static document, but a living one. We will be adding new hints and sections as we go through the path to have an exceptional code.

---

## Choosing proper names inside our code
---

## Function's design
---

## Comments and documentation
---

## Code styling and formatting
---

## Encapsulation
---

## Exception handling

Nobody likes the exceptions but we always have to deal with them. The good part is than Java Exception Handling Framework is very robust and easy to understand and use. Exceptions can come up from different kind of situations: wrong data entered by user, hardware failure, network connection failure, database server error. In this section, we will learn how exceptions are handled in Java.

Java is a OOP language, so when a error occurs while executing a statement, creates an **exception object** and then the normal flow of the program halts and tries to find someone that can handle the raised exception. The exception object contains a lot of debugging information such as method hierarchy, line number where the exception occurred, type of exception... When the exception occurs in a method, the process of creating the exception object and handing it over to runtime environment is called **throwing the exception**.

Once runtime receives the exception object, it tries to find the handler for the exception. Exception handler is the block of code that can process the exception object. The logic to find the exception handler is simple – starting the search in the method where error occurred, if no appropriate handler found, then move to the caller method and so on. So if methods call stack is A->B->C and exception is raised in method C, then the search for appropriate handler will move from C->B->A. If appropriate exception handler is found, exception object is passed to the handler to process it. The handler is said to be *catching the exception*. If there are no appropriate exception handler found then program terminates printing information about the exception.

### Keywords

Java provides specific keywords for exception handling purposes:

* **throw:** We know that if any exception occurs, an exception object is getting created and then Java runtime starts processing to handle them. Sometime we might want to generate exception explicitly in our code, for example in a user authentication program we should throw exception to client if the password is null. **throw** keyword is used to throw exception to the runtime to handle it.

* **throws:** When we are throwing any exception in a method and not handling it, then we need to use **throws** keyword in method signature to let caller program know the exceptions that might be thrown by the method. The caller method might handle these exceptions or propagate it to it’s caller method using throws keyword. We can provide multiple exceptions in the throws clause and it can be used with main() method also.

* **try-catch:** We use try-catch block for exception handling in our code. **try** is the start of the block and **catch** is at the end of try block to handle the exceptions. We can have multiple catch blocks with a try and try-catch block can be nested also. catch block requires a parameter that should be of type Exception.

* **finally:** finally block is optional and can be used only with try-catch block. Since exception halts the process of execution, we might have some resources open that will not get closed, so we can use finally block. finally block gets executed always, whether exception occurred or not.

The following example shows the use of the keywords to handle exceptions:

```java
package com.beeva.examples;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionHandling {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		try {
			testException(-5);
			testException(-10);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Releasing resources");			
		}

		testException(15);
	}

	public static void testException(int i) throws FileNotFoundException, IOException {
		if (i < 0) {
			throw new FileNotFoundException("Negative Integer " + i);
		} else if (i > 10) {
			throw new IOException("Only supported for index 0 to 10");
		}
	}
}
```

The output of the program is:

```
java.io.FileNotFoundException: Negative Integer -5
  at com.journaldev.exceptions.ExceptionHandling.testException(ExceptionHandling.java:24)
  at com.journaldev.exceptions.ExceptionHandling.main(ExceptionHandling.java:10)

Releasing resources

Exception in thread "main" java.io.IOException: Only supported for index 0 to 10
  at com.journaldev.exceptions.ExceptionHandling.testException(ExceptionHandling.java:27)
  at com.journaldev.exceptions.ExceptionHandling.main(ExceptionHandling.java:19)
```

Notice than *testException(-10)* never get executed because of exception *testException(-5)* and then execution of finally block after try-catch block is executed.

### Types of Exceptions

Java Exceptions are hierarchical and inheritance is used to categorize different types of exceptions. Throwable is the parent class of Java Exceptions Hierarchy and it has two child objects – Error and Exception. Exceptions are further divided into checked exceptions and runtime exception.

* **Errors:** Errors are exceptional scenarios that are out of scope of application and it’s not possible to anticipate and recover from them, for example hardware failure, JVM crash or out of memory error. That’s why we have a separate hierarchy of errors and we should not try to handle these situations. Some of the common Errors are OutOfMemoryError and StackOverflowError.

* **Checked Exceptions:** Checked Exceptions are exceptional scenarios that we can anticipate in a program and try to recover from it, for example FileNotFoundException. We should catch this exception and provide useful message to user and log it properly for debugging purpose. Exception is the parent class of all Checked Exceptions and if we are throwing a checked exception, we must catch it in the same method or we have to propagate it to the caller using throws keyword.

* **Runtime Exception:** Runtime Exceptions are cause by bad programming, for example trying to retrieve an element from the Array. We should check the length of array first before trying to retrieve the element otherwise it might throw ArrayIndexOutOfBoundException at runtime. RuntimeException is the parent class of all runtime exceptions. If we are throwing any runtime exception in a method, it’s not required to specify them in the method signature throws clause. Runtime exceptions can be avoided with better programming.

### Exception methods

Exception and all of it’s subclasses doesn’t provide any specific methods and all of the methods are defined in the base class Throwable. The exception classes are created to specify different kind of exception scenarios so that we can easily identify the root cause and handle the exception according to it’s type. Throwable class implements Serializable interface for interoperability.

Some of the useful methods of Throwable class are:

* **public String getMessage():** This method returns the message String of Throwable and the message can be provided while creating the exception through it’s constructor.

* **public String getLocalizedMessage():** This method is provided so that subclasses can override it to provide locale specific message to the calling program. Throwable class implementation of this method simply use getMessage() method to return the exception message.

* **public synchronized Throwable getCause():** This method returns the cause of the exception or null id the cause is unknown.

* **public String toString():** This method returns the information about Throwable in String format, the returned String contains the name of Throwable class and localized message.

* **public void printStackTrace():** This method prints the stack trace information to the standard error stream, this method is overloaded and we can pass PrintStream or PrintWriter as argument to write the stack trace information to the file or stream.

### Recommendations

#### Categorize exceptions by cause

Since exceptions come with a *stacktrace*, it is more useful to categorize them by subsystem causing failure, rather than by which application module the failure occurred in. For example, categorizing exceptions into SQL, file access, or configuration types is generally far more useful than separate types for Customer, Account, and Order modules.

#### Simple exception hierarchy

A simple hierarchy is easy for developers to use & throw, making it obvious to find *the right exception*. It should offer basic broad categories for diagnosis & handling.

Overly complicated hierarchies, non-obvious naming, or per-module exceptions leave developers scratching their head looking round for what to use. It shouldn’t be that hard. Library code which is genuinely separate from the application body may deserve it’s own exceptions, but don’t go overboard.

A clean & effective hierarchy may look like this:

* FailureException extends RuntimeException
  * AppSQLException
  * AppFileException
  * AppConfigException
  * AppDataException
  * AppInternalException


* RecoverableException extends RuntimeException
  * UserException
  * ValidationException

In this hierarchy, exceptions a developer needs to rethrow will be under an “App*” exception classname. This makes it easy for developers to find & throw the correct type.

#### Converting Checked Exception into RuntimeException

This is one of the technique used to limit use of checked Exception in many of frameworks like Spring, where most of checked Exception, which stem from JDBC is wrapped into DataAccessException, an unchecked Exception.

This Java best practice provides benefits, in terms of restricting specific exception into specific modules, like SQLException into DAO layer and throwing meaningful RuntimeException to client layer.

#### Use Standard Exceptions

Using standard Exception instead of creating own Exception every now and then is much better in terms of maintenance and consistency. Reusing standard exception makes code more readable, because most of Java developers are familiar with standard RuntimeException from JDK like, *IllegalStateException*, *IllegalArgumentException* or *NullPointerException*, and they will immediately be able to know purpose of Exception, instead of looking out another place on code or docs to find out purpose of user defined Exceptions.

#### Catch at the outermost level

Exceptions must be reported to the business/external world, and this is done by returning a 500 “error response” or displaying an error in the UI.

Since such an ‘catch’ block or exception handler requires access to the outside to respond, it means exceptions must be caught & handled at the outermost level. Simultaneously, the final handler should log the exception & full *stacktrace* for investigation.

**Catch blocks** in internal code should be avoided and their use minimized as they interfere with the reliable propagation of exceptions to the outermost handler.

#### Rethrow with a cause

In most projects, the most common exception handling code is actually just to rethrow the exception. While the guide lines above should help you drastically reduce the number of catch-blocks you code, it’s crucial to code the remaining ones correctly.

First and most important, **always include the cause**. Constructing and throwing a wrapper exception must always provide full details of the underlying failure.

Logging should generally be left to the outermost handler. This may seem counter-intuitive, but if your outermost handlers are comprehensive & correct, all exceptions will be logged there. Avoid duplication.

In some methods, we may want to **catch and rethrow** exceptions to provide a more informative message or additional diagnostic information.

#### Never use exceptions for flow control

The exceptions are expensive operations and as the name would suggest, exceptional conditions. Using exceptions for control flow is an anti-pattern, and it carries the following handicaps:

* Leads to more difficult to read and understand code.
* Java has existing control structures designed to solve the problems without the use of exceptions.
* The modern compilers tend to optimize with the assumption that exceptions are not used for control flow.

#### Add informative messages

While throwing runtime exceptions outward requires the minimal amount of code, we may sometimes want better information as to what action & data failures the program failed on.

The ideal, is for exception messages to be self-diagnosing. Any developer should be able to read the exception message & immediately understand what failed.

Great informative reporting requires only a few steps:

1. Throw a type, appropriate to the underlying cause.
2. Message stating what failed.
3. Include the query parameters for data being accessed.

#### Use logging

Exceptions should be logged at the outermost handler, to capture a comprehensive record of failures.

Methods throwing exceptions can also log error or warning lines to enrich the log output & increase the available information. Since major facts should be in the exception message, this is normally used to provide more minor & contextual detail.

As well as recording exceptions, logging should also record your program’s activity & decisions to understand why an exception occurred. Good practice is to log major business requests, decisions, outcomes and actions, these provide the context to understand what your program is doing & why.

---

## Log traces
---

## Code testing
---

### Introduction

We strongly believe that an application is incomplete unless it has a good battery (and ideally, automated) test to ensure that its intended purpose and functionality is fulfilled. Moreover, catching bugs early is really important because the effort put to solve a bug in production is overwhelmingly greater than in development stages. Finally, a good battery test provides a high confidence for developers when it comes to do some complex refactors and maintenance in the application.

A common mistake is to believe that there is a lack of time to build tests for the applications. But if we get used to backup our developments with tests (applying the TDD philosophy) we will realize that is not that expensive and the benefits are so big that this is a worthy task to do.

### Are my tests good?

For us, a good test should satisfy the following guidelines:

- Tests one and only one feature
- Is fast, a slow test usually is a bad designed test
- Does not depends on the order in which is executed related to other tests
- Does not depends on the state of the test environment, in other words could be executed everywhere
- Abstract unnecessary complexity using mocks or stubs
- Does not affect to "real" environments' state
- Should externalize common steps to a centralized point, for example a **setUp()** method
- Follow the AAA pattern (Arrange - Act - Assert)
    - Arrange : prepare test data
    - Act : execution of Code Under Test
    - Assert : verifications to check if test did pass or not
- Is well structured to enhance readability
    - For the sake of readability, sometimes is goo to delegate each AAA phase to a separate method with a proper name

### Test types

We use different types of tests:

- Unitary tests
    - Their purpose is to test classes or methods in isolation (mocking or stubbing external dependencies)
    - We should check that the execution of the test effectively follows the expected flow
    - Infrastructure should be mocked or simulated to increase isolation level (for example, use an in-memory database or mocking the repository classes that access that data)
- Integration tests
    - Their purpose is to test several functional blocks working
    - Do not use mocks or stubs
- Acceptance tests
    - Usually they are specified or defined by the end user
    - There are some tools to implement this type of tests, like cucumber/gherkin
    - These tests are beyond the scope of this document at the moment
- Stress tests
    - Ensure that the whole environment in which the application lives is able to handle a minimum incoming traffic (e.g. requests)
    - These tests are beyond the scope of this document at the moment

### Tools

We use the following tools to implement our tests:

- JUnit
- Mockito
- Wiremock
    - It is beyond of the scope of this guide to document the use of Wiremock, please visit its [official website](http://wiremock.org/) to find out more information about this excellent mocking tool
- Spring test
- Cucumber JVM
- Selenium (for tests that involve some front-end management)
- JMeter and Stem (for stress tests)

### Continuous integration

It is strongly recommended to complement our test batteries with a CI tool, like Jenkins. When possible, we should trigger those batteries whenever a commit is performed on the version control repository and have some feedback mechanism to alert about broken tests (for example, an email distribution list or a slack channel).

We also add to the Continuous Integration flow a Sonar analysis to ensure a minimal code quality in our developments.

### Tests in Agile environments

It is **very important** to include test revision in our Definition Of Done (DoD) and, when possible, to make a cross check with another developer in the team. Could be a good idea to include a new state in our scrum taskboard for this purpose (for example, PENDING > WORK IN PROGRESS > PLEASE TEST > PLEASE QA > COMPLETED)

Also, for complex tasks it is recommended to build a battery test plan together with one or more other members of our team (pair programming or mob programming). This is the only way to ensure that our battery test is good enough to reduce the bugs to the minimum.

### Examples

The following examples use Spring to code tests.

#### Integration test with mock

``` java

    import transfer.TransferApp;
    import transfer.TransfersRequest;
    import transfer.TransfersResponse;
    import com.github.tomakehurst.wiremock.WireMockServer;
    import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
    import org.junit.After;
    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.SpringApplicationConfiguration;
    import org.springframework.test.context.ActiveProfiles;
    import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
    import org.springframework.test.context.web.WebAppConfiguration;

    import static org.hamcrest.Matchers.equalTo;

    /**
     * Tests for Transfer service.
     * This tests run a Wiremock server
     *
     * @author BEEVA
     */
    @RunWith(SpringJUnit4ClassRunner.class)
    @SpringApplicationConfiguration(classes = TransferApp.class)
    @WebAppConfiguration
    @ActiveProfiles("test")
    public class TransferServiceTest {
        private WireMockServer wireMockServer;

        @Autowired
        TransferService transferService;

        @Before
        public void setUp() {
            WireMockConfiguration wmc = new WireMockConfiguration();

            wmc.withRootDirectory("src/test/resources/wiremock");   // Wiremock mapping files
            wmc.port(9999);

            wireMockServer = new WireMockServer(wmc);
            wireMockServer.start();
        }

        @Test
        public void sendTransferOK() throws Exception {
            /**
             * Arrange
             */
            TransfersRequest TransfersRequest = new TransfersRequest();
            TransfersRequest.getReceiver().getAccount().getFormats().setCcc("01824000690201927865");
            TransfersRequest.getReceiver().getCustomer().setName("National test");
            TransfersRequest.getAmount().getCurrency().setId("EUR");
            TransfersRequest.getAmount().setAmount("100.00");
            TransfersRequest.getSender().getAccount().setId("ES0182002000000000000000000034211780XXXXXXXXX");
            TransfersRequest.getSender().getCustomer().setId("58987989E76356194I2015284");
            TransfersRequest.setDescription("Send money to someone");

            /**
             * Act
             */
            TransfersResponse response = transferService.sendMoney(TransfersRequest);


            /**
             * Assert
             */
            Assert.assertThat(response.getId(), equalTo("15500006201041"));
        }

        @After
        public void tearDown() {
            wireMockServer.stop();
        }
    }
```

> **Notes**

    - Always try to use hamcrest matchers together with assertThat() method instead of assert()

    - We only show happy path test here, error cases omitted

#### Unit test with Mockito

``` java

    import rest.service.StatusService;
    import rest.service.impl.StatusServiceImpl;
    import org.junit.Assert;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.Mockito;
    import org.mockito.runners.MockitoJUnitRunner;
    import org.springframework.data.mongodb.core.MongoOperations;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.HttpMethod;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.client.RestTemplate;

    import java.util.HashSet;

    import static org.mockito.Matchers.*;
    import static org.mockito.Mockito.when;

    @RunWith(MockitoJUnitRunner.class)
    public class TestStatusService {

        @Mock
        private RestTemplate restTemplateMock;

        @Mock
        private MongoOperations mongoTemplateMock;

        @InjectMocks
        private StatusService statusService = new StatusServiceImpl();

        HashSet<String> response;

        @Before
        public void setUp() throws Exception {
            response = new HashSet<>();

        }

        @Test
        public void testGeneralStatusOk() throws Exception {
            /**
             * Arrange
             */

            when(mongoTemplateMock.getCollectionNames()).thenReturn(response);
            when(restTemplateMock.exchange(startsWith("http://localhost:8086"), eq(HttpMethod.GET), any(HttpEntity.class), any(Class.class)))
                    .thenReturn(new ResponseEntity<byte[]>(HttpStatus.OK));

            /**
             * Act
             */
            String status = statusService.getGeneralStatus();

            /**
             * Assert
             */
            Assert.assertTrue("OK".equals(status));
            Mockito.verify(mongoTemplateMock).getCollectionNames();
            Mockito.verifyZeroInteractions(restTemplateMock.getForEntity(Mockito.anyString(), Mockito.any(Class.class), Mockito.anyVararg()));
        }
    }
```

## Hints about code optimization
### General recommendations
### Java Enterprise Edition recommendations

| Efficient | Inefficient|
|---|---|
|Multi|aa|
|cell|Multi line cell|
---

## References
---

* [Sample reference] (http://www.beeva.es) Sample reference description

___

[BEEVA](http://www.beeva.com) | 2015
