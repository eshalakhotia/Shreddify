# cs0320 Term Project 2021
Team Members: Megan Lu, Esha Lakhotia, Suyash Kothari, Anika Bahl

Team Strengths and Weaknesses: 

Suyash - has done some web scraping before. Not great at front-end handlers and design 

Esha - has done basic front end working with widgets, etc; not too familiar with backend databases, etc

Megan - has art/graphics experience that could be applied to front-end aesthetics. Not familiar with getting/storing information from databases

Anika - Has done some graphic design and web-scraping. Not familiar with front-end handlers.

Project Idea(s): Fill this in with three unique ideas! (Due by March 1)

Idea 1
We’re thinking of making a Courses@Brown extension that could include:
Integrating the Critical Review and CAB in a more intuitive way than having to open up millions of tabs to choose courses
Creating a requirements tree given a course
Currently, you can see whether a course has any prerequisites or similar criteria if you click on a course on CAB. However, it is significantly more difficult if you are looking for the inverse of this: that is, given a course, you're interested in finding out how many other courses it enables you to take (i.e. how many courses does a certain course unlock).
Importantly, this tree shouldn't be static. It should update as professors add new courses to CAB or alter the requirements of a course. This is so that the program can return up-to-date results to students (to avoid advising them incorrectly).
Creating a requirements tree given a concentration
This aims to answer: for a given concentration, using the existing CAB data as well as other data that is on department websites listing concentration electives and requirements, how can I complete the concentration? The root node can be courses that the student has already taken, and the children can be potential pathways depending on course availability and the department's website.
Challenges: 
Updating data when webpages are updated (how often? How can we ensure that web page updates will maintain data types / integrity to make scraping viable?)
Different departments will store their requirements in different formats - how do we make a generalized parser?

Idea 2
Staying fit can be tough, especially when it involves having to plan your workouts around your work, current fitness, diet, and of course, energy level. I want to create a website/app (not sure yet) which will suggest a daily workout for the user based on both an initial questionnaire to establish long-term goals and daily check-ins.
Desired functionality:
- Users create accounts that they can log in and out of; accounts keep track of workouts user has completed + their rating of it
- Questionnaire that stores information about user's long-term goals (e.g. body-building, improve skill level at specific sport, strengthen specific muscle groups, lose weight, etc.)
- Daily check-ins (to be completed right before working out) that gauge how long user can work out, how much/what nutrients they are eating, how energetic they're feeling, if there's a specific style of workout they are in the mood for, whether they want a workout they are already familiar with, etc.
- Following the check-in, a workout recommendation is presented (this could be anything from a run to HIIT to yoga to lift) based on responses to questionnaire/check-ins and user ratings with ability for users to see why this workout was presented to them
- Users rate workouts once they are finished with them, stored in database where similarities between user preferences considered in weighting workout ratings when recommending next workout
- Collection of average/PR stats to see whether user is stagnating or improving with specific workout routines
Main components:
- Storing both user accounts (whose data will be protected) and workouts on graphs with weighted edges so their similarities regarding different metrics can be kept track of easily
- Creating an algorithm to recommend workouts using information from questionnaire, check-ins, and user ratings
Challenges: suggesting related workouts based on previous workouts/questionnaire (would need some type of database of workouts, nutrition, etc), account system, correlating the different questionnaires, and testing accuracy of suggestions.

Idea 3
As someone who doesn't know a lot of art history but enjoys art/decorating the spaces around me with art, I've often thought it would be useful to have a website that can suggest famous artworks that fit a specific theme the user is going for. Users can then use the suggestions to purchase prints/posters/other decor, as inspiration for their own artistic endeavors, or just for pure enjoyment.

For example, if a user chooses a color palette with muted blue, lavender, and orange tones, the platform might return Claude Monet’s “Grand Canal, Venice”, as one of the suggestions. Or, if they choose a palette with bold red, yellow, green, and blue shades, the platform might suggest Maurice de Vlaminck’s “The Orchard”.

This platform would:
 allow users to pick out a customized color palette or choose a randomized palette provided by the platform
Search through a database of famous pieces and analyze the overall pixel colors of each piece, building up some “color profile” containing several of the most used colors in the piece.
return a board of famous paintings of various genres whose color profiles most closely match the provided color scheme

Challenges:
Maintaining a database of paintings--where to get info from and how to store it
Efficiently doing step 2) for all paintings (?) every time the user enters a query
Coming up with an algorithm that generates a reasonable overall color profile for a piece


**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 15)_

**4-Way Checkpoint:** _(Schedule for on or before April 5)_

**Adversary Checkpoint:** _(Schedule for on or before April 12 once you are assigned an adversary TA)_

## How to Build and Run
_A necessary part of any README!_
