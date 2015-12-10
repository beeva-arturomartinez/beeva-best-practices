# Python best practices

## Index

### 1. Introducción
### 2. Python Zen (Pep 20)





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
When people talk or write for what version they should use of Python, the typical response is this appointment: “Python 2.x is legacy, Python 3.x is the present and future of the language” [1]. For this reason It shoulds use Python 3 in new projects and it is more important, if the project will be long in time. Because Python 2.x won’t receive new improvements, it only has bug support and it finishes in 2020 [2].

The biggest disadvantage of  Python 3 is the support from libraries, frameworks, packages… Because nowadays have a libraries, which they only support Python 2.x, but this problem is fixing bit by bit. For this reason we only should use Python 2.x , if it needs a library that only support  Python 2.x. If the project will use Python 2.x, we should write the code thinking in the future port. For this reason we can start write code used for example:

* modules `__future__`
* Create class with inheritance from base class `Object`
* Using the new syntaxes, for example: `exceptions, prints, …`

To help this work, it’s possible use tools to help write code to make a future port. For example in IDE Pycharm can be configured to check for compatibility. Of course nowadays exists tools to try to port code in Python 2.x to Python 3.x automatically, but it doesn’t always work correctly.

Main changes in Python 3.x respect to Python 2.x [1]:

* The best improvement is in Strings, now in Python 3.x The strings are unicode by default.
* Upgrade of bytes are formed unicodes.
* Improvement in packages for concurrence (Threads, locks, …).
* Change exceptions.
* Change print function.
* Change division.
* Change List Comprehension.
* And other changes, for example in improve readability.

### 5. Librería estándar
### 6. Importar librerías
### 7. Logging
### 8. Comentarios y documentación
#### 8.1. De una sola línea
#### 8.2. En bloque
#### 8.3. Comentarios  
### 9 Tratamiento de Strings
### 10. Operadores
#### 10.1. Asignación   
#### 10.2. Operadores aritméticos   
#### 10.3. Operadores lógicos  
#### 10.4. Condicionales  
#### 10.5. Bucles
### 11. Funciones
#### 11.1. Uso de las funciones  
#### 11.2. Lambda
#### 11.3. Decoradores    
### 12. Paradigmas de programación
#### 12.1. Tipo de variables   
#### 12.2. Herencia
#### 12.3. Sobrecarga de funciones   
#### 12.4. Getters y setters  
#### 12.5. Patrones de diseño
#### OOP, estructura, funcional, ...  
### 13. Excepciones
### 14. E/S
### 15. Ficheros de configuración
### 16. Testing
#### 16.1. Tests
#### 16.2. Librerías útiles
#### 16.3. Mocks
### 17. Estructura típica de proyecto
### 18. Empaquetado y distribución de aplicaciones
### 19. Entornos de desarrollo (IDEs)
To develop a Python program, it isn’t necessary to have a IDE. You can develop it in text editor, for example gedit, Sublime Text, Atom… and run it Python console. But if you want a IDE to develop, because you want features how debugger, autocomplete… Nowadays exist a lot of for Python, exists commercial and noncommercial and they have different features. In this document will list only the most popular at this moment (you should know exist other options) and it won’t compare that is the best IDE. You are free to decide, which one to use.

List of IDEs:
* Pycharm [1] is nowadays one of the most used for python developers. It has a free licence, named  free community and other commercial named professional. The free licence, it is very nice and include a lot of features, for example:
  * Debugger
  * Intelligent Code Editor, autocomplete, code analice...
  * Refactor code to PEP 8
  * And other features.
* Eric [2], it is other IDE for python, it is really nice, because it is open source and you can use free. A few features for this IDE are the following:
  * Debugger
  * Intelligent Code Editor, autocomplete, code analice...
  * Integrated version control interface
  * And other features.
* WingWare [3], this is another nice IDE for develop with Python, It is a commercial IDE and you will need pay to use it. A few features for this IDE are the following (similar to before IDEs):
  * Debugger
  * Intelligent Code Editor, autocomplete, code analice...
  * And other features.
* Eclipse with Pydev [4], other nice IDE to develop with Python is Eclipse. This IDE is famed in JAVA, but you can use with Python adding the plugin Pydev. It is free and you don’t need pay for use it. A few features for this IDE are the following (similar to before IDEs):
  * Debugger
  * Intelligent Code Editor, autocomplete, code analice...
  * And other features.

You can discovered other IDEs for Python and  their description in the following link:


### 20. Gestión de librerías y entornos virtuales

In this chapter explains, how add new libraries or packages to the project. It is possible thanks to pip and when you use PIP, it is necessary explain virtual environments.

#### 20.1. pip   
PIP is the package manager for Python. When the project need a extra library, it’s really easy add it. Only need execute the following command, if the package exist in PIP:

`pip install <name package>`

The before command install the latest version of the package. If the project need a specific version, it’s possible indicate it. Use the following command:

`pip install <name package>==<version>`

If the package doesn’t exist in PIP, it’s possible add the url of repository to install it. Use the following command:

`pip install <CVS+URL>`

example:

`pip install git+https://github.com/mirepo.git`

It is possible export all packages, that the project has install to a config file. The convention say, that name of this file is requirements.txt. To do this, execute the following command:

`pip freeze > requirements.txt`

When the project has file requirements.txt, it possible install all packages inside of the file. Use the next command:

`pip install -r requirements.txt `

Pay attention if the project contains a package that was installed with a url of repository. The command pip freeze will not correctly generate the file, because it will put the name of package with the version (not the repository url) and when pip try to install this package, it will not install the desired package. It’s possible to fix this problem manually. It is only necessary to change its line with <CVS+URL> that belongs to package repository (This url is the same used when it was installed with pip).

The documentation can be read in the next link:

<https://pip.readthedocs.org/en/stable/>

#### 20.2. virtualenv

Virtualenv is a tool to generate a virtual environments for Python. It is really useful, because when in the same machine has several projects use python and their use some differents packages. This packages can have conflicts among themself or need use the same package with different versions, without virtualenv have a big problem, because the packages install in Python and all projects use it. But it is possible fix it with this tool and can have unlimited differents virtual environments in the same machine. For this reasons and because it is a bad practice, you never install packages in python without create a virtualenv and use pip with sudo.

To use virtualenv, it is necessary install it. When it is installed, it necessary create a virtual environment, it is doing with this command:

`virtualenv <dir/name_environment>`

Now to use or install packages you need active, it is easy only execute this code:

`source route_environment/bin/activate`

When the environment is not necessary, remove the directory.

Of course virtualenv has several options, you can see in the documentation:

<http://virtualenv.readthedocs.org/en/latest/index.html>

### 21. Integración continua --> solo si da tiempo
### 23. Bibliografía  


[BEEVA](http://www.beeva.com) | 2015
