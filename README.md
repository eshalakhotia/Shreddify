# Shreddify: cs0320 Term Project 2021
## Team Members: 
Megan Lu, Esha Lakhotia, Suyash Kothari, Anika Bahl

## How to Build and Run:
The app can currently only be used locally. To run, follow these steps (see runTutorial in the presentation folder if need more help):

-clone repo and run backend and frontend using two terminals:

-for backend: open a terminal, open the src folder and type command: npm start

-for frontend: in another terminal, open the server folder,

type command: mvn package

type command: ./run --gui

A site, http://localhost:3000 should pop up.

To view and play with an already created account use:

Username: anikabahl76

Password: password

## **Project Summary **

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

Main components:

Questionnaire asking preliminary questions and storing those responses for the first generation of suggestions

KD Tree to store different workouts, where each dimension will represent a different metric about the workout (e.g. intensity, cardio focus, time required, etc) and workouts can be recommended to user using some sort of nearest algorithm based on their preferences as defined in the questionnaire and their history/past ratings of workouts

New data points can be added to KDTree when users add customizations to their recommended workout, so that other users can be recommended this option
Graph containing nodes representing workouts and edges indicating their relatedness to each other uses AI algorithm to change preference metric of all workouts every time user ranks a newly completed workout (preference metric changes different magnitudes based on how similar a workout is to the workout the user just completed)

Create a database to store and pull workouts, excercises, and user information. Can be found in src.

Storing both user accounts (whose data will be protected) and workouts on graphs with weighted edges so their similarities regarding different metrics can be kept track of easily

Challenges: suggesting related workouts based on previous workouts/questionnaire (would need some type of database of workouts, nutrition, etc), account system, correlating the different workouts, and testing accuracy of suggestions.
