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