# Scala Programming Best Practices

![Scala Logo](static/scala-logo.png "Scala")

## Index

* [Introduction](#introduction)
* [Formatting](#formatting)
* [Types and Generics](#types-and-generics)
* [Collections](#collections)
* [Concurrency](#concurrency)
* [Control structures](#control-structures)
* [Functional programming](#functional-programming)
* [Object oriented programming](#object-oriented-programming)
* [Error handling](#error-handling)
* [References](#references)

### Introduction

[URL Base](http://twitter.github.io/effectivescala/):http://twitter.github.io/effectivescala/

Some code examples:
````javascript
    var http = require('http');
    http.createServer(function (req, res) {
      res.writeHead(200, {'Content-Type': 'text/plain'});
      res.end('Hello World\n');
    }).listen(1337, '127.0.0.1');
    console.log('Server running at http://127.0.0.1:1337/');
````

> Blockquotes are very handy in email to emulate reply text.
> This line is part of the same quote.

A Remarkable idea

A nice table

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |

**[⬆ Índice](#index)**

### Formatting
Whitespace, Naming, Imports, Braces, Pattern matching, Comments

### Types and Generics
Return type annotations, Variance, Type aliases, Implicits

### Collections
Hierarchy, Use, Style, Performance, Java Collections

### Concurrency
Futures, Collections

### Control structures
Recursion, Returns, for loops and comprehensions, require and assert

### Functional programming
Case classes as algebraic data types, Options, Pattern matching, Partial functions, Destructuring bindings, Laziness, Call by name, flatMap

### Object oriented programming
Dependency injection, Traits, Visibility, Structural typing

### Error handling
Handling exceptions

### References

* [Link](http://www.url.to) Description
* [nodejs.org](http://www.nodejs.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/nodejs/) A node.js full cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
