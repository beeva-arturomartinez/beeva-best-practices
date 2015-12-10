#Front-end BEEVA Best Practices   

##4.- JavaScript

### ES6

ECMAScript is a trademarked scripting language specification standardized by Ecma International in ECMA-262 and ISO/IEC 16262. Well-known implementations of the language, such as JavaScript, JScript and ActionScript are widely used for client-side scripting on the Web.

The Sixth Edition, known as ECMAScript 2015, adds significant new syntax for writing complex applications, including classes and modules, but defines them semantically in the same terms as ECMAScript 5 strict mode. Other new features include iterators and for/of loops, Python-style generators and generator expressions, arrow functions, binary data, collections (maps, sets and weak maps), and proxies (metaprogramming for virtual objects and wrappers). As the first “ECMAScript Harmony” specification, it is also known as “ES6 Harmony”.

### Releases

* [Promises](#promises)
* [Array](#array)
* [Class](#class)
* [Destructuring](#destructuring)
* [Generator](#generator)
* [Map](#map)
* [Reflect](#reflect)
* [Set](#set)
* [Iterator](#iterator)
* [Object literal](#object_literal)
* [String](#string)
* [Template strings](#template_strings)
* [Symbol](#symbol)
* [Arrow functions](#arroy_functions)
* [Block scope](#block_scope)
* [Rest operator](#rest_operator)
* [Spread operator](#spread_operator)
* [Default parameters](#default_parameters)
* [Modules](#modules)
* [Number](#number)
* [Object](#object)
* [Unicode](#unicode)

## <a name='promises'>Promises</a>

A promise represents an operation that hasn`t completed yet, but is expected in the future.

```javascript
function msgAfterTimeout (msg, who, timeout) {
    return new Promise((resolve, reject) => {
        setTimeout(() => resolve(`${msg} Hello ${who}!`), timeout)
    })
}

msgAfterTimeout("", "Foo", 100).then((msg) =>
    msgAfterTimeout(msg, "Bar", 200)
).then((msg) => {
    console.log(`done after 300ms:${msg}`)
})
```

## <a name='array'>Array</a>

###Array.from()

The Array.from() method creates a new Array instance from an array-like or iterable object.

In ES6, class syntax allows for the subclassing of both built-in and user defined classes; as a result, class-side static methods such as Array.from are "inherited" by subclasses of Array and create new instances of the subclass, not Array.

```javascript
// Array-like object (arguments) to Array
function f() {
  return Array.from(arguments);
}

f(1, 2, 3); 
// [1, 2, 3]
```

###Array.fill()

The fill() method fills all the elements of an array from a start index to an end index with a static value.

```javascript
[1, 2, 3].fill(4);               // [4, 4, 4]
[1, 2, 3].fill(4, 1);            // [1, 4, 4]
[1, 2, 3].fill(4, 1, 2);         // [1, 4, 3]
[1, 2, 3].fill(4, 1, 1);         // [1, 2, 3]
[1, 2, 3].fill(4, -3, -2);       // [4, 2, 3]
[1, 2, 3].fill(4, NaN, NaN);     // [1, 2, 3]
Array(3).fill(4);                // [4, 4, 4]
[].fill.call({ length: 3 }, 4);  // {0: 4, 1: 4, 2: 4, length: 3}
```

###Array.find()

The find() method returns a value in the array, if an element in the array satisfies the provided testing function. Otherwise undefined is returned.

```javascript
function isPrime(element, index, array) {
  var start = 2;
  while (start <= Math.sqrt(element)) {
    if (element % start++ < 1) {
      return false;
    }
  }
  return element > 1;
}

console.log([4, 6, 8, 12].find(isPrime)); // undefined, not found
console.log([4, 5, 8, 12].find(isPrime)); // 5
```

###Array.findIndex()

The findIndex() method returns an index in the array, if an element in the array satisfies the provided testing function. Otherwise -1 is returned.

```javascript
function isPrime(element, index, array) {
  var start = 2;
  while (start <= Math.sqrt(element)) {
    if (element % start++ < 1) {
      return false;
    }
  }
  return element > 1;
}

console.log([4, 6, 8, 12].findIndex(isPrime)); // -1, not found
console.log([4, 6, 7, 12].findIndex(isPrime)); // 2
```

###Array.entries()

The entries() method returns a new Array Iterator object that contains the key/value pairs for each index in the array.

```javascript
var arr = ['a', 'b', 'c'];
var eArr = arr.entries();

console.log(eArr.next().value); // [0, 'a']
console.log(eArr.next().value); // [1, 'b']
console.log(eArr.next().value); // [2, 'c']
```

###Array.keys()

The keys() method returns a new Array Iterator that contains the keys for each index in the array.

```javascript
var arr = ["a", "b", "c"];
var iterator = arr.keys();

console.log(iterator.next()); // { value: 0, done: false }
console.log(iterator.next()); // { value: 1, done: false }
console.log(iterator.next()); // { value: 2, done: false }
console.log(iterator.next()); // { value: undefined, done: true }
```

###Array.values()

The values() method returns a new Array Iterator object that contains the values for each index in the array.

```javascript
var arr = ['w', 'y', 'k', 'o', 'p'];
var eArr = arr.values();
console.log(eArr.next().value); // w
console.log(eArr.next().value); // y
console.log(eArr.next().value); // k
console.log(eArr.next().value); // o
console.log(eArr.next().value); // p
```

## <a name='class'>Class</a>

###Array.from()

JavaScript classes are introduced in ECMAScript 6 and are syntactical sugar over JavaScript's existing prototype-based inheritance. The class syntax is not introducing a new object-oriented inheritance model to JavaScript. JavaScript classes provide a much simpler and clearer syntax to create objects and deal with inheritance.

####Defining classes

Classes are in fact functions, and just as you can define function expressions and function declarations, the class syntax has two components: class expressions and class declarations.

####Class declarations

One way to define a class is using a class declaration. To declare a class, you use the class keyword with the name of the class ("Polygon" here).

```javascript
class Polygon {
  constructor(height, width) {
    this.height = height;
    this.width = width;
  }
}
```

Hoisting

An important difference between function declarations and class declarations is that function declarations are hoisted and class declarations are not. You first need to declare your class and then access it, otherwise code like the following will throw a ReferenceError:

```javascript
var p = new Polygon(); // ReferenceError

class Polygon {}
```

####Static methods

The static keyword defines a static method for a class. Static methods are called without instantiating their class and are also not callable when the class is instantiated. Static methods are often used to create utility functions for an application.

```javascript
class Point {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }

    static distance(a, b) {
        const dx = a.x - b.x;
        const dy = a.y - b.y;

        return Math.sqrt(dx*dx + dy*dy);
    }
}

const p1 = new Point(5, 5);
const p2 = new Point(10, 10);

console.log(Point.distance(p1, p2));
```

####Sub classing with extends

The extends keyword is used in class declarations or class expressions to create a class with a child of another class.

```javascript
class Animal { 
  constructor(name) {
    this.name = name;
  }
  
  speak() {
    console.log(this.name + ' makes a noise.');
  }
}

class Dog extends Animal {
  speak() {
    console.log(this.name + ' barks.');
  }
}
```

####Super

The super keyword is used to call functions on an object's parent.

The super.prop and super(expr) expressions are valid in any method definition in both classes and object literals.

```javascript
class Polygon {
  constructor(height, width) {
    this.name = 'Polygon';
    this.height = height;
    this.width = width;
  }
  sayName() {
    console.log('Hi, I am a ', this.name + '.');
  }
}

class Square extends Polygon {
  constructor(length) {
    this.height; // ReferenceError, super needs to be called first!
    
    // Here, it calls the parent class' constructor with lengths
    // provided for the Polygon's width and height
    super(length, length);
    
    // Note: In derived classes, super() must be called before you
    // can use 'this'. Leaving this out will cause a reference error.
    this.name = 'Square';
  }

  get area() {
    return this.height * this.width;
  }

  set area(value) {
    this.area = value;
  } 
}
```

#####Super.prop can not overwrite non-writable properties

When defining non-writable properties with e.g. Object.defineProperty, super can not overwrite the value of the property.

```javascript
class X {
  constructor() {
    Object.defineProperty(this, "prop", {
      configurable: true,
      writable: false, 
      value: 1
    });
  } 
  f() { 
    super.prop = 2;
  }
}

var x = new X();
x.f();
console.log(x.prop); // 1
```

## <a name='destructuring'>Destructuring</a>

Destructuring Assignment

####Array Matching

Intuitive and flexible destructuring of Arrays into individual variables during assignment.

```javascript
var list = [ 1, 2, 3 ]
var [ a, , b ] = list
console.log(a); // 1
console.log(b); // 3
[ b, a ] = [ a, b ]
console.log(a); // 3
console.log(b); // 1
```



Intuitive and flexible destructuring of Objects into individual variables during assignment.


```javascript
console.log(getNode); // { "a" : 1, "b" : 2 }
var { a, b } = getNode();
console.log(a); // 1
console.log(b); // 2
```

####Object Matching, Shorthand Notation

Intuitive and flexible destructuring of Arrays and Objects into individual parameters during function calls.

```javascript
function f ([ name, val ]) {
    console.log(name, val)
}
function g ({ name: n, val: v }) {
    console.log(n, v)
}
function h ({ name, val }) {
    console.log(name, val)
}
f([ "bar", 42 ]) // bar,42
g({ name: "foo", val:  7 }) //foo,7
h({ name: "bar", val: 42 }) //bar,42
```

####Fail-Soft Destructuring

Fail-soft destructuring, optionally with defaults.

```javascript
var list = [ 7, 42 ]
var [ a = 1, b = 2, c = 3, d ] = list
a === 7
b === 42
c === 3
d === undefined
```

## <a name='generator'>Generator</a>

####Generator Function, Iterator Protocol

Support for generators, a special case of Iterators containing a generator function, where the control flow can be paused and resumed, in order to produce sequence of values (either finite or infinite).

```javascript
let fibonacci = {
    *[Symbol.iterator]() {
        let pre = 0, cur = 1
        for (;;) {
            [ pre, cur ] = [ cur, pre + cur ]
            yield cur
        }
    }
}

for (let n of fibonacci) {
    if (n > 1000)
        break
    console.log(n)
}
```

####Generator Function, Direct Use

Support for generator functions, a special variant of functions where the control flow can be paused and resumed, in order to produce sequence of values (either finite or infinite).

```javascript
function* range (start, end, step) {
    while (start < end) {
        yield start
        start += step
    }
}

for (let i of range(0, 10, 2)) {
    console.log(i) // 0, 2, 4, 6, 8
}
```

####Generator Matching

Support for generator functions, i.e., functions where the control flow can be paused and resumed, in order to produce and spread sequence of values (either finite or infinite).

```javascript
let fibonacci = function* (numbers) {
    let pre = 0, cur = 1
    while (numbers-- > 0) {
        [ pre, cur ] = [ cur, pre + cur ]
        yield cur
    }
}

for (let n of fibonacci(1000))
    console.log(n)

let numbers = [ ...fibonacci(1000) ]

let [ n1, n2, n3, ...others ] = fibonacci(1000)
```