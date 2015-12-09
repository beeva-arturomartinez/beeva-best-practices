![alt text](https://github.com/beeva-danielpetrovic/beeva-best-practices/blob/master/agile/developerTeamScrum/ATDD/static/atdd-cycle.png "ATDD")
# ATDD (Acceptance Test-driven development)
At this point we're going to talk about tdd

## Index

* [What is ATDD?](#what-is-atdd)
* [Advantages and disadvantages](#advantages-and-disadvantages)
* [Based Features](#based-features)
* [Cycle of TDD](#cycle-of-tdd)
* [Speed is the key](#speed-is-the-key)
* [Executable documentation](#executable-documentation)
* [Design Principles](#design-principles)
* [Recommendations](#recommendations)
* [Conclusions](#conclusions)
* [References](#references)

### What is TDD?
The definition of wikipedia express:
> "Acceptance Test-Driven Development (ATDD) is a development methodology based on communication between the business customers, the developers, and the testers.ATDD encompasses many of the same practices as Specification by Example, Behavior Driven Development (BDD), Example-Driven Development (EDD), and Story Test-Driven Development (SDD). All these processes aid developers and testers in understanding the customer’s needs prior to implementation and allow customers to be able to converse in their own domain language. ATDD is closely related to Test-Driven Development[TDD]. It differs by the emphasis on developer-tester-business customer collaboration. ATDD encompasses acceptance testing, but highlights writing acceptance tests before developers begin coding"

TDD is more than just perform test at first. As a result of applying the methodology produced automated test and also occur following the first TFD "Test - First- development".
But we must not forget that the main mission of the methodology is to design the software product by examples. These examples are translated in getting test emerging architecture of our product iteratively and incrementally only what the customer asks us .

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

### Based Features
TDD methodology provides a basis features:

| Written by developers (TDD)|
| :-------------|
| How does the system|
| Test code|
| Vocabulary solution| 
| With implementation detail|

### Cycle of TDD 
The iterative cycle methodology is divided in three easy steps: *RED - GREEN - REFACTOR*

![alt text](https://github.com/beeva-danielpetrovic/beeva-best-practices/blob/master/agile/developerTeamScrum/TDD/static/red-green-refactor.png "Reed-Green-Refactor")

* We write a test
* We run all tests
* Check that the last written test fails **(RED)**
* Write code implementation
* We run all tests
* Check that all tests pass **(GREEN)**
* Refactored the code **(Refactoring)**
* We run all tests
* We check that all tests pass

### Speed is the key 
Speed is the key to success of TDD. We must make the cycle in minutes, even seconds. You can compare it like a game of ping pong.

* We write and execute all test **(PING)**.
* We write implementation **(PONG)**.
* Refactored and confirm that all tests pass **(POINT)**.

### Executable documentation 
One consequence and benefit of applying the TDD methodology is the generation of an executable documentation. The tests act as documentation for developers. Practically we do not need to put comments in the code. The nomenclature should explain the functionality test. It is easier to understand the code does a test that implementation of the code itself. The documentation does not conflict with other documents of higher or wider level. Code documentation is completely further. The documentation is kept current without any extra work because it belongs flow methodology.

### Design Principles
* YAGNI (“You ain’t gonna need it”):

> It is aimed at eliminating unnecessary code and focus on functionality. Less code means less maintenance and less chance of bugs.

* DRY (“Don’t repeat yourself”):

> We reuse previously written code. The benefit is less code to maintain and use code that we know works.

* KISS (“Keep it simple, stupid”):

> Keep the code as simple as possible while performing their function.

* OCCAM'S RAZOR (“Philosophical principle”):

> Being equal, the simplest explanation is usually the most likely. That is, when you have two solutions to the same problem, the simplest is often the best.

### Recommendations 
There are problems in applying the methodology tdd.

* TDD with an unknown technology:

> It is common when we started with new tools that problems occur when following the TDD methodology. As a first approximation is convenient "spikes " or concept testing tools. *Before driving the car it is important to know where the steering wheel, brake etc*.

* TDD middle of a project:

> One of the questions that arise when we start implementing TDD is: *Can you apply TDD project started?*For the new that has to implement course. The problem comes to what is already developed. In this case you could abusing lot of mocks. There are many legacy without coverage test and difficult to use parts. So they can live the parties will be necessary in many mocks even penalize fragility test. *Best parachute jump without it!*

### Conclusions
To apply the TDD methodology is necessary to change the mindset of how teams have worked in traditional development methodologies. This usually has a significant learning curve to start. TDD program "now" is not programmed thinking " it just in case. "Cast the first stone that the developer has not set then things have not been used. In the end this is just in case it not using!

The developments do not work with the super architect who owns the application. **So the super enterprise architect does not mount the foundation product for developers to add functionality?** The answer is a resounding no. The super architect remains a reference and asked for advice and help, but the technical team will be responsible for emerging architecture. That is all developers act as architects, designers and developers. TDD promotes the concept of "collective code" leaving aside the stereotype that certain portions of code only the certain technical touch.

Note that this method works to be met mainly two things ; first the whole team and the second TDD make the team and the client has a constant flow of communication following an iterative and incremental manner any agile methodology like Scrum. Without this low success we have applied the methodology.


### References

* [Link](https://www.beeva.com/beeva-view/metodologiasagiles/desarrollo-dirigido-por-test-el-gran-desconocido/) Beeva TDD
* [Course](https://github.com/beeva-danielpetrovic/beeva-curso-tdd) Beeva course TDD
* [Katas](https://github.com/beeva-danielpetrovic/beeva-taller-tdd/tree/master) Beeva katas TDD

___

[BEEVA](http://www.beeva.com) | 2015

