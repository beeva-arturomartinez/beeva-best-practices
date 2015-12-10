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
