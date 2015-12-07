# Node.js Style Guide & Best Pratices
At this point we're going to talk about nodeJS, we're useing nodeJS to develop lightweight and efficient network apps using an event-driven, non-blocking I/O on the top of Chrome's V8 JavaScript engine.

![alt text](static/nodejs.png "nodeJS")

## Index

## [Frameworks](#nodejs-frameworks)
* [Introduction, best practices, antipatterns](#nodejs-frameworks-best-practices)
* [ExpressJS](#nodejs-frameworks-express)
* [Hapi](#nodejs-frameworks-hapi)
* [Restify](#nodejs-frameworks-restify)

## [DevOps](#nodejs-devops)
* [Scaffolding](#nodejs-devops-scaffolding)
* [Security](#nodejs-devops-security)
* [Log](#nodejs-devops-log)
* [Clustering](#nodejs-devops-clustering)
* [Cloud](#nodejs-devops-cloud)

## [Testing](#nodejs-testing)
* [TDD with Mocha](#nodejs-testing-mocha)
* [BDD with Cucumber](#nodejs-testing-cucumber)

### The Fellowship of the Ring 

Some code examples: 
````javascript
    var http = require('http');
    http.createServer(function (req, res) {
      res.writeHead(200, {'Content-Type': 'text/plain'});
      res.end('Hello World\n');
    }).listen(1337, '127.0.0.1');
    console.log('Server running at http://127.0.0.1:1337/');
````

### The Two Towers

> Blockquotes are very handy in email to emulate reply text.
> This line is part of the same quote.

A Remarkable idea


### BDD with Cucumber

#### Structure for BDD

```
+-- my-application/
|	+--	test/
|		+--		acceptance-test/
|		+--			features/
|		+--				step_definitions/
|		+--					stepdefinition-file1 # methods, helpers and 
|		+--					stepdefinition-file2 # variables for describing 
|		+--					stepdefinition-fileN # step definitions
|		+--				support/
|		+--					hooks.js # hooks functions for clean environment
|		+--					world.js # file with all properties and funcions to be used in step definitions
|		+--				feature-file1.feature # feature files with scenarios 
|		+--				feature-file2.feature # and steps for all user histories
|		+--				feature-fileN.feature # defined in you application
|		+--			npm-debug.log # npm  
|		+--			my-application.log # application acceptance-test logging file
|		+--		mocks/
|		+--			mocks-file1.js # methods and funcions 
|		+--			mocks-file2.js # for mocking during unit 
|		+--			mocks-fileN.js # tests in your application
|		+--		unit-test/
|		+--			unit-test-file1.js # unit-test files for testing 
|		+--			unit-test-file2.js # functions and methods of 
|		+--			unit-test-fileN.js # your application
```

#### Dependencies

#### World function

World is a constructor function with utility properties, destined to be used in step definitions. In this file, you to declare every 

#### Features

#### Support Files

#### Step Definitions

#### Hooks


### The Return of the King

A nice table

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | $1600 |
| col 2 is      | centered      |   $12 |
| zebra stripes | are neat      |    $1 |


### References

* [nodejs.org](http://www.nodejs.org) API & Docs
* [Overapi Cheatsheet](http://overapi.com/nodejs/) A node.js full cheatsheet

___

[BEEVA](http://www.beeva.com) | 2015
