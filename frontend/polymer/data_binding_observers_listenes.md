# Correctly used Data binding, observers and listeners:


### HTML construction

The polymer HTML must have a semantic markup, use semantic Classes  Beyond appropriate element names, classes and IDs are semantic: they describe themselves without specifying.

If we detect a node of the DOM that will have a lot of access and this node isn't inside a DOM-IF or a DOM-REPEAT, we recommend labeling it with an id, because polymer indexes those nodes. We can access them with ```this.$.id```.

Make accessible our component,use correctly ARIA-ROLES.

We always must test our web components in all available browsers and when possible, on mobile devices or simulators.

### JS construction

Our polymers should be simple to use and with a simple coding.

Our Web component are like API's. To develop polymers we must comment in detail the methods and properties, to make it useful for our partners and those know how to manage and integrate it.

The properties that use our polymer statement must be declared in the corresponding section.

If the property is simple we can define it in a single line beside its type , however if it's a complex property, it will use the complex notation.

```javascript
Polymer({

  is: 'x-custom',

  properties: {
    user: String,
    isHappy: Boolean,
    count: {
      type: Number,
      readOnly: true,
      notify: true
    }
  }....

```

Our components must be operable from keyboard when necessary.

JavaScript is not always the answer. We have to think if our solution is the correct one or if we could use CSS techniques and apply them properly.

Of course all of our components must be tested. WCT ( web component tester) includes Chai and Mocha. We recommend building unit, functional and integration test.

For other uses, we will follow the recommendations of javascript guideline. Defining the variable that we use above methods do not abuse the searches in the DOM.

### Polymer and lifecycles

We never rely on the life cycles of polymer its order is as follows :

* Created callback
* Local DOM initialized
* Ready callback
* FactoryImpl callback
* Attached callback

We must bear in mind that this order may vary depending on whether the browser includes native support for web components.

If you need to access items that are not prepared in the lifecycle yet, you can do it from the life cycle attached with async .

```javascript
attached : function ( ) {
   this.async (function ( ) {
      // Access sibling or parent elements here
   } ) ;
}
```

If one of our polymers uses or needs attributes, we recommend initializing them in creation phase and they must be declared in properties through *hostAttributes*.

### Polymer Communication  Data binding

Data binding binds a property or sub-property of a custom element (the host element) to a property or attribute of an element in its local DOM (the child or target element).

A binding is created with a binding annotation in the host element’s local DOM.

First of all, we must know the kind of Data binding.

A binding annotation consists of a property name or subproperty name enclosed in curly brackets ({{}}) or square brackets ([[]]).

* Square brackets ([[]]) create one-way bindings. Data flow is downward, host-to-child, and the binding never modifies the host property.

* Curly brackets ({{}}) create automatic bindings. Data flow is one-way or two-way, depending whether the target property is configured for two-way binding.

When we make a bind on a polymer, this from below make a set function, that deals with it making a dirty-checking, apply property-effects to the property of the bind and launch an event 'name'-changed , beside this, if our binding is "two-way" polymer establishes an eventListener to detect changes and to apply them up. For this reason, to establish binds in our application we will take into account the following considerations:

* If the data will be read from linked lists , we will always use a "one-way" binding.
* If the created property only produces data, this property shall be marked with the read-only flag , as it avoids the creation of the setter.
* When we do bindings to objects and we want to modify a subproperty, we will never overwrite the entire object ,instead we will use the methods given by polymer , which already use below 'notifyPath' to notify the changes.
* The 'DOM-IF' helpers must be use to reduce the rendering of the DOM, the task of this helper is to leave part of the template without rendering when your property is false , if we want features to show and hide, use the attribute *Hidden* giving in polymer.
* When handling Array's binding if we want to reflect changes or update any member of the array, we must update the property by an 'observer' or 'Computed bindings'.


### Observers in Polymer

The use of properties observer from inside properties and outside Observers are different. 

The first one is used for simple properties and to detect changes , this function will always receive the old value and the new value. The second one, observers that are used for arrays and objects can also observe several properties at once.

This kind of array observer allow us to:

* Observe two properties at a time and react when the two are complete. If one of the two is undefined , the function assigned will not run , until the values are different from undefined.

* In this type of observers when we use a "_ * _ " (deep )we will receive an object (change record) in the function with the following properties : path (path of the property has changed) , value ( the new value ) and base ( the part not included in the _ _ * ) , we recommend using this object.

### Listeners in Polymer

To add EventListeners our polymer Listeners must use the function that polymer provides

```javascript
Polymer({

     is: 'x-custom',

     listeners: {
       'tap': 'regularTap',
       'special.tap': 'specialTap'
     },

     regularTap: function(e) {
       alert("Thank you for tapping");
     },

     specialTap: function(e) {
       alert("It was special tapping");
     }

   });
```

If you need to add EventListeners the document should be able to remove them when required or always if a lifecycle 'detached' occurs in our polymer , for it will consider our eventListener will not tied to an anonymous function , as this may cause memory leaks, we must also remember that , if it's necessary use the this context we must pass it through our function by different means available for example bind.

We may also use on-event annotations , if we pick up a touch event , as on-tap, is desirable to use on-click , since it has better performance.

```html
<dom-module id="x-custom">

  <template>
    <button on-tap="handleClick">Kick Me</button>
  </template>

  <script>

    Polymer({

      is: 'x-custom',

      handleClick: function() {
        alert('Ow!');
      }

    });

  </script>

</dom-module>
```


Shadow DOM has a feature called “event retargeting” which changes an event’s target as it bubbles up, such that target is always in the receiving element’s light DOM. Shady DOM does not do event retargeting, so events may behave differently depending on which local DOM system is in use.

Use Polymer.dom(event) to get a normalized event object that provides equivalent target data on both shady DOM and shadow DOM. Specifically, the normalized event has the following properties:

* rootTarget: The original or root target before shadow retargeting (equivalent to event.path[0] under shadow DOM or event.target under shady DOM).

* localTarget: Retargeted event target (equivalent to event.target under shadow DOM)

* path: Array of nodes through which event will pass (equivalent to event.path under shadow DOM).
