import './Workout.css'
import {Link} from "react-router-dom";
import React from "react";

//Workout thumbnail showing title, difficulty, muscle groups, equipment, etc.
class WorkoutDiv {

    constructor(props) {
        this.openPreview = props.open;  //function that opens WorkoutPreview
        this.preview = props.preview; //WorkoutPreview object
    }

    //updates workout preview modal with this WorkoutDiv's info
    /*updatePreview() {
        this.preview.updateInfo({name: this.name, time: this.time, difficulty: this.difficulty,
        targets: this.targets, equipment: this.equipment})
    }*/

    renderTargetTags() {
        let tagDivs = []
        this.targetTags.forEach((t) => {
            tagDivs.push(<div id="targetTag" className="Tag">{t}</div>)
        })
        return tagDivs
    }
    renderEquipTags() {
        let tagDivs = []
        this.equipTags.forEach((t) => {
            tagDivs.push(<div id="equipTag" className="Tag">{t}</div>)
        })
        return tagDivs
    }
    /*
    Renders a workout thumbnail with info
     */
    renderWorkout(props) {
        this.name = props.name
        //console.log("workoutName = " +  this.name)
        this.time = props.time
        //console.log("workoutTime = " + time)
        this.difficulty = props.difficulty
        //console.log("workoutDiff = " + difficulty)
        this.targets = props.targets
        //console.log("targets: " + this.targets)
        this.equipment = props.equipment
        //console.log("equipment: " + this.equipment)

        this.targetTags = []
        //const tagDiv = document.getElementById('tags')
        //if (tagDiv != null) {
            //if (!tagDiv.firstChild) {
            this.targets.forEach((target) => {
                    //console.log(target)
                    //const tag = document.createElement('div')
                    //tag.className = "Tag"
                    //tag.innerHTML = target
                    this.targetTags.push(target)
                    //tagDiv.appendChild(tag)
                }
            )
            //}
        //}

        this.equipTags = []
        this.equipment.forEach((equip) => {
            this.equipTags.push(equip)
        })

        const openWorkout = e => {
            props.openWorkout()
        }

        return (
            /*<Link to={{
                pathname: "/Workout",
            }}>*/
                <div className="Workout" onClick={() => {this.openPreview(this.preview)}
                }>
                    <div id="wrapper">
                        <div id="left">
                            <h3>{this.name}</h3>
                            <h3>Total Time: {this.time} minutes</h3>
                            <h3>Difficulty: {this.difficulty} / 10</h3>
                        </div>
                        <div id="middle">
                            <h3>Exercises</h3>
                            <div id="tags">
                                <h3>Target Areas</h3>
                                {this.renderTargetTags()}
                            </div>
                        </div>
                        <div id="right">
                            <h3>Equip</h3>
                            {this.renderEquipTags()}
                        </div>
                    </div>
                </div>
            /*</Link>*/
        )
    }
}

export default WorkoutDiv