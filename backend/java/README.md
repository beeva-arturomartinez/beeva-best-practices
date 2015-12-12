# JAVA Best Practices

This is how we work with Java at BEEVA.

![BEEVA](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")
![JAVA](static/java.png "JAVA")

## Index

* [Introduction](#java-introduction)
* [Choosing proper names inside our code](#choosing-proper-names-inside-our-code)
* [Function's design](#function's-design)
* [Comments and documentation](#comments-and-documentation)
* [Formatting and code styling](#formatting-and-code-styling)
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


When we choose a name for variables, functions, arguments, classes, etc, it is important to apply a set of rules that will help us to make our code more consistent, and easier to read and understand. What follows are some of these basic rules for creating correct names:

- The name of a variable, function or class must answer a number of basic questions: it should indicate why it exists, what it does, and how it is used. We have to choose names that reveal intentions,  since can make it much easier to understand and change code. For example, if we have the following variable to indicate a number of days since the last modification:

	*int d;*

	we can not know what it refers with the name *d*. To put a name to a variable, we should choose a name that specifies what is being measured and the unit of that measurement:

	*int daysSinceModification;*

- We have to use full descriptors that describe the variable, the field or the class properly. For example, an appropriate name for a field that defines the name of a user, it would be *firstName* or *userName*. Short names (for example *x1* or *f1*), although are easier to type, not provide any information of what they represent and consequently result in a difficult code to understand, maintain and improve.

- Avoid disinformation, i.e. we should avoid words whose meanings vary from our intended meaning. For example, we do not have to put *customerList* as a variable name, if it is not a list, because for a developer the word list has a very particular meaning.

- Avoid names that are too similar, or that differ only in uppercase or lowercase. For example, names *persistentObject* and *persistentObjects*, or names *anSqlDatabase* and *anSQLDatabase*, should not be used together as they give rise to confusion.

- We need to distinguish names such a way that the reader appreciates the differences. Not just with add number-series or noise words, because if the names have to be different, they must also have a different meaning. The names of numerical series do not provide information, and noise words are another distinction without sense. For example, if we have the class *Product*, and we add another class with the name *ProductData*, we will have two classes with different names but with the same meaning (*Data* is a noise word that not provides any meaning).

- Create names that we can pronounce, because if we cannot pronounce them, we are not going to be able to explain them correctly and we will have errors of understanding. For example, the name of the class *DtaRcrd104* cannot be pronounced.

- Use names that can be searched for. Letter names or numeric constants are not easy to locate in the text. The length of a name must correspond to the size of our scope. If a variable or constant is used in several points of our code, we must assign a name that can be looked for.

- Use abbreviations with moderation and intelligence. This means that we must maintain a standard list of short forms (abbreviations), choose them wisely and use them in a consistent manner. For example, if we want to use an abbreviation for the word *number*, we can use *nbr*, *no* or *num*, but recording the register used (no matter what) and using only that one.

- Avoid encodings, because names encoded are unpronounceable and are usually written incorrectly. In Java there is no need to encode types, as is done in other languages with the Hungarian Notation (HN consists in prefixes in lowercase added to the names of the variables, and that indicate its type; the rest of the name indicates, as clearly as possible, the function that performs the variable), so we have to avoid this encoding as it makes it more difficult the readability of the code, may confuse the reader, and makes it more complicated to change the name or the type of a variable or class.

- Use uppercase and lowercase letters to have more legible names. In general, we must use lowercase letters in our names, except on the first letter of the names of the classes and interfaces, that must be in uppercase, as well as the first letter of any word not initial (naming convention CamelCase).

- Use uppercase in the first letter of standard acronyms. The names will generally have standard abbreviations, such as SQL by Standard Query Language. Names such as *sqlDatabase* for an attribute or *SqlDatabase* for a class are much easier to read than *sQLDatabase* and *SQLDatabase*.

- The names of the classes and objects must be nouns or phrases of nouns. For example, *Customer* or *BankAssistant*. The name of a class should not be a verb.

- Methods must have names of verb. For example, *save* or *retrieveResult*. The methods of access, modification and the predicates must have as name its value and use as a prefix get, set, and is. For example:

	*String name = client.getName( );*

- Choose a word for each abstract concept and maintain it. It is very confusing to use names such as *get* and *retrieve* as equivalent methods from different classes, to be difficult to remember which method corresponds to each class. The names of functions must be independent and consistent in order to choose the correct method without need of additional searches. A coherent lexicon is a great advantage for developers who have to use our code.

- Avoid using the same word for two different purposes. For example, if we have several classes with a method *add* that it does is to create a new value by adding two existing values, and we create a new class with a method that adds a value to a list, in this new method we could name it such as *add*, but in this case there is a difference in semantics, and we should use another name such as *insert*. We have to facilitate the understanding of our code.

- Use terminology applicable to the domain of solutions and/or of the problem. It is advisable to use computer terms, algorithms, patterns names, and other mathematical terms, i.e. choose technical names to define technical stuff. However when there is not a programming term for that is being done, we should use a domain name of the problem. Many developers make the mistake of creating generic terms for concepts when there are already perfectly useable terms in the domain. For example, if our users relate to their customers as consumers, we have to use the term *Customer* for the class, not *Client*.

- Some names have a meaning by themselves, but not the majority, so they must be included in a context, in classes, functions and namespaces with appropriate names. For example, we have the variables *firstname*, *lastname*, *street*, *number*, *city* and *country*, that combined, they obviously form an address, but if the variable *number* is used in isolation on a method, we would not be able to identify that is a part of an address. For this, the best would be to create the class *Address*, so we would know that the variables belong to a broader concept.

- Short names are usually more appropriate than the extensive, provided they are clear. For example, the names *AccountAddress* and *ClientAddress* are perfect for instances of the class *Address*, but do not serve as the class name. *Address* serves better as the class name.

Regarding to the naming conventions of identifiers in Java, several communities have established and proposed their owns. In the following link, you can see the naming conventions established by Sun Microsystems:

[http://www.oracle.com/technetwork/java/codeconventions-135099.html](http://www.oracle.com/technetwork/java/codeconventions-135099.html)

---

## Function's design
---

## Comments and documentation

### Introduction
 
There’s always been controversy around code documentation and comments: one side is defending a more verbose approach by writing a lot of comments inside the code and creating large documents where everything is explained in detail whereas the other is claiming that source code should remain as neater as possible and be auto-explanatory, i.e. include as less comments and docs as possible, since they are not needed if the code is clean and structured. Some go even further and state that comments are code smell. 

Well, as in many other aspects, maybe the best choice is to meet halfway so that’s why we will try here to give some tips which we think it may help encounter a reasonable compromise in between. After all they say virtue is in the middle course.

### Documenting your code

Let’s begin by stating the obvious: a public API needs to be well-described. Of course there are plenty of tools in the market to help you with this tedious but necessary task to document your application, but if you’re coding Java, then we think javadocs should be your way to go.

Javadoc generates API documentation automatically from source code with specially formatted documentation comments, more commonly known as doc comments. But furthermore javadoc is powerful because it is way better than a simple "//comment" :

It is recognized by the IDE and used to display a pop-up when you move your cursor on top of one of your - javadoc-ed - function.
It can be parsed by external tools (like xdoclet)

Here we show an example:

``` java
/**
 * Returns an Image object that can then be painted on the screen. 
 * The url argument must specify an absolute {@link URL}. The name
 * argument is a specifier that is relative to the url argument. 
 * <p>
 * This method always returns immediately, whether or not the 
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives 
 * that draw the image will incrementally paint on the screen. 
 *
 * @param  url  an absolute URL giving the base location of the image
 * @param  name the location of the image, relative to the url argument
 * @return      the image at the specified URL
 * @see         Image
 */
 public Image getImage(URL url, String name) {
        try {
            return getImage(new URL(url, name));
        } catch (MalformedURLException e) {
            return null;
        }
 }

``` 

Using Javadoc acknowledges that there are two distinct questions a reader can ask about code:
 - what is this supposed to do? (answered only by the javadoc and method header)
 - how does it try to do it? (answered only by the implementation)

If javadoc is written correctly, then one can understand exactly what services are offered by a method ("what is this supposed this do?"), without having to look at its implementation ("how does it try to do it?"). Reading an implementation usually takes a lot more effort than reading javadoc.

The implementation can be checked for correctness versus the specification. That is, some bugs can be found just by reading the code, as opposed to executing it.

It is not the idea of this guide to show in depth the javadoc tool, all details about it can be found in Sun’s Javadoc Guide  “How to Write Doc Comments”

### Comments

Javadoc comments and self-documenting code (and in-code comments) have two very different target audiences: 

Javadoc comments are typically for users of the API -also developers-, but they don't care about the internal structure of the system, just the classes, methods, inputs, and outputs of the system. The code is contained within a black box. These comments should be used to explain how to do certain tasks, what the expected results of operations are, when exceptions are thrown, and what input values mean. Given a Javadoc-generated set of documentation, I should be able to fully understand how to use your interfaces without ever looking at a line of your code.

On the other hand the code and comments that remain in the code file are for developers. You want to address their concerns here, make it easy to understand what the code does and why the code is the way it is. The use of appropriate variable names, methods, classes, and so on (self-documenting code) coupled with comments achieves this.

As mentioned before it is sometimes said that most comments are just code smell. I guess that what they are really referring to is that comments which do not bring anything interesting to our program should be avoided. Some wrong behaviours would be:

	* Redundancy
```java
	/**
	* @param sellRequest
	* @return
	* @throws ManagedComponentException
	*/
	public SellResponse beginSellItem(SellRequest sellRequest)
	throws ManagedComponentException
```
	* State the obvious
```java
		i++; // increment i
```
	* Commented-out code
	* Comments right after closing a brace
	* Misplace (as in source control)
	* In general any noise to the code

To minimize the probability to fall in one of those bullet cases above, as a simple rule of thumb, it is best to try to keep it simple and write as few comments as possible. At least, think twice before write, see if the code can be written in a way that can elude the comment. And as usual, nothing like a good example to illustrate easier and better what we are trying to explain:

Let’s take a look at a typical example that can be easily found in many references across the web and which uses a Newton formula to calculate displacement.

```java
float a, b, c; a=9.81; b=5; c= .5*a*(b^2);
```

I think everybody will agree that this is awful. No possible reader could take a look at this code and form a minimal idea about what the excerpt is pursuing, let alone if no other context is provided. One could be tempted to simply add some comments to fix it and write something like this:

```java
const float a = 9.81; //gravitational force 
float b = 5; //time in seconds 
float c = (1/2)*a*(b^2) //multiply the time and gravity together to get displacement.
```

Although this is clearly much better than the initial one, it looks like it’s still kind of lame. 

```java
/* compute displacement with Newton's equation x = vₒt + ½at² */ 
const float gravitationalForce = 9.81; 
float timeInSeconds = 5; 
float displacement = (1 / 2) * gravitationalForce * (timeInSeconds ^ 2)
```

Again better than before, this code is fine, but it could be rewritten in an equally informative way without any comment, for instance:

```java
const float accelerationDueToGravity = 9.81;
float timeInSeconds = 5; 
float displacement = NewtonianPhysics.CalculateDisplacement(accelerationDueToGravity);
```
I think the code is now cleaner and self-explanatory, but this will of course depend on the reader’s point of view if we compare it with the previous excerpt.

Anyway, many books and authors tend to preach that, and I quote Robert C. Martin here, “when you find yourself in a position where you need to write a comment, think it through and see whether there isn’t some way to turn the tables and express yourself in code”.

And why do they say that?, you may wonder. Well, there is a problem with comments, a big one I think: comments -like code- need to be maintained, and if not, they often get old too quickly, so it might -it will- become really annoying to go through them. Or even worse, it will not get done, so the comment will rot and get “old” and end up confusing -in the best case scenario, or even “lying” in a worse one- while the code keeps changing.

We like to align with this position, I mean, why do even bother writing a comment when you can explain it via code!? furthermore, why invest effort in maintain a comment instead of using that time in refactoring your code. 

We are not saying run like hell from comments, no. Sometimes they are even good and/or necessary. Just be careful when you use them, because often there is no point if one takes a small pause and think a bit slower. 

And as it often happens, everything’s connected so, as it’s been explained before in previous sections, giving proper names to our variables and methods is essential to reach the goal and keep the code legible and self-documented. And so it is giving good format and style, as it will be explained in the next section.

## Formatting and code styling
---
- Code style and format are very important. Application functionality is changing and code styling is an important aspect of it. A good code style and format will make it much more maintainable and easy to scale.

###Vertical Formatting
- If you perform correct vertical formatting, the code will be clearer. When we review a code, our eyes search for the first lines after a break line. Bits of code should be structured with correct vertical formatting. The following sections provide guidelines to correct vertical formatting.

####Vertical whitespace [blank lines]
- Between consecutive members of a class: fields, constructors, methods, nested classes, static initializers, instance initializers.  
- It is not recommended to perform two consecutive blank lines.    

Example without break line:
``` java
package examples.java
public class Bicycle {
    public int cadence;
    public int gear;
    public int speed;
    public Bicycle(int startCadence, int startSpeed, int startGear) {
        gear = startGear;
        cadence = startCadence;
        speed = startSpeed;
    }
    public void setCadence(int newValue) {
        cadence = newValue;
    }
    public void setGear(int newValue) {
        gear = newValue;
    }
    public void applyBrake(int decrement) {
        speed -= decrement;
    }
    public void speedUp(int increment) {
        speed += increment;
    }
        
}
```
Example with break line:
```java
package examples.java

public class Bicycle {
        
    public int cadence;
    public int gear;
    public int speed;
        
    public Bicycle(int startCadence, int startSpeed, int startGear) {
        gear = startGear;
        cadence = startCadence;
        speed = startSpeed;
    }
        
    public void setCadence(int newValue) {
        cadence = newValue;
    }
        
    public void setGear(int newValue) {
        gear = newValue;
    }
        
    public void applyBrake(int decrement) {
        speed -= decrement;
    }
        
    public void speedUp(int increment) {
        speed += increment;
    }
        
}
```

####Distance between variables
- ***Local variables*** should be declared as close to their usage as possible.  This is helpful to code’s readability. It is not practical to keep on scrolling up just to look for the variables. Control variables for loops should be declared within the loop statement.
```java
//eclared within the loop
for (ObjectMember member : mapObjectMember.values()) {
    nodeGroup.setId(member.getId());
    nodeGroup.setName(member.getMember());
    }
}
```
- ***Instance variables*** should be declared at the top of the class.  
Instance variables example:
```java
public class Employee{

   private String name;
   private double salary;
  
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public double getSalary() {
      return salary;
   }
   public void setSalary(double salary) {
      this.salary = salary;
   }
}
```
####Dependent Functions.
- If one function calls another, they should be vertically close, and the caller should be above the caller.   
Dependent function example:
```java
...
   public void a() {
     b();
   }
  
   public void b() {
     c();
   }
   
   public void c() {}
...
```
####Ordening
- The following are the three basic points for ordering bits of code:   
  - ***Direct dependence:*** when a function calls another. (example given above)
  - ***Affinity:*** Groups of functions perform similar operations.
  - ***Descending order:*** create a flow down the source code module from high level to low level.


###Horizontal Formatting
- ***Line Length***, A line with more than 120 characters is not recommended, because they are not handled well by many terminals and tools.

- ***Wrapping Lines***, When an expression can't fit in a single line, break it according to these general principles: after a comma, before an operator and prefer higher-level breaks.
```java
//example of break after comma
    methodA(param1, param2, param3, 
        param4, param5)  


//example of breaking an arithmetic higher-level expression
  var1 = var2 * (var3 
      + var4) - var5;
  
  var1 = var2 * (var3 + var4) 
      - var5; //This is better than previous one.
```
- Line wrapping for "if": Double indentation should be used. 
```java
//double indentation
if (booleanA || booleanB || 
        booleanC || booleanD || 
        booleanE) {
    System.out.println("***");
}
```
- If you do not use braces for a "if", you should do a break line with indentation.
```java
if (booleanA) System.out.println("***");  // NO!

if (booleanA) 
   System.out.println("***");  // YES!
```
- ***Declaration alignment***, It is recommended to declare a single variable per line, and it is not recommended to align the variable name.
Example:
```java
//not recommended
boolean var1;
int     var2;
float   var3;
int     var4, var5;

//this is better 
boolean var1;
int var2;
float var3;
int var4; 
int var5;
```

- ***Blank Space***: Blank space should be used in the following situations:
  - A keyword followed by a parenthesis should be separated by space.
  - A blanck space should appear after commas in arguments lists.
  - Binary operators.
```java
// Keyword example
if (condition) { //YES!!
  setA(x);
}
if(condition) { //NO!!
  setA(x);
}

//Arguments example
method(x, y, z); //YES!!
method(x,y,z); //NO!!

//binary operators example
a = (b + c) * (d + f); // YES!!
a = (b+c)*(d+f); // NO!!
```

- ***Indentation***, use four spaces for indentation to indicate nesting of control structures. Without indentation, programs would be virtually unreadable by humans.

Indentation example:
```java
class Example {
  int[] myArray = { 1, 2, 3, 4, 5, 6 };
  int theInt = 1;
  String someString = "Hello";
  double aDouble = 3.0;

  void foo(int a, int b, int c, int d, int e, int f) {
    switch (a) {
    case 0:
      Other.doFoo();
      break;
    default:
      Other.doBaz();
    }
  }

  void bar(List v) {
    for (int i = 0; i < 10; i++) {
      v.add(new Integer(i));
    }
  }
}

enum MyEnum {
  UNDEFINED(0) {
    void foo() {
    }
  }
}

@interface MyAnnotation {
  int count() default 1;
}
```

- Nowadays, IDEs such as Eclipse allow us to format code automatically. IDEs establish a lot of parameters like indentation, braces, white spaces, blank lines, control statements, line wrapping, comments etc. When someone start a new project, it is recommended to define these parameters, so all developers will have the same style and format.

## Encapsulation

It allows the data abstraction inside an object. For example, if an object needs to be modified its internal data structure, it won't change (if it's possible) the external use of this object. Moreover, an object doesn't have to publish all its fields, it doesn't expose them through methods or public fields.

There are some advices to use correctly the encapsulation. It's not as simple as make all the fields accesible through getter and setter methods. In fact, this kind of implementation is more common for beans which don't have any extra funcionality, such as DTOs (Data Transfer Object).

In the next example, we can see a bean with a simple constructor and all its fields exposed through getters.

```java
public class User {
	private String name;
	private String email;
	private Date dateOfBirth;

	public User(String name, String email, Date dateOfBirth){
		this.name = name;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
	}

	public String getName(){
		return name;
	}

	public String getName(){
		return name;
	}

	public String getName(){
		return name;
	}
}
```

Commonly, all variables for an object should be private, and there should be methods to get or set their values if it's necessary to be exposed. Getters and setters enables to envelope that data, validating it, transforming it, synchronizing it, etc. It prevents other external modifications which potentially make the object unusable.

The next example shows how the setters of the attributes can validate and transform the input, and the attributes are not accesibles by getters, only obtain the *area*.


```java
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rectangle {
	private double height;
	private double width;

	public void setHeight(double height){
		if (height <= 0) throw new IllegalArgumentException();
	    this.height = getRounded(height);
	}

	public void setWidth(double width){
		if (width <= 0) throw new IllegalArgumentException();
	    this.width = getRounded(width);
	}

	private double getRounded(double value){
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	public double getArea() {
		return height * width;
	}
}
```

### Law of Demeter

It's a guideline to develop software with loose coupling. It specifies that a module should not
know about the internal workings of the objects it uses. Like encapsulation determines, the internal structure and functioning shouldn't be exposed, it means that an object knows about the objects it uses and their interface, it shouldn't know about the internal objects (or methods) of these objects.

There is a clear scenario of what LoD allows: An object A can call a method of an object instance B, but object A should not access through object B to another object C, to call C methods, B's interface should be modified in order to directly serve to object A's the calls to C, propagating it to any relevant subcomponents. 

The Law of Demeter says that a method f of a class C should only call methods of:
* C
* Objects created by f
* Objects as arguments on f
* Objects as variables on C

Usually, this law it's applied strictly, because sometimes, we are using third party libraries which don't allow us to apply the rule completly. LoD should be applied, whenever possible, to follow the encapsulation definition.


In summary, the objects **expose behavoir** and **hide data**, the interface of an object should isolate the internal data of that object.


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

Java logging is not an exact science since is left to the programmer and its experience several decisions, for instance the log level to use, the format of the messages or the information to display, all this is very important since it is proven that Java logging affects to the efficiency of ours applications. Here we offer an outline of how to make good use of the java logs.

### First choose an appropriate tool for logging

We recommend you to use the abstraction layer SLF4J (Simple Logging Facade for Java), this facade give us a list of improvements:
* Loose coupling: using SLF4J we are not linking our classes to an implementation of logging in particular. Thus in a future if we change that implementation we don't need to modify our classes.
* The only dependency in application components should be on the SLF4J API, no class should reference any other logging interfaces.
* SLF4J's parameterized logging: SLF4J has a feature that help us to construct messages in a way much more attractive. Example: 

		logger.debug("There are " + numberOfBooks + " of " + kind); //Log4J
		logger.debug("There are {} of {}", numberOfBooks, kind);//SLF4J
	As we can see, the second form is shorter and easier to read, it is also more efficient because of the way it builds messages SLF4J

### Logging levels
For each message, we have to think carefully which level is the most appropriate. Logging affect the performance of our application because each log is a file IO, that's why is very important to use the right level. Log4J give us 6 levels of priority:

**FATAL:** for critical messages, usually the application will abort after save the message

**ERROR:** designates error events that might still allow the application to continue but it has to be investigated quickly.

**WARN:** used for messages of alert that we want to save but the application still will follow running

**INFO:** give us information about the progress and the status of the application very useful for the final user 

**DEBUG:** this level is used to get information of utility for the developer to debug the application. Commonly this level is only used in the development environment of DEV

**TRACE:** used to show an information more detailed than debug, it has the highest priority of all

### Is Log4j isDebugEnabled() necessary?
This method is only worth of use it if we are not using SLF4J and we can prove that the construct of the specified log is expensive. Example of use:

         if (log.isDebugEnabled()) {
   	 		log.debug("Something happened");
		 }
	
### Use rotation policies
Logs can take up a lot of space. Maybe you need to keep years of archival storage, but the space is limited and expensive. So, we have to set up a good rotation strategy and decide whether to destroy or back up your logs but always it has to allow the analysis of recents events.

For example to create daily rolling log files we have to configure log4j, in that case log4j provides the DailyRollingFileAppender class. Here is an example of a log4j’s properties configuration file 

    log4j.appender.Appender2=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.Appender2.File=app.log
    log4j.appender.Appender2.DatePattern='.'yyyy-MM-dd

### Be concise and descriptive
It's important to log in simple English, it should make sense and human readable. The usual is that the loggings statements include data and description in a single line.
Example: 

    log.debug(“Finalizacion”); //Only description, no data
    log.debug(status); //Only data, no description
    log.debug(“Finalizacion {}”, status); //Is better to put data and description together

### Be careful logging
We have to keep attention that we are logging and avoid typical mistakes.
* Avoid null pointer exceptions:
    
        log.debug(“Correct insertion for the user {} ”, user.getId()); 
    Are we sure that user is not nullpointer? 

* Avoid logging collections:

        log.debug(“Inserting users {} ”, users);
    A lot of errors can happen: object users not initialized, thread startvation, ouf of memories... It is better to log only the size of the collections or the id of each iteration.

* Never log sensitive information as password, credit car numbers or account number

### Log exceptions properly
The usual rule is to not log any exceptions, but there is an exception; if we throw exceptions for some remote service that is capable of serializing exceptions. There are two basic rules to throw an exception:
* If you re-throw an exception, don't log it. For example:

            try {
    			/ …..
    		} catch (IOException ex) {
    			log.error(“Error: {}”, ex);
    			throw new MyCustomerException(ex);
    		}
    This is wrong, we would be duplicating traces of the same error in the logs. Log, or wrap and throw back, never both,

* In order to log an exception the first argument is always the text message, write something about the nature of the problem, and the second argument is the exception itself.

        log.error("Error reading configuration file", e);

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
