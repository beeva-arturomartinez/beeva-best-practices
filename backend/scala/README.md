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

This is not an introduction to Scala; we assume the reader is familiar with the language. Some resources for learning Scala are:

[ScalaByExample](http://www.scala-lang.org/docu/files/ScalaByExample.pdf)

[A Scalable Language](https://www.artima.com/pins1ed/index.html#TOC)

This is a living document that will change to reflect our current “best practices,” but its core ideas are unlikely to change: Always favor readability; write generic code but not at the expensive of clarity; take advantage of simple language features that afford great power but avoid the esoteric ones (especially in the type system). Above all, be always aware of the trade offs you make. A sophisticated language requires a complex implementation, and complexity begets complexity: of reasoning, of semantics, of interaction between features, and of the understanding of your collaborators. Thus complexity is the tax of sophistication — you must always ensure that its utility exceeds its cost.

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

**[⬆ Index](#index)**

### Formatting

Style cannot be inherently good or bad and almost everybody differs in personal preference. However the consistent application of the same formatting rules will almost always enhance readability. A reader already familiar with a particular style does not have to grasp yet another set of local conventions, or decipher yet another corner of the language grammar.

This is of particular importance to Scala, as its grammar has a high degree of overlap.

We adhere to the [Scala style] (http://docs.scala-lang.org/style/)  guide plus the following rules

#### Whitespace
Indent by two spaces. Try to avoid lines greater than 100 columns in length. Use one blank line between method, class, and object definitions

#### Naming
_Use short names for small scopes_
is, js and ks are all but expected in loops.
    
Use longer names for larger scopes: external APIs should have longer and explanatory names that confer meaning. Future.collect not Future.all
    
Use common abbreviations but eschew esoteric ones
Everyone knows ok, err or defn whereas sfri is not so common.
    
Don't rebind names for different uses
Use vals
    
Avoid using s to overload reserved names
typ instead of `type`
    
Use active names for operations with side effects
user.activate() not user.setActive()
    
Use descriptive names for methods that return values
src.isDefined not src.defined
    
Do not prefix getters with get
As per the previous rule, it is redundant: site.count not site.getCount

Do not repeat names that are already encapsulated in package or object name:
    
Prefer:
object User {
  def get(id: Int): Option[User]
}
to
object User {
  def getUser(id: Int): Option[User]
}
They are redundant in use: User.getUser provides no more information than User.get.

#### Imports
#### Braces
#### Pattern matching
#### Comments
#### ScalaDoc

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
