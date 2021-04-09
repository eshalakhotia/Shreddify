import React from "react";

class WorkoutInProgress extends React.Component {
    constructor(props) {
        super(props)
    }
    render () {
        return (
            <div>
                <h1>Workout Title</h1>
                <h3>Muscle Groups Targeted:</h3>
                <h3>Total Time:</h3>
                <h1>Click Button to Begin Workout</h1>
            </div>
        )
    }
}


export default WorkoutInProgress;