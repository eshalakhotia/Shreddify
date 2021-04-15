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
        if (this.equipTags.length === 0) {
            tagDivs.push(<h4>No equipment necessary!</h4>)
        }
        return tagDivs
    }
    /*
    Renders a workout thumbnail with info
     */
    renderWorkout(props) {
        this.name = props.name
        //console.log("workoutName = " +  this.name)

        this.time = Math.ceil(props.time / 60) //round to nearest whole minute

        //convert from seconds to minutes
        //console.log("workoutTime = " + time)
        this.difficulty = Math.round(props.difficulty)
        //console.log("workoutDiff = " + difficulty)
        this.targets = props.targets
        //console.log("targets: " + this.targets)
        this.equipment = props.equipment
        //console.log("equipment: " + this.equipment)

        this.targetTags = []
            this.targets.forEach((target) => {
                    this.targetTags.push(target)
                }
            )

        this.equipTags = []
        this.equipment.forEach((equip) => {
            this.equipTags.push(equip)
        })
        this.equipTags.shift()

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
                            <h4>Total Time: {this.time} minutes</h4>
                            <h4>Difficulty: {this.difficulty} / 100</h4>
                        </div>
                        <div id="middle">
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