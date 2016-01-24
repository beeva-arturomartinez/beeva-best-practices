#Front-end BEEVA Best Practices   

##3.- HTML

* web semántica
* accesibilidad
* SEO (básico)


### Web Semantica
HTML offers a limited number of tags , which usually fall short for the number of elements that are mounted in a Web .
A first level of semantics can be added with the Class attribute, but this only has value at the level of the developer provided they are used in an organized way ( see CSS section of this guide) , not for HTML renderers .

#### Microformats
Microformats are a set of conventions alcazada community that aims to solve the biggest case haw a website with no effort (80/20 philosophy).
These microformats use the class attribute:
 * hCard for contact details
 * hCalendar for events
 * hAtom for news and the like
 * ...

Since it is a community consensus there are tools for parsing, even extensions for browsers. More information on their wiki(http://microformats.org/wiki/Main_Page)

#### Mark
With the advent of HTML5 arrived inline elements specifically became known text- level semantics. Mark is one of them.

An example is the use of Web searches that highlight the results , avoiding using SPAN tags as still being right does not provide semantic or EM or STRONG , since the objective is to highlight the outcome without implying that has a semantic importance.
```html
<h1>Resultados de la búsqueda de la palabra 'poyaque'</h1>
<ol>
    <li>El señor de los <mark>poyaque</mark>s...</li>    
    <li>el cliente y su temido <mark>poyaque</mark></li>
</ol>
```
#### Time
One of the most used is the hCalendar microformats , but have trouble handling date formats unwieldy to humans.

There are solutions created by the community as the use of abbr :
```hmtl
<abbr class="dtstart" title="2011-07-17">
    17 de Enero del 2011
</abbr>
```
And the solution proposed HTML5
```hmtl
<time class="dtstart" datetime="2011-07-17">
    17 de Enero del 2011
</time>

<time datetime="21:00">9pm</time>
<time datetime="2011-09-12">12 de Septiembre del 2011</time>
<time datetime="2011-09-12T13:30">12 de Septiembre del 2011 a las 1:30pm</time>
```
#### Meter
Meter allows you to create a scale of maximum and minimum values ​​, and create ranges with the low, high and Optimum taxes and establish a measure in it.
```
<meter low="-10" high="100" min="12" max="50" optimum="26" value="25">
    Tú lo que quieres es que me coma el tigre es que me coma el tigre...
</meter>
```
#### Progress
while Meter serves to represent something that has been measured , progress allows us to visualize the progress of something that is changing.
```html
Tu perfil está un <progress min="0" max="100" value="70"></progress> 70% completado, añade una foto desnudo/a.
```
```js
var progressBar = document.getElementById('p');
  function updateProgress(newValue) {
    progressBar.value = newValue;
    progressBar.getElementsByTagName('span')[0].textContent = newValue;
  }
```
#### Structural
Originally in the HTML tag it is used DIV for different sections of HTML. HTML5 has created a set of tags with semantic load.

##### Section

The new section element has made ​​the group so interrelated thematic elements. It is very similar to the use that is given to the div element but with the difference that the div element has no semantic weight and does not inform us about the type of content it holds. The section element is used explicitly to group related content.

The rule for knowing when to use the new section element is simple, you just ask this question, "What is the content that will house related to one another?"

##### Header
The header element has the semantic burden of being the head of the HTML page, which can contain a logo, a menu or any other element of a header. But also there is a header element in a section direction.
That is, the header element usually appears at the beginning of a document or section, but what use is defined the content, not the position.

##### Footer
Very similar to the previous item , but its location is the bottom of a document or section.

##### Aside


##### Nav

##### Article

#### Modelos de Contenido
     
##### Contenido de Sección

##### hgroup

