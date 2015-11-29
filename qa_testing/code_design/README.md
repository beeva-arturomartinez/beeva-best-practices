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
* [Agile metodologies implications](#agile-metodologies-implications)
    * [TDD](#tdd)
    * [The importance of testing](#the-importance-of-testing)
    * [How to make a correct application design](how-to-make-a-correct-application-design)
    * [BDD](#bdd)
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


## Code design patterns
---

## Agile metodologies implications
Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum
### TDD
Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum

## [References](references)

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
