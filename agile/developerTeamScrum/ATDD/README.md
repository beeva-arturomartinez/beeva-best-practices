![alt text](https://github.com/beeva-danielpetrovic/beeva-best-practices/blob/master/agile/developerTeamScrum/ATDD/static/atdd-cycle.png "ATDD")
# ATDD (Acceptance Test-driven development)
At this point we're going to talk about atdd

## Index

* [What is ATDD?](#what-is-atdd)
* [Based Features](#based-features)
* [Objectives ATDD](#recommendations)
* [Criteria acceptance vs Acceptance test](#recommendations)
* [Writing acceptance tests](#recommendations)
* [Properties acceptance tests](#recommendations)
* [Workflow ATDD](#cycle-of-tdd)
* [Conclusions](#conclusions)
* [References](#references)

### What is TDD?
The definition of wikipedia express:
> "Acceptance Test-Driven Development (ATDD) is a development methodology based on communication between the business customers, the developers, and the testers.ATDD encompasses many of the same practices as Specification by Example, Behavior Driven Development (BDD), Example-Driven Development (EDD), and Story Test-Driven Development (SDD). All these processes aid developers and testers in understanding the customer’s needs prior to implementation and allow customers to be able to converse in their own domain language. ATDD is closely related to Test-Driven Development[TDD]. It differs by the emphasis on developer-tester-business customer collaboration. ATDD encompasses acceptance testing, but highlights writing acceptance tests before developers begin coding"

Its acronym define it as test driven development of acceptance. ATDD is a requirements specification methodology that more testing methodology. ATDD is the beginning of the iterative development cycle level, because the basis of an acceptance test we deepen implementation through successive TDD unit testing to shape the code that finally meets the defined acceptance criteria.

### Based Features
ATDD methodology provides a basis features:

|Written by clients (ATDD)|
| :-------------|
| What the system does|
| Test of features|
| Business Vocabulary| 
| Without implementation detail|

### Objectives ATDD
ATDD focuses on the implementation of a completely different way to traditional methods. One of the main objectives is to provide customer ATDD rapid feedback of the progress of development by acceptance tests. The business analyst's job becomes to replace pages and pages of requirements written in natural language, for example executable consensus emerged among the various team members, including of course the client. I speak not replace all the documentation, but the requirements, which I consider a subset of the documentation. For this, a specification of less ambiguous and more specific requirements is performed by using examples of Users stories. Written great novels are left behind in defining requirements years ago. The examples do not replace the talks, it is achieved between foster these customer, testers and developers.
The methodology helps both the client understand what you want and developers understand what the customer wants specifying the behavior of each user story and avoiding the free interpretation of casuistry not covered by the specification.

### Criteria acceptance vs Acceptance test
People often make mistakes when defining the criteria and acceptance test. Often we confuse who defines the criteria and acceptance test.

* What is the difference between acceptance criteria and test?

> Criteria are the set of conditions to be met by a story to be given for good.
> The acceptance criteria consist of:
> * Actor
> * Verb (describes a behavior on the system actor)
> * Observable result

* Who should write the test for acceptance?

> The acceptance test must write the Product Owner, but unlike what many people think do not have to do alone, this methodology is based ultimately on communication and collaboration among all stakeholders of the system. We all know that the tests require a technical component that in some cases the customer can get to have it but in other cases, so the customer can ask for help from developers or QA technical aspects. The same happens in reverse when developers need help understanding the test as business rules are often confusing.

### Writing acceptance tests
The acceptance test must be automated to be meaningful in an agile environment. The tests must be specific and not allow ambiguity and avoid generic scenarios. An example of incorrect test could be "add cards to the system ", this is very generic. Instead "Add Card 4777308023346398" , making specific test.
Moreover avoid the implementation details acceptance test. An example of incorrect test could be "By introducing the card and click the Add button, a new record is created in DB" aspects are entering records databases, Web events etc. Instead "The user saves card 4777308023346398 in the payment screen" has a vocabulary of business and is not in technical parts of the system.

### Properties acceptance tests
The acceptance test must meet the following properties:

> * **SPECIFIC**: Explicitly defined and definite.
> * **MEASURABLE**: Possible to observe and quantify.
> * **ARCHIEVABLE**: Capable of existing or taking place.
> * **RELEVANT**: Having a connection with the story.
> * **TIME BOUND**: When will the outcome be observed.

### Workflow ATDD
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

