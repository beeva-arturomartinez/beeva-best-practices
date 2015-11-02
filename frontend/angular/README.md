# Angular Style Guide

Welcome to AngularJS' best practises guide!

![alt text](https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/AngularJS_logo.svg/695px-AngularJS_logo.svg.png "Angular")

## Index

* [Preface](#preface)
  * [Angular's Lifecycle](#angular-lifecycle)
  * [More - TODO](#more)
* [Configuration](#configuration)
  * [TODO](#todo)
* [Controllers](#controllers)
  * [Use of `$scope`](#using-scopes)
  * [`controllerAs`](#controller-as)
  * [Nesting controllers](#nesting-controllers)
* [Directives](#directives)
  * [Restrict](#directive-restrict)
  * [Priorities](#directive-priorities)
* [Filters](#filters)
* [Services](#services)
* [Testing] (#testing)
  * [Unit testing](#karma-jasmine)
    * [Angular mocks](#angular-mocks)
  * [E2E testing](#protractor)
* [References](#references)

# Preface

Thank you for reading this guide, on how to get the best possible outcomes out of your astonishing Angular app. This guide contains procedures and advices, collected from our experience on the tool over time. Every single item should have not only the *what*, but also the *why* using such specific method shall give you better results.

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

`run` processes are launched as soon as your app is configured. TODO.

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

### Others?




___

[BEEVA](http://www.beeva.com) | 2015
