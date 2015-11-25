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

Variance arises when generics are combined with subtyping. Variance defines how subtyping of the contained type relates to subtyping of the container type. Because Scala has declaration site variance annotations, authors of common libraries — especially collections — must be prolific annotators. Such annotations are important for the usability of shared code, but misapplication can be dangerous.

Invariants are an advanced but necessary aspect of Scala’s typesystem, and should be used widely (and correctly) as it aids the application of subtyping.

Immutable collections should be covariant. Methods that receive the contained type should “downgrade” the collection appropriately:

```
trait Collection[+T] {
  def add[U >: T](other: U): Collection[U]
}
```

Mutable collections should be invariant. Covariance is typically invalid with mutable collections. Consider

```
trait HashSet[+T] {
  def add[U >: T](item: U)
}
```
and the following type hierarchy:
```
trait Mammal
trait Dog extends Mammal
trait Cat extends Mammal
```

If I now have a hash set of dogs
```
val dogs: HashSet[Dog]
```

treat it as a set of Mammals and add a cat.

```
val mammals: HashSet[Mammal] = dogs
mammals.add(new Cat{})
```

This is no longer a HashSet of dogs

####Type aliases

Use type aliases when they provide convenient naming or clarify purpose, but do not alias types that are self-explanatory.

####Implicits

It’s definitely OK to use implicits in the following situations:

- Extending or adding a Scala-style collection
- Adapting or extending an object (“pimp my library” pattern)
- Use to enhance type safety by providing constraint evidence
- To provide type evidence (typeclassing)
- For Manifests
 
**[⬆ Index](#index)**

### Collections
Scala has a very generic, rich, powerful, and composable collections library; collections are high level and expose a large set of operations.
Always use the simplest collection that meets your needs.

####Use
Prefer using immutable collections. They are applicable in most circumstances, and make programs easier to reason about since they are referentially transparent and are thus also threadsafe by default.

Use the mutable namespace explicitly. Don’t import scala.collection.mutable._ and refer to Set, instead
```
import scala.collection.mutable
val set = mutable.Set()
```
makes it clear that the mutable variant is being used.

Use the default constructor for the collection type. Whenever you need an ordered sequence (and not necessarily linked list semantics), use the Seq() constructor, and so on:

```
val seq = Seq(1, 2, 3)
val set = Set(1, 2, 3)
val map = Map(1 -> "one", 2 -> "two", 3 -> "three")
```

####Style

Functional programming encourages pipelining transformations of an immutable collection to shape it to its desired result. This often leads to very succinct solutions, but can also be confusing to the reader — it is often difficult to discern the author’s intent, or keep track of all the intermediate results that are only implied.

Developer must think that code will be read by other people. Using {} and intermediate state values you will get readable code

####Performance

Before focusing on low level details, make sure you are using a collection appropriate for your use. Make sure your datastructure doesn’t have unexpected asymptotic complexity.

It is often appropriate to use lower level collections in situations that require better performance or space efficiency. Use arrays instead of lists for large sequences (the immutable Vector collections provides a referentially transparent interface to arrays); and use buffers instead of direct sequence construction when performance matters.

**[⬆ Index](#index)**

### Concurrency

Modern services are highly concurrent, _Threads_ provide a means of expressing concurrency but they are dificult to manage. Also you have to consider issues such as cancellations and timeouts and your code usually lost modularity and it becomes difficult to maintain

####Futures

Use Futures to manage concurrency. Scala has lightweight closure literal syntax, so Futures introduce little syntactic overhead, and they become second nature to most programmers.

Futures allow the programmer to express concurrent computation in a declarative style, are composable, and have principled handling of failure.

Prefer transforming futures over creating your own. Future transformations ensure that failures are propagated, that cancellations are signalled, and free the programmer from thinking about the implications of the Java memory model.

We use flatMap to sequence operations and prepend the result onto the list as we proceed. This is a common functional programming idiom translated to Futures. This is correct, requires less boilerplate, is less error prone, and also reads better.

Use the Future combinators. Future.select, Future.join, and Future.collect codify common patterns when operating over multiple futures that should be combined.


Do not throw your own exceptions in methods that return Futures. Futures represent both successful and failed computations. Therefore, it’s important that errors involved in that computation are properly encapsulated in the returned Future.

####Collections

In most practical situations they are a nonissue: Always start with the simplest, most boring, and most standard collection that serves the purpose. Don’t reach for a concurrent collection before you know that a synchronized one won’t do.

If an immutable collection will do, use it.

Mutable concurrent collections have complicated semantics, and make use of subtler aspects of the Java memory model, so make sure you understand the implications — especially with respect to publishing updates — before you use them.

**[⬆ Index](#index)**

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
