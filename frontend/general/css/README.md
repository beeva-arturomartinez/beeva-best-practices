#Front-end BEEVA Best Practices   

##2.- CSS 

Index
* Creating style sheets
* Using CSS preprocessors
* Organize CSS
* uso correcto de selectores, reducir su número en las hojas de estilo
* evitar utilizar clases para dotar de funcionalidades JS (usar data-xxxx) etc.

### 2.1\. Creating style sheets

In this section we discuss some basic rules for both the creation of new style sheets and for organizing them. The goal is to minimize the volume of the generated files and avoid having to overwrite the classes or attributes using the modifier **!important**.   

#### 2.2\. Using CSS preprocessors

Preprocessors are tools that provide meta for creating CSS files. These are translated into CSS files that will be inserted into the Web.

There are different preprocessors in the market among them Sass/Compass and Less are the most widespread. These tools allow you to create variables and functions for use in CSS, as well as providing features such as loops. optimizing the generation, readability and reuse of CSS.

However, the recommendation is to use SASS/Compass, to the greatest extent in the community.

For correct use of these tools it is necessary to have knowledge of good practice in creating CSS, in order to exploit the power of these preprocessors.   

#### 2.3\. Organize CSS

In the development should generate multiple CSS that will later be unified for production environments. With that we improve the encapsulation of the different needs of the project. 

Using CSS preprocessors is recommended to have an initial file that loads the rest , thus the order in which the classes defined in each of the files are loaded is controlled.

```css
/*** main.scss content */
/* general */
@import colors.scss
@import fonts.scss
@import containers.scss
@import buttons.scss
...

/* specific */
@import students.scss
...
```

To make this separation are certain criteria:

* Generic reusable components
* Functional application components

Reusable components are those that are not part of the application itself that we are developing, it can be used in various applications. These are for example the css for data grids, select boxes, trees and other reusable components or widgets. These components may come from third-party libraries or frameworks that overwrite, but if they themselves want to reuse components between applications we have to pay special attention to this separation because it will allow us to preserve independecia to be reused in the current project or another.

Other components are functional, that is, focusing on all visual content having a certain functionality. They can be several pages of a traditional website based on a pattern of multiple pages, the various functional modules or applications that follow a pattern of a single page or SPA (Single Page Application ). For example, a course management application, we could have a module students with all the CSS that was related to this module located in a students.css file.

These two approaches are not mutually exclusive. It is useful to identify elements that are general and create a reuse approach, and certainly appear specifications related to functionality, which will overload these general styles.

#### 2.4\. Types of CSS classes

It is important that our rules of CSS have a **semantic** nature , so they are very intuitive. To do this , we can group the CSS classes into four types:

* Structure
* Visual state
* Functional entity
* Functional status

##### 2.1.3.1\. Structure

The structure css classes are those that reference **reusable components** in different applications or websites. Again become our reusable components. These must have a unique CSS class to make reference to them. For example, if we use two data grids on our website, we should never have a class grid-first and  another class grid-second, but both should have the same css class data- grid. If in any case requires the viewing is different, this is usually for functional reasons discussed below, in this case an overload of classes will be used, ie, applying a whole class to class data-grid to represent differences.
 
##### 2.1.3.2\. Visual state

The status display indicates which particular state of a component of any type ( structure or entity ). The most common states are:

* Selected
* Disabled
* Focused
* Actived
* The hover property (eg buttons, links, row of a grid of data...)
* Hidden
* Editable

Other states like unchecked, enable hidden or not editable. We can consider that a component in its normal state possesses these characteristics.

Returning to the example of grids, if you have two grids that have to be differentiated and the difference could be the display status one another enabled and disabled. So, instead of having two classes css, one **grid-normal** and one **grid-disabled**, we could use a single class data- grid and use the state as a modifier :

```html
<table class=”data-grid”>   
<tr>   
<td><span>column 1<span></td>   
<td>column 2</td>   
</tr>   
</table>   
<table class=”data-grid disabled”>   
<tr>   
<td><span>column 1</span></td>   
<td>column 2</td>   
</tr>   
</table>   
```

And in our CSS , we would have the generic properties for data- gird and , in the modifier according to the state would be sufficient to override you need:

```css
table.data-grid {color: #000; width: 100%;}   
table.data-grid.disabled {color: #CCC;}   
```

And the same for the nodes that depend on it, as the span

```
table.data-grid {color: #000; width: 100%;}   
table.data-grid span {font-weight: bold;}   

table.data-grid.disabled {color: #CCC;}   
table.data-grid.disabled span {font-weight: normal;}   
```

But also , you might want to not only depended on the status of view, but on different screens, we want to be displayed differently depending on the type of display, which has a functional nature, which we will try next.

##### 2.1.3.3\. Functional entity

These can be either to refer to certain entities (students, courses, teachers...) and more global entities, as it could be an administration module. In the following example we will see how we could differentiate the grids data management module from the rest of the application:

```html
<!-- Management Module -->   
<div class=”administration”>   
<table class=”data-grid”>   
<tr>   
<td><span>column 1<span></td>   
<td>column 2</td>   
</tr>   
</table>   
<table class=”data-grid disabled”>   
<tr>   
<td><span>column 1</span></td>   
<td>column 2</td>   
</tr>   
</table>   
</div>   

<!-- Another Module -->   
<div class=”administration”>   
<table class=”data-grid”>   
<tr>   
<td><span>column 1<span></td>   
<td>column 2</td>   
</tr>   
</table>   
<table class=”data-grid disabled”>   
<tr>   
<td><span>column 1</span></td>   
<td>column 2</td>   
</tr>   
</table>   
</div>   
```

Well, it suffices to make the rules depend hierarchically in terms of these entities:

```css
/* generic Grid */   

table.data-grid {color: #000; width: 100%;}   
table.data-grid span {font-weight: bold;}   

table.data-grid.disabled {color: #CCC;}   
table.data-grid.disabled span {font-weight: normal;}   

/* Management Module */   

.administration table.data-grid {color: #fff;background: #000; }   
```

Simply we override it what interests us, keeping the rest of default rules for both spans containing as to the disabled state, also can be overridden if interesase.

For these classes are useful, they must be located in the upper HTML node, the father highest level overarching umbrella to rest, to not have to be repeated in the HTML. They must be included once. However, in CSS, any rule which depends on this class should reflect on the CSS rule. For instance:

```html
<div class=”user”>   
<form class=”form-user”>   
…   
</form>   
</div> 
```

In the css:

```css
.user {font-size: 14;}   
.form-user {padding: 5px;}   
```

This would be wrong. If we want to indicate to form a class, it should not contain anything regarding user. Instead, it should be accessed through the class hierarchy:

```html
<div class=”user”>   
<form>   
…   
</form>   
</div> 
```

In the css:

```css
.user {font-size: 14;}   
.user form {padding: 5px;}   
```

or, if we want to give a special class to the form so that this rule not all forms are applicable:

```html
<div class=”user”>   
<form class=”registration”>   
…   
</form>   
</div> 
```

In the css:

```css
.user {font-size: 14;}   
.user form {padding: 5px;}   
.user form.registration {font-size: 11px;}   
```

Thus, all forms of user dependent class would have a font size of 14px and a general padding of 5px, except the registration form, which will overwrite the font size.

##### 2.1.3.4\. Functional status

Finally, would the CSS classes of functional status, which refer to individual states may have a particular entity. For example, for different states can have the elements of a message (header, icon, date, etc) in a messaging system. We could have many different css classes for these elements, for example, in terms of icons: ico-sent, ico-received, ico-read... A simple and semantic form, would add that state as a modifier of the css class that represents functional entity, seen in the previous point, defining the normal rules of each camp. For example, let's focus on the field icon:

```html
<div class=”message received”>   
<span class=”transmitter”>Jhon Doe</span>   
<span class=”receiver”>Jane Doe</span>   
<span class=”send-date”>12/12/2013</span>   
<span class=”reception-date”>-</span>   
<span class=”ico”>-</span>   
</div>   
```

In the css...

```css
/*First we define the general characteristics for icons of messages. A note: use sprites for images to reduce calls to the server*/   

.message span.ico {   
display: inline-block;   
width: 20px;   
height: 100%;   
background-image: url(‘sprite_path’);   
}   

/*Now we define the positions of images by state message*/   

.message.sent span.ico {background-position: 0 0}   
.message.received span.ico {background-position: 0 20px}   
.message.read span.ico {background-position: 0 40px}   
```

Thus, we must not create lots of css classes, creating quite optimized, reusable and easy to maintain files.

#### 2.1.4\. Other considerations

In addition to the rules seen above, must be avoided as far as possible use the switch **!important** and / or **style** property of DOM nodes .

As for the style property, it is often essential to use. For example, it is imperative for many when frameworks provide dynamic attributes such as width, height, etc. to offer a dynamic and / or flexible components. However, what it is to be avoided at this property indicate attributes of constant styles. Anything must be consistent in its corresponding CSS file.
And, for the switch **!important**, there are times in which it is essential. For example, in those cases in which frameworks accessing the style property and modify a particular attribute, for any reason, we want to be consistent and we have defined in our CSS. As inclusion in the style property overrides the classes defined in the CSS as a general rule, we can only do so using this switch, overriding the value in our style sheet, but should be limited use as far as possible.

Another measure that may be useful, to avoid having to include specific styles sheets for each browser, Javascript is added using a CSS class BODY DOM node, referring to the browser. For instance:

For IE7   

```html
<html>   
<head>   
...   
</head>   
<body class=”ie v7”>   
</body>   
</html>   

For Chrome   
<html>   
<head>   
...   
</head>   
<body class=”chrome”>   
</body>   
</html>   
```

Thus, we can have generic classes that are cross-browser and overwrite only what is necessary depending on the browser.

```css
/*Generic*/   
.data-grid {padding: 4px;}   

/*For IE7*/   
.ie.v7 .data-grid {padding: 2px;}
```

