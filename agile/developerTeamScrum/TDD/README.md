![alt text](https://github.com/beeva-danielpetrovic/beeva-best-practices/blob/master/agile/developerTeamScrum/TDD/static/Que-es-TDD.png "TDD")
# TDD (Test-driven development)

## Index

* [TDD "The Great Unknown"](#tdd-the-great-unknown)
* [What is TDD?](#what-is-tdd)
* [Advantages and disadvantages](#advantages-and-disadvantages)
* [Cycle of TDD](#cycle-of-tdd)
* [Speed is the key](#speed-is-the-key)
* [Executable documentation](#executable-documentation)
* [Design Principles](#design-principles)
* [Recommendations](#recommendations)
* [References](#references)

### TDD "The Great Unknown" 
The TDD concept is generally misunderstood by people. Perhaps the acronym TDD "Test-driven development " are not the most convenient. The "T" of TDD cheats a bit for TDD is not a testing methodology else a development methodology and design software.
In practice TDD tends to confuse concepts are interrelated but do not start from the same root:
> Self -testing -code ! = TDD ! = Unit testing

Self -testing -code ! = TDD , TDD has not primary mission the test coverage. This coverage is a benefit that is obtained by applying the methodology. This means that you can have high test coverage making after developing the product. It isnt´t condition applying TDD or to have high test coverage.

TDD ! = Unit testing , unit test as much as possible try to be decoupled by definition. In exchange for not applying the compulsory decoupling TDD code is required although it is convenient. Moreover, in many parts of it will be convenient to be a "small" coupled for some tests. Moreover establish any automated test framework is a consequence of applying the methodology.

People also believed that applying TDD the need for other types of tests is eliminated. This is completely false as there are types of test as system test "End to End" performing QA that do not comply with the principles that must meet a test in TDD. TDD therefore does not eliminate the QA work even if indirectly reduced because the number of bugs that can appear in higher-level test without having appeared in unit testing or integration of lower level is very low probability occur.

### What is TDD?
The definition of wikipedia express:
> "Test-driven development (TDD) is a software development process that relies on the repetition of a very short development cycle: first the developer writes an (initially failing) automated test case that defines a desired improvement or new function, then produces the minimum amount of code to pass that test, and finally refactors the new code to acceptable standards"

### Advantages and disadvantages
The main advantages and disadvantages are:

| Advantages                                 | disadvantages |
| :-------------                             |:-------------|
| The tests act as low documentation         | Always focus on micro design |
| Debugging less time                        | The developer makes testing : it goes against good testing practices |
| Higher quality software                    | More development time delay so the feedback to the user |
| Lower maintenance costs                    | Learning curve for new teams |
| Avoid overdesigned : a test requires us to write the minimum functionality | |
| Solid test coverage                        | |

### Cycle of TDD 
The iterative cycle methodology is divided in three easy steps:
> RED - GREEN - REFACTOR


### Speed is the key 
xxxx

### Executable documentation 
xxxx

### Design Principles 
xxxx

### Recommendations 
xxxx



### References

* [Link](https://www.beeva.com/beeva-view/metodologiasagiles/desarrollo-dirigido-por-test-el-gran-desconocido/) Beeva TDD
* [Course](https://github.com/beeva-danielpetrovic/beeva-curso-tdd) Beeva course TDD
* [Katas](https://github.com/beeva-danielpetrovic/beeva-taller-tdd/tree/master) Beeva katas TDD

___

[BEEVA](http://www.beeva.com) | 2015
