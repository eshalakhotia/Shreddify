import React from 'react';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";
import Backend from "./Backend";
import WorkoutDiv from "./WorkoutDiv"
import './Recommendations.css'
import WorkoutPreview from "./WorkoutPreview";

class Recommendations extends React.Component {

    constructor(props) {
        super(props);
        this.questionnaire = new Questionnaire();

        console.log("in recommendation constructor")

        this.state = {
            input : {
                energy: props.location.state.input.energy, time: props.location.state.input.time,
                targets: props.location.state.input.targets, flexibility: props.location.state.input.flexibility.toString()
            },
            output : {
                error: '', success: '', results: []
            },
            user: '',
            workoutPreview : new WorkoutPreview({name: '', time: ''})
        }

        this.getRecommendations();
    }



    //gets recommendations from backend and updates output state
    async getRecommendations() {
        const recs = await Backend.getRecs(this.state.input.energy, this.state.input.time * 60,
            this.state.input.flexibility, this.state.input.targets)

        if (recs === null) {
            console.log("something wrong with backend, response is null")

            this.setState(() => {
                return {
                    output: {
                        error: 'Sorry, something went wrong with getting your recommendations :( ' +
                            'Please make sure the backend gui is running.'
                    }
                }
            })
        }
        else {
            this.setState(() => {
                return {
                    output: {
                        error: recs.error, success: recs.success, results: recs.results
                    },
                    user: recs.user
                }
            })
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

    //renders list of recommended WorkoutDivs
    renderWorkouts() {
        if (this.state.output.results != null) {

            console.log(this.state.output.results.length)

            const workouts = this.state.output.results.map((result) => {
                const exercises = []
                result.exercises.forEach((ex) => {
                    exercises.push([ex.name, ex.time, ex.reps, ex.mType])
                })

                const metricsMap = result.metrics
                const metrics = new Map()
                Object.keys(metricsMap).forEach((key) => {
                    metrics.set(key, metricsMap[key])
                })
                console.log("metrics map" + metrics)

                return{
                    name: result.name, id: result.workoutID, time: result.workoutTime, difficulty: result.workoutDifficulty,
                    targets: result.targetAreas, equipment: result.equipment, exercises: exercises,
                    cycles: result.numCycles, metrics: metrics
                }
            })

            let workoutDivs = []
            for (const workout of workouts) {

                const workoutPreview = new WorkoutPreview(
                    {name: workout.name, id: workout.id, time:workout.time, difficulty: workout.difficulty,
                        targets:workout.targets, equipment: workout.equipment, exercises: workout.exercises,
                    cycles: workout.cycles, metrics: workout.metrics})

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

    componentDidMount() {
        console.log("component mounted")

        document.getElementById("questionnaire").style.display = "none";
        document.getElementById("workoutPreview").style.display = "none";
        document.getElementById("main").style.marginLeft = "300px";
    }

    //opens Questionnaire if needed
    openQuestionnaire() {
        document.getElementById("questionnaire").style.display = "block";
        const options = document.getElementById("options")
        while (options.firstChild) {
            options.removeChild(options.firstChild)
        }
    }

    //renders Recommendations page after Questionnaire submitted
    render() {
        //console.log("rendering Recommendations")
        const errorMessage = this.state.output.error === '' ? '' :
            `${this.state.output.error}`
        return (
            <div id="Recommendations" className="Recommendations">
                <Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire} user={this.state.user}/>

                <div id="main">
                    <h1>Our Picks For You</h1>
                    <div id="inputs" className="inputs">
                        {/*<h3>You searched for workouts with the following attributes:</h3>
                        <h4>Energy: {this.state.input.energy}/100</h4>
                        <h4>Time: {this.state.input.time}</h4>
                        <h4>Flexible: {this.state.input.flexibility}</h4>
                        <h4>Target Areas: {this.state.input.targets}</h4>*/}
                    </div>
                    <div id="results" className="results">
                        <h3>Click on any workout to get started!</h3>
                        <div className="error">
                            <h3>{errorMessage}</h3>
                        </div>
                        {this.renderWorkouts()}
                    </div>
                </div>
                {this.state.workoutPreview.renderPreview()}
                {this.questionnaire.renderQuestionnaire()};
            </div>
        )
    }
}

export default Recommendations;