#USER STORIES

## Index
* [User Stories](#user-stories)
* [User Story-Writing Process](#user-story-writing-process)
* [Frequent Mistakes in User Stories](#frequent-mistakes-in-user-stories)
* [References](#references)

### User Stories

User stories are narrative texts that describe an interaction of the user and the system, focusing on the value a user gains from the system. A true user story is a metaphor for the work being done. It is not a highly documented requirement but rather a reminder to collaborate about the topic of the user story, in other words in agile development (good agile at least), the documentation is secondary to the collaboration. User stories aren’t agile in and of themselves. Instead, their underlying agile values, collaboration and just-in-time definition, make user stories a good agile tool. Formality is specifically removed from the mix and specification of the user story is pushed as late as possible.

A good user story uses the “INVEST” model:

* Independent. Reduced dependencies  = easier to plan
* Negotiable. Details added via collaboration
* Valuable. Provides value to the customer
* Estimable. Too big or too vague = not estimable
* Small. Can be done in less than a week by the team
* Testable. Good acceptance criteria

### User Story-Writing Process

The story-writing process is integral to understanding user stories.

The typical template has 3 parts: the title, the description (or body of the user story), and the acceptance criteria.  The title is used to reference the user story and should be kept very short, around 3 to 10 words.  The description is where the meat of the user story is kept.  It is the only part that can be explained as a reasonable template. The acceptance criteria are used to determine when the user story has met the goal of the user – a sort of test.

Start by writing the title. It should be long enough to allow people on the team to differentiate it from other stories but short enough to fit on a small sticky card when written with a marker.  

Now write the description. You can use the following template: 

*As a [user role] I want to [goal] so I can [reason]*

![alt text](./static/userStoryCard.png "User Story Card")

If the description becomes lengthy (more than will fit on an index card), you should revisit the user story. It is likely it needs to be split into several stories. You might also consider whether you are trying to include too much detail. Remember that the purpose of a user story is to encourage collaboration. A user story is a promise to have a future conversation; it is not meant to document every aspect of the work, as you might in a series of traditional requirements statements.

When writing a user story, you might include some acceptance criteria, perhaps in the form of a test case or a brief description of "done," if those criteria help make the intent of the user story easier to remember. When the team is ready to work on that story, however, the team and the product owner must discuss the user story. This process will include (indeed must include) adding acceptance criteria so the team will know what done means. Later, as the team members begin to work on the story, they might contact the product owner and discuss new/different acceptance criteria as their understanding of the story grows – acceptance criteria enrich the understanding of the story, which in turn brings out new acceptance criteria and more meaningful conversations about what the customer really wants.

### Frequent Mistakes in User Stories

There are three main problems we see in stories:

First is too much information in the description. This leads to a loss of collaboration and a reliance on the old ways of documenting everything. A user story should be thought of as a "talking points", a "to-do list," or a "tickler that a conversation must occur about a topic."  The user story is a placeholder for a conversation or series of conversations: it is only through collaboration that a user story works as an agile tool; otherwise it's just a requirement written on an index card.

Too much information in a description can lead to the second problem: missing information in acceptance criteria. Before agreeing to work on a story, the team must understand the acceptance criteria. These are essential for knowing what needs to be done in order to satisfy the user story. Acceptance criteria should be detailed enough to define when the user story is satisfied, yet not so detailed as to quash collaboration. Writing acceptance criteria should not an afterthought – it is a crucial part of a user story.

The third problem is to confuse acceptance criteria and test cases. Both are necessary for a user story. Acceptance criteria answer the question, “How will I know when I’m done with the story?” Test cases answer the questions, “How do I test and what are the test steps?”  While both acceptance tests and test cases should be added to the user story via collaboration, only acceptance criteria are required. Test-driven development is often used to flesh out the test cases as the code is written.


### References

* [User Stories](https://www.mountaingoatsoftware.com/agile/user-stories)
* [User Stories Applied: For Agile Software 
Development - Mike Cohn](https://www.mountaingoatsoftware.com/books/user-stories-applied)
* [How to split a user story](http://www.agileforall.com/wp-content/uploads/2012/01/Story-Splitting-Flowchart.pdf)
