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
my-application/
	test/
		acceptance-test/
			features/
				step_definitions/
					stepdefinition-file1 # methods, helpers and 
					stepdefinition-file2 # variables for describing 
					stepdefinition-fileN # step definitions
				support/
					hooks.js # hooks functions for clean environment
					world.js # file with all properties and funcions to be used in step definitions
				feature-file1.feature # feature files with scenarios 
				feature-file2.feature # and steps for all user histories
				feature-fileN.feature # defined in you application
			npm-debug.log # npm  
			my-application.log # application acceptance-test logging file
		mocks/
			mocks-file1.js # methods and funcions 
			mocks-file2.js # for mocking during unit 
			mocks-fileN.js # tests in your application
		unit-test/
			unit-test-file1.js # unit-test files for testing 
			unit-test-file2.js # functions and methods of 
			unit-test-fileN.js # your application
```

#### Dependencies

#### World function

World is a constructor function with utility properties, destined to be used in step definitions. World function file should have this desired structure: 

1. Require section for imports.

2. World function declaration.

3. Functions for setting, getting and cleaning data of every scenario.

4. Validation functions for checking JSON objects in responses.

5. Server properties and constants for making requests.

6. Functions for start and stop a mock server for acceptance-tests.

7. Requests functions to call server endpoints you wish to call.

8. Client-side response methods for check if you receive the expected responses.

9. Test description function where you can prepare the request to server endpoints.

This is one valid example for this structure: 

```javascript
var	request = require('request'),//1
	zombie = require('zombie'),
	expect = require('chai').expect,
	async = require('async'),
	server = require('server'),
	extend = require('extend');

var	World = function World(callback) {//2
	var sharedData = {};

	this.setData = function (field, data) {//3
		sharedData[field] = data;
	};

	this.getData = function (field) {//3
		return sharedData[field];
	};

	this.clearData = function (field) {//3
		if (field) {
			delete sharedData[field];
		} else {
			shardData = {};
		}
	};

	this.validations = {
		validationOne: function (data) {//4
			expect(data).to.be.ok;
		}
	};

	var SERVER_URL = this.SERVER_URL = "http://localhost:3000/my-application";//5
	var VERSION = this.VERSION = "v1";//5

	var applicationUrls = this.urls = {//5
		endpoint: SERVER_URL + VERSION + '/{applicationId}'
	};

	var config = this.config = {//5
		//API properties		
		name: 'my-application',
		port: 3000,
		version: "0.0.1",
		apiversion: "v1",
		//Store properties
		store: {
			type: "mongodb",
			url: "mongodb://localhost:27017/my-application_test",
			collection: "notifications",
			options: {}
		},
		//Logging properties
		log: {
			fileName: './my-application.log',
			console: false
		}
	};

	this.startServer = function () {//6
		return server.start(config);
	};

	this.stopServer = function () {//6
		server.stop();
	};

	logger.init(config.log);

	this.requestFunction = function (param1, param2, ..., paramN, callback) {//7
		request.post({
			url: url,
			json: json,
			headers: {
			}
		}, callback);
	};

	callback();
};

World.responseMustBe = function (expectedStatus, callback) {//8
	var statusCode = this.getData('response').statusCode;
	expect(statusCode).to.be.equal(Number(expectedStatus));
	callback();
};

World.followingRequest = function (items, callback) {//9
	async.eachSeries(items, function (item, callback) {
		this.requestFunction(item.param1, item.param2, ..., item.paramN, function (error, response, body) {
			expect(error).to.be.null;
			expect(response.statusCode).to.equal(201);
			callback();
		}.bind(this));
	}.bind(this), function (error) {
		expect(error).to.not.exist;
		callback();
	}.bind(this));
};

exports.World = World;
```

#### Features

The acceptance-test features with user histories must be packed and stored in files with .feature extension. This Features are written using Gherkin language:

``` gherkin
# features/feature-file1.feature

Feature: Example feature
	As a user of cucumber.js
	I want to have documentation on cucumber
	So that I can concentrate on building awesome applications

	Scenario: Reading documentation
		Given I am on the Cucumber.js GitHub repository
		When I go to the README file
		Then I should see "Usage" as the page title
```


It's a best practice to store these files under /features in acceptance-test subfolder.

It isn't the purpose of this article how to describe correct features in Gherkin, but there are a set of recommendations:

1. Use Background and Scenario Outline if it's posible.
2. Don't write large feature files. You can pack these features in more files. For example, if you have 24 scenarios for testing two different application param values, yo can choose:
	⋅⋅1 createnotification.feature (24 Scenarios). ** NOT GOOD **
	⋅⋅2 createnotification_app1.feature (the first 12 Scenarios) and createnotification_app2.feature (the remaining 12 Scenarios). ** BETTER **
	⋅⋅3 createnotification_app1_ok.feature (the first 2 Scenarios for app1), createnotification_app1_errors.feature (the remaining 10 Scenarios for app1), createnotification_app2_ok.feature (the first 2 Scenarios for app2) and createnotification_app2_errors.feature (the remaining 10 Scenarios for app2). ** BEST **

You can check the official cucumber github repository for a beggining guide[Feature-Introduction](https://github.com/cucumber/cucumber/wiki/Feature-Introduction)

You can also check more examples how to describe Features using Gherkin in this [link](http://docs.behat.org/en/v2.5/guides/1.gherkin.html)

#### Support Files

Support files let you setup the environment in which steps will be run, and define step definitions.

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
