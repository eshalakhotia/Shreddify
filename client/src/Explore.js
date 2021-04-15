import React from 'react';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";
import Backend from "./Backend";
import WorkoutDiv from "./WorkoutDiv"
import './Explore.css'
import WorkoutPreview from "./WorkoutPreview";

class Explore extends React.Component {

    // const requestExplore = () => {
    //     const toSend = {
    //     };
    //     let config = {
    //         headers: {
    //             "Content-Type": "application/json",
    //             'Access-Control-Allow-Origin': '*',
    //         }
    //     }
    //
    //     axios.post(
    //         'http://localhost:4567/explore',
    //         toSend,
    //         config
    //     )
    //         .then(response => {
    //             //clearing the current canvas to redraw ways
    //
    //         })
    //
    //         .catch(function (error) {
    //             console.log(error);
    //
    //         });
    // }

    constructor(props) {
        super(props);
        this.questionnaire = new Questionnaire();
        this.state = {
            output : {
                error: '', success: '', recs: []
            },
            workoutPreview : new WorkoutPreview({name: '', time: ''})
        }
        this.getRecommendations();
    }

    componentDidMount() {
        document.getElementById("main").style.marginLeft = "300px";
        document.getElementById("questionnaire").style.display = "none";
        document.getElementById("workoutPreview").style.display = "none";
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


    async getRecommendations() {
        const recs = await Backend.explore()
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
                    }
                }
            })
        }
    }

    //renders list of recommended WorkoutDivs
    renderWorkouts() {
        if (this.state.output.results != null) {
            const workouts = this.state.output.results.map((result) => {
                const exercises = []
                result.exercises.forEach((ex) => {
                    exercises.push([ex.name, ex.time])
                })

                return{
                    name: result.name, time: result.workoutTime, difficulty: result.workoutDifficulty,
                    targets: result.targetAreas, equipment: result.equipment, exercises: exercises,
                    cycles: result.numCycles
                }
            })

            let workoutDivs = []
            for (const workout of workouts) {


                console.log("rendering new workout Div and Preview")
                console.log("name: " + workout.name)
                console.log("target: " + workout.targets)


                const workoutPreview = new WorkoutPreview(
                    {name: workout.name, time:workout.time, difficulty: workout.difficulty,
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


    render() {
        console.log("rendering wouts")
        // const errorMessage = this.state.output.error === '' ? '' :
        //     `${this.state.output.error}`
        const errorMessage = this.state.output.error === '' ? '' :
            `${this.state.output.error}`
        return (
            <div id="Explore" className="Explore">
                <Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire}/>

                <div id="main">
                    <h1>Try Something New!</h1>
                    <div id="inputs" className="inputs">
                        <h3>Here are some workouts other users enjoyed:</h3>
                    </div>
                    <hr/>
                    <div id="results" className="results">
                        <h3>Click on any workout to get started!</h3>
                        <div className="error">{errorMessage}
                        </div>
                        {this.renderWorkouts()}
                    </div>
                </div>
                {this.state.workoutPreview.renderPreview()}
                {this.questionnaire.renderQuestionnaire()}
            </div>
        )
    }
}

export default Explore;