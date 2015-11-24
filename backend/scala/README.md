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
    
_Use longer names for larger scopes_

    external APIs should have longer and explanatory names that confer meaning. Future.collect not Future.all
    
_Use common abbreviations but eschew esoteric ones_

    Everyone knows ok, err or defn whereas sfri is not so common.
    
_Don't rebind names for different uses_

    Use vals
    
_Avoid using s to overload reserved names_

    typ instead of `type`
    
_Use active names for operations with side effects_

    user.activate() not user.setActive()
    
_Use descriptive names for methods that return values_

    src.isDefined not src.defined
    
_Do not prefix getters with get_

    As per the previous rule, it is redundant: site.count not site.getCount

_Do not repeat names that are already encapsulated in package or object name_

    Prefer:
    
```object User {
  def get(id: Int): Option[User]
}```

    to:
    
```object User {
  def getUser(id: Int): Option[User]
}```

_They are redundant in use_

    User.getUser provides no more information than User.get.

#### Imports

_Sort import lines alphabetically_

    This makes it easy to examine visually, and is simple to automate.
    
_Use braces when importing several names from a package_

    import com.twitter.concurrent.{Broker, Offer}
    
_Use wildcards when more than six names are imported_

    e.g.: import com.twitter.concurrent._ 
    Don't apply this blindly: some packages export too many names_

_When using collections, qualify names by importing scala.collection.immutable and/or scala.collection.mutable_

    Mutable and immutable collections have dual names.
    Qualifiying the names makes is obvious to the reader which variant is being used (e.g. "immutable.Map")
    
_Do not use relative imports from other packages_

    Avoid
    import com.twitter
    import concurrent
    in favor of the unambiguous
    import com.twitter.concurrent
    
_Put imports at the top of the file_

    The reader can refer to all imports in one place.

#### Braces

Braces are used to create compound expressions (they serve other uses in the “module language”), where the value of the compound expression is the last expression in the list. Avoid using braces for simple expressions;

write

```
def square(x: Int) = x*x
```
but not

```
def square(x: Int) = {
  x * x
}
```

even though it may be tempting to distinguish the method body syntactically. The first alternative has less clutter and is easier to read. Avoid syntactical ceremony unless it clarifies.

#### Pattern matching

Use pattern matching directly in function definitions whenever applicable; instead of

```
list map { item =>
  item match {
    case Some(x) => x
    case None => default
  }
}
```

collapse the match

```
list map {
  case Some(x) => x
  case None => default
}
```

it's clear that the list items are being mapped over — the extra indirection does not elucidate.

#### Comments

Use ScalaDoc to provide API documentation. Use the following style:

```
/**
 * ServiceBuilder builds services 
 * ...
 */
 ```
 
but not the standard ScalaDoc style:

```
/** ServiceBuilder builds services
 * ...
 */
```

Do not resort to ASCII art or other visual embellishments. Document APIs but do not add unnecessary comments. If you find yourself adding comments to explain the behavior of your code, ask first if it can be restructured so that it becomes obvious what it does. Prefer “obviously it works” to “it works, obviously” (with apologies to Hoare).

#### ScalaDoc

Use Scaladoc when something deserves explanation. In general we must try use user defined types and the choose expressive names. The code is "self-explanatory".


**[⬆ Index](#index)**

### Types and Generics

The primary objective of a type system is to detect programming errors. Use of the type system should reflect this goal, but we must remain mindful of the reader: judicious use of types can serve to enhance clarity, being unduly clever only obfuscates.

####Return type annotations

While Scala allows these to be omitted, such annotations provide good documentation: this is especially important for public methods. Where a method is not exposed and its return type obvious, omit them.

####Variance

####Type aliases

Use type aliases when they provide convenient naming or clarify purpose, but do not alias types that are self-explanatory.

####Implicits

It’s definitely OK to use implicits in the following situations:

- Extending or adding a Scala-style collection
- Adapting or extending an object (“pimp my library” pattern)
- Use to enhance type safety by providing constraint evidence
- To provide type evidence (typeclassing)
- For Manifests

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
