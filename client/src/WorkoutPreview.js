import './WorkoutPreview.css'
import React from "react";
import {Link} from "react-router-dom";

class WorkoutPreview{

    constructor(props) {
        this.name = props.name
        this.time = props.time
        this.diff = Math.round(props.difficulty)
        this.targets = props.targets
        this.equip = props.equipment
        this.exercises = props.exercises
        this.cycles = props.cycles
        this.renderTargets = this.renderTargets.bind(this)
    }

    //renders target areas
    renderTargets() {
        console.log("in render targets")
        console.log("this.target: " + this.targets)
        let tagDivs = []
        if (this.targets !== null) {
            console.log(this.targets)
            /*this.targets.forEach((t) => {
                tagDivs.push(<div id="targetTag" className="Tag">{t}</div>)
            })*/
        }
        return tagDivs
    }

    //closes preview modal
    closePreview() {
        //remove stuff inside so they are not re-rendered when opened again
        //
        document.getElementById("workoutPreview").style.display = "none";
    }

    //renders workout preview modal
    renderPreview() {
        //console.log("rendering preview name: " + this.name)
        return (
            <div id="workoutPreview" className="workout-preview-background">
                <div className="workout-preview">
                    <span className="close" onClick={this.closePreview}>&times;</span>
                    <h1>{this.name}</h1>
                    <h2>Time: {Math.ceil(this.time / 60)} minutes</h2>
                    <h2>Difficulty: {this.diff}/100</h2>
                    <h2>Target Areas</h2>
                    {this.targets}
                    <h2>Equipment</h2>
                    {this.equip}
                    <h2>Exercises</h2>
                    {this.exercises}
                    <Link to={{
                        pathname: "/Workout", //goes to workout in progress component
                        state: {
                            exercises: this.exercises,
                            cycles: this.cycles
                        }
                    }}>
                        <button id='start'>Start!</button>
                    </Link>
                </div>
            </div>
        )
    }

    toString() {
        return this.name
    }

}

export default WorkoutPreview;