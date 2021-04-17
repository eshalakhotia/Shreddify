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
                    exercises.push([ex.name, ex.time])
                })

                return{
                    name: result.name, id: result.workoutID, time: result.workoutTime, difficulty: result.workoutDifficulty,
                    targets: result.targetAreas, equipment: result.equipment, exercises: exercises,
                    cycles: result.numCycles
                }
            })

            let workoutDivs = []
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
            if (workouts.length === 0 ){
                workoutDivs.push(<span>You have no past workouts. Go to Find Workouts on the left to get some recommendations!</span>
                )
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
        }
        //if successful
        if (info.success) {
            console.log("going to home")
            this.setState({authenticated: true})
        } else {
            this.setState({error: info.error})
        }
    }

    renderRedirectToExplore() {
        if (this.state.toExplore) {
            return <Redirect to={{
                pathname: "/Recommendations",
                state: {username: this.username, user: this.user}  }}/>
        }
    }

    //renders Homepage/profile
    render() {
        return (
            <div id="Home" className="Home">
                <Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire}/>
                <div id="main">
                    <div id="logout">
                        <Link to={{
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
                    </div>
                </div>

                {this.state.workoutPreview.renderPreview()}
                {this.questionnaire.renderQuestionnaire()};
            </div>
        );
    }
}

export default Home;


/* Set the width of the side navigation to 250px and the left margin of the page content to 250px */
/*openNav() {
    document.getElementById("mySidenav").style.width = "300px";
    document.getElementById("main").style.marginLeft = "300px";
    document.getElementById("mySidenav").getElementsByClassName("closebtn")[0].style.display = "block";
    document.getElementById("mySidenav").getElementsByClassName("openbtn")[0].style.display = "none";
    document.getElementById("mySidenav").getElementsByClassName("links")[0].style.display = "block";
}*/

/* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
/*closeNav() {
    document.getElementById("mySidenav").style.width = "80px";
    document.getElementById("main").style.marginLeft = "80px";
    document.getElementById("mySidenav").getElementsByClassName("closebtn")[0].style.display = "none";
    document.getElementById("mySidenav").getElementsByClassName("openbtn")[0].style.display = "block";
    document.getElementById("mySidenav").getElementsByClassName("links")[0].style.display = "none";
}*/



/*closeFindWorkouts() {
    document.getElementById("questionnaire").style.display = "none";
}*/
