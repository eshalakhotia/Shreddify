import React from "react";

class WorkoutInProgress extends React.Component {
    constructor(props) {
        super(props)
        //console.log(props.location)
        if (props.location.state !== null) {
            this.exercises = props.location.state.exercises
        }
        //this.exercises = props.location.state.exercises //list of exercises
        this.currExerciseNum = 0;
        this.currExercise = this.exercises[this.currExerciseNum]
        //console.log("first exercise: " + this.exercises[0])
    }

    renderCurrentExercise() {
        return (
            <div>
                <h2>Currently on Exercise #{this.currExerciseNum + 1}</h2>
                <h1>{this.currExercise[0]}</h1>
                <h2>{this.currExercise[1]} seconds</h2>
                <button id='go'>Next</button>
            </div>
        )
    }

    nextExercise() {
        this.currExerciseNum++;
        this.currExercise = this.exercises[this.currExerciseNum]
    }

    render () {
        return (
            <div>
                {this.renderCurrentExercise()}
            </div>
        )
    }
}


export default WorkoutInProgress;