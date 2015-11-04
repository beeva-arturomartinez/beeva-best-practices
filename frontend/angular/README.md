# Angular Style Guide

![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/AngularJS_logo.svg/695px-AngularJS_logo.svg.png "Angular")

## Index

* [Preface](#preface)
  * [Guide's Scope](#guides-scope)
  * [Angular's Lifecycle](#angulars-lifecycle)
* [General](#general)
* [Controllers](#controllers)
* [Directives](#directives)
* [Filters](#filters)
* [Services](#services)
* [Other stuff](#other-stuff)
  * [`$watch` expressions](#watch-expressions)
  * [Event management](#event-management)
* [Testing] (#testing)
  * [Unit testing](#unit-testing)
  * [E2E testing](#e2e-testing)
* [References](#further-reading)

# Preface

Thank you for reading this guide, on how to get the best possible outcomes out of your astonishing Angular app. This guide contains procedures and advices, collected from our experience on the tool over time. Every single item should have not only the *what*, but also the *why* using such specific method shall give you better results.

## Guide's Scope

This guide covers development techniques tightly related to Angular JS. Even if them rules could be abstracted into many other tools, you shall see the specific guide for such tool for further best practises.

The content of this guide lays on top of Frontend's best practises, whose contents should be taken into account too when developing an Angular application.

## Angular's Lifecycle

In order to fully understand the practises defined here, it would be really useful to have certain knowledge about how Angular deals with things. So here you'll find brief details on Angular's `phases`.

### Startup

#### Config

During app startup, Angular first calls to its declared `config` procedures. On these tasks your application should configure all stuff required to initialise your app. Amongst other thigs, this phase usually contains:

* Route configuration.
* i18n configuration.
* Global `$http`, `$location`, and other provider's configurations.
* Your custom config.

#### Run

The `run` process is another configuration phase. But this time around your app will be fully prepared and actually running. This could be a good place to load your transversal resources, configure app's language, ...

#### Config vs Run

Whilst the `run` process is engaged once the app is configured (and all stuff will be loaded and ready), during the `config` phase the application is still loading, and hence Angular hasn't inject your dependencies in order for you to use them. Therefore, during the `config` phase you will have available this:

| Dependency type | Availability                                                     |
| --------------- | ---------------------------------------------------------------- |
| constants       | Constants are available throughout all cycles of Angular's life. |
| providers       | Angular providers are `singletons` (just as any other services) that are not yet initialised during `config`. However, providers provides you (LOL) with a direct access to the declared function before init. |

### Routing

When you request Angular to load a route, you're internally triggering some tasks to verify and load your route, and several events will be fired.

* `$routeChangeStart`: This event is fired once a new route is requested. If you listen to this event you could intercept all route changes, and make some controlling within (verify authorization for the requested path, log information...).
* `$stateChangeSuccess`: Once your new route is loaded and if everything went smooth, you'll get this event triggered.
* `$routeChangeError`: If anything went south this event will be dispatched. 

_NOTE:_ All above-menctioned events are documented [here](https://docs.angularjs.org/api/ngRoute/service/$route).

Once this flow finishes, and if everything went ok, your new route will be loaded. Usually `routing` processes are followed up by `rendering` processes.

### Rendering & data-binding

Rendering is the phase in which Angular takes your views and templates and `$compile`s them. During this lap, all your directives will be engaged, your bindings linked and synced, and your application freed to the user to interact with it. This is all achieved via the `$digest` cycle.

This rendering flow is in most cases completely automatic. Thanks to Angular's `dirty checking`, once something changes within your binded model, Angular will internally launch a `$digest` cycle, in which all targeted values will be checked and updated, and your view should immediately reflect your model updates. There are some cases though in which Angular does not now something has changed, and you need to notify its core to perform a new `$digest`. This is achieved via the `$apply` method.

_NOTE:_ As word of advice, read *carefully* the section about bindings and expressions. Is not usually a good practise to override Angular's native cycle through manual `$apply` calls, and most of the times this could be overcome with a slightly different approach.

# General

## Write obfuscation-ready code

When you inject some dependencies into yours, Angular recognises what you're intending to do by the name of your dependency. Once obfuscation process is through, your variable names would be messed up, and hence Angular would not have a clue about what to inject where.

Angular's solution is to explicitely define dependencies into an array, before declaring your dependency function. In fact, your dependency function will be the last item of such array, and it will receive as arguments all other earlier items.

```javascript
/*
 * Do this
 */
angular.module('myFancyApp')
 .controller('myFancyCtrl', ['$scope', '$filter', 'otherDependency', function($scope, $filter, otherDependency) {
  ...
 }]);
 
/*
 * InsteadOf this
 */
angular.module('myFancyApp')
 .controller('myFancyCtrl', function($scope, $filter, otherDependency) {
  ...
 });
```

_NOTE:_ On some examples below we have ommited this syntax. Our only purpose is to keep this guide short.

## Atomic development

As in any other development language/tool/technique, atomicity gives you great leverage on reading/understanding/refactoring your code. If you don't avoid having thousands lines files with many dependencies within them, using your code will eventually be a nightmare.

```javascript
/*
 * Do this
 */

// SampleDirective.js
angular.module('myFancyApp').directive('sampleDirective', [..., function(...) {
 ...
}]);

// AnotherDirective.js
angular.module('myFancyApp').directive('anotherDirective', [..., function(...) {
 ...
}]);

// --------

/*
 * InsteadOf this
 */
 
// Directives.js
angular.module('myFancyApp').directive('sampleDirective', [..., function(...) {
 ...
}]).directive('anotherDirective', [..., function(...) {
 ...
}]);

```

## Angular's modules

### Setting and getting

Angular modules are defined by invoking the `module` function, passing through a name for the module, and the array of dependencies it must be able to inject. Afterwards, modules are got via the same function call, but giving it just the _name_ attribute. You must avoid setting up modules more than once.

```javascript
/*
 * Do this only once!
 */

//Setting up a module
angular.module('myFancyApp', ['ngRoute', 'ngAnimate', ...]);

/*
 * Once your module is set up, you can just get it anywhere you want
 */

// Getting the module
angular.module('myFancyApp')
```

### Avoid global variables

Using global variables is always a discouraged technique. `True` they will be available all along your app, but precisely, you could cause collisions among your different files. If you want more information about this topic, please go to Douglas Crockford's article on [why global variables are *evil*](http://yuiblog.com/blog/2006/06/01/global-domination/).

```javascript
/*
 * Do this
 */

// Setting up
angular.module('myFancyApp', [...]);

// Getting
angular.module('myFancyApp')
 .controller(...);

/* 
 * InsteadOf this
 */
 
// Setting up
var myFancyApp = angular.module('myFancyApp', [...]);

// Getting
myFancyApp.controller(...);


```

## Modularise your app

In almost every project you will be facing some feature development that doesn't belong to your project's core, or that could be otherwise extracted from central functionalities. 

If you extract these features outside your core, you will ease code understanding, and you could reuse all modularised functionalities into other projects that suit, as they will be component-ready for direct-importing.

> Let's assume your application needs to handle users, and you need to provide several tools for audits.

```javascript
/*
 * Do this
 */
angular.module('myFancyApp.users', [...]) ... // Users' stuff
angular.module('myFancyApp.tools', [...]) ... // Audit tools
angular.module('myFancyApp', ['myFancyApp.users', 'myFancyApp.tools']) ... // General stuff

/*
 * InsteadOf this
 */
angular.module('myFancyApp', [...])
 .directive('userStuff', function() {...})
 .provider('auditTools', function() {...})
```

# Controllers

## `controllerAs`

When you declaring a controller within a view, you will immediately be provided with its scope, for direct use under your views (in other words, the `$scope` of such controller will be the namespace of the view, just as if all view would be nested within a `with($scope)` clause).

This is cool. However, for non-complex types you could be facing reference problems when your model updates, specially if you are using a variable from an inner `$scope`. Your solution's name is `controllerAs`.

```html
<!-- Do this -->
<div ng-controller="myFancyCtrl as fancy">
 {{ fancy.variable }}
</div>

<!-- InsteadOf this -->
<div ng-controller="myFancyCtrl">
 {{ variable }}
</div>
```

This will also help you while using nested controllers, as you won't need to follow-up hierarchy to arrive at your target variable:

```html
<!-- Do this -->
<div ng-controller="myFancyCtrl as fancy">
 <div ng-controller="myChildCtrl as child">
  {{ fancy.variable + child.variable }}
 </div>
</div>

<!-- InsteadOf this -->
<div ng-controller="myFancyCtrl">
 <div ng-controller="myChildCtrl">
  {{ $parent.variable + variable }}
 </div>
</div>
```

## Nesting controllers

### Angular's `$scope` hierarchy

As Angular documentation states:

> Scope is an object that refers to the application model. It is an execution context for expressions. Scopes are arranged in hierarchical structure which mimic the DOM structure of the application. Scopes can watch expressions and propagate events.

Due to this `$scope` inheritance, all parameters defined in upper scopes will be propagated to lower ones. This could be a source of performance leaks, and you should fairly know how Angular's scopes need to work (See the guide about [`$scopes`](https://docs.angularjs.org/guide/scope) and the [`$rootScope`](https://docs.angularjs.org/api/ng/type/$rootScope.Scope) documentation.

### Don't abuse `$rootScope`

`$rootScope` is the highest-level `$scope` of your app. There's only one, accessible and mutable by all child controllers. Hence, every attribute and method defined within `$rootScope` will be propagated downwards to every single controller. Be wise about what you put inside it.

- [x] Common, view-accessible methods
- [ ] ~~Configuration attributes~~
- [ ] ~~Other things?~~

# Directives

## Use `restrict`

Directive's `restrict` parameter lets you define in which form your directive would be recognized. There are four types of directive restrictions:

* `E`: Your directive would be a DOM element (i.e. `<my-directive></my-directive>`).
* `A`: You will assign the directive to an element via an html attribute (i.e. `<any my-directive></any>`).
* `C`: You can use a css class to identify your directive (i.e. `<any class="my-directive"></any>`).
* `M`: Declare your directive with a html comment (i.e. `<!-- my-directive --><!-- /my-directive -->`).

Using the last two is not recommended, as first could be messed-up via dynamic class assignment, and the last could (and should) be removed by your code minifier.

```javascript
/*
 * Do this
 */
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   restrict: 'E', // or 'A' or 'EA'
   ...
  };
 });
```

### Replace your content if using `E`

When you use element-restricted directives (i.e. `E`), your DOM will render a `<my-directive>` tag in it. While this is cool for development purposes (as it keeps the html simple and more readable) it does not comply with HTML standards, and thus some browsers (e.g. our truly loving friend IE) won't execute your application properly. The solution? Replacing directive's content.

```javascript
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   ...
   replace: true
  };
 })
```
**WARNING:** When using `replace`, the template of your directive **must** lay into one sole root element. Otherwise you will get an exception thrown.

__NOTE:__ Seems that Angular has deprecated the `replace` attribute, and won't be available on 2.0 release. Shame on you, older IEs!


## Use `templateUrl` insteadOf `template`

Angular directives let you declare their templates either by an inline html structure (i.e. `template`) or via a view's URL (i.e. `templateUrl`). The latter the better, as therefore you will keep each language within its proper file.

```javascript
/*
 * Do this
 */
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   ...
   templateUrl: '/path/to/your/view.html'
  };
 })
 
/*
 * InsteadOf this
 */
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   ...
   template: '<div class="my-content"></div>'
  };
 })
```

## Leverage directive priority

When your application grows big you could end up having several different directives on the very same DOM element, each of which with a duty given. In some cases you may need one particular directive to be compiled after another. For that purpose Angular gives us a directive priority configuration (see more on [`$compile`](https://docs.angularjs.org/api/ng/service/$compile) documentation).

```html
<div my-directive1 my-directive2></div>
```

```javascript

// Directive 1 (more priority -> compiled first)
angular.module('myFancyApp')
 .directive('myDirective1', function() {
  return {
   ...
   priority: 1
  };
 })

// Directive 2 (less priority -> compiled last)
angular.module('myFancyApp')
 .directive('myDirective2', function() {
  return {
   ...
   priority: 0
  };
 })

```

## Use _object hash_ isolated scopes

Directives are directly nested down on DOM's structure, and hence on the `$scope` hierarchy. Thus, a directive will inherit by default all `$scope` attributes available on the context the directive's at. 

Directive isolation lets you define which attributes the directive could use, and even tell angular how.

```javascript
/*
 * Do this
 */
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   ...
   scope: {
    inheritValue: '=', // Value obtained from upper scopes
    plainValue: '@', // Value given directly via String
    functionValue: '&', // Function call value
    renamedValue: '=otherValue', // Value set up as 'other-value' within the HTML, renamed to 'renamedValue'
    optionalValue: '=?' // If followed-up by a question mark, your parameter will be optional
   }
  };
 })
 
 /*
  * InsteadOf this (full isolated scope)
  */
 angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   ...
   scope: true
  };
 })
 
 /*
  * Or this (no isolated scope)
  */
 angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   ...
   scope: false
  };
 })
```

## Don't declare a directive's controller (normally)

You can include controller functionality under the `link` or `compile` methods. This is on most cases enough to cover your needs.

Angular directives could also define a controller to be directly engaged to the directive's template, in which you could also include features. This is not the best practise. At least not normally.

If your directive features intention are to be shared, an explicit controller might be your solution. On any other cases, `link` methods will do just fine.

```javascript
/*
 * Do this
 */
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   link: function postLink(scope, element, attrs) {
    // your code
   }
  };
 })
 
 /*
 * InsteadOf this
 */
angular.module('myFancyApp')
 .directive('myDirective', function() {
  return {
   controller: function() {
    // your code
   }
  };
 })
```

#Filters

TODO

#Services

TODO

#Other stuff

## `$watch` expressions

TODO

## Event management

TODO

# Testing

## Angular mocks

TODO

## Unit testing

TODO

## E2E Testing

TODO

## Further reading

TODO

___

[BEEVA](http://www.beeva.com) | 2015
