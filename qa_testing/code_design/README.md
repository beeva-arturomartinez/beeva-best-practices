# Code Design Best Practices
At this point we're going to talk about the code design best practices.


## Index

* [Introduction](#introduction)
	* [Format](#format)
	* [Naming](#naming)
	* [Code Documentation](#code-documentation)
	* [Error processing](#error-processing)
	* [Concurrency](#concurrency)
	* [Tools](#tools)
		* [Documentation](#documentation)
		* [Naming and format](#naming-and-format)
* [Code design rules](#code-design-rules)
* [Code design patterns](#code-design-patterns)
	* [Introduction of code design patterns](#introduction-of-code-design-patterns)
	* [Gang of four patterns](#gang-of-four-patterns)
		* [Creational Patterns](#creational-patterns)
		* [Structural Patterns](#structural-patterns)
		* [Behavioral Patterns](#behavioral-patterns)
    * [Integration Patterns](#integration-patterns)
    	* [Integration Styles](#integration-styles)
    	* [Messaging Systems](#messaging-systems)
    	* [Messaging Channels](#messaging-channels)
    	* [Message Construction](#message-construction)
    	* [Message Routing](#message-routing)
    	* [Message Transformation](#message-transformation)
    	* [System Management](#system-management)
    * [Anti-Patterns](#anti-patterns)
    	* [Software Design](#software-design)
    	* [Object Oriented programming](#object-oriented-programming)
    	* [Programming](#programming)
    	* [Methodological](#methodological)
    	* [Configuracion Management](#configuration-management)
* [Agile metodologies implications](#agile-metodologies-implications)
* [References](#references)

## Introduction

This guide's objective is help to developers to write a good code for improve the code readability and help to anybody to understand the code.

 ![alt text](static/codequality_wtf.png "QA's wtf measure")

## Code Design Rules

### Format

* Tab correctly your code for improve the readability.
* Comment your code for tip to understand complex code or bucles.
* Don't use lines too larges.
* Don't use a lot of params in a method or functions. If there are a lot of params the design is bad and you should consider to use one class or object.
* Use with caution the copy & paste. When you copy a portion of code, think about is usefull use a method for it.
* If a method have a lot of lines, probably is better to divide in more methods to improve the readability and reusability of the code.
* Delete the variables not used anywhere, not only for performance reasons, also for readibility reasons.
* Use UTF-8.
* Replace tabs with spaces (Configure the ide or text editor).


### Naming
Use the same naming style for the code variables, functions, objects and constants improve the code readability and help to a team to understand easier the application code.

There are some recommendations dependant of the programming language, and others are common and usefull for all languages.

Some of common recomendations:

* Use english as common language for comments and naming for allow understand the code for everyone, no main where are they from.
* Use only letters (without accents or similar) and numbers and _ as separator.
* Doesn't start with numbers.
* The constants should be writted in upper cases.
* The names should be descriptive about the method, function or variable does.
* Variables should be in lower case.
* Functions and methods with lower case.


Recommendations based on the programming language:

* Java:
	* Classes must start with upper case.
	* For a class name with two or more words separate it with Upper Case. For example a class called "JavaWorker".
	* For a variable, method or function name separes words with upper case, with the first letter in lower case.
	* Separes one word from a number with _
* Python:
	* Separe the words in a variable, function or method name with the character _
	* Class names should be in lower case.


### Code Documentation

It's very important to document the code, to help to anybody to understand the code and how can use it. All programming languages allows document the code using comments in determinated format.

There are some considerations that you must have when you write the documentation:

* All classes and public methods and variables must be documented.
* Write usefull information about what a class or method does and how to use it.
* The functions and methods parameters should be included in the documentation with the possibles values and format expected. For example if is numeric, if is required or not, if is a text with some posible values...
* The posible return values must be included so, including the values returned when something's wrong
* If the function or method throws one or more exceptions is very important include it in the documentation.
* Use UTF-8 characters, don't use accents.
* Include the author of the comments. If one partner need to solve some dubts automatically know who develop it and can ask him for help.

### Error processing

Is very important to have one unique strategy for processing the errors of your application. For this goal, there are some common tips that you can consider to process the errors correctly in all posible situations.

* Define the error detail objetive. It's not the same one error for an developer or sysops person than an error to show to the client. An error for a client should help it about what can do to solve or who have to contact.
* Define a common object error for all project. All methods or function must use it for return the error.
* Define a error code list for the errors.
* If is an error which can be shown to the client, use this code list with a internationalitable file for the descriptions.
* Log correctly the error with all information available about this cause and when it occurred.
* Use a common format for all error logs.


### Concurrency

### Tools
There are a lot of tools that help you to improve your code design for all programming languages. It's very recommended to use them for help to mantain the same format in all project and prevent errors.

#### Documentation
The documentation tools make it possible to generate documentation directly from your source code. Each programming language have its own tools, some of them are:

* **Java**: [Javadoc](http://www.oracle.com/technetwork/articles/java/index-jsp-135444.html) 
* **Python**: [PyDoc](https://wiki.python.org/moin/PyDoc) 
* **Javascript**: [jsdoc toolkit](https://code.google.com/p/jsdoc-toolkit/) 
* **php**: [phpDocumentor](http://www.phpdoc.org/) 
* **Ruby**: [Rubydoc](http://ruby-doc.org/) 


#### Naming and format
There are a lot of tools to help to improve the code's quality and design. These tools allows to review the application code to find naming errors, variables not used, or to force to the developer to comment his methods and classes.

One of the most used tool for this goal is [Sonar](http://www.sonarqube.org/). Normally Sonar is installed in the server, configured for Continuous Integration with other tools (like Jenkins) and ensure that the code must be correct before the deployment.

Other tools that helps you depending on the programming language:

* **Java**: [Checkstyle](http://checkstyle.sourceforge.net/) and [PMD](https://pmd.github.io/) with maven plugin, allow to configure some configuration rules and test that your code is fine when you build your java application, preserving for deployment server errors. You should configure the same rules than sonar. Other usefull tool is [FindBugs](http://findbugs.sourceforge.net/) .
* **Python**: [Pylint](http://www.pylint.org/) 
* **PHP**: [PHPCheckstyle](https://github.com/PHPCheckstyle/phpcheckstyle) 


## Code Design Patterns
---
#### Introduction of code design patterns
The design patterns are solutions to common problems in the software development. They are defined by experienced programmers that seen a common ways to solve the similar problems.

A pattern is designed according to a template

In this point we are going to list the most common design patterns, and when we could use them.
#### Gang of four patterns
In 1994, four authors Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides published a book titled Design Patterns - Elements of Reusable Object-Oriented Software which initiated the concept of Design Pattern in Software development.

These authors are collectively known as Gang of Four (GOF). According to these authors design patterns are primarily based on the following principles of object orientated design:

- Program to an interface not an implementation
- Favor object composition over inheritance

If you want more information you can visit the wikipedia page [Gang Of Four](https://en.wikipedia.org/wiki/Design_Patterns)

At this point we can define the following three categories of patterns:

##### Creational Patterns
These design patterns provide a way to create objects while hiding the creation logic, rather than instantiating objects directly using new opreator. This gives program more flexibility in deciding which objects need to be created for a given use case.


- **Abstract factory pattern**
	- ***Definition***

		Abstract Factory patterns work around a super-factory which creates other factories. This factory is also called as factory of factories. This type of design pattern comes under creational pattern as this pattern provides one of the best ways to create an object.

		In Abstract Factory pattern an interface is responsible for creating a factory of related objects without explicitly specifying their classes. Each generated factory can give the objects as per the Factory pattern.

	- ***Design***

		![alt text](static/640px-Abstract_Factory_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- A system should be independent of how they are created, make up and represent their products.
		- A system must be configured with a family of products from several.
		- A family of related product objects is designed to be used jointly and is necessary to enforce this restriction.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Abstract_factory_pattern)
- **Builder pattern**

	- ***Definition***

		Builder pattern builds a complex object using simple objects and using a step by step approach. This type of design pattern comes under creational pattern as this pattern provides one of the best ways to create an object.

		A Builder class builds the final object step by step. This builder is independent of other objects.

	- ***Design***

		![alt text](static/640px-Builder_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- If a class has an internal complex structure.
		- If a class has dependant attributes each other.
		- If a class uses other system objects that are difficult or inconvenient to obtain during its creation.
	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Builder_pattern)
- **Factory method pattern**

	- ***Definition***

		In Factory pattern, we create object without exposing the creation logic to the client and refer to newly created object using a common interface.

	- ***Design***

		![alt text](static/640px-Factory_Method_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- When you want to create an extensible framework.
		- When you want to be a subclass instead of a superclass, that decides what kind of object must be created.
		- When you know when to create an object, but does not know the object type.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Factory_method_pattern)
- **Prototype pattern**

	- ***Definition***

		It facilitates dynamic creation to define classes whose objects can create copies of themselves.

	- ***Design***

		![alt text](static/prototype-uml.jpg "UML Diagram")

	- ***Applicability***
		- The same as the definition.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Prototype_pattern)
- **Singleton pattern**

	- ***Definition***

		It allows a single instance of a class in the system, at a time that allows all classes have access to that **unique** instance.

	- ***Design***

		![alt text](static/250px-Singleton_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- To store configurations.
		- To cache objects in memory.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Singleton_pattern)

##### Structural Patterns
These design patterns concern class and object composition. Concept of inheritance is used to compose interfaces and define ways to compose objects to obtain new functionalities.

 - **Adapter pattern**

	- ***Definition***

		Adapter pattern works as a bridge between two incompatible interfaces. This type of design pattern comes under structural pattern as this pattern combines the capability of two independent interfaces.

		This pattern involves a single class which is responsible to join functionalities of independent or incompatible interfaces.

	- ***Design***

		![alt text](static/640px-Adapter_using_inheritance_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- When you want to use an object in an environment that expects a different interface offered by the object.
		- When you need a translation between the interfaces of several objects.
		- When an object must be used like an intermediary for a group of classes, and only is possible to know in execution time what class will be used.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Adapter_pattern)

 - **Bridge pattern**

	- ***Definition***

		Bridge pattern is used when we need to decouple an abstraction from its implementation so that the two can vary independently. This type of design pattern comes under structural pattern as this pattern decouples implementation class and abstract class by providing a bridge structure between them.

		This pattern involves an interface which acts as a bridge which makes the functionality of concrete classes independent from interface implementer classes. Both types of classes can be altered structurally without affecting each other.

	- ***Design***

		![alt text](static/Bridge_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- When you want flexibility between abstraction and implementation of component, avoiding a static relationship between the two.
		- When the changes in the implementation should not be visible to the clients.
		- When you identify multiple abstractions and implementations of components.
		- When it is appropriate create subclasses, but you want to handle independently the two aspects of the system.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Bridge_pattern)

 - **Composite pattern**

	- ***Definition***

		Composite pattern is used where we need to treat a group of objects in similar way as a single object. Composite pattern composes objects in term of a tree structure to represent part as well as whole hierarchy. This type of design pattern comes under structural pattern as this pattern creates a tree structure of group of objects.

		This pattern creates a class that contains group of its own objects. This class provides ways to modify its group of same objects.

	- ***Design***

		![alt text](static/640px-Composite_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- When there is a component modeled as a branch-leaf structure (or part-whole, or contained-container).
		- When the structure can have any level of complexity and be dynamic.
		- When you want to deal uniformly the structure of the component, using common operations in all the hierarchy.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Composite_pattern)

 - **Decorator pattern**

	- ***Definition***

		Decorator pattern allows a user to add new functionality to an existing object without altering its structure. This type of design pattern comes under structural pattern as this pattern acts as a wrapper to existing class.

		This pattern creates a decorator class which wraps the original class and provides additional functionality keeping class methods signature intact.

	- ***Design***

		![alt text](static/606px-Decorator_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- When you want to make dynamic changes that be transparents to the users, without the restrictions that implies the creation of subclasses.
		- When they may introduce or remove components capabilities at runtime.
		- when there are features which vary independently, to be applied dynamically and can be arbitrarily combined on a component.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Decorator_pattern)

 - **Facade pattern**

	- ***Definition***

		Facade pattern hides the complexities of the system and provides an interface to the client using which the client can access the system. This type of design pattern comes under structural pattern as this pattern adds an interface to existing system to hide its complexities.

		This pattern involves a single class which provides simplified methods required by client and delegates calls to methods of existing system classes.

	- ***Design***

		![alt text](static/640px-Facade_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***
		- To simplify the use of the complex systems providing an interface easier without delete the advanced options.
		- To reduce the coupling between the clients and the subsystems.
		- To introduce layers in the subsystems, providing facades to the subsystems groups.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Facade_pattern)

 - **Flyweight pattern**

	- ***Definition***

		Flyweight pattern is primarily used to reduce the number of objects created and to decrease memory footprint and increase performance. This type of design pattern comes under structural pattern as this pattern provides ways to decrease object count thus improving the object structure of application.

		Flyweight pattern tries to reuse already existing similar kind objects by storing them and creates new object when no matching object is found.

	- ***Design***

		![alt text](static/Flyweight_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability*** 

		Only when this conditions are true:
		- The application uses a lot of identical objects or almost identical.
		- For all the almost identical objects, the different parts may be separated from the similar parts, allowing the sharing of the common parts.
		- The object groups almost identical may be replaced with a shared object when the differents parts of the state have been removed.
		- The application needs to differentiate between the almost identical objects in their original state.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Flyweight_pattern)

 - **Proxy pattern**

	- ***Definition***

		In proxy pattern, we create object having original object to interface its functionality to outer world.

	- ***Design***

		![alt text](static/400px-Proxy_pattern_diagram.svg.png "UML Diagram")

	- ***Applicability***

		Use this pattern when you need a reference more elaborate to an object rather than a simple reference. There are many different approaches:
		- Remote proxy: when you need a local representative for an remote object, or when reside in a different namespace.
		- Virtual proxy: when it is acting as a representative and delegate the creation of expensive objects.
		- Protector proxy: to establish the access rights to the real object.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Proxy_pattern)

##### Behavioral Patterns
These design patterns are specifically concerned with communication between objects.

 - **Chain of responsibility pattern**

	- ***Definition***

		As the name suggests, the chain of responsibility pattern creates a chain of receiver objects for a request. This pattern decouples sender and receiver of a request based on type of request. This pattern comes under behavioral patterns.

		In this pattern, normally each receiver contains reference to another receiver. If one object cannot handle the request then it passes the same to the next receiver and so on.

	- ***Design***

		![alt text](static/Chain_of_responsibility_UML_diagram.png "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- Exists a group of system objects that may respond potentially to the same types of messages.
		- The messages must be handled by one of the several system objects.
		- The messages follow the model "handle or forward", ergo, some events may be handled at the same level where are received or produced, while the others must be forwarded to a other object.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Chain-of-responsibility_pattern)

 - **Command pattern**

	- ***Definition***

        A request is wrapped under an object as command and passed to invoker object. Invoker object looks for the appropriate object which can handle this command and passes the command to the corresponding object which executes the command.

	- ***Design***

		![alt text](static/Command_Design_Pattern_Class_Diagram.png "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- To provide support for undo commands, identification process and/or transformations.
		- To put in queue and execute commands in different moments.
		- To decouple the source of a request object that satisfies

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Command_pattern)

 - **Interpreter pattern**

	- ***Definition***

		Interpreter pattern provides a way to evaluate language grammar or expression. This type of pattern comes under behavioral pattern. This pattern involves implementing an expression interface which tells to interpret a particular context. This pattern is used in SQL parsing, symbol processing engine etc.

	- ***Design***

		![alt text](static/Interpreter_UML_class_diagram.jpg "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- You need to interpret a simple language.
		- The resolution of a problem may be expressed in that language.
		- The efficiency do not be a fundamental aspect.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Interpreter_pattern)

 - **Iterator pattern**

	- ***Definition***

		This pattern is used to get a way to access the elements of a collection object in sequential manner without any need to know its underlying representation.

        Also, many languages implements this pattern as the normal way to walk into collections.

	- ***Design***

		![alt text](static/640px-Iterator_UML_class_diagram.svg.png "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- To provide a uniform way and independent for the implementation, with the goal of walk the collection elements.
		- To allow the travel of multiple collections, allowing to the different clients to access simultaneously to the same collection.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Iterator_pattern)

 - **Mediator pattern**

	- ***Definition***

		Mediator pattern is used to reduce communication complexity between multiple objects or classes. This pattern provides a mediator class which normally handles all the communications between different classes and supports easy maintenance of the code by loose coupling.

	- ***Design***

		![alt text](static/mediator_pattern.gif "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- There are complex rules to the object communication in a system.
		- You want simple and handling objects.
		- You want this object classes are redistributable and independent business model of the system.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Mediator_pattern)

 - **Memento pattern**

	- ***Definition***

		Memento pattern is used to restore state of an object to a previous state.

	- ***Design***

		![alt text](static/memento_pattern.gif "UML Diagram")

	- ***Applicability***

		Use this pattern when all conditions below are accomplished:
		- It must to take a snapshot of the object state.
		- This snapshot is used to restore the original state of the object.
		- A direct interface that reads the internal state of the object, violates the encapsulation principle, because reveals the internal functionality.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Memento_pattern)

 - **Observer pattern**

	- ***Definition***

		Observer pattern is used when there is one-to-many relationship between objects such as if one object is modified, its depenedent objects are to be notified automatically.

	- ***Design***

		![alt text](static/observer_pattern.gif "UML Diagram")

	- ***Applicability***

		Use this pattern generally when a system have:
		- At least one emitter message.
		- One or more message receptors that could vary inside an application or between applications.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Observer_pattern)

 - **State pattern**

	- ***Definition***

		In State pattern, we create objects which represent various states and a context object whose behavior varies as its state object changes.

	- ***Design***

		![alt text](static/state_pattern.gif "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- The behaviour of the object depends on their state and the state changes frequently.
		- The methods have long conditional sentences that depends on the state of the object.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/State_pattern)

 - **Strategy pattern**

	- ***Definition***

		In Strategy pattern, we create objects which represent various strategies and a context object whose behavior varies as per its strategy object. The strategy object changes the executing algorithm of the context object.

	- ***Design***

		![alt text](static/strategy_pattern.gif "UML Diagram")

	- ***Applicability***

		Use this pattern when:
		- You have many different ways of execute an action.
		- You don't know what approximation use until the execution moment.
		- You want to introduce easily new ways of achieve an action.
		- You want the code to be easily to maintain when you have to add behaviours.

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Strategy_pattern)

 - **Template method pattern**

	- ***Definition***


	- ***Design***

		![alt text](static/400px-Proxy_pattern_diagram.svg.png "UML Diagram")

	- ***Applicability***

		- 

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Adapter_pattern)

 - **Visitor pattern**

	- ***Definition***


	- ***Design***

		![alt text](static/400px-Proxy_pattern_diagram.svg.png "UML Diagram")

	- ***Applicability***

		- 

	You can find more detailed explanation in this [link](https://en.wikipedia.org/wiki/Adapter_pattern)

#### Integration Patterns
Introduction TO-DO

##### Integration Styles
Introduction TO-DO

 - **File Transfer**
 - **Shared Database**
 - **Remote Procedure**
 - **Messaging**

##### Messaging Systems
 - **Message Channel**
 - **Message**
 - **Pipes and Filters**
 - **Message Router**
 - **Message Translator**
 - **Message Endpoint**

##### Messaging Channels
Introduction TO-DO

 - **Point-to-Point Channel**
 - **Publish-Subscribe Channel**
 - **Datatype Channel**
 - **Invalid Message Channel**
 - **Dead Letter Channel**
 - **Guaranteed Delivery**
 - **Channel Adapter**
 - **Messaging Bridge**
 - **Message Bus**

##### Message Construction
Introduction TO-DO

 - **Command Message**
 - **Document Message**
 - **Event Message**
 - **Request-Reply**
 - **Return Address**
 - **Correlation Identifier**
 - **Message Sequence**
 - **Message Expiration**
 - **Format Indicator**

##### Message Routing
Introduction TO-DO

 - **Content-Based Router**
 - **Message Filter**
 - **Dynamic Router**
 - **Recipient List**
 - **Splitter**
 - **Aggregator**
 - **Resequencer**
 - **Composed Message Processor**
 - **Scatter-Gather**
 - **Routing Slip**
 - **Process Manager**
 - **Message Broker**

##### Message Transformation
Introduction TO-DO

 - **Envelope Wrapper**
 - **Content Enricher**
 - **Content Filter**
 - **Claim Check**
 - **Normalizer**
 - **Canonical Data Model**

##### Messaging Endpoints
Introduction TO-DO

 - **Messaging Gateway**
 - **Messaging Mapper**
 - **Transactional Client**
 - **Polling Consumer**
 - **Event-Driven Consumer**
 - **Competing Consumers**
 - **Message Dispatcher**
 - **Selective Consumer**
 - **Durable Subscriber**
 - **Idempotent Receiver**
 - **Service Activator**

##### System Management
Introduction TO-DO

 - **Control Bus**
 - **Detour**
 - **Wire Tap**
 - **Message History**
 - **Message Store**
 - **Smart Proxy**
 - **Test Message**
 - **Channel Purger**

#### Anti-Patterns
Introduccion TO-DO

##### Software design
 - **Abstraction inversion**

	Not exposing implemented functionality required by callers of a function/method/constructor, so that the calling code awkwardly re-implements the same functionality in terms of those calls.
 - **Ambiguous viewpoint**

	Presenting a model (usually Object-oriented analysis and design (OOAD)) without specifying its viewpoint.
 - **Big ball of mud**

	A system with no recognizable structure.
 - **Database-as-IPC**

	Using a database as the message queue for routine interprocess communication where a much more lightweight mechanism would be suitable
 - **Gold plating**

	Continuing to work on a task or project well past the point at which extra effort is adding value.
 - **Inner-platform effect**

	A system so customizable as to become a poor replica of the software development platform.
 - **Input kludge**

	Failing to specify and implement the handling of possibly invalid input
 - **Interface bloat**

	Making an interface so powerful that it is extremely difficult to implement
 - **Magic pushbutton**

    A form with no dynamic validation, or input assistance such as dropdowns
 - **Race hazard**

	Failing to see the consequences of events that can sometimes interfere with each other
 - **Stovepipe system**

	A barely maintainable assemblage of ill-related components

##### Object-oriented programming

 - **Anemic Domain Model**

	The use of the domain model without any business logic. The domain model's objects cannot guarantee their correctness at any moment, because their validation and mutation logic is placed somewhere outside (most likely in multiple places). Martin Fowler considers this to be an anti-pattern, but some disagree that it is always an anti-pattern.
 - **BaseBean**

	Inheriting functionality from a utility class rather than delegating to it
 - **Call super**

	Requiring subclasses to call a superclass's overridden method
 - **Circle-ellipse problem**

	Subtyping variable-types on the basis of value-subtypes
 - **Circular dependency**

    Introducing unnecessary direct or indirect mutual dependencies between objects or software modules
 - **Constant interface**

	Using interfaces to define constants
 - **God object**

	Concentrating too many functions in a single part of the design (class)
 - **Object cesspool**

	Reusing objects whose state does not conform to the (possibly implicit) contract for re-use
 - **Object orgy**

	Failing to properly encapsulate objects permitting unrestricted access to their internals
 - **Poltergeists**

	Objects whose sole purpose is to pass information to another object
 - **Sequential coupling**

    A class that requires its methods to be called in a particular order
 - **Yo-yo problem**

	A structure (e.g., of inheritance) that is hard to understand due to excessive fragmentation

##### Programming

 - **Accidental complexity**

	Programming tasks which could be eliminated with better tools (as opposed to essential complexity inherent in the problem being solved)
 - **Action at a distance**

	Unexpected interaction between widely separated parts of a system
 - **Blind faith**

    Lack of checking of (a) the correctness of a bug fix or (b) the result of a subroutine
 - **Boat anchor**

	Retaining a part of a system that no longer has any use
 - **Busy waiting**

	Consuming CPU while waiting for something to happen, usually by repeated checking instead of messaging
 - **Caching failure**

	Forgetting to clear a cache that holds a negative result (error) after the error condition has been corrected
 - **Cargo cult programming**

	Using patterns and methods without understanding why
 - **Coding by exception**

	Adding new code to handle each special case as it is recognized
 - **Design pattern**

    The use of patterns has itself been called an anti-pattern, a sign that a system is not employing enough abstraction
 - **Error hiding**

	Catching an error message before it can be shown to the user and either showing nothing or showing a meaningless message. Also can refer to erasing the Stack trace during exception handling, which can hamper debugging.
 - **Hard code**

	Embedding assumptions about the environment of a system in its implementation
 - **Lasagna code**

	Programs whose structure consists of too many layers
 - **Lava flow**

	Retaining undesirable (redundant or low-quality) code because removing it is too expensive or has unpredictable consequences
 - **Loop-switch sequence**

	Encoding a set of sequential steps using a switch within a loop statement
 - **Magic numbers**

	Including unexplained numbers in algorithms
 - **Magic strings**

	Implementing presumably unlikely input scenarios, such as comparisons with very specific strings, to mask functionality.
 - **Repeating yourself**

	Writing code which contains repetitive patterns and substrings over again; avoid with once and only once (abstraction principle)
 - **Shotgun surgery**

	Developer adds features to an application codebase which span a multiplicity of implementors or implementations in a single change
 - **Soft code**

	Storing business logic in configuration files rather than source code[8]
 - **Spaghetti code**

	Programs whose structure is barely comprehensible, especially because of misuse of code structures

##### Methodological

 - **Copy and paste programming**

	Copying (and modifying) existing code rather than creating generic solutions
 - **Golden hammer**

	Assuming that a favorite solution is universally applicable (See: Silver bullet)
 - **Improbability factor**

	Assuming that it is improbable that a known error will occur
 - **Not Invented Here (NIH) syndrome**

	The tendency towards reinventing the wheel (failing to adopt an existing, adequate solution)
 - **Invented here**

	The tendency towards dismissing any innovation or less than trivial solution originating from inside the organization, usually because of lack of confidence in the staff
 - **Premature optimization**

	Coding early-on for perceived efficiency, sacrificing good design, maintainability, and sometimes even real-world efficiency
 - **Programming by permutation (or "programming by accident", or "programming by coincidence")**

    Trying to approach a solution by successively modifying the code to see if it works
 - **Reinventing the square wheel**

	Failing to adopt an existing solution and instead adopting a custom solution which performs much worse than the existing one
 - **Silver bullet**

	Assuming that a favorite technical solution can solve a larger process or problem
 - **Tester Driven Development**

	Software projects in which new requirements are specified in bug reports

##### Configuration management

 - **Dependency hell**

    Problems with versions of required products
 - **DLL hell**

    Inadequate management of dynamic-link libraries (DLLs), specifically on Microsoft Windows
 - **Extension conflict**

    Problems with different extensions to pre-Mac OS X versions of the Mac OS attempting to patch the same parts of the operating system
 - **JAR hell**

    Overutilization of multiple JAR files, usually causing versioning and location problems because of misunderstanding of the Java class loading model

## Agile metodologies implications
---

## [References](references)

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
