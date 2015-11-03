# Angular Style Guide

Welcome to AngularJS' best practises guide!

![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/AngularJS_logo.svg/695px-AngularJS_logo.svg.png "Angular")

## Index

* [Preface](#preface)
  * [Guide's Scope](#guides-scope)
  * [Angular's Lifecycle](#angulars-lifecycle)
* [Configuration](#configuration)
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

# Configuration

TODO

# Controllers

TODO

# Directives

TODO

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
