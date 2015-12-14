# Behaviors and inheritance

In this section we'll see about behaviors (called mixins on previous versions), and how they can be used to reuse code between different components or simulate inheritance.
  
## Reusing code by behaviors
Behaviors are reusable modules following the Polymer prototype structure, similar to mixing concept on other languages.

When you need to share functions, or properties between different components, being this shared code someway generic, you could use a behavior for that.

Let's imagine we have two webcomponents for two different purposes having the requirements below:

 * The component must save on a property the current time when it's attached.
 * The component needs a private function to check if DNI is valid or not.
 
Facing this requirements we can assume we have three options:

### Copy Paste (BAD SMELL)

We can just copy and paste code from one component to the other, breaking the DRY concept and making your code less maintainable.

Then we could have some polymer like below:

```
...
  is: 'polymer-one',
  attached: function(){
    this.set('timestamp', Date.now());
  },
...
  checkDNI: function(value){
    // Whatever...
  }
...

```

And then, we can copy our required code into the next one:

```
...
  is: 'polymer-two',
  attached: function(){
    this.set('timestamp', Date.now());
  },
...
  checkDNI: function(value){
    // Whatever...
  }
...

```

That's not so maintainable since you must change every ocurrence of code if you need to modify it.

### Make a Polymer from that (BAD SMELL)

We can create a polymer element in order to use it every time we need to. 

```
...
  is:'common-staff',
  attached: function(){
    this.set('timestamp', Date.now());
  },
...
  checkDNI: function(value){
    // Whatever...
  }
...

```

It could seem useful to use the **<common-staff></common-staff>**, but it's not required to have a component for something that, thoug generic, it's so specific.

### Behavior way

You can create behaviors for that. For example, you can create a behavior called timeStamperBehavior and other behavior called validationUtilsBehavior.

#### time-stamper-behavior.html

Life cycle callbacks declared at behaviors are executed just before the client one that it's using the behavior.

```
  <script>
    TimeStamperBehavior = {
      checkDNI: function(value){
        // Whatever...
      }
    };
  </script>

```

#### validation-utils-behavior.html

Functions and properties declared on behaviors are included into the client's prototype. If there're already a function or property with the same name in the client, the behavior function is overwritten.

```
  <script>
    ValidationUtilsBehavior = {
      attached: function(){
        this.set('timestamp', Date.now());
      }
    };
  </script>
```

#### Using behaviors

You can also extend the behaviors if you need, having all the related logic encapsuled and modularized by behaviors.

Now, if you need to use the behaviors above, you can use both, or just one of them, like this:

```
  <link rel="import" href="time-stamper-behavior.html">
  <link rel="import" href="validation-utils-behavior.html">

  ...
  behaviors: [TimeStamperBehavior, ValidationUtilsBehavior],
  ...
```
  
## Inheritance emulation
//TODO