# Polymer Style Guide

![Polymer logo](https://www.polymer-project.org/images/logos/lockup.svg "BEEVA")

Polymer is a library created to develop web components in order to compose apps with. It relies on Web Components standard from W3C. It is supported by Google and, by now, it has released its 1.2.4 version.

Since Polymer is based on Web Components it has to different contexts.
 
* **Component context:** when you develop standalone components to be used by other web components, or component based apps.
* **App context:** When you use web components to compose your app with, taking care on how they interact.

In this document we're going to introduce some common good practices for developing polymer components, also polymer based apps.

## Index

* Think in polymer:

  - Best practices about how to compose using atomic design, without monolithic code

* Correctly used observers: 
  
  - Notify readOnly

  - {{}} vs [[]]

  - declarative vs imperative

* [Behaviors and inheritance](behaviors_and_inheritance.md):
  
  - Reusing code by mixins
  
  - Inheritance emulation

* Life cycle matters:
  
  - You have to do some stuff at the correct time

* Styling well:

* Javascript applied to Polymer:

* [Appendice](appendice.md):
  - Polymer apps
  - Mediator pattern
  - Tools for polymer
    - Bower
    - Yeoman


### References

* [Web Polymer](https://www.polymer-project.org): Polymer project website

___

[BEEVA](http://www.beeva.com) | 2015
