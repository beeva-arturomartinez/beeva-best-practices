![alt text](../../../static/horizontal-beeva-logo.png "BEEVA")

# Front-end Development Best Practices

## HTML

* Semantic Web
 * Structural
  * Section
  * Header
  * Footer
  * Aside
  * Nav
  * Article
  * Conclusion
 * Content Models
  * Section content
   * hgroup
* Accessibility
* SEO (basic)
 * Internal positioning
 * External positioning

### Semantic Web
HTML offers a limited number of tags, which usually fall short for the number of elements that are mounted in a Web.
A first level of semantics can be added with the Class attribute, but this only has value at the level of the developer provided they are used in an organized way (see CSS section of this guide), not for HTML renderers.

Some of this semantics items are:
* Microformats - Are a set of conventions alcazada community that aims to solve the biggest case haw a website with no effort (80/20 philosophy).
* Mark - Inline elements specifically became known text-level semantics. An example is the use of Web searches that highlight the results.
* Time - To date and patters to dates.
```hmtl
<time class="dtstart" datetime="2011-07-17">
    17 de Enero del 2011
</time>

<time datetime="21:00">9pm</time>
<time datetime="2011-09-12">12 de Septiembre del 2011</time>
<time datetime="2011-09-12T13:30">12 de Septiembre del 2011 a las 1:30pm</time>
```
* Meter and Progress - To represent completion levels or steps in a workflow.

These are just a few examples, searching in the community can find multiple solutions to cases that need solving.

HTML5 provides semantic level estructutal solutions, which focus on the contents of a general, and does not depend on what is contained in each section of the code, but what kind of information it contains.

#### Structural
Originally in the HTML tag it is used DIV for different sections of HTML. HTML5 has created a set of tags with semantic load.

##### Section

The new section element has made ​​the group so interrelated thematic elements. It is very similar to the use that is given to the div element but with the difference that the div element has no semantic weight and does not inform us about the type of content it holds. The section element is used explicitly to group related content.

The rule for knowing when to use the new section element is simple, you just ask this question, "What is the content that will house related to one another?"

##### Header
The header element has the semantic burden of being the head of the HTML page, which can contain a logo, a menu or any other element of a header. But also there is a header element in a section direction.
That is, the header element usually appears at the beginning of a document or section, but what use is defined the content, not the position.

##### Footer
Very similar to the previous item, but its location is the bottom of a document or section.

##### Aside
It does not focus on content have to be at the bedside or in a lateral mode menu section but refers to a content with a particular importance.

Includes content that are within the scope of the rest of the page, it relates to a non directly.

To know whether the content should be an element of this type can have an idea if we will reply *negatively* to the question, If I remove this content the meaning of the main content is reduced?

##### Nav
This element contains information about navigating the site, and should contain only information from this site, without external references.

##### Article
Article should be used as an element specification section. It is defined as a independiete and independent content.

If our content can be redistributed in RSS or Atom feed and retains its full meaning, then, probably the article container element is appropriate for use in our document. In fact, the article element is particularly suitable for syndication.

Usually the item element contains a time element with *pubdate* attribute (important if you have multiple time items, only one has this attribute ). This is very common for publications.

#### Conclusion
Most troubling is that the article and section elements are very similar. The only thing that separates them is the word *"autonomous"*. Deciding which to use often depends on the interpretation made ​​by the designer or web designer and is best to use common sense and use what we look like in a certain context is more semantic.

#### Content Models
Previously the elements were defined within the two categories inline and block.

The inline elements have a content model called "text- level semantics." Many of the old block elements are included under the " grouping content" (lists, divs and paragraphs etc.).

The forms have their own model of content and elements such as img, video, audio and Canvan are included within the "embedded content".
     
##### Section content
The new structural elements form a new content model called "sectioning content".

We can define an outline of HTML document using h1 to h6 elements:

```hmtl
<h1><span class="pln">NBA</span></h1>
<h2><span class="pln">Players</span></h2>
<p><span class="pln">Working team</span></p>
<h3><span class="pln">Michael Jordan</span></h3>
<p><span class="pln">Aerolineas Air Jordan</span></p>
<h3><span class="pln">Kobe Bryant</span></h3>
<p><span class="pln">The number 24</span></p>
<h3><span class="pln">Tracy McGrady</span></h3>
<p><span class="pln">Porqué eres tan bueno McGrady</span></p>
<small><span class="pln">Jugones</span></small>
```
With the next schema:
NBA
 |_ Players
      |_ Michael Jordan
      |_ Kobe Bryant
      |_ Tracy McGrady
      
      
All content appearing after a hX associated with that element, but what about the small element? Must be related to the entire document, but the browser could not tell if the document or previous hX element.
```html
<h1><span class="pln">NBA</span></h1>
<section>
    <h2><span class="pln">Players</span></h2>
    <p><span class="pln">Working team</span></p>
    <h3><span class="pln">Michael Jordan</span></h3>
    <p><span class="pln">The 23 number</span></p>
    <h3><span class="pln">Kobe Bryant</span></h3>
    <p><span class="pln">The number 24</span></p>
    <h3><span class="pln">Tracy McGrady</span></h3>
    <p><span class="pln">Porqué eres tan bueno McGrady</span></p>
</section>
<small><span class="pln">Jugones</span></small>
```

We can define a section within the hx element that encompasses us what is in this block.
```html
<h1>NBA</h1>
<section>
    <header>
        <h2>Players</h2>
    </header>
    <p>Working team</p>
    <section>
        <header>
            <h3>Michael Jordan</h3>
        </header>
        <p>The 23 number</p>
    </section>
    <section>
        <header>
            <h3>Kobe Bryant</h3>
        </header>
        <p>The number 24</p>
    </section>
    <section>
        <header>
            <h3>Tracy McGrady</h3>
        </header>    
        <p>Porqué eres tan bueno McGrady</p>
    </section>
</section>
<small>Jugones</small>
```
We can even use the h1 element at all levels because the structure of the Web content is maintained.

```html
<h1>NBA</h1>
<section>
    <header>
        <h1>Players</h1>
    </header>
    <p>Working team</p>
    <section>
        <header>
            <h1>Michael Jordan</h1>
        </header>
        <p>The 23 number</p>
    </section>
    <section>
        <header>
            <h1>Kobe Bryant</h1>
        </header>
        <p>The number 24</p>
    </section>
    <section>
        <header>
            <h1>Tracy McGrady</h1>
        </header>    
        <p>Porqué eres tan bueno McGrady</p>
    </section>
</section>
<small>Jugones</small>
```
This would not be possible in previous versions, because the resulting structure would be flat.

NBA
Autores
Michael Jordan
Kobe Bryant
Tracy McGrady

##### hgroup
Sometimes we will want to use a title or header element but will not want to alter the document outline. For this we use the hgroup element that serves precisely for that:

```html
<hgroup>
    <h1><span class="pln">NBA</span></h1>
    <h2><span class="pln">Pasión por el Software</span></h2>
</hgroup>
```

Here h2 is the slogan of the first h1 and not taken into account in the document outline. Dento a hgroup element, only the first header contributes to building the document outline and does not have to necessarily be a h1 can be any level.

The fieldset, blockquote and td elements are not taken into account at all in the algorithm outlined in the document. Therefore, these elements are called "sectioning roots" and can be said to be either invisible or innocuous to the document outline.


### Accessibility
Speaking of Web Accessibility is talking about universal access to the Web, regardless of the hardware, software, network infrastructure, language, culture, geographical location and capabilities of users.

With this starting point comes the [Web Accessibility Initiative](http://w3.org/WAI/) (WAI). It is an activity carried out by the W3C, which aims to facilitate the access of people with disabilities by developing guidelines for accessibility, improving the tools for the evaluation and repair of Web accessibility, conducting educational work and awareness in relation the importance of accessible design of web pages, and opening new fields accessibility through research in this area.

#### How it works?
They have created 14 guidelines with proposals for design solutions calls Accessibility Guidelines Web Content (WCAG). These guidelines provide design solutions and used as examples common situations in which the layout of a page can produce problems of access to information. The Guidelines also contain a number of checkpoints that help detect possible errors.

Each checkpoint is assigned to one of three priority levels set by the guidelines.

* Priority 1 : are those things that a Web developer has to comply because, otherwise, certain groups of users could not access the information on the website.
* Priority 2 : are those things that a Web developer should comply because if it were not so, it would be very difficult to access information for certain groups of users.
* Priority 3 : are those things that a Web developer should comply because, otherwise, some users experience certain difficulties in accessing information.

According to these checkpoints conformity levels are established:

Conformance Level "A": all the checkpoints of priority 1 are satisfied.
Conformance Level "Double A": all the checkpoints of priority 1 and 2 are satisfied.
Conformance Level "Triple A": all the checkpoints of priority 1,2 and 3 are satisfied.

### SEO (basic)
SEO stands for search engine optimization and includes a set of tasks aimed at improving the positioning of a web search engine.

Or technical tasks to be performed can be divided into two main sections. Internal positioning and External positioning.

#### Internal positioning
They are changes in development focused on accessibility, appearance, content, etc.

#### External positioning
They are techniques aimed at improving the visibility of the Web to external media online.

[BEEVA](https://www.beeva.com) | 2016
