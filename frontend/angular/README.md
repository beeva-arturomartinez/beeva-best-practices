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

* `$routeChangeStart`: TODO.
* `$stateChangeError`: TODO.
* `$stateNotFound`: TODO.
* `$stateChangeSuccess`: TODO.

_NOTE:_ All above-menctioned events are documented [here](https://docs.angularjs.org/api/ngRoute/service/$route).


Once this flow finishes, and if everything went up according to plan, your new route will be loaded, and if such route has a configured view, it will kick-in right away.

### Rendering & data-binding

* `$viewContentLoaded`: TODO.
* ...

TODO Rendering & data-binding (`$digest`, `$apply`, ...)

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
