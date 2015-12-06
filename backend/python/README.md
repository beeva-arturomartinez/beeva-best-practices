# Python best practices

The following contents table provides an index of the contents covered in this guide.

## Index

* [1. Introduction](#1.-introduction)
* [2. The Zen of Python (Pep 20)](#2.-the-zen-of-python-(Pep-20))
* [3. Style guide (Pep 8)](#3.-style-guide-(Pep-8))
* [4. Python 2 vs Python 3](#4.-python-2-vs-python-3)
* [5. Standard libarary](#5.-standard-libarary)
* [6. Importing libraries](#6.-importing-libraries)
* [7. Logging](#7.-logging)
* [8. Comments and documentation](#8.-comments-and-documentation)
* [9. String treatment](#9.-string-treatment)
* [10. Operators](#10.-operators)
* [11. Functions](#11.-functions)
* [12. Programming paradigms](#12.-programming-paradigms)
* [13. Exceptions](#13.-exceptions)
* [14. Input / Output](#14.-input-/-output)
* [15. Configuration files](#15.-configuration-files)
* [16. Testing](#16.-testing)
* [17. Project structure](#17.-project-structure)
* [18. Application packaging and distribution](#18.-application-packaging-and-distribution)
* [19. Development Environments (IDEs)](#19.-Development-Environments-(IDEs))
* [20. Library and virtual environment management](#20.-library-and-virtual-environment-management)
* [21. References](#21.-references)


### 1. Introduction

Python is a general purpose and high level programming language created by [Guido van Rossum](https://en.wikipedia.org/wiki/Guido_van_Rossum) in 1989. The first public release was made in 1991 (0.9.0) and it reached version 1.0 in 1994. As the time of this writing, Python 2.7.10 and Python 3.5.0 are considered as the stable versions. 

The language allows the programmer to choose between different programming paradigms, although some are more supported than others:
* Procedural.
* Imperative.
* Object oriented.
* Functional.

Python is an __interpreted language__ as well. While files containing code have the .py extension, when executing the file it is partially compiled to a byte code [link goes here] file with the .pyc extension. Hence, the .py file is only compiled for the first time (unless the code is changed) an then the .pyc file is executed upon every invocation. The byte code is then translated to machine instructions by the Python Virtual Machine (PVM) during the execution. Since the byte code needs to be interpreted by the PVM, Python's  performance is in general lower than the one provided by a compiled language.

As an interpreted language, an interactive console is provided as a tool that its part of the language. The console allows to easily ensure that the code that has been just developed performs as expected. As an example, the console supports defining functions and then testing them or loading whole modules just developed and using their inner objects or functions. Therefore the use of the console is highly recommended since it allows testing anything from code snippets to multi module programs.

Python allows employing object oriented design, or procedural programming depending on the needs. And the fact that it is an interpreted language, has led to its use for scripting purposes.

Many libraries and frameworks have been created enriching the Pythons ecosystem and they are available for the developer to be used. These utilities focus on different areas such as scientific computing (SciPy), data analysis (pandas), or web development (Django) to name a few.

Because of all the characteristics previously presented, Python provides great programming flexibility and ease of development at the cost of performance. This facts have boosted the interest on it and its adoption for product development.

In this best practices guide, a set of recommendations is provided in order to take advantage of the all the possibilities the language provides and to avoid common mistakes that can arise in Python developments. Additionally, links to external references are provided as well when required.


### 2. The Zen of Python (Pep 20)

Unlike other languages, Python has a philosophy strongly associated to it, named as “The Zen of Python”. For example, the language itself is more restrictive in some aspects such as indenting and code organisation in comparison to other languages. However, by fixing the code style, programs become more uniform since all programmers follow the same rules and code readability is increased. Because of cases like the previous one, where a benefit is obtained, the community tries to reinforce the philosophy. Therefore, it is highly recommended to follow Python's philosophy when developing programs with it.

### 2.1. PEP overview

In Python, the development of the language itself has an specific procedure to follow, which is based on Python Enhancement Proposals or PEPs. These proposals, are published as an article that describes anything related to improving existing features, adding new features or describing ways of working. Each PEP is independently evolved until it is approved by the community in order to be applied from that moment onwards. All PEPs can be found in the following site:

https://www.python.org/dev/peps/

In these type processes and in the community in general, Guido acts as a Benevolent Dictator For Life (BDFL) by making certain type of decisions and ending discussions. That is, he acts as a recognised authority.

The recommended approach is to follow PEPs as possible, that is, when known and when they apply.

### 2.2. The Zen

The Zen of Python, which is the core of the philosophy in [PEP20](https://www.python.org/dev/peps/pep-0020/), whose content is represented below:

The Zen of Python:

    Beautiful is better than ugly.
    Explicit is better than implicit.
    Simple is better than complex.
    Complex is better than complicated.
    Flat is better than nested.
    Sparse is better than dense.
    Readability counts.
    Special cases aren't special enough to break the rules.
    Although practicality beats purity.
    Errors should never pass silently.
    Unless explicitly silenced.
    In the face of ambiguity, refuse the temptation to guess.
    There should be one-- and preferably only one --obvious way to do it.
    Although that way may not be obvious at first unless you're Dutch.
    Now is better than never.
    Although never is often better than *right* now.
    If the implementation is hard to explain, it's a bad idea.
    If the implementation is easy to explain, it may be a good idea.
    Namespaces are one honking great idea -- let's do more of those!


#### 2.2.1. The Zen through examples:

http://artifex.org/~hblanks/talks/2011/pep20_by_example.html

ADD examples

#### 2.2.2. Performance optimisations

In general, Python developments seek avoiding optimisation of the code during the first development. This is due to the fact that optimisations might affect code readability or complexity. It is only if performance shows to be a crucial point for operation, when optimisations are recommended.


### 3. Style guide (Pep 8)
#### 3.1. Indentation
Use 4 spaces per indentation level.

#### 3.2. Tabs or Spaces?

Spaces are the preferred indentation method.
Tabs should be used solely to remain consistent with code that is already indented with tabs.

#### 3.3. Maximum Line Length
Limit all lines to a maximum of 79 characters.

```python
with open('/path/to/some/file/you/want/to/read') as file_1, \
     open('/path/to/some/file/being/written', 'w') as file_2:
    file_2.write(file_1.read())
```

Make sure to indent the continued line appropriately. The preferred place to break around a binary operator is after the operator, not before it. Some examples:

```python
class Rectangle(Blob):

    def __init__(self, width, height,
                 color='black', emphasis=None, highlight=0):
        if (width == 0 and height == 0 and
                color == 'red' and emphasis == 'strong' or
                highlight > 100):
            raise ValueError("sorry, you lose")
        if width == 0 and height == 0 and (color == 'red' or
                                           emphasis is None):
            raise ValueError("I don't think so -- values are %s, %s" %
                             (width, height))
        Blob.__init__(self, width, height,
                      color, emphasis, highlight)
```
#### 3.4. Blank Lines
Surround top-level function and class definitions with two blank lines.
Method definitions inside a class are surrounded by a single blank line.
Extra blank lines may be used (sparingly) to separate groups of related functions. Blank lines may be omitted between a bunch of related one-liners (e.g. a set of dummy implementations).
Use blank lines in functions, sparingly, to indicate logical sections.
#### 3.5. Source File Encoding
Code in the core Python distribution should always use UTF-8 (or ASCII in Python 2).
Files using ASCII (in Python 2) or UTF-8 (in Python 3) should not have an encoding declaration.

#### 3.6. Imports
Imports should usually be on separate lines, e.g.:

Yes:
```python
import os
import sys
```

No:
```python
import sys, os
```
It's okay to say this though:
```python
from subprocess import Popen, PIPE
```
Imports are always put at the top of the file, just after any module comments and docstrings, and before module globals and constants.

Imports should be grouped in the following order:

standard library imports
related third party imports
local application/library specific imports
You should put a blank line between each group of imports.

#### 3.7. String Quotes
In Python, single-quoted strings and double-quoted strings are the same. This PEP does not make a recommendation for this. Pick a rule and stick to it. When a string contains single or double quote characters, however, use the other one to avoid backslashes in the string. It improves readability.

For triple-quoted strings, always use double quote characters to be consistent with the docstring convention in PEP 257 .

#### 3.8. Whitespace in Expressions and Statements
Pet Peeves
Avoid extraneous whitespace in the following situations:

Immediately inside parentheses, brackets or braces.

Yes:
```python
spam(ham[1], {eggs: 2})
```
No:  
```python
spam( ham[ 1 ], { eggs: 2 } )
```
Immediately before a comma, semicolon, or colon:

Yes: 
```python
if x == 4: print x, y; x, y = y, x
```
No:  
```python
if x == 4 : print x , y ; x , y = y , x
```
However, in a slice the colon acts like a binary operator, and should have equal amounts on either side (treating it as the operator with the lowest priority). In an extended slice, both colons must have the same amount of spacing applied. Exception: when a slice parameter is omitted, the space is omitted.

Yes:
```python
ham[1:9], ham[1:9:3], ham[:9:3], ham[1::3], ham[1:9:]
ham[lower:upper], ham[lower:upper:], ham[lower::step]
ham[lower+offset : upper+offset]
ham[: upper_fn(x) : step_fn(x)], ham[:: step_fn(x)]
ham[lower + offset : upper + offset]
```
No:
```python
ham[lower + offset:upper + offset]
ham[1: 9], ham[1 :9], ham[1:9 :3]
ham[lower : : upper]
ham[ : upper]
```
Immediately before the open parenthesis that starts the argument list of a function call:

Yes: 
```python
spam(1)
```
No: 
```python
spam (1)
```
Immediately before the open parenthesis that starts an indexing or slicing:

Yes: 
```python
dct['key'] = lst[index]
```
No: 
```python
dct ['key'] = lst [index]
```
More than one space around an assignment (or other) operator to align it with another.

Yes:
```python
x = 1
y = 2
long_variable = 3
```
No:
```python
x             = 1
y             = 2
long_variable = 3
```

#### 3.9. Other Recommendations
Always surround these binary operators with a single space on either side: assignment ( = ), augmented assignment ( += , -= etc.), comparisons ( == , < , > , != , <> , <= , >= , in , not in , is , is not ), Booleans ( and , or , not ).

If operators with different priorities are used, consider adding whitespace around the operators with the lowest priority(ies). Use your own judgment; however, never use more than one space, and always have the same amount of whitespace on both sides of a binary operator.

Yes:
```python
i = i + 1
submitted += 1
x = x*2 - 1
hypot2 = x*x + y*y
c = (a+b) * (a-b)
```
No:
```python
i=i+1
submitted +=1
x = x * 2 - 1
hypot2 = x * x + y * y
c = (a + b) * (a - b)
```
Don't use spaces around the = sign when used to indicate a keyword argument or a default parameter value.

Yes:
```python
def complex(real, imag=0.0):
    return magic(r=real, i=imag)
 ```
No:
```python
def complex(real, imag = 0.0):
    return magic(r = real, i = imag)
```
Do use spaces around the = sign of an annotated function definition. Additionally, use a single space after the : , as well as a single space on either side of the -> sign representing an annotated return value.

Yes:
```python
def munge(input: AnyStr):
def munge(sep: AnyStr = None):
def munge() -> AnyStr:
def munge(input: AnyStr, sep: AnyStr = None, limit=1000):
```
No:
```python
def munge(input: AnyStr=None):
def munge(input:AnyStr):
def munge(input: AnyStr)->PosInt:
```
Compound statements (multiple statements on the same line) are generally discouraged.

Yes:
```python
if foo == 'blah':
    do_blah_thing()
do_one()
do_two()
do_three()
Rather not:

if foo == 'blah': do_blah_thing()
do_one(); do_two(); do_three()
```
While sometimes it's okay to put an if/for/while with a small body on the same line, never do this for multi-clause statements. Also avoid folding such long lines!

Rather not:
```python
if foo == 'blah': do_blah_thing()
for x in lst: total += x
while t < 10: t = delay()
```
Definitely not:
```python
if foo == 'blah': do_blah_thing()
else: do_non_blah_thing()

try: something()
finally: cleanup()

do_one(); do_two(); do_three(long, argument,
                             list, like, this)

if foo == 'blah': one(); two(); three()
```

#### 3.10 Comments

Write your comments in English.

#### 3.10.1 Block Comments
Block comments generally apply to some (or all) code that follows them, and are indented to the same level as that code. Each line of a block comment starts with a # and a single space (unless it is indented text inside the comment).

Paragraphs inside a block comment are separated by a line containing a single # .

#### 3.10.2 Inline Comments
Use inline comments sparingly.

An inline comment is a comment on the same line as a statement. Inline comments should be separated by at least two spaces from the statement. They should start with a # and a single space.

Inline comments are unnecessary and in fact distracting if they state the obvious. Don't do this:
```python
x = x + 1                 # Increment x
```
But sometimes, this is useful:
```python
x = x + 1                 # Compensate for border
```

#### 3.10.3 Documentation Strings
Conventions for writing good documentation strings (a.k.a. "docstrings") are immortalized in PEP 257 .

Write docstrings for all public modules, functions, classes, and methods. Docstrings are not necessary for non-public methods, but you should have a comment that describes what the method does. This comment should appear after the def line.

PEP 257 describes good docstring conventions. Note that most importantly, the """ that ends a multiline docstring should be on a line by itself, e.g.:

"""Return a foobang

Optional plotz says to frobnicate the bizbaz first.
"""
For one liner docstrings, please keep the closing """ on the same line.

### 3.11 Version Bookkeeping
If you have to have Subversion, CVS, or RCS crud in your source file, do it as follows.

__version__ = "$Revision$"
# $Source$
These lines should be included after the module's docstring, before any other code, separated by a blank line above and below.

### 3.12 Naming Conventions
The currently recommended naming standards

#### 3.12.1 Overriding Principle
Names that are visible to the user as public parts of the API should follow conventions that reflect usage rather than implementation.

#### 3.12.2 Descriptive: Naming Styles
There are a lot of different naming styles. It helps to be able to recognize what naming style is being used, independently from what they are used for.

The following naming styles are commonly distinguished:

 - b (single lowercase letter)
 - B (single uppercase letter)
 - lowercase
 - lower_case_with_underscores

 - UPPERCASE

 - UPPER_CASE_WITH_UNDERSCORES

 - CapitalizedWords (or CapWords, or CamelCase -- so named because of the bumpy look of its letters [3] ). This is also sometimes known as StudlyCaps.

Note: When using abbreviations in CapWords, capitalize all the letters of the abbreviation. Thus HTTPServerError is better than HttpServerError.

  - mixedCase (differs from CapitalizedWords by initial lowercase character!)

  - Capitalized_Words_With_Underscores (ugly!)

There's also the style of using a short unique prefix to group related names together. This is not used much in Python, but it is mentioned for completeness. For example, the os.stat() function returns a tuple whose items traditionally have names like st_mode , st_size , st_mtime and so on. (This is done to emphasize the correspondence with the fields of the POSIX system call struct, which helps programmers familiar with that.)

The X11 library uses a leading X for all its public functions. In Python, this style is generally deemed unnecessary because attribute and method names are prefixed with an object, and function names are prefixed with a module name.

In addition, the following special forms using leading or trailing underscores are recognized (these can generally be combined with any case convention):

_single_leading_underscore : weak "internal use" indicator. E.g. from M import * does not import objects whose name starts with an underscore.

single_trailing_underscore_ : used by convention to avoid conflicts with Python keyword, e.g.

Tkinter.Toplevel(master, class_='ClassName')
__double_leading_underscore : when naming a class attribute, invokes name mangling (inside class FooBar, __boo becomes _FooBar__boo ; see below).

__double_leading_and_trailing_underscore__ : "magic" objects or attributes that live in user-controlled namespaces. E.g. __init__ , __import__ or __file__ . Never invent such names; only use them as documented.

#### 3.12.3 Prescriptive: Naming Conventions
##### 3.12.3.1 Names to Avoid
Never use the characters 'l' (lowercase letter el), 'O' (uppercase letter oh), or 'I' (uppercase letter eye) as single character variable names.

In some fonts, these characters are indistinguishable from the numerals one and zero. When tempted to use 'l', use 'L' instead.

##### 3.12.3.2 Package and Module Names
Modules should have short, all-lowercase names. Underscores can be used in the module name if it improves readability. Python packages should also have short, all-lowercase names, although the use of underscores is discouraged.

When an extension module written in C or C++ has an accompanying Python module that provides a higher level (e.g. more object oriented) interface, the C/C++ module has a leading underscore (e.g. _socket ).

##### 3.12.3.3 Class Names
Class names should normally use the CapWords convention.

The naming convention for functions may be used instead in cases where the interface is documented and used primarily as a callable.

Note that there is a separate convention for builtin names: most builtin names are single words (or two words run together), with the CapWords convention used only for exception names and builtin constants.

##### 3.12.3.4 Exception Names
Because exceptions should be classes, the class naming convention applies here. However, you should use the suffix "Error" on your exception names (if the exception actually is an error).

##### 3.12.3.5 Global Variable Names
(Let's hope that these variables are meant for use inside one module only.) The conventions are about the same as those for functions.

Modules that are designed for use via from M import * should use the __all__ mechanism to prevent exporting globals, or use the older convention of prefixing such globals with an underscore (which you might want to do to indicate these globals are "module non-public").

##### 3.12.3.6 Function Names
Function names should be lowercase, with words separated by underscores as necessary to improve readability.

mixedCase is allowed only in contexts where that's already the prevailing style (e.g. threading.py), to retain backwards compatibility.

##### 3.12.3.7 Function and method arguments
Always use self for the first argument to instance methods.

Always use cls for the first argument to class methods.

If a function argument's name clashes with a reserved keyword, it is generally better to append a single trailing underscore rather than use an abbreviation or spelling corruption. Thus class_ is better than clss . (Perhaps better is to avoid such clashes by using a synonym.)

##### 3.12.3.8 Method Names and Instance Variables
Use the function naming rules: lowercase with words separated by underscores as necessary to improve readability.

Use one leading underscore only for non-public methods and instance variables.

To avoid name clashes with subclasses, use two leading underscores to invoke Python's name mangling rules.

Python mangles these names with the class name: if class Foo has an attribute named __a , it cannot be accessed by Foo.__a . (An insistent user could still gain access by calling Foo._Foo__a .) Generally, double leading underscores should be used only to avoid name conflicts with attributes in classes designed to be subclassed.

Note: there is some controversy about the use of __names (see below).

##### 3.12.3.9 Constants
Constants are usually defined on a module level and written in all capital letters with underscores separating words. Examples include MAX_OVERFLOW and TOTAL .

##### 3.12.3.10 Designing for inheritance
Always decide whether a class's methods and instance variables (collectively: "attributes") should be public or non-public. If in doubt, choose non-public; it's easier to make it public later than to make a public attribute non-public.


### 4. Python 2 vs Python 3
### 5. Standard libarary
### 6. Importing libraries
### 7. Logging
### 8. Comments and documentation
#### 8.1. De una sola línea
#### 8.2. En bloque
#### 8.3. Comentarios  
### 9. String treatment
### 10. Operators
#### 10.1. Asignación   
#### 10.2. Operadores aritméticos   
#### 10.3. Operadores lógicos  
#### 10.4. Condicionales  
#### 10.5. Bucles 
### 11. Functions
#### 11.1. Uso de las funciones  
#### 11.2. Lambda
#### 11.3. Decoradores    
### 12. Programming paradigms
#### 12.1. Tipo de variables   
#### 12.2. Herencia
#### 12.3. Sobrecarga de funciones   
#### 12.4. Getters y setters  
#### 12.5. Patrones de diseño 
#### OOP, estructura, funcional, ...  
### 13. Exceptions
### 14. Input / Output
### 15. Configuration files
### 16. Testing
#### 16.1. Tests
#### 16.2. Librerías útiles 
#### 16.3. Mocks
### 17. Project structure
### 18. Application packaging and distribution
### 19. Development Environments (IDEs)
### 20. Library and virtual environment management
#### 20.1. pip   
#### 20.2. virtualenv 
### 21. References
### 21. Integración continua --> solo si da tiempo


[BEEVA](http://www.beeva.com) | 2015
