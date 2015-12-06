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
---

## References
---

* [Sample reference] (http://www.beeva.es) Sample reference description

___

[BEEVA](http://www.beeva.com) | 2015
