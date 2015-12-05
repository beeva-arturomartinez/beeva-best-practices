# Code Optimization Best Practices
At this point we're going to talk about the best practices for optimize the code.

## Index

* [Introduction](#introduction)
* [Concepts](#concepts)
	* [O Notation](#o-notation)
	* [Unnecesary code](#unnecesary-code)
* [Simple Data Structures](#simple-data-structures)
	* [Route](#route)
	* [Searching](#searching)
	* [Ordering](#ordering)
	* [Constants propagation](#constants-propagation)
* [Recursivity](#recursivity)
* [Complex Data Structures](#complex-data-structures)
	* [Hash tables](#hash-tables)
	* [Trees](#trees)
	* [Graphs](#graphs)
* [Profiling](#profiling)
	* [Profilers types depending on the response](#profilers-types-depending-on-the-response)
	* [Granularity](#granularity)
	* [Statistic's profilers](#statistics-profilers)
* [Instrumentation](#instrumentation)
* [Loop Optimization](#loop-optimization)
* [Efficient Exceptions Management](#efficient-exceptions-management)
* [Tools](#tools)
	* [Loggers](#loggers)
	* [Death code detection](#death-code-detection)
* [References](#references)

## Introduction

## Concepts

### O Notation

### Unnecesary code

## Simple Data Structures

### Route

### Searching

### Ordering

### Constants propagation

## Recursivity

## Complex Data Structures

### Hash tables

### Trees

### Graphs

## Profiling

### Profilers types depending on the response

### Granularity

### Statistic's profilers

## Instrumentation

## Loop Optimization
* Try to avoid ending conditions of loops with unnecessary calculations, because its will be calculated in each loop iteration.
* By the same reason, is interesting initialize the common variables out of the loop.

## Efficient Exceptions Management
The exception management may be performance expensive, is better write proactive code which verifies some conditions that eventually may produces exceptions. There are some examples for this:
	* If you need divide by a variable, is more efficient test if the divider is 0 than management an exception.
	* Also is better test a correct format for a variable than make a cast and catch the error.

## Tools

### Loggers
All programming languages have systems to print information, but in the most of cases isn't efficient use it for print information, because these operations are synchronous and slow. Each programming language have its own libraries to process log messages and allow to save it at disk and/or screen and configure a determined format. Is very recommended use these libraries for improve the performance and manage the log levels unifying the log styles.

For improve the performance of your application is recommended that in production environments only show logs with level INFO, WARN and ERROR. For this goal its important that when you develop your application and you want to log something think about if is usefull information and if is better use debug or info level. Try to avoid print large objects or huge amount information if isn't really necesary, this type of information may be usefull when you are trying your code in local mode or development environments, but in production environments normally penalize the performance and complicate the understanding or reading of the logs.

### Death code detection



### References

* [Link](http://www.url.to) Description
* [oficialsite.org](http://www.oficialwebsite.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/example/) Cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
