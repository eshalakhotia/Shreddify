import React from 'react';
import Sidebar from './Sidebar';
import Questionnaire from "./Questionnaire";
import Backend from "./Backend";
import WorkoutDiv from "./Workout"
import './Recommendations.css'

class Recommendations extends React.Component {

    constructor(props) {
        document.getElementById("questionnaire").style.display = "none";
        super(props);
        this.questionnaire = new Questionnaire();
        this.state = {
            input : {
                energy: props.location.state.input.energy,
                time: props.location.state.input.time,
                targets: props.location.state.input.targets,
                flexibility: props.location.state.input.flexibility.toString()
            },
            output : {
                error: '',
                success: '',
                recs: []
            },
            workouts: ''
        }

        this.getRecommendations();
    }

    //gets recommendations from backend and updates output state
    async getRecommendations() {
        const recs = await Backend.getRecs(this.state.input.energy, this.state.input.time,
            this.state.input.flexibility, this.state.input.targets)

        if (recs === null) {
            console.log("something wrong with backend, response is null")

            this.setState(() => {
                return {
                    output: {
                        error: 'Sorry, something went wrong with getting your recommendations :('
                    }
                }
            })
        }
        else {
            this.setState(() => {
                return {
                    output: {
                        error: recs.error,
                        success: recs.success,
                        results: recs.results
                    }
                }
            })
            console.log("success? " + this.state.output.success)
            console.log("error? " + this.state.output.error)
            console.log("results" + this.state.output.results)

            /*for each workout result, render
            for(const res of this.state.output.results) {
            }*/

            //const recs = this.state.output.results
            /*const workouts = this.state.output.results.map((result) => {
                return{
                    name: result.name,
                }
            })

            this.renderWorkouts(workouts)*/
        }
    }

    renderWorkouts() {
        if (this.state.output.results != null) {
            const workouts = this.state.output.results.map((result) => {
                console.log("result name: " + result.name)
                return{
                    name: result.name, time: result.workoutTime, difficulty: result.workoutDifficulty,
                    targets: result.targetAreas, equipment: result.equipment
                }
            })
            let workoutDivs = []
            for (const workout of workouts) {
                const workoutDiv = new WorkoutDiv();
                /*workoutDivs.push(<Workout className="Workout" name={workout.name} time={workout.time}
                                          difficulty={workout.difficulty} targets={workout.targets}/>)*/
                workoutDivs.push(workoutDiv.renderWorkout({
                    className: "Workout", name: workout.name, time:workout.time,
                    difficulty: workout.difficulty, targets:workout.targets, equipment: workout.equipment
                }))
                //document.getElementById("results").appendChild(workoutDiv)
            }
            return workoutDivs
        }
    }

    componentDidMount() {
        document.getElementById("questionnaire").style.display = "none";
    }

    //opens Questionnaire if needed
    openQuestionnaire() {
        document.getElementById("questionnaire").style.display = "block";
    }

    //renders Recommendations page after Questionnaire submitted
    render() {
        console.log("rendering Recommendations")
        const errorMessage = this.state.output.error === '' ? '' :
            `${this.state.output.error}`
        return (
            <div id="Recommendations" className="Recommendations">
                <Sidebar className="Sidebar" findWorkouts={this.openQuestionnaire}/*closeNav={this.closeNav} openNav={this.openNav}*//>

                <div id="main">
                    <h1>Our Picks For You</h1>
                    <div id="inputs" className="inputs">
                        <h3>You searched for workouts with the following attributes:</h3>
                        <h4>Energy: {this.state.input.energy}</h4>
                        <h4>Time: {this.state.input.time}</h4>
                        <h4>Flexible: {this.state.input.flexibility}</h4>
                        <h4>Target Areas: {this.state.input.targets}</h4>
                    </div>
                    <hr/>
                    <div id="results" className="results">
                        <div className="error">
                            <h3>{errorMessage}</h3>
                        </div>
                        {this.renderWorkouts()}
                    </div>
                </div>
                {this.questionnaire.renderQuestionnaire()};
            </div>
        )
    }
}

export default Recommendations;