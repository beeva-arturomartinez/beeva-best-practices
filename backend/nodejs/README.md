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
* [Security](#security)
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


### Restify

Restify is a light framework similar to Express and very easy for building REST APIs. This is the easy way to create a REST API application:

``` javascript
var restify = require('restify');
var server = restify.createServer();

server.get('/hello/:name', function(req, res, next) {
	res.send('hello ' + req.params.name);
});

server.listen(3000, function() {
	console.log('Listening on port 3000');
});
```

#### When use Restify instead of Express

1. Exists to let you build "strict" API services that are maintanable and observable. 

2. Comes with automatic DTrace support for all your handlers, if you're running on a platform that supports DTrace.

3. Is lighter than Express

#### When do not use Restify instead of Express 

1. Express use case is targeted at browser applications and contains a lot of functionality, such as templating and rendering, to support that. 

2. Restify does not support that. Express is more powerful on this.

3. More indicated when you have a large number of queries.

#### Conclusion Restify vs Express

Restify: If I need a framework that gave me absolute control over interactions with HTTP and full observability into the latency and characteristics of my applications. 

Express: If you don't need absolute control over these interactions, or don't care about those aspect(s), and I need to manage a large number of queries.

You can see a perfomance comparison between Hapi, Express and Restify in the following [link](https://raygun.io/blog/2015/03/node-performance-hapi-express-js-restify/)

#### Desired structure for a Restify application

This is one of desired structure for restify server application:

```
my-application/
	config.json # logging, repository and server properties file
	package.json # npm metadata and dependency info for application
	server.js # starting and stopping server functions file
	static-server.js # functions for listen connections to server
	bin/
		www # server starting function called from npm start
	node_modules/ # my-application dependencies
		restify/ # npm imported dependency for restify server
```
##### config.json

```javascript
{
	"name": "my-application", //server application context
	"urlResponse": "http://localhost", //server hostname
	"port": 3000, //server port
	"version": "0.0.1", //application module version
	"apiversion": "v1", //current api version
	"store": {
		//respository properties
	},
	"log": {
		//logging properties
	}
}
```

##### package.json

```javascript
{
  "name": "my-application", //application name
  "version": "0.0.1", //application module version
  "description": "my-application description.",
  "authors": [
	//application development team
  ],
  "keywords": [
	"npm"
  ],
  "license": "ISC",
  "dependencies": {
	"restify": "latest"
	//other application dependencies
  },
  "devDependencies": {
 	//development addtional dependencies
  },
  "engines": {
	"node": ">= latest" //node version
  },
  "scripts": {
	"test": "./node_modules/.bin/mocha --reporter spec --ui tdd ", //
	"start": "node bin/www" //
  },
  "repository": {
    //git repository connection properties
  }
}
```

##### server.js

1. Import 'restify', 'q' dependencies and 'static-server.js', '/lib/log/logger.js' user files.
2. Start function.
3. Restify's createServer function invocation.
4. Launch server database repository.
5. Set restify server functions like CORS filters, Oauth settings, parsers, etc...
6. Launch server listener for catching requests.
7. Stop function.

```javascript
var 	restify = require('restify'), //1
	Q = require('q'),
	static_server = require('./static-server'),
	extend = require('extend'),
	logger = require('./lib/log/logger');

module.exports = (function() {
	var listener = null, store = null;

	process.on("error", function() {
		logger.error(arguments);
	});

	return {
		start: function(config) {//2
			var deferred = Q.defer();
			logger.init(config.log);
			var server = restify.createServer({//3
				name: config.name,
				version: require('./package.json').version
			});
			server.on('uncaughtException', function (req, res, route, err) {
				logger.error(err.message, {
					event: 'uncaughtException'
				});
				res.send(500, {
					handler: err
				});
			});
			store = require('./lib/store')(config);
			store.init().then(function (storage) {//4
				config.storage = storage;
				logger.info("Storage initialized");
				server.use(restify.CORS());//5
				server.use(restify.acceptParser(server.acceptable));
				server.use(restify.queryParser());
				server.use(restify.fullResponse());
				server.use(restify.authorizationParser());
				server.use(function (req, res, next) {
					req.rawBody = '';
					req.setEncoding('utf8');
					req.on('data', function (chunk) {
						req.rawBody += chunk;
						req.body = JSON.parse(req.rawBody);
					});
					req.on('end', function() {
						next();
					});
				});
				server.use(function (req, res, next) {
					logger.info(req.method + ' - ' + req.url, req);
					next();
				});
				require('./lib/api')(server, config);
				listener = server.listen(config.port || 3000, function() {//6
					var static_config = extend(true, {}, config);
					static_config.port = (static_config.port + 5) || 3005;
					static_server.start(static_config).then(function (data) {
						logger.info("Server " + server.name + " started, listening on " + config.port);
						deferred.resolve({
							name: server.name,
							url: server.url
						});
					});
				});
			}).fail(function (error) {
				logger.error('Failure to start storage');
				deferred.reject(error);
			});
			return deferred.promise;
		},
		stop: function() {//7
			if (listener) {
				logger.info("Stopping service", {
					file: __filename
				});
				listener.close();
				static_server.stop();
				store.close();
				listener = null;
				store = null;
			}
		}
	};
})();
```

##### static-server.js

This file it's recommended for creating listener to server.

```javascript
var 	restify = require('restify'),
	Q = require('q');

module.exports = (function () {
	var listener = null;

	return {
		start: function (config) {
			var deferred = Q.defer();
			config = config || {};
			config.port = config.port || 3005;
			var server = restify.createServer();
			listener = server.listen(config.port, function () {
				deferred.resolve(config);
			});
			return deferred.promise;
		},
		stop: function () {
			if (listener) {
				listener.close();
			}
		}
	};
})();
```

##### bin/www

This file it's recommended for starting application server.

<<<<<<< HEAD
### Security

#### Conventions

> - Don't use the odd versions of Node.js (5.x.x) in Production enviroments, only use pair because are longer term supported (for example the 4.x.x versions).
> - Don't use deprecated versions of Express (for example 2.x and 3.x are no longer maintained). Security and performance issues in these versions won’t be fixed.

#### Some tips

> - Strict mode, please. With this flag you can opt in to use a restricted variant of JavaScript. It eliminates some silent errors and will throw them all the time.
> - Static code analysis. Use either JSLint, JSHint or ESLint. Static code analysis can catch a lot of potential problems with your code early on.
> - No eval, or friends. Eval is not the only one you should avoid, in the background each one of the following expressions use eval: setInterval(String, 2), setTimeout(String, 2) and new Function(String). But why should you avoid eval? It can open up your code for injections attacks and is slow (as it will run the interpreter/compiler).

#### Helmet

Helmet can help protect your app from some well-known web vulnerabilities by setting HTTP headers appropriately. *It's not a silver bullet*, but it can help!

![Helmet](static/helmet.png "Helmet")

##### Quick start

First, install helmet dependency into your application:

```javascript
npm install --save helmet
```

Next, you can use helmet in your application (for example in Express):

```javascript
var express = require('express');
var helmet = require('helmet');
 
var app = express();
 
app.use(helmet());
 
// ... 
```

##### How it works

Helmet is a collection of 9 smaller middleware functions that set security-related HTTP headers:

> - **contentSecurityPolicy** for setting Content Security Policy to help prevent cross-site scripting attacks and other cross-site injections.
> - **hidePoweredBy** to remove the X-Powered-By header
> - **hpkp** for HTTP Public Key Pinning to prevent man-in-the-middle attacks with forged certificates.
> - **hsts** for HTTP Strict Transport Security that enforces secure (HTTP over SSL/TLS) connections to the server.
> - **ieNoOpen** sets X-Download-Options for IE8+
> - **noCache** to disable client-side caching
> - **noSniff** to keep clients from sniffing the MIME type
> - **frameguard** to prevent clickjacking
> - **xssFilter** adds some small XSS protections in most recent web browsers.

Running app.use(helmet()) will include 6 of the 9, leaving out contentSecurityPolicy, hpkp, and noCache. 
You can also use each module individually: 

```javascript
app.use(helmet.noCache());
app.use(helmet.frameguard());
```

##### Usage guide

For each of the middlewares, we'll talk about three things:

> - What's the attack we're trying to prevent?
> - How do we use Helmet to help mitigate those issues?
> - What are the non-obvious limitations of this middleware?


You can get more information about this middleware functions in detail from this [link](https://www.npmjs.com/package/helmet)

=======
```javascript
var 	server = require('../server'),
	config = require('../config.json'),
	logger = require('../lib/log/logger');

server.start(config).then(
	function (server) {
		logger.info('%s listening at %s', server.name, server.url);
	}).fail(function (err) {
		console.error(err);
		process.exit(1);
	}
);
```
>>>>>>> restify structure and files

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

##### Instalation

Cucumber.js is available as an npm module. Install globally with:

``` shell
$ npm install -g cucumber
```

Install as a development dependency of your application with:

``` shell
$ npm install --save-dev cucumber
```

##### Develop and run test

For checking response code and fields, maybe you also need:

* chai
* json-schema

#### Features

The acceptance-test features with user histories must be packed and stored in files with .feature extension. Must be stored in features folder. This Features are written using Gherkin language:

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
	* createnotification.feature (24 Scenarios). **NOT GOOD**
	* createnotification_app1.feature (the first 12 Scenarios) and createnotification_app2.feature (the remaining 12 Scenarios). **BETTER**
	* createnotification_app1_ok.feature (the first 2 Scenarios for app1), createnotification_app1_errors.feature (the remaining 10 Scenarios for app1), createnotification_app2_ok.feature (the first 2 Scenarios for app2) and createnotification_app2_errors.feature (the remaining 10 Scenarios for app2). **BEST**

You can check the official cucumber github repository for a beggining guide [Feature-Introduction](https://github.com/cucumber/cucumber/wiki/Feature-Introduction)

You can also check more examples how to describe Features using Gherkin in this [link](http://docs.behat.org/en/v2.5/guides/1.gherkin.html)


#### Step Definitions

Step definitions are defined in javascript files under my.application/features/step_definitions folder. This step definitions are 

Best practices:
1. Don't write redundant or near Step definitions. Example:
``` gherkin
	Given(/^the following data for creating a notification:$/, function (table, callback) {
		//step code
	});

	Given(/^the following data for creating a notification:$/, function (table, callback) {
		//step code
	});
``` 
or

``` gherkin
	Given(/^the following data for creating a notification:$/, function (table, callback) {
		//step code
	});

	Given(/^the following data for create a new notification:$/, function (table, callback) {
		//step code
	});
```

You can use the same step definition for all of them. 

2. Don't write ambiguos or near Step definitions:
``` gherkin
	Given(/^the following signature "([^"]*)"$/, function (signature, callback) {
		//step code
	});

	Given(/^the following "([^"]*)" signature$/, function (signature, callback) {
		//step code
	});
``` 
Those are the same definition for an unique step.

3. Group step definitions by functionality, and use only one for common step definitions. Example:

```
my-application/
	test/
		acceptance-test/
			features/
				step_definitions/
					createApp_stepdefinitions.js
					getApp_stepdefinition.js
					common_stepdefinitions.js
				createApp_ok.feature
				createApp_error.feature 
				getApp_ok.feature
				getApp_error.feature 

```

4. Gruop Given, When and Then step definitions in the same file section. Example:

``` gherkin
	Given(/^step 1 definition$/, function (callback) {
		//step code
	});

	Given(/^step 2 definition$/, function (callback) {
		//step code
	});

	When(/^step 3 definition$/, function (callback) {
		//step code
	});

	When(/^step 4 definition$/, function (callback) {
		//step code
	});
	
	Then(/^step 5 definition$/, function (callback) {
		//step code
	});

	Then(/^step 6 definition$/, function (callback) {
		//step code
	});

``` 

Here is a simple example:

``` javascript
var expect = require('chai').expect;

module.exports = function () {
	this.World = World = require("../support/world.js").World;
	var 	Given = this.Given,
		When = this.When,
		Then = this.Then;

	Given(/^the following data for creating a notification:$/, function (table, callback) {
		this.setData("notificationData", table.hashes()[0]);
		callback();
	});

	When(/^I try to create a notification$/, function (callback) {
		World.tryToCreateNotification.call(this, callback);
	});

	Then(/^the response must be "([^"]*)"$/, World.responseMustBe);

	Then(/^I receive all required info for creating notification$/, function (callback) {
		World.checkCreateNotificationResponseData.call(this, callback);
	});

	Then(/^the notification was created and stored$/, function (callback) {
		World.checkNotificationExists.call(this, callback);
	});
};
```
You can also check more examples how to describe Step definitions in this [link]
(https://github.com/cucumber/cucumber/wiki/Step-Definitions)

#### Support Files

Support files let you setup the environment in which steps will be run, and define step definitions. The most important support file is the World function, but you may need more functions for testing. 

The secondary files you need to develop and store are the hooks functions. These two with other acceptance-test functions files must be stored in support subfolder. 

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

#### Hooks

Hooks are functions that can be used to prepare and clean the environment before and after each scenario is executed. Hooks can use callbacks, return promises, or be synchronous. The first argument to hooks is always the current scenario.

There are four different Hook function types:
1. Scenario hooks: will be run before/after the first/last step of each scenario. They will run in the same order of which they are registered.

2. Step hooks: will be run before/after every step of each scenario. Dos not work with scenarios which have backgrounds.

3. Tagged hooks: will be run before/after certain scenarios. You have to use tags for select subset of scenarios to run with this kind of hooks.

4. Global hooks: will be run once before any scenario is run.

Best practices for using Hooks, are:

* Pack all of them in a single file. 
* Store this file with World function file. 
* Use a little set of hooks.

The following example are hooks for clean data repository (in mongoDB) before every scenario, and start/stop server in every Scenario:

```javascript
var expect = require('chai').expect,
	MongoClient = require('mongodb').MongoClient;

var hooks = function () {
	var Before = this.Before,
		After = this.After,
		Around = this.Around;

	Before(function (done) {
		var that = this;
		this.startServer().then(function () {
			MongoClient.connect(that.config.store.url, function (error, db) {
				db.collection(that.config.store.collection).remove(function (error) {
					db.close();
					done();
				});
			});
		}).fail(function (error) {
			expect(error).to.be.null;
			done();
		});
	});

	After(function (done) {
		var that = this;
		this.stopServer();
		done();
	});
};

module.exports = hooks;
```

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
