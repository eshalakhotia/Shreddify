import React from 'react';
import './Home.css';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";
import {Link, Redirect} from "react-router-dom";
import WorkoutPreview from "./WorkoutPreview";
import WorkoutDiv from "./WorkoutDiv";
import Backend from "./Backend";

/**
 * Home screen/profile
 */
class Home extends React.Component {

    constructor(props) {
        super(props);
        this.username = props.location.state.username
        this.user = props.location.state.user
        this.pastWorkouts = this.user.pastWorkouts
        this.questionnaire = new Questionnaire();
        this.state = {
            workoutPreview : new WorkoutPreview({name: '', time: ''}),
            toExplore : false
        }
    }

    componentDidMount() {
        document.getElementById("main").style.marginLeft = "300px";
    }

    //opens Questionnaire modal when needed
    openQuestionnaire() {
        document.getElementById("questionnaire").style.display = "block";
        const options = document.getElementById("options")
        while (options.firstChild) {
            options.removeChild(options.firstChild)
        }
    }


    //display preview component 'info' associated with clicked WorkoutDiv
    openWorkoutPreview(info) {
        this.setState(() => {
            return {
                workoutPreview: info
            }
        })
        document.getElementById("workoutPreview").style.display = "block";
    }

    renderWorkouts() {
        if (this.pastWorkouts != null) {
            const workouts = this.pastWorkouts.map((result) => {

                const exercises = []
                result.exercises.forEach((ex) => {
                    exercises.push([ex.name, ex.time, ex.reps, ex.mType])
                })

                return{
                    name: result.name, id: result.workoutID, time: result.workoutTime, difficulty: result.workoutDifficulty,
                    targets: result.targetAreas, equipment: result.equipment, exercises: exercises,
                    cycles: result.numCycles
                }
            })

            let workoutDivs = []
            if (workouts.length === 0 ){
                workoutDivs.push(<h3>You have no past workouts. Go to Explore or Find Workouts to get some recommendations!</h3>
                )
            } else {
                workoutDivs.push(<h3>Click on any workout to get started!</h3>)
            }

            for (const workout of workouts) {
                const workoutPreview = new WorkoutPreview(
                    {name: workout.name, id: workout.id, time:workout.time, difficulty: workout.difficulty,
                        targets:workout.targets, equipment: workout.equipment, exercises: workout.exercises,
                        cycles: workout.cycles})

                //create workout thumbnail with associated Preview component
                const workoutDiv = new WorkoutDiv( {open: this.openWorkoutPreview.bind(this),
                    preview: workoutPreview});

                workoutDivs.push(workoutDiv.renderWorkout({
                    className: "Workout", name: workout.name, time:workout.time,
                    difficulty: workout.difficulty, targets:workout.targets, equipment: workout.equipment,
                    openWorkout: this.openWorkoutPreview
                }))

            }

            return workoutDivs
        }
    }

    toExplore() {
        this.setState(() => {return {
            toExplore : true
        }
        })
    }

    async toLogOut() {
        const info = await Backend.logOut()
        if (info === null) {
            console.log("response is null, something wrong with backend handler")
        } else {
            console.log("success: " + info.success)
            console.log("error: " + info.error)
            console.log("User: " + info.results)
            if (info.success) {
                console.log("going to home")
                this.setState({authenticated: true})
            } else {
                this.setState({error: info.error})
            }
        }
    }

    renderRedirectToExplore() {
        if (this.state.toExplore) {
            return <Redirect to={{
                pathname: "/Recommendations",
                state: {username: this.username, user: this.user}  }}/>
        }
    }

    renderAchievements() {
        let achievementDivs = []

        if (this.user.streak > 1) {
            achievementDivs.push(
                <div className="achievement">
                    <h2>Worked out for {this.user.streak} days in a row!</h2>
                </div>
            )
        } else if (this.user.streak == 1) {
            achievementDivs.push(
                <div className="achievement">
                    <h2>Worked out for {this.user.streak} day in a row!</h2>
                </div>
            )
        }

        if (this.user.pastWorkouts.length == 1) {
            achievementDivs.push(
                <div className="achievement">
                    <h2>Completed your first workout!</h2>
                </div>
            )
        }

        //account creation
        achievementDivs.push(
            <div className="achievement">
                <h2>Created an account: on your way to get SHREDDED!</h2>
            </div>
        )

        return achievementDivs
    }

    //renders Homepage/profile
    render() {
        return (
            <div id="Home" className="Home">
                <Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire} user={this.user}/>
                <div id="main">
                    <div id="logout">
                        <Link underline={"none"} to={{
                            pathname: "/",
                            state: {
                                input: this.input
                            }
                        }}>
                            <button id='logOutButton' onClick={this.toLogOut.bind(this)}><span>Log Out!</span></button>
                        </Link>
                    </div>
                    <h1>Welcome, {this.username}!</h1>
                    <h3 id="title-h3">Try one of your past workouts, or find some new recommendations on the left. It's time to get SHREDDED!</h3>
                    <div id="past-workouts">
                        <h2>Your Workouts</h2>
                        {this.renderWorkouts()}
                    </div>
                    <div id="achievements">
                        <h2>Your Achievements</h2>
                        {this.renderAchievements()}
                    </div>
                </div>

                {this.state.workoutPreview.renderPreview()}
                {this.questionnaire.renderQuestionnaire()};
            </div>
        );
    }
}

export default Home;

